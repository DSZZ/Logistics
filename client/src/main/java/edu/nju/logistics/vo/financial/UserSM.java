package edu.nju.logistics.vo.financial;

import java.io.Serializable;

/**
 * 期初建账中使用到的
 * @author   侍硕
 *PS：SM代表的是ShortMessage
 */
public class UserSM  implements Serializable {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  String userID;
      private String insitID;
      private  String name;
      private String role;
      
      public UserSM(String id ,String instiID,String name ,String role){
    	     this.setUserID(id);
    	     this.setInsitID(instiID);
    	     this.setName(name);
    	     this.setRole(role);
      }

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getInsitID() {
		return insitID;
	}

	public void setInsitID(String insitID) {
		this.insitID = insitID;
	}
}
