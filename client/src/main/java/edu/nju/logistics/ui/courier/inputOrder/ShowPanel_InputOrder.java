package edu.nju.logistics.ui.courier.inputOrder;

import java.awt.AWTEvent;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.nju.logistics.bl.service.ordermanagement.DistanceAndPriceGetter;
import edu.nju.logistics.bl.service.ordermanagement.OrderManagementService;
import edu.nju.logistics.ui.commonButton.ZYXButton;
import edu.nju.logistics.ui.courier.inputOrder.EmptyException.EmptyException;
import edu.nju.logistics.ui.courier.inputOrder.subPanel.ErrorPanel;
import edu.nju.logistics.ui.courier.inputOrder.subPanel.FeeInputPanel;
import edu.nju.logistics.ui.courier.inputOrder.subPanel.GoodsInfoInputPanel;
import edu.nju.logistics.ui.courier.inputOrder.subPanel.OrderInputPanel;
import edu.nju.logistics.ui.courier.inputOrder.subPanel.PersonalInfoInputPanel;
import edu.nju.logistics.ui.main.MainFrame;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.UserVO;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class ShowPanel_InputOrder extends JPanel{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2764123038719757988L;
	
	private OrderInputPanel orderInputPanel;
	private PersonalInfoInputPanel senderInfoPanel;
	private PersonalInfoInputPanel receiverInfoPanel;
	private GoodsInfoInputPanel goodsInfoInputPanel;
	private FeeInputPanel feeInputPanel;
	private DayAndTimeCalculator dynamicObserver;
	private ErrorPanel errorPanel;
	private OKButton okButton;
	private UserVO userVO;
	private OrderManagementService orderManagement;
	private DistanceAndPriceGetter environmentGetter;
	private DisconnectInformer disconnectInformer;
	
	public final static Font LABELFONT = new java.awt.Font("Dialog", 0, 15);
	public final static Font INPUTFONT = new java.awt.Font("Dialog", 0, 15);


	public ShowPanel_InputOrder(OrderManagementService orderManagement,
			UserVO userVO, DistanceAndPriceGetter environmentGetter
			,DisconnectInformer disconnectInformer) {
		this.userVO = userVO;
		this.orderManagement = orderManagement;
		this.environmentGetter = environmentGetter;
		this.disconnectInformer = disconnectInformer;
		initShowPanel();
	}
	protected void initShowPanel() {
		setBorder(BorderFactory.createEtchedBorder());
		setOpaque(false);
		setLayout(null);
		
		orderInputPanel = new OrderInputPanel();
		orderInputPanel.setBounds(15, 15, 210, 30);
		orderInputPanel.setOpaque(false);
		add(orderInputPanel);
		
		errorPanel = new ErrorPanel();
		errorPanel.setBounds(290, 15, 500, 30);
		errorPanel.setOpaque(false);
		add(errorPanel);

		senderInfoPanel = new PersonalInfoInputPanel("寄件人");
		senderInfoPanel.setOpaque(false);
		senderInfoPanel.setBounds(15, 50, MainFrame.SHOWPANEL_WIDTH - 30, 95);
		add(senderInfoPanel);

		receiverInfoPanel = new PersonalInfoInputPanel("收件人");
		receiverInfoPanel.setOpaque(false);
		receiverInfoPanel
				.setBounds(15, 155, MainFrame.SHOWPANEL_WIDTH - 30, 95);
		add(receiverInfoPanel);

		goodsInfoInputPanel = new GoodsInfoInputPanel();
		goodsInfoInputPanel.setOpaque(false);
		goodsInfoInputPanel.setBounds(15, 155 + 95 + 10,
				MainFrame.SHOWPANEL_WIDTH - 30, 65);
		add(goodsInfoInputPanel);

		feeInputPanel = new FeeInputPanel();
		feeInputPanel.setOpaque(false);
		feeInputPanel.setBounds(15, 155 + 95 + 10 + 65 + 10,
				MainFrame.SHOWPANEL_WIDTH - 30, 65);
		add(feeInputPanel);

		int init_x = 500;
		int init_y = 455;
		okButton = new OKButton(orderManagement);
		okButton.setBounds(init_x, init_y, 160, 40);
		add(okButton);
		
		JButton clearButton = new ZYXButton("清空");
		clearButton.setBounds(init_x+220, init_y, 160, 40);
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				restartFrame();
			}
		});
		add(clearButton);

		dynamicObserver = new DayAndTimeCalculator(senderInfoPanel,
				receiverInfoPanel, feeInputPanel,environmentGetter,disconnectInformer);
		goodsInfoInputPanel.addWeightObserver(dynamicObserver);
		senderInfoPanel.addObserver(dynamicObserver);

		/* 以下代码用于全局监听enter键 */
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {  
            
            @Override  
            public void eventDispatched(AWTEvent event) {
                if(((KeyEvent)event).getID()==KeyEvent.KEY_PRESSED){  
                    switch (((KeyEvent)event).getKeyCode()) {  
                    case KeyEvent.VK_ENTER:okButton.listener.actionPerformed(null);
                        break;  
                    }  
                }  
            }  
        }, AWTEvent.KEY_EVENT_MASK); 
	}
	
	private void restartFrame() {
		orderInputPanel.clear();
		errorPanel.clear();
		senderInfoPanel.clear();
		receiverInfoPanel.clear();
		goodsInfoInputPanel.clear();
		feeInputPanel.clear();
	}
	
	public class OKButton extends ZYXButton {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1299086969521615897L;
		OKListener listener;
		private OrderManagementService orderManagement;

		public OKButton(OrderManagementService orderManagement) {
			super("输入下一订单");
			this.orderManagement = orderManagement;

			listener = new OKListener();

			this.addActionListener(listener);
		}

		class OKListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					errorPanel.clear();
//					System.out.println(senderInfoPanel.getAddress());
					OrderVO orderVO = new OrderVO(orderInputPanel.getOrderID(),senderInfoPanel.getPersonName()
							,senderInfoPanel.getAddress(),senderInfoPanel.getCompany(),senderInfoPanel.getTel()
							,senderInfoPanel.getPhone(),receiverInfoPanel.getPersonName()
							,receiverInfoPanel.getAddress(),receiverInfoPanel.getCompany(),receiverInfoPanel.getTel()
							,receiverInfoPanel.getPhone(),goodsInfoInputPanel.getGoodsName(),goodsInfoInputPanel.getAmount()
							,goodsInfoInputPanel.getRealWeight(),goodsInfoInputPanel.getGoodsSize(),feeInputPanel.getOrderKind()
							,feeInputPanel.getPackageMoney(),feeInputPanel.getTransportationCost(),userVO.getId(),userVO.getName());
					try {
						orderManagement.createOrder(orderVO,userVO.getId());
					} catch (RemoteException e1) {
						disconnectInformer.findDisconnect();
						return;
					}
					restartFrame();
	
					GlobalHintInserter.insertGlobalHint("创建订单成功！");
				} catch (EmptyException e1) {
					errorPanel.showError(e1.getKind());
				}

			}

			

		}
	}
}
