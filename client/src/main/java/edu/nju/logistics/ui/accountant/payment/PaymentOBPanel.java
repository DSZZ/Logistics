package edu.nju.logistics.ui.accountant.payment;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import edu.nju.logistics.ui.accountant.main.UIController;
import edu.nju.logistics.ui.commonButton.BackButton;
import edu.nju.logistics.ui.commonButton.ExportButton;
import edu.nju.logistics.ui.functionButton.MyTextField;
import edu.nju.logistics.ui.util.ExportExcelUtil;
import edu.nju.logistics.vo.financial.PaymentVO;
import edu.nju.logistics.vo.financial.SystemCostVO;

public class PaymentOBPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static int LBH = 35;  
	private static int LBW = 80;
	private static int TFH = 30;
	private static int TFW = 150;
	private static int panelH=560;
	private static int panelW=980;
	
	

	//行垂直间距
	private static int VGap=21;
	
	//条目
	private static int initX=20;
	private static int initY=10;
	//租金LB
	private static int itemX=initX+LBW+50;
	private static int itemY=initY+LBH+VGap;
	//租金TF
	private static int TFX=itemX+LBW+50;
	private static int TFY=itemY;
	
	//运费
	private static int itemX1=TFX+TFW+100;
	
	private static int TFX1 = itemX1+LBW+50	;
	   
	   private PaymentVO paymentVO;
	   private SystemCostVO costVO ;
	
	 //多数条目的字体较小
	   Font font;
	   //部分条目的大字体
	   Font font1;
	   
	   BackButton backBT;
	   ExportButton exportBT;
	   
	   //条目
	   JLabel entryLB;
	 //租金
	   JLabel rentLB;
	   MyTextField  rentTF;
	 //运费
	   JLabel freightLB;
	   MyTextField freightTF;
	 //工资
	   JLabel salaryLB;
	   MyTextField salaryTF;
	 //奖励
	   JLabel rewardLB;
	   MyTextField rewardTF;
	   //小结
	   JLabel summaryLB;
	
	 //付款金额
	   JLabel totalFeeLB;
	   MyTextField totalFeeTF;
	 //付款账号
	   JLabel baLB;
	   MyTextField baTF;
	 //付款日期
	   JLabel payDateLB;
	   MyTextField payDateTF;
	 //付款会计
	   JLabel acLB;
	   MyTextField acTF;
	   
	 //备注
	   JLabel noticeLB;
	 //租金年份
	   JLabel yearLB;
	   MyTextField yearTF;
	 //工资月份
	   JLabel monthLB;
	   MyTextField monthTF;
     
	   String date;
	   String month;
	   String year;
	   JTable table;
	   
	   public PaymentOBPanel(PaymentVO paymentVO ){
		    
		     this.paymentVO = paymentVO;
		     this.costVO=this.paymentVO.getSystemCostVO();
		     this.setLayout(null);
		     
		     font = new Font("微软雅黑", Font.PLAIN, 18);			  
		     font1 = new Font("微软雅黑", Font.PLAIN, 20);		
		     this.initComponent();      
		     this.initTable();
             this.setVisible(true);
 	   }
      //为了统一导出格式而创建一个表格
	   public void initTable(){
		       String []  header= {"付款金额","付款账户","付款人","付款日期"};
		       String [][]data = {  { costVO.getTotalCost()+"",paymentVO.getBankAccountName(),
		    		   paymentVO.getEmployeeId(), paymentVO.getDate() }  };
		       DefaultTableModel model  = new DefaultTableModel(data ,header);
		      JTable table  = new JTable (model);
		      this.table = table;
	   }
	   
	   public void initComponent(){
		
		   initLabel();
		   initTextField();
		   initButton();
	      
	   }
	   public void initLabel(){
		   entryLB = new JLabel("条目:");
		   entryLB.setFont(font);
		   rentLB =  new JLabel("租金");
		   
		                                                          
		   rentLB.setFont(font);
		   freightLB = new JLabel("运费");
		   freightLB.setFont(font);
		   salaryLB = new JLabel("工资");
		   salaryLB.setFont(font);
		   rewardLB = new JLabel("奖金");
		   rewardLB.setFont(font);
		   
		    summaryLB = new JLabel("小结:");
		    summaryLB.setFont(font);
		    totalFeeLB = new JLabel("付款金额");
		    totalFeeLB.setFont(font);
		    payDateLB = new JLabel("付款日期");
		    payDateLB.setFont(font);
		    baLB = new JLabel("付款账户");
		    baLB.setFont(font);
		    acLB = new JLabel("付款人");
		    acLB.setFont(font);
		    
		    noticeLB = new JLabel("备注:");
		    noticeLB.setFont(font);
		    yearLB = new JLabel("租金年份");
		    yearLB.setFont(font);
		    monthLB = new JLabel("工资月份");
		    monthLB.setFont(font);

		   
	        
	        
	        entryLB.setBounds(initX,initY,LBW,LBH);
	        
	        rentLB.setBounds(itemX,itemY,LBW,LBH);               

	        freightLB.setBounds(itemX1, rentLB.getY(), LBW, LBH);
	        salaryLB.setBounds(rentLB.getX(),rentLB.getY()+LBH+VGap,LBW,LBH);
	        rewardLB.setBounds(freightLB.getX(),salaryLB.getY(),LBW,LBH);
	        
	        summaryLB.setBounds(initX,salaryLB.getY()+LBH+VGap,LBW,LBH);
	 
	        totalFeeLB.setBounds(itemX,summaryLB.getY()+LBH,LBW*2,LBH);      
	        payDateLB.setBounds(itemX1,totalFeeLB.getY(),	LBW*2,LBH);
	        baLB.setBounds(itemX,totalFeeLB.getY()+LBH+VGap,LBW*2,LBH);
	        acLB.setBounds(payDateLB.getX(),baLB.getY(),LBW*2,LBH);
	        
	        noticeLB.setBounds(initX,acLB.getY()+LBH+VGap,LBW,LBH);
	        yearLB.setBounds(itemX,noticeLB.getY()+LBH,LBW*2,LBH);
	        monthLB.setBounds(itemX,yearLB.getY()+LBH,LBW*2,LBH);

          this.add(entryLB);
          this.add(rentLB);       
          this.add(freightLB);
          this.add(salaryLB);
          this.add(rewardLB);
          this.add(summaryLB);
          this.add(totalFeeLB);
          this.add(payDateLB);
          this.add(baLB);
          this.add(acLB);
          this.add(noticeLB);
          this.add(yearLB);
          this.add(monthLB);

	   }
	   
	   public void initTextField(){
		   rentTF =  new MyTextField(costVO.getTotalRent()+"");
			
		   freightTF = new MyTextField(costVO.getTotalFreight()+"");

		   salaryTF = new MyTextField(costVO.getTotalSalary()+"");

		   rewardTF = new MyTextField(costVO.getTotalReward()+"");

		   

		    totalFeeTF = new MyTextField(costVO.getTotalCost()+"");
	
		    payDateTF = new MyTextField(paymentVO.getDate());
	       
		    baTF = new MyTextField(paymentVO.getBankAccountName());
		  
		    acTF = new MyTextField(paymentVO.getEmployeeId());

		    yearTF = new MyTextField(costVO.getYear());

		    monthTF = new MyTextField(costVO.getMonth());
	     
	        
		    rentTF.setBounds(TFX,TFY,TFW,TFH);               
	        freightTF.setBounds(TFX1, rentTF.getY(), TFW, TFH);
	        salaryTF.setBounds(rentTF.getX(),rentTF.getY()+TFH+VGap,TFW,TFH);
	        rewardTF.setBounds(freightTF.getX(),salaryTF.getY(),TFW,TFH);
	        
	       
	 
	        totalFeeTF.setBounds(TFX,totalFeeLB.getY(),TFW,TFH);      
	        payDateTF.setBounds(TFX1,totalFeeTF.getY(),TFW,TFH);
	        baTF.setBounds(TFX,baLB.getY(),TFW,TFH);
	        acTF.setBounds(TFX1,baTF.getY(),TFW,TFH);
	        
	       
	        yearTF.setBounds(TFX,yearLB.getY(),TFW,TFH);
	        monthTF.setBounds(TFX,monthLB.getY(),TFW,TFH);

	        
      
          this.add(rentTF);       
          this.add(freightTF);
          this.add(salaryTF);
          this.add(rewardTF);

          this.add(totalFeeTF);
          this.add(payDateTF);
          this.add(baTF);
          this.add(acTF);

          this.add(yearTF);
          this.add(monthTF);
	   }
	   
	   public void initButton(){
		   backBT = new BackButton();
	        backBT.setBounds(panelW/2-LBW-20,panelH-40-LBH,LBW,LBH);
	        backBT.addMouseListener(new MouseAdapter (){
	     		   
	 				public void mouseClicked(MouseEvent e){
					   System.out.println("should change now");
					   UIController.changePanel("paymentMainPanel");
				}
  	       });
          this.add( backBT);
          
          
		   exportBT = new ExportButton();
		   exportBT.setBounds(panelW/2+20,backBT.getY(),LBW,LBH);
		   exportBT.addMouseListener(new MouseAdapter (){
     		   
 				public void mouseClicked(MouseEvent e){
					      String path  =   ExportExcelUtil.getPath();
					      ExportExcelUtil.exportExcel(table, path);
				}
 	       });
         this.add(  exportBT );
	   }
}
