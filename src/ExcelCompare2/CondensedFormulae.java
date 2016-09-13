/* MIT License
 *
 * Copyright (c) 2016 James MacAdie
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ExcelCompare2;

import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author james.macadie
 */
public class CondensedFormulae {
    
    // Ordered (by FormulaR1C1) list of unique formulae on a given sheet 
    private final List<AnalysedFormula> _uniqueFormulae;
    // Fast lookup map to covert a cell ref to it's index in the unique formulae
    // list
    private final Map<String, Integer> _refMap;
    // Track size of sheet
    private int _maxCols;
    private int _maxRows;
    // Memoised store of subsetted rows and columns
    private final Map<Integer, CondensedFormulae> _rows;
    private final Map<Integer, CondensedFormulae> _columns;
    
    // Constructor with no parameters
    public CondensedFormulae() {
        this._uniqueFormulae = new LinkedList<>();
        this._refMap = new HashMap<>();
        this._maxCols = 0;
        this._maxRows = 0;
        this._rows = new HashMap<>();
        this._columns = new HashMap<>();
    }
    
    // Build condensed formulae map from an array of Formulae
    public CondensedFormulae(List<Formula> formulae) {
        this._uniqueFormulae = new LinkedList<>();
        this._refMap = new HashMap<>();
        this._maxCols = 0;
        this._maxRows = 0;
        this._rows = new HashMap<>();
        this._columns = new HashMap<>();
        
        // Loop through all rows
        ListIterator<Formula> iter = formulae.listIterator();
        while (iter.hasNext()) {
            // Add cell to condensed formulae map
            add(iter.next());
        }
        
        // and then finally build the ref map
        buildRefMap();
    }
    
    private void add(Formula formula) {
    // Adds a new formula to the condensed formula map
    // Iterates through the current list of unique formulae and finds if:
    //   1) the formula is equal
    //      - add the cell ref to the existing unique formula reference
    //   2) the formula is "bigger" (String.compareTo())
    //      - found the correct insert place in the ordered list and a new
    //        unique formula so add it
    //   3) the formula is "smaller" (String.compareTo())
    //      - not yet proved if formula already found so keep iterating
    // This ensures the list of unquie formulae is mainted as an ordered list,
    // which make subsequent searching more efficient
        ListIterator<AnalysedFormula> iter = _uniqueFormulae.listIterator();
        boolean found = false;
        AnalysedFormula curr;
        int res;
        while (iter.hasNext()) {
            curr = iter.next();
            res = curr.compareTo(formula);
            if (res == 0) {
                // Same formula so add cell ref to pre-existing unique formula
                curr.addCell(formula.getCellRef());
                iter.set(curr);
                // Quit iteration loop as found the correct action
                found = true;
                break;
            } else if (res > 0) {
                // Not same formula and now formula is "bigger" than the current
                // ordered list of formulae so have found the right place to
                // insert
                // Move cursor back one space, so insert happens in right place
                if (iter.hasPrevious())
                        iter.previous();
                iter.add(new AnalysedFormula(formula));
                // Quit iteration loop as found the correct action
                found = true;
                break;
            }
        }
        // If not found in our loop then just add the formula as a new unique
        // formula at the end of the list
        if (!found) {
            iter.add(new AnalysedFormula(formula));
        }
        
        // Increment the row and col counters
        CellRef cell = formula.getCellRef();
        int row = cell.getRow();
        int col = cell.getCol();
        if (row > _maxRows)
            _maxRows = row;
        
        if (col > _maxCols)
            _maxCols = col;
    }
    
    private void buildRefMap() {
    // Loop through the unique formula list and create a lookup map of cell ref
    // to position index in the formulae list
        ListIterator<AnalysedFormula> iter = _uniqueFormulae.listIterator();
        CompoundRange range;
        CellRef cell;
        String key;
        while (iter.hasNext()) {
            range = iter.next().getRange();
            range.moveFirst();
            while (range.hasNext()) {
                cell = range.next();
                _refMap.put(cell.getAsKey(), iter.previousIndex());
            }
        }
    }
    
