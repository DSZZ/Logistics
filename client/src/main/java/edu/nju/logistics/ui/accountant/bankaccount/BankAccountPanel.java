package edu.nju.logistics.ui.accountant.bankaccount;


import java.awt.Font;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import edu.nju.logistics.bl.impl.financialmanagement.BankAccountController;
import edu.nju.logistics.ui.accountant.main.FinaMainFrame;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.accountant.main.UIController;
import edu.nju.logistics.ui.checkorder.MessagePanel;
import edu.nju.logistics.ui.commonButton.AddButton;
import edu.nju.logistics.ui.commonButton.DeleteButton;
import edu.nju.logistics.ui.commonButton.SaveButton;
import edu.nju.logistics.ui.commonButton.SearchButton;

public class BankAccountPanel extends JPanel{
	
	    private static final long serialVersionUID = 1L;
		private static int showH=350;
		private static int showW=700;
		private static int LBH =35;
		private static int LBW = 80;
		private static int TFH = 35;
		private static int TFW = showW-LBW-10;
		//增加 、删除 、修改的 按钮间距
    	private static int HGap =50;
    	
    	private static int TFX=(FinaMainFrame.showW-showW)/2;
	    JTable table ;
	    BATableModel MyTablemodel;
	    JScrollPane  Spane;
	  
	    SaveButton updateBT;
	    AddButton addBT;
	    DeleteButton deleteBT;
	    SearchButton searchBT;
	    JTextField searchTF;
        MessagePanel messagePanel;
        JLabel PSLB;
	     Font font;
	    public BankAccountPanel (BankAccountController controller){
 	 
	    	 
	    	  this.setLayout(null);
	    	  
	    	  
	    	  MyTablemodel = new BATableModel(controller);
	    	  table = new JTable(MyTablemodel);
	    	  Spane = new JScrollPane(table);
	    	  
	    	  
	          MyTableHandler.initTable(table);
	          MyTableHandler.initSpane(Spane, table);
	          
	          initComponent();
	          Spane.setBounds(TFX,LBH+10,showW,showH);
	          this.add(Spane);
	  		  

	    }
	    public void initComponent(){
	    	font = new Font("黑体", Font.PLAIN, 18);
	    	messagePanel = new MessagePanel("", "a");
	    	addBT = new  AddButton();
	    	deleteBT= new DeleteButton();
	    	updateBT = new SaveButton();
	    	
	        searchBT= new SearchButton();
	        searchTF= new JTextField();
	        
	        searchTF.setBounds(TFX, 0, TFW, TFH);
	        searchBT.setBounds(searchTF.getX()+TFW+10,searchTF.getY(),LBW,LBH);
            
	        deleteBT.setBounds((FinaMainFrame.showW-LBW)/2,showH+TFH+50,LBW,LBH);
	        addBT.setBounds(deleteBT.getX()-HGap-LBW,deleteBT.getY(),LBW,LBH);
	        updateBT.setBounds(deleteBT.getX()+LBW+HGap,deleteBT.getY(),LBW,LBH);
	        messagePanel.setBounds(updateBT.getX()+LBW+10,updateBT.getY(),200,40);
	        
	       
	          searchBT.addMouseListener(new MouseAdapter(){
	          		public void mouseClicked(MouseEvent e){
	          			 String key = searchTF.getText();
	         	        MyTablemodel.UpdateVOListByKey(key);
          		}
           	});
	          
	          addBT.addMouseListener(new MouseAdapter(){
	          		public void mouseClicked(MouseEvent e){
	          			    UIController.changePanel("bankAccountAddPanel");
	          		}
	           	});
	          
	          deleteBT.addMouseListener(new MouseAdapter(){
	          		public void mouseClicked(MouseEvent e){
	          			int row = table.getSelectedRow();
	        			if(row == -1){				
	        				System.out.println("请选择要删除的行!");		
	        			}else{		
	        					MyTablemodel.removeRow(row);				
	        			}
          		}
           	});
	          
	          updateBT.addMouseListener(new MouseAdapter(){
	          		public void mouseClicked(MouseEvent e){
	          			MyTablemodel.getController().updateData();
	          			messagePanel .setMessage("更新成功");
	          			messagePanel.showMessage();
          		}
           	});
		      
	          
	          PSLB =  new JLabel("PS:修改账户名称请双击对应的表格位置");
	          PSLB.setFont(font);
	          PSLB.setBounds(TFX,showH+10,LBW*4,LBH);
	          
	          this.add(addBT);
	          this.add(deleteBT);
	          this.add(updateBT);
	          this.add(searchBT);
	          this.add(searchTF);
	          this.add(messagePanel);
	          this.add(PSLB);
	    }
	    
	    
	    public BATableModel  getTableModel(){
	    	if(this.MyTablemodel!=null){
	    		return this.MyTablemodel;
	    	}
	    	System.out.println("BATableModel.getTableModel() tablemodel为NULL");
	    	return null;
	    }
	    

}
	     
