package edu.nju.logistics.ui.manager;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
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
public class ModifyUserDialog extends JDialog implements MouseListener{

	private  static final Image BACKGROUND=new ImageIcon("Image/manager/dialog.png").getImage();
	
	//private static final ImageIcon CONFIRM = new ImageIcon("Image/manager/confirm.png");
	
	private static final ImageIcon CLOSE=new ImageIcon("Image/login/关闭.png");

	private static final Font FONT = new Font("宋体", Font.BOLD, 16);
	
	private boolean flag_isModify=false;
	
	private OperationManagementBLService controller=null;
	
	private UserVO vo=null;
	
    private JLabel close_lab=null,ins_lab=null,name_lab=null,ID_lab=null,role_lab=null;
	
    private commonButton confirm_lab=null;
    
	private JComboBox<String> role_box=null,ins_box=null;
	
	private JTextField name_jtf=null,ID_jtf=null;
	
	private JPanel panel=null;
	
	private Cursor cursor=null;
	
	private DisconnectInformer disconnectInformer=null;

	private Point origin=null;
	
	public ModifyUserDialog(JFrame owner,String title,boolean modal, 
			OperationManagementBLService controller, UserVO vo,DisconnectInformer disconnectInformer) {
		super(owner,title,modal);
		this.disconnectInformer=disconnectInformer;
		this.controller=controller;
		this.vo=vo;
		
		this.setLayout(null);
		this.setUndecorated(true);
		this.setSize(350,360);
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
		this.panel.setBorder(BorderFactory.createTitledBorder(null, "修改员工信息",0,0, new Font("楷体",Font.BOLD,14), Color.GREEN));
		
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
		this.ID_jtf.setText(this.vo.getId());
		this.panel.add(this.ID_jtf);
	
		this.name_lab=new JLabel("姓名:");
		this.name_lab.setFont(FONT);
		this.name_lab.setBounds(100,85,60,25);
		this.panel.add(this.name_lab);
		
		this.name_jtf=new JTextField();
		this.name_jtf.setBounds(100,115,150,25);
		this.name_jtf.setText(this.vo.getName());
		this.panel.add(this.name_jtf);
		
		this.role_lab=new JLabel("职位：");
		this.role_lab.setFont(FONT);
		this.role_lab.setBounds(100,150,60,25);
		this.panel.add(this.role_lab);
		
		String init[]={this.getSingleIns()};
		this.ins_box=new JComboBox<String>(init);
		//this.ins_box.setSelectedItem(this.getSingleIns());
		this.ins_box.setBounds(100,245,160,25);
		this.panel.add(this.ins_box);
		
		String[] roles={"总经理","财务人员(高)","财务人员(低)","管理员","中转中心业务员","中转中心库存管理员","营业厅业务员","快递员"};
		this.role_box=new JComboBox<String>(roles);
		this.role_box.setSelectedItem(this.vo.getRole());
		this.role_box.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				try{
				String role=(String)role_box.getSelectedItem();
				ComboBoxModel<String> model = new BoxModel(role);
				ins_box.setModel(model);
				ins_box.setSelectedIndex(0);;
				}catch(RemoteException e1){
					dispose();
					disconnectInformer.findDisconnect();
				}
			}
		});
		this.role_box.setBounds(100,180,150,25);
		this.panel.add(this.role_box);
		
		this.ins_lab=new JLabel("所属机构:");
		this.ins_lab.setFont(FONT);
		this.ins_lab.setBounds(100,215,150,25);
		this.panel.add(this.ins_lab);
		
		this.confirm_lab=new commonButton("确认");
		this.confirm_lab.setCursor(this.cursor);
		this.confirm_lab.addMouseListener(this);
		this.confirm_lab.setBounds(122,290,100,40);
		this.panel.add(this.confirm_lab);
		
		this.add(this.panel);
	}
	/**
	 * 获得初始化的下拉选项
	 */
	private String getSingleIns(){
		if(this.vo.getRole().equals("总经理")||this.vo.getRole().equals("财务人员(高)")||
				this.vo.getRole().equals("财务人员(低)")||this.vo.getRole().equals("管理员")){
			return "无";
		}
		return this.vo.getInstitution().toString();
	}
	/**
	 * 返回修改成功标志
	 * @return
	 */
	public boolean getFlag() {
		return this.flag_isModify;
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
	 * 根据职位处理机构
	 * @param role
	 * @return
	 */
	private InstitutionVO handleInstitution(String role) {
		if(role.equals("总经理")||role.equals("财务人员(高)")||
				role.equals("财务人员(低)")||role.equals("管理员")){
			return new InstitutionVO();
		}
			String split[]=((String)this.ins_box.getSelectedItem()).split(" ");
			String location=split[0];
			String ID=split[1];
			String type=split[2];
			return new InstitutionVO(location, ID, type);
	}
	/**
	 * 确认后的操作
	 * @throws RemoteException 
	 * @throws HeadlessException 
	 */
	private void confirm() throws  RemoteException{
		//旧数据
        String oldID=this.vo.getId();
        String oldName=this.vo.getName();
        String oldRole=this.vo.getRole();
        InstitutionVO oldIns=this.vo.getInstitution();
        //新数据
        String newID=this.ID_jtf.getText();
		String name=this.name_jtf.getText();
		String role=(String)this.role_box.getSelectedItem();
		//如果输入非法则返回
		if((!this.isLegalName(name))||(!this.isLegalID(newID))){
			JOptionPane.showMessageDialog(this, "输入非法！\n请检查是否有非法字符或者输入空白");
			return;
		}
		if(role.equals("营业厅业务员")||
				role.equals("快递员")||
				role.equals("中转中心库存管理员")||
				role.equals("中转中心业务员")){
			if(this.ins_box.getSelectedItem()==null){
				JOptionPane.showMessageDialog(this, "请选择一项机构！");
				return;
			}
		}
		InstitutionVO insVO=this.handleInstitution(role);
		this.vo.setId(newID);
		this.vo.setName(name);
		this.vo.setRole(role);
		this.vo.setInstitution(insVO);
		if(!this.controller.updateEmployee(oldID,this.vo)){
			//还原数据
			this.vo.setId(oldID);
			this.vo.setName(oldName);
			this.vo.setRole(oldRole);
			this.vo.setInstitution(oldIns);
			JOptionPane.showMessageDialog(this, "信息修改失败!");
			return;
		}
		//成功添加
		this.flag_isModify=true;
		JOptionPane.showMessageDialog(this, "修改信息成功！");
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
		}catch(RemoteException  e1){
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
	@SuppressWarnings("rawtypes")
	class BoxModel extends AbstractListModel implements ComboBoxModel {

		private String ins[] = null;

		private String item = null;

		public BoxModel(String role) throws RemoteException {
			this.init(role);
		}
		
		private void init(String role) throws RemoteException {
			ArrayList<InstitutionVO> vo=controller.institutionShow();
			//统计营业厅数量和中转中心数量
			int hallCount=0;
			int centerCount=0;
			for (int i = 0; i < vo.size(); i++) {
				if(vo.get(i).getType().equals("营业厅")){
					hallCount++;
				}
			}
			centerCount=vo.size()-hallCount;
			
			if(role.equals("总经理")||role.equals("财务人员(高)")||
					role.equals("财务人员(低)")||role.equals("管理员")){
				this.highRole();
			}
			else if(role.equals("营业厅业务员")||role.equals("快递员")){
				this.branchRole(hallCount,vo);
			}
			else if(role.equals("中转中心库存管理员")||role.equals("中转中心业务员")){
				this.centerRole(centerCount,vo);
			}
		
			
		}
		/**
		 * 中转中心职位
		 * @param centerCount
		 * @param vo
		 */
		private void centerRole(int centerCount, ArrayList<InstitutionVO> vo) {
			this.ins=new String[centerCount];
			for (int i = 0; i < centerCount; i++) {
				for (int j = 0; j < vo.size(); j++) {
					if(vo.get(j).getType().equals("中转中心")){
						this.ins[i]=vo.get(j).toString();
						vo.remove(j);
						break;
					}
				}
			}
		
			
		}

		/**
		 * 营业厅职位
		 * @param vo 
		 * @param hallCount 
		 */
      private void branchRole(int hallCount, ArrayList<InstitutionVO> vo) {
			this.ins=new String[hallCount];
			for (int i = 0; i < hallCount; i++) {
				for (int j = 0; j < vo.size(); j++) {
					if(vo.get(j).getType().equals("营业厅")){
						this.ins[i]=vo.get(j).toString();
						vo.remove(j);
						break;
					}
				}
			}
		
			
		}

	/**
       * 高职位
       */
		private void highRole(){
			this.ins=new String[1];
			this.ins[0]="无";
		}
		@Override
		public Object getElementAt(int index) {
			return this.ins[index];
		}

		@Override
		public int getSize() {

			return this.ins.length;
		}

		@Override
		public Object getSelectedItem() {
			return item;
		}

		@Override
		public void setSelectedItem(Object anItem) {
			item = (String) anItem;

		}

	}
}
