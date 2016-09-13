/* MIT License
 *
 * Copyright (c) 2016 James MacAdie
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
public class CellRefTest {
    
    private final CellRef cA1;
    private final CellRef c$A$1;
    private final CellRef c$A1;
    private final CellRef cA$1;
    private final CellRef cA2;
    private final CellRef cB1;
    private final CellRef cB2;
    private final CellRef cJ1;
    private final CellRef c$J$1;
    private final CellRef c$J1;
    private final CellRef cJ$1;
    private final CellRef cAB123;
    private final CellRef cA1000;
    private final CellRef c$A$1000;
    private final CellRef cDEF542;
    private final CellRef cDEF$542;
    private final CellRef c$DEF542;
    
    public CellRefTest() {
        cA1 = new CellRef("A1");
        c$A$1 = new CellRef("$A$1");
        c$A1 = new CellRef("$A1");
        cA$1 = new CellRef("A$1");
        cA2 = new CellRef("A2");
        cB1 = new CellRef("B1");
        cB2 = new CellRef("B2");
        cJ1 = new CellRef("J1");
        c$J$1 = new CellRef("$J$1");
        c$J1 = new CellRef("$J1");
        cJ$1 = new CellRef("J$1");
        cAB123 = new CellRef("AB123");
        cA1000 = new CellRef("A1000");
        c$A$1000 = new CellRef("$A$1000");
        cDEF542 = new CellRef("DEF542");
        cDEF$542 = new CellRef("DEF$542");
        c$DEF542 = new CellRef("$DEF542");
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("   test CellRef class");
        System.out.println("=========================");
    }
    
    @AfterClass
    public static void tearDownClass() {
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
     * Test of constructor from string ref.
     */
    @Test
    public void testConstructors() {
        System.out.println("*   test constructors");
        
        CellRef test = new CellRef(1, 1);
        assertTrue("(1, 1) -> A1 does not give true", cA1.equalsStrict(test));
        assertFalse("(1, 1) -> $A$1 does not give false", c$A$1.equalsStrict(test));
        assertFalse("(1, 1) -> $A1 does not give false", c$A1.equalsStrict(test));
        assertFalse("(1, 1) -> A$1 does not give false", cA$1.equalsStrict(test));
        
        test = new CellRef(1, 1, false, false);
        assertTrue("(1, 1, false, false) -> A1 does not give true", cA1.equalsStrict(test));
        assertFalse("(1, 1, false, false) -> $A$1 does not give false", c$A$1.equalsStrict(test));
        assertFalse("(1, 1, false, false) -> $A1 does not give false", c$A1.equalsStrict(test));
        assertFalse("(1, 1, false, false) -> A$1 does not give false", cA$1.equalsStrict(test));
        
        test = new CellRef(1, 1, true, true);
        assertFalse("(1, 1, true, false) -> A1 does not give false", cA1.equalsStrict(test));
        assertTrue("(1, 1, true, false) -> $A$1 does not give true", c$A$1.equalsStrict(test));
        assertFalse("(1, 1, true, false) -> $A1 does not give false", c$A1.equalsStrict(test));
        assertFalse("(1, 1, true, false) -> A$1 does not give false", cA$1.equalsStrict(test));
        
        test = new CellRef(1, 1, true, false);
        assertFalse("(1, 1, true, false) -> A1 does not give false", cA1.equalsStrict(test));
        assertFalse("(1, 1, true, false) -> $A$1 does not give false", c$A$1.equalsStrict(test));
        assertFalse("(1, 1, true, false) -> $A1 does not give false", c$A1.equalsStrict(test));
        assertTrue("(1, 1, true, false) -> A$1 does not give true", cA$1.equalsStrict(test));
        
        test = new CellRef(1, 1, false, true);
        assertFalse("(1, 1, true, false) -> A1 does not give false", cA1.equalsStrict(test));
        assertFalse("(1, 1, true, false) -> $A$1 does not give false", c$A$1.equalsStrict(test));
        assertTrue("(1, 1, true, false) -> $A1 does not give true", c$A1.equalsStrict(test));
        assertFalse("(1, 1, true, false) -> A$1 does not give false", cA$1.equalsStrict(test));
    }
    
    @Test()
    public void testInvalidConstructors() {
        System.out.println("*   test invalid constructors");
        
        testInvalidConstructorsInner("$DEF", "Illegal cell specifier $DEF", "$DEF constructs a valid new cell reference");
        testInvalidConstructorsInner("A1A", "Illegal cell specifier A1A", "A1A constructs a valid new cell reference");
        testInvalidConstructorsInner("A!7", "Illegal cell specifier A!7", "A!7 constructs a valid new cell reference");
        testInvalidConstructorsInner("123", "Illegal cell specifier 123", "123 constructs a valid new cell reference");
        testInvalidConstructorsInner("AS5^", "Illegal cell specifier AS5^", "AS5^ constructs a valid new cell reference");
        testInvalidConstructorsInner("6B", "Illegal cell specifier 6B", "6B constructs a valid new cell reference");
        // TODO: test genuine out of range (e.g. > 1,000,000 & > XFD)
    }
    
    private void testInvalidConstructorsInner (String cell, String errorMsg, String failureMsg) {
        try {
            CellRef temp = new CellRef(cell);
            fail(failureMsg);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), errorMsg);
        }
    }
    
    /**
     * Test of toR1C1 method, of class CellRef.
     */
    @Test
    public void testToR1C1() {
        System.out.println("*   test toR1C1() method");
        
        assertEquals("A1 -> A1 does not give RC", "RC", cA1.toR1C1(cA1).toString());
        assertEquals("A1 -> $A$1 does not give R1C1", "R1C1", c$A$1.toR1C1(cA1).toString());
        assertEquals("A1 -> $A1 does not give RC1", "RC1", c$A1.toR1C1(cA1).toString());
        assertEquals("A1 -> A$1 does not give R1C", "R1C", cA$1.toR1C1(cA1).toString());
        
        assertEquals("A1 -> J1 does not give RC[9]", "RC[9]", cJ1.toR1C1(cA1).toString());
        assertEquals("A1 -> $J$1 does not give R1C10", "R1C10", c$J$1.toR1C1(cA1).toString());
        assertEquals("A1 -> $J1 does not give RC10", "RC10", c$J1.toR1C1(cA1).toString());
        assertEquals("A1 -> J$1 does not give R1C[9]", "R1C[9]", cJ$1.toR1C1(cA1).toString());
        
        assertEquals("AB123 -> J1 does not give R[-122]C[-18]", "R[-122]C[-18]", cJ1.toR1C1(cAB123).toString());
        assertEquals("AB123 -> $J$1 does not give R1C10", "R1C10", c$J$1.toR1C1(cAB123).toString());
        assertEquals("AB123 -> $J1 does not give R[-122]C10", "R[-122]C10", c$J1.toR1C1(cAB123).toString());
        assertEquals("AB123 -> J$1 does not give R1C[-18]", "R1C[-18]", cJ$1.toR1C1(cAB123).toString());
    }

    /**
     * Test of equals method, of class CellRef.
     */
    @Test
    public void testEquals() {
        System.out.println("*   test equals() method");
        
        assertTrue("A1 -> A1 does not give true", cA1.equals(cA1));
        assertTrue("A1 -> $A$1 does not give true", cA1.equals(c$A$1));
        assertTrue("A1 -> $A1 does not give true", cA1.equals(c$A1));
        assertTrue("A1 -> A$1 does not give true", cA1.equals(cA$1));
        
        assertTrue("$A$1 -> A1 does not give true", c$A$1.equals(cA1));
        assertTrue("$A1 -> A1 does not give true", c$A1.equals(cA1));
        assertTrue("A$1 -> A1 does not give true", cA$1.equals(cA1));
        
        assertTrue("$A$1000 -> $A$1000 does not give true", c$A$1000.equals(c$A$1000));
        assertTrue("$DEF542 -> DEF$542 does not give true", c$DEF542.equals(cDEF$542));
        
        assertFalse("A1 -> A2 does not give false", cA1.equals(cA2));
        assertFalse("A1 -> B1 does not give false", cA1.equals(cB1));
        assertFalse("A1 -> B2 does not give false", cA1.equals(cB2));
    }

    /**
     * Test of equalsStrict method, of class CellRef.
     */
    @Test
    public void testEqualsStrict() {
        System.out.println("*   test equalsStrict() method");
        
        assertTrue("A1 -> A1 does not give true", cA1.equalsStrict(cA1));
        assertFalse("A1 -> $A$1 does not give false", cA1.equalsStrict(c$A$1));
        assertFalse("A1 -> $A1 does not give false", cA1.equalsStrict(c$A1));
        assertFalse("A1 -> A$1 does not give false", cA1.equalsStrict(cA$1));
        
        assertFalse("$A$1 -> A1 does not give false", c$A$1.equalsStrict(cA1));
        assertFalse("$A1 -> A1 does not give false", c$A1.equalsStrict(cA1));
        assertFalse("A$1 -> A1 does not give false", cA$1.equalsStrict(cA1));
        
        assertTrue("$A$1000 -> $A$1000 does not give true", c$A$1000.equalsStrict(c$A$1000));
        assertTrue("DEF542 -> DEF542 does not give true", cDEF542.equalsStrict(cDEF542));
        
        assertFalse("A1 -> A2 does not give false", cA1.equalsStrict(cA2));
        assertFalse("A1 -> B1 does not give false", cA1.equalsStrict(cB1));
        assertFalse("A1 -> B2 does not give false", cA1.equalsStrict(cB2));
    }

    /**
     * Test of getCol method, of class CellRef.
     */
    @Test
    public void testGetCol() {
        System.out.println("*   test getCol() method");
        
        assertEquals("A1 does not give 1", 1, cA1.getCol());
        assertEquals("A2 does not give 1", 1, cA2.getCol());
        assertEquals("B1 does not give 2", 2, cB1.getCol());
    }

    /**
     * Test of getRow method, of class CellRef.
     */
    @Test
    public void testGetRow() {
        System.out.println("*   test getRow() method");
        
        assertEquals("A1 does not give 1", 1, cA1.getRow());
        assertEquals("A2 does not give 2", 2, cA2.getRow());
        assertEquals("B1 does not give 1", 1, cB1.getRow());
    }
    
    /**
     * Test of makeAbsolute method, of class CellRef.
     */
    @Test
    public void testMakeAbsolute() {
        System.out.println("*   test makeAbsolute() method");
        
        assertTrue("$A$1 is not equal to A1 absolute", c$A$1.equalsStrict(cA1.makeAbsolute()));
        assertTrue("$A$1 is not equal to $A1 absolute", c$A$1.equalsStrict(c$A1.makeAbsolute()));
        assertTrue("$A$1 is not equal to A$1 absolute", c$A$1.equalsStrict(cA$1.makeAbsolute()));
    }
    
    /**
     * Test of makeRelative method, of class CellRef.
     */
    @Test
    public void testMakeRelative() {
        System.out.println("*   test makeRelative() method");
        
        assertTrue("A1 is not equal to $A$1 relative", cA1.equalsStrict(c$A$1.makeRelative()));
        assertTrue("A1 is not equal to $A1 relative", cA1.equalsStrict(c$A1.makeRelative()));
        assertTrue("A1 is not equal to A$1 relative", cA1.equalsStrict(cA$1.makeRelative()));
    }
    
    /**
     * Test of setAbsolute method, of class CellRef.
     */
    @Test
    public void testSetAbsolute() {
        System.out.println("*   test setAbsolute() & getCopy() methods");
        
        CellRef test = cA1.getCopy();
        assertTrue("A1 is not equal to cloned A1", cA1.equalsStrict(test));
        
        test.setAbsolute(true, true);
        assertFalse("A1 is equal to cloned, absolute A1", cA1.equalsStrict(test));
        assertTrue("$A$1 is not equal to cloned, absolute A1", c$A$1.equalsStrict(test));
        
        test.setAbsolute(true, false);
        assertFalse("A1 is equal to cloned, row absolute A1", cA1.equalsStrict(test));
        assertTrue("A$1 is not equal to cloned, row absolute A1", cA$1.equalsStrict(test));
        
        test.setAbsolute(false, true);
        assertFalse("A1 is equal to cloned, column absolute A1", cA1.equalsStrict(test));
        assertTrue("$A1 is not equal to cloned, column absolute A1", c$A1.equalsStrict(test));
    }

    /**
     * Test of toString method, of class CellRef.
     */
    @Test
    public void testToString() {
        System.out.println("*   test toString() method");
        
        assertEquals("A1 does not give A1", "A1", cA1.toString());
        assertEquals("$A$1 does not give $A$1", "$A$1", c$A$1.toString());
        assertEquals("$A1 does not give $A1", "$A1", c$A1.toString());
        assertEquals("A$1 does not give A$1", "A$1", cA$1.toString());
        
        assertEquals("A1000 does not give A1000", "A1000", cA1000.toString());
        assertEquals("$A$1000 does not give $A$1000", "$A$1000", c$A$1000.toString());
        
        assertEquals("DEF542 does not give DEF542", "DEF542", cDEF542.toString());
        assertEquals("$DEF542 does not give $DEF542", "$DEF542", c$DEF542.toString());
        assertEquals("DEF$542 does not give DEF$542", "DEF$542", cDEF$542.toString());
    }

    /**
     * Test of getAsKey method, of class CellRef.
     */
    @Test
    public void testGetAsKey() {
        System.out.println("*   test getAsKey() method");
        
        assertEquals("A1 does not give 1-1", "1-1", cA1.getAsKey());
        assertEquals("$A$1 does not give 1-1", "1-1", c$A$1.getAsKey());
        assertEquals("$A1 does not give 1-1", "1-1", c$A1.getAsKey());
        assertEquals("A$1 does not give 1-1", "1-1", cA$1.getAsKey());
        
        assertEquals("A1000 does not give 1000-1", "1000-1", cA1000.getAsKey());
        assertEquals("$A$1000 does not give 1000-1", "1000-1", c$A$1000.getAsKey());
        
        assertEquals("DEF542 does not give 542-2840", "542-2840", cDEF542.getAsKey());
        assertEquals("$DEF542 does not give 542-2840", "542-2840", c$DEF542.getAsKey());
        assertEquals("DEF$542 does not give 542-2840", "542-2840", cDEF$542.getAsKey());
    }
    
    /**
     * Test of move method, of class CellRef.
     */
    @Test
    public void testMove() {
        System.out.println("*   test move() method");
        
        assertTrue("A1 moved (0, 0) does not give A1", cA1.move(0, 0).equals(cA1));
        assertTrue("A1 moved (1, 0) does not give A2", cA1.move(1, 0).equals(cA2));
        assertTrue("A1 moved (0, 1) does not give B1", cA1.move(0, 1).equals(cB1));
        assertTrue("A1 moved (1, 1) does not give B2", cA1.move(1, 1).equals(cB2));
        
        assertTrue("$A$1 moved (0, 0) does not give A1", c$A$1.move(0, 0).equals(cA1));
        assertTrue("$A$1 moved (1, 0) does not give A1", c$A$1.move(1, 0).equals(cA1));
        assertTrue("$A$1 moved (0, 1) does not give A1", c$A$1.move(0, 1).equals(cA1));
        assertTrue("$A$1 moved (1, 1) does not give A1", c$A$1.move(1, 1).equals(cA1));
        
        assertTrue("$A1 moved (0, 0) does not give A1", c$A1.move(0, 0).equals(cA1));
        assertTrue("$A1 moved (1, 0) does not give A2", c$A1.move(1, 0).equals(cA2));
        assertTrue("$A1 moved (0, 1) does not give A1", c$A1.move(0, 1).equals(cA1));
        assertTrue("$A1 moved (1, 1) does not give A2", c$A1.move(1, 1).equals(cA2));
        
        assertTrue("A$1 moved (0, 0) does not give A1", cA$1.move(0, 0).equals(cA1));
        assertTrue("A$1 moved (1, 0) does not give A1", cA$1.move(1, 0).equals(cA1));
        assertTrue("A$1 moved (0, 1) does not give B1", cA$1.move(0, 1).equals(cB1));
        assertTrue("A$1 moved (1, 1) does not give B1", cA$1.move(1, 1).equals(cB1));

    }
    
    /**
     * Test of getRowAbs method, of class CellRef.
     */
    @Test
    public void testGetRowColAbs() {
        System.out.println("*   test getRowAbs() & getColAbs() methods");
        
        assertFalse("A1 should not have an absolute row", cA1.getRowAbs());
        assertFalse("A1 should not have an absolute column", cA1.getColAbs());
        
        assertFalse("$A1 should not have an absolute row", c$A1.getRowAbs());
        assertTrue("$A1 should have an absolute column", c$A1.getColAbs());
        
        assertTrue("A$1 should have an absolute row", cA$1.getRowAbs());
        assertFalse("A$1 should not have an absolute column", cA$1.getColAbs());
        
        assertTrue("$A$1 should have an absolute row", c$A$1.getRowAbs());
        assertTrue("$A$1 should have an absolute column", c$A$1.getColAbs());

    }
    
}
