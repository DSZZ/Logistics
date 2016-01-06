package edu.nju.logistics.ui.functionButton;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JLabel;

public class MyButton extends JLabel{
	      
          public static int buttonW=170;
          public static int buttonH=55;
	      private static final long serialVersionUID = 1L;
	      
	      private boolean flag_enable=false;
		
	      private boolean flag_clicked=false;
	      
          private Image  normalIcon,hoverIcon,activeIcon,currentImage,wordImage;
          public MyButton(String buttonName){
        	  super(buttonName);
        	  initButton(buttonName);
          }
          
          public void initButton(String buttonName){
        	
        	  try {
				      normalIcon = ImageIO.read(new FileInputStream("Image/financial/functionButton/buttonNormal.png"));
				      hoverIcon = ImageIO.read(new FileInputStream("Image/financial/functionButton/buttonHover.png"));
	                  activeIcon=ImageIO.read(new FileInputStream("Image/financial/functionButton/buttonActive.png")); 
        	          wordImage = ImageIO.read(new FileInputStream("Image/financial/functionButton/"+buttonName+".png")); 
        	          
        	  } catch (IOException e) {
				e.printStackTrace();
			}
                currentImage = normalIcon;
              this.setPreferredSize(new Dimension(buttonW,buttonH));
              this.addMouseListener(new MyButtonMouseListener());
          }
          
          
          public void paintComponent(Graphics g){
        	     g.drawImage(currentImage,0, 0, this.getWidth(), this.getHeight(),null);
        	     g.drawImage(wordImage,0, 0, this.getWidth(), this.getHeight(),null);
          }
          
          class MyButtonMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
//		       currentImage = activeIcon;
//		       
//			   repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			//judgeEnable();
			if(flag_enable||flag_clicked){
				return;
			}
			   currentImage = hoverIcon;
			  
			   repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
		//	judgeEnable();
			//if(currentImage == hoverIcon){
			if(flag_enable||flag_clicked){
				return;
			}
				currentImage = normalIcon;
				   repaint();
		//	}
			   
		}

		@Override
		public void mousePressed(MouseEvent e) {
//			 judgeEnable();
			if(flag_enable||flag_clicked){
				return;
			}
			 	currentImage = activeIcon;
			      repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
			//   currentImage = hoverIcon;
			//    repaint();
		}
         
       }
          /**
           *如果按钮不可点击
           */
          public void setEnableFalseIcon(){
        	  currentImage=activeIcon;
        	  this.repaint();
        	  this.flag_enable=true;
          }
          /**
           *如果按钮可点击
           */
          public void setEnableTrueIcon(){
        	  currentImage=normalIcon;
        	  this.repaint();
        	  this.flag_enable=false;
          }
          //被点击
          public void clicked(){
        	  currentImage=activeIcon;
        	  this.repaint();
        	  this.flag_clicked=true;
          }
          //被点击还原
          public void restore(){
        	  currentImage=normalIcon;
        	  this.repaint();
        	  this.flag_clicked=false;
          }
//          
}
