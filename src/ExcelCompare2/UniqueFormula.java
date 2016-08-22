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
    private final String _formula;
    private final String _formulaR1C1;
    private final CompoundRange _range;
    
    // Constructor with no parameters
    public UniqueFormula(Formula f) {
        this._formula = f.getFormula();
        this._formulaR1C1 = f.getFormulaR1C1();
        this._range = new CompoundRange(f.getCellRef());
    }
    
    public int compareTo(String formulaR1C1) {
        return _formulaR1C1.compareTo(formulaR1C1);
    }
    
    public int compareTo(Formula formula) {
        return compareTo(formula.getFormulaR1C1());
    }
    
    public int compareTo(UniqueFormula formula) {
        return compareTo(formula.getFormulaR1C1());
    }
    
    public void addCell(CellRef newCell) {
        _range.addCell(newCell);
    }
    
    public String getFormula() {
        return _formula;
    }
    
    public String getFormulaR1C1() {
        return _formulaR1C1;
    }
    
    public CompoundRange getRange() {
        return _range;
    }
}
