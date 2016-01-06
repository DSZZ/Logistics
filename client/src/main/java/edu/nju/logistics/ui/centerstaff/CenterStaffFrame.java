package edu.nju.logistics.ui.centerstaff;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.JPanel;

import edu.nju.logistics.ui.functionButton.MyButton;
import edu.nju.logistics.ui.main.MainFrame;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.reconnection.ReconnectPanel;
import edu.nju.logistics.ui.util.reconnection.ReconnectSuccessObserver;
import edu.nju.logistics.vo.UserVO;

/**
 * 中转中心业务员功能栏
 * 
 * @author HanzZou
 *
 */
public class CenterStaffFrame extends MainFrame implements ReconnectSuccessObserver, DisconnectInformer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new CenterStaffFrame(new UserVO("123", "123", "中转中心业务员"));
	}

//	private static final ImageIcon SENDIMG = new ImageIcon("Image/centerstaff/send.png");
//
//	private static final ImageIcon RECEIVEIMG = new ImageIcon("Image/centerstaff/receive.png");

	private UserVO user = null;

	private PanelSend panelSend = null;

	private PanelReceive panelReceive = null;
	
	private ReconnectPanel SHOWPANEL_RECONNECT = null;

//	private JLabel sendLabel = null;
//
//	private JLabel receiveLabel = null;
	
	private MyButton sendLabel = null;
	
	private MyButton receiveLabel = null;
	
	private MyButton temp = null;

	private Cursor cursor = null;

	public CenterStaffFrame(UserVO vo) {
		super(vo);
		this.user = vo;
		this.initializeComponent();
	}

	private void initializeComponent() {
		this.cursor = new Cursor(Cursor.HAND_CURSOR);
		this.sendLabel = new MyButton("发货");
		this.sendLabel.setBounds(BUTTON_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		this.sendLabel.addMouseListener(this);
		this.sendLabel.setCursor(cursor);

		this.receiveLabel = new MyButton("收货");
		this.receiveLabel.setBounds(BUTTON_X, BUTTON_Y + GAP, BUTTON_WIDTH, BUTTON_HEIGHT);
		this.receiveLabel.addMouseListener(this);
		this.receiveLabel.setCursor(cursor);
		this.mainPanel.add(this.sendLabel);
		this.mainPanel.add(this.receiveLabel);
		this.SHOWPANEL_RECONNECT = new ReconnectPanel(this);
		this.SHOWPANEL_RECONNECT.setBounds(SHOWPANEL_INIT_X, SHOWPANEL_INIT_Y,
				SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		this.getMainPanel().add(this.SHOWPANEL_RECONNECT);
		this.SHOWPANEL_RECONNECT.setVisible(false);
		
		this.initPanel();
	}
	
	private void initPanel() {
		try {
			this.panelSend = new PanelSend(this, user, this);
			this.panelReceive = new PanelReceive(this, user, this);
		} catch (RemoteException e) {
			findDisconnect();
			return;
		}
		this.showPanel.setLayout(null);
		this.showPanel.add(this.panelSend);
		this.showPanel.add(this.panelReceive);
		this.panelSend.setVisible(false);
		this.panelReceive.setVisible(false);

		this.repaint();
		this.handlePanel(this.panelSend);
	}

	private void handlePanel(JPanel panel) {
		panelSend.setVisible(false);
		panelReceive.setVisible(false);
		panel.setVisible(true);
	}

	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		if (e.getSource() == this.sendLabel) {
			if(temp!=null){
				temp.restore();
			}
			temp = (MyButton)e.getSource();
			temp.clicked();
			this.handlePanel(panelSend);
			return;
		}
		if (e.getSource() == this.receiveLabel) {
			if(temp!=null){
				temp.restore();
			}
			temp = (MyButton)e.getSource();
			temp.clicked();
			this.handlePanel(panelReceive);
			return;
		}
		this.repaint();
	}

	@Override
	public void findDisconnect() {
		this.handlePanel(SHOWPANEL_RECONNECT);
		this.SHOWPANEL_RECONNECT.setVisible(true);
		this.SHOWPANEL_RECONNECT.startReconnection();
	}

	@Override
	public void informReconnectSuccess() {
		this.SHOWPANEL_RECONNECT.setVisible(false);
		this.initPanel();
	}

}
