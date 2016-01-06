package edu.nju.logistics.ui.manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;

import edu.nju.logistics.bl.service.operationmanagement.OperationManagementBLService;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.commonButton.commonButton;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.DistanceVO;
import edu.nju.logistics.vo.PriceVO;

@SuppressWarnings("serial")
public class ConstantSetPanel extends JPanel implements MouseListener {
	/**
	 * 显示区宽度
	 */
	public static final int SHOWPANEL_WIDTH = 980;
	/**
	 * 显示区高度
	 */
	public static final int SHOWPANEL_HEIGHT = 560;

	private static final Image BACKGROUND = new ImageIcon("Image/manager/background.png").getImage();

//	private static final ImageIcon UPDATE = new ImageIcon("Image/manager/update.png");
//
//	private static final ImageIcon CONFIRM = new ImageIcon("Image/manager/confirm.png");
//
//	private static final ImageIcon CLEAR = new ImageIcon("Image/manager/clear.png");

	// private static final ImageIcon MODIFY=new
	// ImageIcon("Image/manager/modify.png");

	private static final Font FONT1 = new Font("黑体", Font.BOLD, 20);

	private static final Font FONT2 = new Font("楷体", Font.PLAIN, 14);

	private JTabbedPane tabbedPanel = null;
	/**
	 * 制定价格面板
	 */
	private JPanel pricePanel = null;
	/**
	 * 制定距离面板
	 */
	private JPanel distancePanel = null;
	/**
	 * 制定价格组件
	 */
	private JLabel single_lab = null, min_lab = null,  kilometre_lab = null,
			unit_lab = null, car_lab = null, train_lab = null, plane_lab = null, info_lab = null, express_lab = null;

	private JTextField min_jtf = null, car_jtf = null, train_jtf = null, plane_jtf = null, express_jtf = null;
	/**
	 * 制定距离组件
	 */
	private JScrollPane jsp = null;

	private JLabel notice_lab = null, prompt_lab = null, count_lab = null;
	
	private commonButton update_lab = null, ok_lab = null,clear_lab = null, confirm_lab = null;

	private JTextField disntance_jtf = null;

	private JTable disTable = null;

	private DistanceTable disModel = null;

	private Cursor cursor = null;

	private OperationManagementBLService controller = null;

	private PriceVO priceVO = null;

	private HashMap<String, Double> map = null;
	
	private DisconnectInformer disconnectInformer=null;

