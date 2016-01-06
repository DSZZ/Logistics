package edu.nju.logistics.ui.accountant.main;

import java.awt.Graphics;
import java.awt.Image;


import javax.swing.ImageIcon;

import javax.swing.JPanel;
import edu.nju.logistics.ui.main.MainFrame;
import edu.nju.logistics.vo.UserVO;

public class FinaMainFrame extends   MainFrame {
	
	/**
	 * 背景图片
	 */
	private static final Image bg=new ImageIcon("Image/financial/main/Framebg4.jpg").getImage();
    public static int showX=SHOWPANEL_INIT_X;
    public static int showY=SHOWPANEL_INIT_Y;
    public static int showW=980;
    public static int showH=560;
	
	protected void initMainPanel(){
		this.mainPanel=new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(bg, 0, 0, this.getWidth(),this.getHeight(),null);	
			}
		};
     	this.mainPanel.setLayout(null);
		ButtonPanel buttonPanel = new ButtonPanel(this.userVO);
   	    buttonPanel.setBounds(0,SHOWPANEL_INIT_Y,SHOWPANEL_INIT_X,SHOWPANEL_HEIGHT);
        this.add(buttonPanel);
	}
	
	protected JPanel initShowPanel(){
        showPanel = new JPanel();
        showPanel.setLayout(null);
        showPanel.setOpaque(false);
	//    showPanel.setBorder(BorderFactory.createEtchedBorder());
    	showPanel.setBounds(showX,showY
				,showW,showH);
		return showPanel;
	}
        
        public FinaMainFrame(UserVO vo) {
		         super(vo);
		// TODO Auto-generated constructor stub
		     
		         
	    }

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
