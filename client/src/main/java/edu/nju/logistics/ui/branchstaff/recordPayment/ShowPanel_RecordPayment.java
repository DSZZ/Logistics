package edu.nju.logistics.ui.branchstaff.recordPayment;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import edu.nju.logistics.bl.service.ordermanagement.OrderManagementService;
import edu.nju.logistics.ui.branchstaff.BranchFrame;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.tableBuilder.NilHintPanel;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;
import edu.nju.logistics.ui.util.tableBuilder.TableHolder;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class ShowPanel_RecordPayment extends JPanel implements Refresher{


	/**
	 * 
	 */
	private static final long serialVersionUID = -2996800756803610502L;
	
	private OrderManagementService orderManagement;
	
	private NilHintPanel nilHintPanel;
	private TableHolder tabelPanel;
	private DisconnectInformer disconnectInformer;
	
	public ShowPanel_RecordPayment(OrderManagementService orderManagement
			,DisconnectInformer disconnectInformer) {
		this.disconnectInformer = disconnectInformer;
		this.orderManagement = orderManagement;
		initShowPanel();
	}
	
	protected void initShowPanel() {
		setBorder(BorderFactory.createEtchedBorder());
		setOpaque(false);
		setLayout(null);
		
		ArrayList<OrderVO> unpayedOrderList = null;
		try {
			unpayedOrderList = orderManagement.getUnpayedOrderList();
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
			return;
		}
		if(unpayedOrderList == null || unpayedOrderList.size() == 0){
			nilHintPanel = new NilHintPanel(this,"当前无需要收款的订单");
			nilHintPanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
			this.add(nilHintPanel);
		}else{
			OKCaller_RecordPayment payOrderCaller = new OKCaller_RecordPayment(orderManagement, unpayedOrderList);
			tabelPanel = new TableHolder(new VectorBuilder_RecordPayment(unpayedOrderList), payOrderCaller,this,"请选择要收款的订单");
			tabelPanel.setPreferredWidth(0, 100);
			tabelPanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
			this.add(tabelPanel);
		}
	}

	@Override
	public void refresh() {
		JPanel beforePanel = null;
		if(nilHintPanel != null){
			beforePanel = nilHintPanel;
		}else if(tabelPanel != null){
			beforePanel = tabelPanel;
		}
		remove(beforePanel);
		nilHintPanel = null;
		tabelPanel = null;
		repaint();
		initShowPanel();
		
	}

	@Override
	public String getButtonName() {
		return "刷新";
	}

}
