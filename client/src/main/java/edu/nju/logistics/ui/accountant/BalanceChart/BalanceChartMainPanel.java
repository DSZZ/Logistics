package edu.nju.logistics.ui.accountant.BalanceChart;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.nju.logistics.bl.impl.financialmanagement.BalanceChartController;
import edu.nju.logistics.ui.accountant.main.MyLB;
import edu.nju.logistics.vo.financial.BalanceChartVO;

public class BalanceChartMainPanel  extends JPanel{
              /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
			  private BalanceChartController controller;
              private BalanceChartVO chartVO;
              
              //收入
              private double income;
              //成本
              private double cost;
              //利润
              private double balance;
                //总租金
       	      private double  totalRent;      
       	       // 总运费 
       	      private double totalFreight;       	  
       	       //总工资
       	       private double totalSalary;
       	       
       	     private static int VGap = 20;
       	     private static int LBH =35;
       	     private static int LBW=80;
       	     
       	     private static int LBX=50;
       	     private static int LBY=200;
       	     private static int recX = LBW+LBX;
       
              //饼状图起始XY
              private static int cirX=550;
              private static int cirY=170;
             
            //条形统计图长条高度
              private static  int height=35;
              private static  int maxWidth=270;
              //饼状图的半径
              private static  int r=130;
              private static  int inR=90;
             
              //条形图的各元素的宽
              private  int incomeW=0;
              private  int costW=0;
              private  int balanceW=0;
              //饼状图的个元素的角度
              private  int freightA=0;
              private  int rentA=0;
              private  int salaryA=0;
              private  double freightRate;
              private double rentRate;
              private double  salaryRate;
              //饼状图圆心坐标
              private int rx=cirX+r;
              private int ry=cirY+r;
              MyLB    title1,title2;
              MyLB  incomeLB  ,costLB, balanceLB;
              JLabel  incomeVLB ,costVLB,balanceVLB;
              JLabel   freightVLB, rentVLB,salaryVLB;
              
              MyThread thread ;
              MyAnotherThread thread1;
              private int w1=0,w2=0,w3=0;
              private int a1=0,a2=0,a3=0;
              private int longest = 0;
              public BalanceChartMainPanel(  BalanceChartController controller){
            	           this.setLayout(null);
            	            this.controller = controller;
            	            this.chartVO = controller.showNewChart();
            	            
//                            this.income = chartVO.getGeneralIncome();
//                            this.cost = chartVO.getTotalExpense();
//                            this.balance = chartVO.getGrossProfit(); 
//            	                this.totalRent = chartVO.getTotalRent();
//            	                this.totalFreight = chartVO.getTotalFreight();
//            	                this.totalSalary = chartVO.getTotalSalary();
            	            
                            this.income=25000;
                            this.cost=55000;
                            this.balance=10000;
                            
                            this.totalFreight=5000;
                            this.totalRent=8000;
                            this.totalSalary=2000;
                            
                            this.computeHeight();
                            this.computeArc();
                            this.initComponent();
              }
              
