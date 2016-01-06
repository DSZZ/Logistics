package edu.nju.logistics.ui.accountant.main;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class MyLB  extends JLabel{
	  Font font;
	 public MyLB (String a){
	    	super(a);
	    	font = new Font("微软雅黑", Font.BOLD, 18);	
	    	this.setFont(font);
	   
	    //	this.setOpaque(false);
	    	this.setHorizontalAlignment(JTextField.CENTER);
	    //	this.setBorder(new EmptyBorder(0,0,0,0));
	    }
	    
	    public MyLB (){
	    	super();
	    	font = new Font("微软雅黑", Font.BOLD, 18);	
	    	this.setFont(font);
	   
	    //	this.setOpaque(false);
	    	this.setHorizontalAlignment(JTextField.CENTER);
	    //	this.setBorder(new EmptyBorder(0,0,0,0));
	    }
	    
	    public MyLB (String a,String light){
	    	super(a);
	    	font = new Font("微软雅黑", Font.PLAIN, 18);	
	    	this.setFont(font);
	   
	    //	this.setOpaque(false);
	    	this.setHorizontalAlignment(JTextField.CENTER);
	    //	this.setBorder(new EmptyBorder(0,0,0,0));
	    }
}
