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
public class UniqueFormula {
    private final Formula _formula;
    private final CompoundRange _range;
    
    public UniqueFormula(Formula f) {
        this._formula = f;
        this._range = new CompoundRange(f.getCellRef());
    }
    
    public UniqueFormula(Formula f, CompoundRange cr) {
        this._formula = f;
        this._range = cr;
        // Make sure we add the cell in f in case it is disjoint from cr
        this._range.addCell(f.getCellRef());
    }
    
    public int compareTo(String formulaR1C1) {
        return _formula.getR1C1().compareTo(formulaR1C1);
    }
    
    public int compareTo(Formula formula) {
        return compareTo(formula.getR1C1());
    }
    
    public int compareTo(UniqueFormula formula) {
        return compareTo(formula.getFormula());
    }
    
    public void addCell(CellRef newCell) {
        _range.addCell(newCell);
    }
    
    public Formula getFormula() {
        return _formula;
    }
    
    public CompoundRange getRange() {
        return _range;
    }
}
