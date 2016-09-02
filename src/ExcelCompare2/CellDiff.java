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
    private final Formula _from;
    private final Formula _to;
    
    public enum CellDiffType {
        NEW, CLEARED, CHANGED
    }
    
    CellDiff () {
        _type = CellDiffType.NEW;
        _from = new Formula("=B1",new CellRef("A1"));
        _to = new Formula("=B1",new CellRef("A1"));
    }
}
