package edu.nju.logistics.ui.admin;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import edu.nju.logistics.bl.service.identitymanagement.IdentityManagementBLService;
import edu.nju.logistics.vo.UserVO;
/**
 * 管理员账户管理的人员列表
 * @author 董轶波
 *
 */
@SuppressWarnings("serial")
public class AdminOperaTable extends AbstractTableModel {

	private IdentityManagementBLService controller;
	
	private Vector<String> colums = null;

	private Vector<Vector<String>> rows = null;

	public AdminOperaTable(IdentityManagementBLService controller) {
		this.controller=controller;
	}
	
	public void init() throws RemoteException{
        ArrayList<UserVO> vo=this.controller.show();
		
		this.colums=new Vector<String>();
		this.colums.add("ID");
		this.colums.add("密码");
		this.colums.add("职位");
		
		this.rows=new Vector<Vector<String>>();
		
		for(int i=0;i<vo.size();i++){
		Vector<String> temp=new Vector<String>();
		temp.add(vo.get(i).getId());
		temp.add(vo.get(i).getPassword());
		temp.add(vo.get(i).getRole());
		this.rows.add(temp);
		}
	}
	
	public boolean checkByID(String id) throws RemoteException{
		UserVO vo=this.controller.checkUserByID(id);
		if(vo==null){
			return false;
		}
		this.colums=new Vector<String>();
		this.colums.add("ID");
		this.colums.add("密码");
		this.colums.add("职位");
		
		this.rows=new Vector<Vector<String>>();
		Vector<String> temp=new Vector<String>();
		temp.add(id);
		temp.add(vo.getPassword());
		temp.add(vo.getRole());
		
		this.rows.add(temp);
		
		return true;
	}
	
	public boolean checkByRole(String role) throws RemoteException{
		ArrayList<UserVO> vo=this.controller.checkUserByRole(role);
		if(vo.size()==0){
			return false;
		}
		this.colums=new Vector<String>();
		this.colums.add("ID");
		this.colums.add("密码");
		this.colums.add("职位");
		
		this.rows=new Vector<Vector<String>>();
		
		for(int i=0;i<vo.size();i++){
		Vector<String> temp=new Vector<String>();
		temp.add(vo.get(i).getId());
		temp.add(vo.get(i).getPassword());
		temp.add(role);
		this.rows.add(temp);
		}
		
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
