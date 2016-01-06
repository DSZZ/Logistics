package edu.nju.logistics.ui.commonButton;

import java.awt.Graphics;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DeleteButton extends abstractButton {

    /**
 * 
 */
private static final long serialVersionUID = 1L;
	private Image  wordIcon;
    
    public DeleteButton(){
    	try {
			wordIcon = ImageIO.read(new FileInputStream("Image/financial/main/删除.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
     public void paintComponent(Graphics g){
    	 super.paintComponent(g);
  	     g.drawImage(wordIcon,0, 0, this.getWidth(), this.getHeight(),null);
     }
    
    
    
}

