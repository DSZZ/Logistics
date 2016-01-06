package edu.nju.logistics.ui.accountant.receipt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import edu.nju.logistics.bl.impl.financialmanagement.ReceiptController;
import edu.nju.logistics.ui.accountant.main.DateFilterPanel;
import edu.nju.logistics.ui.accountant.main.MyLB;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.accountant.main.UIController;
import edu.nju.logistics.ui.commonButton.ObserveButton;
import edu.nju.logistics.ui.commonButton.SearchButton;
import edu.nju.logistics.ui.functionButton.MyTextField;

public class ReceiptMainPanel  extends JPanel{
	private static final long serialVersionUID = 1L;
    
	//表格的大小
	private static int showH=400;
	private static int showW=980;
	
	private static int LBH = 35;  
	private static int LBW = 80;
	private static int TFW = 150;

    

	ReceiptController controller;
	/**
	 * 显示历史付款单的表格
	 */
    JTable table ;
    ReceiptMainTableModel MainTablemodel;
    JScrollPane  Spane;
    MyLB dateLB , instiIDLB;
    DateFilterPanel datePanel;
    
    JTextField instiIDTF;
    SearchButton searchBT;
    ObserveButton  obBT;

    
    public  ReceiptMainPanel (ReceiptController controller){
    	  this.controller = controller;
    	  
    	  this.setLayout(null);
    	  MainTablemodel = new ReceiptMainTableModel(controller.getInfoList());
    	  table = new JTable(MainTablemodel);
    	  Spane = new JScrollPane(table);
    	  MyTableHandler.initTable(table);
       	  MyTableHandler.initSpane(Spane,table);
          Spane.setBounds(0,LBH+10,showW,showH);
          this.add(Spane);
          
          initComponent();
    }
    
   
    
    public void initComponent(){
    	
    	dateLB = new MyLB("日期:","light");
    	dateLB.setBounds(0,5,LBW,LBH);
    	this.add(dateLB);
    	
    	datePanel = new DateFilterPanel();
    	datePanel.setBounds(dateLB.getX()+LBW,dateLB.getY()+5,
    			datePanel.getWidth(),datePanel.getHeight());
    	this.add(datePanel);
    	
    	instiIDLB = new MyLB("机构编号:","light");
    	instiIDLB.setBounds(datePanel.getX()+datePanel.getWidth()+40, dateLB.getY()
    			,LBW, LBH);
       this.add(instiIDLB);
    	
    	instiIDTF = new JTextField();
    	instiIDTF.setBounds(instiIDLB.getX()+LBW+20,  instiIDLB.getY()+5
    			,TFW, 25);
    	this.add(instiIDTF);
    	
    	 searchBT =  new SearchButton();
 	     searchBT.setBounds(instiIDTF.getX()+TFW+20,instiIDTF.getY(),LBW,25);
 	     searchBT.addMouseListener(new MouseAdapter (){
   		   
				public void mouseClicked(MouseEvent e){
					 String date = datePanel.getDate();
					 String ID = instiIDTF.getText();
					 if(ID.length()<1){
						 //用户未输入机构ID
						 MainTablemodel.refresh(controller.getInfoListByDate(date));
					 }else{
						 MainTablemodel.refresh(controller.getInfoListByBoth(date, ID));
					 }
					 
				}
	      });
 	     this.add(searchBT);
    	 
 	     
 	     
 	     
    	   obBT =  new ObserveButton();
    	   obBT.setBounds(showW/2-LBW-20,showH+50,LBW,LBH);
    	   obBT.addMouseListener(new MouseAdapter (){
     		   
 				public void mouseClicked(MouseEvent e){
					   System.out.println("should change now");
					   int rowIndex= table.getSelectedRow();
					   String ReceiptID=(String)MainTablemodel.getValueAt(rowIndex, 0);
					   System.out.println("ReceiptID: "+ReceiptID);
					   UIController.changePanel("receiptOBPanel"+ReceiptID); 
				}
   	       });
    	   this.add(obBT);
    	   
    	   
    	   
    	   
    	   
    	   
    }
       
    
//    public void refresh(){
//    	this.MainTablemodel.refresh(this.controller.getInfoList());
//    }
}