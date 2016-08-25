/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelCompare2;

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
public class FormulaTest {
    
    public FormulaTest() {
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
     * Test of getFormulaR1C1 method, of class Formula.
     */
    @Test
    public void testGetFormulaR1C1() {
        System.out.println("getFormulaR1C1");
        Formula instance = null;
        String expResult = "";
        String result = instance.getFormulaR1C1();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFormula method, of class Formula.
     */
    @Test
    public void testGetFormula() {
        System.out.println("getFormula");
        Formula instance = null;
        String expResult = "";
        String result = instance.getFormula();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCellRef method, of class Formula.
     */
    @Test
    public void testGetCellRef() {
        System.out.println("getCellRef");
        Formula instance = null;
        CellRef expResult = null;
        CellRef result = instance.getCellRef();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
