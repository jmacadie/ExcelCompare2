/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
                        curr = iter.previous();
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
    
    private boolean findCell(CellRef find) {
        
        return _refMap.containsKey(find.getAsKey());
        
    }
    
    private void setAnalysed(CellRef analysed) {
        // Find if cell ref exists in current map
        if (_refMap.containsKey(analysed.getAsKey())) {
            int i = _refMap.get(analysed.getAsKey());
            AnalysedFormula af = _uniqueFormulae.get(i);
            af.setAnalysed(analysed);
        }
    }
    
    private void setAnalysed(CompoundRange analysed) {
        // Save index position
        analysed.savePosition();
        // Then loop the range setting the analysed state for each cell
        analysed.moveFirst();
        while (analysed.hasNext()) {
            setAnalysed(analysed.next());
        }
        analysed.moveSaved();
    }
    
    public ListIterator<AnalysedFormula> listIterator() {
        return _uniqueFormulae.listIterator();
    }
    
    public AnalysedFormula getForumla(CellRef cell) {
        // Find if cell ref exists in current map
        if (_refMap.containsKey(cell.getAsKey())) {
            int i = _refMap.get(cell.getAsKey());
            return _uniqueFormulae.get(i);
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
            AnalysedFormula af;
            for (int i = 1; i <= _maxCols; i++) {
                cell = new CellRef(row, i);
                af = getForumla(cell);
                if (af != null)
                    f.add(af.getFormula().getCopiedTo(cell));
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
            AnalysedFormula af;
            for (int i = 1; i <= _maxRows; i++) {
                cell = new CellRef(i, column);
                af = getForumla(cell);
                if (af != null)
                    f.add(af.getFormula().getCopiedTo(cell));
            }

            // Create a new compound range out of the list of found formulae
            CondensedFormulae cf = new CondensedFormulae(f);
            // Store the output in the memoised cache
            _columns.put(column, cf);
            // and return
            return cf;
        }
    }
    
    public void diff(CondensedFormulae from) {
        // TODO: return a diff class as reporting on differences should be
        // de-coupled from the act of identifying them
        
        // Look for transformations
        // TODO: implement!
        //SHEET TRANSFORMATIONS
        //Sheets re-ordered
        //Sheet renamed
        //Sheet copied
        //Sheet deleted
        //
        //CELL TRANSFORMATIONS
        //Rows added
        //Rows deleted
        //Rows cut & pasted (i.e. moved)
        //Rows copied & inserted (i.e. duplicated)
        //Columns added
        //Columns deleted
        //Columns cut & pasted (i.e. moved)
        //Columns copied & inserted (i.e. duplicated)
        //Cells deleted (shift left)
        //Cells deleted (shift up)
        //Cells inserted (shift left)
        //Cells inserted (shift down)
        //Possibly need to run this loop 2 (or more) times per sheet to catch instances where, for example, columns have been inserted and then rows
        
        ListIterator<AnalysedFormula> iter = _uniqueFormulae.listIterator();
        
        AnalysedFormula uf;
        CompoundRange fromRange;
        CompoundRange toRange;
        CompoundRange range;
        CompoundRange changedRange;
        CompoundRange newRange;
        CellRef cell;
        
        //-- LOOP THROUGH TO UNIQUE FORMULAE --
        while (iter.hasNext()) {
            
            uf = iter.next();
            fromRange = from.findFormula(uf);
            toRange = uf.getRange();
            
            //1. Formulae does not exist in From
            if (fromRange == null) {
                
                toRange.moveFirst();
                newRange = new CompoundRange();
                
                // Loop through range
                while (toRange.hasNext()) {
                    
                    cell = toRange.next();
                    
                    if (from.findCell(cell)) {
                        //  1.1 Range present in From in some other formulae         => REPORT FORMULAE CHANGED
                        changedRange = from.getForumla(cell).getRange();
                        changedRange = changedRange.intersect(toRange);
                        
                        System.out.println("FORMULA CHANGED: " + changedRange.toString() + " - " + from.getForumla(cell).getFormula().getA1() + " -> " + uf.getFormula().getA1());
                        // Log analysed formula in From
                        from.setAnalysed(changedRange);
                    } else {
                        //  1.2 Range not present in From in any other formulae      => REPORT NEW FORMULAE
                        newRange.addCell(cell);
                        
                    }
                }
                
                if (!newRange.isEmpty())
                    System.out.println("NEW FORMULA: " + newRange.toString() + " - " + uf.getFormula().getA1());
                
                // Move to next unique formula in To set
                continue;
            }
            
            //2. Formulae match, ranges match                            => DO NOTHING
            if (fromRange.equals(toRange)) {
                System.out.println("DO NOTHING, FORMULAE & RANGES MATCH: " + toRange.toString() + " - "  + uf.getFormula().getA1());
                // Log analysed formula in From
                from.setAnalysed(fromRange);
                
                // Move to next unique formula in To set
                continue;
            }
            
            //3. Formulae match, ranges do not match. Loop through sub ranges:
            
            //3.1 Sub ranges match                                     => DO NOTHING
            range = fromRange.intersect(toRange);
            if (!range.isEmpty())
                System.out.println("DO NOTHING, FORMULAE & RANGES MATCH: " + range.toString() + " - "  + uf.getFormula().getA1());
            // Log analysed formula in From
            from.setAnalysed(range);
            
            //  3.2 Range missing in from (for this formulae)
            range = fromRange.removeFrom(toRange);
            newRange = new CompoundRange();
            
            // Loop through range
            while (range.hasNext()) {

                cell = range.next();

                if (from.findCell(cell)) {
                    //  1.1 Range present in From in some other formulae         => REPORT FORMULAE CHANGED
                    changedRange = from.getForumla(cell).getRange();
                    changedRange = changedRange.intersect(range);

                    System.out.println("FORMULA CHANGED: " + changedRange.toString() + " - " + from.getForumla(cell).getFormula().getA1() + " -> " + uf.getFormula().getA1());
                    // Log analysed formula in From
                    from.setAnalysed(changedRange);
                } else {
                    //  1.2 Range not present in From in any other formulae      => REPORT NEW FORMULAE
                    newRange.addCell(cell);

                }
            }

            if (!newRange.isEmpty())
                System.out.println("NEW FORMULA: " + newRange.toString() + " - " + uf.getFormula());
            
            
            //  3.3 Range missing in to (for this formulae)              => DO NOTHING: WILL REPORT WHEN WE GET TO IT
            
        }
        
        iter = from.listIterator();
        
        //-- LOOP THROUGH FROM UNIQUE FORMULAE --
        while (iter.hasNext()) {
            
            // Get the next unique formula
            uf = iter.next();
            
            // Get the compound range of cells not yet analysed
            fromRange = uf.getUnanalysed();
            
            // Make sure we still have cells to look at
            if (!fromRange.isEmpty()) {
                
                toRange = this.findFormula(uf);

                //  Formulae does not exist in To
                if (toRange == null) {
                    
                    System.out.println("REMOVED FORMULAE: " + fromRange.toString() + " - " + uf.getFormula());
                    
                    continue;
                }
                System.out.println("SHOULDN'T GET HERE! " + fromRange.toString());
            }
        }
    }
}