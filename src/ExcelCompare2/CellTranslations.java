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
public class CellTranslations {
    
    private final List<CellTransInsertDelete> _rowInserts;
    private final List<CellTransInsertDelete> _rowDeletes;
    private final List<CellTransMove> _rowMoves;
    private final List<CellTransInsertDelete> _columnInserts;
    private final List<CellTransInsertDelete> _columnDeletes;
    private final List<CellTransMove> _columnMoves;
    
    public CellTranslations () {
        _rowInserts = new LinkedList<> ();
        _rowDeletes = new LinkedList<> ();
        _rowMoves = new LinkedList<> ();
        _columnInserts = new LinkedList<> ();
        _columnDeletes = new LinkedList<> ();
        _columnMoves = new LinkedList<> ();
    }
    
}
