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
public class SpreadSheetUtilsTest {

  public SpreadSheetUtilsTest() {
  }

  @BeforeClass
  public static void setUpClass() {
    System.out.println("   test SpreadSheetUtils class");
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
   * Test of convertToLetter method, of class SpreadSheetUtils.
   */
  @Test
  public void testConvertToLetter() {
    System.out.println("*   test convertToLetter() method");

    assertEquals("Convert 1st column gives 'A'", "A", SpreadSheetUtils.convertToLetter(1));
    assertEquals("Convert 10th column gives 'J'", "J", SpreadSheetUtils.convertToLetter(10));
    assertEquals("Convert 27th column gives 'AA'", "AA", SpreadSheetUtils.convertToLetter(27));
    assertEquals("Convert 163rd column gives 'FG'", "FG", SpreadSheetUtils.convertToLetter(163));
    assertEquals("Convert 2840th column gives 'DEF'", "DEF", SpreadSheetUtils.convertToLetter(2840));
  }

  /**
   * Test of convertFromLetter method, of class SpreadSheetUtils.
   */
  @Test
  public void testConvertFromLetter() {
    System.out.println("*   test convertFromLetter() method");

    assertEquals("Convert 'A' gives 1st column", 1, SpreadSheetUtils.convertFromLetter("A"));
    assertEquals("Convert 'J' gives 10th column", 10, SpreadSheetUtils.convertFromLetter("J"));
    assertEquals("Convert 'AA' gives 27th column", 27, SpreadSheetUtils.convertFromLetter("AA"));
    assertEquals("Convert 'FG' gives 163rd column", 163, SpreadSheetUtils.convertFromLetter("FG"));
    assertEquals("Convert 'DEF' gives 2840th column", 2840, SpreadSheetUtils.convertFromLetter("DEF"));
  }

  /**
   * Test of loadSpreadSheet method, of class SpreadSheetUtils.
   */
  // Can't test???

}
