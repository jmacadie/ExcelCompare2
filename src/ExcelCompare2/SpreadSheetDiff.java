/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
            boolean first = true;
            
            // Assumes From and To have same sheets in same order
            // TODO: needs fixing
            while (first || ssFrom.hasNext() || ssTo.hasNext()) {
                cfFrom = ssFrom.getCondensedFormulae();
                cfTo = ssTo.getCondensedFormulae();
                
                SheetDiff sd = new SheetDiff(cfFrom, cfTo, ssFrom.getSheetName());
                sd.report();
                
                first = false;
                ssFrom.next();
                ssTo.next();
            }

        } catch (Exception e) {
            System.err.println("Failed: " + e.getMessage());
        }
    }
    
}