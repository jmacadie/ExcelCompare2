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
import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author james.macadie
 */
public class CellTranslations {
    
    private final RowColMap _rowMap;
    private final RowColMap _colMap;
    
    private final List<CellTransInsertDelete> _rowInserts;
    private final List<CellTransInsertDelete> _rowDeletes;
    private final List<CellTransMove> _rowMoves;
    private final List<CellTransInsertDelete> _columnInserts;
    private final List<CellTransInsertDelete> _columnDeletes;
    private final List<CellTransMove> _columnMoves;
    
    public CellTranslations (CondensedFormulae from, CondensedFormulae to) {
        _rowInserts = new LinkedList<> ();
        _rowDeletes = new LinkedList<> ();
        _rowMoves = new LinkedList<> ();
        _columnInserts = new LinkedList<> ();
        _columnDeletes = new LinkedList<> ();
        _columnMoves = new LinkedList<> ();
        
        _rowMap = createRowColMap(from, to, RowCol.ROW);
        _colMap = createRowColMap(from, to, RowCol.COL);
        
        findTransFromMap(RowCol.ROW, from.getMaxRows());
        findTransFromMap(RowCol.COL, from.getMaxCols());
    }
    
    public int size() {
        return _rowInserts.size() + _rowDeletes.size() +
               _columnInserts.size() + _columnDeletes.size() +
               _rowMoves.size() + _columnMoves.size();
    }
    
    public Integer translateRow(int source) {
        // Only goes FROM -> TO
        
        return translateInner(RowCol.ROW, source);
        
    }
    
    public Integer translateColumn(int source) {
        // Only goes FROM -> TO
        
        return translateInner(RowCol.COL, source);
        
    }
    
    private Integer translateInner(RowCol type, int source) {
        // Only goes FROM -> TO
        
        RowColMap map = (type == RowCol.ROW) ? _rowMap : _colMap;
        
        if (!map.isFromMapped(source)) 
            return null;
        
        return map.fromIsMappedTo(source);
        
    }
    
    public void report() {
        
        if (!_rowInserts.isEmpty() || !_rowDeletes.isEmpty() ||
            !_columnInserts.isEmpty() || !_columnDeletes.isEmpty() ||
            !_rowMoves.isEmpty() || !_columnMoves.isEmpty()) {
            System.out.println("Sheet translations");
            System.out.println("");
            reportIDInner(_rowInserts, RowCol.ROW, "insert");
            reportIDInner(_rowDeletes, RowCol.ROW, "delete");
            reportMInner(_rowMoves, RowCol.ROW);
            reportIDInner(_columnInserts, RowCol.COL, "insert");
            reportIDInner(_columnDeletes, RowCol.COL, "delete");
            reportMInner(_columnMoves, RowCol.COL);
        }
        
    }
    
    private void reportIDInner(List<CellTransInsertDelete> r, RowCol type, String id) {
        if (r.isEmpty())
            return;
        String rc = (type == RowCol.ROW) ? "row" : "column";
        String ePos;
        if (r.size() == 1) {
            System.out.println("The following " + rc + " " + id + " was found:");
        } else if (r.size() > 1) {
            System.out.println("The following " + rc + " " + id + "s were found:");
        }
        for (CellTransInsertDelete e : r) {
            if (type == RowCol.COL) {
                ePos = SpreadSheetUtils.convertToLetter(e.getPosn());
            } else {
                ePos = String.valueOf(e.getPosn());
            }
            if (e.getNumber() == 1) {
                System.out.println("1 " + rc + " " + id + "ed at " 
                        + rc + " " + ePos);
            } else {
                System.out.println(e.getNumber() + " " + rc + "s " 
                        + id + "ed at " + rc + " " + ePos);
            }
        }
        System.out.println("");
    }
    
    private void reportMInner(List<CellTransMove> r, RowCol type) {
        if (r.isEmpty())
            return;
        String rc = (type == RowCol.ROW) ? "row" : "column";
        String eFrom;
        String eTo;
        if (r.size() == 1) {
            System.out.println("The following " + rc + " move was found:");
        } else if (r.size() > 1) {
            System.out.println("The following " + rc + " moves were found:");
        }
        for (CellTransMove e : r) {
            if (type == RowCol.COL) {
                eFrom = SpreadSheetUtils.convertToLetter(e.getFrom());
                eTo = SpreadSheetUtils.convertToLetter(e.getTo());
            } else {
                eFrom = String.valueOf(e.getFrom());
                eTo = String.valueOf(e.getTo());
            }
            if (e.getNumber() == 1) {
                System.out.println("1 " + rc + " moveded from " 
                        + rc + " " + eFrom + " to " + rc + " " + eTo);
            } else {
                System.out.println(e.getNumber() + " " + rc
                        + "s moveded from " + rc + " " + eFrom
                        + " to " + rc + " "  + eTo);
            }
        }
        System.out.println("");
    }
    
