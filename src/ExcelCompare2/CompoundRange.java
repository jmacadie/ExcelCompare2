/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelCompare2;

import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author james.macadie
 */
public class CompoundRange {
    
    // Best way to store?
    // Might be better to store as blocks (e.g. A1:H17 rather than A1, A2, A3, ..., A17, B1, etc.)
    private final List<CellRef> _compoundRange;
    
    // Constructor with no parameters
    public CompoundRange() {
        this._compoundRange = new LinkedList<>();
    }
    
    // Constructor with single cell
    public CompoundRange(CellRef cr) {
        this._compoundRange = new LinkedList<>();
        _compoundRange.add(cr);
    }
    
    public void addCell(CellRef cr) {
        _compoundRange.add(cr);
    }
    
    public CompoundRange intersect(CompoundRange cr) {
        return new CompoundRange();
    }
    
    public CompoundRange isMissingFrom(CompoundRange cr) {
        return new CompoundRange();
    }
    
    public CompoundRange isAdditionalTo(CompoundRange cr) {
        return new CompoundRange();
    }
    
    @Override
    public String toString() {
        return "This is a compound range";
    }
    
}
