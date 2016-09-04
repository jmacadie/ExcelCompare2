/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    public static void test(CondensedFormulae from, CondensedFormulae to) {
        
        RowColMap r = createRowColMap(from, to, RowCol.ROW);
        System.out.println("*** Break here ***");
        
    }
    
    private static RowColMap createRowColMap(CondensedFormulae from, CondensedFormulae to, RowCol searchBy) {
        
        RowColMap map = new RowColMap(from.getMaxRows(), to.getMaxRows());
        
        // Map From -> To
        createAtoBMap(from, to, map, Direction.FROM_TO, searchBy);
        
        // Map To -> From
        createAtoBMap(to, from, map, Direction.TO_FROM, searchBy);
        
        return map;
        
    }
    
    private static Direction reverseDirection(Direction direction) {
        if (direction == Direction.FROM_TO) {
            return Direction.TO_FROM;
        } else {
            return Direction.FROM_TO;
        }
    }
    
    private static void createAtoBMap(
            CondensedFormulae a, 
            CondensedFormulae b, 
            RowColMap map, 
            Direction direction, 
            RowCol searchBy) {
        
        CondensedFormulae aRowCol;
        CondensedFormulae bRowCol;
        Match m1;
        Match m2;
        int offset = 0;
        int matchedBPos;
        int limit;
        
        limit = (searchBy == RowCol.ROW) ? a.getMaxRows() : a.getMaxCols();
        
        // Loop through all A rows / cols
        for (int i = 1; i <= limit; i++) {
            // Only search rows / cols not already mapped
            if ((direction == Direction.FROM_TO && !map.isFromMapped(i)) ||
                (direction == Direction.TO_FROM && !map.isToMapped(i))) {
                aRowCol = (searchBy == RowCol.ROW) ? a.getRow(i) : a.getColumn(i);
                // Find 1st match
                m1 = fanSearch(
                        aRowCol, 
                        b, 
                        map, 
                        direction, 
                        searchBy, 
                        i + offset, 
                        0);
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
                        // TODO: Recursively reservse search as can't be sure 
                        // current found match is best
                        // Add to map ...
                        map.add(m2.getPos(), matchedBPos - offset);
                        // ... and keep looking
                        // Find next match
                        m1 = fanSearch(
                                aRowCol,
                                b,
                                map,
                                direction,
                                searchBy,
                                i + offset,
                                matchedBPos - (i + offset) + 1);
                        matchedBPos = m1.getPos();
                    } else {
                        // Original match (A -> B) better
                        // Add to map
                        map.add(i, matchedBPos);
                        // Set our offset
                        offset = (matchedBPos - i);
                        // Set counter to zero to force quit while loop
                        matchedBPos = 0;
                    }
                }
            }
        } 
    }
    
    private enum RowCol {
        ROW, COL
    }
    
    private enum Direction {
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
        searchPosPos = Math.min(searchPosPos, maxLimit);
        searchNegPos = Math.max(searchNegPos, 1);
        
        CondensedFormulae option;
        double d;
        
        int limit = Math.max(startPos - 1, (maxLimit - startPos));
        
        // Loop over a block of 10, up and down
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
                d = distance(option, matchTo);
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
                d = distance(option, matchTo);
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
    
    private static double distance(CondensedFormulae from, CondensedFormulae to) {
        // Assumes we're comparing a row or column to one another
        
        ListIterator<AnalysedFormula> iterTo;
        ListIterator<AnalysedFormula> iterFrom = from.listIterator();
        Formula fTo;
        Formula fFrom;
        double d;
        double tmp;
        double out = 0;
        
        // Loop through every from formula
        while (iterFrom.hasNext()) {
            fFrom = iterFrom.next().getFormula();
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
            // If no match on any formula then whole row is considered not
            // matched so break
            if (tmp == -1) {
                out = -1;
                break;
            } else {
                out += tmp;
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
        
        public void add(int from, int to) {
            _from.set(from, to);
            _to.set(to, from);
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
    
    // TODO: delete
    private Match findBoxedMatch(
            CondensedFormulae matchTo,
            CondensedFormulae findIn,
            RowColMap currentMap,
            Direction direction,
            RowCol searching,
            int startPos,
            int iteration) {
        
        // Set the starting search positions
        int searchPosPos = startPos + 10 * (iteration - 1);
        int searchNegPos = startPos - 10 * (iteration - 1);
        CondensedFormulae option;
        double d;
        double minDist = -1;
        int bestMatch = 0;
        int limit = (searching == RowCol.ROW) ? findIn.getMaxRows() : findIn.getMaxCols();
        
        // Loop over a block of 10, up and down
        int j;
        for (int i = 0; i < 10; i++) {
            // Look down first
            // Don't look beyond the end of the range though
            // Also don't check an already mapped row
            j = searchPosPos + i;
            if (j <= limit &&
                ((direction == Direction.FROM_TO && currentMap.isToMapped(j)) ||
                (direction == Direction.TO_FROM && currentMap.isFromMapped(j)))) {
                // Find the searched row or column
                option = getOption(findIn, searching, j);
                d = distance(option, matchTo);
                if ((d != -1) && ((minDist == -1) || (d < minDist))) {
                    minDist = d;
                    bestMatch = j;
                }
            }
            
            // Then up
            // Don't look beyond the end of the range though
            // Also don't check an already mapped row
            j = searchNegPos - i;
            if (j > 0 &&
                ((direction == Direction.FROM_TO && currentMap.isToMapped(j)) ||
                (direction == Direction.TO_FROM && currentMap.isFromMapped(j)))) {
                // Find the searched row or column
                option = getOption(findIn, searching, j);
                d = distance(option, matchTo);
                if ((d != -1) && ((minDist == -1) || (d < minDist))) {
                    minDist = d;
                    bestMatch = j;
                }
            }
        }
        return new Match(bestMatch, minDist);
    }
    
}
