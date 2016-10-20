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
    } else if (_rowAbs) {
      rowDist = (_row - comp._row);
    } else {
      rowDist = (_rowOffset - comp._rowOffset);
    }
    int colDist;
    if ((_colAbs && !comp._colAbs) || (!_colAbs && comp._colAbs)) {
      colDist = absMismatch;
    } else if (_colAbs) {
      colDist = (_col - comp._col);
    } else {
      colDist = (_colOffset - comp._colOffset);
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
