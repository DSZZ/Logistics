package edu.nju.logistics.ui.keeper;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import edu.nju.logistics.ui.util.TimeUtil;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.StorehouseRecordVO;
import edu.nju.logistics.vo.UserVO;

/**
 * 库存查看界面（出入库记录）
 * @author HanzZou
 *
 */
public class PanelSearch extends JPanel implements MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	private static final int SHOWPANEL_WIDTH = 980;
	
	private static final int SHOWPANEL_HEIGHT = 560;
	
	private Cursor cursor = null;
	
	private JComboBox<String> startYear, startMonth, startDay, startHour, startMinute, endYear,endMonth, endDay, endHour, endMinute = null;
	
	private JLabel startTime, endTime, importAmountSum, importMoneySum, exportAmountSum, exportMoneySum, semicolon1, semicolon2 = null;
	
	private JButton confirm_button = null;
	
	private UserVO user = null;
	
	private StorehouseManagementBLService controller = null;
	
	private DisconnectInformer disconnectInformer = null;
	
	private JTable table = null;
	
	private Table model = null;
	
	private JScrollPane jsp = null;

	public PanelSearch(UserVO user, DisconnectInformer d) throws RemoteException {
		this.user = user;
		this.controller = new StorehouseManagementController();
		this.setSize(SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		this.setLayout(null);
		this.setOpaque(false);
		this.cursor = new Cursor(Cursor.HAND_CURSOR);
		this.initComponent();
		this.disconnectInformer = d;
	}
	
	private void initComponent() {
		String nowTime = TimeUtil.getCurrentTime();
		String[] dateAndTime = nowTime.split(" ");
		cursor = new Cursor(Cursor.HAND_CURSOR);
		//时间年份下拉选择框
		int nowYear = Integer.valueOf(dateAndTime[0].split("/")[0]);
		String[] years = new String[nowYear-2011];
		for(int i = 2012; i <= nowYear; i++) {
			years[i-2012] = String.valueOf(i);
		}

		startYear = new JComboBox<String>(years);
		endYear = new JComboBox<String>(years);
		startYear.setBounds(680, 60, 90, 20);
		endYear.setBounds(680, 180, 90, 20);
		startYear.setSelectedItem(nowYear+"");
		endYear.setSelectedItem(nowYear+"");
		startYear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				startDay.setSelectedIndex(0);
				String year = (String) startYear.getSelectedItem();
				String month = (String) startMonth.getSelectedItem();
				@SuppressWarnings("unchecked")
				ComboBoxModel<String> model = new BoxModel(year, month);
				startDay.setModel(model);
				startDay.setSelectedIndex(0);
			}
			
		});
		startYear.setVisible(true);
		endYear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				endDay.setSelectedIndex(0);
				String year = (String) endYear.getSelectedItem();
				String month = (String) endMonth.getSelectedItem();
				@SuppressWarnings("unchecked")
				ComboBoxModel<String> model = new BoxModel(year, month);
				endDay.setModel(model);
				endDay.setSelectedIndex(0);
			}
			
		});

		//时间月份下拉选择框
		String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		startMonth = new JComboBox<String>(months);
		endMonth = new JComboBox<String>(months);
		startMonth.setBounds(780, 60, 70, 20);
		endMonth.setBounds(780, 180, 70, 20);
		//时间日期下拉选择框
		String[] days = new String[31];
		for(int i = 1; i <= 31; i++) {
			if(i < 10)
				days[i-1] = "0" + String.valueOf(i);
			else
				days[i-1] = String.valueOf(i);
		}
		startDay = new JComboBox<String>(days);
		endDay = new JComboBox<String>(days);
		startDay.setBounds(860, 60, 70, 20);
		endDay.setBounds(860, 180, 70, 20);
		startDay.setSelectedItem(dateAndTime[0].split("/")[2]);
		endDay.setSelectedItem(dateAndTime[0].split("/")[2]);
		startMonth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String year = (String) startYear.getSelectedItem();
				String month = (String) startMonth.getSelectedItem();
				@SuppressWarnings("unchecked")
				ComboBoxModel<String> model = new BoxModel(year, month);
				startDay.setModel(model);
				startDay.setSelectedIndex(0);
			}
			
		});
		endMonth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String year = (String) endYear.getSelectedItem();
				String month = (String) endMonth.getSelectedItem();
				@SuppressWarnings("unchecked")
				ComboBoxModel<String> model = new BoxModel(year, month);
				endDay.setModel(model);
				endDay.setSelectedIndex(0);
			}
			
		});
		startMonth.setSelectedItem(dateAndTime[0].split("/")[1]);
		endMonth.setSelectedItem(dateAndTime[0].split("/")[1]);
		//时间小时下拉选择框
		String[] hours = new String[24];
		for(int i = 0; i < 24; i++) {
			if(i < 10)
				hours[i] = "0" + String.valueOf(i);
			else
				hours[i] = String.valueOf(i);
		}
		startHour = new JComboBox<String>(hours);
		endHour = new JComboBox<String>(hours);
		startHour.setBounds(680, 120, 70, 20);
		endHour.setBounds(680, 240, 70, 20);
		endHour.setSelectedIndex(Integer.valueOf(dateAndTime[1].split(":")[0]));
