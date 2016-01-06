package edu.nju.logistics.ui.centerstaff;

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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import edu.nju.logistics.bl.impl.transfersheetmanagement.TransferSheetManagementController;
import edu.nju.logistics.bl.service.transfersheetmanagement.TransferSheetManagementBLService;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.commonButton.commonButton;
import edu.nju.logistics.ui.util.TimeUtil;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.BufferVO;
import edu.nju.logistics.vo.TransferSheetVO;
import edu.nju.logistics.vo.Transportation;
import edu.nju.logistics.vo.UserVO;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

/**
 * 发货登记界面
 * 
 * @author HanzZou
 *
 */
public class PanelSend extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserVO user = null;

	private TransferSheetManagementBLService controller = null;
	
	private DisconnectInformer disconnectInformer = null;

	private Cursor cursor = null;

	private JButton confirm_button, cancel_button, update_button = null;

	private JTable sendTable = null;

	private SendTable model = null;

	private JScrollPane jsp = null;

	private JLabel observerLabel, destinationLabel, transportationLabel, transportationIDLabel, supercargoLabel, tip = null;

	private JTextField observerText, transportationID, supercargo = null;

	private JComboBox<String> destination, transportation = null;
	
	private JFrame frame = null;

	public PanelSend(JFrame frame, UserVO user, DisconnectInformer d) throws RemoteException {
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
		// 取消按钮
		this.cancel_button = new commonButton("清空");
		this.cancel_button.setLocation(680, 480);
		this.cancel_button.setSize(100, 40);
		this.cancel_button.setCursor(cursor);
		this.cancel_button.addMouseListener(this);
		this.add(cancel_button);
		// 确认按钮
		this.confirm_button = new commonButton("发货");
		this.confirm_button.setLocation(830, 480);
		this.confirm_button.setSize(100, 40);
		this.confirm_button.setCursor(cursor);
		this.confirm_button.addMouseListener(this);
		this.add(confirm_button);
		//刷新按钮
		this.update_button = new commonButton("刷新");
		this.update_button.setBounds(830, 400, 100, 40);
		this.update_button.setCursor(cursor);
		this.update_button.addMouseListener(this);
		this.add(this.update_button);
		//tip
        tip = new JLabel("提示:按住ctrl或command可多选。");
        tip.setForeground(Color.red);
        tip.setBounds(660, 20, 300, 30);
        this.add(this.tip);
		// 表格
		this.model = new SendTable();
		this.sendTable = new JTable(this.model);
		this.sendTable.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14));
		this.sendTable.setFont(new Font("楷体", Font.PLAIN, 14));
		this.jsp = new JScrollPane(sendTable);
		jsp.setBounds(20, 20, 620, 520);
		MyTableHandler.initTable(this.sendTable);
		MyTableHandler.initSpane(this.jsp, this.sendTable);
		sendTable.getColumnModel().getColumn(0).setPreferredWidth(190);
		sendTable.getColumnModel().getColumn(1).setPreferredWidth(390);
		this.sendTable.getTableHeader().setReorderingAllowed(false);
		this.sendTable.getTableHeader().setResizingAllowed(false);
		RowSorter<SendTable> sorter = new TableRowSorter<SendTable>(model);  //设置点击表头自动排序的功能
        sendTable.setRowSorter(sorter);  //设置点击表头自动排序的功能
		this.add(this.jsp);
		this.initTable();
		// 交通方式
		this.transportationLabel = new JLabel("交通方式:");
		this.transportationLabel.setBounds(670, 130, 60, 30);
		this.add(this.transportationLabel);
		String[] temp = { "航空", "铁路", "汽运" };
		this.transportation = new JComboBox<String>(temp);
		this.transportation.setBounds(740, 130, 100, 30);
		this.add(this.transportation);
		// 交通编号
		this.transportationIDLabel = new JLabel("交通编号");
		this.transportationIDLabel.setBounds(670, 190, 60, 30);
		this.add(this.transportationIDLabel);
		this.transportationID = new JTextField();
		this.transportationID.setBounds(740, 190, 120, 30);
		this.add(this.transportationID);
		// 监装员
		this.observerLabel = new JLabel("监装员:");
		this.observerLabel.setBounds(670, 250, 60, 30);
		this.add(this.observerLabel);
		this.observerText = new JTextField();
		this.observerText.setBounds(740, 250, 100, 30);
		this.add(this.observerText);
		// 押运员
		this.supercargoLabel = new JLabel("押运员:");
		this.supercargoLabel.setBounds(670, 310, 60, 30);
		this.add(this.supercargoLabel);
		this.supercargo = new JTextField();
		this.supercargo.setBounds(740, 310, 100, 30);
		this.add(this.supercargo);
		// 目标机构选择
		this.destinationLabel = new JLabel("目的机构:");
		this.destinationLabel.setBounds(670, 70, 60, 30);
		this.add(this.destinationLabel);
		String[] destinations = null;
		try {
			destinations = controller.getDestinations(controller.getInstitutionIDByUser(this.user.getId()));
			for(int i = 0; i < destinations.length; i++) {
				if(destinations[i].split(" ").length == 3) {
					destinations[i] = "下属" + " " +destinations[i].split(" ")[2];
				}
			}
		} catch (RemoteException e) {
			this.disconnectInformer.findDisconnect();
		}
		this.destination = new JComboBox<String>(destinations);
		this.destination.setBounds(740, 70, 220, 30);
		this.destination.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(((String)destination.getSelectedItem()).split(" ")[0].equals("下属")) {
					transportation.setSelectedItem("汽运");
				} else {
					transportation.setSelectedIndex(0);
				}
				
			}
			
		});
		this.add(this.destination);
	}
	
	private boolean initTable() {
		model.reset();
		ArrayList<BufferVO> list = new ArrayList<BufferVO>();
		try {
			list = controller.getToSend(controller.getInstitutionIDByUser(user.getId()));
			for (BufferVO vo : list) {
				Vector<String> temp = new Vector<String>();
				temp.add(vo.getId());
				temp.add(vo.getDestination());
				model.rows.add(temp);
			}
			sendTable.repaint();
		} catch (RemoteException e1) {
			disconnectInformer.findDisconnect();
		}
		if(list.size() == 0) {
			return false;
		}
		return true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == this.cancel_button) {
			this.observerText.setText("");
			this.transportation.setSelectedIndex(0);
			this.destination.setSelectedIndex(0);
			this.supercargo.setText("");
			this.transportationID.setText("");
		}
		if (e.getSource() == this.confirm_button) {
			if (this.transportationID.getText().trim().equals("") || supercargo.getText().trim().equals("") || this.observerText.getText().trim().equals("")) {
				GlobalHintInserter.insertGlobalHint("请输入完整信息！");
				return;
			}
			Transportation tran = null;
			switch (this.transportation.getSelectedIndex()) {
			case 0:
				tran = Transportation.AIR;
				break;
			case 1:
				tran = Transportation.TRAIN;
				break;
			case 2:
				tran = Transportation.CAR;
				break;
			}
			if (sendTable.getSelectedRowCount() == 0) {
				GlobalHintInserter.insertGlobalHint("请选择货物！");
				return;
			}
			int[] x = sendTable.getSelectedRows();
			ArrayList<OrderVO> voList = new ArrayList<OrderVO>();
			try {
				for (int i = 0; i < x.length; i++) {
					voList.add(controller.getOrderVO((String) sendTable.getValueAt(x[i], 0)));
				}
				String destinationID = controller.getInstitutionIDByName((String)this.destination.getSelectedItem());
				controller
						.createTransferSheet(
								new TransferSheetVO(controller.getInstitutionIDByUser(user.getId()), destinationID,
										TimeUtil.getCurrentTime(), voList, controller.getTransferSheetID(), tran,
										this.transportationID.getText(), this.observerText.getText(),
										this.supercargo.getText(), controller.getFee(voList, tran,
												controller.getInstitutionIDByUser(user.getId()), destinationID)),
										user.getId());
			} catch (RemoteException re) {
				disconnectInformer.findDisconnect();
			}
			this.observerText.setText("");
			this.transportation.setSelectedIndex(0);
			this.supercargo.setText("");
			this.transportationID.setText("");
			ShowResultDialog window = new ShowResultDialog(frame, "货物列表", true, voList, ResultType.SEND);
			window.setVisible(true);
		}
		if(e.getSource() == this.update_button) {
			if(!this.initTable())
				GlobalHintInserter.insertGlobalHint("暂无可发货物!");
			GlobalHintInserter.insertGlobalHint("刷新成功！");
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

class SendTable extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Vector<String> colums = null;

	public Vector<Vector<String>> rows = null;

	public SendTable() {
		this.colums = new Vector<String>();
		this.colums.add("快递编号");
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