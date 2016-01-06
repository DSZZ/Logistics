package edu.nju.logistics.ui.keeper;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import edu.nju.logistics.bl.impl.storehousemanagement.StorehouseManagementController;
import edu.nju.logistics.bl.service.storehousemanagement.StorehouseManagementBLService;
import edu.nju.logistics.po.StorageState;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.commonButton.commonButton;
import edu.nju.logistics.ui.util.TimeUtil;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.StorehouseVO;
import edu.nju.logistics.vo.UserVO;

public class PanelInitialize extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StorehouseManagementBLService controller = null;
	
	private DisconnectInformer disconnectInformer = null;

	private UserVO user = null;

	private JLabel trainLabel1, trainLabel2, trainLabel3, planeLabel1, planeLabel2, planeLabel3, carLabel1, carLabel2,
			carLabel3 = null;

	private JTextField train1, train2, train3, plane1, plane2, plane3, car1, car2, car3 = null;

	private JButton confirm_button, cancel_button, cancel_button2, next = null;

	private JLabel number, area = null;

	private JTextField number_text = null;

	private JComboBox<String> area_text = null;

	private JTable table = null;

	private InitializeTable model = null;

	private JScrollPane jsp = null;

	private Cursor cursor = new Cursor(Cursor.HAND_CURSOR);

	public PanelInitialize(UserVO user, DisconnectInformer d) throws RemoteException {
		this.user = user;
		this.controller = new StorehouseManagementController();
		this.setLayout(null);
		this.setSize(980, 560);
		this.setOpaque(false);
		this.cursor = new Cursor(Cursor.HAND_CURSOR);
		this.initComponent1();
		this.disconnectInformer = d;
	}

	private void initComponent1() {
		// 标签
		trainLabel1 = new JLabel("铁运区排数:");
		trainLabel2 = new JLabel("铁运区架数:");
		trainLabel3 = new JLabel("铁运区位数:");
		planeLabel1 = new JLabel("航运区排数:");
		planeLabel2 = new JLabel("航运区架数:");
		planeLabel3 = new JLabel("航运区位数:");
		carLabel1 = new JLabel("汽运区排数:");
		carLabel2 = new JLabel("汽运区架数:");
		carLabel3 = new JLabel("汽运区位数:");
		JLabel[] labels = { trainLabel1, trainLabel2, trainLabel3, planeLabel1, planeLabel2, planeLabel3, carLabel1,
				carLabel2, carLabel3 };
		for (JLabel label : labels) {
			label.setSize(100, 30);
			this.add(label);
		}
		int temp1 = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				labels[temp1++].setLocation(100 + j * 240, 120 + i * 120);
			}
		}
		// 输入框
		train1 = new JTextField();
		train2 = new JTextField();
		train3 = new JTextField();
		plane1 = new JTextField();
		plane2 = new JTextField();
		plane3 = new JTextField();
		car1 = new JTextField();
		car2 = new JTextField();
		car3 = new JTextField();
		JTextField[] texts = { train1, train2, train3, plane1, plane2, plane3, car1, car2, car3 };
		for (JTextField text : texts) {
			text.setSize(80, 30);
			text.setText("100");
			this.add(text);
		}
		int temp2 = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				texts[temp2++].setLocation(220 + j * 240, 120 + i * 120);
			}
		}

		// 取消按钮
		cancel_button = new commonButton("取消");
		cancel_button.setLocation(680, 480);
		cancel_button.setSize(100, 40);
		cancel_button.setCursor(cursor);
		cancel_button.addMouseListener(this);
		this.add(cancel_button);
		// 确认按钮
		next = new commonButton("下一步");
		next.setLocation(830, 480);
		next.setSize(100, 40);
		next.setCursor(cursor);
		next.addMouseListener(this);
		this.add(next);

		this.repaint();
	}

	private void initComponent2() {
		// 快递单号
		number = new JLabel("快递单号:");
		number.setBounds(650, 70, 60, 30);
		this.add(this.number);
		number_text = new JTextField();
		number_text.setBounds(750, 70, 120, 30);
		this.add(this.number_text);
		// 区域
		area = new JLabel("区域:");
		area.setBounds(650, 150, 40, 30);
		this.add(this.area);
		String[] areas = { "航运区", "铁运区", "汽运区" };
		area_text = new JComboBox<String>(areas);
		area_text.setBounds(750, 150, 100, 30);
		this.add(this.area_text);
		// 取消按钮
		cancel_button2 = new commonButton("完成");
		cancel_button2.setLocation(680, 480);
		cancel_button2.setSize(100, 40);
		cancel_button2.setCursor(cursor);
		cancel_button2.addMouseListener(this);
		this.add(cancel_button2);
		// 确认按钮
		confirm_button = new commonButton("入库");
		confirm_button.setLocation(830, 480);
		confirm_button.setSize(100, 40);
		confirm_button.setCursor(cursor);
		confirm_button.addMouseListener(this);
		this.add(confirm_button);
		// 表格
		this.model = new InitializeTable();
		this.table = new JTable(this.model);
		this.table.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14));
		this.table.setFont(new Font("楷体", Font.PLAIN, 14));
		this.jsp = new JScrollPane(table);
		jsp.setBounds(20, 20, 580, 520);
		MyTableHandler.initTable(this.table);
		MyTableHandler.initSpane(this.jsp, this.table);
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setPreferredWidth(80);
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.getTableHeader().setResizingAllowed(false);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.add(this.jsp);

		this.updateUI();
	}

	/**
	 * 判断字符串为数字
	 * 
	 * @param str
	 * @return
	 */
	private boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 清空文本框
	 */
	private void clearTextField() {
		this.number_text.setText("");
	}
	
	/**
	 * 判断数字合法
	 * @param i
	 * @return
	 */
	private boolean isLeagal(int i) {
		if(i < 1000 && i > 10) {
			return true;
		}
		return false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == this.cancel_button)
			this.setVisible(false);
		if (e.getSource() == this.next) {
			if (train1.getText().equals("") || train2.getText().equals("") || train3.getText().equals("")
					|| plane1.getText().equals("") || plane2.getText().equals("") || plane3.getText().equals("")
					|| car1.getText().equals("") || car2.getText().equals("") || car3.getText().equals("")) {
				GlobalHintInserter.insertGlobalHint("请输入完整！");
				return;
			}
			if(!isNumeric(train1.getText()) || !isNumeric(train2.getText()) || !isNumeric(train3.getText()) || 
					!isNumeric(plane1.getText()) || !isNumeric(plane2.getText()) || !isNumeric(plane3.getText()) || 
					!isNumeric(car1.getText()) || !isNumeric(car2.getText()) || !isNumeric(car3.getText())) {
				GlobalHintInserter.insertGlobalHint("请输入整数！");
				return;
			}
			int train1 = Integer.valueOf(this.train1.getText());
			int train2 = Integer.valueOf(this.train2.getText());
			int train3 = Integer.valueOf(this.train3.getText());
			int plane1 = Integer.valueOf(this.plane1.getText());
			int plane2 = Integer.valueOf(this.plane2.getText());
			int plane3 = Integer.valueOf(this.plane3.getText());
			int car1 = Integer.valueOf(this.car1.getText());
			int car2 = Integer.valueOf(this.car2.getText());
			int car3 = Integer.valueOf(this.car3.getText());
			if(!isLeagal(train1) || !isLeagal(train2) || !isLeagal(train3) || !isLeagal(plane1) || !isLeagal(plane2) || 
					!isLeagal(plane3) || !isLeagal(car1) || !isLeagal(car2) || !isLeagal(car3)) {
				GlobalHintInserter.insertGlobalHint("请合理规模！");
				return;
			}
			try {
				controller.setScale(controller.getInstitutionIDByUser(user.getId()), "铁运区", train1, train2, train3);
				controller.setScale(controller.getInstitutionIDByUser(user.getId()), "航运区", plane1, plane2, plane3);
				controller.setScale(controller.getInstitutionIDByUser(user.getId()), "汽运区", car1, car2, car3);
			} catch (RemoteException re) {
				this.disconnectInformer.findDisconnect();
				return;
			}
			this.removeAll();
			this.initComponent2();
			GlobalHintInserter.insertGlobalHint("规模设置成功！");
		}
		if (e.getSource() == this.confirm_button) {
			if (!this.isNumeric(this.number_text.getText()) || this.number_text.getText().length() != 10) {
				GlobalHintInserter.insertGlobalHint("快递编号应为10位数字！");
				this.clearTextField();
				return;
			}
			int[] place = null;
			try {
				place = controller.getPlace(controller.getInstitutionIDByUser(user.getId()), (String) area_text.getSelectedItem());
				StorehouseVO vo = new StorehouseVO(controller.getInstitutionIDByUser(user.getId()),
						TimeUtil.getCurrentTime(), (String) area_text.getSelectedItem(), place[0], place[1], place[2],
						number_text.getText(), controller.getOrderAddress(number_text.getText()),
						controller.getMoney(number_text.getText()), StorageState.IN);
				controller.addToStorage(vo);
				Vector<String> temp = new Vector<String>();
				temp.add(number_text.getText());
				temp.add(controller.getOrderAddress(number_text.getText()));
				temp.add((String) area_text.getSelectedItem());
				temp.add(String.valueOf(place[0]));
				temp.add(String.valueOf(place[1]));
				temp.add(String.valueOf(place[2]));
				this.model.rows.add(temp);
				table.repaint();
				this.clearTextField();
			} catch (RemoteException re) {
				this.disconnectInformer.findDisconnect();
			}
		}
		if (e.getSource() == this.cancel_button2) {
			this.removeAll();
			this.initComponent1();
			this.setVisible(false);
			GlobalHintInserter.insertGlobalHint("初始化成功！");
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}

class InitializeTable extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Vector<String> colums = null;

	public Vector<Vector<String>> rows = null;

	public InitializeTable() {
		this.colums = new Vector<String>();
		this.colums.add("快递编号");
		this.colums.add("目的地");
		this.colums.add("区域");
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

}
