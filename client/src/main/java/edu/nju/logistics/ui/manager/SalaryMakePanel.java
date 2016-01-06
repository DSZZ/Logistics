package edu.nju.logistics.ui.manager;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.nju.logistics.bl.service.operationmanagement.OperationManagementBLService;
import edu.nju.logistics.ui.commonButton.commonButton;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;

@SuppressWarnings("serial")
public class SalaryMakePanel extends JPanel implements MouseListener{
	
//	private static final ImageIcon CONFIRM=new ImageIcon("Image/manager/confirm.png");
//	
//	private static final ImageIcon CLEAR=new ImageIcon("Image/manager/clear.png");
	/**
	 * 显示区宽度
	 */
	public static final int SHOWPANEL_WIDTH = 980;
	/**
	 * 显示区高度
	 */
	public static final int SHOWPANEL_HEIGHT = 560;
	
	private static final Font FONT=new Font("黑体", Font.BOLD, 20);

	private JLabel salary_lab=null,courierPercent_lab=null,driverPercent_lab=null,
			courier_lab=null,driver_lab=null,hallStaff_lab=null,centerStaff_lab=null,
			keeper_lab=null,prompt_lab=null;
	
	private JTextField courier_jtf=null,driver_jtf=null,hallStaff_jtf=null,
			centerStaff_jtf=null,keeper_jtf=null,courierPercent_jtf=null,
			driverPercent_jtf=null;
	
	private Cursor cursor=null;
	
	private commonButton confirm_lab=null,clear_lab=null;
	
	private OperationManagementBLService controller=null;
	
	private HashMap<String ,Double> map=null;
	
	private DisconnectInformer disconnectInformer=null;
	
