package edu.nju.logistics.server.data.impl.orderdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import edu.nju.logistics.data.service.orderdata.OrderDataService;
import edu.nju.logistics.po.orderdata.OrderPO;
import edu.nju.logistics.po.orderdata.OrderStatus;

public class GlobalOrderManager {

	private static final String PATH = "File//orderData";
	
	public static OrderPO getOrder(String id) {
		String absolutePath = GlobalOrderSeacher.getOrderFullPath(id, PATH);
		if(absolutePath == null)
			return null;
		File orderFile = new File(absolutePath);
		
		ObjectInputStream in;
		OrderPO tempOrder = null;
		
		try {
			in = new ObjectInputStream(
					 new FileInputStream(orderFile));
			tempOrder = (OrderPO)in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return tempOrder;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			orderFile.delete();
		}
		
		System.out.println("path : " + absolutePath);
		System.out.println("orderid : " + tempOrder.getOrderID());
		System.out.println("order status : " + tempOrder.getOrderStatus());
		System.out.println("\n");
		
		return tempOrder;
	}
	
	/**
	 *  destination为目标机构的id，中转中心为六位，营业厅为八位
	 * @param orderid
	 * @param destinationid
	 */
	public static void moveTo(String orderid, String destinationid){
		String orderPath = GlobalOrderSeacher.getOrderFullPath(orderid, PATH);
		if(destinationid.equals(OrderDataService.branchCloud)){
			GlobalOrderSeacher.move(orderPath, PATH + "//branchCloud");
		}else if(destinationid.length() == 6)
			GlobalOrderSeacher.move(orderPath, PATH + "//center");
		else
			GlobalOrderSeacher.move(orderPath, PATH + "//branch//" + destinationid + "//" + OrderDataService.STAYLIST);
	}
	
	/**
	 *  destination为目标机构的id，营业厅为八位
	 * @param orderid
	 * @param destinationid
	 */
	public static void moveToCourierList(String orderid, String destinationid,String courierid){
		String orderPath = GlobalOrderSeacher.getOrderFullPath(orderid, PATH);

		GlobalOrderSeacher.move(orderPath, PATH + "//branch//" + destinationid +"//courier//" + courierid);
	}
	
	public static void replace(String orderid, OrderPO newOrder){
		String orderPath = GlobalOrderSeacher.getOrderFullPath(orderid, PATH);
		
		File file = new File(orderPath);
		try {
			file.delete();
			file.createNewFile();
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(file));
			out.writeObject(newOrder);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		OrderPO order = getOrder("0001115555");
		order.changeStatusTo(OrderStatus.senderBranch);
		replace(order.getOrderID(),order);
		getOrder("0001115555");
		
		order.changeStatusTo(OrderStatus.dispatching);
		replace(order.getOrderID(),order);
		getOrder("0001115555");
	}

}
