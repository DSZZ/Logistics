package edu.nju.logistics.ui.manager;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import edu.nju.logistics.bl.service.operationmanagement.OperationManagementBLService;
import edu.nju.logistics.vo.LogVO;

@SuppressWarnings("serial")
public class LogTable extends AbstractTableModel {

	private Vector<String> colums = null;

	private Vector<Vector<String>> rows = null;

	OperationManagementBLService controller = null;

	public LogTable() {
	}

	public boolean showByDate(String date, OperationManagementBLService controller) throws RemoteException {
		this.controller = controller;

		ArrayList<LogVO> voList = this.controller.checkSystemLog(date);
	
		this.colums = new Vector<String>();
		this.colums.add("时间");
		this.colums.add("员工ID");
		this.colums.add("内容");

		this.rows = new Vector<Vector<String>>();
		//顺序很重要
		if(voList.size()==0){
			return false;
		}
		for (int i = 0; i < voList.size(); i++) {
			Vector<String> temp = new Vector<String>();
			LogVO vo = voList.get(i);
			temp.add(vo.getDate());
			temp.add(vo.getEmployeeID());
			temp.add(vo.getContent());
			this.rows.addElement(temp);
		}
		return true;
	}

	/**
	 * 初始化
	 */
	public void init() {
		this.colums = new Vector<String>();
		this.colums.add("时间");
		this.colums.add("员工ID");
		this.colums.add("内容");

		this.rows = new Vector<Vector<String>>();
	}

	public int getColumnCount() {
		//if()
		return this.colums.size();
	}

	public int getRowCount() {
		if (this.rows == null) {
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
}
