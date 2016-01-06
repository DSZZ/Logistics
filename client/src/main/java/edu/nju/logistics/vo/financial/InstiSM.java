package edu.nju.logistics.vo.financial;

import java.io.Serializable;

/**
 * 期初建账中使用到的
 * @author   侍硕
 *PS：SM代表的是ShortMessage
 */
public class InstiSM  implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//机构编号
      private  String InstiID;
       //地理位置
      private  String Location;
       //员工数（营业厅包括业务员和司机）
       private int  numOfStaff;
       
       public InstiSM(String id,String location , int num){
    	      this.setInstiID(id);
    	      this.setLocation(location);
    	      this.setNumOfStaff(num);
       }

	public String getInstiID() {
		return InstiID;
	}

	public void setInstiID(String instiID) {
		InstiID = instiID;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public int getNumOfStaff() {
		return numOfStaff;
	}

	public void setNumOfStaff(int numOfStaff) {
		this.numOfStaff = numOfStaff;
	}
       
       
       
       
}
