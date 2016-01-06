package edu.nju.logistics.ui.accountant.main;


import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MyTF  extends JTextField{
	Font font;
	
    public MyTF (String a){
    	super(a);
    	font = new Font("微软雅黑", Font.PLAIN, 14);	
    	this.setFont(font);
   
    	this.setOpaque(false);
    	this.setHorizontalAlignment(JTextField.CENTER);
    	this.setBorder(new EmptyBorder(0,0,0,0));
    }
    
    public MyTF (){
    	super();
    	font = new Font("微软雅黑", Font.PLAIN, 14);	
    	this.setFont(font);
    	this.setOpaque(false);
    	this.setHorizontalAlignment(JTextField.CENTER);
    	this.setBorder(new EmptyBorder(0,0,0,0));
    }
}
