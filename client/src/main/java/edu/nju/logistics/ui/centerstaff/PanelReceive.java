package edu.nju.logistics.ui.centerstaff;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import edu.nju.logistics.bl.impl.transfersheetmanagement.TransferSheetManagementController;
import edu.nju.logistics.bl.service.transfersheetmanagement.TransferSheetManagementBLService;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.commonButton.commonButton;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.ReceiptState;
import edu.nju.logistics.vo.TransferSheetVO;
import edu.nju.logistics.vo.UserVO;

/**
 * 接收货物登记界面
 * @author HanzZou
 *
 */
public class PanelReceive extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserVO user = null;
	
	private TransferSheetManagementBLService controller = null;
	
	private DisconnectInformer disconnectInformer = null;
	
	private JTable table = null;
	
	private JScrollPane jsp = null;
	
	private ReceiveTable model = null;
	
	private JButton confirm_button, update_button = null;
	
	private JComboBox<String> state = null;
	
	private Cursor cursor = null;
	
	public JLabel stateLabel = null;
	
	public JFrame frame = null;

	public PanelReceive(JFrame frame, UserVO user, DisconnectInformer d) throws RemoteException {
		this.user = user;
		this.frame = frame;
		this.controller = new TransferSheetManagementController();
		this.disconnectInformer = d;
		this.setLayout(null);
		this.setSize(980, 560);
		this.setOpaque(false);
		this.cursor = new Cursor(Cursor.HAND_CURSOR);
		this.initComponent();
	}
	
	private void initComponent() {
		//确认按钮
		this.confirm_button = new commonButton("收货");
		this.confirm_button.setLocation(830, 480);
		this.confirm_button.setSize(100, 40);
		this.confirm_button.setCursor(cursor);
		this.confirm_button.addMouseListener(this);
		this.add(confirm_button);
		//刷新按钮
		this.update_button = new commonButton("刷新");
		this.update_button.setBounds(680, 480, 100, 40);
		this.update_button.setCursor(cursor);
		this.update_button.addMouseListener(this);
		this.add(this.update_button);
		//表格
		this.model = new ReceiveTable();
		this.table = new JTable(this.model);
		this.table.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14));
		this.table.setFont(new Font("楷体", Font.PLAIN, 14));
        this.jsp = new JScrollPane(table);
		jsp.setBounds(20, 20, 620, 520);
		MyTableHandler.initTable(this.table);
		MyTableHandler.initSpane(this.jsp, this.table);
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(170);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(160);
        table.getColumnModel().getColumn(4).setPreferredWidth(180);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);
		RowSorter<ReceiveTable> sorter = new TableRowSorter<ReceiveTable>(model);  //设置点击表头自动排序的功能
        table.setRowSorter(sorter);  //设置点击表头自动排序的功能
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.getTableHeader().setResizingAllowed(false);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.add(this.jsp);
        this.initTable();
        //状态选择
        stateLabel = new JLabel("到达状态:");
        stateLabel.setBounds(680, 120, 80, 30);
        this.add(this.stateLabel);
        String[] temp = {"完整", "损坏", "丢失"};
        this.state = new JComboBox<String>(temp);
        this.state.setBounds(800, 120, 80, 30);
        this.add(this.state);

        this.repaint();
	}
	
	private void initTable() {
		model.reset();
		ArrayList<TransferSheetVO> list = new ArrayList<TransferSheetVO>();
		try {
			list = controller.show(controller.getInstitutionIDByUser(user.getId()));
			for(TransferSheetVO vo : list) {
				Vector<String> temp = new Vector<String>();
				temp.add(vo.getID());
				temp.add(controller.getInstituionNameByID(vo.getOriginID()));
				switch(vo.getTransportation()) {
				case AIR : temp.add("航空");
				case TRAIN : temp.add("铁运");
				case CAR : temp.add("汽运");
				}
				temp.add(vo.getTransportationID());
				temp.add(vo.getTime());
				temp.add(vo.getObserver());
				model.rows.add(temp);
			}
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
		}
		this.table.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == this.confirm_button) {
			if(table.getSelectedRowCount() == 0) {
				GlobalHintInserter.insertGlobalHint("请选择接收！");
				return;
			}
			ReceiptState s = null;
			switch(this.state.getSelectedIndex()) {
			case 0: s = ReceiptState.COMPLETE; break;
			case 1: s = ReceiptState.BROKEN; break;
			case 2: s = ReceiptState.LOST; break;
			}
			try {
				controller.receiveItems((String)table.getValueAt(table.getSelectedRow(), 0), user.getId(), s);
			} catch (RemoteException e1) {
				disconnectInformer.findDisconnect();
				return;
			}
			GlobalHintInserter.insertGlobalHint("接收成功！");
			try {
				JDialog window = new ShowResultDialog(frame, "货物列表", true, controller.getOrderListInTransferSheet
						((String)table.getValueAt(table.getSelectedRow(), 0)), ResultType.RECEIVE);
				window.setVisible(true);
			} catch (RemoteException e1) {
				disconnectInformer.findDisconnect();
				return;
			}
		}
		if(e.getSource() == this.update_button) {
			this.initTable();
			GlobalHintInserter.insertGlobalHint("刷新成功");
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

class ReceiveTable extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Vector<String> colums = null;
	
	public Vector<Vector<String>> rows = null;
	
	public ReceiveTable() {
		this.colums = new Vector<String>();
		this.colums.add("中转单编号");
		this.colums.add("始发地");
		this.colums.add("交通方式");
		this.colums.add("运输编号");
		this.colums.add("发车时间");
		this.colums.add("监装员");
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