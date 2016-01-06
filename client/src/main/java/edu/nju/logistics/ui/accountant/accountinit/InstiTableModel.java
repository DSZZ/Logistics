package edu.nju.logistics.ui.accountant.accountinit;

import java.util.ArrayList;
import java.util.Vector;

import edu.nju.logistics.vo.financial.InstiSM;

public class InstiTableModel extends SuperTableModel{
	
            /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
			private ArrayList<InstiSM> instiSMList;
            
            public   InstiTableModel(ArrayList<InstiSM> instiSMList){
            	this.instiSMList = instiSMList;
            	 initModel();
            }
            public void initModel(){
    			title.add("机构编号");
    			title.add("机构所在地");
    			title.add("机构人员数");
    			
    			for(int i = 0; i< instiSMList.size();i++){
    				Vector<String> temp = new Vector<String> ();
    				temp.add(instiSMList.get(i).getInstiID());
    				temp.add(instiSMList.get(i).getLocation());
    				temp.add(instiSMList.get(i).getNumOfStaff()+"");
    				data.add(temp);
    			}
    		}
            
            
}
