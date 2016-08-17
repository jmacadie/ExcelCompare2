/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelCompare2;

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
    
    public Formula(String formula, CellRef cellRef) {
        this._formula = formula;
        this._cellRef = cellRef;
        this._formulaR1C1 = convertToR1C1();
    }
    
    public Formula(Cell cell) {
        this._formula = getValue(cell);
        this._cellRef = new CellRef(cell.getColumnIndex() + 1,
                                    cell.getRowIndex() + 1);
        this._formulaR1C1 = convertToR1C1();
    }
    
    // Convert formula to R1C1
    private String convertToR1C1() {
        
        // TODO: break up into smaller parts?
        
        String editFormula;
        editFormula = this._formula;
        
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
        Pattern cellPat = Pattern.compile(cellPatFull);
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
            
            // Clean cell reference by removing starting and trailing cell
            // refernces, if they exist.
            // Also escape any $
            cleanCellRef = matcher.group().replaceAll("^[^A-Z]|[^0-9]$", "");
            cleanCellRef = cleanCellRef.replaceAll("\\$", "\\\\\\$");
            
            // Finally replace the A1 style reference with its R1C1 counterpart
            editFormula = editFormula.replaceAll(
                    cleanCellRef, formulaCellRef.toR1C1(_cellRef));
            
            // TODO: can we short-curcuit the fact we might have replaced the
            // same cell reference multiple times?
            
            // Move position index back 1 from the current end match
            // Need this as the trailing delimiter can be the starting
            // delimiter for the next cell ref
            posn = matcher.end() - 1; 
        }
        
        return editFormula;
    }
    
    public String getFormulaR1C1() {
        return _formulaR1C1;
    }
    
    public String getFormula() {
        return _formula;
    }
    
    public CellRef getCellRef() {
        return _cellRef;
    }

    private String getValue(Cell cell) {
        String formula;
        int cellType = cell.getCellType();
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
    
}
