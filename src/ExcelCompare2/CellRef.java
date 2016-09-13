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
    private boolean _colAbs;
    private boolean _rowAbs;
    
    // Constructor with numeric rows and columns
    public CellRef(int row, int col, boolean rowAbs, boolean colAbs) {
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
    
    public CellRef getCopy() {
        return new CellRef(this._row, this._col, this._rowAbs, this._colAbs);
    }
    
    public CellRefR1C1 toR1C1(CellRef origin) {
        int row = (_rowAbs) ? _row : (_row - origin._row);
        int col = (_colAbs) ? _col : (_col - origin._col);
        return new CellRefR1C1(_rowAbs, row, _colAbs, col);
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
    
    public boolean getColAbs() {
        return _colAbs;
    }
    
    public boolean getRowAbs() {
        return _rowAbs;
    }
    
    public void setAbsolute(boolean rowAbs, boolean colAbs) {
        _rowAbs = rowAbs;
        _colAbs = colAbs;
    }
    
    public CellRef makeRelative() {
        // Clone to make sure we don't disturb the original object
        CellRef out = getCopy();
        out.setAbsolute(false, false);
        return out;
    }
    
    public CellRef makeAbsolute() {
        // Clone to make sure we don't disturb the original object
        CellRef out = getCopy();
        out.setAbsolute(true, true);
        return out;
    }
    
    public CellRef move(int rows, int cols) {
        int row = _row;
        if (!_rowAbs)
            row += rows;
        int col = _col;
        if (!_colAbs)
            col += cols;
        return new CellRef(row, col, _rowAbs, _colAbs);
    }
    
    @Override
    public String toString() {
        String out = "";
        if (_colAbs) out += "$";
        out += SpreadSheetUtils.convertToLetter(_col);
        if (_rowAbs) out += "$";
        out += String.valueOf(_row);
        return out;
    }
    
    public String getAsKey() {
        return String.valueOf(_row) + "-" + String.valueOf(_col);
    }
}
