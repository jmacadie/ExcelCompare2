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
public class SheetDiff {
    
    private final CellTranslations _translations;
    private final List<CellDiff> _differences;
    
    SheetDiff () {
        _translations = new CellTranslations();
        _differences = new LinkedList<> ();
    }
    
}
