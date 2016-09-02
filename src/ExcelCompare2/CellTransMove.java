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
public class CellTransMove {
    
    private final int _from;
    private final int _to;
    private final int _numberAffected;
    
    public CellTransMove(int from, int to, int numberAffected) {
        this._from = from;
        this._to = to;
        this._numberAffected = numberAffected;
    }
    
}