    private void findTransFromMap (RowCol type, int limit) {
        
        Integer actualToPos;
        Integer tmpFromPos;
        TransTracker t;
        CellTransInsertDelete e;
        int maxMove;
        int inserts;
        int maxTo = 0;
        RowColMap map;
        
        map = (type == RowCol.ROW) ? _rowMap : _colMap;
        
        // Loop through all the FROM rows
        t = new TransTracker(limit);
        for (int fromPos = 1; fromPos <= limit; fromPos++) {
            // Get mapped TO row
            actualToPos = map.fromIsMappedTo(fromPos);
            // Record the max to pointer
            maxTo = (actualToPos == null) ? maxTo : Math.max(actualToPos, maxTo);
            // If is null then either a delete or a changed match
            if (actualToPos == null) {
                if (t.atEnd() ||
                    map.toIsMappedTo(t.pos()) != null) {
                    // either other we've tracked all the to rows
                    // or the target other side is non-null
                    // Row deleted
                    // TODO: group multiple deletes
                    t.delete(fromPos, 1);
                    e = new CellTransInsertDelete(
                            CellTransInsertDelete.CellTranslationType.DELETED, fromPos, 1);
                    if(type == RowCol.ROW) {
                        this._rowDeletes.add(e);
                    } else {
                        this._columnDeletes.add(e);
                    }
                } else {
                    // If null assume a changed match
                    // in which case assume row not moved just edited
                    // Add changed match to map
                    map.add(fromPos, t.pos());
                    // Record the to match in the Max to variable
                    maxTo = Math.max(t.pos(), maxTo);;
                    // & then increment the target counter
                    t.match();
                }
            } else {
                // Not null so mapped somewhere
                // If is the same as target then have found row / col where we
                // expected it
                if (actualToPos == t.pos()) {
                    // so do nothing other than increment the target counter
                    t.match();
                } else if (actualToPos > t.pos()) {
                    // Loop over skipped to rows and see where they went to
                    maxMove = 0;
                    inserts = 0;
                    for (int tmpToPos = t.pos(); tmpToPos < actualToPos; tmpToPos++) {
                        tmpFromPos = map.toIsMappedTo(tmpToPos);
                        if (tmpFromPos == null) {
                            // Unmapped so must mean an insert
                            inserts++;
                        } else {
                            // Row Moved
                            maxMove = Math.max(maxMove, tmpFromPos - fromPos);
                        }
                    }
                    if (maxMove > 0) {
                        // Have moved so add the transform
                        if(type == RowCol.ROW) {
                            this._rowMoves.add(new CellTransMove(fromPos, fromPos + maxMove, 1));
                        } else {
                            this._columnMoves.add(new CellTransMove(fromPos, fromPos + maxMove, 1));
                        }
                        // and record the move
                        t.move(fromPos, fromPos + maxMove, 1);
                    } else {
                        // Was just an insert / inserts
                        t.insert(fromPos, inserts);
                        e = new CellTransInsertDelete(
                                CellTransInsertDelete.CellTranslationType.INSERTED, 
                                fromPos, inserts);
                        if(type == RowCol.ROW) {
                            this._rowInserts.add(e);
                        } else {
                            this._columnInserts.add(e);
                        }
                        // Log same row found after insert / inserts
                        t.match();
                    }
                } else { // actual position is less then expected
                    // TODO: do we ever get here?
                }
            }
        }
        
        // Loop through all TO rows that are below where all FROM rows got
        // mapped. This will pick up any inserts at the bottom of the sheet
        for (int i = maxTo + 1; i < map._to.size(); i++) {
            e = new CellTransInsertDelete(
                        CellTransInsertDelete.CellTranslationType.INSERTED, map._from.size(), 1);
            if(type == RowCol.ROW) {
                this._rowInserts.add(e);
            } else {
                this._columnInserts.add(e);
            }
        }
    }
    
    private class TransTracker {
        
        // Note to future self: if the map is correct but the translations are
        // not correctly found from the map I have found it useful to watch the
        // state of _fromCurMap, _idx (expected TO row / column) and fromPos 
        // (the source FROM row / column being sought) as each sucessive FROM 
        // row or column is mapped in findTransFromMap(). The fromPos variable 
        // is in the parent routine, not in this class
        
