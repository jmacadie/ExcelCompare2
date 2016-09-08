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
public class UniqueFormula {
    private final Formula _formula;
    private final CompoundRange _range;
    
    public UniqueFormula(Formula f) {
        this._formula = f;
        this._range = new CompoundRange(f.getCellRef());
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
