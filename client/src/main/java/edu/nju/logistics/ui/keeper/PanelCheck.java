package edu.nju.logistics.ui.keeper;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.AbstractTableModel;

import edu.nju.logistics.bl.impl.storehousemanagement.StorehouseManagementController;
import edu.nju.logistics.bl.service.storehousemanagement.StorehouseManagementBLService;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.commonButton.commonButton;
import edu.nju.logistics.ui.util.ExportExcelUtil;
import edu.nju.logistics.ui.util.TimeUtil;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.StorehouseVO;
import edu.nju.logistics.vo.UserVO;

/**
 * 库存盘点界面（货物列表）
 * 
 * @author HanzZou
 *
 */
public class PanelCheck extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel batchTime, time = null;

	private JButton confirm_button, excel_button = null;

	private Cursor cursor = new Cursor(Cursor.HAND_CURSOR);

	private UserVO user = null;

	private StorehouseManagementBLService controller = null;
	
	private DisconnectInformer disconnectInformer = null;

	private JTable table = null;

	private Table model = null;

	private JScrollPane jsp = null;

	public PanelCheck(UserVO user, DisconnectInformer d) throws RemoteException {
		this.user = user;
		this.controller = new StorehouseManagementController();
		this.setLayout(null);
		this.setSize(980, 560);
		this.setOpaque(false);
		this.cursor = new Cursor(Cursor.HAND_CURSOR);
		this.initComponent();
		this.disconnectInformer = d;
	}

	private void initComponent() {
		// 标签
		this.batchTime = new JLabel("上一批次时间：");
		this.batchTime.setBounds(680, 120, 120, 40);
		this.add(batchTime);
		// 时间显示
		String patchTime = new String();
		try {
			patchTime = controller.getPatchTime(controller.getInstitutionIDByUser(user.getId()));
		} catch (RemoteException e) {
			this.disconnectInformer.findDisconnect();
		}
		this.time = new JLabel(patchTime);
		this.time.setBounds(680, 200, 160, 40);
		this.add(time);
		// 确认按钮
		this.confirm_button = new commonButton("盘点");
		this.confirm_button.setLocation(830, 480);
		this.confirm_button.setSize(100, 40);
		this.confirm_button.setCursor(cursor);
		this.confirm_button.addMouseListener(this);
		this.add(confirm_button);
		//导出excel表格
		this.excel_button = new commonButton("导出");
		this.excel_button.setBounds(670, 480, 100, 40);
		this.excel_button.setCursor(cursor);
		this.excel_button.addMouseListener(this);
		this.add(this.excel_button);
		// 表格
		this.model = new Table();
		this.table = new JTable(this.model);
		this.table.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14));
		this.table.setFont(new Font("楷体", Font.PLAIN, 14));
		this.jsp = new JScrollPane(table);
		jsp.setBounds(20, 20, 580, 520);
		this.jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		MyTableHandler.initTable(this.table);
		MyTableHandler.initSpane(this.jsp, this.table);
		table.getColumnModel().getColumn(0).setPreferredWidth(130);
		table.getColumnModel().getColumn(1).setPreferredWidth(190);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(60);
		table.getColumnModel().getColumn(4).setPreferredWidth(60);
		table.getColumnModel().getColumn(5).setPreferredWidth(60);
		table.getColumnModel().getColumn(6).setPreferredWidth(360);
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.getTableHeader().setResizingAllowed(false);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.add(this.jsp);

		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//确定
		if (e.getSource() == this.confirm_button) {
			this.model.reset();
			time.setText(TimeUtil.getCurrentTime());
			String centerID = null;
			ArrayList<StorehouseVO> list = new ArrayList<StorehouseVO>();
			try {
				centerID = controller.getInstitutionIDByUser(user.getId());
				list = controller.check(centerID);
			} catch (RemoteException re) {
				this.disconnectInformer.findDisconnect();
				return;
			}
			if(list.size() == 0) {
				GlobalHintInserter.insertGlobalHint("仓库内没有库存！");
				return;
			}
			for (StorehouseVO vo : list) {
				Vector<String> temp = new Vector<String>();
				temp.add(vo.getNumber());
				temp.add(vo.getTime());
				temp.add(vo.getArea());
				temp.add(String.valueOf(vo.getLine()));
				temp.add(String.valueOf(vo.getShelf()));
				temp.add(String.valueOf(vo.getPosition()));
				temp.add(String.valueOf(vo.getDestination()));
				model.rows.add(temp);
			}
			table.repaint();
		}
		//导出excel
		if(e.getSource() == this.excel_button) {
			ExportExcelUtil.exportExcel(table, "local/库存盘点.xls");
			GlobalHintInserter.insertGlobalHint("导出成功！");
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

class Table extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Vector<String> colums = null;

	public Vector<Vector<String>> rows = null;

	public Table() {
		this.colums = new Vector<String>();
		this.colums.add("快递编号");
		this.colums.add("时间");
		this.colums.add("区域");
		this.colums.add("排号");
		this.colums.add("架号");
		this.colums.add("位号");
		this.colums.add("目的地");

		this.rows = new Vector<Vector<String>>();
	}

	@Override
	public int getRowCount() {
		if (this.rows == null) {
			return 0;
		}
		return this.rows.size();
	}

	@Override
	public int getColumnCount() {
		return this.colums.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.rows.get(rowIndex).get(columnIndex);
	}

	@Override
	public String getColumnName(int arg0) {

		return this.colums.get(arg0);
	}

	public void reset() {
		this.rows.removeAllElements();
	}
}
