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
    
    public Integer translateRow(Direction direction, int source) {
        
        return translateInner(RowCol.ROW, direction, source);
        
    }
    
    public Integer translateColumn(Direction direction, int source) {
        
        return translateInner(RowCol.COL, direction, source);
        
    }
    
    private Integer translateInner(RowCol type, Direction direction, int source) {
        
        RowColMap map = (type == RowCol.ROW) ? _rowMap : _colMap;
        
        if (direction == Direction.FROM_TO && !map.isFromMapped(source)) 
            return null;
        
        if (direction == Direction.TO_FROM && !map.isToMapped(source)) 
            return null;
        
        if (direction == Direction.FROM_TO) 
            return map.fromIsMappedTo(source);
        
        if (direction == Direction.TO_FROM) 
            return map.toIsMappedTo(source);
        
        // Won't get here but stops and IDE error
        return null;
        
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
        Integer actualFromPos;
        TransTracker t;
        CellTransInsertDelete e;
        int maxMove;
        int maxTo = 0;
        RowColMap map;
        
        map = (type == RowCol.ROW) ? _rowMap : _colMap;
        
        // Loop through all the FROM rows
        t = new TransTracker(limit);
        for (int i = 1; i <= limit; i++) {
            // Get mapped TO row
            actualToPos = map.fromIsMappedTo(i);
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
                    t.delete(i, 1);
                    e = new CellTransInsertDelete(
                            CellTransInsertDelete.CellTranslationType.DELETED, i, 1);
                    if(type == RowCol.ROW) {
                        this._rowDeletes.add(e);
                    } else {
                        this._columnDeletes.add(e);
                    }
                } else {
                    // If null assume a changed match
                    // in which case assume row not moved just edited
                    // so do nothing other than increment the target counter
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
                    for (int j = t.pos(); j < actualToPos; j++) {
                        actualFromPos = map.toIsMappedTo(j);
                        if (actualFromPos == null) {
                            // Unmapped so must mean an insert
                            // TODO: group multiple inserts
                            t.insert(i, 1);
                            e = new CellTransInsertDelete(
                                    CellTransInsertDelete.CellTranslationType.INSERTED, i, 1);
                            if(type == RowCol.ROW) {
                                this._rowInserts.add(e);
                            } else {
                                this._columnInserts.add(e);
                            }
                        } else {
                            // Row Moved
                            // TODO: group multiple inserts
                            maxMove = Math.max(maxMove, actualFromPos - i);
                        }
                    }
                    if (maxMove > 0) {
                        // Have moved so add the transform
                        if(type == RowCol.ROW) {
                            this._rowMoves.add(new CellTransMove(i, i + maxMove, 1));
                        } else {
                            this._columnMoves.add(new CellTransMove(i, i + maxMove, 1));
                        }
                        // and record the move
                        t.move(i, i + maxMove, 1);
                    } else {
                        // Was just an insert / inserts
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
            this._rowInserts.add(e);
        }
    }
    
    private class TransTracker {
        
        List<Integer> _toMapped;
        List<Integer> _fromCurMap;
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
        
        private boolean isMapped(int pos) {
            for (int i = 1; i < _fromCurMap.size(); i++) {
                if (_fromCurMap.get(i) != null &&
                    _fromCurMap.get(i) == pos) {
                    return (_toMapped.get(i) == 1);
                }
            }
            // shouldn't get here
            return false;
        }
        
        private void increment() {
            while (true) {
                _idx++;
                if (_idx > _toMapped.size() || !isMapped(_idx))
                    break;
            }
        }
        
        public void match() {
            // Move index on
            increment();
        }
        
        public void delete(int at, int number) {
            // update _fromCurrMap
            for (int i = at; i < at + number; i++) {
                _fromCurMap.set(i, null);
            }
            for (int i = at + number; i < _fromCurMap.size(); i++) {
                _fromCurMap.set(i, _fromCurMap.get(i) - number);
            }
        }
        
        public void insert(int at, int number) {
            // update _fromCurrMap
            for (int i = at; i < _fromCurMap.size(); i++) {
                _fromCurMap.set(i, _fromCurMap.get(i) + number);
            }
            // Move index on
            increment();
        }
        
        public void move(int from, int to, int number) {
            // update _fromCurrMap
            for (int i = from; i < from + number; i++) {
                _fromCurMap.set(i, to + _fromCurMap.get(i) - from);
                // update _toMapped
                _toMapped.set(i, 1);
            }
            for (int i = from + number; i < to; i++) {
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
    
    private static RowColMap createRowColMap(CondensedFormulae from, CondensedFormulae to, RowCol searchBy) {
        
        int fromLimit = (searchBy == RowCol.ROW) ? from.getMaxRows() : from.getMaxCols();
        int toLimit = (searchBy == RowCol.ROW) ? to.getMaxRows() : to.getMaxCols();
        RowColMap map = new RowColMap(fromLimit, toLimit);
        
        int offset = 0;
        
        // Loop through all FROM rows / cols
        for (int i = 1; i <= fromLimit; i++) {
            offset = mapAToB(from, to, i, map, Direction.FROM_TO, searchBy, offset, 0);
        }
        
        return map;
        
    }
    
    private static Direction reverseDirection(Direction direction) {
        if (direction == Direction.FROM_TO) {
            return Direction.TO_FROM;
        } else {
            return Direction.FROM_TO;
        }
    }
    
    private static int mapAToB(
            CondensedFormulae a, 
            CondensedFormulae b,
            int pos,
            RowColMap map, 
            Direction direction, 
            RowCol searchBy,
            int offset,
            int savedPos) {
        
        CondensedFormulae aRowCol;
        CondensedFormulae bRowCol;
        Match m1;
        Match m2;
        int matchedBPos;

        // Only search rows / cols not already mapped
        if ((direction == Direction.FROM_TO && !map.isFromMapped(pos)) ||
            (direction == Direction.TO_FROM && !map.isToMapped(pos))) {
            aRowCol = (searchBy == RowCol.ROW) ? a.getRow(pos) : a.getColumn(pos);
            // Find 1st match
            m1 = fanSearch(
                    aRowCol, 
                    b, 
                    map, 
                    direction, 
                    searchBy, 
                    pos + offset, 
                    savedPos);
            // Keep looping until we don't have a match
            // Will break out if match A -> B is not as good
            matchedBPos = m1.getPos();
            while (matchedBPos != 0) {
                // Get matched row in to
                bRowCol = (searchBy == RowCol.ROW) ? b.getRow(matchedBPos) : b.getColumn(matchedBPos);
                // ... and check for no better reverse match
                m2 = fanSearch(
                        bRowCol, 
                        a, 
                        map, 
                        reverseDirection(direction), 
                        searchBy, 
                        matchedBPos - offset, 
                        0);
                if (m2.getPos() != 0 && m2.getDistance() < m1.getDistance()) {
                    // Reverse match (B -> A) better
                    // Recursively look other way
                    mapAToB(b, a, matchedBPos, map, reverseDirection(direction), searchBy, -offset, 0);
                    // and then keep looking on original row / col
                    mapAToB(a, b, pos, map, direction, searchBy, offset, matchedBPos);
                } else {
                    // Original match (A -> B) better
                    // Add to map
                    map.add(pos, matchedBPos, direction);
                    // Set our offset
                    offset = (matchedBPos - pos);
                    // Set counter to zero to force quit while loop
                    matchedBPos = 0;
                }
            }
        }
        return offset;
    }
    
    private enum RowCol {
        ROW, COL
    }
    
    public enum Direction {
        FROM_TO, TO_FROM
    }
    
    private static Match fanSearch(
            CondensedFormulae matchTo, // Assumed to be a single row / column
            CondensedFormulae findIn, // Assumed to be a whole sheet
            RowColMap currentMap,
            Direction direction,
            RowCol searching,
            int startPos,
            int offset) {
        
        // Get the limits of the searched sheet
        int maxLimit = (searching == RowCol.ROW) ? findIn.getMaxRows() : findIn.getMaxCols();
        
        // Don't search out of the bounds of the searched sheet
        startPos = Math.min(startPos, maxLimit);
        
        // Set start search position
        int searchPosPos = startPos + offset;
        int searchNegPos = startPos - offset;
        searchPosPos = Math.max(Math.min(searchPosPos, maxLimit),1);
        searchNegPos = Math.max(Math.min(searchNegPos, maxLimit),1);
        
        CondensedFormulae option;
        double d;
        
        int limit = Math.max(startPos - 1, (maxLimit - startPos));
        
        // Loop over the whole sheet, fanning out up and down
        // Will quit out as soon as we find the first match
        int j;
        for (int i = 0; i < limit; i++) {
            // Look down first
            // Don't look beyond the end of the range though
            // Also don't check an already mapped row
            j = searchPosPos + i;
            if (j <= maxLimit &&
                ((direction == Direction.FROM_TO && !currentMap.isToMapped(j)) ||
                (direction == Direction.TO_FROM && !currentMap.isFromMapped(j)))) {
                // Find the searched row or column
                option = getOption(findIn, searching, j);
                d = distance(matchTo, option, true);
                // If we have a shell match return
                if (d != -1)
                    return new Match(j, d);
            }
            
            // Then up
            // Don't look beyond the end of the range though
            // Also don't check an already mapped row
            j = searchNegPos - i;
            if (j > 0 &&
                ((direction == Direction.FROM_TO && !currentMap.isToMapped(j)) ||
                (direction == Direction.TO_FROM && !currentMap.isFromMapped(j)))) {
                // Find the searched row or column
                option = getOption(findIn, searching, j);
                d = distance(matchTo, option, true);
                // If we have a shell match return
                if (d != -1)
                    return new Match(j, d);
            }
        }
        // If we get here we've not found a match so retrun a negative signal
        return new Match(0, -1);
    }
    
    private static CondensedFormulae getOption(CondensedFormulae findIn, RowCol searching, int pos) {
        if (searching == RowCol.ROW) {
            return findIn.getRow(pos);
        } else {
            return findIn.getColumn(pos);
        }
    }
    
    private static double distance(CondensedFormulae from, CondensedFormulae to, boolean strict) {
        // Assumes we're comparing a row or column to one another
        
        ListIterator<AnalysedFormula> iterTo;
        ListIterator<AnalysedFormula> iterFrom = from.listIterator();
        AnalysedFormula af;
        Formula fTo;
        Formula fFrom;
        int cells;
        int goodCells = 0;
        int badCells = 0;
        double d;
        double tmp;
        double out = 0;
        
        // Loop through every from formula
        while (iterFrom.hasNext()) {
            af = iterFrom.next();
            fFrom = af.getFormula();
            cells = af.getRange().size();
            // Loop through every to formula
            // Need to double loop as can't be sure of pairwise order
            iterTo = to.listIterator();
            tmp = -1;
            while (iterTo.hasNext()) {
                fTo = iterTo.next().getFormula();
                // Get the formula distance
                d = fFrom.translatedDistance(fTo);
                // If there's a shell match record the distance
                if (d != -1)
                    tmp = (tmp == -1) ? d : Math.min(tmp, d);
            }
            // TODO: cannot cope with a single delete in both dimensions
            // This is beacuse on either rows or columns the whole of the source
            // row / col cannot be found in the target
            // A fix would be to allow a match if more than [70%] of cells have
            // a direct match, rather than the 100% threshold currently. Having
            // a threshold introduces the risk of funny matches though
            // If no match on any formula then whole row is considered not
            // matched so break
            if (tmp == -1 && strict) {
                return -1;
            } else if (tmp == -1) {
                // Increment counter of bad (i.e. not matched) cells
                badCells += cells;
            } else {
                // Increment counter of good (i.e. matched) cells
                goodCells += cells;
                out += tmp;
            }    
        }
        
        // If have some bad cells but within threshold of good cells (i.e.
        // have mapped almost all of the row / column then scale up the
        // output and return a match
        if (badCells > 0) {
            if ((badCells / (goodCells + badCells)) < 0.3) {
                out = out * 10;
            } else {
                out = -1;
            }
        }
        
        return out;
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
        
        public void add(int from, int to, Direction direction) {
            if (direction == Direction.FROM_TO) {
                _from.set(from, to);
                _to.set(to, from);
            } else {
                _from.set(to, from);
                _to.set(from, to);
            }
            
        }
        
        public Integer fromIsMappedTo(int posn) {
            return _from.get(posn);
        }
        
        public Integer toIsMappedTo(int posn) {
            return _to.get(posn);
        }

    }
    
    private static class Match {
        
        private final int _matchPosn;
        private final double _distance;
        
        Match (int pos, double distance) {
            _matchPosn = pos;
            _distance = distance;
        }
        
        public int getPos() {
            return _matchPosn;
        }
        
        public double getDistance() {
            return _distance;
        }
    }
       
}