              public void initComponent() {
            	  
            	    this.title1= new MyLB("利润收支表","light");
            	    this.title2= new MyLB("支出比率表","light");
            	  
            	     this.incomeLB = new MyLB("收入","light");
            	     this.costLB = new MyLB("支出","light");
            	     this.balanceLB = new MyLB("利润","light");
            	     
            	     this.incomeVLB = new JLabel(this.income+"");
            	     this.costVLB=  new JLabel(this.cost+"");
            	     this.balanceVLB = new JLabel(this.balance+"");
            	     
            	     this.incomeVLB.setFont(new Font("Romantic",Font.BOLD,18));
            	     this.incomeVLB.setForeground(new Color(0 ,191 ,255,150));
            	     this.costVLB.setFont(new Font("Romantic",Font.BOLD,18));
            	     this.costVLB.setForeground(new Color(255,0,0,150));
            	     this.balanceVLB.setFont(new Font("Romantic",Font.BOLD,18));
            	     this.balanceVLB.setForeground(new Color(0,255,0,150));
            	     
            	     this.incomeLB.setBounds(LBX,LBY,LBW,LBH);
            	     this.costLB.setBounds(LBX,incomeLB.getY()+LBH+VGap,LBW,LBH);
            	     this.balanceLB.setBounds(LBX,costLB.getY()+LBH+VGap,LBW,LBH);
            	     
            	     this.incomeVLB.setBounds(recX+this.incomeW+10,this.incomeLB.getY(),LBW,LBH);
            	     this.costVLB.setBounds(recX+this.costW+10,this.costLB.getY(),LBW,LBH);
            	     this.balanceVLB.setBounds(recX+this.balanceW+10,this.balanceLB.getY(),LBW,LBH);
            	
            	     this.incomeVLB.setVisible(false);
            	     this.costVLB.setVisible(false);
            	     this.balanceVLB.setVisible(false);
            	     
            	     
            	     this.freightVLB = new JLabel("运费:"+(int)(this.freightRate*100)+"%");
            	     this.rentVLB = new JLabel("租金:"+(int)(this.rentRate*100)+"%");
            	     this.salaryVLB = new  JLabel("薪水:"+(int)(this.salaryRate*100)+"%");
            	     
            	     this.freightVLB.setFont(new Font("Romantic",Font.BOLD,24));
            	     this.freightVLB.setForeground(new Color( 255, 255, 0,200));
            	     this.rentVLB.setFont(new Font("Romantic",Font.BOLD,24));
            	     this.rentVLB.setForeground(new Color(147, 112 ,219,200));
            	     this.salaryVLB.setFont(new Font("Romantic",Font.BOLD,24));
            	     this.salaryVLB.setForeground(new Color(0 ,238 ,118,200));
            	     
            	     this.title1.setBounds(incomeLB.getX()+LBW,80,200,LBH);
            	     this.title2.setBounds(cirX+20,title1.getY(),200,LBH);
            	     
            	     
            	     
            	     this.freightVLB.setBounds(rx+(int)(Math.cos(freightA/2*Math.PI/180)*(r+30)),
            	    		 ry-(int)(Math.sin(freightA/2*Math.PI/180)*(r+60)),LBW*2,LBH);
            	  
            	     
            	     this.rentVLB.setBounds(rx+(int)(Math.cos((freightA+rentA/2)*Math.PI/180)*r),
            	    		 ry-(int)(Math.sin((freightA+rentA/2)*Math.PI/180)*(r+60)),LBW*2,LBH);
            	     
            	     this.salaryVLB.setBounds(rx+(int)(Math.cos((freightA+rentA+salaryA/2)*Math.PI/180)*r),
            	    		 ry-(int)(Math.sin((freightA+rentA+salaryA/2)*Math.PI/180)*(r+60)),LBW*2,LBH);
                    
                     
            	     this.freightVLB.setVisible(false);
                     this.rentVLB.setVisible(false);
                     this.salaryVLB.setVisible(false);
                     
                     this.add(title1);  
                     this.add(title2);
                     
                     this.add(freightVLB);
                     this.add(rentVLB);
                     this.add(salaryVLB);
            	     
            	      
            	     this.add(incomeLB);
            	     this.add(costLB);
            	     this.add(balanceLB);
            	     
            	     this.add(incomeVLB);
            	     this.add(costVLB);
            	     this.add(balanceVLB);
            	     thread = new MyThread();
            	     thread.start();
            	     
            	     thread1 = new MyAnotherThread();
            	     thread1 .start();     
              }
              
              public void paintComponent(Graphics g){
    		                
    		                 Graphics2D g2 = ( Graphics2D)  g;
    		                 g.setColor(Color.white);
    		                 g.fillRect(0, 0, this.getWidth(), this.getHeight());
    		                 
    		                 //柱状图:
    		                 
    		                 //蓝色的总收入
    		                 g2.setColor(new Color(0 ,191 ,255,150));
    		                 g2.fillRect(recX, this.incomeLB.getY(), w1,height);
    		                 //红色的总支出
    		                 g2.setColor(new Color(255,0,0,150));
    		                 g2.fillRect(recX,this.costLB.getY(),w2,height );
    		                 //绿色的总利润
    		                 g2.setColor(new Color(0,255,0,150));
    		                 g2.fillRect(recX,this.balanceLB.getY(), w3, height);
    		                 
    		                 //饼状图：
    		                 
    		                 
    		                 //黄色的总运费
    		                 g2.setColor(new Color(255, 255, 0,150));
    		                 g2.fillArc(cirX, cirY,2*r, 2*r, 0, a1);

    		                 // 浅蓝色的总租金
    		                 g2.setColor(new Color(147, 112 ,219,200));
    		                 g2.fillArc(cirX, cirY,2*r, 2*r, freightA, a2);

    		                 //绿色的总薪水
    		                 g2.setColor(new Color(0 ,238 ,118,200));
    		                 g2.fillArc(cirX, cirY,2*r, 2*r, freightA+rentA, a3);
    		                 //内圈白色
    		                 g2.setColor(new Color(255,255,255,255));
    		                 g2.fillOval(cirX+r-inR, cirY+r-inR,2* inR, 2*inR);
    		                
    		                 
              }
              
