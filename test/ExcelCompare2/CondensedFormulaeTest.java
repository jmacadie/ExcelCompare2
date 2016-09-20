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
    List<Formula> f;
    List<Formula> f2;
    private CondensedFormulae cf;
    private CondensedFormulae cf2;
    
    public CondensedFormulaeTest() {
        cA1 = new CellRef("A1");
        cA2 = new CellRef("A2");
        cB1 = new CellRef("B1");
        cB2 = new CellRef("B2");
        cAA32 = new CellRef("AA32");
        
        f = new ArrayList<> ();
        f.add(new Formula("=B1", cA1, "1"));
        f.add(new Formula("=C1", cB1, "2"));
        f.add(new Formula("=A3", cA2, "3"));
        f.add(new Formula("=B3", cB2, "4"));
        cf = new CondensedFormulae(f);
        
        f2 = new ArrayList<> ();
        f2.add(new Formula("=B3", cB2, "4"));
        f2.add(new Formula("=A3", cA2, "3"));
        f2.add(new Formula("=C1", cB1, "2"));
        f2.add(new Formula("=B1", cA1, "1"));
        cf2 = new CondensedFormulae(f2);
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
        cf = new CondensedFormulae(f);
        cf2 = new CondensedFormulae(f2);
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
        af = new AnalysedFormula(new Formula("=B1", cA1, "1"));
        expected = new CompoundRange(cA1);
        expected.addCell(cB1);
        assertTrue("Matching refence (in range)", cf.findFormula(af).equals(expected));
        
        // Find matching instance (out of range)
        af = new AnalysedFormula(new Formula("=AB32", cAA32, "32"));
        assertTrue("Matching refence (out of range)", cf.findFormula(af).equals(expected));
        
        // Find the other matching instance
        af = new AnalysedFormula(new Formula("=AA33", cAA32, "33"));
        expected = new CompoundRange(cA2);
        expected.addCell(cB2);
        assertTrue("The other matching instance", cf.findFormula(af).equals(expected));
        
        // Find non-matching instance => returns null
        af = new AnalysedFormula(new Formula("=AB37", cAA32, "37"));
        assertEquals("Non-matching instance", null, cf.findFormula(af));
        
        // Find non-matching instance => returns null
        // Should quit out part way through list
        af = new AnalysedFormula(new Formula("=AA32", cAA32, "37"));
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
        expected = new AnalysedFormula(new Formula("=B1", cA1, "45"));
        assertEquals("Matching refence", expected.getFormula().getA1(), cf.getForumla(cell).getFormula().getA1());
        assertEquals("Matching refence R1C1", expected.getFormula().getR1C1(), cf.getForumla(cell).getFormula().getR1C1());
        
        // Find 2nd matching instance
        cell = new CellRef("B1");
        expected = new AnalysedFormula(new Formula("=C1", cB1, "27 Dec 2016"));
        assertEquals("2nd matching refence", expected.getFormula().getR1C1(), cf.getForumla(cell).getFormula().getR1C1());
        
        // Find the other matching instance
        cell = new CellRef("B2");
        expected = new AnalysedFormula(new Formula("=A3", cA2, "5%"));
        assertEquals("The other matching refence", expected.getFormula().getA1(), cf.getForumla(cell).getFormula().getA1());
        assertEquals("The other matching refence R1C1", expected.getFormula().getR1C1(), cf.getForumla(cell).getFormula().getR1C1());
        
        // Find non-matching instance => returns null
        assertEquals("Non-matching instance", null, cf.getForumla(cAA32));
    }

    /**
     * Test of getMaxRows method, of class CondensedFormulae.
     */
    @Test
    public void testGetMaxRows() {
        System.out.println("*   test getMaxRows() method");
        
        assertEquals("Condensed Formula doen't have 2 rows", 2, cf.getMaxRows());
        // Do again with the re-ordered CondensedFormulae
        assertEquals("Condensed Formula doen't have 2 rows", 2, cf2.getMaxRows());
    }

    /**
     * Test of getMaxCols method, of class CondensedFormulae.
     */
    @Test
    public void testGetMaxCols() {
        System.out.println("*   test getMaxCols() method");
        
        assertEquals("Condensed Formula doen't have 2 columns", 2, cf.getMaxCols());
        // Do again with the re-ordered CondensedFormulae
        assertEquals("Condensed Formula doen't have 2 columns", 2, cf2.getMaxCols());
    }

    /**
     * Test of findCell method, of class CondensedFormulae.
     */
    @Test
    public void testFindCell() {
        System.out.println("*   test findCell() method");
        
        assertTrue("Can't find A1", cf.findCell(cA1));
        assertTrue("Can't find A1 (re-created)", cf.findCell(new CellRef("A1")));
        
        assertFalse("Can find J10", cf.findCell(new CellRef("J10")));
        
        // Do again with the re-ordered CondensedFormulae
        assertTrue("Can't find A1", cf2.findCell(cA1));
        assertTrue("Can't find A1 (re-created)", cf2.findCell(new CellRef("A1")));
        
        assertFalse("Can find J10", cf2.findCell(new CellRef("J10")));
    }

    /**
     * Test of setAnalysed method, of class CondensedFormulae.
     */
    @Test
    public void testSetAnalysed_CellRef() {
        System.out.println("*   test setAnalysed(CellRef) method");
        
        assertFalse("A1 is analysed", cf.isAnalysed(cA1));
        
        cf.setAnalysed(cA1);
        assertTrue("A1 isn't analysed", cf.isAnalysed(cA1));
        
        cf.setAnalysed(cAA32);
        assertFalse("AA32 is analysed", cf.isAnalysed(cAA32));
        
        // Do again with the re-ordered CondensedFormulae
        assertFalse("A1 is analysed", cf2.isAnalysed(cA1));
        
        cf2.setAnalysed(cA1);
        assertTrue("A1 isn't analysed", cf2.isAnalysed(cA1));
        
        cf2.setAnalysed(cAA32);
        assertFalse("AA32 is analysed", cf2.isAnalysed(cAA32));
        
    }

    /**
     * Test of setAnalysed method, of class CondensedFormulae.
     */
    @Test
    public void testSetAnalysed_CompoundRange() {
        System.out.println("*   test setAnalysed(CompoundRange) method");
        
        CompoundRange test = new CompoundRange();
        test.addCell(cB1);
        test.addCell(cB2);
        
        assertFalse("B1 is analysed", cf.isAnalysed(cB1));
        assertFalse("B2 is analysed", cf.isAnalysed(cB2));
        
        cf.setAnalysed(test);
        assertTrue("B1 isn't analysed", cf.isAnalysed(cB1));
        assertTrue("B2 isn't analysed", cf.isAnalysed(cB2));
        
        // Do again with the re-ordered CondensedFormulae
        assertFalse("B1 is analysed", cf2.isAnalysed(cB1));
        assertFalse("B2 is analysed", cf2.isAnalysed(cB2));
        
        cf2.setAnalysed(test);
        assertTrue("B1 isn't analysed", cf2.isAnalysed(cB1));
        assertTrue("B2 isn't analysed", cf2.isAnalysed(cB2));
    }

    /**
     * Test of isAnalysed method, of class CondensedFormulae.
     */
    @Test
    public void testIsAnalysed() {
        System.out.println("*   test isAnalysed() method");
        
        assertFalse("A1 is analysed", cf.isAnalysed(cA1));
        assertFalse("A1 is analysed", cf.isAnalysed(cAA32));
        
        cf.setAnalysed(cA1);
        assertTrue("A1 is not analysed", cf.isAnalysed(cA1));
        
        // Do again with the re-ordered CondensedFormulae
        assertFalse("A1 is analysed", cf2.isAnalysed(cA1));
        assertFalse("A1 is analysed", cf2.isAnalysed(cAA32));
        
        cf2.setAnalysed(cA1);
        assertTrue("A1 is not analysed", cf2.isAnalysed(cA1));
    }

    /**
     * Test of getRow method, of class CondensedFormulae.
     */
    @Test
    public void testGetRow() {
        System.out.println("*   test getRow() method");
        
        CondensedFormulae cfRow;
        
        // Get row out of range: too small
        cfRow = cf.getRow(0);
        assertEquals("Biggest row of extracted row out of range is not zero", 0, cfRow.getMaxRows());
        assertEquals("Biggest column of extracted row out of range is not zero", 0, cfRow.getMaxCols());
        assertFalse("Can find cell A1 in extracted row out of range", cfRow.findCell(cA1));
        assertFalse("Can find cell A2 in extracted row out of range", cfRow.findCell(cA2));
        assertFalse("Can find cell B1 in extracted row out of range", cfRow.findCell(cB1));
        assertFalse("Can find cell B2 in extracted row out of range", cfRow.findCell(cB2));
        assertFalse("Can find cell AA32 in extracted row out of range", cfRow.findCell(cAA32));
        assertFalse("Next formula in extracted row out of range", cfRow.listIterator().hasNext());
        assertFalse("Previous formula in extracted row out of range", cfRow.listIterator().hasPrevious());
        
        // Get row out of range: too big
        cfRow = cf.getRow(3);
        assertEquals("Biggest row of extracted row out of range is not zero", 0, cfRow.getMaxRows());
        assertEquals("Biggest column of extracted row out of range is not zero", 0, cfRow.getMaxCols());
        assertFalse("Can find cell A1 in extracted row out of range", cfRow.findCell(cA1));
        assertFalse("Can find cell A2 in extracted row out of range", cfRow.findCell(cA2));
        assertFalse("Can find cell B1 in extracted row out of range", cfRow.findCell(cB1));
        assertFalse("Can find cell B2 in extracted row out of range", cfRow.findCell(cB2));
        assertFalse("Can find cell AA32 in extracted row out of range", cfRow.findCell(cAA32));
        assertFalse("Next formula in extracted row out of range", cfRow.listIterator().hasNext());
        assertFalse("Previous formula in extracted row out of range", cfRow.listIterator().hasPrevious());
        
        // Get 1st row
        cfRow = cf.getRow(1);
        assertEquals("Biggest row of extracted 1st row is not 1", 1, cfRow.getMaxRows());
        assertEquals("Biggest column of extracted 1st row is not 2", 2, cfRow.getMaxCols());
        assertTrue("Can't find cell A1 in extracted 1st row", cfRow.findCell(cA1));
        assertFalse("Can find cell A2 in extracted 1st row", cfRow.findCell(cA2));
        assertTrue("Can't find cell B1 in extracted 1st row", cfRow.findCell(cB1));
        assertFalse("Can find cell B2 in extracted 1st row", cfRow.findCell(cB2));
        assertFalse("Can find cell AA32 in extracted 1st row", cfRow.findCell(cAA32));
        assertTrue("No next formula in extracted 1st row", cfRow.listIterator().hasNext());
        
        // Get 1st row again: test memoised cache
        cfRow = cf.getRow(1);
        assertEquals("Biggest row of extracted 1st row (again) is not 1", 1, cfRow.getMaxRows());
        assertEquals("Biggest column of extracted 1st row (again) is not 2", 2, cfRow.getMaxCols());
        assertTrue("Can't find cell A1 in extracted 1st row (again) ", cfRow.findCell(cA1));
        assertFalse("Can find cell A2 in extracted 1st row (again)", cfRow.findCell(cA2));
        assertTrue("Can't find cell B1 in extracted 1st row (again)", cfRow.findCell(cB1));
        assertFalse("Can find cell B2 in extracted 1st row (again)", cfRow.findCell(cB2));
        assertFalse("Can find cell AA32 in extracted 1st row (again)", cfRow.findCell(cAA32));
        assertTrue("No next formula in extracted 1st row", cfRow.listIterator().hasNext());
        
        // Get 2nd row
        cfRow = cf.getRow(2);
        assertEquals("Biggest row of extracted 2nd row is not 2", 2, cfRow.getMaxRows());
        assertEquals("Biggest column of extracted 2nd row is not 2", 2, cfRow.getMaxCols());
        assertFalse("Can find cell A1 in extracted 2nd row", cfRow.findCell(cA1));
        assertTrue("Can't find cell A2 in extracted 2nd row", cfRow.findCell(cA2));
        assertFalse("Can find cell B1 in extracted 2nd row", cfRow.findCell(cB1));
        assertTrue("Can't find cell B2 in extracted 2nd row", cfRow.findCell(cB2));
        assertFalse("Can find cell AA32 in extracted 2nd row", cfRow.findCell(cAA32));
        assertTrue("No next formula in extracted 2nd row", cfRow.listIterator().hasNext());
    }

    /**
     * Test of getColumn method, of class CondensedFormulae.
     */
    @Test
    public void testGetColumn() {
        System.out.println("*   test getColumn() method");
        
        CondensedFormulae cfCol;
        
        // Get row out of range: too small
        cfCol = cf.getColumn(0);
        assertEquals("Biggest row of extracted column out of range is not zero", 0, cfCol.getMaxRows());
        assertEquals("Biggest column of extracted column out of range is not zero", 0, cfCol.getMaxCols());
        assertFalse("Can find cell A1 in extracted column out of range", cfCol.findCell(cA1));
        assertFalse("Can find cell A2 in extracted column out of range", cfCol.findCell(cA2));
        assertFalse("Can find cell B1 in extracted column out of range", cfCol.findCell(cB1));
        assertFalse("Can find cell B2 in extracted column out of range", cfCol.findCell(cB2));
        assertFalse("Can find cell AA32 in extracted column out of range", cfCol.findCell(cAA32));
        assertFalse("Next formula in extracted column out of range", cfCol.listIterator().hasNext());
        assertFalse("Previous formula in extracted column out of range", cfCol.listIterator().hasPrevious());
        
        // Get row out of range: too big
        cfCol = cf.getColumn(3);
        assertEquals("Biggest row of extracted column out of range is not zero", 0, cfCol.getMaxRows());
        assertEquals("Biggest column of extracted column out of range is not zero", 0, cfCol.getMaxCols());
        assertFalse("Can find cell A1 in extracted column out of range", cfCol.findCell(cA1));
        assertFalse("Can find cell A2 in extracted column out of range", cfCol.findCell(cA2));
        assertFalse("Can find cell B1 in extracted column out of range", cfCol.findCell(cB1));
        assertFalse("Can find cell B2 in extracted column out of range", cfCol.findCell(cB2));
        assertFalse("Can find cell AA32 in extracted column out of range", cfCol.findCell(cAA32));
        assertFalse("Next formula in extracted column out of range", cfCol.listIterator().hasNext());
        assertFalse("Previous formula in extracted column out of range", cfCol.listIterator().hasPrevious());
        
        // Get 1st row
        cfCol = cf.getColumn(1);
        assertEquals("Biggest row of extracted 1st column is not 2", 2, cfCol.getMaxRows());
        assertEquals("Biggest column of extracted 1st column is not 1", 1, cfCol.getMaxCols());
        assertTrue("Can't find cell A1 in extracted 1st column", cfCol.findCell(cA1));
        assertTrue("Can't find cell A2 in extracted 1st column", cfCol.findCell(cA2));
        assertFalse("Can find cell B1 in extracted 1st column", cfCol.findCell(cB1));
        assertFalse("Can find cell B2 in extracted 1st column", cfCol.findCell(cB2));
        assertFalse("Can find cell AA32 in extracted 1st column", cfCol.findCell(cAA32));
        assertTrue("No next formula in extracted 1st column", cfCol.listIterator().hasNext());
        
        // Get 1st row again: test memoised cache
        cfCol = cf.getColumn(1);
        assertEquals("Biggest row of extracted 1st column (again) is not 2", 2, cfCol.getMaxRows());
        assertEquals("Biggest column of extracted 1st column (again) is not 1", 1, cfCol.getMaxCols());
        assertTrue("Can't find cell A1 in extracted 1st column (again) ", cfCol.findCell(cA1));
        assertTrue("Can't find cell A2 in extracted 1st column (again)", cfCol.findCell(cA2));
        assertFalse("Can find cell B1 in extracted 1st column (again)", cfCol.findCell(cB1));
        assertFalse("Can find cell B2 in extracted 1st column (again)", cfCol.findCell(cB2));
        assertFalse("Can find cell AA32 in extracted 1st column (again)", cfCol.findCell(cAA32));
        assertTrue("No next formula in extracted 1st column", cfCol.listIterator().hasNext());
        
        // Get 2nd row
        cfCol = cf.getColumn(2);
        assertEquals("Biggest row of extracted 2nd column is not 2", 2, cfCol.getMaxRows());
        assertEquals("Biggest column of extracted 2nd column is not 2", 2, cfCol.getMaxCols());
        assertFalse("Can find cell A1 in extracted 2nd column", cfCol.findCell(cA1));
        assertFalse("Can find cell A2 in extracted 2nd column", cfCol.findCell(cA2));
        assertTrue("Can't find cell B1 in extracted 2nd column", cfCol.findCell(cB1));
        assertTrue("Can't find cell B2 in extracted 2nd column", cfCol.findCell(cB2));
        assertFalse("Can find cell AA32 in extracted 2nd column", cfCol.findCell(cAA32));
        assertTrue("No next formula in extracted 2nd column", cfCol.listIterator().hasNext());
    }
    
}
