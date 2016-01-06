package edu.nju.logistics.init;

import edu.nju.logistics.ui.courier.CourierFrame;
import edu.nju.logistics.vo.UserVO;

/**
 * @author Zhuyuxiang
 * 快递员的main方法，暂时示意一下，以后再链接到登陆用例
 */
public class CourierLauncher {

	public static void main(String[] args){
		new CourierFrame(new UserVO("0250007","000"));
	}
}
