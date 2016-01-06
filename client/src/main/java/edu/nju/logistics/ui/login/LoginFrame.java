package edu.nju.logistics.ui.login;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.UIManager;

import edu.nju.logistics.ui.util.FrameUtil;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame {
	  private Point origin=null;
	  public static int  frameHeight=530;
	  public static int  frameWidth=630;
      public  LoginFrame(){
    	  
    	  try{
  		  	     UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
  	     }catch(Exception e){}
    	  this.setUndecorated(true);
    	  LoginPanel mainPanel  = new LoginPanel(this);
    	  
    	  origin = new Point();
    	  this.add(mainPanel);
    	  this.setVisible(true);
    	  this.setSize( frameWidth,frameHeight);
    	  this.setResizable(false);
    	  
    	  FrameUtil.setFrameCenter(this);
    	//设置可拖拽
  		  this.addMouseListener(new MouseAdapter(){
  			 public void mousePressed(MouseEvent e) {
  				// 当鼠标按下的时候获得窗口当前的位置
  				origin.x = e.getX();
  				origin.y = e.getY();
  			}
  		});
  		  this.addMouseMotionListener(new MouseMotionAdapter(){
  			public void mouseDragged(MouseEvent e) {
  				// 当鼠标拖动时获取窗口当前位置
  				Point p = getLocation();
  				// 设置窗口的位置
  				// 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
  				setLocation(p.x + e.getX() - origin.x, p.y + e.getY()- origin.y);
  			}
  		
  		});
      }

      
      public static void main(String args []){
    	
    	  new LoginFrame();
    	  
      }
}
