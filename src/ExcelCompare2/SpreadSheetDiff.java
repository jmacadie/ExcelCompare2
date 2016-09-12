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