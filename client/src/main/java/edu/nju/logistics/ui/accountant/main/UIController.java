package edu.nju.logistics.ui.accountant.main;

import java.rmi.RemoteException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.nju.logistics.bl.impl.financialmanagement.PaymentBL;
import edu.nju.logistics.bl.impl.financialmanagement.PaymentController;
import edu.nju.logistics.bl.impl.financialmanagement.ReceiptBL;
import edu.nju.logistics.bl.impl.financialmanagement.ReceiptController;
import edu.nju.logistics.bl.impl.operationmanagement.OperationManagementController;
import edu.nju.logistics.bl.service.finacialmanagement.BankAccountGetter;
import edu.nju.logistics.bl.impl.financialmanagement.AccountInitBL;
import edu.nju.logistics.bl.impl.financialmanagement.AccountInitController;
import edu.nju.logistics.bl.impl.financialmanagement.BalanceChartBL;
import edu.nju.logistics.bl.impl.financialmanagement.BalanceChartController;
import edu.nju.logistics.bl.impl.financialmanagement.BankAccountBL;
import edu.nju.logistics.bl.impl.financialmanagement.BankAccountController;
import edu.nju.logistics.bl.impl.financialmanagement.BankAccountGetterImpl;
import edu.nju.logistics.bl.impl.financialmanagement.LogController;
import edu.nju.logistics.bl.impl.financialmanagement.OperationStatusBL;
import edu.nju.logistics.bl.impl.financialmanagement.OperationStatusController;
import edu.nju.logistics.ui.accountant.BalanceChart.BalanceChartMainPanel;
import edu.nju.logistics.ui.accountant.accountinit.AccountMainPanel;
import edu.nju.logistics.ui.accountant.accountinit.AccountCreatePanel;
import edu.nju.logistics.ui.accountant.accountinit.AccountInitOBPanel;
import edu.nju.logistics.ui.accountant.bankaccount.BankAccountAddPanel;
import edu.nju.logistics.ui.accountant.bankaccount.BankAccountPanel;
import edu.nju.logistics.ui.accountant.log.LogPanel;
import edu.nju.logistics.ui.accountant.operationStatus.OperationStatusMainPanel;
import edu.nju.logistics.ui.accountant.payment.PaymentCreatePanel;
import edu.nju.logistics.ui.accountant.payment.PaymentMainPanel;
import edu.nju.logistics.ui.accountant.payment.PaymentOBPanel;
import edu.nju.logistics.ui.accountant.receipt.ReceiptMainPanel;
import edu.nju.logistics.ui.accountant.receipt.ReceiptOBPanel;
import edu.nju.logistics.vo.UserVO;
import edu.nju.logistics.vo.courierIncomeReceipt.CourierIncomeReceiptVO;
import edu.nju.logistics.vo.financial.PaymentVO;
/**
 * 界面跳转的控制器
 * 有些界面是静态的，所以作为静态变量，在系统开始运行的时候，初始化依次即可。
 * 而有些界面是动态的，需要在跳转的时候新建出来
 * @author 侍硕
 *
 */
public class UIController {
         private static FinaMainFrame frame;
         
         private static   PaymentBL paymentbl ;
         private static   ReceiptBL receiptbl ;
         private static   BankAccountBL bankAccountbl ;
         private static   BankAccountGetter bankAccountGetter;
         private static   BalanceChartBL balanceChartbl;
         private static   OperationStatusBL operationStatusbl;
         private static   AccountInitBL accountInitbl;
         private static  OperationManagementController logbl;
         
         private static PaymentController paymentController;
         private static ReceiptController receiptController;
         private static BankAccountController bankAccountController;
         private static  BalanceChartController balanceChartController;
         private static  OperationStatusController operationStatusController;
         private static  AccountInitController accountInitController;
         private static LogController   logController;
         
         //静态界面
         private static BankAccountPanel bankAccountPanel;
         private static BankAccountAddPanel bankAccountAddPanel;
         private static PaymentMainPanel paymentMainPanel;
         private static ReceiptMainPanel receiptMainPanel;
         private static BalanceChartMainPanel balanceChartMainPanel;
         private static OperationStatusMainPanel operationStatusMainPanel;
         private static AccountMainPanel accountInitMainPanel;
         private static LogPanel logMainPanel;
        
         public static void preparePanel() throws RemoteException{
        	 bankAccountbl= new BankAccountBL();
        	 bankAccountGetter = new BankAccountGetterImpl();
              paymentbl = new  PaymentBL();
        	 receiptbl = new ReceiptBL();
        	 balanceChartbl = new BalanceChartBL();
        	 operationStatusbl = new OperationStatusBL();
        	 accountInitbl = new AccountInitBL();
        	 logbl = new OperationManagementController();
        	 
        	 paymentController = new  PaymentController(paymentbl);
        	 bankAccountController = new BankAccountController(bankAccountbl);
        	 receiptController = new ReceiptController(receiptbl);
        	 balanceChartController =  new BalanceChartController(balanceChartbl);
        	 operationStatusController =  new OperationStatusController(operationStatusbl);
         	 accountInitController = new AccountInitController(accountInitbl);
        	 logController = new LogController(logbl);
        	 
       	     bankAccountPanel = new BankAccountPanel(bankAccountController);
       	     bankAccountAddPanel  =  new BankAccountAddPanel(bankAccountPanel.getTableModel());
        	 paymentMainPanel = new PaymentMainPanel(paymentController);
       	     receiptMainPanel = new ReceiptMainPanel(receiptController);
        	 operationStatusMainPanel =  new OperationStatusMainPanel(operationStatusController);
             accountInitMainPanel = new AccountMainPanel(accountInitController );
             logMainPanel = new LogPanel(logController);

         }
         
