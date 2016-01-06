package edu.nju.logistics.ui.accountant.receipt;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.nju.logistics.ui.accountant.main.MyLB;
import edu.nju.logistics.ui.accountant.main.MyTF;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.accountant.main.UIController;
import edu.nju.logistics.ui.commonButton.BackButton;
import edu.nju.logistics.ui.commonButton.ExportButton;
import edu.nju.logistics.ui.util.ExportExcelUtil;
import edu.nju.logistics.vo.courierIncomeReceipt.CourierIncomeReceiptVO;

public class ReceiptOBPanel  extends JPanel{
	    //表格的大小
		private static int showH=300;
		private static int showW=600;
		//表格的位置
		private static int tableX=100;
		private static int tableY=100;
		//此面板大小
		private static int panelH=505;
		private static int panelW=952;
		
		private static int LBH = 35;  
		private static int LBW = 80;

	
		private      CourierIncomeReceiptVO receiptVO;
		private     JTable  table;
		private     ReceiptOBTableModel MytableModel;
		private     JScrollPane Spane ;
		private     Font font;
		private     ExportButton exportBT;
		private     BackButton backBT;
		private     MyLB receiptIDLB;  
		private      MyTF IDTF;
		private     MyLB dateLB;   
		private     MyTF dateTF;
		private     MyLB  totalFeeLB;
		private     MyTF  totalFeeTF;
		
            
            public ReceiptOBPanel(CourierIncomeReceiptVO receiptVO){
                 	  this.setLayout(null);
                 	  this. font = new Font("微软雅黑", Font.PLAIN, 18);	
                 	  this.receiptVO = receiptVO;
                 	  this.MytableModel = new ReceiptOBTableModel(receiptVO);
                 	  this.table = new JTable(MytableModel);
                 	  this.Spane = new JScrollPane(table);
                 	  MyTableHandler.initTable(table);
                  	  MyTableHandler.initSpane(Spane,table);
                      Spane.setBounds(tableX,tableY,showW,showH);
                      this.add(Spane);
                      initComponent();
            }
            
            public void initComponent(){
            	backBT = new BackButton();          	
    	        backBT.setFont(font);
                backBT.setBounds(panelW/2-LBW-20,panelH-LBH-10,LBW,LBH);
                backBT.addMouseListener(new MouseAdapter (){
          		   
     				public void mouseClicked(MouseEvent e){
    					   System.out.println("should change now");
    					   UIController.changePanel("receiptMainPanel");
    				}
        	   });
               this.add(backBT);
               
               
              exportBT = new ExportButton();          	
              exportBT.setFont(font);
              exportBT.setBounds(backBT.getX()+LBW+40,backBT.getY(),LBW,LBH);
              exportBT.addMouseListener(new MouseAdapter (){
        		   
   				public void mouseClicked(MouseEvent e){
   					      String path  =   ExportExcelUtil.getPath();
   					      ExportExcelUtil.exportExcel(table, path);
   					   
   				    }
       	        
              });
              
                this.add(exportBT);
               
               
               
               
               
             receiptIDLB = new MyLB("收款单号: ");
             IDTF=  new MyTF(this.receiptVO.getReceiptID());
             
       		 dateLB = new MyLB("日期: ");
       		 dateTF =  new MyTF(this.receiptVO.getTime());
       		 
       		 totalFeeLB = new MyLB("收款额: ");
       		 totalFeeTF =  new MyTF(this.receiptVO.getTotalFee()+"");
       		 
       		 
       		 receiptIDLB.setBounds(20,50,LBW+10,LBH);
       		 IDTF.setBounds(receiptIDLB.getX()+LBW,receiptIDLB.getY(),LBW,LBH);
       		 dateLB.setBounds(tableX,tableY+showH+10,LBW,LBH);
       		 dateTF.setBounds(dateLB.getX()+LBW,dateLB.getY(),LBW,LBH);
       		 totalFeeLB.setBounds(dateLB.getX()+LBW+100,dateLB.getY(),LBW,LBH);
       		 totalFeeTF.setBounds(totalFeeLB.getX()+LBW,
       		 totalFeeLB.getY(),LBW,LBH);
       		 
       		 this.add(receiptIDLB);
       		 this.add(IDTF);
       		 this.add(dateLB); 
       		 this.add(dateTF);
       		 this.add(totalFeeLB); 
       		 this.add(totalFeeTF);
            }
}