	public ConstantSetPanel(OperationManagementBLService controller,DisconnectInformer disconnectInformer) {
		try {
			this.setOpaque(false);
			this.setLayout(new BorderLayout());
			this.setSize(SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);

			this.controller = controller;
			this.disconnectInformer=disconnectInformer;
			this.cursor = new Cursor(Cursor.HAND_CURSOR);

			this.tabbedPanel = new JTabbedPane();
			this.tabbedPanel.addTab("服务价格", this.createPricePanel());
			this.tabbedPanel.addTab("城市距离", this.createDistancePanel());
			UIManager.put("TabbedPane.contentOpaque", false);
			this.tabbedPanel.setOpaque(false);
			this.tabbedPanel.setFont(new Font("楷体", Font.BOLD, 16));
			this.add(this.tabbedPanel, "Center");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建城市距离制定面板
	 * 
	 * @return
	 * @throws RemoteException
	 */
	private JPanel createDistancePanel() throws RemoteException {
		this.distancePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(BACKGROUND, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		this.distancePanel.setLayout(null);
		this.distancePanel.setOpaque(false);

		this.disModel = new DistanceTable(this.controller);
		this.disTable = new JTable(this.disModel);
		this.disTable.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14));
		this.disTable.setFont(new Font("楷体", Font.PLAIN, 14));
		this.disTable.addMouseListener(this);
		// //设置列宽
		// disTable.getColumnModel().getColumn(0).setPreferredWidth(80);
		// disTable.getColumnModel().getColumn(1).setPreferredWidth(80);
		// disTable.getColumnModel().getColumn(2).setPreferredWidth(610);
		// 不可整列移动
		this.disTable.getTableHeader().setReorderingAllowed(false);
		// 不可拉动表格
		this.disTable.getTableHeader().setResizingAllowed(false);
		this.jsp = new JScrollPane(this.disTable);
		this.decorateTable();
		this.jsp.setBounds(5, 5, 970, 480);
		this.distancePanel.add(this.jsp);

		this.count_lab = new JLabel();
		this.count_lab.setText("当前记录为 " + this.disTable.getRowCount());
		this.count_lab.setFont(new Font("宋体", Font.BOLD, 14));
		this.count_lab.setBounds(5, 495, 200, 25);
		this.distancePanel.add(this.count_lab);

		this.prompt_lab = new JLabel();
		this.prompt_lab.setText("(点击表项可修改距离)");
		this.prompt_lab.setFont(new Font("楷体", Font.BOLD, 16));
		this.prompt_lab.setForeground(Color.GREEN);
		this.prompt_lab.setBounds(250, 495, 200, 25);
		this.distancePanel.add(this.prompt_lab);

		this.notice_lab = new JLabel();
		this.notice_lab.setText("请先刷新！");
		this.notice_lab.setForeground(Color.RED);
		this.notice_lab.setFont(new Font("楷体", Font.BOLD, 16));
		this.notice_lab.setBounds(550, 495, 130, 25);
		this.distancePanel.add(this.notice_lab);

		this.disntance_jtf = new JTextField();
		this.disntance_jtf.setBounds(635, 497, 100, 25);
		this.disntance_jtf.setVisible(false);
		this.distancePanel.add(this.disntance_jtf);

		this.update_lab = new commonButton("更新");
		this.update_lab.setBounds(860, 485, 100, 40);
		this.update_lab.addMouseListener(this);
		this.update_lab.setCursor(this.cursor);
		this.distancePanel.add(this.update_lab);

		this.ok_lab = new commonButton("确认");
		this.ok_lab.setBounds(750, 485, 100, 40);
		this.ok_lab.addMouseListener(this);
		this.ok_lab.setCursor(this.cursor);
		this.distancePanel.add(this.ok_lab);

		return this.distancePanel;
	}

	/**
	 * 创建服务价格制定面板
	 * 
	 * @return
	 * @throws RemoteException
	 */
	private JPanel createPricePanel() throws RemoteException {
		this.priceVO = this.controller.priceShow();
		this.map = this.priceVO.getMap();

		this.pricePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(BACKGROUND, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		this.pricePanel.setLayout(null);
		this.pricePanel.setOpaque(false);

		this.car_lab = new JLabel("汽车:");
		this.car_lab.setBounds(400, 20, 100, 30);
		this.car_lab.setFont(FONT1);
		this.pricePanel.add(this.car_lab);

		this.car_jtf = new JTextField();
		this.car_jtf.setText(this.map.get("汽车") + "");
		this.car_jtf.setBounds(400, 60, 120, 25);
		this.pricePanel.add(this.car_jtf);

		this.train_lab = new JLabel("火车:");
		this.train_lab.setBounds(400, 105, 100, 30);
		this.train_lab.setFont(FONT1);
		this.pricePanel.add(this.train_lab);

		this.train_jtf = new JTextField();
		this.train_jtf.setText(this.map.get("火车") + "");
		this.train_jtf.setBounds(400, 145, 120, 25);
		this.pricePanel.add(this.train_jtf);

		this.plane_lab = new JLabel("飞机:");
		this.plane_lab.setBounds(400, 190, 100, 30);
		this.plane_lab.setFont(FONT1);
		this.pricePanel.add(this.plane_lab);

		this.plane_jtf = new JTextField();
		this.plane_jtf.setText(this.map.get("飞机") + "");
		this.plane_jtf.setBounds(400, 230, 120, 25);
		this.pricePanel.add(this.plane_jtf);

		this.info_lab = new JLabel();
		this.info_lab.setText("(注：以上均为每公里每吨的价格，单位为元)");
		this.info_lab.setForeground(Color.RED);
		this.info_lab.setFont(new Font("楷体", Font.BOLD, 16));
		this.info_lab.setBounds(300, 265, 400, 25);
		this.pricePanel.add(this.info_lab);

		this.express_lab = new JLabel("标准快递的运费价格:");
		this.express_lab.setBounds(360, 310, 300, 30);
		this.express_lab.setFont(FONT1);
		this.pricePanel.add(this.express_lab);

		this.kilometre_lab = new JLabel("公里数/1000*");
		this.kilometre_lab.setBounds(315, 350, 100, 25);
		this.kilometre_lab.setFont(FONT2);
		this.pricePanel.add(this.kilometre_lab);

		this.express_jtf = new JTextField();
		this.express_jtf.setText(this.map.get("标准快递运费价格") + "");
		this.express_jtf.setBounds(400, 350, 120, 25);
		this.pricePanel.add(this.express_jtf);

		this.unit_lab = new JLabel("元每公斤");
		this.unit_lab.setBounds(525, 350, 100, 25);
		this.unit_lab.setFont(FONT2);
		this.pricePanel.add(this.unit_lab);

		this.min_lab = new JLabel("标准快递的最低运费:");
		this.min_lab.setFont(FONT1);
		this.min_lab.setBounds(360, 395, 300, 30);
		this.pricePanel.add(this.min_lab);

		this.min_jtf = new JTextField();
		this.min_jtf.setText(this.map.get("标准快递的最低运费") + "");
		this.min_jtf.setBounds(400, 435, 120, 25);
		this.pricePanel.add(this.min_jtf);

		this.single_lab = new JLabel("元");
		this.single_lab.setFont(FONT2);
		this.single_lab.setBounds(525, 435, 30, 25);
		this.pricePanel.add(this.single_lab);

		this.confirm_lab = new commonButton("确认");
		this.confirm_lab.addMouseListener(this);
		this.confirm_lab.setBounds(345, 485, 100, 40);
		this.confirm_lab.setCursor(this.cursor);
		this.pricePanel.add(this.confirm_lab);

		this.clear_lab = new commonButton("清空");
		this.clear_lab.addMouseListener(this);
		this.clear_lab.setBounds(500, 485, 100, 40);
		this.clear_lab.setCursor(this.cursor);
		this.pricePanel.add(this.clear_lab);

		return this.pricePanel;
	}
	/**
	 * 表格装饰
	 */
	private void decorateTable(){
		MyTableHandler.initTable(this.disTable);
		MyTableHandler.initSpane(this.jsp, this.disTable);
	}
	/**
	 * 判断输入距离是否合法
	 * 
	 * @return
	 */
	private boolean isLegalDistance(String line) {
		int count = 0;

		if (line.equals("") || line.length() > 8) {
			return false;
		}
		for (int i = 0; i < line.length(); i++) {
			if (((line.charAt(i) < '0') && (line.charAt(i) != '.')) || (line.charAt(i) > '9')) {
				return false;
			} else if (line.charAt(i) == '.') {
				count++;
				if (count == 2) {
					return false;
				}
			}
		}
		double temp = Double.parseDouble(line);
		if (temp == 0) {
			return false;
		}

		return true;
	}
	/**
	 * 简化字符串
	 * @param string
	 * @return
	 */
	private String simplifyString(String string){
		boolean flag=false;
		int index=0;
		int padding=0;
		int length=string.length();
		for(int i=0;i<length;i++){
			if(string.charAt(i)=='.'){
				flag=true;
				index=i;
			}
		}
		
		if(flag){
			int i;
			for(i=length-1;i>index&&string.charAt(i)=='0';i--);
			padding=length-1-i;
			if(i==index){
				string=string.substring(0,string.length()-padding-1);
			}
			else{
				string=string.substring(0,string.length()-padding);
			}
		}
		return string;
	}
	/**
	 * 根据价格输入判断是否合法
	 * 
	 * @param line
	 * @return
	 */
	private boolean isLegalPrice(String line) {
		int count = 0;

		if (line.equals("") || line.length() > 10) {
			return false;
		}
		for (int i = 0; i < line.length(); i++) {
			if (((line.charAt(i) < '0') && (line.charAt(i) != '.')) || (line.charAt(i) > '9')) {
				return false;
			} else if (line.charAt(i) == '.') {
				count++;
				if (count == 2) {
					return false;
				}
				if ((i != line.length() - 3) && (i != line.length() - 2)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 价格确认操作
	 * 
	 * @throws RemoteException
	 */
	private void confirmForPrice() throws RemoteException {
		String car_price = this.car_jtf.getText();
		String train_price = this.train_jtf.getText();
		String plane_price = this.plane_jtf.getText();
		String express_price = this.express_jtf.getText();
		String min_price = this.min_jtf.getText();
		//去0操作
		String car_price_new = this.simplifyString(car_price);
		String train_price_new = this.simplifyString(train_price);
		String plane_price_new = this.simplifyString(plane_price);
		String express_price_new = this.simplifyString(express_price);
		String min_price_new = this.simplifyString(min_price);
		// 如果输入非法，则返回
		if ((!this.isLegalPrice(car_price)) || (!this.isLegalPrice(train_price)) || (!this.isLegalPrice(plane_price))
				|| (!this.isLegalPrice(express_price)) || (!this.isLegalPrice(min_price))) {
			JOptionPane.showMessageDialog(this, "输入非法！");
			return;
		}
		// 如果未做修改，则返回
		// System.out.println(car_price+" "+this.map.get("汽车"));
		if ((car_price_new.equals(this.simplifyString(this.map.get("汽车") + ""))) 
				&& (train_price_new.equals(this.simplifyString(this.map.get("火车") + "")))
				&& (plane_price_new.equals(this.simplifyString(this.map.get("飞机") + "")))
				&& (express_price_new.equals(this.simplifyString(this.map.get("标准快递运费价格") + "")))
				&& (min_price_new.equals(this.simplifyString(this.map.get("标准快递的最低运费") + "")))) {
			JOptionPane.showMessageDialog(this, "请做相应修改！");
			return;
		}
		// 新数据覆盖旧数据
		this.map.put("汽车", Double.parseDouble(car_price));
		this.map.put("火车", Double.parseDouble(train_price));
		this.map.put("飞机", Double.parseDouble(plane_price));
		this.map.put("标准快递运费价格", Double.parseDouble(express_price));
		this.map.put("标准快递的最低运费", Double.parseDouble(min_price));

		this.priceVO.setMap(this.map);
		// 存入新数据
		this.controller.makePrice(this.priceVO);
		// this.map.
		JOptionPane.showMessageDialog(this, "修改成功！");

	}

	/**
	 * 清空操作
	 */
	private void clear() {
		this.car_jtf.setText("");
		this.train_jtf.setText("");
		this.plane_jtf.setText("");
		this.express_jtf.setText("");
		this.min_jtf.setText("");
		this.car_jtf.requestFocus();
	}

	/**
	 * 距离确认操作
	 * 
	 * @throws RemoteException
	 */
	private void confirmForDistance() throws RemoteException {
		int rowNum = this.disTable.getSelectedRow();
		if (rowNum == -1) {
			JOptionPane.showMessageDialog(this, "请选择一行！");
			return;
		}
		String city1 = (String) this.disTable.getValueAt(rowNum, 0);
		String city2 = (String) this.disTable.getValueAt(rowNum, 1);
		String oldDis = (String) this.disTable.getValueAt(rowNum, 2);
		String distance = this.disntance_jtf.getText();
		if (!this.isLegalDistance(distance)) {
			JOptionPane.showMessageDialog(this, "输入非法！");
			return;
		}
		if (this.simplifyString(oldDis).equals(this.simplifyString(distance))) {
			JOptionPane.showMessageDialog(this, "请做相应修改！");
			return;
		}
		DistanceVO temp = new DistanceVO(city1, city2, Double.parseDouble(distance));
		this.controller.makeDistance(temp);
		// 刷新
		this.update();
		this.disntance_jtf.setVisible(false);
		JOptionPane.showMessageDialog(this, "修改成功");
	}

	/**
	 * 刷新距离面板
	 * 
	 * @throws RemoteException
	 */
	private void update() throws RemoteException {
		this.disModel = new DistanceTable(this.controller);
		this.disTable.setModel(this.disModel);
		this.decorateTable();
		this.count_lab.setText("当前记录为 " + this.disTable.getRowCount());
	}

	public void mouseClicked(MouseEvent e) {
		try {
			// 价格制定确认
			if (e.getSource() == this.confirm_lab) {
				this.confirmForPrice();
			}
			// 把价格清空
			else if (e.getSource() == this.clear_lab) {
				this.clear();
			}
			// 刷新距离面板
			else if (e.getSource() == this.update_lab) {
				this.update();
				GlobalHintInserter.insertGlobalHint("更新成功！");
				//JOptionPane.showMessageDialog(this, "更新成功！");
			}
			// 距离设置完成后确定
			else if (e.getSource() == this.ok_lab) {
				this.confirmForDistance();

			} else if (e.getSource() == this.disTable) {
				this.disntance_jtf.setVisible(true);
				int rowNum = this.disTable.getSelectedRow();
				String distance = (String) this.disTable.getValueAt(rowNum, 2);
				this.disntance_jtf.setText(distance);

			}
		} catch (RemoteException e1) {
			this.disconnectInformer.findDisconnect();
		}
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

}
