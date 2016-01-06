package edu.nju.logistics.ui.centerstaff;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class TablePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 表格
	 */
	private JTable table = null;
	
	/**
	 * 模型
	 */
	private TableModel model = null;
	
	/**
	 * 滑轮滚动
	 */
	private JScrollPane jsp = null;
	
	/**
	 * 列表
	 */
	private ArrayList<OrderVO> list = null;

	public TablePanel(ArrayList<OrderVO> list) {
		this.list = list;
		this.setSize(560, 400);
		this.setLayout(null);
		this.setOpaque(false);
		this.initComponent();
	}
	
	private void initComponent() {
		this.model = new TableModel();
		this.table = new JTable(this.model);
		this.table.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14));
		this.table.setFont(new Font("楷体", Font.PLAIN, 14));
        this.jsp = new JScrollPane(table);
        jsp.setBounds(0, 0, 560, 400);
		MyTableHandler.initTable(this.table);
		MyTableHandler.initSpane(this.jsp, this.table);
		table.getColumnModel().getColumn(0).setPreferredWidth(130);
        table.getColumnModel().getColumn(1).setPreferredWidth(430);
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.getTableHeader().setResizingAllowed(false);
        this.add(this.jsp);
        this.initTable();
	}
	
	private void initTable() {
		for(OrderVO vo : list) {
			Vector<String> temp = new Vector<String>();
			temp.add(vo.getOrderID());
			temp.add(vo.getReceiverAddress());
			this.model.rows.add(temp);
		}
		this.table.repaint();
	}

}

class TableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Vector<String> columns = null;
	
	public Vector<Vector<String>> rows = null;
	
	public TableModel() {
		this.columns = new Vector<String>();
		this.columns.add("快件编号");
		this.columns.add("收件地址");
		this.rows = new Vector<Vector<String>>();
	}
	
	@Override
	public int getRowCount() {
		if(this.rows==null){
			return 0;
		}
		return this.rows.size();
	}

	@Override
	public int getColumnCount() {
		return this.columns.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.rows.get(rowIndex).get(columnIndex);
	}
	
	@Override
	public String getColumnName(int arg0) {
		return this.columns.get(arg0);
	}
	
}
