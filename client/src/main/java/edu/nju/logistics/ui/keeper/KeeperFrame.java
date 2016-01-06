package edu.nju.logistics.ui.keeper;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.nju.logistics.bl.impl.storehousemanagement.StorehouseManagementController;
import edu.nju.logistics.bl.service.storehousemanagement.StorehouseManagementBLService;
import edu.nju.logistics.ui.functionButton.MyButton;
import edu.nju.logistics.ui.main.MainFrame;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.reconnection.ReconnectPanel;
import edu.nju.logistics.ui.util.reconnection.ReconnectSuccessObserver;
import edu.nju.logistics.vo.UserVO;

/**
 * 中转中心库存管理员功能区
 * 
 * @author HanzZou
 *
 */
public class KeeperFrame extends MainFrame implements ReconnectSuccessObserver, DisconnectInformer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private static final ImageIcon IMPORTIMG = new ImageIcon("Image/keeper/importImg.png");
//
//	private static final ImageIcon EXPORTIMG = new ImageIcon("Image/keeper/exportImg.png");
//
//	private static final ImageIcon CHECKIMG = new ImageIcon("Image/keeper/checkImg.png");
//
//	private static final ImageIcon SEARCHIMG = new ImageIcon("Image/keeper/searchImg.png");
//
//	private static final ImageIcon ADJUSTIMG = new ImageIcon("Image/keeper/adjustImg.png");
//
//	private static final ImageIcon SETALARMIMG = new ImageIcon("Image/keeper/setAlarmImg.png");
//
//	private static final ImageIcon INITIALIZEIMG = new ImageIcon("Image/keeper/initializeImg.png");

	private UserVO user = null;

	/**
	 * 库区调整面板
	 */
	private PanelAdjust panelAdjust;

	/**
	 * 库存盘点面板
	 */
	private PanelCheck panelCheck;

	/**
	 * 登记出库面板
	 */
	private PanelExport panelExport;

	/**
	 * 登记入库面板
	 */
	private PanelImport panelImport;

	/**
	 * 库存初始化面板
	 */
	private PanelInitialize panelInitialize;

	/**
	 * 库存查看面板
	 */
	private PanelSearch panelSearch;

	/**
	 * 库存警戒值设定面板
	 */
	private PanelSetAlarm panelSetAlarm;
	
	/**
	 * 断线重连面板
	 */
	private ReconnectPanel SHOWPANEL_RECONNECT;

	private MyButton importButton = null;

	private MyButton exportButton = null;

	private MyButton setAlarmButton = null;

	private MyButton initializeButton = null;

	private MyButton checkButton = null;

	private MyButton searchButton = null;

	private MyButton adjustButton = null;
	
	private MyButton temp = null;	//改变按钮图片

	private Cursor myCursor = null;

	private JPanel[] panelList = null;

	private HashMap<JLabel, JPanel> map = null;

	private StorehouseManagementBLService controller = null;

	public KeeperFrame(UserVO vo) {
		super(vo);
		this.user = vo;
		this.initButtons();
		this.initializePanels();
	}
	
	private void initButtons() {
		// 按钮
		this.myCursor = new Cursor(Cursor.HAND_CURSOR);
		this.importButton = new MyButton("入库");
		this.exportButton = new MyButton("出库");
		this.checkButton = new MyButton("库存盘点");
		this.searchButton = new MyButton("库存查看");
		this.adjustButton = new MyButton("库存调整");
		this.initializeButton = new MyButton("库存初始化");
		this.setAlarmButton = new MyButton("警戒值设定");
		JLabel[] buttons = { importButton, exportButton, checkButton, searchButton, adjustButton, initializeButton,
				setAlarmButton };
		int temp = 0;
		for (JLabel button : buttons) {
			button.setBounds(BUTTON_X, BUTTON_Y + GAP * temp++, BUTTON_WIDTH, BUTTON_HEIGHT);
			button.setCursor(myCursor);
			button.addMouseListener(this);
			this.mainPanel.add(button);
		}
		this.SHOWPANEL_RECONNECT = new ReconnectPanel(this);
		this.SHOWPANEL_RECONNECT.setBounds(SHOWPANEL_INIT_X, SHOWPANEL_INIT_Y,
				SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		this.SHOWPANEL_RECONNECT.setVisible(false);
		this.getMainPanel().add(this.SHOWPANEL_RECONNECT);
	}

	private void initializePanels() {
		// 面板
		try {
			//逻辑控制 
			this.controller = new StorehouseManagementController();
			this.panelImport = new PanelImport(user, this);
			this.panelExport = new PanelExport(user, this);
			this.panelCheck = new PanelCheck(user, this);
			this.panelSearch = new PanelSearch(user, this);
			this.panelAdjust = new PanelAdjust(user, this);
			this.panelInitialize = new PanelInitialize(user, this);
			this.panelSetAlarm = new PanelSetAlarm(user, this);
		} catch (RemoteException e) {
			findDisconnect();
			return;
		}
		this.buildPanels();
	}
	
	private void buildPanels() {
		this.panelList = new JPanel[] { this.panelAdjust, this.panelCheck, this.panelExport, this.panelImport,
				this.panelInitialize, this.panelSearch, this.panelSetAlarm };

		this.showPanel.setLayout(null);
		for (JPanel p : panelList) {
			this.showPanel.add(p);
			p.setVisible(false);
		}

		this.map = new HashMap<JLabel, JPanel>();
		this.map.put(this.setAlarmButton, this.panelSetAlarm);
		this.map.put(this.initializeButton, this.panelInitialize);
		this.map.put(this.adjustButton, this.panelAdjust);
		this.map.put(this.searchButton, this.panelSearch);
		this.map.put(this.checkButton, this.panelCheck);
		this.map.put(this.exportButton, this.panelExport);
		this.map.put(this.importButton, this.panelImport);

		this.repaint();
		this.handlePanel(this.panelImport);
	}

	private void handlePanel(JPanel panel) {
		for (JPanel p : this.panelList) {
			p.setVisible(false);
		}
		panel.setVisible(true);
	}

	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);

		Set<Entry<JLabel, JPanel>> entryset = this.map.entrySet();
		for (Entry<JLabel, JPanel> arg : entryset) {
			if (e.getSource() == arg.getKey()) {
				if(temp != null)
					temp.restore();
				temp = (MyButton)e.getSource();
				temp.clicked();
				if (e.getSource() == initializeButton) {
					int i = JOptionPane.showConfirmDialog(null, "确定清空库存？", "确认初始化", JOptionPane.YES_NO_OPTION);
					if (i == 0) {
						this.handlePanel(panelInitialize);
						try {
							controller.clear(controller.getInstitutionIDByUser(this.user.getId()));
						} catch (RemoteException e1) {
							findDisconnect();
							return;
						}
					}
				} else
					this.handlePanel(this.map.get(e.getSource()));
			}
		}
		this.repaint();
	}

	/**
	 * 显示重连界面
	 */
	@Override
	public void findDisconnect() {
		this.handlePanel(this.SHOWPANEL_RECONNECT);
		this.SHOWPANEL_RECONNECT.setVisible(true);
		this.SHOWPANEL_RECONNECT.startReconnection();
	}

	@Override
	public void informReconnectSuccess() {
		this.SHOWPANEL_RECONNECT.setVisible(false);
		this.initializePanels();
	}

}
