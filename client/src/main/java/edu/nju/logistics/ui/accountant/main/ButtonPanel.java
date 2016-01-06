package edu.nju.logistics.ui.accountant.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import edu.nju.logistics.ui.checkorder.MessagePanel;
import edu.nju.logistics.ui.functionButton.MyButton;
import edu.nju.logistics.vo.UserVO;

public class ButtonPanel extends JPanel {
    
	      private static final long serialVersionUID = 1L;
		  private static int panelW=210;
          private static int panelH=500;
          private static int LBW=170;
          private static int LBH=55;
          private static int VGap=20;
          private static int LBX=15;
          private static int LBY=20;
          private MyButton temp=null;
          MessagePanel messagePanel = new MessagePanel("","warning");
          MyButton bankAccountBT,paymentBT,balanceBT,receiptBT,operationStatusBT,accountInitBT,logBT;
          
          public ButtonPanel(UserVO uservo){
        	  this.setLayout(null);
        	  bankAccountBT = new MyButton("银行账户");
        	  paymentBT = new  MyButton("创建付款单");
        	  balanceBT= new MyButton("成本收益表");
        	  receiptBT = new MyButton("查看收款单");
        	  accountInitBT = new  MyButton("期初建账");
        	  operationStatusBT = new MyButton("经营情况表");
        	  logBT = new MyButton("查询日志");
        	  
        	  bankAccountBT.setBounds(LBX,LBY, LBW, LBH);
        	  messagePanel.setBounds(0,10,200,40);
        	  paymentBT.setBounds(LBX,bankAccountBT.getY()+LBH+VGap,LBW,LBH);
        	  receiptBT.setBounds(LBX,paymentBT.getY()+LBH+VGap,LBW,LBH);
        	  balanceBT .setBounds(LBX,receiptBT.getY()+LBH+VGap,LBW,LBH);
        	  operationStatusBT.setBounds(LBX,balanceBT.getY()+LBH+VGap,LBW,LBH);
        	  accountInitBT.setBounds(LBX,operationStatusBT.getY()+LBH+VGap,LBW,LBH);
        	  logBT.setBounds(LBX,accountInitBT.getY()+LBH+VGap,LBW,LBH);
        	  
        	  bankAccountBT.addMouseListener(new MouseAdapter(){
          		public void mouseClicked(MouseEvent e){
          			handlebutton(bankAccountBT);
          			if(uservo.getRole().endsWith("财务人员(高)")){
          			    UIController.changePanel("bankAccountPanel");
          			}else{
          				messagePanel.setMessage("对不起，您没有此权限");
          				messagePanel.showMessage();
          			}
          		}
           	});
        	  
        	 paymentBT.addMouseListener(new MouseAdapter(){
            		public void mouseClicked(MouseEvent e){
            			handlebutton(paymentBT);
            			    UIController.changePanel("paymentMainPanel");
            		}
             	});
        	  
        	  receiptBT.addMouseListener(new MouseAdapter(){
            		public void mouseClicked(MouseEvent e){
            			handlebutton(receiptBT);
            			    UIController.changePanel("receiptMainPanel");
            		}
             	});
        	  
        	  balanceBT.addMouseListener(new MouseAdapter(){
          		  public void mouseClicked(MouseEvent e){
          			handlebutton(balanceBT);
    			      UIController.changePanel("balanceChartMainPanel");
    		      }
     	     }); 
        	  
        	  operationStatusBT.addMouseListener(new MouseAdapter(){
          		  public void mouseClicked(MouseEvent e){
          			handlebutton(operationStatusBT);
    			      UIController.changePanel("operationStatusMainPanel");
    		      }
     	     }); 
        	  
        	  accountInitBT.addMouseListener(new MouseAdapter(){
          		  public void mouseClicked(MouseEvent e){
          			handlebutton(accountInitBT);
    			      UIController.changePanel("accountInitMainPanel");
    		      }
     	     }); 
        	  
        	  logBT.addMouseListener(new MouseAdapter(){
          		  public void mouseClicked(MouseEvent e){
          			handlebutton(logBT);
    			      UIController.changePanel("logMainPanel");
    		      }
     	     }); 
        	
        	  this.add(bankAccountBT);    this.add(messagePanel);
        	  this.add(paymentBT);
        	  this.add(receiptBT);
        	  this.add(balanceBT);
        	  this.add(operationStatusBT);
        	  this.add(accountInitBT);
        	  this.add(logBT);
        	  
        	  
        	
        	  this.setSize(panelW,panelH);
        	  this.setOpaque(false);
        	  this.setVisible(true);
          }
          /*
           * 按钮处理
           */
          private void handlebutton(MyButton e){
        	  if(this.temp!=null){
        		  temp.restore();
        	  }
        	  this.temp=e;
        	  this.temp.clicked();
          }
}