         public static void setFrame(FinaMainFrame frame){
        	   UIController.frame = frame;
         }
         
         public static void changePanel(String panelName) {
        	 JPanel showPanel = new JPanel();
        	 showPanel.setOpaque(false);
        	 showPanel.setLayout(null);
        	 if(panelName.equals("paymentMainPanel")){        	
        		 System.out.println("change to paymentMainpanel");
        		   //每次返回该面板前先更新数据
        		    paymentMainPanel.refresh();
        		    showPanel = paymentMainPanel;
        	 }else if(panelName.equals("paymentCreatePanel")){
        		   System.out.println("change to  paymentCreatepanel");
        		 
        		   PaymentCreatePanel paymentCreatePanel;
				try {
					paymentCreatePanel = new PaymentCreatePanel(paymentController,bankAccountGetter);
					showPanel = paymentCreatePanel;
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		   
        		   
        	 }else if (panelName.charAt(7)=='O'&&panelName.charAt(0)=='p'){
        		  System.out.println("change to  paymentOBPanel");
      		      String paymentID = panelName.substring(14);
      		      System.out.println("PaymentID:"+paymentID);
      		      PaymentVO paymentVO = paymentController.showOldChart(paymentID);
      		      PaymentOBPanel paymentOBPanel = new PaymentOBPanel(paymentVO);
      		      showPanel = paymentOBPanel;
        	 }else if(panelName.equals("bankAccountPanel")){
        		 System.out.println("change to  bankAccountPanel");
        		 showPanel = bankAccountPanel;
        	 }else if(panelName.equals("bankAccountAddPanel")){
        		 System.out.println("change to  bankAccountAddPanel");
        		 showPanel = bankAccountAddPanel;
        	 } else if(panelName.equals("receiptMainPanel")){
        		 System.out.println("change to  receiptMainPanel");
        		 showPanel = receiptMainPanel;
        	 }else if(panelName.charAt(7)=='O'&&panelName.charAt(0)=='r'){
        		 System.out.println("change to  receiptOBPanel");
        		 String receiptID = panelName.substring(14);
        		 System.out.println("receiptID: "+receiptID);
        		 CourierIncomeReceiptVO receiptVO = receiptController.getReceiptVO(receiptID);
        		 ReceiptOBPanel obpanel = new ReceiptOBPanel(receiptVO);
        		 showPanel = obpanel;
        	 }else if(panelName.equals("balanceChartMainPanel")){
        		 System.out.println("change to  balanceChartMainPanel");
        		 
        		 balanceChartMainPanel = new BalanceChartMainPanel(balanceChartController);
        		 showPanel = balanceChartMainPanel;
        		
        	 }else if(panelName.equals("operationStatusMainPanel")){
        		 System.out.println("change to operationStatusMainPanel");
        		 showPanel = operationStatusMainPanel;
        		 
        	 }else if(panelName.equals("accountInitMainPanel")){
        		 System.out.println("change to accountInitMainPanel");
        		 accountInitMainPanel.refresh();
        		 showPanel = accountInitMainPanel;
        		 
        	 }else if(panelName.equals("logMainPanel")){
        		 System.out.println("change to logMainPanel");
        		 showPanel = logMainPanel;
        		 
        	 } else if(panelName.equals("accountInitCreatePanel")){
        		 System.out.println("change to accountInitCreatePanel");
        		 AccountCreatePanel createPanel = new AccountCreatePanel(accountInitController);
        		 JScrollPane  Spane = new  JScrollPane(createPanel);
        		 Spane.setOpaque(false);
        		 Spane.getViewport().setOpaque(false);
        		 Spane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        		 Spane.setBounds(0,0,frame.showW,frame.showH);
        		 showPanel.add(Spane);

        		 
        	 }else if(panelName.charAt(11)=='O'&&panelName.charAt(0)=='a'){
        		 System.out.println("change to accountInitOBPanel");
                 String date = panelName.substring(18);
        		 AccountInitOBPanel obPanel = new AccountInitOBPanel(accountInitController.showOldAccount(date));
        		 JScrollPane  spane = new  JScrollPane(obPanel);
        		 spane.setOpaque(false);
        		 spane.getViewport().setOpaque(false);
        		 spane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        		 spane.setBounds(0,0,frame.showW,frame.showH);
        		 showPanel.add(spane);

        	 }
        	 
        	 
        	  showPanel.setBounds(0,0,frame.showW,frame.showH);
              showPanel.setOpaque(false);
              
             frame.getShowPanel().removeAll();
             frame.getShowPanel().add(showPanel);
             frame.validate();
             frame.repaint();
             
         }
         
         public static void  initFinancialFrame(UserVO vo) throws RemoteException  {
        	  FinaMainFrame frame = new FinaMainFrame((vo));
              UIController.setFrame(frame);
              UIController.preparePanel();
           //   UIController.changePanel("bankAccountPanel");
          
         }
         
         public static void main(String args []) throws RemoteException{
        	 FinaMainFrame frame = new FinaMainFrame(new UserVO ("11","11","财务人员"));
             UIController.setFrame(frame);
             UIController.preparePanel();
             UIController.changePanel("bankAccountPanel");
         }
}
