package edu.nju.logistics.ui.manager;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
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
import edu.nju.logistics.vo.UserVO;

@SuppressWarnings("serial")
public class AddUserDialog extends JDialog implements MouseListener{
	
	private  static final Image BACKGROUND=new ImageIcon("Image/manager/dialog.png").getImage();
	
	//private static final ImageIcon CONFIRM = new ImageIcon("Image/manager/confirm.png");
	
	private static final ImageIcon CLOSE=new ImageIcon("Image/login/关闭.png");

	private static final Font FONT = new Font("宋体", Font.BOLD, 16);
	
	private boolean flag_isAdd = false;

	private InstitutionVO institutionVO = null;
	
    private JLabel close_lab=null,name_lab=null,ID_lab=null,role_lab=null;
    
    private commonButton confirm_lab=null;
	
	private JComboBox<String> role_box=null;
	
	private JTextField name_jtf=null,ID_jtf=null;
	
	private JPanel panel=null;
	
	private Cursor cursor=null;
	
	private Point origin=null;
	
	private OperationManagementBLService controller=null;
	
	private DisconnectInformer disconnectInformer=null;

	public AddUserDialog(JFrame owner, String title, boolean modal, 
			InstitutionVO institutionVO, OperationManagementBLService controller,
			DisconnectInformer disconnectInformer) {
		super(owner, title, modal);
		this.institutionVO = institutionVO;
		this.disconnectInformer=disconnectInformer;
		this.controller=controller;
		
		this.setLayout(null);
		this.setUndecorated(true);
		this.setSize(350,300);
		FrameUtil.setFrameCenter(this);
		//初始化组件
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
	private void initComponent() {
		this.cursor=new Cursor(Cursor.HAND_CURSOR);
		
		this.panel=new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(BACKGROUND, 0, 0, this.getWidth(),this.getHeight(),null);
			}
		};
		this.panel.setLayout(null);
		this.panel.setSize(350,350);
		this.panel.setBorder(BorderFactory.createTitledBorder(null, "增加员工",0,0, new Font("楷体",Font.BOLD,14), Color.GREEN));
		
		this.close_lab=new JLabel(CLOSE);
		this.close_lab.setBounds(330,5,20,20);
		this.close_lab.addMouseListener(this);
		this.close_lab.setCursor(this.cursor);
		this.panel.add(this.close_lab);
		
		this.ID_lab=new JLabel("ID:");
		this.ID_lab.setFont(FONT);
		this.ID_lab.setBounds(100,20,60,25);
		this.panel.add(this.ID_lab);
		
		this.ID_jtf=new JTextField();
		this.ID_jtf.setBounds(100,50,150,25);
		this.panel.add(this.ID_jtf);
	
		this.name_lab=new JLabel("姓名:");
		this.name_lab.setFont(FONT);
		this.name_lab.setBounds(100,85,60,25);
		this.panel.add(this.name_lab);
		
		this.name_jtf=new JTextField();
		this.name_jtf.setBounds(100,115,150,25);
		this.panel.add(this.name_jtf);
		
		this.role_lab=new JLabel("职位：");
		this.role_lab.setFont(FONT);
		this.role_lab.setBounds(100,150,60,25);
		this.panel.add(this.role_lab);
		
		String[] roles=this.roleBoxSwitch(this.institutionVO.getType());
		this.role_box=new JComboBox<String>(roles);
		this.role_box.setBounds(100,180,150,25);
		this.panel.add(this.role_box);
		
		this.confirm_lab=new commonButton("确认");
		this.confirm_lab.setCursor(this.cursor);
		this.confirm_lab.addMouseListener(this);
		this.confirm_lab.setBounds(122,235,100,40);
		this.panel.add(this.confirm_lab);
		
		this.add(this.panel);
	}
	/**
	 * 根据机构种类显示职位下拉框
	 * @param type
	 */
	private String[] roleBoxSwitch(String type) {
		if(type.equals("中转中心")){
			String roles[]={"总经理","财务人员(高)","财务人员(低)","管理员","中转中心库存管理员","中转中心业务员"};
			return roles;
		}
		else{
			String roles[]={"总经理","财务人员(高)","财务人员(低)","管理员","营业厅业务员","快递员"};
			return roles;
		}
		
	}
	/**
	 * 返回是否成功添加标志
	 * @return
	 */
	public boolean getFlag() {
		return this.flag_isAdd;
	}
	/**
	 * 是否为格式合法的ID
	 * @param text
	 * @return
	 */
	private boolean isLegalID(String ID) {
		if(ID.length()!=7){
			return false;
		}
		for (int i = 0; i < ID.length(); i++) {
			char temp=ID.charAt(i);
			if(temp<'0'||temp>'9'){
				return false;
			}
		}
		return true;
	}
	/**
	 * 是否为格式合法的名字
	 * @param text
	 * @return
	 */
	private boolean isLegalName(String name) {
		if((name.length()==0)||(name.length()>10)){
			return false;
		}
		return true;
	}
	/**
	 * 根据职位处理机构信息
	 * @param selectedItem
	 * @return
	 */
	private InstitutionVO handleInstitution(String role) {
		if(role.equals("总经理")||role.equals("财务人员(高)")||
				role.equals("财务人员(低)")||role.equals("管理员")){
			return new InstitutionVO();
		}
			return this.institutionVO;
	}
	/**
	 * 点击确认后的操作
	 * @throws RemoteException 
	 * @throws HeadlessException 
	 */
	private void confirm() throws RemoteException{
		String ID=this.ID_jtf.getText();
		String name=this.name_jtf.getText();
		String role=(String)this.role_box.getSelectedItem();
		//如果输入非法则返回
		if((!this.isLegalID(ID))||(!this.isLegalName(name))){
			JOptionPane.showMessageDialog(this, "输入非法！\n请检查是否有非法字符或者输入空白");
			return;
		}
		InstitutionVO insVO=this.handleInstitution(role);
		
		UserVO vo=new UserVO(ID,name,role,insVO,0,0,0,false);
		if(!this.controller.addEmployee(vo)){
			JOptionPane.showMessageDialog(this, "该ID已存在!\n或登录信息还存在该ID");
			return;
		}
		//成功添加
		this.flag_isAdd=true;
		JOptionPane.showMessageDialog(this, "添加成功！");
		//关闭
		this.dispose();
	
	}
	
	public void mouseClicked(MouseEvent e) {
		try{
		if(e.getSource()==this.confirm_lab){
			this.confirm();
		}
		else if(e.getSource()==this.close_lab){
			this.dispose();
		}
		}catch(RemoteException e1){
			this.dispose();
			this.disconnectInformer.findDisconnect();
		}
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
