package edu.nju.logistics.ui.manager;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import edu.nju.logistics.bl.service.operationmanagement.OperationManagementBLService;
import edu.nju.logistics.vo.InstitutionVO;
import edu.nju.logistics.vo.UserVO;

@SuppressWarnings("serial")
public class UserTable extends AbstractTableModel{
	
    private OperationManagementBLService controller=null;
	
	private Vector<String> colums = null;

	private Vector<Vector<String>> rows = null;
	
	private InstitutionVO institutionVO=null;
	
	public UserTable(OperationManagementBLService controller,InstitutionVO institutionVO) {
	    this.controller=controller;
	    this.institutionVO=institutionVO;
	}
	/**
	 * 初始化
	 * @throws RemoteException 
	 */
	public void init() throws RemoteException{
		this.colums=new Vector<String>();
		this.colums.add("ID");
		this.colums.add("姓名");
		this.colums.add("职位");
		this.colums.add("所属机构");
		
		this.rows=new Vector<Vector<String>>();
		
		ArrayList<UserVO> voList=this.controller.employeeShow(this.institutionVO);
		for (int i = 0; i < voList.size(); i++) {
		Vector<String> temp=new Vector<String>();
		UserVO vo=voList.get(i);
		temp.add(vo.getId());
		temp.add(vo.getName());
		temp.add(vo.getRole());
		
		if(vo.getInstitution()==null){
			temp.add("");
		}
		else{
		temp.add(this.institutionVO.toString());
		}
		this.rows.add(temp);
		}
	}
	/**
	 * 根据职位查询员工
	 * @param role
	 * @return
	 * @throws RemoteException 
	 */
	public boolean checkByRole(String role) throws RemoteException {
		ArrayList<UserVO> voList=this.controller.checkEmployeeByRole(role, this.institutionVO.getId());;
		if(voList.size()==0){
			return false;
		}
		this.colums=new Vector<String>();
		this.colums.add("ID");
		this.colums.add("姓名");
		this.colums.add("职位");
		this.colums.add("所属机构");
		
		this.rows=new Vector<Vector<String>>();
		
		for(int i=0;i<voList.size();i++){
		Vector<String> temp=new Vector<String>();
		UserVO vo=voList.get(i);
		temp.add(vo.getId());
		temp.add(vo.getName());
		temp.add(vo.getRole());
		
		if(vo.getInstitution()==null){
			temp.add("");
		}
		else{
		temp.add(this.institutionVO.toString());
		}
		this.rows.add(temp);
		}
		
		return true;
	}

	/**
	 * 根据ID查询员工
	 * @param iD
	 * @return
	 * @throws RemoteException 
	 */
	public boolean checkByID(String ID) throws RemoteException {
		UserVO vo=this.controller.checkEmployeeByID(ID);
		if(vo==null){
			return false;
		}
		this.colums=new Vector<String>();
		this.colums.add("ID");
		this.colums.add("姓名");
		this.colums.add("职位");
		this.colums.add("所属机构");
		
		this.rows=new Vector<Vector<String>>();
		Vector<String> temp=new Vector<String>();
		temp.add(ID);
		temp.add(vo.getName());
		temp.add(vo.getRole());
		if(vo.getInstitution()==null){
			temp.add("");
		}
		else{
		temp.add(vo.getInstitution().toString());
		}
		this.rows.add(temp);
		
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
