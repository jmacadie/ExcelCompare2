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
        _unanalysedRange = super.getRange().getCopy();
    }
    
    @Override
    public void addCell(CellRef newCell) {
        if (!super.getRange().contains(newCell)) {
            super.addCell(newCell);
            _unanalysedRange.addCell(newCell);
            _analysed = false;
        }
    }
    
    public void setAnalysed(CellRef analysed) {
        _unanalysedRange.removeCell(analysed);
        if (_unanalysedRange.isEmpty())
            _analysed = true;
    }
    
    public void setAnalysed(CompoundRange analysed) {
        _unanalysedRange = _unanalysedRange.missingFrom(analysed);
        if (_unanalysedRange.isEmpty())
            _analysed = true;
    }
    
    public boolean isAnalysed() {
        return _analysed;
    }
    
    public boolean isAnalysed(CellRef cell) {
        return !_unanalysedRange.contains(cell);
    }
    
    public CompoundRange getUnanalysed() {
        return _unanalysedRange;
    }
    
    public UniqueFormula getUniqueFormula() {
        // TODO: clones but might be a more effienct way to do this
        return new UniqueFormula(super.getFormula(), super.getRange());
    }
    
}
