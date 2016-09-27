/* MIT License
 *
 * Copyright (c) 2016 James MacAdie
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ExcelCompare2;

import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author james.macadie
 */
public class Formula {
    private final String _formula;
    private final String _formulaR1C1;
    private final CellRef _cellRef;
    private final String _shellFormula;
    private final List<CellRefExt> _references;
    private final List<CellRefR1C1> _referencesR1C1;
    private final String _text;
    
    public Formula(String formula, CellRef cellRef, String text) {
        this._formula = formula;
        this._cellRef = cellRef;
        this._references = new LinkedList<>();
        this._referencesR1C1 = new LinkedList<>();
        
        String[] tmp = convertToR1C1();
        this._formulaR1C1 = tmp[0];
        this._shellFormula = tmp[1];
        this._text = text;
    }
    
    private static String getCellRefDelimiters(boolean incExtSheet) {
        // Build delimiters
        // Important as e.g. "A1A1" is not a cell reference
        String whitespace = "\\s";
        String brackets = "\\(\\)\\[\\]\\{\\}";
        String operators = "\\*/\\+\\-\\^\\<\\>\\=&";
        String rangeConcat = ":";
        String extSheet = "!";
        String argSep = ",";
        String out =  "[" +
                        whitespace +
                        brackets +
                        operators +
                        rangeConcat +
                        argSep;
        if (incExtSheet) {
            out += extSheet + "]";
        } else {
            out += "]";
        }
        return out;
    }
    
    private static String getCellRefRegex() {
        // Individual cell ref patern
        String cellPatUnit = "(\\$)?([A-Z]{1,3})(\\$)?(\\d+)";
        // Sheet patern
        String sheetUnit = "('[^/\\\\?*\\[\\]]{1,31}'\\!|[A-Za-z0-9_]{1,31}\\!)";
        String extWBUnit = "(\\[.+\\])";
        // TODO: multi-sheet references e.g. =SUM(Sheet2:Sheet3!A1)
        // TODO: Full columns and rows
        String collPatUnit = "(\\$)?([A-Z]{1,3}):(\\$)?([A-Z]{1,3})";
        String rowPatUnit = "(\\$)?(\\d+):(\\$)?(\\d+)";
        // Delimiters
        String delimiters = getCellRefDelimiters(false);
        // Create the full regex for finding cell ref parts in any formulae
        return "(\"[^\"]*\")|" + // First find anything in double quotes. Find so we can make sure we can ignore
                "(?<=" + delimiters + ")" +
                extWBUnit + "?" +
                sheetUnit + "?" + 
                cellPatUnit + "(:)?" +
                "(" + cellPatUnit + ")?" +
                "(?=" + delimiters + "|$)";
                             
    }
    
    private static String replaceCellRef(String formula, String oldCell, String replaceCell) {
        String delimiters = getCellRefDelimiters(true);
        String cellPat = "(" + delimiters + ")" +
                         "(" + oldCell.replaceAll("\\$", "\\\\\\$") + ")" +
                         "(" + delimiters + "|$)";
        
        Pattern p = Pattern.compile(cellPat);
        Matcher m = p.matcher(formula);
        
        String replacement = replaceCell.replaceAll("\\$", "\\\\\\$");
        replacement = "$1" + replacement + "$3";
        
        String output = formula;
        if (m.find()) {
            output = m.replaceAll(replacement);
        }
        
        return output;
    }
    
    private String[] convertToR1C1() {
        // Convert formula to R1C1
        
        // Only interpret formulae
        if (this._formula.length() > 0 &&
            !this._formula.substring(0, 1).equals("="))
            return new String[] {this._formula, this._formula};
        
        StringBuffer edit = new StringBuffer(this._formula.length());
        String shellFormula = "";
        
        Pattern cellPat = Pattern.compile(getCellRefRegex());
        Matcher matcher = cellPat.matcher(_formula);
        
        String sheet;
        String extWB;
        String col;
        int row;
        boolean colAbs;
        boolean rowAbs;
        CellRefExt cell;
        String replaceText;
        int lastPos = 0;
        
        while (matcher.find()) {
            
            // If no cell ref found move straight onto next match
            // This catches the instance where we've found text in double quotes
            if (matcher.group(5) == null)
                continue;
            
            // Extract the formula parts
            extWB = (matcher.group(2) == null) ? "" : matcher.group(2);
            sheet = (matcher.group(3) == null) ? "" : matcher.group(3);
            colAbs = (matcher.group(4) != null);
            col = matcher.group(5);
            rowAbs = (matcher.group(6) != null);
            row = Integer.parseInt(matcher.group(7));
            
            // Build Cell object and add it to our refernces
            cell = new CellRefExt(col, row, colAbs, rowAbs, sheet, extWB);
            _references.add(cell);
            _referencesR1C1.add(cell.toR1C1(_cellRef));
            
            // Build replacement text
            replaceText = extWB + sheet + cell.toR1C1(_cellRef).toString();
            
            // Get second part in a multi-cell range
            colAbs = (matcher.group(10) != null);
            col = matcher.group(11);
            rowAbs = (matcher.group(12) != null);
            row = (matcher.group(13) == null) ? 0 : Integer.parseInt(matcher.group(13));
            
            // If we have a multi-cell range, add the other part
            // Important to do it like this as the same sheet and wb part that
            // was only attached initial refence needs to be applied to the
            // second refernce too
            if (col != null) {
                // Build Cell object and add it to our refernces
                cell = new CellRefExt(col, row, colAbs, rowAbs, sheet, extWB);
                _references.add(cell);
                _referencesR1C1.add(cell.toR1C1(_cellRef));
                
                replaceText += ":" + cell.toR1C1(_cellRef).toString();
            }
            
            // Escape any dollars in the replacement text
            matcher.appendReplacement(edit, Matcher.quoteReplacement(replaceText));
            
            // Build the shell formula
            shellFormula += extWB + sheet + _formula.subSequence(lastPos, matcher.start());
            lastPos = matcher.end();
            
        }
        
        matcher.appendTail(edit);
        shellFormula += _formula.substring(lastPos);

        return new String[] {edit.toString(), shellFormula};
    }
    
