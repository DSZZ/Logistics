package edu.nju.logistics.bl.service.operationmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.InstitutionPO;
import edu.nju.logistics.po.UserPO;
import edu.nju.logistics.vo.UserVO;

/**
 * 本接口提供人员列表，机构列表，距离价格等常量
 * @author 董轶波
 *
 */
public interface EnvironmentGetter {
	/**
	 * 获得所有的机构（中转中心和营业厅）
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<InstitutionPO> getAllInstitution() throws RemoteException;
	/**
	 * 获得该营业厅的所有快递员列表
	 * @param id
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<UserVO> getCourierList(String id) throws RemoteException;
	/**
	 * 获得所有员工列表
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<UserPO> getAllUser() throws RemoteException;
	/**
	 * 获得所有的距离常量
	 * @return
	 * @throws RemoteException 
	 */
	public double getDistance(String city1,String city2) throws RemoteException;
	/**
	 * 获得所有已增加城市的名字
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<String> getAllCities() throws RemoteException;
	/**
	 * 获取城市已创建中转中心的所有ID
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<String> getCentersID(String city) throws RemoteException;
	/**
	 * 获取给出的中转中心ID对应的名字
	 * @param ID
	 * @return
	 * @throws RemoteException 
	 */
	public String getCenterName(String ID) throws RemoteException;
	/**
	 * 获取给出的中转中心ID对应的营业厅的ID
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<String> getBranchesID(String ID) throws RemoteException;
	/**
	 * 获取给出的营业厅ID对应的名字
	 * @param ID
	 * @return
	 * @throws RemoteException 
	 */
	public String getBranchName(String ID) throws RemoteException;
	/**
	 * 获得制定服务的价格
	 * 服务名：汽车，火车，飞机，标准快递运费价格，标准快递的最低运费(共五个)
	 * @return
	 * @throws RemoteException 
	 */
	public double getServicePrice(String name) throws RemoteException;
	/**
	 * 获取详细地址条目
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<String> getListToShow(String centerID) throws RemoteException;
	/**
	 * 获得薪水策略
	 * 输入 快递员提成  或 司机提成 获得他们的单次提成策略
	 * 输入 快递员 司机 营业厅业务员 中转中心业务员 中转中心库存管理员 获得他们的月薪
	 * @throws RemoteException 
	 */
	public double getSalaryStrategy(String name) throws RemoteException; 
	/**
	 * 根据用户ID获取其所属机构ID
	 * @param ID
	 * @return
	 * @throws RemoteException 
	 */
	public String getUserInstitutionID(String ID) throws RemoteException;
	/**
	 * 根据用户ID获取其所属机构所属城市
	 * @param ID
	 * @return
	 * @throws RemoteException 
	 */
	public String getUserInstitutionCity(String ID) throws RemoteException;
	/**
	 * 设置员工被付款的月份
	 * @param usrID
	 * @param month
	 * @throws RemoteException 
	 */
	public void setLastPaymentMonth(String userID,String month) throws RemoteException;
	/**
	 * 设置机构被付款的年份
	 * @param insID
	 * @param year
	 * @throws RemoteException 
	 */
	public void setLastPaymentYear(String insID,String year) throws RemoteException;
	/**
	 * 根据名字得到机构ID
	 * @param name
	 * @return
	 * @throws RemoteException 
	 */
	public String getInstitutionID(String name) throws RemoteException;
	
	public static void main(String[] args) {
		//EnvironmentGetter getter=new OperationManagementController();
		//System.out.println(getter.getAllCities());
		//System.out.println(getter.getCentersID("北京市"));
//		System.out.println(getter.getCenterName("002100"));
//		System.out.println(getter.getBranchName("00200000"));
		//System.out.println(getter.getBranchesID("002100"));
//		ArrayList<String> temp=getter.getListToShow("002500");
//		for (int i = 0; i < temp.size(); i++) {
//			System.out.println(temp.get(i));
//		}
		//getter.setLastPaymentYear("001000", "2015");
		//getter.setLastPaymentMonth("1111111", "2");
	//System.out.println(getter.getUserInstitutionID("1111111"));
	//System.out.println(getter.getUserInstitutionCity("1111111"));
		//System.out.println(getter.getInstitutionID("一却公园"));
		System.out.println("success");
	}
}
