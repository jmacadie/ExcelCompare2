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

import java.util.List;
import java.util.ArrayList;
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
    
    private final CellRef cA1;
    private final CellRef cA2;
    private final CellRef cB1;
    private final CellRef cB2;
    private final CellRef cAA32;
    private final CondensedFormulae cf;
    
    public CondensedFormulaeTest() {
        cA1 = new CellRef("A1");
        cA2 = new CellRef("A2");
        cB1 = new CellRef("B1");
        cB2 = new CellRef("B2");
        cAA32 = new CellRef("AA32");
        
        List<Formula> f = new ArrayList<> ();
        f.add(new Formula("=B1", cA1));
        f.add(new Formula("=C1", cB1));
        f.add(new Formula("=A3", cA2));
        f.add(new Formula("=B3", cB2));
        cf = new CondensedFormulae(f);
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("   test CondensedFormulae class");
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
     * Test of findFormula method, of class CondensedFormulae.
     */
    @Test
    public void testFindFormula() {
        System.out.println("*   test findFormula() method");
        
        AnalysedFormula af;
        CompoundRange expected;
        
        // Find matching instance (in range)
        af = new AnalysedFormula(new Formula("=B1", cA1));
        expected = new CompoundRange(cA1);
        expected.addCell(cB1);
        assertTrue("Matching refence (in range)", cf.findFormula(af).equals(expected));
        
        // Find matching instance (out of range)
        af = new AnalysedFormula(new Formula("=AB32", cAA32));
        assertTrue("Matching refence (out of range)", cf.findFormula(af).equals(expected));
        
        // Find the other matching instance
        af = new AnalysedFormula(new Formula("=AA33", cAA32));
        expected = new CompoundRange(cA2);
        expected.addCell(cB2);
        assertTrue("The other matching instance", cf.findFormula(af).equals(expected));
        
        // Find non-matching instance => returns null
        af = new AnalysedFormula(new Formula("=AB37", cAA32));
        assertEquals("Non-matching instance", null, cf.findFormula(af));
    }

    /**
     * Test of getForumla method, of class CondensedFormulae.
     */
    @Test
    public void testGetForumla() {
        System.out.println("*   test getForumla() method");
        
        CellRef cell;
        AnalysedFormula expected;
        
        // Find matching instance
        cell = new CellRef("A1");
        expected = new AnalysedFormula(new Formula("=B1", cA1));
        assertEquals("Matching refence", expected.getFormula().getA1(), cf.getForumla(cell).getFormula().getA1());
        assertEquals("Matching refence R1C1", expected.getFormula().getR1C1(), cf.getForumla(cell).getFormula().getR1C1());
        
        // Find 2nd matching instance
        cell = new CellRef("B1");
        expected = new AnalysedFormula(new Formula("=C1", cB1));
        assertEquals("2nd matching refence", expected.getFormula().getR1C1(), cf.getForumla(cell).getFormula().getR1C1());
        
        // Find the other matching instance
        cell = new CellRef("B2");
        expected = new AnalysedFormula(new Formula("=A3", cA2));
        assertEquals("The other matching refence", expected.getFormula().getA1(), cf.getForumla(cell).getFormula().getA1());
        assertEquals("The other matching refence R1C1", expected.getFormula().getR1C1(), cf.getForumla(cell).getFormula().getR1C1());
        
        // Find non-matching instance => returns null
        assertEquals("Non-matching instance", null, cf.getForumla(cAA32));
    }

    /**
     * Test of diff method, of class CondensedFormulae.
     */
    @Test
    public void testDiff() {
        System.out.println("*   test diff() method");
        
        // Not sure how to test output to stdout?
    }
    
}
