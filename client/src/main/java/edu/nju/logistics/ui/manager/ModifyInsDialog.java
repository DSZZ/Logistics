package edu.nju.logistics.ui.manager;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.nju.logistics.bl.service.operationmanagement.OperationManagementBLService;
import edu.nju.logistics.ui.commonButton.commonButton;
import edu.nju.logistics.ui.util.FrameUtil;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.InstitutionVO;

@SuppressWarnings("serial")
public class ModifyInsDialog extends JDialog implements MouseListener {
	
	private  static final Image BACKGROUND=new ImageIcon("Image/manager/dialog.png").getImage();
	
	//private static final ImageIcon CONFIRM = new ImageIcon("Image/manager/confirm.png");
	
	private static final ImageIcon CLOSE=new ImageIcon("Image/login/关闭.png");

	private static final Font FONT = new Font("宋体", Font.BOLD, 16);

	private boolean flag_isModify = false;

	private JLabel close_lab=null, location_lab = null, ID_lab = null, zone_lab = null, rental_lab = null;

	private commonButton confirm_lab = null;
	
	private JTextField location_jtf = null, zone_jtf = null, ID_jtf = null, rental_jtf = null;

	private JPanel panel = null;

	private Cursor cursor = null;

	private OperationManagementBLService controller = null;

	private InstitutionVO vo = null;
	
	private Point origin=null;
	
	private DisconnectInformer disconnectInformer=null;

