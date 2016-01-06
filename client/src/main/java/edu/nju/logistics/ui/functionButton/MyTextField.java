package edu.nju.logistics.ui.functionButton;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.nju.logistics.ui.accountant.main.MoneyDocument;

public class MyTextField extends JPanel{
 
       	private static final long serialVersionUID = 1L;
		  public static  int TFW=150;
          public static  int TFH=30;
          
          private Image  normalIcon,activeIcon,currentIcon;
          private JTextField tf;
          private Label lb ;
          public MyTextField(){
        	  super();
        	  this.setLayout(null);
        	  this.setOpaque(false);
        	  this.setPreferredSize(new Dimension(TFW,TFH));
        	  tf = new JTextField();
        	  initComponent();
        	  setBorder(BorderFactory.createEmptyBorder());
          }
          
          
          public MyTextField(String text){
        	  super();
        	  this.setLayout(null);
        	  this.setOpaque(false);
        	  this.setPreferredSize(new Dimension(TFW,TFH));
        	  tf = new JTextField(text);
        	  initComponent();
        	  setBorder(BorderFactory.createEmptyBorder());
          }
          
          
          public void  initComponent(){
        	   
        	    tf.setBorder(BorderFactory.createEmptyBorder());
        	    tf.setOpaque(false);
        	    tf.setFont(new Font("微软雅黑",Font.PLAIN,18));
        	    
        	    lb = new Label();
        	    
        	    tf.setBounds(3,0,TFW-3,TFH);
        	    lb.setBounds(0,0,TFW,TFH);
        	    
        	    this.add(tf);   this.add(lb);
        	    
          }
          
          
          public String getText(){
        	  return  tf.getText();
          }
          
          
          public void setText(String text){
        	  this.tf.setText(text);
          }
          
          public void NumsOnly(){
        	  this.tf.setDocument(new MoneyDocument());
          }
          
          
           class LBListener implements MouseListener{

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				  currentIcon = activeIcon;
				  repaint();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				currentIcon = normalIcon;
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	  
          } 
          
           class  Label extends JLabel{
        	   
			private static final long serialVersionUID = 1L;

				public Label(){
        	    	     super();
        	    	     initComponent();
        	      }
        	      
                 
                 public void initComponent(){
               	      try {
       			               normalIcon = ImageIO.read
       			    		         (new FileInputStream("Image/financial/main/TFnormal.png"));
                               activeIcon=ImageIO.read
                       		         (new FileInputStream("Image/financial/main/TFactive.png"));
           	      
           	           } catch (IOException e) {
       			               e.printStackTrace();
       		           }
               	      
               	  currentIcon = normalIcon;
               	  this.addMouseListener(new LBListener());
               	  this.setPreferredSize(new Dimension(TFW,TFH));
               	  repaint();
               	  
                 }
                 
                 public void  paintComponent(Graphics g){
               	        g.drawImage(currentIcon,0,0,getWidth(),getHeight(),null);
               	        super.paintComponent(g);
                 }
                 
           }
}
