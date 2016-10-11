# ExcelCompare2

ExcelCompare2 is a java program for analysing the difference between two Excel files

ExcelCompare2 is inspired by [ExcelCompare](https://github.com/na-ka-na/ExcelCompare) and [this blog](http://programmaticallyspeaking.com/git-diffing-excel-files.html). The ultimate goal is to develop it into a command line tool that can be integrated into a git workflow. We could then *whisper it quietly* bring the wonderful world of git source control to spreadsheets

ExecelCompare2 differs from ExcelCompare in two key ways:

  1. Translations

    Translations are row or column inserts, deletes and moves. Individual cell moves (insert / delete cells) could/should fall under here but are too difficult to detect currently.  
    Translation detection is important as otherwise a row insert on the first row of a sheet would make every subsequent cell on the sheet look different when all that's happened is that they've all been shifted down one row.  
    If a translation is detected then the "from" spreadsheet will be re-written (in memory), including moving the cell references of all shifted cells as well as re-writing the cell references within any formula that refer to shifted cells. Following the re-write for all detected translations, the re-written 'from' spreadsheet and the 'to' spreadsheet are compared for any remaining formulae differences
  
  2. Blocks of like formulae are grouped together and reported as a single change

    Blocks of formulae are formulae that are identical as if they were copied from one cell to another. For example:  
    `=A1` in cell `B1` is the same as `=G7` in cell `H7`  
    `=$A1` in cell `B1` is the same as `=$A7` in cell `H7`  
    `=$A1` in cell `B1` is *not* the same as `=$G7` in cell `H7` 

    This can be important in large Excel files where the formulae are structured in blocks. For example, in financial models it is common practice to have a time dimension in the top row(s) of each sheet. In a file structured this way each successive row down the page will be the next step in the calculation and will be computing that step for all time periods of the model. When structured like this, each individual row of the spreadsheet should have the same formula across all its cells.  
    If one block is changed, rather than repeatedly report the many changes made, the single change is reported once for all cells affected

## How it works

There are a number of tricks / techniques employed:

  * Actual spreadsheet access is interfaced out as, in theory, it could be provided by some other means but currently the program relies only on Apache POI

  * Formulae are blocked together by comparing the R1C1 representation of the formulae; in R1C1 notation "same" formulae will have the exactly the same formula. Unfortunately POI doesn't provide R1C1 notation formulae so to convert between the two, regex is used to parse the formula and find & replace all the cell references  
  * Formulae are stored in the Formula class, which holds the original formula, the cell the formula is in, the R1C1 representation of the formula and finally the "shell formula". The shell formula concept stores the text of the formula with all cell references stripped out and is useful for detecting translations as any translation may edit cell references but will always leave the shell of each formula untouched  
  * Next up, we have the UniqueFormulae class that stores a single formula plus a record of all other cells on the sheet that share the same formula  
  * Building on this we have the CondensedFormulae class that represents the entire sheet. This is just a list of all the UniqueFormulae on the sheet  
    Note CondensedFormulae can also be (and is) used to store subsets of sheet. For example rows and columns within a sheet are also represented in a CondensedFormulae object

  * To detect translations, firstly a map is built for both rows and columns (read rows for columns and vice versa in the following explanation). The map is two directional and says which row in the 'to' sheet each row in the 'from' sheet goes to. An inserted row would be represented by row x in 'to' being mapped to `null` in 'from', likewise a deleted row would be represented by row x in 'from' being mapped to `null` in 'to'  
  * To build the map, row equivalence therefore needs to be determined. This is done by subsetting the CondensedFormulae of the sheet into a series of CondensedFormulae, one for each row. In turn each CondensedFormulae row from the 'from' sheet is taken. Matching is done by looking an identical "shell formula" match for all the UniqueFormulae in the target CondensedFormulae 'to' row  
  * Searching is done by fanning out from the expected position. This is based on the expectation that if 'from' row 7 is found on row 10 of 'to' then the most likely place to find 'from' row 8 is on row 11 of 'to'. The fanning process (from our example) will try row 11, 12, 10, 13, 9, etc... ignoring any rows already mapped and stopping when the match is found  
  * Finally the map is converted into a series of human-friendly translation actions, such as 2 rows inserted at row 4
  
  * The diff process works by firstly applying all the detected translations to the 'from' sheet. This produces a TranslatedCondensedFormulae object and moves all cells that have been translated as well as re-writing any formula that refers to a translated cell. Having done this, same cells that used to share the same formula (and therefore be part of the same UniqueFormulae object) may no longer be the same so the entire unique formulae grouping process is done over again  
  * Having translated the 'from' sheet all cells should now be lined up and we can get on with the business of determining the real differences  
  * Differences are determined by looping through the UniqueFormulae in the 'to' CondensedFormulae sheet
    1. If the formula does not exist in the 'from' sheet
      1. 'To' range is on the 'from' sheet: log a changed formula
      2. 'To' range is not on the 'from' sheet: log a new formula
    2. If the formula does exist in the 'from' sheet and the ranges are identical: do nothing as there is no change to report
    3. If the formula not exist in the 'from' sheet but the ranges do not match
      1.  Extract the sub-ranges that do match (if any) and do nothing as they match
      2. 'To' range is on the 'from' sheet: log a changed formula
      3. 'To' range is not on the 'from' sheet: log a new formula
    4. Finally, any remaining 'from' formula that have not yet been analysed: log a deleted formula as doesn't exist on 'from' sheet

## Dependencies

ExcelCompare2 depends only on [Apache POI](https://poi.apache.org/) and its child dependencies

The Test suites additionally rely on `JUnit 4.12` and `Hamcrest 1.3`

## Further work

Help is always appreciated. The following is my unordered list of development areas:

  * Improve efficiency. The tool lives or dies by its ability to analyse files in seconds. Currently a moderately sized 600k xlsx workbook can be analysed in about 7 seconds, if there aren't any translations (on my PC). Would like to speed up the program generally but also remove the penalty for translations
  * Improve translation detection. I have run tests on some real-world workbooks where the program makes mistakes. Not sure if this is in the detection part or applying the translations to the translated formulae and it needs more investigation
  * Deal with sheets being translated (order changed, inserted, deleted, copied, etc.)
  * Handle translations on other sheets when re-writing the translated formulae. Current process only translates the parts of the formula that refer within the current sheet. This is clearly limited on formulae that refer across to another sheet that has a translation
  * Row and column matching currently requires a perfect match of all 'from' "shell formulae" in the 'to' target. This means that if a row and a column were deleted from a sheet then no rows or columns can be matched (assuming the rows & columns had unique formulae on them) as no dimension will have all the formulae from its cross-section. For this reason (and others) it would be nice to find the best  row / column match above a threshold
  * Threading? Could different sheets be analysed in parallel using multiple threads? I know nothing about this...
  * Detect formatting changes as well as formulae changes
  * Read VBA as well as the formulae and provide a diff of that too
  * Consider the format of the output. Current output doesn't feel very well structured. Can it be integrated into [unified format](http://ftp.gnu.org/old-gnu/Manuals/diffutils-2.8.1/html_mono/diff.html#Unified%20Format)?

If you don't want to help develop but do have an idea for please log [an issue](https://github.com/jmacadie/ExcelCompare2/issues)