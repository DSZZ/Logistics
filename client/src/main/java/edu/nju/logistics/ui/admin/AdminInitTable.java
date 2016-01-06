package edu.nju.logistics.ui.admin;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import edu.nju.logistics.bl.service.identitymanagement.IdentityManagementBLService;
import edu.nju.logistics.vo.UserVO;
/**
 * 管理员主界面人员情况变更列表
 * @author 董轶波
 *
 */
@SuppressWarnings("serial")
public class AdminInitTable extends AbstractTableModel{ 
	
	private IdentityManagementBLService controller=null;
	
	private Vector<String> colums=null;
	
	private Vector<Vector<String>> rows=null;
	
	public AdminInitTable(IdentityManagementBLService controller) throws RemoteException {
		this.controller=controller;
		this.init();
	}
	
	public void init() throws RemoteException{
		ArrayList<UserVO> voAdd=this.controller.showAddWindow();
		ArrayList<UserVO> voDel=this.controller.showDeleteWindow();
		
		this.colums=new Vector<String>();
		this.colums.add("ID");
		this.colums.add("职位");
		this.colums.add("变更情况");
		
		this.rows=new Vector<Vector<String>>();
		
		for (int i = 0; i < voAdd.size(); i++) {
			Vector<String> temp=new Vector<String>();
			temp.add(voAdd.get(i).getId());
			temp.add(voAdd.get(i).getRole());
			temp.add("新增");
			this.rows.add(temp);
		}
		for (int i = 0; i < voDel.size(); i++) {
			Vector<String> temp=new Vector<String>();
			temp.add(voDel.get(i).getId());
			temp.add(voDel.get(i).getRole());
			temp.add("解雇");
			this.rows.add(temp);
		}
	}
	
	public int getColumnCount() {
		
		return this.colums.size();
	}


	public int getRowCount() {
		
		return this.rows.size();
	}


	public Object getValueAt(int arg0, int arg1) {
		
		return this.rows.get(arg0).get(arg1);
	}


	public String getColumnName(int arg0) {
		
		return this.colums.get(arg0);
	}

}
