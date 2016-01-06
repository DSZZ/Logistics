package edu.nju.logistics.ui.accountant.payment;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.nju.logistics.bl.impl.financialmanagement.PaymentController;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.accountant.main.UIController;
import edu.nju.logistics.ui.commonButton.AddButton;
import edu.nju.logistics.ui.commonButton.ObserveButton;

public class PaymentMainPanel  extends JPanel{
	private static final long serialVersionUID = 1L;
    
	//表格的大小
	private static int showH=400;
	private static int showW=980;
	
	private static int LBH = 35;  
	private static int LBW = 80;
	
	private static int TFH = 35;
    

	PaymentController controller;
	/**
	 * 显示历史付款单的表格
	 */
    JTable table ;
    PaymentMainTableModel MainTablemodel;
    JScrollPane  Spane;
    
    AddButton addbt;
    ObserveButton  obbt;
    
    public  PaymentMainPanel (PaymentController controller){
    	  this.controller = controller;
    	  this.setLayout(null);
    	  this.setOpaque(false);
    	  MainTablemodel = new PaymentMainTableModel(controller.getPaymentInfoList());
    	  table = new JTable(MainTablemodel);
    	  Spane = new JScrollPane(table);
    	  MyTableHandler.initTable(table);
       	  MyTableHandler.initSpane(Spane,table);
          Spane.setBounds(0,0,showW,showH);
          this.add(Spane);
          
          initComponent();
    }
    
   
    
    public void initComponent(){
    	   addbt = new AddButton();
    	   obbt =  new ObserveButton();
    	   
    	   addbt.setBounds(showW/2-LBW,this.Spane.getY()+this.Spane.getHeight(),LBW,LBH);
    	   obbt.setBounds(addbt.getX()+LBW+20,addbt.getY(),LBW,LBH);
    	   
    	   addbt.addMouseListener(new MouseAdapter (){
    		   
				public void mouseClicked(MouseEvent e){
					   System.out.println("should change now");
					   UIController.changePanel("paymentCreatePanel");
				        
				}
    	   });
    	   
    	   obbt.addMouseListener(new MouseAdapter (){
    		   
				public void mouseClicked(MouseEvent e){
					   System.out.println("should change now");
					   int rowIndex= table.getSelectedRow();
					   String PaymentID=(String)MainTablemodel.getValueAt(rowIndex, 0);
					   System.out.println("Paymentid: "+PaymentID);
					   UIController.changePanel("paymentOBPanel"+PaymentID); 
				}
   	       });
    	   
    	   this.add(addbt);
    	   this.add(obbt);
    	   
    }
       
    
    public void refresh(){
    	this.MainTablemodel.refresh(this.controller.getPaymentInfoList());
    }
}
