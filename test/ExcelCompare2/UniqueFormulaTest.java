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
public class UniqueFormulaTest {
    
    private final CellRef cA1;
    private final CellRef cA2;
    private final Formula f;
    private final UniqueFormula uf;
    
    public UniqueFormulaTest() {
        cA1 = new CellRef("A1");
        cA2 = new CellRef("A2");
        f = new Formula("=B1", cA1);
        uf = new UniqueFormula(f);
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("   test UniqueFormula class");
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
     * Test of compareTo method, of class UniqueFormula.
     */
    @Test
    public void testCompareTo_String() {
        System.out.println("*   test CompareTo(String) method");
        
        assertEquals("'=RC[1]' is not the same formula as '=B1' in cell 'A1'", 0, uf.compareTo("=RC[1]"));
        assertTrue("'=RC[2]' is not a smaller formula than '=B1' in cell 'A1'", (uf.compareTo("=RC[2]") < 0));
        assertTrue("'=RC' is not a larger formula than '=B1' in cell 'A1'", (uf.compareTo("=RC") > 0));
    }

    /**
     * Test of compareTo method, of class UniqueFormula.
     */
    @Test
    public void testCompareTo_Formula() {
        System.out.println("*   test CompareTo(Formula) method");
        
        Formula g = new Formula("=F3", new CellRef("E3")); // =RC[1]
        assertEquals("'=F3' in cell 'E3' is not the same formula as '=B1' in cell 'A1'", 0, uf.compareTo(g));
        
        g = new Formula("=BB36", new CellRef("AZ36")); // =RC[2]
        assertTrue("'=BB36' in cell 'AZ36' is not a smaller formula than '=B1' in cell 'A1'", (uf.compareTo(g) < 0));
        
        g = new Formula("=DEF3504", new CellRef("DEF3504")); // =RC
        assertTrue("'=DEF3504' in cell 'DEF3504' is not a larger formula than '=B1' in cell 'A1'", (uf.compareTo(g) > 0));
    }

    /**
     * Test of compareTo method, of class UniqueFormula.
     */
    @Test
    public void testCompareTo_UniqueFormula() {
        System.out.println("*   test CompareTo(UniqueFormula) method");
        
        UniqueFormula g = new UniqueFormula(new Formula("=F3", new CellRef("E3"))); // =RC[1]
        assertEquals("'=F3' in cell 'E3' is not the same formula as '=B1' in cell 'A1'", 0, uf.compareTo(g));
        g.addCell(cA1);
        assertEquals("'=F3' in cell 'E3' is not the same formula as '=B1' in cell 'A1'", 0, uf.compareTo(g));
        
        g = new UniqueFormula(new Formula("=BB36", new CellRef("AZ36"))); // =RC[2]
        assertTrue("'=BB36' in cell 'AZ36' is not a smaller formula than '=B1' in cell 'A1'", (uf.compareTo(g) < 0));
        
        g = new UniqueFormula(new Formula("=DEF3504", new CellRef("DEF3504"))); // =RC
        assertTrue("'=DEF3504' in cell 'DEF3504' is not a larger formula than '=B1' in cell 'A1'", (uf.compareTo(g) > 0));
    }

    /**
     * Test of addCell method, of class UniqueFormula.
     */
    @Test
    public void testAddCell() {
        System.out.println("*   test addCell() and getRange() methods");
        
        assertFalse("A2 should not be part of the unique formula yet", uf.getRange().contains(cA2));
        
        uf.addCell(cA2);
        assertTrue("A2 should now be part of the unique formula", uf.getRange().contains(cA2));
    }

    /**
     * Test of getFormula method, of class UniqueFormula.
     */
    @Test
    public void testGetFormula() {
        System.out.println("*   test getFormula() method");
        assertEquals("'=B1' is not the formula of the unique formula", "=B1", uf.getFormula().getA1());
    }

    /**
     * Test of getFormulaR1C1 method, of class UniqueFormula.
     */
    @Test
    public void testGetFormulaR1C1() {
        System.out.println("*   test getFormulaR1C1() method");
        assertEquals("'=RC[1]' is not the formula of the unique formula", "=RC[1]", uf.getFormula().getR1C1());
    }

    
}
