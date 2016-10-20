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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
public class SheetDiffTest {

  private final SheetDiff diff;

  public SheetDiffTest() {
    CondensedFormulae before = generateFrom();
    CondensedFormulae after = generateTo();

    diff = new SheetDiff(before, after, "Sheet 1");
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

  private CondensedFormulae generateTo() {

    List<Formula> f = new ArrayList<> ();

    f.add(new Formula("1", new CellRef("A1"), "1"));
    f.add(new Formula("=A7", new CellRef("B1"), "5"));
    f.add(new Formula("=B1", new CellRef("C1"), "5"));
    f.add(new Formula("=C1", new CellRef("D1"), "5"));
    f.add(new Formula("=D1", new CellRef("E1"), "5"));
    f.add(new Formula("=E1", new CellRef("F1"), "5"));
    f.add(new Formula("=F1", new CellRef("G1"), "5"));

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
    f.add(new Formula("=C2", new CellRef("D7"), "INSERT"));
    f.add(new Formula("=D7", new CellRef("E7"), "INSERT"));
    f.add(new Formula("=E7", new CellRef("F7"), "INSERT"));
    f.add(new Formula("=F7", new CellRef("G7"), "INSERT"));

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
    System.out.println("   test SheetDiff class");
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
   * Test of report method, of class SheetDiff.
   */
  @Test
  public void testReport() {
    System.out.println("*   test report() method");

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    // Capture the print output
    PrintStream pso = System.out;
    PrintStream pse = System.err;
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));

    String output;

    diff.report();
    output = outContent.toString();
    assertTrue("Doesn't report on Translations", output.contains("Sheet translations"));
    assertTrue("Doesn't report on row translations", output.contains("row"));
    assertFalse("Reports on column translations", output.contains("column"));
    // TODO: need more testing!

    // Reset the print output
    System.setOut(pso);
    System.setErr(pse);
  }

}
