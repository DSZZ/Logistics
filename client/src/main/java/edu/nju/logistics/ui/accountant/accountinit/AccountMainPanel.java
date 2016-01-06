package edu.nju.logistics.ui.accountant.accountinit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JPanel;


import edu.nju.logistics.bl.impl.financialmanagement.AccountInitController;
import edu.nju.logistics.ui.accountant.main.MyLB;
import edu.nju.logistics.ui.accountant.main.UIController;
import edu.nju.logistics.ui.commonButton.AddButton;
import edu.nju.logistics.ui.commonButton.ObserveButton;

public class AccountMainPanel extends JPanel{
	
	     /**
	       * 
	       */
	private static final long serialVersionUID = 1L;
	     private static int LBH = 35;
	     private static int LBW = 80;
	     private static int LBX=200;
	     private static int LBY=200-LBH;
		 private static int BoxH = 35;
	     private static int BoxW= 110;
	     
		 
	     private AccountInitController controller;
	     String []  dateList;
	     JComboBox<String>  dateBox;
	     MyLB dateLB;
         AddButton createBT;
         ObserveButton  obBT;
         
         public AccountMainPanel(AccountInitController Controller){
        	 this.setLayout(null);
        	 this.setOpaque(false);
        	 this.controller = Controller;
        	 this.dateList = new String [this.controller.showDateList().size()];
        	 
        	 for(int i=0;i<this.controller.showDateList().size();i++){
        		   dateList[i] = this.controller.showDateList().get(i);
        	 }
        	 
        	 initComponent();
        	 
         }
         
         public void initComponent(){
        	 this.dateLB = new MyLB("请选择历史账目:");
        	 this.dateLB.setBounds(LBX,LBY,LBW*2,LBH);
        	 this.add(dateLB);
        	 
        	 this.dateBox = new JComboBox<String>(dateList);
        	 dateBox.setBounds(dateLB.getX()+dateLB.getWidth(),dateLB.getY()+LBH,BoxW,BoxH);
        	 this.add(dateBox);
        	 
        	 this.obBT =  new ObserveButton();
        	 obBT.addActionListener(new ActionListener(){
        		    public void actionPerformed(ActionEvent e){
        		    	String date = (String )dateBox.getSelectedItem();
        		    	UIController.changePanel("accountInitOBPanel"+date);
        		    }
        	 });
        	 obBT.setBounds(dateBox.getX()+BoxW+50,dateBox.getY(),LBW,LBH);
        	 this.add(obBT);
        	 
        	 this.createBT = new AddButton();
        	 createBT.addActionListener(new ActionListener(){
     		    public void actionPerformed(ActionEvent e){
    		    
    		    	UIController.changePanel("accountInitCreatePanel");
    		    }
    	    });
        	 createBT.setBounds(obBT.getX(),obBT.getY()+LBH+10,LBW,LBH);
        	 this.add(createBT);
        	 
         }
         
         public void refresh(){
             ArrayList<String > dates = this.controller.showDateList();
             this.dateList = new String [dates.size()];
        	 for(int i=0;i<dates.size();i++){
        		   dateList[i] = dates.get(i);
        	 }
        	 System.out.println("accountinitMainPanel -refresh :   dateList.size : "+dateList.length);
        	 this.dateBox = new JComboBox<>(this.dateList);
        	 
         }
}
