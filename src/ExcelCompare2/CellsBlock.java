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
public class CellsBlock {

  private final CellRef _topLeftCell;
  private final CellRef _bottomRightCell;

  // Constructor with single cell
  public CellsBlock(CellRef cr) {
    this._topLeftCell = cr;
    this._bottomRightCell = cr;
  }

  // Constructor with two cells
  public CellsBlock(CellRef topLeft, CellRef bottomRight) {
    this._topLeftCell = topLeft;
    this._bottomRightCell = bottomRight;
  }

  public int size() {
    return ((_bottomRightCell.getCol() - _topLeftCell.getCol() + 1) *
            (_bottomRightCell.getRow() - _topLeftCell.getRow() + 1));
  }

  public boolean contains(CellRef cr) {
    return (cr.getCol() >= _topLeftCell.getCol() &&
            cr.getCol() <= _bottomRightCell.getCol() &&
            cr.getRow() >= _topLeftCell.getRow() &&
            cr.getRow() <= _bottomRightCell.getRow());
  }

  public boolean isWithin(CompoundRange cr) {
    int FromRow = _topLeftCell.getRow();
    int ToRow = _bottomRightCell.getRow();
    int FromCol = _topLeftCell.getCol();
    int ToCol = _bottomRightCell.getCol();
    for (int i = FromRow; i <= ToRow; i++) {
      for (int j = FromCol; j <= ToCol; j++) {
        if (!cr.contains(new CellRef(i, j)))
          return false;
      }
    }
    return true;
  }

  public CompoundRange toCompoundRange() {
    int FromRow = _topLeftCell.getRow();
    int ToRow = _bottomRightCell.getRow();
    int FromCol = _topLeftCell.getCol();
    int ToCol = _bottomRightCell.getCol();
    CompoundRange out = new CompoundRange();

    for (int i = FromRow; i <= ToRow; i++) {
      for (int j = FromCol; j <= ToCol; j++) {
        out.addCell(new CellRef(i, j));
      }
    }

    return out;
  }

  @Override
  public String toString() {
    if (size() == 1) {
      return _topLeftCell.makeRelative().toString();
    } else {
      return _topLeftCell.makeRelative().toString() + ":" + _bottomRightCell.makeRelative().toString();
    }
  }

}
