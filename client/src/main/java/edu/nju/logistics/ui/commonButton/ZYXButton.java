package edu.nju.logistics.ui.commonButton;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ZYXButton extends abstractButtonZYX {

    /**
 * 
 */
private static final long serialVersionUID = 1L;
	private Image  wordIcon;
    private static int ButtonWidth=100;
    private static int ButtonHeight=40;
    public ZYXButton(String name){
    	try {
			wordIcon = ImageIO.read(new FileInputStream("Image/financial/zyxButton/"+name+".png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	this.setPreferredSize(new Dimension(ButtonWidth,ButtonHeight));
    }
    
    
     public void paintComponent(Graphics g){
    	 super.paintComponent(g);
  	     g.drawImage(wordIcon,0, 0, this.getWidth(), this.getHeight(),null);
     }
    
    
    
}
