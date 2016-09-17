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
    private final List<CellRef> _references;
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
    
    private static String getCellRefRegex() {
        // Individual cell ref patern
        String cellPatUnit = "(\\$)?([A-Z]{1,3})(\\$)?(\\d+)";
        // TODO: Full columns and rows
        String collPatUnit = "(\\$)?([A-Z]{1,3}):(\\$)?([A-Z]{1,3})";
        String rowPatUnit = "(\\$)?(\\d+):(\\$)?(\\d+)";
        // Build delimiters
        // Important as e.g. "A1A1" is not a cell reference
        String whitespace = "\\s";
        String brackets = "\\(|\\)|\\[|\\]|\\{|\\}";
        String operators = "\\*|/|\\+|\\-|\\^|\\<|\\>|\\=|&";
        String rangeConcat = ":";
        String extRef = "\\!";
        String argSep = ",";
        String delimiters = "(?:" + // Start group & make sure non-capturing
                            whitespace + "|" +
                            brackets + "|" +
                            operators + "|" +
                            rangeConcat + "|" +
                            extRef + "|" +
                            argSep + ")";
        // Create the full regex for finding cell ref parts in any formulae
        String cellPatFull = "^" + cellPatUnit + "$|" + // Only find cell ref
                             delimiters + cellPatUnit + "$|" + // find cell ref at end
                             "^" + cellPatUnit + delimiters + "|" + // find cell ref at start
                             delimiters + cellPatUnit + delimiters; // find cell ref in middle
        return cellPatFull;
    }
    
    // Convert formula to R1C1
    private String[] convertToR1C1() {
        
        // TODO: break up into smaller parts?
        // TODO: don't find cell refs in sheet names or workbook names
        
        String editFormula;
        editFormula = this._formula;
        
        String shellFormula;
        shellFormula = this._formula;
        
        Pattern cellPat = Pattern.compile(getCellRefRegex());
        Matcher matcher = cellPat.matcher(_formula);
        
        int posn = 0;
        String col;
        int row;
        boolean colAbs;
        boolean rowAbs;
        CellRef formulaCellRef;
        String cleanCellRef;
        
        while (matcher.find(posn)) {
            // Look for column absolute
            colAbs = false;
            for (int i = 1; i < 17; i = i + 4) {
                if (matcher.group(i) != null) {
                    colAbs = true;
                    break;
                }
            }
            // Look for row absolute
            rowAbs = false;
            for (int i = 3; i < 17; i = i + 4) {
                if (matcher.group(i) != null) {
                    rowAbs = true;
                    break;
                }
            }
            // Look for column letter
            col = "A"; // Default to column A, not sure if this is needed
            for (int i = 2; i < 17; i = i + 4) {
                if (matcher.group(i) != null) {
                    col = matcher.group(i);
                    break;
                }
            }
            // Look for row
            row = 1 ; // Default to row 1, not sure if this is needed
            for (int i = 4; i < 17; i = i + 4) {
                if (matcher.group(i) != null) {
                    row = Integer.parseInt(matcher.group(i));
                    break;
                }
            }
            
            // Build cell reference object and get it's R1C1 representation
            formulaCellRef = new CellRef(col, row, colAbs, rowAbs);
            _references.add(formulaCellRef);
            _referencesR1C1.add(formulaCellRef.toR1C1(_cellRef));
            
            // Clean cell reference by removing starting and trailing cell
            // refernces, if they exist.
            // Also escape any $
            cleanCellRef = matcher.group().replaceAll("^[^A-Z]|[^0-9]$", "");
            cleanCellRef = cleanCellRef.replaceAll("\\$", "\\\\\\$");
            
            // Finally replace the A1 style reference with its R1C1 counterpart
            editFormula = editFormula.replaceAll(
                    cleanCellRef, formulaCellRef.toR1C1(_cellRef).toString());
            
            // ... and just strip the cell ref for the shell
            shellFormula = shellFormula.replaceAll(
                    cleanCellRef, "");
            
            // TODO: can we short-curcuit the fact we might have replaced the
            // same cell reference multiple times?
            
            // Move position index back 1 from the current end match
            // Need this as the trailing delimiter can be the starting
            // delimiter for the next cell ref
            posn = matcher.end() - 1; 
        }
        
        return new String[] {editFormula, shellFormula};
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
        String find;
        String replace;
        
        // Loop through the references moving them and replacing them in formula
        for (CellRef orig : _references) {
            trans = orig.move(rows, cols);
            // Escape the dollars in the cell ref
            find = orig.toString().replaceAll("\\$", "\\\\\\$");
            replace = trans.toString().replaceAll("\\$", "\\\\\\$");
            newFormula = newFormula.replaceAll(find, replace);
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
        
        // If the rest of the formula doesn't match return -1
        // TODO: could consider some residual Levenstien distance measure to
        // capture formuale that have been moved and (moderately!) edited
        if (!comp._shellFormula.equals(_shellFormula))
            return false;
        
        ListIterator<CellRef> iter = _references.listIterator();
        ListIterator<CellRef> iterComp = comp._references.listIterator();
        CellRef cell;
        CellRef cellComp;
        
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
            
            // If mapped and the translation doesn't match then no match
            // Rows
            if(rc == CellTranslations.RowCol.ROW &&
               map.get(cell.getRow()) != null &&
               map.get(cell.getRow()) != cellComp.getRow())
               return false;

            // If mapped and the translation doesn't match then no match
            // Columns
            if(rc == CellTranslations.RowCol.COL &&
               map.get(cell.getCol()) != null &&
               map.get(cell.getCol()) != cellComp.getCol())
               return false;
            
            // TODO: worth checking the unmapped row / col references???
            // Would convert this back into more of a distance measure
        }
        
        // If we get here then all cells match
        return true;
    }
}
