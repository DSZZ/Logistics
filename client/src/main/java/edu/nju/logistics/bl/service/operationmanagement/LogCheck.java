package edu.nju.logistics.bl.service.operationmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.vo.LogVO;
/**
 * 为财务人员查询日志的接口
 * @author 董轶波
 *
 */
public interface LogCheck {
	/**
	 * 查看日志
	 * @param date
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<LogVO> checkSystemLog(String date) throws RemoteException;

}
