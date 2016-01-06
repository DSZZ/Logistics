package edu.nju.logistics.vo;
import java.util.HashMap;

/**
 * 所有价格的VO对象
 * @author 董轶波
 *
 */
public class PriceVO{
	/**
	 * 存放服务与对应价格的HashMap
	 */
	private HashMap<String,Double> map;

	public PriceVO(HashMap<String, Double> map) {
		this.map = map;
	}

	public PriceVO() {
	}

	public void setMap(HashMap<String, Double> map) {
		this.map = map;
	}

	public HashMap<String, Double> getMap() {
		return map;
	}
	
}
