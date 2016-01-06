package edu.nju.logistics.ui.util.tableBuilder;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8571901483845617652L;
	
	private Vector<String> columnNameVector; // declare the table column name vector  
    private Vector<Vector<Object>> tableValueVector;

	public TableModel(VectorGetter vectorGetter) {
		columnNameVector = vectorGetter.getColumnNameVector();
		tableValueVector = vectorGetter.getTableValueVector();
	}

	@Override
	public int getColumnCount() {
		return columnNameVector.size();
	}

	@Override
	public int getRowCount() {
		return tableValueVector.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return tableValueVector.get(rowIndex).get(columnIndex);  
	}
	
	@Override  
    public String getColumnName(int columnIndex) {  
        return columnNameVector.get(columnIndex);  
    }  

}
