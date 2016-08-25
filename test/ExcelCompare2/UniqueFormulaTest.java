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
public class UniqueFormulaTest {
    
    public UniqueFormulaTest() {
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
     * Test of compareTo method, of class UniqueFormula.
     */
    @Test
    public void testCompareTo_String() {
        System.out.println("compareTo");
        String formulaR1C1 = "";
        UniqueFormula instance = null;
        int expResult = 0;
        int result = instance.compareTo(formulaR1C1);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareTo method, of class UniqueFormula.
     */
    @Test
    public void testCompareTo_Formula() {
        System.out.println("compareTo");
        Formula formula = null;
        UniqueFormula instance = null;
        int expResult = 0;
        int result = instance.compareTo(formula);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareTo method, of class UniqueFormula.
     */
    @Test
    public void testCompareTo_UniqueFormula() {
        System.out.println("compareTo");
        UniqueFormula formula = null;
        UniqueFormula instance = null;
        int expResult = 0;
        int result = instance.compareTo(formula);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addCell method, of class UniqueFormula.
     */
    @Test
    public void testAddCell() {
        System.out.println("addCell");
        CellRef newCell = null;
        UniqueFormula instance = null;
        instance.addCell(newCell);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFormula method, of class UniqueFormula.
     */
    @Test
    public void testGetFormula() {
        System.out.println("getFormula");
        UniqueFormula instance = null;
        String expResult = "";
        String result = instance.getFormula();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFormulaR1C1 method, of class UniqueFormula.
     */
    @Test
    public void testGetFormulaR1C1() {
        System.out.println("getFormulaR1C1");
        UniqueFormula instance = null;
        String expResult = "";
        String result = instance.getFormulaR1C1();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRange method, of class UniqueFormula.
     */
    @Test
    public void testGetRange() {
        System.out.println("getRange");
        UniqueFormula instance = null;
        CompoundRange expResult = null;
        CompoundRange result = instance.getRange();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
