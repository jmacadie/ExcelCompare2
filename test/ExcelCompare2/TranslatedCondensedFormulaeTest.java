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

import java.util.ArrayList;
import java.util.List;
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
public class TranslatedCondensedFormulaeTest {
    
    private final CellTranslations trans;
    private final CellTranslations transSame;
    private final TranslatedCondensedFormulae tcf;
    private final TranslatedCondensedFormulae tcfSame;
    
    public TranslatedCondensedFormulaeTest() {
        CondensedFormulae before = generateFrom();
        CondensedFormulae after = generateRowTo();
        
        trans = new CellTranslations(before, after);
        tcf = new TranslatedCondensedFormulae(before, trans);
        
        transSame = new CellTranslations(before, before);
        tcfSame = new TranslatedCondensedFormulae(before, transSame);
    }
    
    private CondensedFormulae generateFrom() {
        
        List<Formula> f = new ArrayList<> ();
        
        f.add(new Formula("1", new CellRef("A1"), "1"));
        f.add(new Formula("=A1", new CellRef("B1"), "1"));
        f.add(new Formula("=B1", new CellRef("C1"), "1"));
        f.add(new Formula("=C1", new CellRef("D1"), "1"));
        f.add(new Formula("=D1", new CellRef("E1"), "1"));
        f.add(new Formula("=E1", new CellRef("F1"), "1"));
        f.add(new Formula("=F1", new CellRef("G1"), "1"));
        
        f.add(new Formula("2", new CellRef("A2"), "2"));
        f.add(new Formula("=A2", new CellRef("B2"), "2"));
        f.add(new Formula("=B2", new CellRef("C2"), "2"));
        f.add(new Formula("=C2", new CellRef("D2"), "2"));
        f.add(new Formula("=D2", new CellRef("E2"), "2"));
        f.add(new Formula("=E2", new CellRef("F2"), "2"));
        f.add(new Formula("=F2", new CellRef("G2"), "2"));
        
        f.add(new Formula("3", new CellRef("A3"), "3"));
        f.add(new Formula("=A3", new CellRef("B3"), "3"));
        f.add(new Formula("=B3", new CellRef("C3"), "3"));
        f.add(new Formula("=C3", new CellRef("D3"), "3"));
        f.add(new Formula("=D3", new CellRef("E3"), "3"));
        f.add(new Formula("=E3", new CellRef("F3"), "3"));
        f.add(new Formula("=F3", new CellRef("G3"), "3"));
        
        f.add(new Formula("4", new CellRef("A4"), "4"));
        f.add(new Formula("=A4", new CellRef("B4"), "4"));
        f.add(new Formula("=B4", new CellRef("C4"), "4"));
        f.add(new Formula("=C4", new CellRef("D4"), "4"));
        f.add(new Formula("=D4", new CellRef("E4"), "4"));
        f.add(new Formula("=E4", new CellRef("F4"), "4"));
        f.add(new Formula("=F4", new CellRef("G4"), "4"));
        
        f.add(new Formula("5", new CellRef("A5"), "5"));
        f.add(new Formula("=A5", new CellRef("B5"), "5"));
        f.add(new Formula("=B5", new CellRef("C5"), "5"));
        f.add(new Formula("=C5", new CellRef("D5"), "5"));
        f.add(new Formula("=D5", new CellRef("E5"), "5"));
        f.add(new Formula("=E5", new CellRef("F5"), "5"));
        f.add(new Formula("=F5", new CellRef("G5"), "5"));
        
        f.add(new Formula("6", new CellRef("A6"), "6"));
        f.add(new Formula("=A6", new CellRef("B6"), "6"));
        f.add(new Formula("=B6", new CellRef("C6"), "6"));
        f.add(new Formula("=C6", new CellRef("D6"), "6"));
        f.add(new Formula("=D6", new CellRef("E6"), "6"));
        f.add(new Formula("=E6", new CellRef("F6"), "6"));
        f.add(new Formula("=F6", new CellRef("G6"), "6"));
        
        f.add(new Formula("7", new CellRef("A7"), "7"));
        f.add(new Formula("=A7", new CellRef("B7"), "7"));
        f.add(new Formula("=B7", new CellRef("C7"), "7"));
        f.add(new Formula("=C7", new CellRef("D7"), "7"));
        f.add(new Formula("=D7", new CellRef("E7"), "7"));
        f.add(new Formula("=E7", new CellRef("F7"), "7"));
        f.add(new Formula("=F7", new CellRef("G7"), "7"));
        
        f.add(new Formula("8", new CellRef("A8"), "8"));
        f.add(new Formula("=A8", new CellRef("B8"), "8"));
        f.add(new Formula("=B8", new CellRef("C8"), "8"));
        f.add(new Formula("=C8", new CellRef("D8"), "8"));
        f.add(new Formula("=D8", new CellRef("E8"), "8"));
        f.add(new Formula("=E8", new CellRef("F8"), "8"));
        f.add(new Formula("=F8", new CellRef("G8"), "8"));
        
        f.add(new Formula("9", new CellRef("A9"), "9"));
        f.add(new Formula("=A9", new CellRef("B9"), "9"));
        f.add(new Formula("=B9", new CellRef("C9"), "9"));
        f.add(new Formula("=C9", new CellRef("D9"), "9"));
        f.add(new Formula("=D9", new CellRef("E9"), "9"));
        f.add(new Formula("=E9", new CellRef("F9"), "9"));
        f.add(new Formula("=F9", new CellRef("G9"), "9"));
        
        f.add(new Formula("10", new CellRef("A10"), "10"));
        f.add(new Formula("=A10", new CellRef("B10"), "10"));
        f.add(new Formula("=B10", new CellRef("C10"), "10"));
        f.add(new Formula("=C10", new CellRef("D10"), "10"));
        f.add(new Formula("=D10", new CellRef("E10"), "10"));
        f.add(new Formula("=E10", new CellRef("F10"), "10"));
        f.add(new Formula("=F10", new CellRef("G10"), "10"));
        
        f.add(new Formula("11", new CellRef("A11"), "11"));
        f.add(new Formula("=A11", new CellRef("B11"), "11"));
        f.add(new Formula("=B11", new CellRef("C11"), "11"));
        f.add(new Formula("=C11", new CellRef("D11"), "11"));
        f.add(new Formula("=D11", new CellRef("E11"), "11"));
        f.add(new Formula("=E11", new CellRef("F11"), "11"));
        f.add(new Formula("=F11", new CellRef("G11"), "11"));
        
        f.add(new Formula("12", new CellRef("A12"), "12"));
        f.add(new Formula("=A12", new CellRef("B12"), "12"));
        f.add(new Formula("=B12", new CellRef("C12"), "12"));
        f.add(new Formula("=C12", new CellRef("D12"), "12"));
        f.add(new Formula("=D12", new CellRef("E12"), "12"));
        f.add(new Formula("=E12", new CellRef("F12"), "12"));
        f.add(new Formula("=F12", new CellRef("G12"), "12"));

        return new CondensedFormulae(f);
        
    }
    
