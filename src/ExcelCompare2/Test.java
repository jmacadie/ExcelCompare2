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
            System.out.println("*** Loaded From ***");
            
            workbook = SpreadSheetUtils.loadSpreadSheet(fileTo);
            sheet = workbook.getSheetAt(0);
            CondensedFormulae cfTo = new CondensedFormulae(sheet);
            System.out.println("*** Loaded To ***");
            
            cfTo.diff(cfFrom);
            System.out.println("*** Done Diff ***");
            
        } catch (Exception e) {
            System.err.println("Failed: " + e.getMessage());
        }
        
    }
    
    private static void testR1C1Conversion() {
        String fStr = "'=IF(Z1815,-SUM(Z1818:Z1819),IF(Z1316>0,(IF(OR(Z1806=0,Z1809=0),0,Z1806*Z1812/Z1809)+IF(OR(Z1807=0,Z1810=0),0,Z1807*Z1813/Z1810)),0))";
        CellRef cr = new CellRef("$Z$1820");
        Formula f = new Formula(fStr, cr);
        
        System.out.println(f.getFormulaR1C1());
    }
}