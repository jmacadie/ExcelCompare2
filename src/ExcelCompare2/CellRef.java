/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelCompare2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author james.macadie
 */
public class CellRef {
    // TODO: deal with whole columns / rows
    private final int _col;
    private final int _row;
    private final boolean _colAbs;
    private final boolean _rowAbs;
    
    // Constructor with numeric rows and columns
    public CellRef(int row, int col, boolean colAbs, boolean rowAbs) {
        this._col = col;
        this._row = row;
        this._colAbs = colAbs;
        this._rowAbs = rowAbs;
    }
    
    // Constructor with numeric rows and alpha columns (e.g. AA34)
    public CellRef(String col, int row, boolean colAbs, boolean rowAbs) {
        this._col = SpreadSheetUtils.convertFromLetter(col);
        this._row = row;
        this._colAbs = colAbs;
        this._rowAbs = rowAbs;
    }
    
    // Constructor with numeric rows and columns, default non-absolute
    public CellRef(int row, int col) {
        this._col = col;
        this._row = row;
        this._colAbs = false;
        this._rowAbs = false;
    }
    
    // Constructor with single Excel style A1 notation input
    public CellRef(String a1Ref) {
        String cellPat = "(\\$)?([A-Z]{1,3})(\\$)?(\\d+)";
        Matcher matcher = Pattern.compile(cellPat).matcher(a1Ref);
        
        if (!matcher.matches())
            throw new IllegalArgumentException("Illegal cell specifier " + a1Ref);
        
        boolean tmpColAbs;
        boolean tmpRowAbs;
        int tmpRow;
        int tmpCol;
        
        tmpCol = SpreadSheetUtils.convertFromLetter(matcher.group(2));
        tmpRow = Integer.parseInt(matcher.group(4));
        tmpColAbs = (matcher.group(1) != null);
        tmpRowAbs = (matcher.group(3) != null);

        this._col = tmpCol;
        this._row = tmpRow;
        this._colAbs = tmpColAbs;
        this._rowAbs = tmpRowAbs;
    }
    
    public String toR1C1(CellRef orig) {
        String out;
        if (_rowAbs) {
            out = "R" + _row;
        } else {
            if (orig._row == _row) {
                out = "R";
            } else {
                out = "R[" + (_row - orig._row) + "]";
            }
        }
        if (_colAbs) {
            out += "C" + _col;
        } else {
            if (orig._col == _col) {
                out += "C";
            } else {
                out += "C[" + (_col - orig._col) + "]";
            }
        }
        return out;
    }
    
    // Is one cell the same as another
    public boolean equals(CellRef comp) {
        return (comp._row == _row) && (comp._col == _col);
    }
    
    // Is one cell the same as another, considering absolute cell refs too
    public boolean equalsStrict(CellRef comp) {
        return (comp._row == _row) && 
                (comp._col == _col) &&
                (comp._rowAbs == _rowAbs) &&
                (comp._colAbs == _colAbs);
    }
    
    public int getCol() {
        return _col;
    }
    
    public int getRow() {
        return _row;
    }
    
    public String getStringRef() {
        return String.valueOf(_row) + "-" + String.valueOf(_col);
    }
}
