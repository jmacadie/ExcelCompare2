/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelCompare2;

/**
 *
 * @author james.macadie
 */
public class AnalysedFormula extends UniqueFormula {
    
    private boolean _analysed;
    private CompoundRange _unanalysedRange;
    
    public AnalysedFormula(Formula f) {
        // Construct the superclass
        super(f);
        _analysed = false;
        _unanalysedRange = super.getRange().clone();
    }
    
    @Override
    public void addCell(CellRef newCell) {
        if (!super.getRange().contains(newCell)) {
            super.addCell(newCell);
            _unanalysedRange.addCell(newCell);
            _analysed = false;
        }
    }
    
    public void setAnalysed(boolean analysed) {
        _analysed = analysed;
    }
    
    public void setAnalysed(CellRef analysed) {
        setAnalysed(new CompoundRange(analysed));
    }
    
    public void setAnalysed(CompoundRange analysed) {
        _unanalysedRange = _unanalysedRange.missingFrom(analysed);
        if (_unanalysedRange.isEmpty())
            _analysed = true;
    }
    
    public boolean isAnalysed() {
        return _analysed;
    }
    
    public CompoundRange getUnanalysed() {
        return _unanalysedRange;
    }
    
}
