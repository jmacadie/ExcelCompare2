/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelCompare2;

import java.util.ListIterator;
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
public class CondensedFormulaeTest {
    
    public CondensedFormulaeTest() {
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
     * Test of findFormula method, of class CondensedFormulae.
     */
    @Test
    public void testFindFormula() {
        System.out.println("findFormula");
        AnalysedFormula f = null;
        CondensedFormulae instance = new CondensedFormulae();
        CompoundRange expResult = null;
        CompoundRange result = instance.findFormula(f);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findCell method, of class CondensedFormulae.
     */
    @Test
    public void testFindCell() {
        System.out.println("findCell");
        CellRef find = null;
        CondensedFormulae instance = new CondensedFormulae();
        boolean expResult = false;
        boolean result = instance.findCell(find);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAnalysed method, of class CondensedFormulae.
     */
    @Test
    public void testSetAnalysed_CellRef() {
        System.out.println("setAnalysed");
        CellRef analysed = null;
        CondensedFormulae instance = new CondensedFormulae();
        instance.setAnalysed(analysed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAnalysed method, of class CondensedFormulae.
     */
    @Test
    public void testSetAnalysed_CompoundRange() {
        System.out.println("setAnalysed");
        CompoundRange analysed = null;
        CondensedFormulae instance = new CondensedFormulae();
        instance.setAnalysed(analysed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listIterator method, of class CondensedFormulae.
     */
    @Test
    public void testListIterator() {
        System.out.println("listIterator");
        CondensedFormulae instance = new CondensedFormulae();
        ListIterator<AnalysedFormula> expResult = null;
        ListIterator<AnalysedFormula> result = instance.listIterator();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getForumla method, of class CondensedFormulae.
     */
    @Test
    public void testGetForumla() {
        System.out.println("getForumla");
        CellRef cell = null;
        CondensedFormulae instance = new CondensedFormulae();
        AnalysedFormula expResult = null;
        AnalysedFormula result = instance.getForumla(cell);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of diff method, of class CondensedFormulae.
     */
    @Test
    public void testDiff() {
        System.out.println("diff");
        CondensedFormulae from = null;
        CondensedFormulae instance = new CondensedFormulae();
        instance.diff(from);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
