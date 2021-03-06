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
public class CellDiff {

  private final CellDiffType _type;
  private final UniqueFormula _from;
  private final UniqueFormula _to;
  private final CompoundRange _fromPreTrans;

  public enum CellDiffType {
    NEW, CLEARED, CHANGED
  }

  CellDiff (CellDiffType type, UniqueFormula from, UniqueFormula to, CompoundRange fromPreTrans) {
    _type = type;
    _from = from;
    _to = to;
    _fromPreTrans = fromPreTrans;
  }

  public CellDiffType getType() {
    return _type;
  }

  public UniqueFormula getFrom() {
    return _from;
  }

  public UniqueFormula getTo() {
    return _to;
  }

  public CompoundRange getFromPreTrans() {
    return _fromPreTrans;
  }
}
