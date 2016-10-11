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

/**
 *
 * @author james.macadie
 */
public class SheetDiff {
    
    private final CellTranslations _translations;
    private final List<CellDiff> _differences;
    private final String _sheetName;
    
    SheetDiff (CondensedFormulae from, CondensedFormulae to, String name) {
        _translations = new CellTranslations(from, to);
        _differences = new LinkedList<> ();
        
        // Translate from so rows & cols line up
        TranslatedCondensedFormulae tFrom =
                new TranslatedCondensedFormulae(from, _translations);
        analyseDiff(tFrom, to);
        
        _sheetName = name;
    }
    
    private void analyseDiff(TranslatedCondensedFormulae from, CondensedFormulae to) {
        
        // TODO: need to account for translations
        
        ListIterator<AnalysedFormula> iter = to.listIterator();
        
        CondensedFormulae cfFrom = from.getTranslated();
        AnalysedFormula af;
        CompoundRange fromRange;
        CompoundRange toRange;
        CompoundRange range;
        
        // Loop through all the TO unique formuale
        while (iter.hasNext()) {
            
            // Get the TO unique uormula
            af = iter.next();
            
            // Find the ranges in each of FROM and TO that correspond to this
            // formula
            fromRange = cfFrom.findFormula(af);
            toRange = af.getRange();
            
            // 1) Formula does not exist in FROM at all
            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            if (fromRange == null) {
                // Find changed and new formulae over the TO range
                findChangedAndNew(toRange, af, from);
                // Move to next unique formula in TO
                continue;
            }
            
            // 2) Formula exists in FROM & ranges exactly match
            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            if (fromRange.equals(toRange)) {
                // Log analysed formula in FROM
                cfFrom.setAnalysed(fromRange);
                // Move to next unique formula in TO
                continue;
            }
            
            // 3) Formula exists in FROM but ranges do not match
            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            
            // 3.1) Extract the sub range that does match
            range = fromRange.intersect(toRange);
            if (!range.isEmpty())
                // Log analysed formula in FROM
                cfFrom.setAnalysed(range);
            
            // 3.2) Sub-range, present in TO but missing in FROM
            // Find changed and new formulae over the TO sub-range
            range = fromRange.removeFrom(toRange);
            findChangedAndNew(range, af, from);
            
            // 3.3) Sub-range, present in FROM but missing in TO
            // Do nothing as will pick it up when looping through FROM next
            
        }
        
        // Loop through all the FROM unique formuale that were removed
        // TODO: make sure there aren't any unanalysed formualae in the main
        // TranslatedFormulae bit of FROM
        CellDiff diff;
        iter = from.getRemoved().listIterator();
        // Loop through all the FROM unique formuale
        while (iter.hasNext()) {
            // Get the next FROM unique formula
            af = iter.next();
            // Add the difference
            diff = new CellDiff(CellDiff.CellDiffType.CLEARED,
                                af.getUniqueFormula(), null,
                                af.getRange());
            _differences.add(diff);
        }
    }
    
