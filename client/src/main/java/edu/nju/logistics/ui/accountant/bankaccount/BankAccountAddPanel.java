package edu.nju.logistics.ui.accountant.bankaccount;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.nju.logistics.ui.accountant.main.MoneyDocument;
import edu.nju.logistics.ui.accountant.main.UIController;
import edu.nju.logistics.ui.checkorder.MessagePanel;
import edu.nju.logistics.ui.commonButton.BackButton;
import edu.nju.logistics.ui.commonButton.CancelButton;
import edu.nju.logistics.ui.commonButton.ConfirmButton;
import edu.nju.logistics.ui.functionButton.MyTextField;

public class BankAccountAddPanel  extends JPanel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int AddPanelH=560;
	private static int AddPanelW=980;
	private static int LBH = 35;
	private static int LBW = 80;
	private static int TFH = 30;
	private static int TFW = 150;
	private static int initX=350;
	private static int  initY=150;
	private static int  VGap =60;
    private BATableModel model;
    	JLabel  nameLB;
    	JLabel  balanceLB;
    	MyTextField nameTF;
    	MyTextField balanceTF;
    	ConfirmButton confirmBT;
    	CancelButton cancelBT;
    	Font font;
    	Font font1;
    	MessagePanel messagePanel ;
    	public BankAccountAddPanel(BATableModel model){
    		this.model = model;
    		this.setLayout(null);
    		this.setOpaque(false);
    		this.setPreferredSize(new Dimension(AddPanelW,AddPanelH));
    		initComponent();
    	}
    	
    	
    	public void initComponent(){
    		this.messagePanel = new MessagePanel("", "warning");
    		this.font = new Font("微软雅黑", Font.PLAIN, 18);
    		this.font1 = new Font("微软雅黑", Font.PLAIN, 16);
    		this.nameLB = new JLabel("账户:");   nameLB.setFont(font);
    		this.balanceLB = new JLabel("余额:");    balanceLB.setFont(font);
    		this.nameTF = new MyTextField();               nameTF.setFont(font);
    		this.balanceTF = new MyTextField();           balanceTF.setFont(font);
    		this.confirmBT = new ConfirmButton();  confirmBT.setFont(font1);
    		this.cancelBT = new CancelButton();     cancelBT.setFont(font1);
    		
    		
    		nameLB.setBounds(initX, initY, LBW, LBH);
    		balanceLB.setBounds(initX, nameLB.getY()+LBH+VGap, LBW, LBH);
    		nameTF.setBounds(nameLB.getX()+LBW,nameLB.getY(),TFW,TFH);
    		balanceTF.setBounds(balanceLB.getX()+LBW,balanceLB.getY(),TFW,TFH);
    		//余额只可能输入数字
    	    balanceTF.NumsOnly();
    	    messagePanel.setBounds(nameTF.getX()+TFW+10,nameTF.getY(),400,40);
    	    
    	    
    		confirmBT.setBounds(AddPanelW/2-LBW-20,balanceLB.getY()+LBH+VGap,LBW,LBH);
    		cancelBT.setBounds(AddPanelW/2+20,confirmBT.getY(),LBW,LBH);
    		
    		this.add(nameLB);  this.add(balanceLB);
    		this.add(nameTF);  this.add(balanceTF);
    		this.add(confirmBT);  this.add(cancelBT);
    		this.add(messagePanel);
    		
    		confirmBT.addMouseListener(new MouseAdapter(){
				
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					//输入信息不完整
					if(nameTF.getText().length()<1||balanceTF.getText().length()<1){
						 if(nameTF.getText().length()<1&&balanceTF.getText().length()>=1){
							    messagePanel.setType("warning");
						        messagePanel.setMessage("请输入账户名称");
						        messagePanel.showMessage();
						 }else if(nameTF.getText().length()>=1&&balanceTF.getText().length()<1){
							  messagePanel.setType("warning");
						        messagePanel.setMessage("请输入账户余额");
						        messagePanel.showMessage();
						 }else{
							  messagePanel.setType("warning");
							    messagePanel.setMessage("请填写账户信息");
						        messagePanel.showMessage();
						 }
						 	 
					}else{
						    //余额为负
						    if(Double.parseDouble(balanceTF.getText())<0&& isValidName(nameTF.getText())){
						    	  messagePanel.setType("warning");
						    	messagePanel.setMessage("请确认账户余额是否为负");
						        messagePanel.showMessage();
						    //账户名称格式错误
						    }else  if(Double.parseDouble(balanceTF.getText())>=0
						    		&&! isValidName(nameTF.getText())){						    	
						    	  messagePanel.setType("warning");
						    	messagePanel.setMessage("请确认账户名称格式是否正确");
						        messagePanel.showMessage();
						    //均错误
						    }else if(Double.parseDouble(balanceTF.getText())<0
						    		&&! isValidName(nameTF.getText())){
						    	  messagePanel.setType("warning");
						    	messagePanel.setMessage("请确认输入信息是否正确");
						        messagePanel.showMessage();
						        
						    }else if(model.isAlreadyExist(nameTF.getText() )){
						    	  messagePanel.setType("warning");
						    	messagePanel.setMessage("该账户已存在");
						        messagePanel.showMessage();
						    }else{
						        model.addRow(nameTF.getText(),balanceTF.getText());
						        nameTF.setText("");  balanceTF.setText("");
						        messagePanel.setType("success");
						        messagePanel.setMessage("添加成功");
						        messagePanel.showMessage();
						    }
						 
					}
					
				}
	       	});
    	    
    	    cancelBT.addMouseListener(new MouseAdapter(){

	
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				   nameTF.setText("");
				   balanceTF.setText("");
			       UIController.changePanel("bankAccountPanel");
			}
    		
    	
    	});
      }
    
  	  //判断输入的账户名称格式是否正确
    	/**
    	 * 银行账户名称格式：
    	 * 长度至少为4个字符
    	 *以英文开头
    	 * @param name
    	 * @return
    	 */
		public boolean isValidName(String name){
			if(name.length()<4){
				return false;
			}
			
			if(!isABC(name.charAt(0))){
				return false;
			}
			return true;
		}
		
		private boolean isABC(char c){
			   if(('a'<=c&&c<='z')||('A'<=c&&c<='Z')){
				     return true;
			   }
			   return false;
		}
   
}
