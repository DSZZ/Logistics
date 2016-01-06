package edu.nju.logistics.ui.accountant.accountinit;

import java.util.ArrayList;
import java.util.Vector;

import edu.nju.logistics.vo.financial.DriverSM;

public class DriverTableModel extends SuperTableModel{
	
	
    /**
* 
*/
private static final long serialVersionUID = 1L;
	private ArrayList<DriverSM> driverSMList;
    
    public   DriverTableModel(ArrayList<DriverSM> driverSMList){
    	this.driverSMList = driverSMList;
    	 initModel();
    }
    public void initModel(){
		title.add("司机编号");
		title.add("司机姓名");
		title.add("所属机构编号");
		
		for(int i = 0; i< driverSMList.size();i++){
			Vector<String> temp = new Vector<String> ();
			temp.add(driverSMList.get(i).getDriverID());
			temp.add(driverSMList.get(i).getName());
			temp.add(driverSMList.get(i).getInstitutionID());
			data.add(temp);
		}
	}
    
    


}

