package edu.nju.logistics.data.service.constantdata;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import edu.nju.logistics.po.DistancePO;
import edu.nju.logistics.po.PricePO;

/**
 * 常量数据接口
 * @author 董轶波
 *
 */
public interface ConstantDataService extends Remote{
	/**
	 * 获得薪水策略数据
	 * @return
	 */
	public HashMap<String,Double> getSalaryStrategy()throws RemoteException;
	/**
	 * 薪水策略写入文本
	 * @param map
	 */
	public void writeSalaryStrategy(HashMap<String,Double> map)throws RemoteException;
	/**
	 * 获得所有的距离数据
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<DistancePO> getAllDistance() throws RemoteException;
	/**
	 * 所有的距离常量写入文本
	 * @param po
	 * @throws RemoteException
	 */
	public void writeAll(ArrayList<DistancePO> po) throws RemoteException;
	/**
	 * 获得所有的价格数据
	 * @return
	 * @throws RemoteException
	 */
	public PricePO getAllPrice() throws RemoteException;
	/**
	 * 所有的价格常量写入文本
	 * @param po
	 * @throws RemoteException
	 */
	public void writeAll(PricePO po) throws RemoteException;
}
