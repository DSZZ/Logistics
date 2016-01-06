package edu.nju.logistics.server.data.impl.logdata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import edu.nju.logistics.data.service.logdata.LogDataService;
import edu.nju.logistics.po.LogPO;

public class LogData extends UnicastRemoteObject implements LogDataService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String FILE="File/manager/log.txt";
	
	private ArrayList<LogPO> po=null;
	
	public LogData() throws RemoteException {}

	@Override
	public ArrayList<LogPO> getLog(String date) throws RemoteException {
		this.po=new ArrayList<LogPO>();
		BufferedReader reader=null;
		String line="";
		try {
			reader=new BufferedReader(new FileReader(FILE));
			while((line=reader.readLine())!=null){
				this.handleDataFrom(line,date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this.po;

	}
	/**
	 * 从文本处理数据
	 * @param line
	 */
	private void handleDataFrom(String line,String date) {
		String single[]=line.split("；");
		String employeeID = null;
		String content = null;

		String temp[]=single[0].split(" ");
		
		if(date.equals(temp[0])){
		employeeID=single[1];
		content=single[2];
		LogPO logPO=new LogPO(single[0], employeeID, content);
		this.po.add(logPO);
		}
		
	}

	@Override
	public void insert(LogPO po) throws RemoteException {
		FileWriter writer=null;
		try {
			writer=new FileWriter(FILE,true);
			writer.write(po.getDate()+"；"+po.getEmployeeID()+"；"+po.getContent()+"\n");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	
		
	}

}
