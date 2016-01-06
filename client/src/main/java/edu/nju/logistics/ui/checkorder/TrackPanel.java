package edu.nju.logistics.ui.checkorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import edu.nju.logistics.po.orderdata.OrderPO;
import edu.nju.logistics.ui.accountant.main.MyLB;

public class TrackPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int LBH=40;
    private static int LBW=80;
    private static int LBX=100;
	private ArrayList<String> track;
	private int preferredWidth, preferredHeight;

    
    public TrackPanel(ArrayList<String> track){
    	   this.setLayout(null);
    	   this.track = track;
    	   computeWidthAndHeight();
    	   this.setPreferredSize(new Dimension(preferredWidth,preferredHeight));
    }
    
	private void computeWidthAndHeight(){
		int max=0;
		 for(int i=0;i<this.track.size();i++){
			   if(track.get(i).length()>max){
				   max=track.get(i).length();
			   }
			 
		 }
		 this.preferredWidth = 20*max;
		 this.preferredHeight=40*track.size();
	}
	
	  
	  public void paintComponent(Graphics g){
		  super.paintComponent(g);
		      g.setColor(Color.gray);
		      g.setFont(new Font("黑体",Font.PLAIN,18));
		     
		      for(int i=0;i<this.track.size();i++){
		    	  g.drawString(track.get(i),10, i*LBH+30);
		      }
		     
	  }
}
