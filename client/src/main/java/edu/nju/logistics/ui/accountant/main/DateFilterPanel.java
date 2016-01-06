package edu.nju.logistics.ui.accountant.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 专门把日期的年、月、日的搜索栏做成一个Panel
 * @author 侍硕
 *
 */

public class DateFilterPanel  extends JPanel{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  private static  int  width=250;
	  private static int   high=30;
	  private static int LBW=25;
	  private static int LBH=30;
	  private static int BoxH = 25;
	  private static int BoxW=60;
	  JComboBox<String>   yearBox,monthBox,dayBox;
	  MyLB year,month,day;
	  public DateFilterPanel (){
		  this.setLayout(null);
	      this.setOpaque(false);
		  this.setSize(width,high);
		  this.initComponent();
	  }
	  public void initComponent(){
		    year = new MyLB("年","light");
		    month = new MyLB("月","light");
		    day= new MyLB("日","light");
		  
		    String [] years= new String [10];
	        	for(int i=0;i<10;i++){
	    	     	years[i]=Integer.toString(2010+i);
	    	}
	    	//时间年份下拉选择框
			yearBox = new JComboBox<String>(years);
			yearBox.setBounds(0,0,BoxW,BoxH);
            year.setBounds(yearBox.getX()+BoxW,yearBox.getY()-5,LBW,LBH);
			yearBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dayBox.setSelectedIndex(0);
					String year = (String) yearBox.getSelectedItem();
					String month = (String) monthBox.getSelectedItem();
					@SuppressWarnings("unchecked")
					ComboBoxModel<String> model = new MyBoxModel(year, month);
					dayBox.setModel(model);
					dayBox.setSelectedIndex(0);
				}
				
			});
			
			
			
			//时间月份下拉选择框
			String[] months = new String [12];
			for(int i=0;i<12;i++){
				if(i<9){
				    months[i]="0"+Integer.toString(i+1);
				}else{
				    months[i]=Integer.toString(i+1);
				 }
			}
			monthBox = new JComboBox<String>(months);
			monthBox.setBounds(year.getX()+LBW, yearBox.getY(), BoxW,BoxH);
		    month.setBounds(monthBox.getX()+BoxW,monthBox.getY()-5,LBW,LBH);
			monthBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String year = (String) yearBox.getSelectedItem();
					String month = (String) monthBox.getSelectedItem();
					@SuppressWarnings("unchecked")
					ComboBoxModel<String> model = new MyBoxModel(year, month);
					dayBox.setModel(model);
					dayBox.setSelectedIndex(0);
				}
				
			});
	    	
			//时间日期下拉选择框
			String[] days = new String[31];
			for(int i = 1; i <= 31; i++) {
				if(i < 10)
					days[i-1] = "0" + String.valueOf(i);
				else
					days[i-1] = String.valueOf(i);
			}
			
			dayBox = new JComboBox<String>(days);	
			dayBox.setBounds(month.getX()+LBW, monthBox.getY(), BoxW,BoxH);
			day.setBounds(dayBox.getX()+BoxW,dayBox.getY()-5,LBW,LBH);
			
			
			
			this.add(yearBox);    this.add(year);
			this.add(monthBox);   this.add(month);
			this.add(dayBox);     this.add(day);
		  
	  }

	  
	  
	  public String getYear(){
		  return (String)this.yearBox.getSelectedItem();
	  }
	  public String getMonth(){
		  return (String)this.monthBox.getSelectedItem();
	  }
	  public String getDay(){
		  return (String)this.dayBox.getSelectedItem();
	  }
	  
	  public String getDate(){
		  String temp = (String)this.yearBox.getSelectedItem()+"/"
	                                    +(String)this.monthBox.getSelectedItem()+"/"
	                                     +(String)this.dayBox.getSelectedItem();
		  return temp;
	  }
	  
	  
	  public static void main(String args []){
		     JFrame frame = new JFrame();
		     DateFilterPanel pane = new DateFilterPanel();
		     frame.add(pane,BorderLayout.CENTER);
		     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		     frame.setVisible(true);
		     
	  }
}
