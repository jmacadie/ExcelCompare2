/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelCompare2;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author james.macadie
 */
public class CompoundRange implements Iterator<CellRef> {
    
    // Best way to store?
    // Might be better to store as blocks (e.g. A1:H17 rather than A1, A2, A3, ..., A17, B1, etc.)
    private final List<CellRef> _compoundRange;
    private int _idx;
    private int _idxMax;
    private int _idxSave;
    
    // Constructor with no parameters
    public CompoundRange() {
        this._compoundRange = new LinkedList<>();
        this._idx = 0;
        this._idxMax = -1;
        this._idxSave = 0;
    }
    
    // Constructor with single cell
    public CompoundRange(CellRef cr) {
        this._compoundRange = new LinkedList<>();
        _compoundRange.add(cr);
        this._idx = 0;
        this._idxMax = 0;
        this._idxSave = 0;
    }
    
    // Constructor with another coupound range
    // i.e. clone the current compound range
    public CompoundRange(CompoundRange cr) {
        this._compoundRange = new LinkedList<>();
        cr.savePosition();
        cr.moveFirst();
        while (cr.hasNext()) {
            _compoundRange.add(cr.next());
        }
        cr.moveSaved();
        this._idx = 0;
        this._idxMax = cr.size() - 2;
        this._idxSave = 0;
    }
    
    public void addCell(CellRef cr) {
        // TODO: store sorted?
        // Will affect the compartive methods below
        _compoundRange.add(cr);
        _idxMax++;
    }
    
    public void removeCell(CellRef cr) {
        if (_compoundRange.remove(cr))
            _idxMax--;
        // TODO: Decrement _idx too.
        // Depends on current index being "above" removed cell
    }
    
    public int size() {
        return (_idxMax + 2);
    }
    
    public boolean isEmpty() {
        return (_idxMax == -1);
    }
    
    public void savePosition() {
        _idxSave = _idx;
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
    
    public void moveFirst() {
        _idx = 0;
    }
    
    public void moveSaved() {
        _idx = _idxSave;
    }
    
    @Override
    public boolean hasNext() {
        return (_idx <= _idxMax);
    }
    
    // TODO: throw out of range exception?
    @Override
    public CellRef next() {
        _idx++;
        return _compoundRange.get(_idx - 1);
    }
    
    @Override
    public String toString() {
        return "This is a compound range";
    }
    
}
