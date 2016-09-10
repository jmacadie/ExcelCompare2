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
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        testRowMap();
    }
    
    private static void testRowMap() {

        String fileFrom = "resources/a1.xlsx";
        String fileTo = "resources/b1.xlsx";
        
        try {
            ISpreadSheet ssFrom = new POISpreadSheet(fileFrom);
            CondensedFormulae cfFrom = ssFrom.getCondensedFormulae();
            
            ISpreadSheet ssTo = new POISpreadSheet(fileTo);
            CondensedFormulae cfTo = ssTo.getCondensedFormulae();
            System.out.println("*** Loaded ***");
            
            SheetDiff sd = new SheetDiff(cfFrom, cfTo, ssFrom.getSheetName());
            System.out.println("*** Done Map ***");
            
            sd.report();
            
        } catch (Exception e) {
            System.err.println("Failed: " + e.getMessage());
        }
    }
    
}