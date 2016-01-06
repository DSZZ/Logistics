package edu.nju.logistics.ui.courier;

import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.JPanel;

import edu.nju.logistics.bl.impl.ordermanagement.OrderManagementImpl;
import edu.nju.logistics.bl.impl.ordermanagement.serviceProvider.DistanceAndPriceProvider;
import edu.nju.logistics.bl.service.ordermanagement.DistanceAndPriceGetter;
import edu.nju.logistics.bl.service.ordermanagement.OrderManagementService;
import edu.nju.logistics.ui.courier.dispatch.ShowPanel_Dispatch;
import edu.nju.logistics.ui.courier.inputOrder.ShowPanel_InputOrder;
import edu.nju.logistics.ui.main.MainFrame;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.reconnection.ReconnectPanel;
import edu.nju.logistics.ui.util.reconnection.ReconnectSuccessObserver;
import edu.nju.logistics.vo.UserVO;

public class CourierFrame extends MainFrame implements ReconnectSuccessObserver,DisconnectInformer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2850588212321712292L;

	private OrderManagementService orderManagement;
	
	JPanel SHOWPANEL_DISPATCH;
	JPanel SHOWPANEL_INPUT;
	ReconnectPanel SHOWPANEL_RECONNECT;
	
	JPanel leftPanel;
	
	private void initLeftPanel() {
		leftPanel = new CourierLeftPanel(this);
		leftPanel.setBounds(10, SHOWPANEL_INIT_Y, SHOWPANEL_INIT_X, SHOWPANEL_HEIGHT);
		this.add(leftPanel);
	}

	protected JPanel initShowPanel() {
		initLeftPanel();
		
		SHOWPANEL_RECONNECT = new ReconnectPanel(this);
		SHOWPANEL_RECONNECT.setBounds(SHOWPANEL_INIT_X, SHOWPANEL_INIT_Y,
				SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		
		DistanceAndPriceGetter environmentGetter = null;
		try {
			orderManagement = new OrderManagementImpl(userVO);
			environmentGetter = new DistanceAndPriceProvider();
		} catch (RemoteException e) {
			System.out.println("catch construction remoteException!!!!!!");
			findDisconnect();
			return null;
		}
		
		SHOWPANEL_INPUT = new ShowPanel_InputOrder(orderManagement,userVO,environmentGetter,this);
		SHOWPANEL_INPUT.setBounds(SHOWPANEL_INIT_X, SHOWPANEL_INIT_Y,
				SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		
		SHOWPANEL_DISPATCH = new ShowPanel_Dispatch(orderManagement,userVO,this);
		SHOWPANEL_DISPATCH.setBounds(SHOWPANEL_INIT_X, SHOWPANEL_INIT_Y,
				SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		
		this.showPanel = SHOWPANEL_INPUT;
		return showPanel;
	}

	public CourierFrame(UserVO vo) {
		super(vo);
		
	}
	
	public void changeShowPanelTo(JPanel showPanel){
		if(showPanel == null) return;
		
		if(this.showPanel != null)
			mainPanel.remove(this.showPanel);
		this.showPanel = showPanel;
		mainPanel.add(showPanel);
		showPanel.repaint();
	}

	@Override
	public void findDisconnect() {
		SHOWPANEL_INPUT = null;
		SHOWPANEL_DISPATCH = null;
		changeShowPanelTo(SHOWPANEL_RECONNECT);
		SHOWPANEL_RECONNECT.startReconnection();
	}

	@Override
	public void informReconnectSuccess() {
		mainPanel.remove(SHOWPANEL_RECONNECT);
		initShowPanel();
		changeShowPanelTo(SHOWPANEL_INPUT);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
	}
}
