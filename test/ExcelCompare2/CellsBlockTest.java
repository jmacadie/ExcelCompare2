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
    
    protected final CellRef cA1;
    protected final CellRef cA2;
    protected final CellRef cB1;
    protected final CellRef cB2;
    protected final CellRef cDE1;
    protected final CellRef cA105;
    protected final CellRef cE10;
    
    protected final CellsBlock cbA1;
    protected final CellsBlock cbA1B2;
    protected final CellsBlock cbA1DE1;
    protected final CellsBlock cbA1A105;
    protected final CellsBlock cbA1E10;
    
    public CellsBlockTest() {
        cA1 = new CellRef("A1");
        cA2 = new CellRef("A2");
        cB1 = new CellRef("B1");
        cB2 = new CellRef("B2");
        cDE1 = new CellRef("DE1");
        cA105 = new CellRef("A105");
        cE10 = new CellRef("E10");
        
        cbA1 = new CellsBlock(cA1);
        cbA1B2 = new CellsBlock(cA1, cB2);
        cbA1DE1 = new CellsBlock(cA1, cDE1);
        cbA1A105 = new CellsBlock(cA1, cA105);
        cbA1E10 = new CellsBlock(cA1, cE10);
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("   test CellsBlock class");
        System.out.println("=========================");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("   END");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("");
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
        System.out.println("*   test size() method");

        assertEquals("A1 does not give 1 (1 by 1)", 1, cbA1.size());
        assertEquals("A1:DE1 does not give 50 (5 by 1)", 109, cbA1DE1.size());
        assertEquals("A1:A105 does not give 105 (1 by 105)", 105, cbA1A105.size());
        assertEquals("A1:E10 does not give 50 (5 by 10)", 50, cbA1E10.size());
    }

    /**
     * Test of contains method, of class CellsBlock.
     */
    @Test
    public void testContains() {
        System.out.println("*   test contains() method");
        
        assertTrue("A1 does not contain A1", cbA1.contains(cA1));
        assertFalse("A1 does contains B2", cbA1.contains(cB2));
        
        assertTrue("A1:DE1 does not contain A1", cbA1DE1.contains(cA1));
        assertFalse("A1:DE1 does contains B2", cbA1DE1.contains(cB2));
        
        assertTrue("A1:A105 does not contain A1", cbA1A105.contains(cA1));
        assertFalse("A1:A105 does contains B2", cbA1A105.contains(cB2));
        
        assertTrue("A1:A105 does not contain A1", cbA1E10.contains(cA1));
        assertTrue("A1:A105 does not contain B2", cbA1E10.contains(cB2));
    }

    /**
     * Test of isWithin method, of class CellsBlock.
     */
    @Test
    public void testIsWithin() {
        System.out.println("*   test isWithin() method");
        
        CompoundRange cr = new CompoundRange();
        cr.addCell(cA1);
        cr.addCell(cA2);
        cr.addCell(cB1);
        assertFalse("A1:B2 can be made from A1, A2, B1", cbA1B2.isWithin(cr));
        
        cr.addCell(cB2);
        assertTrue("A1:B2 cannot be made from A1, A2, B1, B2", cbA1B2.isWithin(cr));
        
        cr.addCell(cA105);
        cr.addCell(cDE1);
        assertTrue("A1:B2 cannot be made from A1, A2, A105, B1, B2, DE1", cbA1B2.isWithin(cr));
    }

    /**
     * Test of toCompoundRange method, of class CellsBlock.
     */
    @Test
    public void testToCompoundRange() {
        System.out.println("*   test toCompoundRange() method");
        
        CompoundRange expect = new CompoundRange();
        expect.addCell(cA1);
        expect.addCell(cA2);
        expect.addCell(cB1);
        expect.addCell(cB2);
        
        assertTrue("A1:B2 is the same as A1, A2, B1, B2", cbA1B2.toCompoundRange().equals(expect));
    }

    /**
     * Test of toString method, of class CellsBlock.
     */
    @Test
    public void testToString() {
        System.out.println("*   test toString() method");
        
        assertEquals("A1 does not give A1", "A1", cbA1.toString());
        assertEquals("A1:DE1 does not give A1:DE1", "A1:DE1", cbA1DE1.toString());
        assertEquals("A1:A105 does not give A1:A105", "A1:A105", cbA1A105.toString());
        assertEquals("A1:E10 does not give A1:E10", "A1:E10", cbA1E10.toString());
    }
    
}
