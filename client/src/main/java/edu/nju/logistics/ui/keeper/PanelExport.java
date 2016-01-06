package edu.nju.logistics.ui.keeper;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import edu.nju.logistics.bl.impl.storehousemanagement.StorehouseManagementController;
import edu.nju.logistics.bl.service.storehousemanagement.StorehouseManagementBLService;
import edu.nju.logistics.po.StorageState;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.commonButton.commonButton;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.StorehouseVO;
import edu.nju.logistics.vo.UserVO;

/**
 * 入库登记界面
 * 
 * @author HanzZou
 *
 */
public class PanelExport extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StorehouseManagementBLService controller = null;
	
	private DisconnectInformer disconnectInformer = null;

	private ArrayList<StorehouseVO> list = null;

	private UserVO user = null;

	private JLabel area, tip = null;

	private JComboBox<String> areas = null;

	private JButton confirm_button, update_button = null;

	private Cursor cursor = null;

	private ExportTable model = null;

	private JTable table = null;

	private JScrollPane jsp = null;

	public PanelExport(UserVO user, DisconnectInformer d) throws RemoteException {
		this.controller = new StorehouseManagementController();
		this.user = user;
		this.setLayout(null);
		this.setSize(980, 560);
		this.setOpaque(false);
		this.initComponent();
		cursor = new Cursor(Cursor.HAND_CURSOR);
		this.disconnectInformer = d;
	}

	private void initComponent() {
		// 确认按钮
		confirm_button = new commonButton("出库");
		confirm_button.setLocation(830, 480);
		confirm_button.setSize(100, 40);
		confirm_button.setCursor(cursor);
		confirm_button.addMouseListener(this);
		this.add(confirm_button);
		//更新按钮
		update_button = new commonButton("刷新");
		update_button.setBounds(680, 480, 100, 40);
		update_button.setCursor(cursor);
		update_button.addMouseListener(this);
		this.add(this.update_button);
		//提示
		tip = new JLabel("提示:按住ctrl或command可多选。");
		tip.setBounds(680, 300, 300, 30);
        tip.setForeground(Color.red);
		this.add(this.tip);
		// 表格初始化
		this.model = new ExportTable();
		this.table = new JTable(model);
		this.table.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14));
		this.table.setFont(new Font("楷体", Font.PLAIN, 14));
		this.jsp = new JScrollPane(table);
		jsp.setBounds(20, 20, 620, 520);
		MyTableHandler.initTable(this.table);
		MyTableHandler.initSpane(this.jsp, this.table);
		table.getColumnModel().getColumn(0).setPreferredWidth(160);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(60);
		table.getColumnModel().getColumn(4).setPreferredWidth(60);
		table.getColumnModel().getColumn(5).setPreferredWidth(60);
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.getTableHeader().setResizingAllowed(false);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.add(this.jsp);
		// 区域
		this.area = new JLabel("区域:");
		this.area.setBounds(680, 120, 80, 30);
		this.add(this.area);
		String[] s = { "铁运区", "航运区", "汽运区", "机动区" };
		this.areas = new JComboBox<String>(s);
		this.areas.setBounds(760, 120, 120, 30);
//		this.areas.setBounds(780, 140, 180, 30);
		this.add(this.areas);
		this.areas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}

		});
		this.initTable();
	}
	
	private void initTable() {
		model.reset();
		list = new ArrayList<StorehouseVO>();
		try {
			list = controller.show(controller.getInstitutionIDByUser(user.getId()), (String) areas.getSelectedItem());
		} catch (RemoteException e) {
			this.disconnectInformer.findDisconnect();
			return;
		}
		for (StorehouseVO vo : list) {
			if (vo.getState() == StorageState.IN) {
				Vector<String> temp = new Vector<String>();
				temp.add(vo.getNumber());
				temp.add(vo.getDestination());
				temp.add(vo.getArea());
				temp.add(String.valueOf(vo.getLine()));
				temp.add(String.valueOf(vo.getShelf()));
				temp.add(String.valueOf(vo.getPosition()));
				model.rows.add(temp);
			}
		}
		table.repaint();
	}

	// 表格刷新
	private void updateTable() {
		this.initTable();
		GlobalHintInserter.insertGlobalHint("刷新成功！");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == this.confirm_button) {
			if(table.getSelectedRow() == -1) {
				GlobalHintInserter.insertGlobalHint("请选择出库项！");
				return;
			}
			int[] rows = table.getSelectedRows();
			for (int i = 0; i < rows.length; i++) {
				try {
					this.controller.exportItem((String) table.getValueAt(rows[i], 0), this.user.getId());
				} catch (RemoteException e1) {
					this.disconnectInformer.findDisconnect();
					return;
				}
			}
			this.updateTable();
		}
		if(e.getSource() == this.update_button) {
			this.updateTable();
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

class ExportTable extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Vector<String> colums = null;

	public Vector<Vector<String>> rows = null;

	public ExportTable() {
		this.colums = new Vector<String>();
		this.colums.add("快递编号");
		this.colums.add("收件地址");
		this.colums.add("区域");
		this.colums.add("架号");
		this.colums.add("排号");
		this.colums.add("位号");

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