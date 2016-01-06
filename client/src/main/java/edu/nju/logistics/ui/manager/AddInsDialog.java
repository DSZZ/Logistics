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
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.nju.logistics.bl.impl.operationmanagement.CityHelper;
import edu.nju.logistics.bl.service.operationmanagement.OperationManagementBLService;
import edu.nju.logistics.ui.commonButton.commonButton;
import edu.nju.logistics.ui.util.FrameUtil;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.InstitutionVO;

@SuppressWarnings("serial")
public class AddInsDialog extends JDialog implements MouseListener{

	private  static final Image BACKGROUND=new ImageIcon("Image/manager/dialog.png").getImage();
	
	//private static final ImageIcon CONFIRM=new ImageIcon("Image/manager/confirm.png");
	
	private static final ImageIcon CLOSE=new ImageIcon("Image/login/关闭.png");
	
	private static final Font FONT=new Font("宋体", Font.BOLD, 16);
	
	private boolean flag_isAdd=false;
	
	private JLabel close_lab=null,location_lab=null,ID_lab=null,zone_lab=null,rental_lab=null;
	
	private commonButton confirm_lab=null;
	
	private JComboBox<String> location_box=null;
	
	private JTextField zone_jtf=null,ID_jtf=null,rental_jtf=null;
	
	private JPanel panel=null;
	
	private Cursor cursor=null;
	
	private String type;
	
	private Point origin=null;
	
	private OperationManagementBLService controller=null;
	
	private DisconnectInformer disconnectInformer=null;
	
	public AddInsDialog(JFrame owner,String title,boolean modal,
			OperationManagementBLService controller,DisconnectInformer disconnectInformer) {
		super(owner,title,modal);
		this.disconnectInformer=disconnectInformer;
		this.controller=controller;
		
		this.setLayout(null);
		this.setSize(350,300);
		this.setUndecorated(true);
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
	/**
	 * 初始化组件
	 */
	private void initComponent() {
		this.cursor=new Cursor(Cursor.HAND_CURSOR);
		
		this.panel=new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(BACKGROUND, 0, 0, this.getWidth(),this.getHeight(),null);
			}
		};
		
		this.close_lab=new JLabel(CLOSE);
		this.close_lab.setBounds(330,5,20,20);
		this.close_lab.addMouseListener(this);
		this.close_lab.setCursor(this.cursor);
		this.panel.add(this.close_lab);
		
		this.panel.setLayout(null);
		this.panel.setSize(350,350);
		this.panel.setBorder(BorderFactory.createTitledBorder(null, "增加机构",0,0, new Font("楷体",Font.BOLD,14), Color.GREEN));

		
		this.location_lab=new JLabel("地点:");
		this.location_lab.setFont(FONT);
		this.location_lab.setBounds(100,20,60,25);
		this.panel.add(this.location_lab);
		
		String cities[]=this.getAllCities();
		this.location_box=new JComboBox<String>(cities);
		this.location_box.setBounds(100,50,150,25);
		this.location_box.setSelectedItem("南京");
		this.location_box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String city=(String)location_box.getSelectedItem();
				handleZone(city);
			}
		});
		this.panel.add(this.location_box);
		
		this.ID_lab=new JLabel("ID:");
		this.ID_lab.setFont(FONT);
		this.ID_lab.setBounds(100,85,60,25);
		this.panel.add(this.ID_lab);
		
		this.zone_lab=new JLabel("(区号)");
		this.zone_lab.setForeground(Color.RED);
		this.zone_lab.setBounds(105,112,50,20);
		this.panel.add(this.zone_lab);
		
		this.zone_jtf=new JTextField();
		this.zone_jtf.setText("0025");
		this.zone_jtf.setBounds(100,137,50,25);
		this.zone_jtf.setEditable(false);
		this.panel.add(this.zone_jtf);
		
		this.ID_jtf=new JTextField();
		this.ID_jtf.setBounds(160,137,90,25);
		this.panel.add(this.ID_jtf);
		
		this.rental_lab=new JLabel("租金：");
		this.rental_lab.setFont(FONT);
		this.rental_lab.setBounds(100,172,60,25);
		this.panel.add(this.rental_lab);
		
		this.rental_jtf=new JTextField();
		this.rental_jtf.setBounds(100,202,150,25);
		this.panel.add(this.rental_jtf);
		
		this.confirm_lab=new commonButton("确认");
		this.confirm_lab.setCursor(this.cursor);
		this.confirm_lab.addMouseListener(this);
		this.confirm_lab.setBounds(122,245,100,40);
		this.panel.add(this.confirm_lab);
		
		this.add(this.panel);
	}
	/**
	 * 获得所有的城市名单
	 * @return
	 */
	private String[] getAllCities() {
		ArrayList<String> list=CityHelper.getCityName();
		String city[]=new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			city[i]=list.get(i);
		}
		return city;
	}
	/**
	 * 根据城市来固定区号
	 * @param city
	 */
	protected void handleZone(String city) {
		HashMap<String,String> map=CityHelper.getCityMap();
		this.zone_jtf.setText(map.get(city));
		
	}
	/**
	 * 根据租金输入判断是否合法
	 * @param line
	 * @return
	 */
	private boolean isLegalRental(String line) {
		int count=0;
	
		if(line.equals("")){
			return false;
		}
		for (int i = 0; i < line.length(); i++) {
			if(((line.charAt(i)<'0')&&(line.charAt(i)!='.'))||(line.charAt(i)>'9')){
				return false;
			}
			else if(line.charAt(i)=='.'){
				count++;
				if(count==2){
					return false;
				}
				if((i!=line.length()-3)&&(i!=line.length()-2)){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * 根据ID输入判断是否合法
	 * @param line
	 * @return
	 */
	private boolean isLegalID(String line) {
		if((line.length()!=4)&&(line.length()!=2)){
			return false;
		}
		for (int i = 0; i < line.length(); i++) {
			if((line.charAt(i)<'0')||(line.charAt(i)>'9')){
				return false;
			}
		}
		if(line.length()==2){
			this.type="中转中心";
		}
		else if(line.length()==4){
			this.type="营业厅";
		}
		return true;
	}
	/**
	 * 返回是否添加成功标志
	 * @return
	 */
	public boolean getFlag(){
			return this.flag_isAdd;
	}
	/**
	 * 点击确认后的操作
	 * @throws RemoteException 
	 * @throws HeadlessException 
	 */
	private void confirm() throws HeadlessException, RemoteException{
		String location=(String)this.location_box.getSelectedItem();
		//判断ID和租金的输入是否合法
		if((!this.isLegalID(this.ID_jtf.getText().trim()))||
				(!this.isLegalRental(this.rental_jtf.getText().trim()))){
			JOptionPane.showMessageDialog(this, "输入非法！\n请检查是否有非法字符或者输入空白");
			return;
		}
		String ID=this.zone_jtf.getText()+this.ID_jtf.getText().trim();

		double rental=Double.parseDouble(this.rental_jtf.getText().trim());
		
		InstitutionVO vo=new InstitutionVO(location, ID, this.type, rental);
		if(!this.controller.addInstitution(vo)){
			JOptionPane.showMessageDialog(this, "该ID已存在\n或其所属中转中心尚未建立");
			return;
		}
		//成功添加
		this.flag_isAdd=true;
		JOptionPane.showMessageDialog(this, "添加成功！");
		//关闭
		this.dispose();
	}
	
	public void mouseClicked(MouseEvent e) {
			try {
				if(e.getSource()==this.confirm_lab){
					this.confirm();
				}
				else if(e.getSource()==this.close_lab){
					this.dispose();
				}
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (RemoteException e1) {
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
