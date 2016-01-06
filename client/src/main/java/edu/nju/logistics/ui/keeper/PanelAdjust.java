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
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.commonButton.commonButton;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.StorehouseVO;
import edu.nju.logistics.vo.UserVO;

/**
 * 库存调整界面
 * 
 * @author HanzZou
 *
 */
public class PanelAdjust extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserVO user;

	private StorehouseManagementBLService controller;
	
	private DisconnectInformer disconnectInformer = null;

	private Cursor cursor = null;

	private JComboBox<String> area = null;

	private JLabel selectArea, showAlarm, alarmValue, tip = null;

	private JButton confirm_button, update_button = null;

	private JTable table = null;

	private AdjustTable model = null;

	private JScrollPane jsp = null;

	private ArrayList<StorehouseVO> list = null;

	public PanelAdjust(UserVO user, DisconnectInformer d) throws RemoteException {
		this.user = user;
		this.controller = new StorehouseManagementController();
		this.setSize(980, 560);
		this.setLayout(null);
		this.cursor = new Cursor(Cursor.HAND_CURSOR);
		this.setOpaque(false);
		this.initComponent();
		this.disconnectInformer = d;
	}

	private void initComponent() {
		// 即时警戒值显示
		showAlarm = new JLabel("即时比例为：");
		showAlarm.setBounds(680, 180, 160, 20);
		this.add(this.showAlarm);
		// 显示区域
		alarmValue = new JLabel();
		alarmValue.setBounds(680, 240, 50, 20);
		this.add(this.alarmValue);
		// 区域显示标签
		selectArea = new JLabel("请选择区域：");
		selectArea.setBounds(680, 60, 160, 20);
		this.add(this.selectArea);
		//tip
		tip = new JLabel("待出库项目不可移动，计算即时比例时计入。");
		tip.setBounds(680, 300, 300, 20);
        tip.setForeground(Color.red);
        this.add(this.tip);
		// 区域选择
		String[] areas = { "航运区", "铁运区", "汽运区" };
		area = new JComboBox<String>(areas);
		area.setBounds(680, 120, 100, 20);
		area.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}

		});
		this.add(this.area);
		// 确认按钮
		confirm_button = new commonButton("移至机动区");
		confirm_button.setLocation(830, 480);
		confirm_button.setSize(100, 40);
		confirm_button.setCursor(cursor);
		confirm_button.addMouseListener(this);
		this.add(confirm_button);
		// 刷新按钮
		update_button = new commonButton("刷新");
		update_button.setBounds(680, 480, 100, 40);
		update_button.setCursor(cursor);
		update_button.addMouseListener(this);
		this.add(update_button);
		// 表格
		this.model = new AdjustTable();
		this.table = new JTable(this.model);
		this.table.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14));
		this.table.setFont(new Font("楷体", Font.PLAIN, 14));
		this.jsp = new JScrollPane(table);
		jsp.setBounds(20, 20, 580, 520);
		MyTableHandler.initTable(this.table);
		MyTableHandler.initSpane(this.jsp, this.table);
		table.getColumnModel().getColumn(0).setPreferredWidth(160);
		table.getColumnModel().getColumn(1).setPreferredWidth(260);
		table.getColumnModel().getColumn(2).setPreferredWidth(90);
		table.getColumnModel().getColumn(3).setPreferredWidth(90);
		table.getColumnModel().getColumn(4).setPreferredWidth(90);
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.getTableHeader().setResizingAllowed(false);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.add(this.jsp);
		this.updateTable();
	}
	
	private void updateTable() {
		try {
			model.reset();
			list = controller.show(controller.getInstitutionIDByUser(user.getId()),
					(String) area.getSelectedItem());
			for (StorehouseVO vo : list) {
				Vector<String> temp = new Vector<String>();
				temp.add(vo.getNumber());
				temp.add(vo.getTime());
				temp.add(String.valueOf(vo.getLine()));
				temp.add(String.valueOf(vo.getShelf()));
				temp.add(String.valueOf(vo.getPosition()));
				model.rows.add(temp);
			}
			System.out.println(list.size());
			table.updateUI();
			getAlarmNow();
		} catch (RemoteException re) {
			disconnectInformer.findDisconnect();
			return;
		}
	}
	
	private void getAlarmNow() {
		try {
			double alarm = controller.getPercentage(
					controller.getInstitutionIDByUser(user.getId()), (String) area.getSelectedItem()) * 100;
			java.text.DecimalFormat df=new java.text.DecimalFormat("#.###"); 
			alarmValue.setText(df.format(alarm) + "%");
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
			return;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			if (e.getSource() == confirm_button) {
				controller.adjust((String) table.getValueAt(table.getSelectedRow(), 0),
						controller.getInstitutionIDByUser(user.getId()));
				list = controller.show(controller.getInstitutionIDByUser(user.getId()),
						(String) area.getSelectedItem());
				for (StorehouseVO vo : list) {
					Vector<String> temp = new Vector<String>();
					temp.add(vo.getNumber());
					temp.add(vo.getTime());
					temp.add(String.valueOf(vo.getLine()));
					temp.add(String.valueOf(vo.getShelf()));
					temp.add(String.valueOf(vo.getPosition()));
					model.rows.add(temp);
				}
				table.repaint();
				this.getAlarmNow();
				GlobalHintInserter.insertGlobalHint("调整成功！");
			}
			if(e.getSource() == update_button) {
				this.updateTable();
				GlobalHintInserter.insertGlobalHint("刷新成功！");
			}
		} catch (RemoteException re) {
			disconnectInformer.findDisconnect();
			return;
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

class AdjustTable extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Vector<String> colums = null;

	public Vector<Vector<String>> rows = null;

	public AdjustTable() {
		this.colums = new Vector<String>();
		this.colums.add("快递编号");
		this.colums.add("时间");
		this.colums.add("排号");
		this.colums.add("架号");
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
