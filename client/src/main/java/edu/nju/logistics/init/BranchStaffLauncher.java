package edu.nju.logistics.init;

import edu.nju.logistics.ui.branchstaff.BranchFrame;
import edu.nju.logistics.vo.UserVO;

public class BranchStaffLauncher {

	public static void main(String[] args){
		new BranchFrame(new UserVO("0250010","000"));
//		new BranchFrame(new UserVO("0000000","000"));
		 
	}
}
