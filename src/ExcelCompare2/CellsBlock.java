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
public class CellsBlock {
    
    private final CellRef _topLeftCell;
    private final CellRef _bottomRightCell;
    
    // Constructor with single cell
    public CellsBlock(CellRef cr) {
        this._topLeftCell = cr;
        this._bottomRightCell = cr;
    }
    
    // Constructor with two cells
    public CellsBlock(CellRef topLeft, CellRef bottomRight) {
        this._topLeftCell = topLeft;
        this._bottomRightCell = bottomRight;
    }
    
    public int size() {
        return ((_bottomRightCell.getCol() - _topLeftCell.getCol() + 1) *
                (_bottomRightCell.getRow() - _topLeftCell.getRow() + 1));
    }
    
    public boolean contains(CellRef cr) {
        return (cr.getCol() >= _topLeftCell.getCol() &&
                cr.getCol() <= _bottomRightCell.getCol() &&
                cr.getRow() >= _topLeftCell.getRow() &&
                cr.getRow() <= _bottomRightCell.getRow());
    }
    
    public boolean isWithin(CompoundRange cr) {
        int FromRow = _topLeftCell.getRow();
        int ToRow = _bottomRightCell.getRow();
        int FromCol = _topLeftCell.getCol();
        int ToCol = _bottomRightCell.getCol();
        for (int i = FromRow; i <= ToRow; i++) {
            for (int j = FromCol; j <= ToCol; j++) {
                if (!cr.contains(new CellRef(i, j)))
                    return false;
            }
        }
        return true;
    }
    
    public CompoundRange toCompoundRange() {
        int FromRow = _topLeftCell.getRow();
        int ToRow = _bottomRightCell.getRow();
        int FromCol = _topLeftCell.getCol();
        int ToCol = _bottomRightCell.getCol();
        CompoundRange out = new CompoundRange();
        
        for (int i = FromRow; i <= ToRow; i++) {
            for (int j = FromCol; j <= ToCol; j++) {
                out.addCell(new CellRef(i, j));
            }
        }
        
        return out;
    }
    
    @Override
    public String toString() {
        if (size() == 1) {
            return _topLeftCell.makeRelative().toString();
        } else {
            return _topLeftCell.makeRelative().toString() + ":" + _bottomRightCell.makeRelative().toString();
        }
    }
    
}
