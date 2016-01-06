package edu.nju.logistics.vo;
/**
 * 所有距离的VO对象
 * @author 董轶波
 *
 */
public class DistanceVO{
	/**
	 * 城市1
	 */
	private String city1;
	/**
	 * 城市2
	 */
	private String city2;
	/**
	 * 距离
	 */
	private double distance;
	
	public DistanceVO(String city1,String city2, double distance) {
		this.city1=city1;
		this.city2=city2;
		this.distance = distance;
	}

	public String getCity1() {
		return city1;
	}

	public void setCity1(String city1) {
		this.city1 = city1;
	}

	public String getCity2() {
		return city2;
	}

	public void setCity2(String city2) {
		this.city2 = city2;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	
	
}
