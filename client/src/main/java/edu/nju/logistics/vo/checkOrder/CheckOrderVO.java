package edu.nju.logistics.vo.checkOrder;

public class CheckOrderVO {

	public enum OrderStatus{
		received/*已收件*/,
		branch2center,
		center2branch,
		dispatching/*正派件*/
	}
	private OrderStatus orderStatus;

	private String[] HistoryTrace;
	
	public CheckOrderVO(OrderStatus orderStatus,String[] HistoryTrace) {
		this.orderStatus = orderStatus;
		this.HistoryTrace = HistoryTrace;
	}
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	
	public String[] getHistoryTrace() {
		return HistoryTrace;
	}
}