              //根据比例计算条形图的高
              private void computeHeight(){
            	       double max  =0;
            	       double valuePerPixel =0;
            	       
            	       if(this.income>=this.cost){
            	    	     max=this.income;
            	       }else{
            	    	    max=this.cost;
            	       }
            	       
            	       valuePerPixel = max/(double)maxWidth;
            	       
            	        this.incomeW=(int)  (this.income/valuePerPixel);
            	        System.out.println("incomeW"+this.incomeW);
            	        this.costW=(int)  (this.cost/valuePerPixel);
            	        System.out.println("costW"+this.costW);
            	        this.balanceW=(int)  (this.balance/valuePerPixel);
            	        System.out.println("balanceW"+this.balanceW);
            	        
            	        this.longest =(int) (max/valuePerPixel);
              }
              
            //根据比例计算饼状图的角度
              private void computeArc(){
            	       double total  =0;
       	               double valuePerDegree =0;
       	               total =  this.totalFreight+this.totalRent+this.totalSalary;
       	    	       valuePerDegree =total/360;
            	        this.freightA=(int)  (this.totalFreight/valuePerDegree);
            	        this.rentA=(int)  (this.totalRent/valuePerDegree);
            	        this.salaryA=(int)  (this.totalSalary/valuePerDegree);
            	        
            	        this.freightRate = this.totalFreight/total;
            	        this.rentRate = this.totalRent/total;
            	        this.salaryRate = this.totalSalary / total;
              }
              
              private class MyThread extends Thread{
            	   
            	  
            	    @Override
            	     public void run(){
            	    	  if(incomeW>costW){
            	    		    while(w1<incomeW){
                  	    		
                  	    		  if(w3<=balanceW){
                  	    			   w3++;
                  	    		  }else{
                  	    		     	balanceVLB.setVisible(true);
                  	    		  }
                  	    		
                  	    	      if(w2<=costW){
                  	    	    	   w2++;
                  	    	       }else{
                  	    	    	   costVLB.setVisible(true);
                  	    	       }
                  	    	    
                  	    	        w1++;
                  	    	        repaint();
                  	    	       try {
              						     MyThread.sleep(8);
              					    } catch (InterruptedException e) {
              						 
              						  e.printStackTrace();
              					    }
                  	    	   }
                  	           incomeVLB.setVisible(true);
                  	      }else{
                  	    	while(w2<costW){
                  	    		
                	    		  if(w3<=balanceW){
                	    			   w3++;
                	    		  }else{
                	    		     	balanceVLB.setVisible(true);
                	    		  }
                	    		
                	    	      if(w1<=incomeW){
                	    	    	   w1++;
                	    	       }else{
                	    	    	   incomeVLB.setVisible(true);
                	    	       }
                	    	    
                	    	        w2++;
                	    	        repaint();
                	    	       try {
            						     MyThread.sleep(8);
            					    } catch (InterruptedException e) {
            						 
            						  e.printStackTrace();
            					    }
                	    	   }
                	           costVLB.setVisible(true);
                  	      }
            	    	
                  	   
            	     }
              }

              private class    MyAnotherThread extends Thread{
            	     @Override
         	        public void run(){
            	    	 while(a1<freightA){
            	    		 a1++;
            	    		 repaint();
            	    		 try {
         						MyThread.sleep(8);
         					} catch (InterruptedException e) {
         						// TODO Auto-generated catch block
         						e.printStackTrace();
         					}
            	    	 }
            	    	 freightVLB.setVisible(true);
            	    	 
            	    	 while(a2<rentA){
            	    		 a2++;
            	    		 repaint();
            	    		 try {
         						MyThread.sleep(8);
         					} catch (InterruptedException e) {
         						// TODO Auto-generated catch block
         						e.printStackTrace();
         					}
            	    	 }
            	    	 rentVLB.setVisible(true);
            	    	 
            	    	 while(a3<salaryA){
            	    		 a3++;
            	    		 repaint();
            	    		 try {
         						MyThread.sleep(8);
         					} catch (InterruptedException e) {
         						// TODO Auto-generated catch block
         						e.printStackTrace();
         					}
            	    	 }
            	    	   salaryVLB.setVisible(true);
            	     }
            	     
            	  
              }

}
