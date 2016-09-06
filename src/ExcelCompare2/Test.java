/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelCompare2;

import java.util.List;
import java.util.ArrayList;
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
        testRowMap();
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
    
    private static void testRowMap() {
        
//        CellRef cA1 = new CellRef("A1");
//        CellRef cA2 = new CellRef("A2");
//        CellRef cB1 = new CellRef("B1");
//        CellRef cB2 = new CellRef("B2");
//        
//        List<Formula> f = new ArrayList<> ();
//        f.add(new Formula("=B1", cA1));
//        f.add(new Formula("=C1", cB1));
//        f.add(new Formula("=A3", cA2));
//        f.add(new Formula("=B3", cB2));
//        CondensedFormulae from = new CondensedFormulae(f);
//        
//        f = new ArrayList<> ();
//        f.add(new Formula("=B1", cA1));
//        f.add(new Formula("=C1", cB1));
//        f.add(new Formula("=A3", cA2));
//        f.add(new Formula("=B3", cB2));
//        CondensedFormulae to = new CondensedFormulae(f);
//        
//        CellTranslations.test(from, to);

        String fileFrom = "resources/a2.xlsx";
        String fileTo = "resources/b2.xlsx";
        
        try {
            Workbook workbook = SpreadSheetUtils.loadSpreadSheet(fileFrom);
            Sheet sheet = workbook.getSheetAt(0);
            CondensedFormulae cfFrom = new CondensedFormulae(sheet);
            
            workbook = SpreadSheetUtils.loadSpreadSheet(fileTo);
            sheet = workbook.getSheetAt(0);
            CondensedFormulae cfTo = new CondensedFormulae(sheet);
            System.out.println("*** Loaded ***");
            
            CellTranslations ct = new CellTranslations(cfFrom, cfTo);
            ct.report();
            System.out.println("*** Done Map ***");
            
        } catch (Exception e) {
            System.err.println("Failed: " + e.getMessage());
        }
    }
    
}