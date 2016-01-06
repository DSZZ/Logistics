package edu.nju.logistics.vo;

/**
 * 缓存对象
 * @author HanzZou
 *
 */
public class BufferVO {

	/*
	 * 中转中心ID
	 */
	private String centerID;
	
	/*
	 * 快递编号
	 */
	private String id;
	
	/*
	 * 目的地
	 */
	private String destination;
	
	/*
	 * 状态
	 */
	private String status;
	
	public BufferVO(String centerID, String id, String destination, String status) {
		this.centerID = centerID;
		this.id = id;
		this.destination = destination;
		this.status = status;
	}

	public String getCenterID() {
		return centerID;
	}

	public void setCenterID(String centerID) {
		this.centerID = centerID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
