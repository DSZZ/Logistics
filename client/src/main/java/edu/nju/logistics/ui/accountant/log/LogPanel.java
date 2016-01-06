package edu.nju.logistics.ui.accountant.log;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.nju.logistics.bl.impl.financialmanagement.LogController;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.commonButton.SearchButton;
import edu.nju.logistics.ui.util.TimeUtil;

@SuppressWarnings("serial")
public class LogPanel extends JPanel implements MouseListener {

	//private static final ImageIcon CHECK = new ImageIcon("Image/manager/check.png");
	/**
	 * 显示区宽度
	 */
	public static final int SHOWPANEL_WIDTH = 980;
	/**
	 * 显示区高度
	 */
	public static final int SHOWPANEL_HEIGHT = 560;
	/**
	 * 基准年份
	 */
	private static final int YEAR_BASE = 2014;

	private JPanel northPanel = null, centerPanel = null, southPanel = null;

	private JComboBox<String> year_box = null, month_box = null, day_box = null;

	private JLabel year_lab = null, month_lab = null, day_lab = null;
	SearchButton check_lab = null;

	private Cursor cursor = null;

	private JScrollPane jsp = null;

	private JTable table = null;

	private LogTable model = null;

	private LogController  controller = null;
	
	//private DisconnectInformer disconnectInformer=null;

	public LogPanel(LogController controller) {
		//this.setSize(SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		this.setLayout(null);
		this.setOpaque(false);
        ///this.disconnectInformer=disconnectInformer;
		this.controller = controller;
		// 初始化北部面板
		this.initNorthPanel();
		// 初始化中心面板
		this.initCenterPanel();
		// 初始化南部面板
		this.initSouthPanel();

	}

