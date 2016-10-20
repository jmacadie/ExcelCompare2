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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author james.macadie
 */
public class CompoundRange implements Iterator<CellRef> {

  // Best way to store?
  // Might be better to store as blocks (e.g. A1:H17 rather than A1, A2, A3, ..., A17, B1, etc.)
  private final List<CellRef> _compoundRange;
  private final Map<String, Integer> _refMap;
  private int _idx;
  private int _idxMax;
  private int _idxSave;

  // Constructor with no parameters
  public CompoundRange() {
    this._compoundRange = new LinkedList<>();
    this._refMap = new HashMap<>();
    this._idx = 0;
    this._idxMax = -1;
    this._idxSave = 0;
  }

  // Constructor with single cell
  public CompoundRange(CellRef cr) {
    this._compoundRange = new LinkedList<>();
    this._refMap = new HashMap<>();
    _compoundRange.add(cr);
    _refMap.put(cr.getAsKey(), 0);
    this._idx = 0;
    this._idxMax = 0;
    this._idxSave = 0;
  }

  // Constructor with text range e.g. A1:B5
  public CompoundRange(String range) {
    this._compoundRange = new LinkedList<>();
    this._refMap = new HashMap<>();
    // TODO: safety checks on the input string, which is currently just
    // assumed to be well formatted
    // 1st split the range ref by comma delimited parts
    String[] parts = range.split(",");
    String[] ends;
    CellRef cell;
    CellRef cellStart;
    CellRef cellEnd;
    int cellsAdded = 0;
    for (String part : parts) {
      // split each part into it's block top-left to bottom-right range
      ends = part.split(":");
      if (ends.length == 1) {
        // No block range so just add
        cell = new CellRef(part);
        _compoundRange.add(cell);
        _refMap.put(cell.getAsKey(), 0);
        cellsAdded++;
      } else {
        // Block so loop over whole block adding
        cellStart = new CellRef(ends[0]);
        cellEnd = new CellRef(ends[1]);
        for (int j = cellStart.getCol(); j <= cellEnd.getCol(); j++) {
          for (int k = cellStart.getRow(); k <= cellEnd.getRow(); k++) {
            cell = new CellRef(k, j);
            _compoundRange.add(cell);
            _refMap.put(cell.getAsKey(), 0);
            cellsAdded++;
          }
        }
      }
    }
    this._idx = 0;
    this._idxMax = cellsAdded - 1;
    this._idxSave = 0;
  }

  public CompoundRange getCopy() {
    CompoundRange out = new CompoundRange();

    savePosition();
    moveFirst();
    while (hasNext()) {
      out.addCell(next().getCopy());
    }
    moveSaved();

    return out;
  }

  public void addCell(CellRef cr) {
    // TODO: store sorted?
    // Will affect the comparative methods below
    if (!contains(cr)) {
      _compoundRange.add(cr);
      _idxMax++;
      _refMap.put(cr.getAsKey(), _idxMax);
    }
  }

  public void removeCell(CellRef cr) {
    if (contains(cr)) {
      // Adjust current index
      if (_compoundRange.indexOf(cr) <= _idx)
        _idx--;

      // Adjust saved position index
      if (_compoundRange.indexOf(cr) == _idxSave) {
        _idxSave = 0;
      } else if (_compoundRange.indexOf(cr) < _idxSave) {
        _idxSave--;
      }

      // Finally remove the item and decrement the overall counter
      removeInner(cr);
      _refMap.remove(cr.getAsKey());
      _idxMax--;
    }
  }

  private void removeInner(CellRef cr) {
    for(Iterator<CellRef> it=_compoundRange.iterator(); it.hasNext(); ) {
      if(it.next().equals(cr)) {
        it.remove();
        break;
      }
    }
  }

  public int size() {
    return (_idxMax + 1);
  }

  public boolean isEmpty() {
    return (_idxMax == -1);
  }

  public void savePosition() {
    _idxSave = _idx;
  }

  public CompoundRange intersect(CompoundRange cr) {
    // Start with empty compoundRange
    CompoundRange out = new CompoundRange();
    CellRef cell;
    // Save index position
    this.savePosition();
    // Then loop through this range adding any common refs
    this.moveFirst();
    while (this.hasNext()) {
      cell = this.next();
      if (cr.contains(cell))
        out.addCell(cell);
    }
    this.moveSaved();
    return out;
  }