    public int getMaxRows() {
        return _maxRows;
    }
    
    public int getMaxCols() {
        return _maxCols;
    }
    
    public CompoundRange findFormula(AnalysedFormula f) {
        ListIterator<AnalysedFormula> iter = _uniqueFormulae.listIterator();
        AnalysedFormula curr;
        int res;
        while (iter.hasNext()) {
            curr = iter.next();
            res = curr.compareTo(f);
            if (res == 0) {
                return curr.getRange();
            } else if (res > 0) {
                // Not same formula and now formula is "bigger" than the current
                // ordered list of formulae so have proved it's not here
                break;
            }
        }
        // If we've got here then it's not worked
        // Use null to indicate failure
        return null;
    }
    
    public boolean findCell(CellRef find) {
        
        return _refMap.containsKey(find.getAsKey());
        
    }
    
    public void setAnalysed(CellRef analysed) {
        // Find if cell ref exists in current map
        if (_refMap.containsKey(analysed.getAsKey())) {
            int i = _refMap.get(analysed.getAsKey());
            AnalysedFormula af = _uniqueFormulae.get(i);
            af.setAnalysed(analysed);
        }
    }
    
    public void setAnalysed(CompoundRange analysed) {
        // Save index position
        analysed.savePosition();
        // Then loop the range setting the analysed state for each cell
        analysed.moveFirst();
        while (analysed.hasNext()) {
            setAnalysed(analysed.next());
        }
        analysed.moveSaved();
    }
    
    public boolean isAnalysed(CellRef cell) {
        // Find if cell ref exists in current map
        if (_refMap.containsKey(cell.getAsKey())) {
            int i = _refMap.get(cell.getAsKey());
            AnalysedFormula af = _uniqueFormulae.get(i);
            return af.isAnalysed(cell);
        }
        return false;
    }
    
    public ListIterator<AnalysedFormula> listIterator() {
        return _uniqueFormulae.listIterator();
    }
    
    public UniqueFormula getForumla(CellRef cell) {
        // Find if cell ref exists in current map
        if (_refMap.containsKey(cell.getAsKey())) {
            int i = _refMap.get(cell.getAsKey());
            return _uniqueFormulae.get(i).getUniqueFormula();
        }
        return null;
    }
    
    public CondensedFormulae getRow(int row) {
        if (_rows.containsKey(row)) {
            // If row already stroed in memoised cache then just return
            return _rows.get(row);
        } else {
            List<Formula> f = new LinkedList<> ();

            // Loop along the row finding formulae
            // TODO: more effieicnt to do bulk finds of same formula?
            CellRef cell;
            UniqueFormula uf;
            for (int i = 1; i <= _maxCols; i++) {
                cell = new CellRef(row, i);
                uf = getForumla(cell);
                if (uf != null)
                    f.add(uf.getFormula().getCopiedTo(cell));
            }

            // Create a new compound range out of the list of found formulae
            CondensedFormulae cf = new CondensedFormulae(f);
            // Store the output in the memoised cache
            _rows.put(row, cf);
            // and return
            return cf;
        }
    }
    
    public CondensedFormulae getColumn(int column) {
        if (_columns.containsKey(column)) {
            // If row already stroed in memoised cache then just return
            return _columns.get(column);
        } else {
            List<Formula> f = new LinkedList<> ();

            // Loop along the column finding formulae
            // TODO: more effieicnt to do bulk finds of same formula?
            CellRef cell;
            UniqueFormula uf;
            for (int i = 1; i <= _maxRows; i++) {
                cell = new CellRef(i, column);
                uf = getForumla(cell);
                if (uf != null)
                    f.add(uf.getFormula().getCopiedTo(cell));
            }

            // Create a new compound range out of the list of found formulae
            CondensedFormulae cf = new CondensedFormulae(f);
            // Store the output in the memoised cache
            _columns.put(column, cf);
            // and return
            return cf;
        }
    }
    
}