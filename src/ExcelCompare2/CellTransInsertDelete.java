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
public class CellTransInsertDelete {
    
    private final CellTranslationType _type;
    private final int _at;
    private final int _numberAffected;
    
    public enum CellTranslationType {
        INSERTED, DELETED
    }
    
    public CellTransInsertDelete(CellTranslationType type, int at, int numberAffected) {
        this._type = type;
        this._at = at;
        this._numberAffected = numberAffected;
    }
    
}
