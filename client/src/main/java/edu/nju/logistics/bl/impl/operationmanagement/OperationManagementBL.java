package edu.nju.logistics.bl.impl.operationmanagement;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import edu.nju.logistics.bl.impl.examinebl.ExamineBL;
import edu.nju.logistics.bl.impl.financialmanagement.BalanceChartBL;
import edu.nju.logistics.bl.impl.financialmanagement.OperationStatusBL;
import edu.nju.logistics.bl.impl.identitymanagement.IdentityManagementController;
import edu.nju.logistics.bl.impl.log.LogBL;
import edu.nju.logistics.bl.service.finacialmanagement.BalanceChartBLService;
import edu.nju.logistics.bl.service.finacialmanagement.OperationStatusBLService;
import edu.nju.logistics.bl.service.identitymanagement.LoginUserList;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.constantdata.ConstantDataService;
import edu.nju.logistics.data.service.employeedata.EmployeeDataService;
import edu.nju.logistics.data.service.institutiondata.InstitutionDataService;
import edu.nju.logistics.po.DistancePO;
import edu.nju.logistics.po.InstitutionPO;
import edu.nju.logistics.po.LogPO;
import edu.nju.logistics.po.PricePO;
import edu.nju.logistics.po.SheetPO;
import edu.nju.logistics.po.UserPO;
import edu.nju.logistics.vo.DistanceVO;
import edu.nju.logistics.vo.InstitutionVO;
import edu.nju.logistics.vo.LogVO;
import edu.nju.logistics.vo.PriceVO;
import edu.nju.logistics.vo.SheetVO;
import edu.nju.logistics.vo.UserVO;
import edu.nju.logistics.vo.courierIncomeReceipt.ReceiptInfo;
import edu.nju.logistics.vo.financial.BalanceChartVO;
import edu.nju.logistics.vo.financial.PaymentInfo;

public class OperationManagementBL {
	/**
	 * 人事资料数据
	 */
	private EmployeeDataService employeeDataService = null;
	/**
	 * 机构数据
	 */
	private InstitutionDataService institutionDataService = null;
	/**
	 * 常量数据
	 */
	private ConstantDataService constantDataService = null;
	/**
	 * 封装日志的方法
	 */
	private LogBL log = null;
	/**
	 * 封装单据的方法
	 */
	private ExamineBL examine = null;
	/**
	 * 获得管理员的员工登录信息
	 */
	private LoginUserList loginUserList = null;
	/**
	 * 获得成本收益表
	 */
	private BalanceChartBLService balanceChartBLService = null;
	/**
	 * 获得经营情况表
	 */
	private OperationStatusBLService situationReportBLService = null;

	private ArrayList<UserPO> userPO = null;

	private ArrayList<InstitutionPO> institutionPO = null;

	private ArrayList<SheetPO> sheetPO = null;

	private ArrayList<LogPO> logPO = null;

	private ArrayList<DistancePO> distancePO = null;

	private PricePO pricePO = null;

	private HashMap<String, Double> salaryMap = null;

