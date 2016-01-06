package edu.nju.logistics.ui.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

/**
 * 窗口的工具类
 * @author 董轶波
 *
 */
public class FrameUtil {
	
	private FrameUtil(){};
	/**
     * 窗口居中
     */
	public static void setFrameCenter(Window frame) {
		Toolkit toolkit=Toolkit.getDefaultToolkit();
    	Dimension screen=toolkit.getScreenSize();
    	int x=screen.width-frame.getWidth()>>1;
    	int y=screen.height-frame.getHeight()>>1;
    	frame.setLocation(x, y);
    	
	}

}
