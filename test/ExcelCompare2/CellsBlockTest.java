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
public class CellsBlockTest {

  private final CellRef cA1;
  private final CellRef cA2;
  private final CellRef cB1;
  private final CellRef cB2;
  private final CellRef cDE1;
  private final CellRef cDK79;
  private final CellRef cA105;
  private final CellRef cE10;

  private final CellsBlock cbA1;
  private final CellsBlock cbA1B2;
  private final CellsBlock cbA1DE1;
  private final CellsBlock cbA1A105;
  private final CellsBlock cbA1E10;
  private final CellsBlock cbB2DK79;

  public CellsBlockTest() {
    cA1 = new CellRef("A1");
    cA2 = new CellRef("A2");
    cB1 = new CellRef("B1");
    cB2 = new CellRef("B2");
    cDE1 = new CellRef("DE1");
    cDK79 = new CellRef("DK79");
    cA105 = new CellRef("A105");
    cE10 = new CellRef("E10");

    cbA1 = new CellsBlock(cA1);
    cbA1B2 = new CellsBlock(cA1, cB2);
    cbA1DE1 = new CellsBlock(cA1, cDE1);
    cbA1A105 = new CellsBlock(cA1, cA105);
    cbA1E10 = new CellsBlock(cA1, cE10);
    cbB2DK79 = new CellsBlock(cB2, cDK79);
  }

  @BeforeClass
  public static void setUpClass() {
    System.out.println("   test CellsBlock class");
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
    assertFalse("A1 contains B2", cbA1.contains(cB2));

    assertTrue("A1:DE1 does not contain A1", cbA1DE1.contains(cA1));
    assertFalse("A1:DE1 contains B2", cbA1DE1.contains(cB2));

    assertTrue("A1:A105 does not contain A1", cbA1A105.contains(cA1));
    assertFalse("A1:A105 contains B2", cbA1A105.contains(cB2));

    assertTrue("A1:A105 does not contain A1", cbA1E10.contains(cA1));
    assertTrue("A1:A105 does not contain B2", cbA1E10.contains(cB2));

    assertFalse("B2:DK79 contains A1", cbB2DK79.contains(cA1));
    assertFalse("B2:DK79 contains DE1", cbB2DK79.contains(cDE1));
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