    public String getR1C1() {
        return _formulaR1C1;
    }
    
    public String getA1() {
        return _formula;
    }
    
    public String getText() {
        return _text;
    }
    
    public CellRef getCellRef() {
        return _cellRef;
    }
    
    public Formula getCopiedTo(CellRef cell) {
        if (cell.equals(_cellRef))
            return this;
        
        String newFormula = _formula;
        int rows = cell.getRow() - _cellRef.getRow();
        int cols = cell.getCol() - _cellRef.getCol();
        CellRef trans;
        
        // Loop through the references moving them and replacing them in formula
        for (CellRef orig : _references) {
            trans = orig.move(rows, cols);
            newFormula = replaceCellRef(newFormula, orig.toString(), trans.toString());
        }
        return new Formula(newFormula, cell, this._text);
    }
    
    public Formula getTranslatedTo(CellRef cell, CellTranslations trans) {
        if (cell.equals(_cellRef) && trans.size() == 0)
            return this;
        
        String newFormula = _formula;
        CellRef translated;
        String find;
        String replace;
        
        // Loop through the references moving them and replacing them in formula
        for (CellRef orig : _references) {
            translated = TranslatedCondensedFormulae.applyTranslations(orig, trans);
            // Escape the dollars in the cell ref
            find = orig.toString().replaceAll("\\$", "\\\\\\$");
            replace = translated.toString().replaceAll("\\$", "\\\\\\$");
            newFormula = newFormula.replaceAll(find, replace);
        }
        return new Formula(newFormula, cell, this._text);
    }
    
    public double translatedDistance(Formula comp) {
        
        // If the rest of the formula doesn't match return -1
        // TODO: could consider some residual Levenstien distance measure to
        // capture formuale that have been moved and (moderately!) edited
        if (!comp._shellFormula.equals(_shellFormula))
            return -1;
        
        // TODO: verify lists retain pairwise order
        double dist = 0;
        for (int i = 0; i < _referencesR1C1.size(); i++) {
            dist += _referencesR1C1.get(i).distance(comp._referencesR1C1.get(i));
        }
        return dist;
    }
    
    public boolean translatedMatch(Formula comp, List<Integer> map, CellTranslations.RowCol rc) {
        
        // Assumes map is applied to the current Formula and then compared to
        // the comp Formula
        
        // TODO: don't try to translate cell references that are either off 
        // sheet or off workbook. They havn't been translated by the map passed 
        // in and could be out of range of the current sheet, which would cause 
        // the program to crash
        
        // TODO: can we consider translations found on other sheets? 
        // Seems way too hard
        
        // If the rest of the formula doesn't match return false
        // TODO: could consider some residual Levenstien distance measure to
        // capture formuale that have been moved and (moderately!) edited
        if (!comp._shellFormula.equals(_shellFormula))
            return false;
        
        ListIterator<CellRefExt> iter = _references.listIterator();
        ListIterator<CellRefExt> iterComp = comp._references.listIterator();
        CellRefExt cell;
        CellRefExt cellComp;
        
        // Loop through all the refernces
        while (iter.hasNext()) {
            
            // Shouldn't happen (as we already have a shell match) but if the
            // lists of references don't match then return non-match
            if (!iterComp.hasNext())
                return false;
            
            // Get the cell refs
            cell = iter.next();
            cellComp = iterComp.next();
            
            // If absolute state isn't the same then no match
            if (cell.getRowAbs() != cellComp.getRowAbs() ||
                cell.getColAbs() != cellComp.getColAbs())
                return false;
            
            // If on different sheets then the cells don't match
            // Note this shouldn't happen as the sheet ref parts are left in the
            // shell formula
            if (!cell.getSheet().equals(cellComp.getSheet()))
                return false;
            
            // TODO: you can get a cell on the same sheet with an explicit sheet
            // refernce in the formula. Need to guard against this but requires
            // knowing which sheet you're on in the Formula object
            
            // If mapped and the translation doesn't match then no match
            // Only translate cells with no sheet (i.e. on current sheet)
            // Rows
            if(cell.getSheet().equals("") &&
               rc == CellTranslations.RowCol.ROW &&
               map.get(cell.getRow()) != null &&
               map.get(cell.getRow()) != cellComp.getRow())
               return false;

            // If mapped and the translation doesn't match then no match
            // Only translate cells with no sheet (i.e. on current sheet)
            // Columns
            if(cell.getSheet().equals("") &&
               rc == CellTranslations.RowCol.COL &&
               map.get(cell.getCol()) != null &&
               map.get(cell.getCol()) != cellComp.getCol())
               return false;
            
            // If cell ref is off sheet (or workbook) then always check the
            // whole thing
            if(!cell.getSheet().equals("") &&
               !cell.toString().equals(cellComp.toString()))
               return false;
            
            // TODO: worth checking the unmapped row / col references???
            // Would convert this back into more of a distance measure
        }
        
        // If we get here then all cells match
        return true;
    }
}
