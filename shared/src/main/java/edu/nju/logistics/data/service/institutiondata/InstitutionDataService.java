package edu.nju.logistics.data.service.institutiondata;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.InstitutionPO;

public interface InstitutionDataService extends Remote{
	/**
	 * 获得所有机构PO
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<InstitutionPO> getAll() throws RemoteException;
	/**
	 * 所有机构信息写入文本
	 * @param po
	 * @throws RemoteException
	 */
	public void writeAll(ArrayList<InstitutionPO> po) throws RemoteException;
	/**
	 * 机构ID与名字写入文本
	 * @param line
	 */
	public void writeName(String line)throws RemoteException;
	/**
	 * 删除机构名字
	 * @param id
	 */
	public void deleteName(String id)throws RemoteException;
	/**
	 * 通过ID得到机构名字
	 * @param id
	 * @return
	 */
	public String getName(String id)throws RemoteException;
	/**
	 * 通过名字的道机构ID
	 * @param name
	 * @return
	 */
	public String getID(String name)throws RemoteException;
	/**
	 * 得到所有的机构名字
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<String> getAllNames() throws RemoteException;
}
