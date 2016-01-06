package edu.nju.logistics.ui.manager;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import edu.nju.logistics.bl.service.operationmanagement.OperationManagementBLService;
import edu.nju.logistics.vo.DistanceVO;

@SuppressWarnings("serial")
public class DistanceTable extends AbstractTableModel{
	
	private Vector<String> colums = null;

	private Vector<Vector<String>> rows = null;
	
	private OperationManagementBLService controller=null;
	
	public DistanceTable(OperationManagementBLService controller) throws RemoteException {
		this.controller=controller;
		
		ArrayList<DistanceVO> voList=this.controller.distanceShow();
		
		this.colums=new Vector<String>();
		this.colums.add("城市");
		this.colums.add("城市");
		this.colums.add("距离/km");
		
		this.rows=new Vector<Vector<String>>();
		for (int i = 0; i < voList.size(); i++) {
			DistanceVO vo=voList.get(i);
			Vector<String> temp=new Vector<String>();
			temp.add(vo.getCity1());
			temp.add(vo.getCity2());
			temp.add(vo.getDistance()+"");
			this.rows.add(temp);
		}
	
	}
	
	public int getColumnCount() {

		return this.colums.size();
	}

	public int getRowCount() {
		if(this.rows==null){
			return 0;
		}
		return this.rows.size();
	}

	public Object getValueAt(int arg0, int arg1) {

		return this.rows.get(arg0).get(arg1);
	}

	public String getColumnName(int arg0) {

		return this.colums.get(arg0);
	}
//
//	public boolean isCellEditable(int arg0, int arg1) {
//		if(arg1==2){
//			return true;
//		}
//		return false;
//	}
//
//
//	public void setValueAt(Object arg0, int arg1, int arg2) {
//		Vector<String> temp=this.rows.get(arg1);
//		temp.set(arg2, (String)arg0);
//		this.rows.set(arg1,temp);
//		super.setValueAt(arg0, arg1, arg2);
//	}
}
