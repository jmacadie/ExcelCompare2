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
public class CellDiff {
    
    private final CellDiffType _type;
    private final UniqueFormula _from;
    private final UniqueFormula _to;
    
    public enum CellDiffType {
        NEW, CLEARED, CHANGED
    }
    
    CellDiff () {
//        _type = CellDiffType.NEW;
//        _from = null;
//        _to = new UniqueFormula(new Formula("=B1",new CellRef("A1")));
//        
//        _type = CellDiffType.CLEARED;
//        _from = new UniqueFormula(new Formula("=B1",new CellRef("A1")));
//        _to = null;
        
        _type = CellDiffType.CHANGED;
        _from = new UniqueFormula(new Formula("=B1",new CellRef("A1")));
        _to = new UniqueFormula(new Formula("=B1",new CellRef("A1")));
    }
}
