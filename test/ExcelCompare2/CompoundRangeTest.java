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
public class CompoundRangeTest {
    
    public CompoundRangeTest() {
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
     * Test of addCell method, of class CompoundRange.
     */
    @Test
    public void testAddCell() {
        System.out.println("addCell");
        CellRef cr = null;
        CompoundRange instance = new CompoundRange();
        instance.addCell(cr);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeCell method, of class CompoundRange.
     */
    @Test
    public void testRemoveCell() {
        System.out.println("removeCell");
        CellRef cr = null;
        CompoundRange instance = new CompoundRange();
        instance.removeCell(cr);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class CompoundRange.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        CompoundRange instance = new CompoundRange();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isEmpty method, of class CompoundRange.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        CompoundRange instance = new CompoundRange();
        boolean expResult = false;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of savePosition method, of class CompoundRange.
     */
    @Test
    public void testSavePosition() {
        System.out.println("savePosition");
        CompoundRange instance = new CompoundRange();
        instance.savePosition();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of intersect method, of class CompoundRange.
     */
    @Test
    public void testIntersect() {
        System.out.println("intersect");
        CompoundRange cr = null;
        CompoundRange instance = new CompoundRange();
        CompoundRange expResult = null;
        CompoundRange result = instance.intersect(cr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of union method, of class CompoundRange.
     */
    @Test
    public void testUnion() {
        System.out.println("union");
        CompoundRange cr = null;
        CompoundRange instance = new CompoundRange();
        CompoundRange expResult = null;
        CompoundRange result = instance.union(cr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of missingFrom method, of class CompoundRange.
     */
    @Test
    public void testMissingFrom() {
        System.out.println("missingFrom");
        CompoundRange cr = null;
        CompoundRange instance = new CompoundRange();
        CompoundRange expResult = null;
        CompoundRange result = instance.missingFrom(cr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeFrom method, of class CompoundRange.
     */
    @Test
    public void testRemoveFrom() {
        System.out.println("removeFrom");
        CompoundRange cr = null;
        CompoundRange instance = new CompoundRange();
        CompoundRange expResult = null;
        CompoundRange result = instance.removeFrom(cr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of contains method, of class CompoundRange.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        CellRef cell = null;
        CompoundRange instance = new CompoundRange();
        boolean expResult = false;
        boolean result = instance.contains(cell);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class CompoundRange.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        CompoundRange cr = null;
        CompoundRange instance = new CompoundRange();
        boolean expResult = false;
        boolean result = instance.equals(cr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveFirst method, of class CompoundRange.
     */
    @Test
    public void testMoveFirst() {
        System.out.println("moveFirst");
        CompoundRange instance = new CompoundRange();
        instance.moveFirst();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveSaved method, of class CompoundRange.
     */
    @Test
    public void testMoveSaved() {
        System.out.println("moveSaved");
        CompoundRange instance = new CompoundRange();
        instance.moveSaved();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasNext method, of class CompoundRange.
     */
    @Test
    public void testHasNext() {
        System.out.println("hasNext");
        CompoundRange instance = new CompoundRange();
        boolean expResult = false;
        boolean result = instance.hasNext();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of next method, of class CompoundRange.
     */
    @Test
    public void testNext() {
        System.out.println("next");
        CompoundRange instance = new CompoundRange();
        CellRef expResult = null;
        CellRef result = instance.next();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class CompoundRange.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        CompoundRange instance = new CompoundRange();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
