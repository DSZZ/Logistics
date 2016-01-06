package edu.nju.logistics.bl.impl.log;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.operationmanagement.InsertLogService;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.logdata.LogDataService;
import edu.nju.logistics.po.LogPO;
import edu.nju.logistics.vo.LogVO;

/**
 * 总经理与财务人员读取日志，其他人员记录日志
 * 
 * @author 董轶波
 *
 */
public class LogBL implements InsertLogService{
	/**
	 * 日志数据
	 */
	private LogDataService logDataService = null;

	private ArrayList<LogPO> po = null;

	public LogBL() throws RemoteException{
			try {
				this.logDataService = (LogDataService) Naming.lookup("rmi://"+RMI.getIP()+":2014/LogData");
			} catch (MalformedURLException | NotBoundException e) {
				e.printStackTrace();
			}
	}

	/**
	 * 读取日志
	 * 
	 * @param date
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<LogPO> getSystemLog(String date) throws RemoteException {
			this.po = this.logDataService.getLog(date);
		return this.po;
	}

	/**
	 * 记录日志
	 * 
	 * @param vo
	 * @throws RemoteException 
	 */
	public void insert(LogVO vo) throws RemoteException {
		LogPO temp = new LogPO(vo.getDate(), vo.getEmployeeID(), vo.getContent());

		this.logDataService.insert(temp);
	}

}