        // List of which of the TO rows have currently been mapped
        // Starts as a list of zeros and is successively updated to 1's each
        // new row match is found
        // Index is denominated in terms of the FROM rows
        List<Integer> _toMapped;
        // Current map of where all FROM rows are mapped to
        // Starts expecting all rows to go to their matching counetrpart
        // e.g. 1 to 1, 2 to 2, ... 13 to 13, etc.
        // This map will be adjusted as each sucessive insert, delete and move
        // is detected
        List<Integer> _fromCurMap;
        // The internal index where we're currently up to on the TO sheet
        int _idx;
        
        TransTracker(int fromSize) {
            // Start index at 1st row / col
            _idx = 1;
            // Create a linked list with 0 everywhere
            _toMapped = new LinkedList<> ();
            // put null in first slot as we're 1 basing all arrays
            _toMapped.add(null); 
            for (int i = 1; i <= fromSize; i++) {
                _toMapped.add(0);
            }
            // Create a linked list with same number as index everywhere
            _fromCurMap = new LinkedList<> ();
            // put null in first slot as we're 1 basing all arrays
            _fromCurMap.add(null);
            for (int i = 1; i <= fromSize; i++) {
                _fromCurMap.add(i);
            }
        }
        
        private boolean isToMapped(int toPos) {
            for (int fromPos = 1; fromPos < _fromCurMap.size(); fromPos++) {
                if (_fromCurMap.get(fromPos) != null &&
                    _fromCurMap.get(fromPos) == toPos) {
                    return (_toMapped.get(fromPos) == 1);
                }
            }
            // shouldn't get here
            return false;
        }
        
        private void increment() {
            while (true) {
                _idx++;
                if (_idx > _toMapped.size() || !isToMapped(_idx))
                    break;
            }
        }
        
        public void match() {
            // Move index on
            increment();
        }
        
        public void delete(int at, int number) {
            // update _fromCurrMap
            
            // Set the first 'number' slots to map to null i.e. deleted ...
            for (int i = at; i < at + number; i++) {
                _fromCurMap.set(i, null);
            }
            // .. and shift the remaining slots back by 'number'
            for (int i = at + number; i < _fromCurMap.size(); i++) {
                _fromCurMap.set(i, _fromCurMap.get(i) - number);
            }
            // finally shift the forward-mapped early slots back by 'number'
            for (int i = 1; i < at; i++) {
                if(_fromCurMap.get(i) != null &&
                   _fromCurMap.get(i) > _idx)
                    _fromCurMap.set(i, _fromCurMap.get(i) - number);
            }
        }
        
        public void insert(int at, int number) {
            // update _fromCurrMap
            for (int i = at; i < _fromCurMap.size(); i++) {
                _fromCurMap.set(i, _fromCurMap.get(i) + number);
            }
            // finally shift the forward-mapped early slots forward by 'number'
            for (int i = 1; i < at; i++) {
                if(_fromCurMap.get(i) != null &&
                   _fromCurMap.get(i) > _idx)
                    _fromCurMap.set(i, _fromCurMap.get(i) + number);
            }
            // Move index on
            for (int i = 0; i < number; i++) {
                increment();
            }
        }
        
        public void move(int from, int to, int number) {
            // update _fromCurrMap
            // Move the moved rows ...
            for (int fromPos = from; fromPos < from + number; fromPos++) {
                _fromCurMap.set(fromPos, to + _fromCurMap.get(fromPos) - from);
                // update _toMapped
                // This way we know we'll have already spotted this row when we
                // increment our expected row counter later on, and can skip it
                _toMapped.set(fromPos, 1);
            }
            // ... and shift back the intermediate rows
            for (int i = from + number; i <= to; i++) {
                _fromCurMap.set(i, _fromCurMap.get(i) - number);
            }
        }
        
        public boolean atEnd() {
            return (_idx > _toMapped.size());
        }
        
        public int pos() {
            return _idx;
        }
        
    }
    
    private RowColMap createRowColMap(CondensedFormulae from, CondensedFormulae to, RowCol searchBy) {
        
        int fromLimit = (searchBy == RowCol.ROW) ? from.getMaxRows() : from.getMaxCols();
        int toLimit = (searchBy == RowCol.ROW) ? to.getMaxRows() : to.getMaxCols();
        RowColMap map = new RowColMap(fromLimit, toLimit);
        
        int offset = 0;
        int pos;
        int match;
        CondensedFormulae fromRowCol;
        
        // Loop through all FROM rows / cols
        for (int i = 1; i <= fromLimit; i++) {
            pos = i + offset;
            fromRowCol = (searchBy == RowCol.ROW) ? from.getRow(i) : from.getColumn(i);
            // Find a match in To
            match = fanSearch(
                    fromRowCol, 
                    to, 
                    map,
                    searchBy, 
                    pos);
            // If matched then record and update the offset tracker
            if (match != 0) {
                map.add(i, match);
                offset = match - i;
            }
        }
        return map;
    }
    
