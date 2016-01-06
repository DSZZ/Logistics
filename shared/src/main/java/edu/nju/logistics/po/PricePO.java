package edu.nju.logistics.po;
import java.io.Serializable;
import java.util.HashMap;

/**
 * 所有价格的PO对象
 * @author 董轶波
 */
public class PricePO implements Serializable {
	/**
	 * 存放服务与对应价格的HashMap
	 */
	private HashMap<String,Double> map;

	public PricePO(HashMap<String, Double> map) {
		this.map = map;
	}

	
	public PricePO() {
	}


	public void setMap(HashMap<String, Double> map) {
		this.map = map;
	}


	public HashMap<String, Double> getMap() {
		return map;
	}
	
}
