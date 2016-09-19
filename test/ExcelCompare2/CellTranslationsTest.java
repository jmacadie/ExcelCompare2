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
public class CellTranslationsTest {
    
    private final CellTranslations transRow;
    private final CellTranslations transCol;
    
    public CellTranslationsTest() {
        transRow = new CellTranslations(generateFrom(), generateRowTo());
        transCol = new CellTranslations(generateFrom(), generateColTo());
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
    
    private CondensedFormulae generateColTo() {
        
        List<Formula> f = new ArrayList<> ();
        
        f.add(new Formula("1", new CellRef("A1"), "1"));
        f.add(new Formula("=G1", new CellRef("B1"), "1"));
        f.add(new Formula("=B1", new CellRef("C1"), "1"));
        f.add(new Formula("INSERT", new CellRef("D1"), "INSERT"));
        f.add(new Formula("=C1", new CellRef("E1"), "1"));
        f.add(new Formula("=E1", new CellRef("F1"), "1"));
        f.add(new Formula("=A1", new CellRef("G1"), "1"));
        f.add(new Formula("=F1", new CellRef("H1"), "1"));

        f.add(new Formula("2", new CellRef("A2"), "2"));
        f.add(new Formula("=G2", new CellRef("B2"), "2"));
        f.add(new Formula("=B2", new CellRef("C2"), "2"));
        f.add(new Formula("INSERT", new CellRef("D2"), "INSERT"));
        f.add(new Formula("=C2", new CellRef("E2"), "2"));
        f.add(new Formula("=E2", new CellRef("F2"), "2"));
        f.add(new Formula("=A2", new CellRef("G2"), "2"));
        f.add(new Formula("=F2", new CellRef("H2"), "2"));

        f.add(new Formula("3", new CellRef("A3"), "3"));
        f.add(new Formula("=G3", new CellRef("B3"), "3"));
        f.add(new Formula("=B3", new CellRef("C3"), "3"));
        f.add(new Formula("INSERT", new CellRef("D3"), "INSERT"));
        f.add(new Formula("=C3", new CellRef("E3"), "3"));
        f.add(new Formula("=E3", new CellRef("F3"), "3"));
        f.add(new Formula("=A3", new CellRef("G3"), "3"));
        f.add(new Formula("=F3", new CellRef("H3"), "3"));

        f.add(new Formula("4", new CellRef("A4"), "4"));
        f.add(new Formula("=G4", new CellRef("B4"), "4"));
        f.add(new Formula("=B4", new CellRef("C4"), "4"));
        f.add(new Formula("INSERT", new CellRef("D4"), "INSERT"));
        f.add(new Formula("=C4", new CellRef("E4"), "4"));
        f.add(new Formula("=E4", new CellRef("F4"), "4"));
        f.add(new Formula("=A4", new CellRef("G4"), "4"));
        f.add(new Formula("=F4", new CellRef("H4"), "4"));

        f.add(new Formula("5", new CellRef("A5"), "5"));
        f.add(new Formula("=G5", new CellRef("B5"), "5"));
        f.add(new Formula("=B5", new CellRef("C5"), "5"));
        f.add(new Formula("INSERT", new CellRef("D5"), "INSERT"));
        f.add(new Formula("=C5", new CellRef("E5"), "5"));
        f.add(new Formula("=E5", new CellRef("F5"), "5"));
        f.add(new Formula("=A5", new CellRef("G5"), "5"));
        f.add(new Formula("=F5", new CellRef("H5"), "5"));

        f.add(new Formula("6", new CellRef("A6"), "6"));
        f.add(new Formula("=G6", new CellRef("B6"), "6"));
        f.add(new Formula("=B6", new CellRef("C6"), "6"));
        f.add(new Formula("INSERT", new CellRef("D6"), "INSERT"));
        f.add(new Formula("=C6", new CellRef("E6"), "6"));
        f.add(new Formula("=E6", new CellRef("F6"), "6"));
        f.add(new Formula("=A6", new CellRef("G6"), "6"));
        f.add(new Formula("=F6", new CellRef("H6"), "6"));

        f.add(new Formula("7", new CellRef("A7"), "7"));
        f.add(new Formula("=G7", new CellRef("B7"), "7"));
        f.add(new Formula("=B7", new CellRef("C7"), "7"));
        f.add(new Formula("INSERT", new CellRef("D7"), "INSERT"));
        f.add(new Formula("=C7", new CellRef("E7"), "7"));
        f.add(new Formula("=E7", new CellRef("F7"), "7"));
        f.add(new Formula("=A7", new CellRef("G7"), "7"));
        f.add(new Formula("=F7", new CellRef("H7"), "7"));

        f.add(new Formula("8", new CellRef("A8"), "8"));
        f.add(new Formula("=G8", new CellRef("B8"), "8"));
        f.add(new Formula("=B8", new CellRef("C8"), "8"));
        f.add(new Formula("INSERT", new CellRef("D8"), "INSERT"));
        f.add(new Formula("=C8", new CellRef("E8"), "8"));
        f.add(new Formula("=E8", new CellRef("F8"), "8"));
        f.add(new Formula("=A8", new CellRef("G8"), "8"));
        f.add(new Formula("=F8", new CellRef("H8"), "8"));

        f.add(new Formula("9", new CellRef("A9"), "9"));
        f.add(new Formula("=G9", new CellRef("B9"), "9"));
        f.add(new Formula("=B9", new CellRef("C9"), "9"));
        f.add(new Formula("INSERT", new CellRef("D9"), "INSERT"));
        f.add(new Formula("=C9", new CellRef("E9"), "9"));
        f.add(new Formula("=E9", new CellRef("F9"), "9"));
        f.add(new Formula("=A9", new CellRef("G9"), "9"));
        f.add(new Formula("=F9", new CellRef("H9"), "9"));

        f.add(new Formula("10", new CellRef("A10"), "10"));
        f.add(new Formula("=G10", new CellRef("B10"), "10"));
        f.add(new Formula("=B10", new CellRef("C10"), "10"));
        f.add(new Formula("INSERT", new CellRef("D10"), "INSERT"));
        f.add(new Formula("=C10", new CellRef("E10"), "10"));
        f.add(new Formula("=E10", new CellRef("F10"), "10"));
        f.add(new Formula("=A10", new CellRef("G10"), "10"));
        f.add(new Formula("=F10", new CellRef("H10"), "10"));

        f.add(new Formula("11", new CellRef("A11"), "11"));
        f.add(new Formula("=G11", new CellRef("B11"), "11"));
        f.add(new Formula("=B11", new CellRef("C11"), "11"));
        f.add(new Formula("INSERT", new CellRef("D11"), "INSERT"));
        f.add(new Formula("=C11", new CellRef("E11"), "11"));
        f.add(new Formula("=E11", new CellRef("F11"), "11"));
        f.add(new Formula("=A11", new CellRef("G11"), "11"));
        f.add(new Formula("=F11", new CellRef("H11"), "11"));

        f.add(new Formula("12", new CellRef("A12"), "12"));
        f.add(new Formula("=G12", new CellRef("B12"), "12"));
        f.add(new Formula("=B12", new CellRef("C12"), "12"));
        f.add(new Formula("INSERT", new CellRef("D12"), "INSERT"));
        f.add(new Formula("=C12", new CellRef("E12"), "12"));
        f.add(new Formula("=E12", new CellRef("F12"), "12"));
        f.add(new Formula("=A12", new CellRef("G12"), "12"));
        f.add(new Formula("=F12", new CellRef("H12"), "12"));

        return new CondensedFormulae(f);
        
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
     * Test of size method, of class CellTranslations.
     */
    @Test
    public void testSize() {
        System.out.println("*   test size() method");
        
        assertEquals("Should be 5 translations (two inserts, two deletes and a move)", 5, transRow.size());
        assertEquals("Should be 2 translations (one insert and a move)", 2, transCol.size());
    }

    /**
     * Test of translateRow method, of class CellTranslations.
     */
    @Test
    public void testTranslateRow() {
        System.out.println("*   test translateRow() method");
        
        assertEquals("Row 1 isn't translated to row 1", 1, transRow.translateRow(CellTranslations.Direction.FROM_TO, 1).intValue());
        assertEquals("Row 2 isn't translated to row 4", 4, transRow.translateRow(CellTranslations.Direction.FROM_TO, 2).intValue());
        assertEquals("Row 3 isn't translated to row 6", 6, transRow.translateRow(CellTranslations.Direction.FROM_TO, 3).intValue());
        assertEquals("Row 4 isn't translated to row 12", 12, transRow.translateRow(CellTranslations.Direction.FROM_TO, 4).intValue());
        assertEquals("Row 5 isn't translated to row 7", 7, transRow.translateRow(CellTranslations.Direction.FROM_TO, 5).intValue());
        assertEquals("Row 6 isn't translated to row 8", 8, transRow.translateRow(CellTranslations.Direction.FROM_TO, 6).intValue());
        assertEquals("Row 7 isn't translated to null", null, transRow.translateRow(CellTranslations.Direction.FROM_TO, 7));
        assertEquals("Row 8 isn't translated to null", null, transRow.translateRow(CellTranslations.Direction.FROM_TO, 8));
        assertEquals("Row 9 isn't translated to row 9", 9, transRow.translateRow(CellTranslations.Direction.FROM_TO, 9).intValue());
        assertEquals("Row 10 isn't translated to row 10", 10, transRow.translateRow(CellTranslations.Direction.FROM_TO, 10).intValue());
        assertEquals("Row 11 isn't translated to row 11", 11, transRow.translateRow(CellTranslations.Direction.FROM_TO, 11).intValue());
        assertEquals("Row 12 isn't translated to row 13", 13, transRow.translateRow(CellTranslations.Direction.FROM_TO, 12).intValue());
        
    }

    /**
     * Test of translateColumn method, of class CellTranslations.
     */
    @Test
    public void testTranslateColumn() {
        System.out.println("*   test translateColumn() method");
        
        assertEquals("Column 1 isn't translated to column 1", 1, transCol.translateColumn(CellTranslations.Direction.FROM_TO, 1).intValue());
        assertEquals("Column 2 isn't translated to column 7", 7, transCol.translateColumn(CellTranslations.Direction.FROM_TO, 2).intValue());
        assertEquals("Column 3 isn't translated to column 2", 2, transCol.translateColumn(CellTranslations.Direction.FROM_TO, 3).intValue());
        assertEquals("Column 4 isn't translated to column 3", 3, transCol.translateColumn(CellTranslations.Direction.FROM_TO, 4).intValue());
        assertEquals("Column 5 isn't translated to column 5", 5, transCol.translateColumn(CellTranslations.Direction.FROM_TO, 5).intValue());
        assertEquals("Column 6 isn't translated to column 6", 6, transCol.translateColumn(CellTranslations.Direction.FROM_TO, 6).intValue());
        assertEquals("Column 7 isn't translated to column 8", 8, transCol.translateColumn(CellTranslations.Direction.FROM_TO, 7).intValue());
    }

    /**
     * Test of report method, of class CellTranslations.
     */
    @Test
    public void testReport() {
        System.out.println("*   test report() method");
    }
    
}