    public enum RowCol {
        ROW, COL
    }
    
    private int fanSearch(
            CondensedFormulae matchTo, // Assumed to be a single row / column
            CondensedFormulae findIn, // Assumed to be a whole sheet
            RowColMap currentMap,
            RowCol searching,
            int startPos) {
        
        // Get the limits of the searched sheet
        int maxLimit = (searching == RowCol.ROW) ? findIn.getMaxRows() : findIn.getMaxCols();
        
        // Don't search out of the bounds of the searched sheet
        startPos = Math.max(Math.min(startPos, maxLimit),1);
        
        CondensedFormulae option;
        
        int limit = Math.max(startPos - 1, (maxLimit - startPos));
        
        // Loop over the whole sheet, fanning out up and down
        // Will quit out as soon as we find the first match
        int j;
        for (int i = 0; i <= limit; i++) {
            // Look down first
            // Don't look beyond the end of the range though
            // Also don't check an already mapped row
            j = startPos + i;
            if (j <= maxLimit && !currentMap.isToMapped(j)) {
                // Find the searched row or column
                option = getOption(findIn, searching, j);
                if (rowColMatch(matchTo, option, currentMap, searching)) {
                    return j;
                }
            }
            
            // Then up
            // Don't look beyond the end of the range though
            // Also don't check an already mapped row
            j = startPos - i;
            if (j > 0 && !currentMap.isToMapped(j)) {
                // Find the searched row or column
                option = getOption(findIn, searching, j);
                if (rowColMatch(matchTo, option, currentMap, searching)) {
                    return j;
                }
            }
        }
        // If we get here we've not found a match so retrun a negative signal
        return 0;
    }
    
    private static CondensedFormulae getOption(CondensedFormulae findIn, RowCol searching, int pos) {
        if (searching == RowCol.ROW) {
            return findIn.getRow(pos);
        } else {
            return findIn.getColumn(pos);
        }
    }
    
    private boolean rowColMatch(
            CondensedFormulae from,
            CondensedFormulae to,
            RowColMap map,
            RowCol searching) {
        // Assumes we're comparing a row or column to one another
        
        ListIterator<AnalysedFormula> iterTo = to.listIterator();
        ListIterator<AnalysedFormula> iterFrom = from.listIterator();
        Formula fTo;
        Formula fFrom;
        List<Integer> dMap;
        boolean match;
        
        dMap = map._from;
        
        // Loop through every FROM formula
        while (iterFrom.hasNext()) {
            fFrom = iterFrom.next().getFormula();
            // Loop through every TO formula
            // As underlying formulae are sorted don't need to re-set the to
            // itertor & check earlier formulae
            match = false;
            while (iterTo.hasNext()) {
                fTo = iterTo.next().getFormula();
                // If there's a match then move to next FROM formula
                if (fFrom.translatedMatch(fTo, dMap, searching)) {
                    match = true;
                    break;
                }
            }
            // If no TO formulae matched then quit out
            if (!match)
                return false;
        }
        // Looped through all FROM formuale and found a match to a TO formula
        // so return true
        return true;
    }
    
    private static class RowColMap {
        
        List<Integer> _from;
        List<Integer> _to;
        
        RowColMap (int sizeFrom, int sizeTo) {
            // Add 1 as we're not zero basing anything in this project
            // Rows and cols all start at 1 in Excel
            // Initialse arrays with nulls everywhere as nothing mapped
            int size = sizeFrom + 1;
            _from = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                _from.add(null);
            }
            size = sizeTo + 1;
            _to = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                _to.add(null);
            }
        }
        
        public boolean isFromMapped(int posn) {
            return (_from.get(posn) != null);
        }
        
        public boolean isToMapped(int posn) {
            return (_to.get(posn) != null);
        }
        
        public void add(int from, int to) {
                _from.set(from, to);
                _to.set(to, from);
        }
        
        public Integer fromIsMappedTo(int posn) {
            return _from.get(posn);
        }
        
        public Integer toIsMappedTo(int posn) {
            return _to.get(posn);
        }

    }
    
}
