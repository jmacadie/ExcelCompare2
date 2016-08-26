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
    
    protected final CellRef cA1;
    protected final CellRef cA2;
    protected final CellRef cA3;
    protected final CellRef cB1;
    protected final CellRef cB2;
    protected final CellRef cB3;
    protected final CellRef cC1;
    protected final CellRef cC2;
    protected final CellRef cC3;
    protected final CellRef cDE1;
    protected final CellRef cA105;
    protected final CellRef cE10;
    
    protected CompoundRange cr;
    protected CompoundRange cr2;
    
    public CompoundRangeTest() {
        cA1 = new CellRef("A1");
        cA2 = new CellRef("A2");
        cA3 = new CellRef("A3");
        cB1 = new CellRef("B1");
        cB2 = new CellRef("B2");
        cB3 = new CellRef("B3");
        cC1 = new CellRef("C1");
        cC2 = new CellRef("C2");
        cC3 = new CellRef("C3");
        cDE1 = new CellRef("DE1");
        cA105 = new CellRef("A105");
        cE10 = new CellRef("E10");
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("   test CompoundRange class");
        System.out.println("=========================");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("");
    }
    
    @Before
    public void setUp() {
        // Create a minty-fresh compound range object
        cr = new CompoundRange();
        cr.addCell(cA1);
        cr.addCell(cA2);
        cr.addCell(cB1);
        cr.addCell(cB2);
        
        // Create a second for intersect etc. testing
        cr2 = new CompoundRange();
        cr2.addCell(cB1);
        cr2.addCell(cB2);
        cr2.addCell(cB3);
        cr2.addCell(cC1);
        cr2.addCell(cC2);
        cr2.addCell(cC3);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addCell method, of class CompoundRange.
     */
    @Test
    public void testAddCell() {
        System.out.println("*   test addCell() method");
        
        int size = cr.size();
        assertFalse("A1, A2, B1, B2 contains DE1", cr.contains(cDE1));
        cr.addCell(cDE1);
        assertTrue("A1, A2, B1, B2, DE1 does not contain DE1", cr.contains(cDE1));
        assertEquals("CompundRange has not increased in size by 1", size + 1, cr.size());
        
        size = cr.size();
        assertTrue("A1, A2, B1, B2, DE1 does not contain B1", cr.contains(cB1));
        cr.addCell(cB1);
        assertTrue("A1, A2, B1, B2, DE1 does not contain B1", cr.contains(cB1));
        assertEquals("CompundRange has not stayed the same when adding a cell already in the collection", size, cr.size());
    }

    /**
     * Test of removeCell method, of class CompoundRange.
     */
    @Test
    public void testRemoveCell() {
        System.out.println("*   test removeCell() method");
        
        int size = cr.size();
        assertTrue("A1, A2, B1, B2 does not contain B1", cr.contains(cB1));
        cr.removeCell(cB1);
        assertFalse("A1, A2, B2 contains B1", cr.contains(cB1));
        assertEquals("CompoundRange has not decreased in size by 1", size - 1, cr.size());
        
        // Test removing a cell not in the list
        size = cr.size();
        assertFalse("A1, A2, B2 contains DE1", cr.contains(cDE1));
        cr.removeCell(cDE1);
        assertFalse("A1, A2, B2 contains DE1", cr.contains(cDE1));
        assertEquals("CompoundRange has not stayed the same when removing a cell not in the collection", size, cr.size());
        
        // Test iterator index position & saved position
        CellRef cell = cr2.next(); // After 1st cell, next cell -> B2
        cell = cr2.next(); // After 2nd cell, next cell -> B3
        cr2.savePosition();
        cell = cr2.next(); // After 3rd cell, next cell -> A1
        cr2.removeCell(cB2);
        cr2.removeCell(cC2);
        assertTrue("Next cell in CompoundRange is not C1", cr2.next().equals(cC1));
        cr2.moveSaved();
        assertTrue("Saved position cell in CompoundRange is not A1", cr2.next().equals(cB3));
    }

    /**
     * Test of size method, of class CompoundRange.
     */
    @Test
    public void testSize() {
        System.out.println("*   test size() method");
        
        assertEquals("A1, A2, B1, B2 does not have size 4", 4, cr.size());
        
        cr = new CompoundRange();
        assertEquals("Empty CompoundRange does not have size 0", 0, cr.size());
    }

    /**
     * Test of isEmpty method, of class CompoundRange.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("*   test isEmpty() method");
        
        assertFalse("A1, A2, B1, B2 is empty", cr.isEmpty());
        cr.removeCell(cA1);
        cr.removeCell(cA2);
        cr.removeCell(cB1);
        cr.removeCell(cB2);
        assertTrue("A1, A2, B1, B2 (all removed) is not empty", cr.isEmpty());
        
        cr = new CompoundRange();
        assertTrue("Empty CompoundRange is not empty", cr.isEmpty());
        cr.addCell(cA1);
        assertFalse("A1 is empty", cr.isEmpty());
    }

    /**
     * Test of savePosition method, of class CompoundRange.
     */
    @Test
    public void testSavePosition() {
        System.out.println("*   test savePosition() & moveSaved() methods");
        
        // Use next method to increment on
        CellRef saved = cr.next();
        
        cr.savePosition();
        saved = cr.next(); // Save the next item for real this time
        
        // Go back to the start
        cr.moveFirst();
        CellRef test = cr.next();
        assertFalse("Saved (second cell) is the same as the first cell", test.equals(saved));
        
        // Find last cell
        while (cr.hasNext()) {
            test = cr.next();
        }
        assertFalse("Saved (second cell) is the same as the last cell", test.equals(saved));
        
        // Finally move back to the saved cell
        cr.moveSaved();
        test = cr.next();
        assertTrue("Saved (second cell) is not the same as the moved to saved cell", test.equals(saved));
    }

    /**
     * Test of intersect method, of class CompoundRange.
     */
    @Test
    public void testIntersect() {
        System.out.println("*   test intersect() method");
        
        CompoundRange expect = new CompoundRange();
        expect.addCell(cB1);
        expect.addCell(cB2);
        
        CompoundRange test = cr.intersect(cr2);
        assertTrue("Intersection of 'A1, A2, B1, B2' and 'B1, B2, B3, C1, C2, C3' is not 'B1, B2'", test.equals(expect));
        
        // Add some disjoint cells
        cr.addCell(cDE1);
        cr.addCell(cA105);
        cr2.addCell(cA105);
        expect.addCell(cA105);
        
        // Test again
        test = cr.intersect(cr2);
        assertTrue("Intersection of 'A1, A2, B1, B2, DE1, A105' and 'B1, B2, B3, C1, C2, C3, A105' is not 'B1, B2, A105'", test.equals(expect));
        
    }

    /**
     * Test of union method, of class CompoundRange.
     */
    @Test
    public void testUnion() {
        System.out.println("*   test union() method");
        
        CompoundRange expect = new CompoundRange();
        expect.addCell(cA1);
        expect.addCell(cA2);
        expect.addCell(cB1);
        expect.addCell(cB2);
        expect.addCell(cB3);
        expect.addCell(cC1);
        expect.addCell(cC2);
        expect.addCell(cC3);
        
        CompoundRange test = cr.union(cr2);
        assertTrue("Union of 'A1, A2, B1, B2' and 'B1, B2, B3, C1, C2, C3' is not 'A1, A2, B1, B2, B3, C1, C2, C3'", test.equals(expect));
        
        // Add some disjoint cells
        cr.addCell(cDE1);
        cr.addCell(cA105);
        cr2.addCell(cA105);
        expect.addCell(cDE1);
        expect.addCell(cA105);
        
        // Test again
        test = cr.union(cr2);
        assertTrue("Union of 'A1, A2, B1, B2, DE1, A105' and 'B1, B2, B3, C1, C2, C3, A105' is not 'A1, A2, B1, B2, B3, C1, C2, C3, DE1, A105'", test.equals(expect));
    }

    /**
     * Test of missingFrom method, of class CompoundRange.
     */
    @Test
    public void testMissingFrom() {
        System.out.println("*   test missingFrom() method");
        
        CompoundRange expect = new CompoundRange();
        expect.addCell(cA1);
        expect.addCell(cA2);
        
        CompoundRange test = cr.missingFrom(cr2);
        assertTrue("'A1, A2, B1, B2' missing from 'B1, B2, B3, C1, C2, C3' is not 'A1, A2'", test.equals(expect));
        
        // Add some disjoint cells
        cr.addCell(cDE1);
        cr.addCell(cA105);
        cr2.addCell(cA105);
        expect.addCell(cDE1);
        
        // Test again
        test = cr.missingFrom(cr2);
        assertTrue("'A1, A2, B1, B2, DE1, A105' missing from 'B1, B2, B3, C1, C2, C3, A105' is not 'A1, A2, DE1'", test.equals(expect));
    }

    /**
     * Test of removeFrom method, of class CompoundRange.
     */
    @Test
    public void testRemoveFrom() {
        System.out.println("*   test removeFrom() method");
        
        CompoundRange expect = new CompoundRange();
        expect.addCell(cB3);
        expect.addCell(cC1);
        expect.addCell(cC2);
        expect.addCell(cC3);
        
        CompoundRange test = cr.removeFrom(cr2);
        assertTrue("'A1, A2, B1, B2' removed from 'B1, B2, B3, C1, C2, C3' is not 'B3, C1, C2, C3'", test.equals(expect));
        
        // Add some disjoint cells
        cr.addCell(cDE1);
        cr.addCell(cA105);
        cr2.addCell(cA105);
        
        // Test again
        test = cr.removeFrom(cr2);
        assertTrue("'A1, A2, B1, B2, DE1, A105' missing from 'B1, B2, B3, C1, C2, C3, A105' is not 'B3, C1, C2, C3'", test.equals(expect));
    }

    /**
     * Test of contains method, of class CompoundRange.
     */
    @Test
    public void testContains() {
        System.out.println("*   test contains() method");
        
        assertTrue("'A1, A2, B1, B2' does not contain 'A1'", cr.contains(cA1));
        assertTrue("'A1, A2, B1, B2' does not contain 'A2'", cr.contains(cA2));
        assertTrue("'A1, A2, B1, B2' does not contain 'B1'", cr.contains(cB1));
        assertTrue("'A1, A2, B1, B2' does not contain 'B2'", cr.contains(cB2));
        
        assertTrue("'A1, A2, B1, B2' does not contain 'A2' (regenerated so diff object)", cr.contains(new CellRef("A2")));
        
        assertFalse("'A1, A2, B1, B2' contains 'DE1'", cr.contains(cDE1));
        assertFalse("'A1, A2, B1, B2' contains 'A105'", cr.contains(cA105));
        assertFalse("'A1, A2, B1, B2' contains 'E10'", cr.contains(cE10));
        
        // Add some disjoint cells
        cr.addCell(cDE1);
        cr.addCell(cA105);
        assertTrue("'A1, A2, B1, B2, DE1, A105' does not contain 'DE1'", cr.contains(cDE1));
        assertTrue("'A1, A2, B1, B2, DE1, A105' does not contain 'A105'", cr.contains(cA105));
        
        // Remove some cells
        cr.removeCell(cA2);
        cr.removeCell(cB1);
        assertFalse("'A1, B2, DE1, A105' contains 'A2'", cr.contains(cA2));
        assertFalse("'A1, B2, DE1, A105' contains 'B1'", cr.contains(cB1));
    }

    /**
     * Test of equals method, of class CompoundRange.
     */
    @Test
    public void testEquals() {
        System.out.println("*   test equals() method");
        
        assertTrue("'A1, A2, B1, B2' does not equal 'A1, A2, B1, B2'", cr.equals(cr));
        assertTrue("'B1, B2, B3, C1, C2, C3' does not equal 'B1, B2, B3, C1, C2, C3'", cr2.equals(cr2));
        
        assertFalse("'A1, A2, B1, B2' equals 'B1, B2, B3, C1, C2, C3'", cr.equals(cr2));
        assertFalse("'B1, B2, B3, C1, C2, C3' equals 'A1, A2, B1, B2'", cr2.equals(cr));
        
        CompoundRange test = new CompoundRange();
        test.addCell(cA1);
        test.addCell(cA2);
        test.addCell(cB1);
        test.addCell(cB2);
        assertTrue("'A1, A2, B1, B2' does not equal 'A1, A2, B1, B2' (rebuilt)", cr.equals(test));
        assertTrue("'A1, A2, B1, B2' (rebuilt) does not equal 'A1, A2, B1, B2'", test.equals(cr));
        
        test.addCell(cA105);
        assertFalse("'A1, A2, B1, B2' equals 'A1, A2, B1, B2,A105'", cr.equals(test));
        assertFalse("'A1, A2, B1, B2, A105' equals 'A1, A2, B1, B2'", test.equals(cr));
    }

    /**
     * Test of moveFirst method, of class CompoundRange.
     */
    @Test
    public void testMoveFirst() {
        System.out.println("*   test moveFirst() method");
        
        // Pick first cell and check it is A1
        CellRef cell = cr.next();
        assertTrue("First cell in 'A1, A2, B1, B2' is not A1", cell.equals(cA1));
        
        // Try next cell
        cell = cr.next();
        assertFalse("Next cell in 'A1, A2, B1, B2' is A1", cell.equals(cA1));
        
        // Go back to the start and test again
        cr.moveFirst();
        cell = cr.next();
        assertTrue("Move back to start & first cell in 'A1, A2, B1, B2' is not A1", cell.equals(cA1));
        
        // Find last cell
        while (cr.hasNext()) {
            cell = cr.next();
        }
        assertFalse("Last cell in 'A1, A2, B1, B2' is A1", cell.equals(cA1));
        
        // Go back to the start and test again
        cr.moveFirst();
        cell = cr.next();
        assertTrue("Move back to start & first cell in 'A1, A2, B1, B2' is not A1", cell.equals(cA1));
        
    }

    /**
     * Test of hasNext method, of class CompoundRange.
     */
    @Test
    public void testHasNext() {
        System.out.println("*   test hasNext() method");
        
        // Test start of range
        assertTrue("At start of 'A1, A2, B1, B2' does not have next cell", cr.hasNext());
        
        // Move past 1st cell
        CellRef cell = cr.next();
        assertTrue("Before 2nd cell of 'A1, A2, B1, B2' does not have next cell", cr.hasNext());
        
        // Move past 2nd cell
        cell = cr.next();
        assertTrue("Before 3rd cell of 'A1, A2, B1, B2' does not have next cell", cr.hasNext());
        
        // Move past 3rd cell
        cell = cr.next();
        assertTrue("Before 4th cell of 'A1, A2, B1, B2' does not have next cell", cr.hasNext());
        
        // Move past 4th cell
        cell = cr.next();
        assertFalse("After 4th cell of 'A1, A2, B1, B2' has next cell", cr.hasNext());
        
        // Move back to start
        cr.moveFirst();
        assertTrue("Back at start of 'A1, A2, B1, B2' does not have next cell", cr.hasNext());
        
        // Test empty CompoundRange
        CompoundRange test = new CompoundRange();
        assertFalse("Empty CompoundRange has next cell", test.hasNext());
    }

    /**
     * Test of next method, of class CompoundRange.
     */
    @Test
    public void testNext() {
        System.out.println("*   test next() method");
        
        // 1st cell
        CellRef cell = cr.next();
        assertTrue("1st cell in 'A1, A2, B1, B2' is not A1", cell.equals(cA1));
        
        // 2nd cell
        cell = cr.next();
        assertTrue("2nd cell in 'A1, A2, B1, B2' is not A2", cell.equals(cA2));
        
        // 3rd cell
        cell = cr.next();
        assertTrue("3rd cell in 'A1, A2, B1, B2' is not B1", cell.equals(cB1));
        
        // 4th cell
        cell = cr.next();
        assertTrue("4th cell in 'A1, A2, B1, B2' is not B2", cell.equals(cB2));
        
        // Move back to start
        cr.moveFirst();
        cell = cr.next();
        assertTrue("Move back & 1st cell in 'A1, A2, B1, B2' is not A1", cell.equals(cA1));
        
        // TODO: Test empty CompoundRange
        // Should throw exception
        CompoundRange test = new CompoundRange();
    }

    /**
     * Test of toString method, of class CompoundRange.
     */
    @Test
    public void testToString() {
        System.out.println("*   test toString() method");
        
        assertEquals("'A1, A2, B1, B2'  is not written as 'A1:B2'", "A1:B2", cr.toString());
        assertEquals("'B1, B2, B3, C1, C2, C3'  is not written as 'B1:C3'", "B1:C3", cr2.toString());
        
        // Check some disjoint additions
        cr.addCell(cDE1);
        cr.addCell(cA105);
        assertEquals("'A1, A2, B1, B2, DE1, A105'  is not written as 'A1:B2,DE1,A105'", "A1:B2,DE1,A105", cr.toString());
        
        // Check absolute cell references
        CompoundRange test = new CompoundRange();
        test.addCell(cA1);
        test.addCell(new CellRef("$A2"));
        test.addCell(new CellRef("B$1"));
        test.addCell(new CellRef("$B$2"));
        assertEquals("'A1, $A2, B$1, $B$2'  is not written as 'A1:B2'", "A1:B2", test.toString());
        
        // Build CompoundRange in different order and test
        test = new CompoundRange();
        test.addCell(cB2);
        test.addCell(cA2);
        test.addCell(cA1);
        test.addCell(cB1);
        assertEquals("'B2, A2, A1, B1'  is not written as 'A1:B2'", "A1:B2", test.toString());
        
        // Check we find the biggest blocks first
        cr2.addCell(new CellRef("B4"));
        cr2.addCell(new CellRef("C4"));
        cr2.addCell(new CellRef("D1"));
        cr2.addCell(new CellRef("D2"));
        cr2.addCell(new CellRef("D3"));
        assertEquals("'B1, B2, B3, C1, C2, C3, B4, C4, D1, D2, D3'  is not written as 'B1:D3,B4:C4'", "B1:D3,B4:C4", cr2.toString());
        
    }
    
}
