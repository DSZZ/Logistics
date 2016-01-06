package edu.nju.logistics.ui.manager;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import edu.nju.logistics.bl.service.operationmanagement.OperationManagementBLService;
import edu.nju.logistics.vo.SheetVO;

@SuppressWarnings("serial")
public class SheetTable extends AbstractTableModel {

	private Vector<String> colums = null;

	private Vector<Vector<String>> rows = null;

	private OperationManagementBLService controller = null;

	public SheetTable(OperationManagementBLService controller) {
		this.controller = controller;
	}

	/**
	 * 初始化
	 */
	public void init() {
		this.colums = new Vector<String>();
		this.colums.add("日期");
		this.colums.add("类型");
		this.colums.add("单据ID");
		this.colums.add("所属员工ID");
		this.colums.add("状态");
		this.colums.add("内容");

		this.rows = new Vector<Vector<String>>();
		// for (int i = 0; i < 70; i++) {
		// Vector<String> temp=new Vector<String>();
		// temp.add("2015/11/28 12:32:02");
		// temp.add("收款单");
		// temp.add("00000000");
		// temp.add("0000001");
		// temp.add("已提交");
		// temp.add("收款200xx发生的发生地方人挺好挺好认购的公");
		// this.rows.add(temp);
		// }
	}

	/**
	 * 根据单据类型显示
	 * 
	 * @param controller
	 * @param string
	 * @return
	 * @throws RemoteException 
	 */
	public boolean showByType(String type) throws RemoteException {
			this.colums = new Vector<String>();
			this.colums.add("日期");
			this.colums.add("类型");
			this.colums.add("单据ID");
			this.colums.add("所属员工ID");
			this.colums.add("状态");
			this.colums.add("内容");

			this.rows = new Vector<Vector<String>>();

			ArrayList<SheetVO> voList;

			voList = this.controller.sheetShow(type);

			if (voList.size() == 0) {
				return false;
			}
			for (int i = 0; i < voList.size(); i++) {
				Vector<String> temp = new Vector<String>();
				SheetVO vo = voList.get(i);
				temp.add(vo.getDate());
				temp.add(vo.getType());
				temp.add(vo.getId());
				temp.add(vo.getEmployeeId());
				temp.add(vo.getState());
				temp.add(vo.getDescription());
				this.rows.add(temp);
			}
		return true;
	}

	/**
	 * 显示该类型尚未审批的单据
	 * 
	 * @param controller2
	 * @param type
	 * @throws RemoteException 
	 */
	public boolean showNoApproval(String type) throws RemoteException {
			this.colums = new Vector<String>();
			this.colums.add("日期");
			this.colums.add("类型");
			this.colums.add("单据ID");
			this.colums.add("所属员工ID");
			this.colums.add("状态");
			this.colums.add("内容");

			this.rows = new Vector<Vector<String>>();

			ArrayList<SheetVO> voList;

			voList = this.controller.sheetShow(type);

			for (int i = 0; i < voList.size(); i++) {
				Vector<String> temp = new Vector<String>();
				SheetVO vo = voList.get(i);
				if (vo.getState().equals("提交")) {
					temp.add(vo.getDate());
					temp.add(vo.getType());
					temp.add(vo.getId());
					temp.add(vo.getEmployeeId());
					temp.add(vo.getState());
					temp.add(vo.getDescription());
					this.rows.add(temp);
				}
			}
			if (this.rows.size() == 0) {
				return false;
			}
		return true;

	}

	public int getColumnCount() {

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
