package edu.nju.logistics.ui.accountant.accountinit;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class SuperTableModel extends AbstractTableModel{
	  protected Vector<String> title=null;
	  protected Vector<Vector<String>> data=null;
	  public SuperTableModel(){
		  super();
		  title = new Vector<>();
		  data = new Vector<>();
	  }
	  @Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return  title.size();
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getValueAt(int rowIndex,	 int columnIndex) {
			// TODO Auto-generated method stub
			return data.get(rowIndex).get(columnIndex);
		}
	     
		@Override
		public String getColumnName(int arg0) {
			
			return title.get(arg0);
		}
		
		@Override
    	public boolean isCellEditable(int row, int column) {
    		//不可编辑
    	          	return false;
    	}

}
