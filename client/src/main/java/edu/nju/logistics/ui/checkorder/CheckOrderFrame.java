package edu.nju.logistics.ui.checkorder;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.rmi.RemoteException;
import javax.swing.JFrame;
import javax.swing.UIManager;
import edu.nju.logistics.bl.impl.checkorder.CheckOrderBL;
import edu.nju.logistics.bl.impl.checkorder.CheckOrderController;
import edu.nju.logistics.ui.util.FrameUtil;
/**
 * 查询订单信息的框架
 * @author 侍硕
 *
 */
public class CheckOrderFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Point origin=null;
	
	  public static int  frameHeight=530;
	  public static int  frameWidth=630;
    public  CheckOrderFrame() throws RemoteException{
  	  
  	  try{
		  	     UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	     }catch(Exception e){
		          //e.printStackTrace();
	     }
  	  this.setUndecorated(true);
  	  CheckOrderBL bl = new CheckOrderBL();
  	  CheckOrderMainPanel mainPanel  = new CheckOrderMainPanel(this , new CheckOrderController(bl));
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

    
    public static void main(String args []) throws RemoteException{
  	
  	  CheckOrderFrame frame = new CheckOrderFrame();
  	  
    }
	

}
