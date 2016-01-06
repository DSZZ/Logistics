package edu.nju.logistics.server.data.impl.identitydata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import edu.nju.logistics.data.service.identitydata.IdentityDataService;
import edu.nju.logistics.po.UserPO;
/**
 * 用户登陆数据管理
 * @author 董轶波
 *
 */
public class IdentityData extends UnicastRemoteObject implements IdentityDataService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String FILE_IDENTITY="File/admin/identity.txt";
	
	private static final String FILE_RECORD="File/admin/record.txt";
	
	private ArrayList<UserPO> po=null;
	
	public IdentityData() throws RemoteException{}
	/**
	 * 获得所有用户登陆信息
	 */
	@Override
	public ArrayList<UserPO> getAll() throws RemoteException {
		this.po=new ArrayList<UserPO>();
		BufferedReader reader=null;
		String line="";
		try {
			reader=new BufferedReader(new FileReader(FILE_IDENTITY));
			while((line=reader.readLine())!=null){
				this.handleDataFrom(line);
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
	 * 所有用户登陆信息写入文本
	 */
	@Override
	public void writeAll(ArrayList<UserPO> po) throws RemoteException {
		FileWriter writer=null;
		try {
			writer=new FileWriter(FILE_IDENTITY);
			for (int i = 0; i < po.size(); i++) {
				writer.write(this.handleDataTo(po.get(i))+"\n");
			}
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
	/**
	 * 从文本处理单行数据
	 * @param line
	 */
	private void handleDataFrom(String line) {
		String[] single=line.split("；");
		String ID=single[0];
		String password=single[1];
		String role=single[2];
		//System.out.println(id+" "+password+" "+role);
		this.po.add(new UserPO(ID, password, role));	
	}
	/**
	 * 处理单行数据到文本
	 * @param po
	 * @return
	 */
	private String handleDataTo(UserPO po) {
		String ID=po.getId();
		String password=po.getPassword();
		String role=po.getRole();
		String result=ID+"；"+password+"；"+role;
		return result;
	}
	/**
	 * 记录登录成功的用户
	 * @param ID
	 * @throws RemoteException
	 */
	@Override
	public void record(String ID) throws RemoteException {
		FileWriter writer=null;
		try{
			writer=new FileWriter(FILE_RECORD,true);
			writer.write(ID+"\n");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	/**
	 * 得到当前所有已在线用户
	 * @return
	 * @throws RemoteException
	 */
	@Override
	public ArrayList<String> getCurrentLoginUsers() throws RemoteException {
		ArrayList<String> userList=new ArrayList<String>();
		BufferedReader reader=null;
		String line="";
		try{
			reader=new BufferedReader(new FileReader(FILE_RECORD));
			while((line=reader.readLine())!=null){
				userList.add(line);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return userList;
	}
	/**
	 *更新登录信息
	 */
	@Override
	public void updateLoginUsers(ArrayList<String> list) throws RemoteException {
		FileWriter writer=null;
		try{
			writer=new FileWriter(FILE_RECORD);
			for (int i = 0; i < list.size(); i++) {
				writer.write(list.get(i)+"\n");
			}
		}catch(Exception e){
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
