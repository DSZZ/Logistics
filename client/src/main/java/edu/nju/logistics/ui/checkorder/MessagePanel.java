package edu.nju.logistics.ui.checkorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MessagePanel  extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private int alpha=0;
		private String message;
		private String type;
		private Font fontBig;
		private Font fontSmall;
		private int StringY=0  ,StringW=0;
		private static int panelH=40;
		private MyThread1 thread1 ;
		private MyThread2 thread2; 
		
         public MessagePanel (String message,String type){
        	    super();
        	    this.setOpaque(false);
        	    this.message = message;
        	    this.type = type;
        	    this.setLayout(null);
        	    this.setPreferredSize(new Dimension(StringW+10,panelH));
        	   fontBig= new Font("黑体",Font.BOLD,22);
        	   fontSmall= new Font("黑体",Font.PLAIN,18);
         }
         
         public void paintComponent(Graphics g){
        	 if(message.length()>6){
        		 g.setFont(fontSmall);
        		 StringY=20;
        		 StringW=20*message.length();
        	 }else{
        		 g.setFont(fontBig);
        		 StringY=22;
        		 StringW=28*message.length();
        	 }
        	
        	 
        	 if(this.type .equals("warning")){
        		 // 浅红色
        		
        	    g.setColor(new Color(255,0,0,alpha));
        	    g.fillRect(0, 0,StringW, this.getHeight());
        	    g.setColor(new Color(255,255,255,alpha));
        	    g.drawString(this.message, 10,StringY);
        	 }else{
        		 // 绿色
         		
         	    g.setColor(new Color(0 ,255 ,127,alpha));
         	    g.fillRect(0, 0,StringW, this.getHeight());
         	    g.setColor(new Color(255,255,255,alpha));
         	    g.drawString(this.message, 10,StringY);
        	 }
         }
         
         public void showMessage(){
        	  alpha =0;
        	  thread1 = new MyThread1();
        	  thread2 = new MyThread2();
        	  thread1.start();
        	  
         }
         
         
         public void setMessage (String message){
        	   this.message = message;
         }
         
         public void setType(String type){
        	 this.type = type;
         }
         
         private class MyThread1 extends Thread {
        	 public void run() {
        		   while(alpha<150){
        			     alpha++;
        		          try {
					            MyThread1.sleep(8);
				          } catch (InterruptedException e) {
					             e.printStackTrace();
				          }
        		        
        		          repaint();
        		 }
        		     try {
			            MyThread1.sleep(500);
		             } catch (InterruptedException e) {
			             e.printStackTrace();
		            }
        			 thread2.start();
        	 }
        }
         
         
         private class MyThread2 extends Thread {
        	 public void run() {
        		 while(alpha>0){
        			 alpha--;
        			 
        			 try {
				         MyThread2.sleep(8);
			          } catch (InterruptedException e) {
				          e.printStackTrace();
			          }

        			  repaint();
        		 }
             }
         }
}
