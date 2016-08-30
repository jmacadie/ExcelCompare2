/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelCompare2;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author james.macadie
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        testCondense();
    }
    
    private static void testCondense() {
        String fileTo = "resources/a.xlsx";
        String fileFrom = "resources/b.xlsx";
        
        try {
            Workbook workbook = SpreadSheetUtils.loadSpreadSheet(fileFrom);
            Sheet sheet = workbook.getSheetAt(0);
            CondensedFormulae cfFrom = new CondensedFormulae(sheet);
            
            workbook = SpreadSheetUtils.loadSpreadSheet(fileTo);
            sheet = workbook.getSheetAt(0);
            CondensedFormulae cfTo = new CondensedFormulae(sheet);
            System.out.println("*** Loaded ***");
            
            cfTo.diff(cfFrom);
            System.out.println("*** Done Diff ***");
            
        } catch (Exception e) {
            System.err.println("Failed: " + e.getMessage());
        }
        
    }
    
}