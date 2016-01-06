package edu.nju.logistics.po;

import java.io.Serializable;

/**
 * 库存缓存对象
 * @author HanzZou
 *
 */
public class BufferPO implements Serializable{

	public BufferPO(String centerID, String id, String destination, String status) {
		this.centerID = centerID;
		this.id = id;
		this.destination = destination;
		this.status = status;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 机构ID
	 */
	private String centerID;
	
	/*
	 * 快递编号
	 */
	private String id;
	
	/*
	 * 下一站目的地ID
	 */
	private String destination;
	
	/*
	 * 入库或是出库 
	 */
	private String status;

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
