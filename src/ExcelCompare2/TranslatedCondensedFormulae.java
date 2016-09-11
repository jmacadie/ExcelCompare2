/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelCompare2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 *
 * @author james.macadie
 */
public class TranslatedCondensedFormulae {
    
    // Lookup map to covert a translated reference into it's pre-translation state
    private final Map<String, CellRef> _beforeMap;
    // Store the translated sheet as a Condensed Formulae object
    private final CondensedFormulae _translated;
    // Need to store the cells that no longer exist as a consequence of the
    // translations being applied
    private final CondensedFormulae _removed;
    
    TranslatedCondensedFormulae (CondensedFormulae before, CellTranslations trans, CellTranslations.Direction dir) {
        
        this._beforeMap = new HashMap<>();
        
        List<Formula> ft = new ArrayList<> ();
        List<Formula> fr = new ArrayList<> ();
        
        ListIterator<AnalysedFormula> iter;
        AnalysedFormula af;
        CompoundRange r;
        Formula origFormula;
        Formula transFormula;
        CellRef origCell;
        CellRef transCell;
        
        // If no translations then simpler job to do
        if (trans.size() == 0) {
            // Translated is just before
            this._translated = before;
            // Loop through each analysed formula in the before object
            // and build the map pointing each cell at itself
            iter = before.listIterator();
            while (iter.hasNext()) {
                af = iter.next();
                r = af.getRange();
                // Loop through every cell in the analysed formula
                r.moveFirst();
                while (r.hasNext()) {
                    origCell = r.next();
                    _beforeMap.put(origCell.getAsKey(), origCell);
                }
            }
        } else {
            // Only map FROM_TO currently
            // TODO: maybe do in reverse as well? Not sure needed
            if (dir == CellTranslations.Direction.FROM_TO) {
                // Loop through each analysed formula in the before object
                iter = before.listIterator();
                while (iter.hasNext()) {
                    af = iter.next();
                    r = af.getRange();
                    origFormula = af.getFormula();
                    // Loop through every cell in the analysed formula
                    r.moveFirst();
                    while (r.hasNext()) {
                        origCell = r.next();
                        // Copy the Formula to the current cell
                        transFormula = origFormula.getCopiedTo(origCell);
                        // Figure out where the cell gets translated to
                        transCell = applyTranslations(origCell, trans);
                        if (transCell == null) {
                            // Cell gets removed by translations
                            // add to our list of removed formulae
                            fr.add(transFormula);
                        } else {
                            // Add translation to our map
                            _beforeMap.put(transCell.getAsKey(), origCell);
                            // Move the formula
                            // TODO: Not good enough - the A1 representation of
                            // the formula can change too
                            transFormula = transFormula.getTranslatedTo(transCell, trans);
                            // and add to our list of translated formulae
                            ft.add(transFormula);
                        }
                    }
                }
            }
            // Construct CondensedFormulae of the translated cells
            this._translated = new CondensedFormulae(ft);
        }
        
        // Construct CondensedFormulae of the removed cells
        this._removed = new CondensedFormulae(fr);
    }
    
    public CompoundRange getPreTransRange(CompoundRange curr) {
        CompoundRange out = new CompoundRange();
        curr.savePosition();
        curr.moveFirst();
        while (curr.hasNext()) {
            out.addCell(_beforeMap.get(curr.next().getAsKey()));
        }
        curr.moveSaved();
        return out;
    }
    
    public CondensedFormulae getTranslated() {
        return _translated;
    }
    
    public CondensedFormulae getRemoved() {
        return _removed;
    }
    
    public static CellRef applyTranslations(CellRef source, CellTranslations trans) {
        
        // Only goes FROM -> TO
        
        Integer i = trans.translateRow(CellTranslations.Direction.FROM_TO, source.getRow());
        Integer j = trans.translateColumn(CellTranslations.Direction.FROM_TO, source.getCol());
        
        // If deleted return null
        if (i == null || j == null)
            return null;
        
        return new CellRef(i, j, source.getRowAbs(), source.getColAbs());
    }
}
