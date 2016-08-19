/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelCompare2;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author james.macadie
 */
public class CondensedFormulae {
    
    // Ordered (by FormulaR1C1) list of unique formulae on a given sheet 
    private final List<UniqueFormula> _uniqueFormulae;
    // Track size of sheet
    private final int _maxCols;
    private final int _maxRows;
    
    // Constructor with no parameters
    public CondensedFormulae() {
        this._uniqueFormulae = new LinkedList<>();
        this._maxCols = 0;
        this._maxRows = 0;
    }
    
    // Constructor with worksheet
    // Build condensed formulae map from this worksheet
    public CondensedFormulae(Sheet sheet) {
        final Iterator<Row> rowIterator = sheet.rowIterator();
        Row row;
        Iterator<Cell> cellIterator;
        Cell cell;
        
        this._uniqueFormulae = new LinkedList<>();
        this._maxCols = 0;
        this._maxRows = 0;
        
        // Loop through all rows
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            cellIterator = row.cellIterator();
            
            // Loop through all cells
            while (cellIterator.hasNext()) {
                cell = cellIterator.next();
                
                // Add cell to condensed formulae map
                add(new Formula(cell));
            }
        }
    }
    
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
    private void add(Formula formula) {
        ListIterator<UniqueFormula> iter = _uniqueFormulae.listIterator();
        boolean found = false;
        UniqueFormula curr;
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
                iter.add(new UniqueFormula(formula));
                // Quit iteration loop as found the correct action
                found = true;
                break;
            }
        }
        // If not found in our loop then just add the formula as a new unique
        // formula at the end of the list
        if (!found) {
            iter.add(new UniqueFormula(formula));
        }
    }
    
}