	public OperationManagementBL() throws RemoteException {
		try {
			this.employeeDataService = (EmployeeDataService) Naming
					.lookup("rmi://" + RMI.getIP() + ":2014/EmployeeData");
			this.institutionDataService = (InstitutionDataService) Naming
					.lookup("rmi://" + RMI.getIP() + ":2014/InstitutionData");
			this.constantDataService = (ConstantDataService) Naming
					.lookup("rmi://" + RMI.getIP() + ":2014/ConstantData");
			this.log = new LogBL();
			this.examine = new ExamineBL();
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据机构名字得到ID
	 * 
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	public String getInstitutionID(String name) throws RemoteException {
		String id = "";
		InstitutionNameOperation operate = new InstitutionNameOperation(institutionDataService);
		id = operate.getID(name);
		return id;
	}

	/**
	 * 设置员工被付款的月份
	 * 
	 * @param usrID
	 * @param month
	 * @throws RemoteException
	 */
	public void setLastPaymentMonth(String userID, String month) throws RemoteException {
		this.userPO = this.employeeDataService.getAll();
		for (int i = 0; i < this.userPO.size(); i++) {
			UserPO temp = this.userPO.get(i);
			if (userID.equals(temp.getId())) {
				temp.setMonth(month);
			}
		}
		this.employeeDataService.writeAll(this.userPO);

	}

	/**
	 * 设置机构被付款的年份
	 * 
	 * @param insID
	 * @param year
	 * @throws RemoteException
	 */
	public void setLastPaymentYear(String insID, String year) throws RemoteException {
		this.institutionPO = this.institutionDataService.getAll();
		for (int i = 0; i < this.institutionPO.size(); i++) {
			InstitutionPO temp = this.institutionPO.get(i);
			if (temp.getId().equals(insID)) {
				temp.setYear(year);
			}
		}
		this.institutionDataService.writeAll(this.institutionPO);
	}

	/**
	 * 获得所有的机构
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<InstitutionPO> getAllInstitution() throws RemoteException {
		this.institutionPO = this.institutionDataService.getAll();
		return this.institutionPO;
	}

	/**
	 * 根据用户ID获取其所属机构所属城市
	 * 
	 * @param ID
	 * @return
	 * @throws RemoteException
	 */
	public String getUserInstitutionCity(String ID) throws RemoteException {
		String city = "";
		this.userPO = this.employeeDataService.getAll();
		for (int i = 0; i < this.userPO.size(); i++) {
			UserPO temp = this.userPO.get(i);
			if (temp.getId().equals(ID)) {
				city = temp.getInstitution().getLocation();
			}
		}

		return city;

	}

	/**
	 * 根据用户ID获取其所属机构
	 * 
	 * @param ID
	 * @return
	 * @throws RemoteException
	 */
	public String getUserInstitutionID(String ID) throws RemoteException {
		String insID = "";
		this.userPO = this.employeeDataService.getAll();
		for (int i = 0; i < this.userPO.size(); i++) {
			UserPO temp = this.userPO.get(i);
			if (temp.getId().equals(ID)) {
				insID = temp.getInstitution().getId();
			}
		}

		return insID;
	}

	/**
	 * 获取详细地址条目
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<String> getListToShow(String centerID) throws RemoteException {
		ArrayList<String> show = new ArrayList<String>();

		ArrayList<String> cities = this.getAllCities();
		for (int i = 0; i < cities.size(); i++) {
			String city = cities.get(i);
			ArrayList<String> centersID = this.getCentersID(city);
			for (int j = 0; j < centersID.size(); j++) {
				String centerID_temp = centersID.get(j);
				if (!centerID_temp.equals(centerID)) {
					String centerName = this.getCenterName(centerID_temp);
					String single = city + " " + centerName + "中转中心";
					show.add(single);
				}
			}
		}
		// System.out.println(centerID+"a");
		String city = CityHelper.getCityNameByID(centerID);
		String centerName = this.getCenterName(centerID);
		ArrayList<String> branchesID = this.getBranchesID(centerID);
		for (int k = 0; k < branchesID.size(); k++) {
			String branchID = branchesID.get(k);
			String branchName = this.getBranchName(branchID);
			String single = city + " " + centerName + "中转中心" + " " + branchName + "营业厅";
			show.add(single);
		}

		return show;

	}

	/**
	 * 获取给出的中转中心ID对应的营业厅的ID
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<String> getBranchesID(String ID) throws RemoteException {
		ArrayList<String> IDs = new ArrayList<String>();
		this.institutionPO = this.institutionDataService.getAll();
		for (int i = 0; i < this.institutionPO.size(); i++) {
			InstitutionPO temp = this.institutionPO.get(i);
			if (temp.getId().substring(0, temp.getId().length() - 2).equals(ID)) {
				IDs.add(temp.getId());
			}
		}

		return IDs;
	}

	/**
	 * 获取城市已创建中转中心的所有ID
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<String> getCentersID(String city) throws RemoteException {

		if (city.substring(city.length() - 1, city.length()).equals("市")) {
			city = city.substring(0, city.length() - 1);
			// System.out.println(city);
		}
		// System.out.println(city);
		ArrayList<String> IDs = new ArrayList<String>();
		this.institutionPO = this.institutionDataService.getAll();
		for (int i = 0; i < this.institutionPO.size(); i++) {
			InstitutionPO temp = this.institutionPO.get(i);
			if (temp.getLocation().equals(city) && temp.getType().equals("中转中心")) {
				String ID = temp.getId();
				IDs.add(ID);
			}
		}
		return IDs;
	}

	/**
	 * 获取给出的中转中心ID对应的名字
	 * 
	 * @param ID
	 * @return
	 * @throws RemoteException
	 */
	public String getCenterName(String ID) throws RemoteException {
		String name = "";
		InstitutionNameOperation operate = new InstitutionNameOperation(this.institutionDataService);
		name = operate.getName(ID);
		// String city = "";
		// int id = Integer.parseInt(ID.substring(ID.length() - 2,
		// ID.length()));
		// // System.out.println(id);
		// try {
		// this.institutionPO = this.institutionDataService.getAll();
		// for (int i = 0; i < this.institutionPO.size(); i++) {
		// InstitutionPO temp = this.institutionPO.get(i);
		// if (temp.getId().equals(ID)) {
		// city = temp.getLocation();
		// }
		// }
		// name = ZoneHelper.zoneMap.get(city).get(id);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		return name;
	}

	/**
	 * 获取给出的营业厅ID对应的名字
	 * 
	 * @param ID
	 * @return
	 * @throws RemoteException
	 */
	public String getBranchName(String ID) throws RemoteException {
		// int id = Integer.parseInt(ID.substring(ID.length() - 2,
		// ID.length()));
		// String name = ZoneHelper.hallMap.get(id);
		// return name;
		String name = "";
		InstitutionNameOperation operate = new InstitutionNameOperation(this.institutionDataService);
		name = operate.getName(ID);
		return name;
	}

	/**
	 * 获得所有已设置的城市名字
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<String> getAllCities() throws RemoteException {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		ArrayList<String> city = new ArrayList<String>();
		this.institutionPO = this.institutionDataService.getAll();
		for (int i = 0; i < this.institutionPO.size(); i++) {
			InstitutionPO temp = this.institutionPO.get(i);
			String location = temp.getLocation();
			map.put(location, 0);
		}
		Set<Entry<String, Integer>> entryset = map.entrySet();
		for (Entry<String, Integer> arg : entryset) {
			city.add(arg.getKey());
		}

		return city;
	}

	/**
	 * 查看系统日志
	 * 
	 * @param date
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<LogVO> checkSystemLog(String date) throws RemoteException {
		ArrayList<LogVO> vo = new ArrayList<LogVO>();
		this.logPO = this.log.getSystemLog(date);
		for (int i = 0; i < this.logPO.size(); i++) {
			LogPO tempPO = this.logPO.get(i);
			LogVO tempVO = new LogVO(tempPO.getDate(), tempPO.getEmployeeID(), tempPO.getContent());
			vo.add(tempVO);
		}
		return vo;
	}

	/**
	 * 获得该营业厅的所有快递员列表
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<UserVO> getCourierList(String id) throws RemoteException {
		ArrayList<UserVO> voList = new ArrayList<UserVO>();
		this.userPO = this.employeeDataService.getAll();
		for (int i = 0; i < this.userPO.size(); i++) {
			UserPO temp = this.userPO.get(i);
			InstitutionPO insPO = temp.getInstitution();
			if (insPO.getId() != null) {
				if (insPO.getId().equals(id)) {
					if(temp.getRole().equals("快递员")){
					UserVO vo = new UserVO(temp.getId(), temp.getName(), temp.getRole(), null);
					voList.add(vo);
					}
				}
			}
		}
		return voList;
	}

	/**
	 * 获得两个城市之间的距离
	 * 
	 * @param city1
	 * @param city2
	 * @return
	 * @throws RemoteException
	 */
	public double getDistance(String city1, String city2) throws RemoteException {
		this.distancePO = this.constantDataService.getAllDistance();
		for (int i = 0; i < this.distancePO.size(); i++) {
			DistancePO temp = this.distancePO.get(i);
			if ((temp.getCity1().equals(city1) && temp.getCity2().equals(city2))
					|| (temp.getCity1().equals(city2) && temp.getCity2().equals(city1))) {
				return temp.getDistance();
			}
		}

		return 0;
	}

	/**
	 * 获得服务价格
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public double getServicePrice(String name) throws RemoteException {
		this.pricePO = this.constantDataService.getAllPrice();
		HashMap<String, Double> map = this.pricePO.getMap();
		Set<Entry<String, Double>> entryset = map.entrySet();
		for (Entry<String, Double> arg : entryset) {
			if (arg.getKey().equals(name)) {
				return arg.getValue();
			}
		}
		return 0;
	}

	/**
	 * 得到薪水策略
	 * 
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	public double getSalaryStrategy(String name) throws RemoteException {
		this.salaryMap = this.constantDataService.getSalaryStrategy();
		return this.salaryMap.get(name);
	}

	/**
	 * 处理新的薪水策略
	 * 
	 * @param map
	 * @throws RemoteException
	 */
	public void makeSalaryStrategy(HashMap<String, Double> map) throws RemoteException {
		this.constantDataService.writeSalaryStrategy(map);
	}

	/**
	 * 薪水策略显示
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public HashMap<String, Double> salaryShow() throws RemoteException {
		this.salaryMap = this.constantDataService.getSalaryStrategy();
		return this.salaryMap;
	}

	/**
	 * 制定距离
	 * 
	 * @param vo
	 * @throws RemoteException
	 */
	public void makeDistance(DistanceVO vo) throws RemoteException {
		this.distancePO = this.constantDataService.getAllDistance();
		for (int i = 0; i < this.distancePO.size(); i++) {
			DistancePO temp = this.distancePO.get(i);
			if (vo.getCity1().equals(temp.getCity1()) && vo.getCity2().equals(temp.getCity2())) {
				temp.setDistance(vo.getDistance());
			}
		}
		// 写入文本
		this.constantDataService.writeAll(this.distancePO);

	}

	/**
	 * 显示城市之间距离
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<DistanceVO> distanceShow() throws RemoteException {
		ArrayList<DistanceVO> voList = new ArrayList<DistanceVO>();
		ArrayList<String> addCity = new ArrayList<String>();
		ArrayList<String> delCity = new ArrayList<String>();
		this.distancePO = this.constantDataService.getAllDistance();

		ArrayList<String> city1 = this.getAllCities();
		ArrayList<String> city2 = new ArrayList<String>();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		// 先获得所有的已有距离的城市
		for (int i = 0; i < this.distancePO.size(); i++) {
			DistancePO distancePO = this.distancePO.get(i);
			map.put(distancePO.getCity1(), 0);
			map.put(distancePO.getCity2(), 0);
		}
		Set<Entry<String, Integer>> entryset = map.entrySet();
		for (Entry<String, Integer> arg : entryset) {
			city2.add(arg.getKey());
		}
		// 筛选获得新增的还未获得距离的城市
		this.filterAddWithoutDistance(city1, city2, addCity);
		// 筛选获得已删除但还存在距离的城市
		this.filterdelWithDistance(city1, city2, delCity);
		// 在已有的距离数据中删除delcity
		for (int i = 0; i < delCity.size(); i++) {
			for (int j = 0; j < this.distancePO.size(); j++) {
				DistancePO temp = this.distancePO.get(j);
				String del = delCity.get(i);
				if (temp.getCity1().equals(del) || temp.getCity2().equals(del)) {
					this.distancePO.remove(j);
					j--;
				}
			}
		}
		// city2中删除delcity
		for (int i = 0; i < delCity.size(); i++) {
			for (int j = 0; j < city2.size(); j++) {
				if (delCity.get(i).equals(city2.get(j))) {
					city2.remove(j);
					break;
				}
			}
		}
		// 组合算法，得到新增地点的所有组合，并初始化距离为0，前提是城市个数多余1个
		this.combinationCity(addCity, city2);
		// 存入文本
		this.constantDataService.writeAll(this.distancePO);
		// po转化vo
		for (int i = 0; i < this.distancePO.size(); i++) {
			DistancePO po = this.distancePO.get(i);
			DistanceVO vo = new DistanceVO(po.getCity1(), po.getCity2(), po.getDistance());
			voList.add(vo);
		}
		return voList;
	}

	/**
	 * 组合算法，得到新增地点的所有组合，并初始化距离为0，前提是城市个数多余1个 并且让新增城市与已有城市之间建立距离，初始化为0
	 * 
	 * @param addCity
	 * @param city1
	 */
	private void combinationCity(ArrayList<String> addCity, ArrayList<String> city2) {
		if (addCity.size() > 1) {
			HashMap<Integer, String> newCity = new HashMap<Integer, String>();
			for (int i = 0; i < addCity.size(); i++) {
				newCity.put(i + 1, addCity.get(i));
			}
			int num = newCity.size();
			int cities[] = new int[num];
			for (int i = 0; i < cities.length; i++) {
				cities[i] = i + 1;
			}
			Combination.list.clear();
			Combination.comb(num, num, 2, cities);
			ArrayList<int[]> allComb = Combination.list;
			for (int i = 0; i < allComb.size(); i++) {
				int index1 = allComb.get(i)[0];
				int index2 = allComb.get(i)[1];
				DistancePO temp = new DistancePO(newCity.get(index1), newCity.get(index2), 0);
				this.distancePO.add(temp);
			}
		}
		// 让新增城市与已有城市之间建立距离，初始化为0
		for (int i = 0; i < addCity.size(); i++) {
			for (int j = 0; j < city2.size(); j++) {
				DistancePO temp = new DistancePO(addCity.get(i), city2.get(j), 0);
				this.distancePO.add(temp);
			}
		}

	}

	/**
	 * 筛选获得已删除但还存在距离的城市
	 * 
	 * @param city1
	 * @param city2
	 * @param delCity
	 */
	private void filterdelWithDistance(ArrayList<String> city1, ArrayList<String> city2, ArrayList<String> delCity) {
		for (int i = 0; i < city2.size(); i++) {
			int j = 0;
			for (j = 0; j < city1.size(); j++) {
				if (city2.get(i).equals(city1.get(j))) {
					break;
				}
			}
			if (j == city1.size()) {
				delCity.add(city2.get(i));
			}
		}
	}

	/**
	 * 筛选获得新增的还未获得距离的城市
	 * 
	 * @param city1
	 * @param city2
	 * @param addCity
	 */
	private void filterAddWithoutDistance(ArrayList<String> city1, ArrayList<String> city2, ArrayList<String> addCity) {
		for (int i = 0; i < city1.size(); i++) {
			int j = 0;
			for (j = 0; j < city2.size(); j++) {
				if (city1.get(i).equals(city2.get(j))) {
					break;
				}
			}
			if (j == city2.size()) {
				addCity.add(city1.get(i));
			}
		}

	}

	/**
	 * 制定服务价格
	 * 
	 * @param vo
	 * @throws RemoteException
	 */
	public void makePrice(PriceVO vo) throws RemoteException {
		this.pricePO = new PricePO();
		this.pricePO.setMap(vo.getMap());
		this.constantDataService.writeAll(pricePO);

	}

	/**
	 * 服务价格显示
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public PriceVO priceShow() throws RemoteException {
		PriceVO vo = new PriceVO();
		this.pricePO = this.constantDataService.getAllPrice();
		HashMap<String, Double> map = this.pricePO.getMap();
		vo.setMap(map);
		return vo;
	}

	/**
	 * 审批某单据通过
	 * 
	 * @param vo
	 * @throws RemoteException
	 */
	public void setApproval(SheetVO vo) throws RemoteException {
		// SheetPO po=new SheetPO(vo.getId(), vo.getEmployeeId(), vo.getType(),
		// vo.getDate(), vo.getState(), vo.getDescription());
		this.sheetPO = this.examine.getSheet(vo.getType());
		for (int i = 0; i < this.sheetPO.size(); i++) {
			SheetPO temp = this.sheetPO.get(i);
			if (temp.getId().equals(vo.getId())) {
				this.examine.setApproval(temp);
			}
		}
	}

	/**
	 * 设置审批不通过
	 * 
	 * @param id
	 * @param type
	 * @throws RemoteException
	 */
	public void setNoApproval(String id) throws RemoteException {
		this.examine.setNoApproval(id);

	}

	/**
	 * 批量通过某类型单据
	 * 
	 * @param type
	 * @throws RemoteException
	 */
	public void setMassApproval(String type) throws RemoteException {
		this.examine.setMassApproval(type);
	}

	/**
	 * 界面显示某类单据
	 * 
	 * @param type
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<SheetVO> sheetShow(String type) throws RemoteException {
		ArrayList<SheetVO> voList = new ArrayList<SheetVO>();
		this.sheetPO = this.examine.getSheet(type);
		for (int i = 0; i < this.sheetPO.size(); i++) {
			SheetPO po = this.sheetPO.get(i);
			SheetVO vo = new SheetVO(po.getId(), po.getEmployeeId(), po.getType(), po.getDate(), po.getState(),
					po.getDescription());
			voList.add(vo);
		}
		return voList;
	}

	/**
	 * 所有机构显示
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<InstitutionVO> institutionShow() throws RemoteException {
		ArrayList<InstitutionVO> vo = new ArrayList<InstitutionVO>();
		this.institutionPO = this.institutionDataService.getAll();
		for (int i = 0; i < this.institutionPO.size(); i++) {
			String location = this.institutionPO.get(i).getLocation();
			String ID = this.institutionPO.get(i).getId();
			String type = this.institutionPO.get(i).getType();
			double rental = this.institutionPO.get(i).getRental();
			vo.add(new InstitutionVO(location, ID, type, rental));
		}
		return vo;
	}

	/**
	 * 机构下的所有员工显示
	 * 
	 * @param vo
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<UserVO> employeeShow(InstitutionVO vo) throws RemoteException {
		ArrayList<UserVO> UserList = new ArrayList<UserVO>();
		ArrayList<UserPO> po = this.employeeDataService.getAll();
		// 把高职位提出
		for (int i = 0; i < po.size(); i++) {
			UserPO tempPO = po.get(i);
			if (tempPO.getInstitution().getId() == null) {
				UserVO tempVO = new UserVO(tempPO.getId(), tempPO.getName(), tempPO.getRole(), null);
				UserList.add(tempVO);
			}
		}
		// 把低职位提出
		for (int i = 0; i < po.size(); i++) {
			UserPO tempPO = po.get(i);
			if (tempPO.getInstitution().getId() != null) {
				if (vo.getId().equals(tempPO.getInstitution().getId())) {
					UserVO tempVO = new UserVO(tempPO.getId(), tempPO.getName(), tempPO.getRole(), vo);

					UserList.add(tempVO);
				}
			}
		}
		return UserList;

	}

	/**
	 * 增加机构
	 * 
	 * @param vo
	 * @return
	 * @throws RemoteException
	 */
	public boolean addInstitution(InstitutionVO vo) throws RemoteException {
		boolean flag = true;
		this.institutionPO = this.institutionDataService.getAll();
		// 遍历找出重复ID以及不能创建的营业厅
		for (int i = 0; i < this.institutionPO.size(); i++) {
			InstitutionPO temp = this.institutionPO.get(i);
			if (vo.getId().equals(temp.getId())) {
				flag = false;
				return flag;
			}
		}
		// ID尚未建立
		for (int i = 0; i < this.institutionPO.size(); i++) {
			InstitutionPO temp = this.institutionPO.get(i);
			if (vo.getType().equals("营业厅") && temp.getType().equals("中转中心")) {
				if (vo.getId().substring(0, 6).equals(temp.getId())) {
					break;
				}
				if (i == this.getCenterCount() - 1) {
					flag = false;
					return flag;
				}
			}
		}
		// 按顺序插入
		this.insertNewInstitution(vo);
		// 获得新的名字
		InstitutionNameOperation operate = new InstitutionNameOperation(institutionDataService);
		operate.createNewName(vo.getId());
		// 写入文本
		this.institutionDataService.writeAll(this.institutionPO);
		return flag;
	}

	/**
	 * 按顺序插入机构
	 * 
	 * @param vo
	 */
	private void insertNewInstitution(InstitutionVO vo) {
		int size = this.institutionPO.size();
		int i = 0;
		for (i = 0; i < size - 1; i++) {
			InstitutionPO temp1 = this.institutionPO.get(i);
			InstitutionPO temp2 = this.institutionPO.get(i + 1);
			long voID = Integer.parseInt(vo.getId());
			long temp1ID = Integer.parseInt(temp1.getId());
			long temp2ID = Integer.parseInt(temp2.getId());
			// 如果插入的元素比第一个元素小
			if (i == 0) {
				if (voID < temp1ID) {
					InstitutionPO temp[] = new InstitutionPO[size + 1];
					temp[0] = new InstitutionPO(vo.getLocation(), vo.getId(), vo.getType(), vo.getRental());
					temp[0].setYear("0");
					for (int j = 1; j < size + 1; j++) {
						temp[j] = this.institutionPO.get(1);
						this.institutionPO.remove(1);
					}
					// 重新整理元素
					for (int j = 0; j < temp.length; j++) {
						this.institutionPO.add(temp[j]);
					}
					// 跳出循环
					break;
				}
			}
			// 元素插入在中间的情况
			if (voID > temp1ID && voID < temp2ID) {
				InstitutionPO temp[] = new InstitutionPO[size - i];
				temp[0] = new InstitutionPO(vo.getLocation(), vo.getId(), vo.getType(), vo.getRental());
				temp[0].setYear("0");
				for (int j = 1; j < size - i; j++) {
					temp[j] = this.institutionPO.get(i + 1);
					this.institutionPO.remove(i + 1);
				}
				// 重新整理元素
				for (int j = 0; j < temp.length; j++) {
					this.institutionPO.add(temp[j]);
				}
				// 跳出循环
				break;
			}

		}
		// 处理最后一个元素
		if (i == size - 1) {
			InstitutionPO temp = new InstitutionPO(vo.getLocation(), vo.getId(), vo.getType(), vo.getRental());
			temp.setYear("0");
			this.institutionPO.add(temp);
		}

	}

	/**
	 * 删除机构
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public boolean deleteInstitution(String id) throws RemoteException {
		boolean flag = true;
		this.institutionPO = institutionDataService.getAll();
		// 尚有营业厅的中转中心不能删
		if (id.length() == 6) {
			for (int i = 0; i < this.institutionPO.size(); i++) {
				InstitutionPO po = this.institutionPO.get(i);
				if ((po.getType().equals("营业厅")) && (po.getId().substring(0, 6).equals(id))) {
					flag = false;
					return flag;
				}
			}
		}
		// 删除机构细节
		this.handleDelInstitution(id);
		// 写入文本
		this.institutionDataService.writeAll(this.institutionPO);
		return flag;
	}

	/**
	 * 处理删除的机构细节
	 * 
	 * @param id
	 * @throws RemoteException
	 */
	private void handleDelInstitution(String id) throws RemoteException {
		// 遍历删除
		for (int i = 0; i < this.institutionPO.size(); i++) {
			InstitutionPO po = this.institutionPO.get(i);
			if (id.equals(po.getId())) {
				this.institutionPO.remove(i);
				i--;
			}
		}
		// 机构名字也删除
		InstitutionNameOperation operate = new InstitutionNameOperation(institutionDataService);
		operate.deleteName(id);
		// 将该机构下的员工也删除
		ArrayList<UserPO> poList;
		poList = this.employeeDataService.getAll();

		for (int i = 0; i < poList.size(); i++) {
			UserPO temp1 = poList.get(i);
			InstitutionPO temp2 = temp1.getInstitution();
			if (temp2.getId() != null) {
				if (temp2.getId().equals(id)) {
					this.deleteEmployee(temp1.getId());
				}
			}
		}

	}

	/**
	 * 查看机构信息
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public InstitutionVO checkInstitution(String id) throws RemoteException {
		InstitutionVO vo = null;
		this.institutionPO = this.institutionDataService.getAll();
		for (int i = 0; i < this.institutionPO.size(); i++) {
			InstitutionPO po = this.institutionPO.get(i);
			if (id.equals(po.getId())) {
				vo = new InstitutionVO(po.getLocation(), po.getId(), po.getType(), po.getRental());
			}
		}
		return vo;
	}

	/**
	 * 修改账户信息
	 * 
	 * @param vo
	 * @param rowNum
	 * @return
	 * @throws RemoteException
	 */
	public boolean updateInstitution(String oldID, InstitutionVO vo) throws RemoteException {
		// System.out.println(oldID+" "+vo.getId());
		String oldYear = "";
		boolean flag = true;
		this.institutionPO = this.institutionDataService.getAll();
		// System.out.println(this.institutionPO.size());
		// 遍历找出重复ID以及不能修改的营业厅
		for (int i = 0; i < this.institutionPO.size(); i++) {
			InstitutionPO temp = this.institutionPO.get(i);
			// System.out.println(vo.getId()+" "+temp.getId());
			if (vo.getId().equals(temp.getId())) {
				// System.out.println("a"+temp.getId());
				// ID未做修改，不算出错
				if (oldID.equals(vo.getId())) {
					// System.out.println("出错了");
					break;
				}
				flag = false;
				return flag;
			}
		}
		// ID是还没创建的中转中心
		for (int i = 0; i < this.institutionPO.size(); i++) {
			InstitutionPO temp = this.institutionPO.get(i);
			if (vo.getType().equals("营业厅") && temp.getType().equals("中转中心")) {
				if (vo.getId().substring(0, 6).equals(temp.getId())) {
					break;
				}
				if (i == this.getCenterCount() - 1) {
					flag = false;
					return flag;
				}
			}
		}
		// 删除替换的机构
		for (int i = 0; i < this.institutionPO.size(); i++) {
			// if(i==rowNum){
			// this.institutionPO.remove(i);
			// break;
			// }
			if (this.institutionPO.get(i).getId().equals(oldID)) {
				oldYear = this.institutionPO.get(i).getYear();
				this.institutionPO.remove(i);
				break;
			}
		}
		// 处理插入
		this.handleModifiedInstitution(oldYear, vo);
		// 同时把该机构下的员工属性更新
		this.updateInstitutionForUpdate(oldID, vo);
		// 写入文本
		this.institutionDataService.writeAll(this.institutionPO);
		return flag;
	}

	/**
	 * 更新修改后的机构的属性（员工，机构名）
	 * 
	 * @param oldID
	 * @param vo
	 * @throws RemoteException
	 */
	private void updateInstitutionForUpdate(String oldID, InstitutionVO vo) throws RemoteException {
		ArrayList<UserPO> poList;
		poList = this.employeeDataService.getAll();
		for (int j = 0; j < poList.size(); j++) {
			UserPO temp1 = poList.get(j);
			InstitutionPO temp2 = temp1.getInstitution();
			if (temp2.getId() != null) {
				if (temp2.getId().equals(oldID)) {
					// po转换vo
					UserVO tempVO = new UserVO(temp1.getId(), temp1.getName(), temp1.getRole(),
							new InstitutionVO(vo.getLocation(), vo.getId(), vo.getType()));
					this.updateEmployee(temp1.getId(), tempVO);
				}
			}
		}
		//如果没修改ID不用换名字
		if(oldID.equals(vo.getId())){
			return ;
		}
		// 更新机构名字
		InstitutionNameOperation operate = new InstitutionNameOperation(this.institutionDataService);
		operate.deleteName(oldID);
		operate.createNewName(vo.getId());
	}

	/**
	 * 处理信息修改的机构细节
	 * 
	 * @param oldYear
	 * @param vo
	 */
	private void handleModifiedInstitution(String oldYear, InstitutionVO vo) {
		int size = this.institutionPO.size();
		int i = 0;
		for (i = 0; i < size - 1; i++) {
			InstitutionPO temp1 = this.institutionPO.get(i);
			InstitutionPO temp2 = this.institutionPO.get(i + 1);
			long voID = Integer.parseInt(vo.getId());
			long temp1ID = Integer.parseInt(temp1.getId());
			long temp2ID = Integer.parseInt(temp2.getId());
			// 如果插入的元素比第一个元素小
			if (i == 0) {
				if (voID < temp1ID) {
					InstitutionPO temp[] = new InstitutionPO[size + 1];
					temp[0] = new InstitutionPO(vo.getLocation(), vo.getId(), vo.getType(), vo.getRental());
					temp[0].setYear(oldYear);
					for (int j = 1; j < size + 1; j++) {
						temp[j] = this.institutionPO.get(1);
						this.institutionPO.remove(1);
					}
					// 重新整理元素
					for (int j = 0; j < temp.length; j++) {
						this.institutionPO.add(temp[j]);
					}
					// 跳出循环
					break;
				}
			}
			// 元素插入在中间的情况
			if (voID > temp1ID && voID < temp2ID) {
				InstitutionPO temp[] = new InstitutionPO[size - i];
				temp[0] = new InstitutionPO(vo.getLocation(), vo.getId(), vo.getType(), vo.getRental());
				temp[0].setYear(oldYear);
				for (int j = 1; j < size - i; j++) {
					temp[j] = this.institutionPO.get(i + 1);
					this.institutionPO.remove(i + 1);
				}
				// 重新整理元素
				for (int j = 0; j < temp.length; j++) {
					this.institutionPO.add(temp[j]);
				}
				// 跳出循环
				break;
			}

		}
		// 处理最后一个元素
		if (i == size - 1) {
			InstitutionPO temp = new InstitutionPO(vo.getLocation(), vo.getId(), vo.getType(), vo.getRental());
			temp.setYear(oldYear);
			this.institutionPO.add(temp);
		}
	}

	/**
	 * 增加员工
	 * 
	 * @param vo
	 * @return
	 * @throws RemoteException
	 */
	public boolean addEmployee(UserVO vo) throws RemoteException {
		boolean flag = true;
		this.userPO = this.employeeDataService.getAll();
		for (int i = 0; i < this.userPO.size(); i++) {
			UserPO po = this.userPO.get(i);
			if (vo.getId().equals(po.getId())) {
				flag = false;
				return flag;
			}
		}
		//判断是否与登录信息冲突
		if(this.isRepeatWithIdentityUsers(vo.getId())){
			flag=false;
			return flag;
		}
		
		InstitutionVO insVO = vo.getInstitution();
		InstitutionPO insPO = new InstitutionPO(insVO.getLocation(), insVO.getId(), insVO.getType(), insVO.getRental());
		UserPO temp = new UserPO(vo.getId(), vo.getName(), vo.getRole(), insPO, vo.getSalary(), vo.getBonus(),
				vo.getCount(), vo.isBusy());
		// 新属性（上次付款的月份）
		temp.setMonth("0");

		this.userPO.add(temp);
		// 写入文本
		this.employeeDataService.writeAll(this.userPO);
		return flag;
	}
	/**
	 * 删除用户
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public boolean deleteEmployee(String id) throws RemoteException {
		System.out.println(id);
		boolean flag = true;
		this.userPO = this.employeeDataService.getAll();
		for (int i = 0; i < this.userPO.size(); i++) {
			UserPO po = this.userPO.get(i);
			if (po.getId().equals(id)) {
				this.userPO.remove(i);
				i--;
			}
		}
		// 写入文本
		this.employeeDataService.writeAll(this.userPO);
		return flag;
	}

	/**
	 * 根据ID查看职员信息
	 * 
	 * @param vo
	 * @return
	 * @throws RemoteException
	 */
	public UserVO checkEmployeeByID(String ID) throws RemoteException {
		UserVO vo = null;
		this.userPO = this.employeeDataService.getAll();
		for (int i = 0; i < this.userPO.size(); i++) {
			UserPO po = this.userPO.get(i);
			if (po.getId().equals(ID)) {
				// 高职位筛选
				if (po.getInstitution().getId() == null) {
					vo = new UserVO(po.getId(), po.getName(), po.getRole(), null);
				} else {
					InstitutionPO tempPO = po.getInstitution();
					InstitutionVO tempVO = new InstitutionVO(tempPO.getLocation(), tempPO.getId(), tempPO.getType(),
							tempPO.getRental());
					vo = new UserVO(po.getId(), po.getName(), po.getRole(), tempVO);
				}
			}
		}
		return vo;
	}

	/**
	 * 根据职位查询职员信息
	 * 
	 * @param role
	 * @param InstitutionID
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<UserVO> checkEmployeeByRole(String role, String InstitutionID) throws RemoteException {
		ArrayList<UserVO> voList = new ArrayList<UserVO>();
		this.userPO = this.employeeDataService.getAll();
		for (int i = 0; i < this.userPO.size(); i++) {
			UserPO po = this.userPO.get(i);
			if (po.getRole().equals(role)) {
				// 高职位筛选
				if (po.getInstitution().getId() == null) {
					voList.add(new UserVO(po.getId(), po.getName(), po.getRole(), null));
				}
				// 低职位筛选
				else if (po.getInstitution().getId().equals(InstitutionID)) {
					InstitutionPO tempPO = po.getInstitution();
					InstitutionVO tempVO = new InstitutionVO(tempPO.getLocation(), tempPO.getId(), tempPO.getType(),
							tempPO.getRental());
					voList.add(new UserVO(po.getId(), po.getName(), po.getRole(), tempVO));
				}
			}

		}
		return voList;
	}

	public boolean updateEmployee(String oldID, UserVO vo) throws RemoteException {
		//System.out.println(oldID+"  "+vo.getId());
		boolean flag = true;
		this.userPO = this.employeeDataService.getAll();
		// 遍历找出重复ID
		for (int i = 0; i < this.userPO.size(); i++) {
			// 新旧ID一样不出错,但不能与其他人的ID重复
			UserPO po = this.userPO.get(i);
			if (po.getId().equals(vo.getId())) {
				if (po.getId().equals(oldID)) {
					break;
				}
				flag = false;
				return flag;
			}
		}
		//判断是否与登录用户信息冲突
		if(!oldID.equals(vo.getId())){
			if(this.isRepeatWithIdentityUsers(vo.getId())){
				flag=false;
				return flag;
			}
		}
		
		for (int i = 0; i < this.userPO.size(); i++) {
			UserPO po = this.userPO.get(i);
			if (po.getId().equals(oldID)) {
				InstitutionVO insVO = vo.getInstitution();
				InstitutionPO insPO = new InstitutionPO(insVO.getLocation(), insVO.getId(), insVO.getType(),
						insVO.getRental());
				po.setId(vo.getId());
				po.setName(vo.getName());
				po.setRole(vo.getRole());
				po.setInstitution(insPO);
			}
		}
		// 还要同时把登录验证信息修改
		this.loginUserList = new IdentityManagementController();
		this.loginUserList.setNewID(oldID, vo.getId());
		// 写入文本
		this.employeeDataService.writeAll(this.userPO);
		return flag;
	}

	/**
	 * 获得成本收益表
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public BalanceChartVO getBalanceChar() throws RemoteException {
		this.balanceChartBLService = new BalanceChartBL();
		return this.balanceChartBLService.showNewChart();
	}

	/**
	 * 获得经营情况表
	 * 付款单
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<PaymentInfo>  showPaymentList(String startTime, String endTime) throws RemoteException {
		this.situationReportBLService=new OperationStatusBL();
		return this.situationReportBLService.showPaymentList(startTime, endTime);
	}
	/**
	 * 获得经营情况表
	 * 收款单
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<ReceiptInfo> showReceiptList(String startTime, String endTime) throws RemoteException {
		this.situationReportBLService=new OperationStatusBL();
		return this.situationReportBLService.showReceiptList(startTime, endTime);
	}
	/**
	 * 获得所有员工列表
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<UserPO> getAllUser() throws RemoteException {
		// ArrayList<UserVO> list=new ArrayList<UserVO>();
		this.userPO = this.employeeDataService.getAll();
		return this.userPO;
	}

	/**
	 * 得到所有中转中心的数量
	 * 
	 * @return
	 * @throws RemoteException
	 */
	private int getCenterCount() throws RemoteException {
		int count = 0;
		ArrayList<InstitutionPO> po = this.institutionDataService.getAll();
		for (int i = 0; i < po.size(); i++) {
			if (po.get(i).getType().equals("中转中心")) {
				count++;
			}
		}
		return count;
	}
	/**
	 * 是否和登录用户信息冲突
	 * @param ID
	 * @return
	 * @throws RemoteException 
	 */
	private boolean isRepeatWithIdentityUsers(String ID) throws RemoteException{
		this.loginUserList = new IdentityManagementController();
		ArrayList<UserPO> list=this.loginUserList.getIdentityUsers();
		for (int i = 0; i < list.size(); i++) {
			if(ID.equals(list.get(i).getId())){
				return true;
			}
		}
		return false;
	}
}
