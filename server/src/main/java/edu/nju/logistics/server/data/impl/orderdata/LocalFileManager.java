package edu.nju.logistics.server.data.impl.orderdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.data.service.institutiondata.InstitutionDataService;
import edu.nju.logistics.po.InstitutionPO;
import edu.nju.logistics.po.orderdata.OrderPO;

public class LocalFileManager {

	private static final String PATH = "File//orderData";
	/**
	 * 更新机构信息（即目录框架）
	 * @param institutionDataGetter
	 */
	public void updateLocalInstitution(InstitutionDataService institutionDataGetter){
		ArrayList<InstitutionPO> institutionList = null;
		try {
			institutionList = institutionDataGetter.getAll();
		} catch (RemoteException e) {e.printStackTrace();}
		
		File centerDir = new File(PATH+"//center");
		if(!centerDir.exists()){
			centerDir.mkdirs();
		}
		
		for(InstitutionPO institution : institutionList){
			if(!institution.getType().equals("中转中心")){
				File file = new File(PATH+"//branch//"+institution.getId());
				if(!file.exists()){
					file.mkdirs();
				}
			}
		}
	}
	
	/**
	 * 下一个方法的重载形式，用于批量将某个订单加入某个营业厅的某个列表
	 * @param listKind
	 * @param orderPOList
	 * @param institutionid
	 */
	public void addToBranchList(String listKind,ArrayList<OrderPO> orderPOList, String institutionid) {
		for(OrderPO order : orderPOList){
			addToBranchList(listKind,order, institutionid);
		}
	}
	/**
	 * 将某个订单加入某个营业厅的某个列表
	 * @param listKind 包括“unpay”，“to dispatch”，以及“staylist”，使用常量调用
	 * @param orderPO
	 * @param institutionid
	 */
	public void addToBranchList(String listKind,OrderPO orderPO, String institutionid){
		String directoryPath = PATH+"//branch//"+institutionid+"//"+listKind;
		File file = new File(directoryPath);
		if(!file.exists()){
			file.mkdirs();
		}
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(directoryPath+"//"+orderPO.getOrderVO().getOrderID()+".order"));
			out.writeObject(orderPO);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	/**
	 * 获得某个营业厅的某个列表
	 * @param listKind
	 * @param institutionid
	 * @return
	 */
	public ArrayList<OrderPO> getBranchList(String listKind,String institutionid){
		String directoryPath = PATH+"//branch//"+institutionid+"//"+listKind;
		File file = new File(directoryPath);
		if(!file.exists()){
			file.mkdirs();
		}
		File f[] = file.listFiles();
		
		ArrayList<OrderPO> orderList = new ArrayList<>();
		
		for(File temp : f){
			if(temp.getName().endsWith(".order")){
				ObjectInputStream in;
				try {
					in = new ObjectInputStream(
							 new FileInputStream(temp));
					orderList.add((OrderPO)in.readObject());
					in.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					temp.delete();
				}
			}
			
		}
		
		return orderList;
	}

	/**
	 * 更新某个营业厅的快递员列表，即更新营业厅目录下的目录组织
	 * @param courierIDList
	 * @param institutionid
	 */
	public void updateCourierList(ArrayList<String> courierIDList,
			String institutionid) {
		String directoryPath = PATH+"//branch//"+institutionid+"//"+"courier";
		File file = new File(directoryPath);
		if(!file.exists()){
			file.mkdirs();
		}
		
		/* 删除不存在的courierID */
		File f[] = file.listFiles();
		for(File temp : f){
			if(!courierIDList.contains(temp.getName())){
				temp.delete();
			}
		}
		
		for(String courierID : courierIDList){
			File temp = new File(directoryPath+"//"+courierID);
			if(!temp.exists()){
				temp.mkdirs();
			}
		}
	}

	/**
	 * 以某个快递员ID和营业厅ID为参数，获取快递员的待派送列表
	 * @param courierID
	 * @param institutionID
	 * @return
	 */
	public ArrayList<OrderPO> getCourierOrderList(String courierID,
			String institutionID) {
		String directoryPath = PATH+"//branch//"+institutionID+"//courier//"+courierID;
		
		File file = new File(directoryPath);
		if(!file.exists()){
			file.mkdirs();
		}
		File f[] = file.listFiles();
		
		ArrayList<OrderPO> orderList = new ArrayList<>();
		
		for(File temp : f){
			if(temp.getName().endsWith(".order")){
				ObjectInputStream in;
				try {
					in = new ObjectInputStream(
							 new FileInputStream(temp));
					orderList.add((OrderPO)in.readObject());
					in.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					temp.delete();
				}
			}
			
		}
		
		return orderList;
	}

	public void addOrderToCourierOrderList(ArrayList<OrderPO> orderPOList,
			String courierID, String institutionID) {
		
		String directoryPath = PATH+"//branch//"+institutionID+"//courier//"+courierID;
		File file = new File(directoryPath);
		if(!file.exists()){
			file.mkdirs();
		}
		try {
			for(OrderPO orderPO: orderPOList){
				ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(directoryPath+"//"+orderPO.getOrderVO().getOrderID()+".order"));
				out.writeObject(orderPO);
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void deleteFromBranchList(String listKind, String orderID,
			String institutionid) {
		String directoryPath = PATH+"//branch//"+institutionid+"//"+listKind;
		File file = new File(directoryPath);
		if(!file.exists()){
			file.mkdirs();
		}
		File f[] = file.listFiles();
		
		for(File temp : f){
			if(temp.getName().endsWith(".order")){
				ObjectInputStream in;
				try {
					in = new ObjectInputStream(
							 new FileInputStream(temp));
					OrderPO order = (OrderPO)in.readObject();
					in.close();
					if(order.getOrderVO().getOrderID().equals(orderID)){
						temp.delete();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					temp.delete();
				}
			}
			
		}
	}

	public void deleteFromCourierList(String courierid, String orderID,
			String institutionid) {
		String directoryPath = PATH+"//branch//"+institutionid+"//courier//"+courierid;
		File file = new File(directoryPath);
		if(!file.exists()){
			file.mkdirs();
		}
		File f[] = file.listFiles();
		
		for(File temp : f){
			if(temp.getName().endsWith(".order")){
				ObjectInputStream in;
				try {
					in = new ObjectInputStream(
							 new FileInputStream(temp));
					OrderPO order = (OrderPO)in.readObject();
					in.close();
					if(order.getOrderVO().getOrderID().equals(orderID)){
						temp.delete();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					temp.delete();
				}
			}
			
		}
		
	}


}
