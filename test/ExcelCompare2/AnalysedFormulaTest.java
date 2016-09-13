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
public class AnalysedFormulaTest {
    
    private final CellRef cA1;
    private final CellRef cA2;
    private final CellRef cA3;
    private final CellRef cA4;
    private final Formula f;
    private AnalysedFormula af;
    
    public AnalysedFormulaTest() {
        cA1 = new CellRef("A1");
        cA2 = new CellRef("A2");
        cA3 = new CellRef("A3");
        cA4 = new CellRef("A4");
        f = new Formula("=B1", cA1, "1");
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("   test AnalysedFormula class");
        System.out.println("=========================");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("");
    }
    
    @Before
    public void setUp() {
        af = new AnalysedFormula(f);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addCell method, of class AnalysedFormula.
     */
    @Test
    public void testAddCell() {
        System.out.println("*   test addCell(), getUnanalysed() & isAnalysed() methods");
        
        CompoundRange expected = new CompoundRange(cA1);
        
        // Test the base Analysed forumla and make sure it's only got A1 in there
        assertTrue("Was expecing A1 as the only unanalysed cell", af.getUnanalysed().equals(expected));
        
        // Test adding same cell twice
        af.addCell(cA1);
        assertEquals("Was expecing only 1 unanalysed cell", 1, af.getUnanalysed().size());
        assertTrue("Was expecing A1 as the only unanalysed cell", af.getUnanalysed().equals(expected));
        
        // Test is analysed
        af.setAnalysed(cA1);
        af.addCell(cA1);
        assertTrue("Was expecing no unanalysed cells", af.getUnanalysed().isEmpty());
        assertTrue("Was expecing object to be analysed", af.isAnalysed());
        
        // Test the base Analysed forumla and make sure it's only got A1 in there
        expected = new CompoundRange(cA2);
        af.addCell(cA2);
        assertFalse("Was expecing object to be not analysed", af.isAnalysed());
        assertTrue("Was expecing A2 as the only unanalysed cell", af.getUnanalysed().equals(expected));
        
    }

    /**
     * Test of setAnalysed method, of class AnalysedFormula.
     */
    @Test
    public void testSetAnalysed_CellRef() {
        System.out.println("*   test setAnalysed(CellRef), getUnanalysed() & isAnalysed() methods");
        
        CompoundRange expected = new CompoundRange(cA1);
        
        // Test setting analysed a cell that is not part of af
        af.setAnalysed(cA2);
        assertTrue("Was expecing A1 as the only unanalysed cell", af.getUnanalysed().equals(expected));
        assertFalse("Was expecing object to be not analysed", af.isAnalysed());
        
        // Add a cell and make sure everything looks OK
        af.addCell(cA2);
        expected.addCell(cA2);
        assertTrue("Was expecing A1, A2 as the only unanalysed cells", af.getUnanalysed().equals(expected));
        assertFalse("Was expecing object to be not analysed", af.isAnalysed());
        
        // Analyse one cell and make sure everything is OK
        af.setAnalysed(cA2);
        expected = new CompoundRange(cA1);
        assertTrue("Was expecing A1 as the only unanalysed cell", af.getUnanalysed().equals(expected));
        assertFalse("Was expecing object to be not analysed", af.isAnalysed());
        
        // Analyse the other cell and make sure everything is OK
        af.setAnalysed(cA1);
        assertTrue("Was expecing no unanalysed cells", af.getUnanalysed().isEmpty());
        assertTrue("Was expecing object to be analysed", af.isAnalysed());
    }

    /**
     * Test of setAnalysed method, of class AnalysedFormula.
     */
    @Test
    public void testSetAnalysed_CompoundRange() {
        System.out.println("*   test setAnalysed(CompoundRange), getUnanalysed() & isAnalysed() methods");
        
        CompoundRange test = new CompoundRange(cA2);
        test.addCell(cA3);
        
        CompoundRange expected = new CompoundRange(cA1);
        
        // Test setting analysed a range that is not part of af
        af.setAnalysed(test);
        assertTrue("Was expecing A1 as the only unanalysed cell", af.getUnanalysed().equals(expected));
        assertFalse("Was expecing object to be not analysed", af.isAnalysed());
        
        // Test setting analysed a range that is only partially in af
        af.addCell(cA2);
        af.setAnalysed(test);
        assertTrue("Was expecing A1 as the only unanalysed cell", af.getUnanalysed().equals(expected));
        assertFalse("Was expecing object to be not analysed", af.isAnalysed());
        
        // Add another cell and make sure everything looks OK
        af.addCell(cA3);
        expected.addCell(cA3);
        assertTrue("Was expecing A1, A3 as the only unanalysed cells", af.getUnanalysed().equals(expected));
        assertFalse("Was expecing object to be not analysed", af.isAnalysed());
        
        // Analyse one cell and make sure everything is OK
        af.setAnalysed(test);
        expected = new CompoundRange(cA1);
        assertTrue("Was expecing A1 as the only unanalysed cell", af.getUnanalysed().equals(expected));
        assertFalse("Was expecing object to be not analysed", af.isAnalysed());
        
        // Expand again (A4) and then analyse everything should get 
        af.addCell(cA4);
        test.addCell(cA1);
        test.addCell(cA4);
        af.setAnalysed(test);
        assertTrue("Was expecing A1 as the only unanalysed cell", af.getUnanalysed().isEmpty());
        assertTrue("Was expecing object to be analysed", af.isAnalysed());
    }
    
    /**
     * Test of isAnalysed method, of class AnalysedFormula.
     */
    @Test
    public void testIsAnalysed_CellRef() {
        System.out.println("*   test isAnalysed(CellRef) method");
        
        CompoundRange test = new CompoundRange(cA2);
        test.addCell(cA3);
        
        af.addCell(cA2);
        af.addCell(cA3);
        af.addCell(cA4);
        
        // Test setting analysed a range that is not part of af
        assertFalse("Was expecing A1 to be not analysed", af.isAnalysed(cA1));
        assertFalse("Was expecing A2 to be not analysed", af.isAnalysed(cA2));
        assertFalse("Was expecing A3 to be not analysed", af.isAnalysed(new CellRef("A3")));
        assertFalse("Was expecing A4 to be not analysed", af.isAnalysed(new CellRef("A4")));
        
        // Test setting analysed a range that is only partially in af
        af.setAnalysed(test);
        assertFalse("Was expecing A1 to be not analysed", af.isAnalysed(cA1));
        assertTrue("Was expecing A2 to be analysed", af.isAnalysed(cA2));
        assertTrue("Was expecing A3 to be analysed", af.isAnalysed(new CellRef("A3")));
        assertFalse("Was expecing A4 to be not analysed", af.isAnalysed(new CellRef("A4")));
    }
    
}
