package edu.nju.logistics.ui.courier.dispatch;

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
import edu.nju.logistics.vo.UserVO;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class ShowPanel_Dispatch extends JPanel implements Refresher{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3106298351817743972L;
	
	private OrderManagementService orderManagement;
	private UserVO userVO;
	
	private NilHintPanel nilHintPanel;
	private TableHolder tablePanel;
	private DisconnectInformer disconnectInformer;
	
	public ShowPanel_Dispatch(OrderManagementService orderManagement
			, UserVO userVO,DisconnectInformer disconnectInformer) {
		this.orderManagement = orderManagement;
		this.disconnectInformer = disconnectInformer;
		this.userVO = userVO;
		initShowPanel();
	}
	
	protected void initShowPanel() {
		setBorder(BorderFactory.createEtchedBorder());
		setOpaque(false);
		setLayout(null);
		
		ArrayList<OrderVO> undispatchedOrderList = null;
		try {
			undispatchedOrderList = orderManagement.getCourierUndispatchedList(userVO.getId());
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
		}
		if(undispatchedOrderList == null || undispatchedOrderList.size() == 0){
			nilHintPanel = new NilHintPanel(this,"当前无需要派送的订单");
			nilHintPanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
			this.add(nilHintPanel);
		}else{
			OKCaller_Dispatch dispatchOrderCaller = new OKCaller_Dispatch(orderManagement, undispatchedOrderList,userVO.getId(),disconnectInformer);
			tablePanel = new TableHolder(new VectorBuilder_Dispatch(undispatchedOrderList), dispatchOrderCaller,this,"请选择要派件的订单");
			setTablePanelWidth(tablePanel);
			tablePanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
			this.add(tablePanel);
		}
	}
	
	protected void setTablePanelWidth(TableHolder tablePanel){
		tablePanel.setPreferredWidth(1, 50);
		tablePanel.setPreferredWidth(1, 250);
		tablePanel.setPreferredWidth(4, 5);
	}

	@Override
	public void refresh() {
		JPanel beforePanel = null;
		if(nilHintPanel != null){
			beforePanel = nilHintPanel;
		}else if(tablePanel != null){
			beforePanel = tablePanel;
		}
		remove(beforePanel);
		nilHintPanel = null;
		tablePanel = null;
		repaint();
		initShowPanel();
	}

	@Override
	public String getButtonName() {
		return "刷新";
	}

}
