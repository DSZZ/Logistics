package edu.nju.logistics.ui.branchstaff.load.chooseOrder;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import edu.nju.logistics.bl.service.ordermanagement.OrderManagementService;
import edu.nju.logistics.ui.branchstaff.BranchFrame;
import edu.nju.logistics.ui.branchstaff.load.ShowPanel_Load;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.tableBuilder.NilHintPanel;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;
import edu.nju.logistics.ui.util.tableBuilder.TableHolder;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class ChooseOrderPanel extends JPanel implements Refresher{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4048636366272152104L;
	
	private OrderManagementService orderManagement;
	private DisconnectInformer disconnectInformer;
	private ShowPanel_Load owner;
	
	private NilHintPanel nilHintPanel;
	private TableHolder tabelPanel;

	public ChooseOrderPanel(OrderManagementService orderManagement
			, ShowPanel_Load showPanel_Load, DisconnectInformer disconnectInformer) {
		this.orderManagement = orderManagement;
		owner = showPanel_Load;
		initShowPanel();
	}

	private void initShowPanel() {
		setBorder(BorderFactory.createEtchedBorder());
		setOpaque(false);
		setLayout(null);
		
		ArrayList<OrderVO> stayOrderList;
		try {
			stayOrderList = orderManagement.getStayOrderList();
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
			return;
		}
		if(stayOrderList == null || stayOrderList.size() == 0){
			nilHintPanel = new NilHintPanel(this,"当前无需要装车的订单");
			nilHintPanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
			this.add(nilHintPanel);
		}else{
			OKCaller_ChooseOrder payOrderCaller = new OKCaller_ChooseOrder(owner, stayOrderList);
			tabelPanel = new TableHolder(new VectorBuilder_ChooseOrder(stayOrderList), payOrderCaller,this,"请选择要装车的订单");
			tabelPanel.setPreferredWidth(0, 80);
			tabelPanel.setPreferredWidth(1, 40);
			tabelPanel.setPreferredWidth(2, 30);
			tabelPanel.setPreferredWidth(3, 40);
			tabelPanel.setPreferredWidth(4, 40);
			tabelPanel.setPreferredWidth(5, 40);
			tabelPanel.setPreferredWidth(6, 200);
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