    private void findChangedAndNew (CompoundRange toRange, AnalysedFormula toFormula, TranslatedCondensedFormulae from) {
        
        CondensedFormulae cfFrom = from.getTranslated();
        CellRef cell;
        UniqueFormula ufFrom;
        UniqueFormula ufTo;
        CompoundRange fromPreTrans;
        CellRef c;
        Formula f;
        CompoundRange changedRange;
        CompoundRange newRange = new CompoundRange();
        CellDiff diff;
        
        toRange.moveFirst();
        while (toRange.hasNext()) {

            cell = toRange.next();

            if (!cfFrom.isAnalysed(cell) && cfFrom.findCell(cell)) {
                // Cell present in FROM but has a different formula
                // Cell also not yet analysed which is important as the 
                // code below will block add all cells that are moving
                // from one formula to another

                // Get the FROM formula for this cell
                ufFrom = cfFrom.getForumla(cell);

                // Figure out the compound range that this change occurs
                // over
                changedRange = ufFrom.getRange();
                changedRange = changedRange.intersect(toRange);

                // Add the found difference
                changedRange.moveFirst();
                c = changedRange.next();
                f = ufFrom.getFormula().getCopiedTo(c);
                ufFrom = new UniqueFormula(f, changedRange);
                f = toFormula.getFormula().getCopiedTo(c);
                ufTo = new UniqueFormula(f, changedRange);
                fromPreTrans = from.getPreTransRange(changedRange);
                diff = new CellDiff(CellDiff.CellDiffType.CHANGED, ufFrom, ufTo, fromPreTrans);
                _differences.add(diff);

                // Log analysed formula in FROM
                cfFrom.setAnalysed(changedRange);
            } else {
                // Cell not present in FROM in any other formulae
                // Add it to our new range accumulator
                newRange.addCell(cell);
            }
        }

        // Add the new range difference
        if (!newRange.isEmpty()) {
            ufFrom = null;
            newRange.moveFirst();
            c = newRange.next();
            f = toFormula.getFormula().getCopiedTo(c);
            ufTo = new UniqueFormula(f, newRange);
            diff = new CellDiff(CellDiff.CellDiffType.NEW, ufFrom, ufTo, null);
            _differences.add(diff);
        }
        
    }
    
    public void report() {
        // Don't report if sheet has no changes
        if (_differences.isEmpty() && _translations.size() == 0)
            return;
        
        System.out.println("");
        System.out.println("Differences for " + _sheetName);
        System.out.println("-----------------------------");
        System.out.println("");
        
        // Report on tranlations that might have been found
        _translations.report();
        
        CellDiff.CellDiffType type;
        UniqueFormula from;
        UniqueFormula to;
        CompoundRange preTrans;
        
        for (CellDiff d : _differences) {
            type = d.getType();
            switch (type) {
                case NEW:
                    to = d.getTo();
                    System.out.println("New formula found at " + to.getRange().toString());
                    if (to.getFormula().hasFormula()) {
                        System.out.println("  NEW: " + to.getFormula().getA1() + " (" + to.getFormula().getText() + ")");
                    } else {
                        System.out.println("  NEW: " + to.getFormula().getText());
                    };
                    System.out.println("  OLD: ");
                    break;
                case CLEARED:
                    from = d.getFrom();
                    preTrans = d.getFromPreTrans();
                    System.out.println("Formula removed from " + from.getRange().toString());
                    if (!preTrans.equals(from.getRange()))
                        System.out.println("- translated from " + preTrans.toString() + " originally");
                    System.out.println("  NEW: ");
                    if (from.getFormula().hasFormula()) {
                        System.out.println("  OLD: " + from.getFormula().getA1() + " (" + from.getFormula().getText() + ")");
                    } else {
                        System.out.println("  OLD: " + from.getFormula().getText());
                    }
                    break;
                case CHANGED:
                    from = d.getFrom();
                    to = d.getTo();
                    preTrans = d.getFromPreTrans();
                    System.out.println("Changed formula found at " + to.getRange().toString());
                    if (!preTrans.equals(from.getRange()))
                        System.out.println("- translated from " + preTrans.toString() + " originally");
                    if (to.getFormula().hasFormula()) {
                        System.out.println("  NEW: " + to.getFormula().getA1() + " (" + to.getFormula().getText() + ")");
                    } else {
                        System.out.println("  NEW: " + to.getFormula().getText());
                    }
                    if (from.getFormula().hasFormula()) {
                        System.out.println("  OLD: " + from.getFormula().getA1() + " (" + from.getFormula().getText() + ")");
                    } else {
                        System.out.println("  OLD: " + from.getFormula().getText());
                    }
                    break;
                default:
                    System.out.println("Unknown difference type?");
                    break;
            }
        }
    }
    
}
