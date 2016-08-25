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
public class AnalysedFormulaTest {
    
    public AnalysedFormulaTest() {
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
     * Test of addCell method, of class AnalysedFormula.
     */
    @Test
    public void testAddCell() {
        System.out.println("addCell");
        CellRef newCell = null;
        AnalysedFormula instance = null;
        instance.addCell(newCell);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAnalysed method, of class AnalysedFormula.
     */
    @Test
    public void testSetAnalysed_boolean() {
        System.out.println("setAnalysed");
        boolean analysed = false;
        AnalysedFormula instance = null;
        instance.setAnalysed(analysed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAnalysed method, of class AnalysedFormula.
     */
    @Test
    public void testSetAnalysed_CellRef() {
        System.out.println("setAnalysed");
        CellRef analysed = null;
        AnalysedFormula instance = null;
        instance.setAnalysed(analysed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAnalysed method, of class AnalysedFormula.
     */
    @Test
    public void testSetAnalysed_CompoundRange() {
        System.out.println("setAnalysed");
        CompoundRange analysed = null;
        AnalysedFormula instance = null;
        instance.setAnalysed(analysed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAnalysed method, of class AnalysedFormula.
     */
    @Test
    public void testIsAnalysed() {
        System.out.println("isAnalysed");
        AnalysedFormula instance = null;
        boolean expResult = false;
        boolean result = instance.isAnalysed();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnanalysed method, of class AnalysedFormula.
     */
    @Test
    public void testGetUnanalysed() {
        System.out.println("getUnanalysed");
        AnalysedFormula instance = null;
        CompoundRange expResult = null;
        CompoundRange result = instance.getUnanalysed();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