  public CompoundRange union(CompoundRange cr) {
    // Clone this coumpound range
    CompoundRange out = this.getCopy();
    CellRef cell;
    // Save index position
    cr.savePosition();
    // Then loop through other range adding any missing refs
    cr.moveFirst();
    while (cr.hasNext()) {
      cell = cr.next();
      if (!this.contains(cell))
        out.addCell(cell);
    }
    cr.moveSaved();
    return out;
  }

  public CompoundRange missingFrom(CompoundRange cr) {
    // Start with empty compoundRange
    CompoundRange out = new CompoundRange();
    CellRef cell;
    // Save index position
    this.savePosition();
    // Then loop through this range adding any missing refs
    this.moveFirst();
    while (this.hasNext()) {
      cell = this.next();
      if (!cr.contains(cell))
        out.addCell(cell);
    }
    this.moveSaved();
    return out;
  }

  public CompoundRange removeFrom(CompoundRange cr) {
    // Start with empty compoundRange
    CompoundRange out = new CompoundRange();
    CellRef cell;
    // Save index position
    cr.savePosition();
    // Then loop through other range adding any missing refs
    cr.moveFirst();
    while (cr.hasNext()) {
      cell = cr.next();
      if (!this.contains(cell))
        out.addCell(cell);
    }
    cr.moveSaved();
    return out;
  }

  public boolean contains(CellRef cell) {
    return _refMap.containsKey(cell.getAsKey());
  }

  public boolean equals(CompoundRange cr) {
    // If not same size then not same
    if (this.size() != cr.size())
      return false;

    // Save index position
    this.savePosition();

    // Otherwise loop through all cells
    this.moveFirst();
    while (this.hasNext()) {
      if (!cr.contains(this.next())) {
        this.moveSaved();
        return false;
      }
    }
    this.moveSaved();
    return true;
  }

  public void moveFirst() {
    _idx = 0;
  }

  public void moveSaved() {
    _idx = _idxSave;
  }

  @Override
  public boolean hasNext() {
    return (_idx <= _idxMax);
  }

  // TODO: throw out of range exception?
  @Override
  public CellRef next() {
    _idx++;
    return _compoundRange.get(_idx - 1);
  }

  @Override
  public String toString() {
    String out = "";
    // TODO: better to initialise here or as a private field of the object?
    // Suspect better here as every time you add / remove / construct
    // you'll have to re-generate the blocks
    List<CellsBlock> blocks = toBlocks();

    // Loop through the blocks building an output string
    for (CellsBlock b : blocks) {
      if(out == "") {
        out = b.toString();
      } else {
        out += "," + b.toString();
      }
    }

    return out;
  }

  private List<CellsBlock> toBlocks() {
    CompoundRange remaining = this.getCopy();
    CellsBlock block;
    List<CellsBlock> blocks = new LinkedList<>();

    // Loop through until have blocked all cells
    while (!remaining.isEmpty()) {
      block = getMaxBlock(remaining);
      blocks.add(block);
      remaining = remaining.missingFrom(block.toCompoundRange());
    }
    return blocks;
  }

  private CellsBlock getMaxBlock(CompoundRange cr) {
    // TODO: feels inefficient
    CellsBlock maxBlock;
    CellsBlock block;
    CellRef start;
    CellRef end;

    // Set to null to start
    maxBlock = null;

    // New compound range so we can loop again
    CompoundRange cr2 = cr.getCopy();

    // Loop through every cell as a potential start of the biggest block
    cr.moveFirst();
    while (cr.hasNext()) {

      // Set potential start cell
      start = cr.next();

      // Loop through every cell as a potential end of the biggest block
      cr2.moveFirst();
      while (cr2.hasNext()) {

        // Set potential start cell
        end = cr2.next();

        // Test if end is below, right of start
        // Not a proper start - end otherwise
        if (end.getCol() >= start.getCol() &&
            end.getRow() >= start.getRow()) {
          // Create block
          block = new CellsBlock(start, end);
          // Test if block is valid
          // i.e. all cells in the compound range are found in the
          // proposed block
          if (block.isWithin(cr2)) {
            // If the max block is not yet set or the proposed block
            // is bigger then set the max block as the proposed
            if (maxBlock == null || block.size() > maxBlock.size())
              maxBlock = block;
          }
        }
      }
    }

    // Having looped through every cell pair this is the biggest cell block
    return maxBlock;
  }

}
