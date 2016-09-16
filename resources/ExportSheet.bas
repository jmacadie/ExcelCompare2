Attribute VB_Name = "ExportSheet"
Option Explicit

Public Sub GenerateFormulaeList()

Dim cell As Range
Dim row As Integer
Dim column As Integer
Dim out As String

    out = ""
    For row = 1 To ActiveSheet.UsedRange.Rows.Count
        For column = 1 To ActiveSheet.UsedRange.Columns.Count
            Set cell = ActiveSheet.Cells(row, column)
            out = out & "f.add(new Formula(" & Chr(34) & cell.Formula & Chr(34) & _
                ", new CellRef(" & Chr(34) & cell.Address(RowAbsolute:=False, ColumnAbsolute:=False) & _
                Chr(34) & "), " & Chr(34) & cell.Text & Chr(34) & "));" & vbCr
        Next column
        out = out & vbCr
    Next row
    Debug.Print (out)

End Sub
