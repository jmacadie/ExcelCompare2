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
public class CellRefExt extends CellRef {

  private final String _sheet;
  private final String _extWB;

  public CellRefExt(String col, int row, boolean colAbs, boolean rowAbs, String sheet, String extWB) {
    super(col, row, colAbs, rowAbs);
    _sheet = sheet;
    _extWB = extWB;
  }

  public CellRefExt(String col, int row, boolean colAbs, boolean rowAbs, String sheet) {
    this(col, row, colAbs, rowAbs, sheet, "");
  }

  public CellRefExt(String col, int row, boolean colAbs, boolean rowAbs) {
    this(col, row, colAbs, rowAbs, "", "");
  }

  public CellRefExt(int row, int col, String sheet, String extWB) {
    super(row, col);
    _sheet = sheet;
    _extWB = extWB;
  }

  public CellRefExt(int row, int col, String sheet) {
    this(row, col, sheet, "");
  }

  public CellRefExt(int row, int col) {
    this(row, col, "", "");
  }

  public CellRefExt(String a1Ref) {
    // TODO: interpret the external workbook and sheet name parts from the
    // start of a fuller reference?
    super(a1Ref);
    _sheet = "";
    _extWB = "";
  }

  public String getSheet() {
    return _sheet;
  }

  public String getExtWB() {
    return _extWB;
  }
}
