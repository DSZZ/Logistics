package edu.nju.logistics.ui.manager;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import edu.nju.logistics.bl.service.operationmanagement.OperationManagementBLService;
import edu.nju.logistics.vo.InstitutionVO;

@SuppressWarnings("serial")
public class InstitutionTable extends AbstractTableModel{
	
	private OperationManagementBLService controller=null;
	
	private Vector<String> colums = null;

	private Vector<Vector<String>> rows = null;

	public InstitutionTable(OperationManagementBLService controller) {
		this.controller=controller;
	
	}
	/**
	 * 列表初始化
	 * @throws RemoteException 
	 */
	public void init() throws RemoteException {
		this.colums=new Vector<String>();
		this.colums.add("地点");
		this.colums.add("ID");
		this.colums.add("类型");
		this.colums.add("租金");
		
		this.rows=new Vector<Vector<String>>();
		
		ArrayList<InstitutionVO> voList=this.controller.institutionShow();
		for (int i = 0; i < voList.size(); i++) {
		Vector<String> temp=new Vector<String>();
		InstitutionVO vo=voList.get(i);
		temp.add(vo.getLocation());
		temp.add(vo.getId());
		temp.add(vo.getType());
		temp.add(vo.getRental()+"");
		this.rows.add(temp);
		}
		
	}
	/**
	 * 根据ID查询机构
	 * @param ID 
	 * @return
	 * @throws RemoteException 
	 */
	public boolean checkInstitution(String ID) throws RemoteException {
		InstitutionVO vo=this.controller.checkInstitution(ID);
		if(vo==null){
			return false;
		}
		this.colums=new Vector<String>();
		this.colums.add("地点");
		this.colums.add("ID");
		this.colums.add("类型");
		this.colums.add("租金");
		
		this.rows=new Vector<Vector<String>>();
		Vector<String> temp=new Vector<String>();
		temp.add(vo.getLocation());
		temp.add(vo.getId());
		temp.add(vo.getType());
		temp.add(vo.getRental()+"");
		this.rows.addElement(temp);
		
		return true;
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
	
}
