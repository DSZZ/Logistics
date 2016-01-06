package edu.nju.logistics.bl.service.finacialmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.vo.LogVO;

public interface LogBLService {
	
	public ArrayList<LogVO> checkSystemLog(String date) throws RemoteException;
}
