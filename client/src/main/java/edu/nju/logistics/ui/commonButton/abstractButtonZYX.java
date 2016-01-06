package edu.nju.logistics.ui.commonButton;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class abstractButtonZYX extends JButton{
	public static int buttonW=80;
    public static int buttonH=35;
    private static final long serialVersionUID = 1L;
    
    private Image  normalIcon,activeIcon,currentImage;
    
    public abstractButtonZYX(){
  	  super();
  	  initButton();
  	  this.setPreferredSize(new Dimension(buttonW,buttonH));
    }
    
    public void initButton(){
  	
  	  try {
			      normalIcon = ImageIO.read(new FileInputStream("Image/financial/zxyButton/BTN1.png"));
                  activeIcon=ImageIO.read(new FileInputStream("Image/financial/zxyButton/BTA1.png")); 
  	  } catch (IOException e) {
			e.printStackTrace();
		}
          currentImage = normalIcon;
        this.setPreferredSize(new Dimension(buttonW,buttonH));
        this.addMouseListener(new MyButtonMouseListener());
    }
    
    
    public void paintComponent(Graphics g){
  	     g.drawImage(currentImage,0, 0, this.getWidth(), this.getHeight(),null);
    }
    
    class MyButtonMouseListener implements MouseListener{

	@Override
	public void mouseEntered(MouseEvent e) {
		 currentImage = activeIcon;
	     repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		 currentImage = normalIcon;
	     repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

   
 }
    


}
