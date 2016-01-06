package edu.nju.logistics.ui.accountant.accountinit;

import java.util.ArrayList;
import java.util.Vector;

import edu.nju.logistics.vo.financial.InstiSM;
import edu.nju.logistics.vo.financial.UserSM;

public class UserTableModel extends SuperTableModel{
	
		
        /**
 * 
 */
private static final long serialVersionUID = 1L;
		private ArrayList<UserSM> userSMList;
        
        public   UserTableModel(ArrayList<UserSM> userSMList){
        	this.userSMList = userSMList;
        	 initModel();
        }
        public void initModel(){
			title.add("员工编号");
			title.add("员工姓名");
			title.add("员工职务");
			title.add("所属机构编号");
			
			for(int i = 0; i< userSMList.size();i++){
				Vector<String> temp = new Vector<String> ();
				temp.add(userSMList.get(i).getUserID());
				temp.add(userSMList.get(i).getName());
				temp.add(userSMList.get(i).getRole());
				temp.add(userSMList.get(i).getInsitID());
				data.add(temp);
			}
		}
        
        


}
