/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelCompare2;

import java.io.File;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author james.macadie
 */
public class SpreadSheetUtils {

  public static String convertToLetter(int col) {
    if (col >= 26) {
      int mod26 = (col % 26);
      return convertToLetter((col - mod26) / 26) + convertToLetter(mod26);
    } else {
      return String.valueOf((char) (col + 64));
    }
  }

  public static int convertFromLetter(String col) {
    int idx = 0;
    for (int i = col.length() - 1, exp = 0; i >= 0; i--, exp++) {
      idx += Math.pow(26, exp) * (col.charAt(i) - 'A' + 1);
    }
    return idx;
  }
}
