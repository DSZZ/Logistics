package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.impl.operationmanagement.OperationManagementController;
import edu.nju.logistics.bl.service.finacialmanagement.LogBLService;
import edu.nju.logistics.bl.service.operationmanagement.LogCheck;
import edu.nju.logistics.vo.LogVO;

public class LogController implements LogBLService{

	private LogCheck log=null;
	
	public LogController (OperationManagementController log){
		   this.log = log;
	}
	
	@Override
	public ArrayList<LogVO> checkSystemLog(String date) throws RemoteException {
		return this.log.checkSystemLog(date);
	}

}
