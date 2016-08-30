/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelCompare2;

/**
 *
 * @author james.macadie
 */
public class CellRefR1C1 {
    
    private final int _rowOffset;
    private final int _row;
    private final int _colOffset;
    private final int _col;
    private final boolean _rowAbs;
    private final boolean _colAbs;
    
    CellRefR1C1 (boolean rowAbs, int row,  boolean colAbs, int col) {
        if (rowAbs) {
            _rowOffset = 0;
            _row = row;
            _rowAbs = true;
        } else {
            _rowOffset = row;
            _row = 0;
            _rowAbs = false;
        }
        if (colAbs) {
            _colOffset = 0;
            _col = col;
            _colAbs = true;
        } else {
            _colOffset = col;
            _col = 0;
            _colAbs = false;
        }
    }
    
    CellRefR1C1 (int rowOffset, int colOffset) {
        this(false, rowOffset, false, colOffset);
    }
    
    public double distance(CellRefR1C1 comp) {
        int absMismatch = 100;
        int rowDist;
        if ((_rowAbs && !comp._rowAbs) || (!_rowAbs && comp._rowAbs)) {
            rowDist = absMismatch;
        } else {
            rowDist = (_row - comp._row);
        }
        int colDist;
        if ((_colAbs && !comp._colAbs) || (!_colAbs && comp._colAbs)) {
            colDist = absMismatch;
        } else {
            colDist = (_col - comp._col);
        }
        return Math.sqrt(rowDist * rowDist + colDist * colDist);
    }
    
    @Override
    public String toString() {
        String out = "";
        if (_rowAbs) {
            out += "R" + _row;
        } else {
            if (_rowOffset == 0) {
                out += "R";
            } else {
                out += "R[" + (_rowOffset) + "]";
            }
        }
        if (_colAbs) {
            out += "C" + _col;
        } else {
            if (_colOffset == 0) {
                out += "C";
            } else {
                out += "C[" + (_colOffset) + "]";
            }
        }
        return out;
    }
    
}