	public ModifyInsDialog(JFrame owner, String title, boolean modal, OperationManagementBLService controller,
			InstitutionVO vo,DisconnectInformer disconnectInformer ) {
		super(owner, title, true);
		this.disconnectInformer=disconnectInformer;
		this.controller = controller;
		this.vo = vo;

		this.setLayout(null);
		this.setSize(350, 300);
		this.setUndecorated(true);
		FrameUtil.setFrameCenter(this);
		// 初始化组件
		this.initComponent();
		
		this.origin=new Point();
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// 当鼠标按下的时候获得窗口当前的位置
				origin.x = e.getX();
				origin.y = e.getY();
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				// 当鼠标拖动时获取窗口当前位置
				Point p = getLocation();
				// 设置窗口的位置
				// 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
				setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
			}
		});
	}

	/**
	 * 初始化组件
	 */
	private void initComponent() {
		this.cursor = new Cursor(Cursor.HAND_CURSOR);

		this.panel = new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(BACKGROUND, 0, 0, this.getWidth(),this.getHeight(),null);
			}
		};
		this.panel.setLayout(null);
		this.panel.setSize(350, 350);
		this.panel.setBorder(BorderFactory.createTitledBorder(null, "修改机构信息",0,0, new Font("楷体",Font.BOLD,14), Color.GREEN));
		
		this.close_lab=new JLabel(CLOSE);
		this.close_lab.setBounds(330,5,20,20);
		this.close_lab.addMouseListener(this);
		this.close_lab.setCursor(this.cursor);
		this.panel.add(this.close_lab);

		this.location_lab = new JLabel("地点:");
		this.location_lab.setFont(FONT);
		this.location_lab.setBounds(100, 20, 60, 25);
		this.panel.add(this.location_lab);

		this.location_jtf = new JTextField();
		this.location_jtf.setBounds(100, 50, 150, 25);
		this.location_jtf.setText(vo.getLocation());
		this.location_jtf.setEditable(false);
		this.panel.add(this.location_jtf);

		this.ID_lab = new JLabel("ID:");
		this.ID_lab.setFont(FONT);
		this.ID_lab.setBounds(100, 85, 60, 25);
		this.panel.add(this.ID_lab);

		this.zone_lab = new JLabel("(区号)");
		this.zone_lab.setForeground(Color.RED);
		this.zone_lab.setBounds(105, 112, 50, 20);
		this.panel.add(this.zone_lab);

		this.zone_jtf = new JTextField();
		this.zone_jtf.setText(this.vo.getId().substring(0, 4));
		this.zone_jtf.setBounds(100, 137, 50, 25);
		this.zone_jtf.setEditable(false);
		this.panel.add(this.zone_jtf);

		this.ID_jtf = new JTextField();
		this.ID_jtf.setText(this.vo.getId().substring(4, this.vo.getId().length()));
		this.ID_jtf.setBounds(160, 137, 90, 25);
		this.panel.add(this.ID_jtf);

		this.rental_lab = new JLabel("租金：");
		this.rental_lab.setFont(FONT);
		this.rental_lab.setBounds(100, 172, 60, 25);
		this.panel.add(this.rental_lab);

		this.rental_jtf = new JTextField();
		this.rental_jtf.setText(this.vo.getRental() + "");
		this.rental_jtf.setBounds(100, 202, 150, 25);
		this.panel.add(this.rental_jtf);

		this.confirm_lab = new commonButton("确认");
		this.confirm_lab.setCursor(this.cursor);
		this.confirm_lab.addMouseListener(this);
		this.confirm_lab.setBounds(122, 245, 100, 40);
		this.panel.add(this.confirm_lab);

		this.add(this.panel);
	}

	/**
	 * 根据租金输入判断是否合法
	 * 
	 * @param line
	 * @return
	 */
	private boolean isLegalRental(String line) {
		int count = 0;

		if (line.equals("")) {
			return false;
		}
		for (int i = 0; i < line.length(); i++) {
			if (((line.charAt(i) < '0') && (line.charAt(i) != '.')) || (line.charAt(i) > '9')) {
				return false;
			} else if (line.charAt(i) == '.') {
				count++;
				if (count == 2) {
					return false;
				}
				if ((i != line.length() - 3) && (i != line.length() - 2)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 根据ID输入判断是否合法
	 * 
	 * @param line
	 * @return
	 */
	private boolean isLegalID(String line) {
		if (this.vo.getType().equals("中转中心")) {
			if (line.length() != 2) {
				return false;
			}
		}
		if (this.vo.getType().equals("营业厅")) {
			if (line.length() != 4) {
				return false;
			}
		}
		for (int i = 0; i < line.length(); i++) {
			if ((line.charAt(i) < '0') || (line.charAt(i) > '9')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 得到是否修改成功表示
	 * 
	 * @return
	 */
	public boolean getFlag() {

		return this.flag_isModify;
	}

	public void mouseClicked(MouseEvent e) {
		try {
			if (e.getSource() == this.confirm_lab) {
				this.confirm();
			}
			else if(e.getSource()==this.close_lab){
				this.dispose();
			}
		} catch (RemoteException e1) {
			this.dispose();
			this.disconnectInformer.findDisconnect();
		}
	}

	private void confirm() throws RemoteException {

		if ((!this.isLegalID(this.ID_jtf.getText().trim()))
				|| (!this.isLegalRental(this.rental_jtf.getText().trim()))) {
			JOptionPane.showMessageDialog(this, "输入非法！\n请检查是否有非法字符或者输入空白");
			return;
		}
		String newID = this.zone_jtf.getText() + this.ID_jtf.getText().trim();
		String oldID = this.vo.getId();
		double oldRental=this.vo.getRental();
		double rental = Double.parseDouble(this.rental_jtf.getText().trim());

		this.vo.setId(newID);
		this.vo.setRental(rental);

		if (!this.controller.updateInstitution(oldID, this.vo)) {
			//还原
			this.vo.setId(oldID);
			this.vo.setRental(oldRental);
			JOptionPane.showMessageDialog(this, "该ID已存在\n或其所属中转中心尚未建立");
			return;
		}

		// 成功添加
		this.flag_isModify = true;
		JOptionPane.showMessageDialog(this, "修改信息成功！");
		// 关闭
		this.dispose();
	
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

}