	/**
	 * 初始化北部面板
	 */
	private void initNorthPanel() {
		this.cursor = new Cursor(Cursor.HAND_CURSOR);

		this.northPanel = new JPanel();
		this.northPanel.setLayout(null);
		this.northPanel.setOpaque(false);
		this.northPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.northPanel.setBounds(5, 5, 970, 40);
		// 年份下拉框
		String years[] = this.getYearBox();
		this.year_box = new JComboBox<String>(years);
		this.year_box.setBounds(20, 10, 70, 20);
		this.northPanel.add(this.year_box);
		// 日期下拉框
		String days[] = new String[31];
		for (int i = 1; i <= 31; i++) {
			days[i - 1] = i + "";
		}

		this.day_box = new JComboBox<String>(days);
		this.day_box.setBounds(240, 10, 70, 20);
		this.northPanel.add(this.day_box);
		// 月份下拉框
		String months[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
		this.month_box = new JComboBox<String>(months);
		this.month_box.setBounds(130, 10, 70, 20);
		this.month_box.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				String year = (String) year_box.getSelectedItem();
				String month = (String) month_box.getSelectedItem();
				ComboBoxModel<String> model = new BoxModel(year, month);
				day_box.setModel(model);
				day_box.setSelectedItem("1");

			}
		});
		this.northPanel.add(this.month_box);

		this.year_lab = new JLabel("年");
		this.year_lab.setBounds(100, 10, 20, 20);
		this.northPanel.add(this.year_lab);

		this.month_lab = new JLabel("月");
		this.month_lab.setBounds(210, 10, 20, 20);
		this.northPanel.add(this.month_lab);

		this.day_lab = new JLabel("日");
		this.day_lab.setBounds(320, 10, 20, 20);
		this.northPanel.add(this.day_lab);

		this.check_lab = new SearchButton();
		this.check_lab.addMouseListener(this);
		this.check_lab.setBounds(400, 5, 65, 30);
		this.check_lab.setCursor(this.cursor);
		this.northPanel.add(this.check_lab);

		this.add(this.northPanel);

		// 将时间设置为当前时间
		this.setNowTime();

		this.repaint();
	}
	/**
	 * 根据实际创建年份下拉框
	 * @return
	 */
	private String[] getYearBox() {
		String temp[]=TimeUtil.getCurrentDate().split("/");
		int len=Integer.parseInt(temp[0])-YEAR_BASE+1;
		String time[]=new String[len]; 
		for (int i = 0; i < time.length; i++) {
			time[i]=(YEAR_BASE+i)+"";
		}
		return time;
	}

	/**
	 * 将时间设置为当前时间
	 */
	private void setNowTime() {
		String single[] = TimeUtil.getCurrentDate().split("/");
		this.year_box.setSelectedItem(single[0]);
		this.month_box.setSelectedItem(Integer.parseInt(single[1]) + "");
		this.day_box.setSelectedItem(Integer.parseInt(single[2]) + "");
	}

	/**
	 * 初始化中心面板
	 */
	private void initCenterPanel() {
		this.centerPanel = new JPanel(new BorderLayout());
		this.centerPanel.setBounds(5, 50, 970, 470);
		this.centerPanel.setOpaque(false);
		this.model = new LogTable();
		this.model.init();
		this.table = new JTable(this.model);
		this.table.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14));
		this.table.setFont(new Font("楷体", Font.PLAIN, 14));
		// 设置列宽
		this.table.getColumnModel().getColumn(0).setPreferredWidth(80);
		this.table.getColumnModel().getColumn(1).setPreferredWidth(80);
		this.table.getColumnModel().getColumn(2).setPreferredWidth(610);
		// 不可整列移动
		this.table.getTableHeader().setReorderingAllowed(false);
		// 不可拉动表格
		this.table.getTableHeader().setResizingAllowed(false);
		this.jsp = new JScrollPane(this.table);
		/**
		 * 好像与原先的一些设置重复或者冲突了。
		 */
		MyTableHandler.decorateTableAndSpane(table, jsp);
		
		
		this.centerPanel.add(this.jsp, "Center");

		this.add(this.centerPanel);

	}

	/**
	 * 初始化南部面板
	 */
	private void initSouthPanel() {
		this.southPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.setFont(new Font("宋体", Font.BOLD, 14));
				g.drawString("记录条数为 " + model.getRowCount(), 5, 20);
			}
		};
		this.southPanel.setOpaque(false);
		this.southPanel.setBounds(5, 525, 970, 30);
		this.add(this.southPanel);
		this.repaint();
	}

	/**
	 * 点击查询的操作
	 * 
	 * @throws RemoteException
	 * @throws HeadlessException
	 */
	private void checkLog() throws RemoteException {
		String year = (String) this.year_box.getSelectedItem();
		String month = (String) this.month_box.getSelectedItem();
		if (month.length() == 1) {
			month = "0" + month;
		}
		String day = (String) this.day_box.getSelectedItem();
		if (day.length() == 1) {
			day = "0" + day;
		}
		String date = year + "/" + month + "/" + day;
		this.model = new LogTable();
		if (!this.model.showByDate(date, this.controller)) {
			JOptionPane.showMessageDialog(this, "该日期无日志查询！");
		}
		this.table.setModel(this.model);
		// 设置列宽
		this.table.getColumnModel().getColumn(0).setPreferredWidth(80);
		this.table.getColumnModel().getColumn(1).setPreferredWidth(80);
		this.table.getColumnModel().getColumn(2).setPreferredWidth(610);
		// 刷新面板
		this.southPanel.repaint();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			if (e.getSource() == this.check_lab) {
				this.checkLog();
			}
		} catch (RemoteException e1) {
			//this.disconnectInformer.findDisconnect();
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

	@SuppressWarnings({ "rawtypes" })
	class BoxModel extends AbstractListModel implements ComboBoxModel {

		private String days[] = null;

		private String item = null;

		private int monthToDay[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		public BoxModel(String year, String month) {
			this.init(year, month);
		}

		// 根据年份和月份初始化当月天数
		private void init(String year, String month) {
			if (isLeapYear(year)) {
				this.monthToDay[1] = 29;
			}
			int month_num = Integer.parseInt(month);
			this.days = new String[this.monthToDay[month_num - 1]];
			for (int i = 1; i <= this.monthToDay[month_num - 1]; i++) {
				this.days[i - 1] = i + "";
			}
		}

		// 判断是否是闰年
		private boolean isLeapYear(String year) {
			int y = Integer.parseInt(year);
			if ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) {
				return true;
			}
			return false;
		}

		@Override
		public Object getElementAt(int index) {
			return this.days[index];
		}

		@Override
		public int getSize() {

			return this.days.length;
		}

		@Override
		public Object getSelectedItem() {
			return item;
		}

		@Override
		public void setSelectedItem(Object anItem) {
			item = (String) anItem;

		}

	}
}
