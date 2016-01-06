package edu.nju.logistics.ui.checkorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class StatusPanel  extends JPanel{
	     
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String status;
	
         public StatusPanel (String status){
        	    super();
        	    this.setLayout(null);
        	    this.status = status;
         }
         
         public void paintComponent(Graphics g){
        	    g.setColor(new Color(0,0,0,150));
        	    g.setFont(new Font("黑体",Font.PLAIN,25));
        	    g.drawString(status, 20, 20);
        	 
         }
         
         
}
