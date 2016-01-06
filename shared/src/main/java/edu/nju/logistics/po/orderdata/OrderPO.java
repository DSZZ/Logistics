package edu.nju.logistics.po.orderdata;

import java.io.Serializable;
import java.util.ArrayList;

import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class OrderPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6897405856886914637L;
	
	
	
	public OrderPO(OrderVO orderVO,
			String createTime) {
		super();
		this.orderVO = orderVO;
		this.historyTrace = new ArrayList<>();
		this.createTime = createTime;
		this.orderStatus = OrderStatus.received;
	}

	OrderVO orderVO;

	ArrayList<String> historyTrace;
	
	String createTime;

	OrderStatus orderStatus;
	
	public void addHistoryTrace(String history){
		historyTrace.add(history);
	}
	public void changeStatusTo(OrderStatus newStatus){
		this.orderStatus = newStatus;
	}
	public OrderVO getOrderVO() {
		return orderVO;
	}
	public String getOrderID() {
		return orderVO.getOrderID();
	}

	public ArrayList<String> getHistoryTrace() {
		return historyTrace;
	}

	public String getCreateTime() {
		return createTime;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
}
