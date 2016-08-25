/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelCompare2;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author james.macadie
 */
public class SpreadSheetUtilsTest {
    
    public SpreadSheetUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of convertToLetter method, of class SpreadSheetUtils.
     */
    @Test
    public void testConvertToLetter() {
        System.out.println("convertToLetter");
        int col = 0;
        String expResult = "";
        String result = SpreadSheetUtils.convertToLetter(col);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of convertFromLetter method, of class SpreadSheetUtils.
     */
    @Test
    public void testConvertFromLetter() {
        System.out.println("convertFromLetter");
        String col = "";
        int expResult = 0;
        int result = SpreadSheetUtils.convertFromLetter(col);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadSpreadSheet method, of class SpreadSheetUtils.
     */
    @Test
    public void testLoadSpreadSheet() throws Exception {
        System.out.println("loadSpreadSheet");
        String file = "";
        Workbook expResult = null;
        Workbook result = SpreadSheetUtils.loadSpreadSheet(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
