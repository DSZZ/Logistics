package edu.nju.logistics.ui.checkorder;

import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import edu.nju.logistics.bl.impl.checkorder.CheckOrderController;
import edu.nju.logistics.po.orderdata.OrderPO;
import edu.nju.logistics.ui.commonButton.BackButton;
import edu.nju.logistics.ui.functionButton.MySearchButton;
import edu.nju.logistics.ui.login.LoginFrame;


public class CheckOrderMainPanel extends JPanel {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private static int  MinW=20;
	 private static int  MinH=20;
	 private static int  MinX=(CheckOrderFrame.frameWidth-2*MinW);
	 
	 //文本框位置大小
     private static int TFX=242;
     private static int TFY=71;
     private static int TFW =212-35;
     private static int TFH =36;
     //搜索按钮位置
     private static int LBX=TFX+TFW;
	 private static int LBY=TFY;
	 
     private static int LBH=35;
     private static int LBW=80;
     
     public static int  frameHeight=530;
	 public static int  frameWidth=630;
	 
	 public static int  statusPanelY=200;
     public static int  statusPanelW=230;
	 public static int   statusPanelH=55;
	 
	 public static int   trackPanelY=300;
	 public static int   trackPanelW=300;
	 public static int   trackPanelH=160;
	 

	 public static int   messagePanelX=460;
	 public static int   messagePanelY=TFY;
	 public static int   messagePanelW=200;
	 public static int   messagePanelH=40;
     
	 private JFrame frame;
	 private CheckOrderController controller;
     private  StatusPanel  statusPanel;
     private  TrackPanel    trackPanel;	
     private MessagePanel  messagePanel;
     private JScrollPane spane;
     private  String status;
     private  ArrayList<String> track;
	 Image bg;
	//min\exit
    ImageIcon renderIcon2;
    ImageIcon minIcon;
    ImageIcon exitIcon;
    
	 //最小化、关闭渲染图标
    JLabel renderLB4,renderLB5;
    //最小化正常显示图标
    JLabel min;
    //关闭
    JLabel  exit;
    
    //输入订单号的文本框
     JTextField  inputTF;
    MySearchButton searchBT ;
    BackButton backBT;
    
    public CheckOrderMainPanel (JFrame frame  , CheckOrderController controller){
   	    this.frame=frame;
   	    this.controller = controller;
   	    this.setLayout(null); 
   	      try{
    	       bg= ImageIO.read(new FileInputStream("Image/financial/main/订单查询.png"));
               renderIcon2=new ImageIcon("Image/login/衬底.png"); 
               minIcon = new ImageIcon("Image/login/最小化.png"); 
               exitIcon = new ImageIcon("Image/login/关闭.png"); 
            }catch (Exception e){
                  e.printStackTrace();
            }    
    	this.initComponent();
    }
    
    
    public void paintComponent(Graphics g){
    	  g.drawImage(bg, 0, 0,frameWidth,frameHeight,null);
           
    }
    
    public void initMinAndExit(){

   	    
	      renderLB4 = new JLabel (renderIcon2);
          renderLB5 = new JLabel (renderIcon2); 
          renderLB4.setVisible(false);
          renderLB5.setVisible(false);
	      min = new JLabel(minIcon);
 	      exit= new JLabel(exitIcon);
 	      min.addMouseListener(new MouseAdapter(){
 		
		        public void mouseEntered(MouseEvent e) {
			        renderLB4.setVisible(true);
		        }

	
		         public void mouseExited(MouseEvent e) {
		 	        renderLB4.setVisible(false);
		         }
		
		         public void mouseClicked(MouseEvent  e){
		            	JLabel minLB = (JLabel) e.getSource();
			            Container p=minLB.getParent();
			           while(p!=null&&!(p instanceof CheckOrderFrame)){
				                         p=p.getParent();
			            }
			            CheckOrderFrame frame = (CheckOrderFrame) p ;
			            frame.setExtendedState(JFrame.ICONIFIED);
	   	          }
     	});
 	
                exit.addMouseListener(new MouseAdapter(){
 	 	
		            public void mouseEntered(MouseEvent e) {
			             renderLB5.setVisible(true);
		            }

	
		            public void mouseExited(MouseEvent e) {
		              	renderLB5.setVisible(false);
		            }
		            
		            public void mouseClicked(MouseEvent  e){
                          //	JLabel minLB = (JLabel) e.getSource();
                          //	CheckOrderFrame frame = (CheckOrderFrame)minLB.getParent().getParent();
			              System.exit(0);
		            }
 	         });
                
 	        //设置所有组件的位置
 	        min.setBounds(MinX, 0, MinW, MinH);
 	        renderLB4.setBounds(MinX, 0, MinW, MinH);
 	        exit.setBounds(MinX+MinW, 0, MinW, MinH);
 	        renderLB5.setBounds(MinX+MinW, 0, MinW, MinH);
 	        
 	        
 	        this.add(min);   this.add(exit);
 	        this.add(renderLB4); this.add(renderLB5);
          }
    
    
    public void initMessagePanel(){
    	
        this.messagePanel = new MessagePanel("","warning");
    	messagePanel .setBounds(messagePanelX,messagePanelY,
    			messagePanelW,messagePanelH);
    	this.add(messagePanel);
    	
    }
    
