package edu.nju.logistics.ui.accountant.accountinit;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.nju.logistics.ui.accountant.main.FinaMainFrame;
import edu.nju.logistics.ui.accountant.main.MyLB;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.accountant.main.UIController;
import edu.nju.logistics.ui.commonButton.BackButton;
import edu.nju.logistics.vo.financial.AccountVO;


public class AccountInitOBPanel  extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private static int LBW=80;
	private static int LBH=35;
	private static int LBX=50;
	private static int LBY=20;
	
	private static int tableX=LBX+LBW;
	private static int tableY=LBY+LBH+10;
	private static int VGap=50;
	private static int tableW=600;
	private static int tableH=280;

	
    //private AccountInitController controller;
    private AccountVO accountVO;
    private JTable instiTable,userTable,driverTable,carTable,
                                   storageTable,bankaccountTable;
    private SuperTableModel   instiTableModel,userTableModel,driverTableModel,
                                                           carTableModel,storageTableModel,bankaccountTableModel;
    private JScrollPane instiSpane,userSpane,driverSpane,carSpane,storageSpane,bankaccountSpane;
    
    MyLB instiLB,userLB,driverLB,carLB,storageLB,bankaccountLB ;
    BackButton backBT;
    
    public AccountInitOBPanel  (AccountVO accountVO){
    	  super();
    	  this.setLayout(null);
    	  this.setOpaque(false);
 	      this.accountVO =accountVO;
 	      this.initTables();
 	      this.initComponent();
 	      this.setPreferredSize(new Dimension(FinaMainFrame.showW,this.bankaccountSpane.getY()+tableH));
  }
    
    public void  initTables(){
   	      this.instiTableModel  = new InstiTableModel(this.accountVO.getInstiSMList());
   	      this.userTableModel =  new UserTableModel(this.accountVO.getUserSMList());
   	      this.driverTableModel = new DriverTableModel(this.accountVO .getDriverSMList());
   	      this.carTableModel  = new CarTableModel(this.accountVO.getCarSMList());
   	      this.storageTableModel = new StorageTableModel(this.accountVO.getStorageSMList());
   	      this.bankaccountTableModel = new BankAccountTableModel(this.accountVO.getBankaccountVOList());
   	      
   	      this.instiTable  =  new JTable(this.instiTableModel);
   	      this.userTable = new JTable(this.userTableModel);
   	      this.driverTable = new JTable(this.driverTableModel);
   	      this.carTable =  new JTable(this.carTableModel);
   	      this.storageTable = new JTable(this.storageTableModel);
   	      this.bankaccountTable =  new JTable(this.bankaccountTableModel);
   	      
   	      this.instiSpane  = new JScrollPane(this.instiTable);
   	      this.userSpane  = new JScrollPane(this.userTable);
   	      this.driverSpane = new JScrollPane(this.driverTable);
   	      this.carSpane = new JScrollPane(this.carTable);
   	      this.storageSpane = new JScrollPane(this.storageTable);
   	      this.bankaccountSpane = new JScrollPane(this.bankaccountTable);
   	      
   	      MyTableHandler.initTable(instiTable);
    	  MyTableHandler.initTable(userTable);
    	  MyTableHandler.initTable(driverTable);
    	  MyTableHandler.initTable(carTable);
    	  MyTableHandler.initTable(storageTable);
    	  MyTableHandler.initTable(bankaccountTable);
    	  
    	  MyTableHandler.initSpane(instiSpane, instiTable);
    	  MyTableHandler.initSpane(userSpane, userTable);
    	  MyTableHandler.initSpane(driverSpane, driverTable);
    	  MyTableHandler.initSpane(carSpane, carTable);
    	  MyTableHandler.initSpane(storageSpane,storageTable);
    	  MyTableHandler.initSpane(bankaccountSpane, bankaccountTable);
    	  
    	  
   	      
    }

    public void initComponent(){
    	instiLB = new MyLB("机构信息:");
    	userLB = new MyLB("员工信息:");	
    	driverLB = new MyLB("司机信息:");
    	carLB = new MyLB("车辆信息:");
    	storageLB = new MyLB("仓库信息:");
    	bankaccountLB = new MyLB("银行账户:");
    	
    	backBT = new BackButton();
    	
    	
    	instiLB.setBounds(LBX,LBY,LBW,LBH);
    	this.instiSpane.setBounds(tableX,tableY,tableW,tableH);

    	userLB.setBounds(LBX,instiSpane.getY()+tableH+VGap,LBW,LBH);
    	this.userSpane.setBounds(tableX,userLB.getY()+LBH,tableW,tableH);
    	
    	driverLB.setBounds(LBX,userSpane.getY()+tableH+VGap,LBW,LBH);
    	 this.driverSpane.setBounds(tableX,driverLB.getY()+LBH,tableW,tableH);
    	 
    	 carLB.setBounds(LBX,driverSpane.getY()+tableH+VGap,LBW,LBH);
    	 this.carSpane.setBounds(tableX,carLB.getY()+LBH,tableW,tableH);
    	 
    	 storageLB.setBounds(LBX,carSpane.getY()+tableH+VGap,LBW,LBH);
    	 this.storageSpane.setBounds(tableX,storageLB.getY()+LBH,tableW,tableH);
    	 
    	 bankaccountLB.setBounds(LBX,storageSpane.getY()+tableH+VGap,LBW,LBH);
    	 this.bankaccountSpane.setBounds(tableX,bankaccountLB.getY()+LBH,tableW,tableH);
  	    
    	 backBT .setBounds(tableW,0,LBW,LBH);
    	 backBT.addActionListener(new ActionListener (){
   		   
				public void actionPerformed(ActionEvent e){
					   System.out.println("should change now");
					   UIController.changePanel("accountInitMainPanel");
				}
				
  	   });
    	 
     	
     	 
    	 
  	    this.add(instiLB);  this.add(userLB);  
  	    this.add(driverLB); this.add(carLB);
  	    this.add(storageLB); this.add(bankaccountLB);
  	    this.add(instiSpane);  this.add(userSpane);
  	    this.add(driverSpane);  this.add(carSpane);
  	    this.add(storageSpane);this.add(bankaccountSpane);
  	    
  	    this.add(backBT);  
    }
}