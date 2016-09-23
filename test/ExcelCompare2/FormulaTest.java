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
public class FormulaTest {
    
    private final CellRef cA1;
    private final CellRef cJ10;
    private final Formula f;
    private final Formula fComplex;
    
    public FormulaTest() {
        cA1 = new CellRef("A1");
        cJ10 = new CellRef("J10");
        f = new Formula("=B1", cA1, "1");
        fComplex = new Formula("=(F2+D$5)/$E$8*INDEX($A$4:$A$17,MATCH($B3,J$4:J$17,0))", cA1, "1");
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("   test Formula class");
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
     * Test of getR1C1 method, of class Formula.
     */
    @Test
    public void testGetR1C1() {
        System.out.println("*   test getFormulaR1C1() method");
        
        assertEquals("R1C1 formula of '=B1' in cell 'A1' is not '=RC[1]'", "=RC[1]", f.getR1C1());
        
        // The 8 worst examples from the headlease module
        testGetFormulaR1C1Inner(
                "$AA$435",
                "=AND(OR(AA402,AA403,AA404,AA405,AA406,AA407,AA408,AA409,AA410,AA411,AA412,AA413,AA414,AA415,AA416,AA417,AA418,AA419,AA420,AA421,AA422,AA423,AA424,AA425,AA426,AA427,AA428,AA429,AA430,AA431),NOT(AA267))",
                "=AND(OR(R[-33]C,R[-32]C,R[-31]C,R[-30]C,R[-29]C,R[-28]C,R[-27]C,R[-26]C,R[-25]C,R[-24]C,R[-23]C,R[-22]C,R[-21]C,R[-20]C,R[-19]C,R[-18]C,R[-17]C,R[-16]C,R[-15]C,R[-14]C,R[-13]C,R[-12]C,R[-11]C,R[-10]C,R[-9]C,R[-8]C,R[-7]C,R[-6]C,R[-5]C,R[-4]C),NOT(R[-168]C))");
        testGetFormulaR1C1Inner(
                "$G$1172",
                "=IF(G1154=0,0,IF(AND(G1171>0,MOD(G1171,G1154)<>0),ROUNDDOWN(G1171/G1154,0)+1,ROUNDDOWN(G1171/G1154,0))*G1154)",
                "=IF(R[-18]C=0,0,IF(AND(R[-1]C>0,MOD(R[-1]C,R[-18]C)<>0),ROUNDDOWN(R[-1]C/R[-18]C,0)+1,ROUNDDOWN(R[-1]C/R[-18]C,0))*R[-18]C)");
        testGetFormulaR1C1Inner(
                "$G$1465",
                "=MAX(G402*Z402,G403*Z403,G404*Z404,G405*Z405,G406*Z406,G407*Z407,G408*Z408,G409*Z409,G410*Z410,G411*Z411,G412*Z412,G413*Z413,G414*Z414,G415*Z415,G416*Z416,G417*Z417,G418*Z418,G419*Z419,G420*Z420,G421*Z421,G422*Z422,G423*Z423,G424*Z424,G425*Z425,G426*Z426,G427*Z427,G428*Z428,G429*Z429,G430*Z430,G431*Z431)",
                "=MAX(R[-1063]C*R[-1063]C[19],R[-1062]C*R[-1062]C[19],R[-1061]C*R[-1061]C[19],R[-1060]C*R[-1060]C[19],R[-1059]C*R[-1059]C[19],R[-1058]C*R[-1058]C[19],R[-1057]C*R[-1057]C[19],R[-1056]C*R[-1056]C[19],R[-1055]C*R[-1055]C[19],R[-1054]C*R[-1054]C[19],R[-1053]C*R[-1053]C[19],R[-1052]C*R[-1052]C[19],R[-1051]C*R[-1051]C[19],R[-1050]C*R[-1050]C[19],R[-1049]C*R[-1049]C[19],R[-1048]C*R[-1048]C[19],R[-1047]C*R[-1047]C[19],R[-1046]C*R[-1046]C[19],R[-1045]C*R[-1045]C[19],R[-1044]C*R[-1044]C[19],R[-1043]C*R[-1043]C[19],R[-1042]C*R[-1042]C[19],R[-1041]C*R[-1041]C[19],R[-1040]C*R[-1040]C[19],R[-1039]C*R[-1039]C[19],R[-1038]C*R[-1038]C[19],R[-1037]C*R[-1037]C[19],R[-1036]C*R[-1036]C[19],R[-1035]C*R[-1035]C[19],R[-1034]C*R[-1034]C[19])");
        testGetFormulaR1C1Inner(
                "$Z$1796",
                "=IF(AND(Z779,Y1796=1),Y1796,IF(OR(Z222>0,Z224>0,Z223=1),1,IF(AND(Z692,Y1796<>0),(Y1796+1),IF(Z780,Y1796,IF(Y1796=1,0,Y1796)))))",
                "=IF(AND(R[-1017]C,RC[-1]=1),RC[-1],IF(OR(R[-1574]C>0,R[-1572]C>0,R[-1573]C=1),1,IF(AND(R[-1104]C,RC[-1]<>0),(RC[-1]+1),IF(R[-1016]C,RC[-1],IF(RC[-1]=1,0,RC[-1])))))");
        testGetFormulaR1C1Inner(
                "$Z$1806",
                "=IF(Z1800=1,Z$226,IF(Z1800=0,0,IF(AND(Y1800=0,Z1800>0),Z$1803,IF(AND(Z1800>0,AA1800=0),Z$1804,Z$797))))",
                "=IF(R[-6]C=1,R226C,IF(R[-6]C=0,0,IF(AND(R[-6]C[-1]=0,R[-6]C>0),R1803C,IF(AND(R[-6]C>0,R[-6]C[1]=0),R1804C,R797C))))");
        testGetFormulaR1C1Inner(
                "$Z$1820",
                "=IF(Z1815,-SUM(Z1818:Z1819),IF(Z1316>0,(IF(OR(Z1806=0,Z1809=0),0,Z1806*Z1812/Z1809)+IF(OR(Z1807=0,Z1810=0),0,Z1807*Z1813/Z1810)),0))",
                "=IF(R[-5]C,-SUM(R[-2]C:R[-1]C),IF(R[-504]C>0,(IF(OR(R[-14]C=0,R[-11]C=0),0,R[-14]C*R[-8]C/R[-11]C)+IF(OR(R[-13]C=0,R[-10]C=0),0,R[-13]C*R[-7]C/R[-10]C)),0))");
        testGetFormulaR1C1Inner(
                "$AA$2497",
                "=IF($G$2495=0,$G$2497*AA$1316,IF($G2497=\"NULL\",\"NULL\",IF(OR(AA218,Z218),AA$1316*$G2497*AA2490/$G2491,IF(AA1113,$G2497*AA2490/$G2491,Z2497))))",
                "=IF(R2495C7=0,R2497C7*R1316C,IF(RC7=\"NULL\",\"NULL\",IF(OR(R[-2279]C,R[-2279]C[-1]),R1316C*RC7*R[-7]C/R[-6]C7,IF(R[-1384]C,RC7*R[-7]C/R[-6]C7,RC[-1]))))");
        testGetFormulaR1C1Inner(
                "$G$2782",
                "=IF(G2762=\"RBS\",0,IF(G2756=\"NULL\",IF(G2761=\"Other\",G2779,IF(G2761=\"PIA\",G2777,G2778))*G2769,G2756*$AA$20/G2774)*G2770)",
                "=IF(R[-20]C=\"RBS\",0,IF(R[-26]C=\"NULL\",IF(R[-21]C=\"Other\",R[-3]C,IF(R[-21]C=\"PIA\",R[-5]C,R[-4]C))*R[-13]C,R[-26]C*R20C27/R[-8]C)*R[-12]C)");
        
        // Test formulae across sheets
        testGetFormulaR1C1Inner(
                "$A$1",
                "=Sheet2!A1",
                "=Sheet2!RC");
        
        testGetFormulaR1C1Inner(
                "$S$6",
                "=VLOOKUP(B6,'Overhanging Leases'!$B$5:$W$166,7,FALSE)",
                "=VLOOKUP(RC[-17],'Overhanging Leases'!R5C2:R166C23,7,FALSE)");
        
        // Test non-formula
        testGetFormulaR1C1Inner(
                "$S$6",
                "Q1 2015 to Q1 2016",
                "Q1 2015 to Q1 2016");
        
        // TODO: Come up with some other edge cases:
        // - sums across sheets,
        // - diff types of brackets,
        // - external links,
        // - whole columns/rows
        // - etc.

    }
    
    private void testGetFormulaR1C1Inner(String cell, String formula, String expected) {
        String errorMessage = "R1C1 formula of '" + formula + 
                                "' in cell '" + cell + 
                                "' is not '" + expected + "'";
        Formula fx = new Formula(formula, new CellRef(cell), "123");
        assertEquals(errorMessage, expected, fx.getR1C1());
    }

    /**
     * Test of getFormula method, of class Formula.
     */
    @Test
    public void testGetFormula() {
        System.out.println("*   test getFormula() method");
        
        assertEquals("Cannot retieve the input cell formula", "=B1", f.getA1());
    }

    /**
     * Test of getCellRef method, of class Formula.
     */
    @Test
    public void testGetCellRef() {
        System.out.println("*   test getCellRef() method");
        
        assertTrue("Cannot retieve the input cell ref", cA1.equalsStrict(f.getCellRef()));
    }

    /**
     * Test of getA1 method, of class Formula.
     */
    @Test
    public void testGetA1() {
        System.out.println("*   test getA1() method");
        
        assertEquals("A1 formula of '=B1' in cell 'A1' is not '=B1'", "=B1", f.getA1());
    }

    /**
     * Test of getText method, of class Formula.
     */
    @Test
    public void testGetText() {
        System.out.println("*   test getText() method");
        
        assertEquals("Text of '=B1' (1) in cell 'A1' is not '1'", "1", f.getText());
    }

    /**
     * Test of getCopiedTo method, of class Formula.
     */
    @Test
    public void testGetCopiedTo() {
        System.out.println("*   test getCopiedTo() method");
        
        Formula fCopied;
        Formula fExpected;
        
        // Simple Formula
        
        // Copy to same cell
        fCopied = f.getCopiedTo(cA1);
        assertEquals("A1 formula of cell copied to itself is not the same", fCopied.getA1(), f.getA1());
        assertEquals("R1C1 formula of cell copied to itself is not the same", fCopied.getR1C1(), f.getR1C1());
        assertEquals("Text of cell copied to itself is not the same", fCopied.getText(), f.getText());
        assertTrue("CellRef of cell copied to itself is not the same", fCopied.getCellRef().equalsStrict(f.getCellRef()));
        
        // Copy to different cell
        fCopied = f.getCopiedTo(cJ10);
        fExpected = new Formula("=K10", cJ10, "1");
        assertEquals("A1 formula of cell copied elsewhere is not the same", fCopied.getA1(), fExpected.getA1());
        assertEquals("R1C1 formula of cell copied elsewhere is not the same", fCopied.getR1C1(), fExpected.getR1C1());
        assertEquals("Text of cell copied elsewhere is not the same", fCopied.getText(), fExpected.getText());
        assertTrue("CellRef of cell copied elsewhere is not the same", fCopied.getCellRef().equalsStrict(fExpected.getCellRef()));
        
        // Formula with all mixes of refernce
        
        // Copy to same cell
        fCopied = fComplex.getCopiedTo(cA1);
        assertEquals("A1 formula of complex cell copied to itself is not the same", fCopied.getA1(), fComplex.getA1());
        assertEquals("R1C1 formula of complex cell copied to itself is not the same", fCopied.getR1C1(), fComplex.getR1C1());
        assertEquals("Text of complex cell copied to itself is not the same", fCopied.getText(), fComplex.getText());
        assertTrue("CellRef of complex cell copied to itself is not the same", fCopied.getCellRef().equalsStrict(fComplex.getCellRef()));
        
        // Copy to different cell
        fCopied = fComplex.getCopiedTo(cJ10);
        fExpected = new Formula("=(O11+M$5)/$E$8*INDEX($A$4:$A$17,MATCH($B12,S$4:S$17,0))", cJ10, "1");
        assertEquals("A1 formula of complex cell copied elsewhere is not the same", fCopied.getA1(), fExpected.getA1());
        assertEquals("R1C1 formula of complex cell copied elsewhere is not the same", fCopied.getR1C1(), fExpected.getR1C1());
        assertEquals("Text of complex cell copied elsewhere is not the same", fCopied.getText(), fExpected.getText());
        assertTrue("CellRef of complex cell copied elsewhere is not the same", fCopied.getCellRef().equalsStrict(fExpected.getCellRef()));
        
    }

    /**
     * Test of getTranslatedTo method, of class Formula.
     */
    @Test
    public void testGetTranslatedTo() {
        System.out.println("*   test getTranslatedTo() method");
        System.out.println("OMG this is impossible to test!");
    }

    /**
     * Test of translatedDistance method, of class Formula.
     */
    @Test
    public void testTranslatedDistance() {
        System.out.println("*   test translatedDistance() method");
        
        // Test formula to itself
        assertEquals("Formula of cell is 0 not from itself", 0.0, f.translatedDistance(f), 0.01);
        assertEquals("Formula of complex cell is not 0 from itself", 0.0, fComplex.translatedDistance(fComplex), 0.01);
        
        // Test formula to fomula copied elsewhere
        assertEquals("Formula of cell copied elsewhere is not 0 from itself", 0.0, f.getCopiedTo(cJ10).translatedDistance(f), 0.01);
        assertEquals("Formula of complex cell copied elsewhere is not 0 from itself", 0.0, fComplex.getCopiedTo(cJ10).translatedDistance(fComplex), 0.01);
        
        // Test two different formulae
        assertEquals("Formula of cell is not -1 from a totally differnt formula", -1.0, f.translatedDistance(fComplex), 0.01);
        
        // Test translated formulae
        Formula f2;
        f2 = new Formula("=C1", cA1, "1");
        assertEquals("Formula of '=C1' in A1 is not 1 from '=B1' in A1 ", 1.0, f.translatedDistance(f2), 0.01);
        f2 = new Formula("=B2", cA1, "1");
        assertEquals("Formula of '=B2' in A1 is not 1 from '=B1' in A1 ", 1.0, f.translatedDistance(f2), 0.01);
        f2 = new Formula("=A10", cJ10, "1");
        assertEquals("Formula of '=A10' in J10 is not 10 from '=B1' in A1 ", 10.0, f.translatedDistance(f2), 0.01);
        f2 = new Formula("=A1", cJ10, "1");
        assertEquals("Formula of '=A1' in J10 is not root 181 from '=B1' in A1 ", Math.sqrt(181), f.translatedDistance(f2), 0.01);
        
        f2 = new Formula("=(F2+D$5)/E$8*INDEX($A$4:$A$17,MATCH($B3,J$4:J$17,0))", cA1, "1");
        assertEquals("Formula is not 100 from reference discrepancy", 100.0, fComplex.translatedDistance(f2), 0.01);
        f2 = new Formula("=(F3+C$5)/$D$8*INDEX($A$5:$B$17,MATCH($C3,J$5:K$17,0))", cA1, "1");
        assertEquals("Formula is not 8 from formula with 8 single cell move changes", 8.0, fComplex.translatedDistance(f2), 0.01);
    }
    
}
