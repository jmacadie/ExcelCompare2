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
public class CellsBlockTest {
    
    public CellsBlockTest() {
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
     * Test of size method, of class CellsBlock.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        CellsBlock instance = null;
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of contains method, of class CellsBlock.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        CellRef cr = null;
        CellsBlock instance = null;
        boolean expResult = false;
        boolean result = instance.contains(cr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isWithin method, of class CellsBlock.
     */
    @Test
    public void testIsWithin() {
        System.out.println("isWithin");
        CompoundRange cr = null;
        CellsBlock instance = null;
        boolean expResult = false;
        boolean result = instance.isWithin(cr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toCompoundRange method, of class CellsBlock.
     */
    @Test
    public void testToCompoundRange() {
        System.out.println("toCompoundRange");
        CellsBlock instance = null;
        CompoundRange expResult = null;
        CompoundRange result = instance.toCompoundRange();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class CellsBlock.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        CellsBlock instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
