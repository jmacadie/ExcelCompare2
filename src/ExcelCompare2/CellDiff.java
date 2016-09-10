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
    
    CellDiff (CellDiffType type, UniqueFormula from, UniqueFormula to) {
        _type = type;
        _from = from;
        _to = to;
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
}
