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
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author james.macadie
 */
public class CellRefR1C1Test {
    
    private final CellRefR1C1 cR1C1;
    private final CellRefR1C1 cRm1C1;
    private final CellRefR1C1 cRC1;
    private final CellRefR1C1 cRp1C1;
    private final CellRefR1C1 cR1Cm1;
    private final CellRefR1C1 cR1C;
    private final CellRefR1C1 cR1Cp1;
    private final CellRefR1C1 cRm1Cm1;
    private final CellRefR1C1 cRC;
    private final CellRefR1C1 cRp1Cp1;
    private final CellRefR1C1 cR2C2;
    
    public CellRefR1C1Test() {
        cR1C1 = new CellRefR1C1(true, 1, true, 1);
        cRm1C1 = new CellRefR1C1(false, -1, true, 1);
        cRC1 = new CellRefR1C1(false, 0, true, 1);
        cRp1C1 = new CellRefR1C1(false, 1, true, 1);
        cR1Cm1 = new CellRefR1C1(true, 1, false, -1);
        cR1C = new CellRefR1C1(true, 1, false, 0);
        cR1Cp1 = new CellRefR1C1(true, 1, false, 1);
        cRm1Cm1 = new CellRefR1C1(-1, -1);
        cRC = new CellRefR1C1(0, 0);
        cRp1Cp1 = new CellRefR1C1(1, 1);
        cR2C2 = new CellRefR1C1(true, 2, true, 2);
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("   test CellRefR1C1 class");
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
     * Test of distance method, of class CellRefR1C1.
     */
    @Test
    public void testDistance() {
        System.out.println("*   test distance() method");
        
        assertTrue("R[-1]c[-1] is not 1.414 (root 2) from RC", sameDist(1.414, cRC.distance(cRm1Cm1)));
        assertTrue("R[1]c[1] is not 1.414 (root 2) from RC", sameDist(1.414, cRC.distance(cRp1Cp1)));
        assertTrue("R[1]c[1] is not 2.828 (2 root 2) from R[-1]c[-1]", sameDist(2.828, cRm1Cm1.distance(cRp1Cp1)));
        
        assertTrue("R1C1 is not 1.414 (root 2) from R2C2", sameDist(1.414, cR1C1.distance(cR2C2)));
        
        assertTrue("R[1]c[1] is not 100 from R1C[1]", sameDist(100.0, cR1Cp1.distance(cRp1Cp1)));
        assertTrue("R[1]c[1] is not 100 from R[1]C", sameDist(100.0, cRp1C1.distance(cRp1Cp1)));
        assertTrue("R1C[1] is not 100 from R[1]c[1]", sameDist(100.0, cRp1Cp1.distance(cR1Cp1)));
        assertTrue("R[1]C is not 100 from R[1]c[1]", sameDist(100.0, cRp1Cp1.distance(cRp1C1)));
    }
    
    private boolean sameDist(double first, double second) {
        double diff = Math.abs(first - second);
        return (diff < 0.001);
    }
    
    /**
     * Test of toString method, of class CellRefR1C1.
     */
    public void testToString() {
        System.out.println("*   test toString() method");
    }
    
}
