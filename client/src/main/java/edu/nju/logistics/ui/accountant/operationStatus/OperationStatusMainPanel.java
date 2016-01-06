package edu.nju.logistics.ui.accountant.operationStatus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.nju.logistics.bl.impl.financialmanagement.OperationStatusController;
import edu.nju.logistics.ui.accountant.main.DateFilterPanel;
import edu.nju.logistics.ui.accountant.main.MyBoxModel;
import edu.nju.logistics.ui.accountant.main.MyLB;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.accountant.payment.PaymentMainTableModel;
import edu.nju.logistics.ui.accountant.receipt.ReceiptMainTableModel;
import edu.nju.logistics.ui.commonButton.ExportButton;
import edu.nju.logistics.ui.commonButton.SearchButton;
import edu.nju.logistics.ui.util.ExportExcelUtil;

public class OperationStatusMainPanel  extends JPanel{
	    /**
	      * 
	      */
	private static final long serialVersionUID = 1L;
		//表格的大小
		private static int showH=200;
		private static int showW=980;
		private static int VGap=50;
		private static int LBH = 30;  
		private static int LBW = 80;
			
		private OperationStatusController controller;
		private String startDate ,endDate;
		
		JTable paymentTable ,receiptTable ;
	    ReceiptMainTableModel receiptModel;
	    PaymentMainTableModel paymentModel;
	    JScrollPane  paymentSpane,receiptSpane;
	    
	    MyLB startDateLB;
	    MyLB endDateLB;
	    DateFilterPanel startFilterPanel,endFilterPanel;
	
	    
	    SearchButton searchBT  ;
	    ExportButton  exportBT;
	    
	    public OperationStatusMainPanel(OperationStatusController controller){
	    	this.controller = controller;
	   	     this.setLayout(null);
	    	 this.setOpaque(false);
	    	 startDate = "2014/01/01";
	    	 endDate = "2015/12/20";
	    	 receiptModel = new ReceiptMainTableModel
	    			 (controller.showReceiptList(startDate, endDate));
	    	 paymentModel = new PaymentMainTableModel
	    			 (controller.showPaymentList(startDate, endDate));
	    	 paymentTable = new JTable(paymentModel);
	    	 receiptTable = new JTable(receiptModel);
	    	 paymentSpane = new JScrollPane(paymentTable);
	    	 receiptSpane = new JScrollPane(receiptTable);
	    	 MyTableHandler.initTable(paymentTable);
	       	 MyTableHandler.initSpane(paymentSpane, paymentTable);
	       	 MyTableHandler.initTable(receiptTable);
	       	 MyTableHandler.initSpane(receiptSpane, receiptTable);
	         paymentSpane.setBounds(0,40,showW,showH);
	         receiptSpane.setBounds(0,paymentSpane.getY()+showH+VGap,showW,showH);
	         this.add(paymentSpane);
	         this.add(receiptSpane);          
	         initComponent();
	    }
	    
	    public void initComponent(){
	    	startDateLB = new MyLB("开始日期:","light");
	    	startDateLB.setBounds(0,0,LBW,LBH);
	    	this.add(startDateLB);
	    	
	    	endDateLB = new MyLB("结束日期:","light");
	    	endDateLB.setBounds(showW/2-100,0,LBW,LBH);
	    	this.add(endDateLB);
	    	
	    	startFilterPanel = new DateFilterPanel();
	    	endFilterPanel = new DateFilterPanel();
	    	startFilterPanel.setBounds(startDateLB.getX()+LBW+10,startDateLB.getY(),
	    			startFilterPanel.getWidth(),startFilterPanel.getHeight());
	    	endFilterPanel.setBounds(endDateLB.getX()+LBW+10,endDateLB.getY(),
	    			endFilterPanel.getWidth(),endFilterPanel.getHeight());
	    	
	    	this.add(startFilterPanel);
	    	this.add(endFilterPanel);
	    	
	    	searchBT = new SearchButton();
	    	searchBT.setBounds(endFilterPanel.getX()+endFilterPanel.getWidth()+10,
	    			startDateLB.getY(),LBW,LBH);
	    	
	    	searchBT.addActionListener(new ActionListener(){
        	    public  void actionPerformed(ActionEvent e){
        	    	String date1=startFilterPanel.getDate();
        	    	String date2=endFilterPanel.getDate();
	    	       paymentModel.refresh(controller.showPaymentList(date1, date2));
	    	       receiptModel.refresh(controller.showReceiptList(date1, date2));
	            }
             });
	    	
	    	this.add(searchBT);
	    	
	    	exportBT = new ExportButton();
	    	exportBT.setBounds(searchBT.getX()+LBW+10,
	    			searchBT.getY(),LBW,LBH);
	    	
	    	exportBT.addActionListener(new ActionListener(){
        	    public  void actionPerformed(ActionEvent e){
        	    	   String path  =   ExportExcelUtil.getPath();
					   ExportExcelUtil.exportBoth(paymentTable, receiptTable, path);
	            }
             });
	    	
	    	this.add(exportBT);
	    }
}
