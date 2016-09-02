/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelCompare2;

import java.util.List;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.Cell;

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
    
    public Formula(String formula, CellRef cellRef) {
        this._formula = formula;
        this._cellRef = cellRef;
        this._references = new LinkedList<>();
        this._referencesR1C1 = new LinkedList<>();
        
        String[] tmp = convertToR1C1();
        this._formulaR1C1 = tmp[0];
        this._shellFormula = tmp[1];
    }
    
    public Formula(Cell cell) {
        this._formula = getValue(cell);
        this._cellRef = new CellRef(cell.getRowIndex() + 1,
                                    cell.getColumnIndex() + 1);
        this._references = new LinkedList<>();
        this._referencesR1C1 = new LinkedList<>();
        
        String[] tmp = convertToR1C1();
        this._formulaR1C1 = tmp[0];
        this._shellFormula = tmp[1];
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
    
    public CellRef getCellRef() {
        return _cellRef;
    }

    private String getValue(Cell cell) {
        String formula;
        int cellType = cell.getCellType();
        // TODO: dates get stored as numbers
        // not sure if this should be changed as dates are techincally just
        // formatting but the end user might get confused when all thier dates
        // get reported back as numbers
        // e.g. 1st April 2017 currently gets reported as 42826.0
        switch (cellType) {
            case Cell.CELL_TYPE_FORMULA:
                formula = "=" + cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                formula = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                formula = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                formula = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                formula = cell.getStringCellValue();
                break;
        }
        return formula;
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
    
}