	public SalaryMakePanel(OperationManagementBLService controller,DisconnectInformer disconnectInformer) {
		try {
		this.setSize(SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		this.setLayout(null);
		this.setOpaque(false);
		
		this.controller=controller;
		this.disconnectInformer=disconnectInformer;
		//初始化组件
			this.initComponent();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化组件
	 * @throws RemoteException 
	 */
	private void initComponent() throws RemoteException {
		this.cursor=new Cursor(Cursor.HAND_CURSOR);
		this.map=this.controller.salaryShow();
		
		this.salary_lab=new JLabel("月薪：");
		this.salary_lab.setFont(new Font("楷体", Font.BOLD, 24));
		this.salary_lab.setBounds(15,15,80,25);
		this.add(this.salary_lab);
		
		this.prompt_lab=new JLabel();
		this.prompt_lab.setText("(单位均为元)");
		this.prompt_lab.setFont(new Font("楷体", Font.BOLD, 16));
		this.prompt_lab.setForeground(Color.RED);
		this.prompt_lab.setBounds(90,20,150,20);
		this.add(this.prompt_lab);
		
		this.hallStaff_lab=new JLabel("营业厅业务员:");
		this.hallStaff_lab.setFont(FONT);
		this.hallStaff_lab.setBounds(200,60,200,30);
		this.add(this.hallStaff_lab);
		
		this.hallStaff_jtf=new JTextField();
		this.hallStaff_jtf.setText(this.map.get("营业厅业务员")+"");
		this.hallStaff_jtf.setBounds(200,100,100,25);
		this.add(this.hallStaff_jtf);
		
		this.centerStaff_lab=new JLabel("中转中心业务员:");
		this.centerStaff_lab.setFont(FONT);
		this.centerStaff_lab.setBounds(200,145,200,30);
		this.add(this.centerStaff_lab);
		
		this.centerStaff_jtf=new JTextField();
		this.centerStaff_jtf.setText(this.map.get("中转中心业务员")+"");
		this.centerStaff_jtf.setBounds(200,185,100,25);
		this.add(this.centerStaff_jtf);
		
		this.keeper_lab=new JLabel("中转中心库存管理员:");
		this.keeper_lab.setFont(FONT);
		this.keeper_lab.setBounds(200,230,200,30);
		this.add(this.keeper_lab);
		
		this.keeper_jtf=new JTextField();
		this.keeper_jtf.setText(this.map.get("中转中心库存管理员")+"");
		this.keeper_jtf.setBounds(200,270,100,25);
		this.add(this.keeper_jtf);
		
		this.courier_lab=new JLabel("快递员:");
		this.courier_lab.setFont(FONT);
		this.courier_lab.setBounds(200,315,200,30);
		this.add(this.courier_lab);
		
		this.courier_jtf=new JTextField();
		this.courier_jtf.setText(this.map.get("快递员")+"");
		this.courier_jtf.setBounds(200,355,100,25);
		this.add(this.courier_jtf);
		
		this.driver_lab=new JLabel("司机：");
		this.driver_lab.setFont(FONT);
		this.driver_lab.setBounds(200,400,200,30);
		this.add(this.driver_lab);
		
		this.driver_jtf=new JTextField();
		this.driver_jtf.setText(this.map.get("司机")+"");
		this.driver_jtf.setBounds(200,440,100,25);
		this.add(this.driver_jtf);
		
		
		this.courierPercent_lab=new JLabel();
		this.courierPercent_lab.setText("快递员单次提成：");
		this.courierPercent_lab.setFont(FONT);
		this.courierPercent_lab.setBounds(550,60,200,30);
		this.add(this.courierPercent_lab);
		
		this.courierPercent_jtf=new JTextField();
		this.courierPercent_jtf.setText(this.map.get("快递员提成")+"");
		this.courierPercent_jtf.setBounds(550,100,100,25);
		this.add(this.courierPercent_jtf);
		
		this.driverPercent_lab=new JLabel();
		this.driverPercent_lab.setText("司机单次提成：");
		this.driverPercent_lab.setFont(FONT);
		this.driverPercent_lab.setBounds(550,145,200,30);
		this.add(this.driverPercent_lab);
		
		this.driverPercent_jtf=new JTextField();
		this.driverPercent_jtf.setText(this.map.get("司机提成")+"");
		this.driverPercent_jtf.setBounds(550,185,100,25);
		this.add(this.driverPercent_jtf);
		
		this.confirm_lab=new commonButton("确认");
		this.confirm_lab.addMouseListener(this);
		this.confirm_lab.setBounds(320,510,100,40);
		this.confirm_lab.setCursor(this.cursor);
		this.add(this.confirm_lab);
		
		this.clear_lab=new commonButton("清空");
		this.clear_lab.addMouseListener(this);
		this.clear_lab.setBounds(500,510,100,40);
		this.clear_lab.setCursor(this.cursor);
		this.add(this.clear_lab);
		
	}
	/**
	 * 根据价格输入判断是否合法
	 * @param line
	 * @return
	 */
	private boolean isLegalPrice(String line) {
		int count=0;
	
		if(line.equals("")||line.length()>10){
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
	 * 简化字符串
	 * @param string
	 * @return
	 */
	private String simplifyString(String string){
		boolean flag=false;
		int index=0;
		int padding=0;
		int length=string.length();
		for(int i=0;i<length;i++){
			if(string.charAt(i)=='.'){
				flag=true;
				index=i;
			}
		}
		
		if(flag){
			int i;
			for(i=length-1;i>index&&string.charAt(i)=='0';i--);
			padding=length-1-i;
			if(i==index){
				string=string.substring(0,string.length()-padding-1);
			}
			else{
				string=string.substring(0,string.length()-padding);
			}
		}
		return string;
	}
	/**
	 * 确认操作
	 * @throws RemoteException 
	 */
	private void confirm() throws RemoteException {
		String courierPercent=this.courierPercent_jtf.getText();
		String driverPercent=this.driverPercent_jtf.getText();
		String courier=this.courier_jtf.getText();
		String hallStaff=this.hallStaff_jtf.getText();
		String centerStaff=this.centerStaff_jtf.getText();
		String keeper=this.keeper_jtf.getText();
		String driver=this.driver_jtf.getText();
		//如果输入非法，则返回
		if ((!this.isLegalPrice(courierPercent)) || (!this.isLegalPrice(driverPercent)) || (!this.isLegalPrice(courier))
				|| (!this.isLegalPrice(driver)) || (!this.isLegalPrice(hallStaff)) || (!this.isLegalPrice(centerStaff))
				|| (!this.isLegalPrice(keeper))) {
			JOptionPane.showMessageDialog(this, "输入非法！");
			return;
		}
		//去零操作 
		String courierPercentNew=this.simplifyString(courierPercent);
		String driverPercentNew=this.simplifyString(driverPercent);
		String courierNew=this.simplifyString(courier);
		String hallStaffNew=this.simplifyString(hallStaff);
		String centerStaffNew=this.simplifyString(centerStaff);
		String keeperNew=this.simplifyString(keeper);
		String driverNew=this.simplifyString(driver);
//		//如果未做修改，则返回
		if(courierPercentNew.equals(this.simplifyString(this.map.get("快递员提成")+""))&&
				driverPercentNew.equals(this.simplifyString(this.map.get("司机提成")+""))&&
				courierNew.equals(this.simplifyString(this.map.get("快递员")+""))&&
				hallStaffNew.equals(this.simplifyString(this.map.get("营业厅业务员")+""))&&
				centerStaffNew.equals(this.simplifyString(this.map.get("中转中心业务员")+""))&&
				keeperNew.equals(this.simplifyString(this.map.get("中转中心库存管理员")+""))&&
				driverNew.equals(this.simplifyString(this.map.get("司机")+""))){
			JOptionPane.showMessageDialog(this, "请做相应修改！");
     		return;
		}
		//新数据覆盖旧数据
		this.map.put("快递员提成", Double.parseDouble(courierPercent));
		this.map.put("司机提成", Double.parseDouble(driverPercent));
		this.map.put("快递员", Double.parseDouble(courier));
		this.map.put("司机", Double.parseDouble(driver));
		this.map.put("营业厅业务员", Double.parseDouble(hallStaff));
		this.map.put("中转中心业务员", Double.parseDouble(centerStaff));
		this.map.put("中转中心库存管理员", Double.parseDouble(keeper));
		
		this.controller.makeSalaryStrategy(this.map);
		
		JOptionPane.showMessageDialog(this, "修改成功！");
	}
	/**
	 * 清空操作
	 */
	private void clear() {
		this.courierPercent_jtf.setText("");
		this.driverPercent_jtf.setText("");
		this.centerStaff_jtf.setText("");
		this.hallStaff_jtf.setText("");
		this.courier_jtf.setText("");
		this.driver_jtf.setText("");
		this.keeper_jtf.setText("");
		this.hallStaff_jtf.requestFocus();
		
	}
	
	public void paintComponent(Graphics g){
//		super.paintComponent(g);
//		g.draw3DRect(170, 55, 260, 424, false);
	}
	
	public void mouseClicked(MouseEvent e) {
		try{
		//清空
		if(e.getSource()==this.clear_lab){
			this.clear();
		}
		//确认
		else if(e.getSource()==this.confirm_lab){
			this.confirm();
		}
		}catch(RemoteException e1){
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