    public void initComponent(){
    	this.initMinAndExit();
    	this.initMessagePanel();
    	
    	this.inputTF  =  new JTextField();
    	this.inputTF.setOpaque(false);
    	this.inputTF.setFont(new Font("微软雅黑",Font.PLAIN,18));
    	this.searchBT = new MySearchButton();
    	this.backBT = new BackButton();
    	
    	
    	this.inputTF.setBounds(TFX,TFY+1,
    			TFW,TFH);
    	this.searchBT.setBounds(LBX,LBY+1,
    			35,35);
    	this.backBT.setBounds((frameWidth-LBW)/2,frameHeight-LBH-10,
    			LBW,LBH);
    	
    	
    	this.searchBT.addMouseListener(new MouseAdapter(){
      		public void mouseClicked(MouseEvent e){
      			    String orderID = inputTF.getText();
				   if( controller.isValidInput(orderID)){
					    OrderPO po = null;
						try {
							po = controller.getOrderPO(orderID);
						} catch (RemoteException e1) {
							JOptionPane.showMessageDialog(null,"与服务器断开连接\n等待服务器重连");
							frame.dispose();
							new LoginFrame();
						}
						
					    if(po==null){
					    	System.out.println("订单号码不存在");
					    	messagePanel.setMessage("订单号不存在");
					    	messagePanel.showMessage();
					    	repaint();
					    }else{
					    	
					    	setStatus(po);
					    	setTrack(po);
					    	statusPanel = new StatusPanel(status);
					    	trackPanel = new TrackPanel(track);
					    	spane = new JScrollPane(trackPanel);
					    	
					    	statusPanel.setBounds(TFX,statusPanelY,statusPanelW,statusPanelH);
					    	spane.setBounds(TFX,trackPanelY,trackPanelW,trackPanelH);
					    	spane.setOpaque(false);
					    	spane.getViewport().setOpaque(false);
					    	add(statusPanel);
					    	add(spane);
					    	validate();
					    	repaint();
					    }
				   }else{
					   
					    System.out.println("格式错误");
				    	messagePanel.setMessage("订单格式错误");
				    	messagePanel.showMessage();
				    	repaint();
					   
				   }
				   
		
      		}
       	});
 				
 					   
 				

    	
    	this.backBT.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				    frame.dispose();
				    new LoginFrame();
			}
    		
    	});
    	
    	
    	
    	this.add(inputTF) ; this.add(searchBT)  ; 
    	
    	this.add(backBT);    	
    }
   
    private void setStatus(OrderPO po){

 		switch(po.getOrderStatus()){
               case  received :              this. status = "已接收";
               case  senderBranch:    this.status = "寄件人所在地营业厅";
               case  senderCenter  :   this.status = "寄件人所在地中转点";
               case  receiverCenter :  this.status = "收件人所在地中转点";
               case receiverBranch :  this.status = "寄件人所在地营业厅";
               case dispatching :         this. status ="派件中";
         }
    }
    
      private void setTrack(OrderPO po){
    	  this.track = po.getHistoryTrace();   
          if(this.track.size()==0){
          	System.out.println("历史轨迹为空");
          }
     }
    
}
