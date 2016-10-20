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

/**
 *
 * @author james.macadie
 */
public class SpreadSheetDiff {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    // Use input arguments, or revert to defaults
    String fileFrom = (args.length != 2) ? "resources/a1.xlsx" : args[0];
    String fileTo = (args.length != 2) ? "resources/b1.xlsx" : args[1];

    try {
      ISpreadSheet ssFrom = new POISpreadSheet(fileFrom);
      ISpreadSheet ssTo = new POISpreadSheet(fileTo);

      CondensedFormulae cfFrom;
      CondensedFormulae cfTo;
      boolean loop = true;

      // Assumes From and To have same sheets in same order
      // TODO: needs fixing
      while (loop) {
        cfFrom = ssFrom.getCondensedFormulae();
        cfTo = ssTo.getCondensedFormulae();

        SheetDiff sd = new SheetDiff(cfFrom, cfTo, ssFrom.getSheetName());
        sd.report();

        loop = ssFrom.hasNext() || ssTo.hasNext();
        ssFrom.next();
        ssTo.next();
      }

    } catch (Exception e) {
      System.err.println("Failed: " + e.getMessage());
    }
  }

}