//		startHour.setSelectedIndex(Integer.valueOf(dateAndTime[1].split(":")[0]));
		//时间分钟下拉选择框
		String[] minutes = new String[60];
		for(int i = 0; i < 60; i++) {
			if(i < 10)
				minutes[i] = "0" + String.valueOf(i);
			else
				minutes[i] = String.valueOf(i);
		}
		startMinute = new JComboBox<String>(minutes);
		endMinute = new JComboBox<String>(minutes);
		startMinute.setBounds(760, 120, 70, 20);
		endMinute.setBounds(760, 240, 70, 20);
		endMinute.setSelectedIndex(Integer.valueOf(dateAndTime[1].split(":")[1]));
		semicolon1 = new JLabel(":");
		semicolon1.setLocation(750, 120);
		semicolon1.setSize(10, 20);
		semicolon2 = new JLabel(":");
		semicolon2.setLocation(750, 240);
		semicolon2.setSize(10,20);
		//确认按钮
		confirm_button = new commonButton("查看");
		confirm_button.setLocation(830, 480);
		confirm_button.setSize(100, 40);
		confirm_button.addMouseListener(this);
		confirm_button.setCursor(cursor);
		//开始时间标签
		startTime = new JLabel("开始时间:");
		startTime.setLocation(620, 60);
		startTime.setSize(70, 20);
		//结束时间标签
		endTime = new JLabel("结束时间:");
		endTime.setLocation(620, 180);
		endTime.setSize(70, 20);
		//入库总数量
		importAmountSum = new JLabel("入库总数量：0");
		importAmountSum.setLocation(620, 300);
		importAmountSum.setSize(140, 20);
		//入库总金额
		importMoneySum = new JLabel("入库总金额：0");
		importMoneySum.setLocation(800, 300);
		importMoneySum.setSize(140, 20);
		//出库总数量
		exportAmountSum = new JLabel("出库总数量：0");
		exportAmountSum.setLocation(620, 360);
		exportAmountSum.setSize(140, 20);
		//出库总金额
		exportMoneySum = new JLabel("出库总金额：0");
		exportMoneySum.setLocation(800, 360);
		exportMoneySum.setSize(140, 20);
		//表格
		this.model = new Table();
		this.table = new JTable(this.model);
		this.table.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14));
		this.table.setFont(new Font("楷体", Font.PLAIN, 14));
        this.jsp = new JScrollPane(table);
		this.jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setBounds(20, 20, 580, 520);
		MyTableHandler.initTable(this.table);
		MyTableHandler.initSpane(this.jsp, this.table);
		table.getColumnModel().getColumn(0).setPreferredWidth(160);
        table.getColumnModel().getColumn(1).setPreferredWidth(260);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(90);
        table.getColumnModel().getColumn(4).setPreferredWidth(70);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);
        table.getColumnModel().getColumn(6).setPreferredWidth(70);
        table.getColumnModel().getColumn(7).setPreferredWidth(90);
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.getTableHeader().setResizingAllowed(false);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
		this.add(this.startYear);
		this.add(this.startMonth);
		this.add(this.endYear);
		this.add(this.endMonth);
		this.add(this.startDay);
		this.add(this.endDay);
		this.add(this.startHour);
		this.add(this.endHour);
		this.add(this.startMinute);
		this.add(this.endMinute);
		this.add(this.semicolon1);
		this.add(this.semicolon2);
		this.add(this.confirm_button);
		this.add(this.startTime);
		this.add(this.endTime);
		this.add(this.importAmountSum);
		this.add(this.importMoneySum);
		this.add(this.exportAmountSum);
		this.add(this.exportMoneySum);
		this.add(this.jsp);
		
		this.repaint();
	}
	
	//查询记录按钮点击响应
	public void searchRecord() {
		this.model.reset();
		String startTime = startYear.getSelectedItem() + "/" + startMonth.getSelectedItem() + "/" + startDay.getSelectedItem()
				+ " " + startHour.getSelectedItem() + ":" + startMinute.getSelectedItem() + ":00";
		String endTime = endYear.getSelectedItem() + "/" + endMonth.getSelectedItem() + "/" + endDay.getSelectedItem()
				+ " " + endHour.getSelectedItem() + ":" + endMinute.getSelectedItem() + ":00";
		if(!TimeUtil.isFore(startTime, endTime)) {
			GlobalHintInserter.insertGlobalHint("确保时间顺序正确！");
			return;
		}
		ArrayList<StorehouseRecordVO> list = new ArrayList<StorehouseRecordVO>();
		try {
			list = controller.takeStock(controller.getInstitutionIDByUser(user.getId()), startTime, endTime);
		} catch (RemoteException e) {
			this.disconnectInformer.findDisconnect();
			return;
		}
		int importAmountSum = 0;
		int exportAmountSum = 0;
		int importMoneySum = 0;
		int exportMoneySum = 0;
		if(list.size() == 0) {
			GlobalHintInserter.insertGlobalHint("该时段内无记录！");
		}
		for(StorehouseRecordVO vo: list) {
			Vector<String> temp = new Vector<String>();
			temp.add(vo.getNumber());
			if(vo.getState().equals("import")) {
				importAmountSum++;
				importMoneySum += vo.getMoney();
			} else {
				exportAmountSum++;
				exportMoneySum += vo.getMoney();
			}
			temp.add(vo.getTime());
			temp.add(vo.getState().equals("import")?"入库":"出库");
			temp.add(vo.getArea());
			temp.add(String.valueOf(vo.getLine()));
			temp.add(String.valueOf(vo.getShelf()));
			temp.add(String.valueOf(vo.getPosition()));
			temp.add(String.valueOf(vo.getMoney()));
			model.rows.add(temp);
		}
		table.updateUI();
		this.importAmountSum.setText("入库总数量：" + importAmountSum);
		this.importMoneySum.setText("入库总金额：" + importMoneySum);
		this.exportAmountSum.setText("出库总数量：" + exportAmountSum);
		this.exportMoneySum.setText("出库总金额：" + exportMoneySum);
		this.repaint();
	}
	
	@SuppressWarnings("rawtypes")
	class BoxModel extends AbstractListModel implements ComboBoxModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

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
				if(i < 10)
					this.days[i-1] = "0" + String.valueOf(i);
				else
					this.days[i-1] = String.valueOf(i);
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
			this.colums.add("出入库");
			this.colums.add("区域");
			this.colums.add("排号");
			this.colums.add("架号");
			this.colums.add("位号");
			this.colums.add("金额");
			
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

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == this.confirm_button) {
			this.searchRecord();
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