    private CondensedFormulae generateRowTo() {
        
        List<Formula> f = new ArrayList<> ();
        
        f.add(new Formula("1", new CellRef("A1"), "1"));
        f.add(new Formula("=A1", new CellRef("B1"), "1"));
        f.add(new Formula("=B1", new CellRef("C1"), "1"));
        f.add(new Formula("=C1", new CellRef("D1"), "1"));
        f.add(new Formula("=D1", new CellRef("E1"), "1"));
        f.add(new Formula("=E1", new CellRef("F1"), "1"));
        f.add(new Formula("=F1", new CellRef("G1"), "1"));
        
        f.add(new Formula("INSERT", new CellRef("A2"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("B2"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("C2"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("D2"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("E2"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("F2"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("G2"), "INSERT"));
        
        f.add(new Formula("INSERT", new CellRef("A3"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("B3"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("C3"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("D3"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("E3"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("F3"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("G3"), "INSERT"));
        
        f.add(new Formula("2", new CellRef("A4"), "2"));
        f.add(new Formula("=A4", new CellRef("B4"), "2"));
        f.add(new Formula("=B4", new CellRef("C4"), "2"));
        f.add(new Formula("=C4", new CellRef("D4"), "2"));
        f.add(new Formula("=D4", new CellRef("E4"), "2"));
        f.add(new Formula("=E4", new CellRef("F4"), "2"));
        f.add(new Formula("=F4", new CellRef("G4"), "2"));
        
        f.add(new Formula("INSERT", new CellRef("A5"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("B5"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("C5"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("D5"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("E5"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("F5"), "INSERT"));
        f.add(new Formula("INSERT", new CellRef("G5"), "INSERT"));
        
        f.add(new Formula("3", new CellRef("A6"), "3"));
        f.add(new Formula("=A6", new CellRef("B6"), "3"));
        f.add(new Formula("=B6", new CellRef("C6"), "3"));
        f.add(new Formula("=C6", new CellRef("D6"), "3"));
        f.add(new Formula("=D6", new CellRef("E6"), "3"));
        f.add(new Formula("=E6", new CellRef("F6"), "3"));
        f.add(new Formula("=F6", new CellRef("G6"), "3"));
        
        f.add(new Formula("5", new CellRef("A7"), "5"));
        f.add(new Formula("=A7", new CellRef("B7"), "5"));
        f.add(new Formula("=B7", new CellRef("C7"), "5"));
        f.add(new Formula("=C7", new CellRef("D7"), "5"));
        f.add(new Formula("=D7", new CellRef("E7"), "5"));
        f.add(new Formula("=E7", new CellRef("F7"), "5"));
        f.add(new Formula("=F7", new CellRef("G7"), "5"));
        
        f.add(new Formula("6", new CellRef("A8"), "6"));
        f.add(new Formula("=A8", new CellRef("B8"), "6"));
        f.add(new Formula("=B8", new CellRef("C8"), "6"));
        f.add(new Formula("=C8", new CellRef("D8"), "6"));
        f.add(new Formula("=D8", new CellRef("E8"), "6"));
        f.add(new Formula("=E8", new CellRef("F8"), "6"));
        f.add(new Formula("=F8", new CellRef("G8"), "6"));
        
        f.add(new Formula("9", new CellRef("A9"), "9"));
        f.add(new Formula("=A9", new CellRef("B9"), "9"));
        f.add(new Formula("=B9", new CellRef("C9"), "9"));
        f.add(new Formula("=C9", new CellRef("D9"), "9"));
        f.add(new Formula("=D9", new CellRef("E9"), "9"));
        f.add(new Formula("=E9", new CellRef("F9"), "9"));
        f.add(new Formula("=F9", new CellRef("G9"), "9"));
        
        f.add(new Formula("10", new CellRef("A10"), "10"));
        f.add(new Formula("=A10", new CellRef("B10"), "10"));
        f.add(new Formula("=B10", new CellRef("C10"), "10"));
        f.add(new Formula("=C10", new CellRef("D10"), "10"));
        f.add(new Formula("=D10", new CellRef("E10"), "10"));
        f.add(new Formula("=E10", new CellRef("F10"), "10"));
        f.add(new Formula("=F10", new CellRef("G10"), "10"));
        
        f.add(new Formula("11", new CellRef("A11"), "11"));
        f.add(new Formula("=A11", new CellRef("B11"), "11"));
        f.add(new Formula("=B11", new CellRef("C11"), "11"));
        f.add(new Formula("=C11", new CellRef("D11"), "11"));
        f.add(new Formula("=D11", new CellRef("E11"), "11"));
        f.add(new Formula("=E11", new CellRef("F11"), "11"));
        f.add(new Formula("=F11", new CellRef("G11"), "11"));
        
        f.add(new Formula("4", new CellRef("A12"), "4"));
        f.add(new Formula("=A12", new CellRef("B12"), "4"));
        f.add(new Formula("=B12", new CellRef("C12"), "4"));
        f.add(new Formula("=C12", new CellRef("D12"), "4"));
        f.add(new Formula("=D12", new CellRef("E12"), "4"));
        f.add(new Formula("=E12", new CellRef("F12"), "4"));
        f.add(new Formula("=F12", new CellRef("G12"), "4"));
        
        f.add(new Formula("12", new CellRef("A13"), "12"));
        f.add(new Formula("=A13", new CellRef("B13"), "12"));
        f.add(new Formula("=B13", new CellRef("C13"), "12"));
        f.add(new Formula("=C13", new CellRef("D13"), "12"));
        f.add(new Formula("=D13", new CellRef("E13"), "12"));
        f.add(new Formula("=E13", new CellRef("F13"), "12"));
        f.add(new Formula("=F13", new CellRef("G13"), "12"));
        
        return new CondensedFormulae(f);
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("   test TranslatedCondensedFormulae class");
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
     * Test of getPreTransRange method, of class TranslatedCondensedFormulae.
     */
    @Test
    public void testGetPreTransRange() {
        System.out.println("*   test getPreTransRange() method");
        
        CompoundRange postTranslation;
        CompoundRange preTranslation;
        CompoundRange expected;
        
        postTranslation = new CompoundRange("A1");
        expected = new CompoundRange("A1");
        
        preTranslation = tcf.getPreTransRange(postTranslation);
        assertTrue("'A1' doesn't get reverse translated to 'A1'", preTranslation.equals(expected));
        
        postTranslation.addCell(new CellRef("A4"));
        expected = new CompoundRange("A1:A2");
        
        preTranslation = tcf.getPreTransRange(postTranslation);
        assertTrue("'A1,A4' doesn't get reverse translated to 'A1:A2'", preTranslation.equals(expected));
        
        postTranslation.addCell(new CellRef("A6"));
        postTranslation.addCell(new CellRef("A12"));
        expected = new CompoundRange("A1:A4");
        
        preTranslation = tcf.getPreTransRange(postTranslation);
        assertTrue("'A1,A4,A6,A12' doesn't get reverse translated to 'A1:A4'", preTranslation.equals(expected));
        
    }

    /**
     * Test of getTranslated method, of class TranslatedCondensedFormulae.
     */
    @Test
    public void testGetTranslated() {
        System.out.println("*   test getTranslated() method");
        
        CondensedFormulae cf = tcf.getTranslated().getColumn(1);
        assertEquals("Row 1 hasn't been translated to row 1", "1", cf.getRow(1).listIterator().next().getFormula().getA1());
        assertEquals("Row 2 hasn't been translated to row 4", "2", cf.getRow(4).listIterator().next().getFormula().getA1());
        assertEquals("Row 3 hasn't been translated to row 6", "3", cf.getRow(6).listIterator().next().getFormula().getA1());
        assertEquals("Row 4 hasn't been translated to row 12", "4", cf.getRow(12).listIterator().next().getFormula().getA1());
        assertEquals("Row 5 hasn't been translated to row 7", "5", cf.getRow(7).listIterator().next().getFormula().getA1());
        assertEquals("Row 6 hasn't been translated to row 8", "6", cf.getRow(8).listIterator().next().getFormula().getA1());
        assertEquals("Row 9 hasn't been translated to row 9", "9", cf.getRow(9).listIterator().next().getFormula().getA1());
        assertEquals("Row 10 hasn't been translated to row 10", "10", cf.getRow(10).listIterator().next().getFormula().getA1());
        assertEquals("Row 11 hasn't been translated to row 11", "11", cf.getRow(11).listIterator().next().getFormula().getA1());
        assertEquals("Row 12 hasn't been translated to row 13", "12", cf.getRow(13).listIterator().next().getFormula().getA1());
        
        assertFalse("Can find cell A2 in translated object", tcf.getTranslated().findCell(new CellRef("A2")));
        assertFalse("Can find cell B3 in translated object", tcf.getTranslated().findCell(new CellRef("B3")));
        assertFalse("Can find cell G5 in translated object", tcf.getTranslated().findCell(new CellRef("G5")));
    }

    /**
     * Test of getRemoved method, of class TranslatedCondensedFormulae.
     */
    @Test
    public void testGetRemoved() {
        System.out.println("*   test getRemoved() method");
        
        CondensedFormulae cf = tcf.getRemoved().getColumn(1);
        assertEquals("Row 7 hasn't been removed", "7", cf.getRow(7).listIterator().next().getFormula().getA1());
        assertEquals("Row 8 hasn't been removed", "8", cf.getRow(8).listIterator().next().getFormula().getA1());
        
        assertTrue("Can't find cell D7 in removed object", tcf.getRemoved().findCell(new CellRef("D7")));
        assertTrue("Can't find cell E8 in removed object", tcf.getRemoved().findCell(new CellRef("E8")));
        
        assertFalse("Can find cell A1 in removed object", tcf.getRemoved().findCell(new CellRef("A1")));
        assertFalse("Can find cell B2 in removed object", tcf.getRemoved().findCell(new CellRef("B2")));
        assertFalse("Can find cell C3 in removed object", tcf.getRemoved().findCell(new CellRef("C3")));
        assertFalse("Can find cell D4 in removed object", tcf.getRemoved().findCell(new CellRef("D4")));
        assertFalse("Can find cell E5 in removed object", tcf.getRemoved().findCell(new CellRef("E5")));
        assertFalse("Can find cell F6 in removed object", tcf.getRemoved().findCell(new CellRef("F6")));
        assertFalse("Can find cell A9 in removed object", tcf.getRemoved().findCell(new CellRef("A9")));
        assertFalse("Can find cell B10 in removed object", tcf.getRemoved().findCell(new CellRef("B10")));
        assertFalse("Can find cell C11 in removed object", tcf.getRemoved().findCell(new CellRef("C11")));
        assertFalse("Can find cell G12 in removed object", tcf.getRemoved().findCell(new CellRef("G12")));
    }

    /**
     * Test of applyTranslations method, of class TranslatedCondensedFormulae.
     */
    @Test
    public void testApplyTranslations() {
        System.out.println("*   test applyTranslations() method");
        
        assertTrue("'A1' is not mapped to 'A1'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A1"), trans).equalsStrict(new CellRef("A1")));
        assertTrue("'A2' is not mapped to 'A4'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A2"), trans).equalsStrict(new CellRef("A4")));
        assertTrue("'A3' is not mapped to 'A6'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A3"), trans).equalsStrict(new CellRef("A6")));
        assertTrue("'A4' is not mapped to 'A12'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A4"), trans).equalsStrict(new CellRef("A12")));
        assertTrue("'A5' is not mapped to 'A7'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A5"), trans).equalsStrict(new CellRef("A7")));
        assertTrue("'A6' is not mapped to 'A8'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A6"), trans).equalsStrict(new CellRef("A8")));
        assertEquals("'A7' is not mapped to null", null, TranslatedCondensedFormulae.applyTranslations(new CellRef("A7"), trans));
        assertEquals("'A8' is not mapped to null", null, TranslatedCondensedFormulae.applyTranslations(new CellRef("A8"), trans));
        assertTrue("'A9' is not mapped to 'A9'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A9"), trans).equalsStrict(new CellRef("A9")));
        assertTrue("'A10' is not mapped to 'A10'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A10"), trans).equalsStrict(new CellRef("A10")));
        assertTrue("'A11' is not mapped to 'A11'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A11"), trans).equalsStrict(new CellRef("A11")));
        assertTrue("'A12' is not mapped to 'A13'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A12"), trans).equalsStrict(new CellRef("A13")));
        
        assertTrue("'$A1' is not mapped to '$A1'", TranslatedCondensedFormulae.applyTranslations(new CellRef("$A1"), trans).equalsStrict(new CellRef("$A1")));
        assertTrue("'A$1' is not mapped to 'A$1'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A$1"), trans).equalsStrict(new CellRef("A$1")));
        assertTrue("'$A$1' is not mapped to '$A$1'", TranslatedCondensedFormulae.applyTranslations(new CellRef("$A$1"), trans).equalsStrict(new CellRef("$A$1")));
        
        assertTrue("'$A2' is not mapped to '$A4'", TranslatedCondensedFormulae.applyTranslations(new CellRef("$A2"), trans).equalsStrict(new CellRef("$A4")));
        assertTrue("'A$2' is not mapped to 'A$4'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A$2"), trans).equalsStrict(new CellRef("A$4")));
        assertTrue("'$A$2' is not mapped to '$A$4'", TranslatedCondensedFormulae.applyTranslations(new CellRef("$A$2"), trans).equalsStrict(new CellRef("$A$4")));
        
        assertTrue("'A1' is not mapped to 'A1'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A1"), transSame).equalsStrict(new CellRef("A1")));
        assertTrue("'A2' is not mapped to 'A2'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A2"), transSame).equalsStrict(new CellRef("A2")));
        assertTrue("'A3' is not mapped to 'A3'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A3"), transSame).equalsStrict(new CellRef("A3")));
        assertTrue("'A4' is not mapped to 'A4'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A4"), transSame).equalsStrict(new CellRef("A4")));
        assertTrue("'A5' is not mapped to 'A5'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A5"), transSame).equalsStrict(new CellRef("A5")));
        assertTrue("'A6' is not mapped to 'A6'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A6"), transSame).equalsStrict(new CellRef("A6")));
        assertTrue("'A7' is not mapped to 'A7'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A7"), transSame).equalsStrict(new CellRef("A7")));
        assertTrue("'A8' is not mapped to 'A8'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A8"), transSame).equalsStrict(new CellRef("A8")));
        assertTrue("'A9' is not mapped to 'A9'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A9"), transSame).equalsStrict(new CellRef("A9")));
        assertTrue("'A10' is not mapped to 'A10'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A10"), transSame).equalsStrict(new CellRef("A10")));
        assertTrue("'A11' is not mapped to 'A11'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A11"), transSame).equalsStrict(new CellRef("A11")));
        assertTrue("'A12' is not mapped to 'A13'", TranslatedCondensedFormulae.applyTranslations(new CellRef("A12"), transSame).equalsStrict(new CellRef("A12")));
    }
    
}
