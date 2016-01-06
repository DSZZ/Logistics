package edu.nju.logistics.server.data.impl.employeedata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import edu.nju.logistics.data.service.employeedata.EmployeeDataService;
import edu.nju.logistics.po.InstitutionPO;
import edu.nju.logistics.po.UserPO;
/**
 * 员工数据管理
 * @author 董轶波
 *
 */
public class EmployeeData extends UnicastRemoteObject implements EmployeeDataService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String FILE="File/manager/employee.txt";
	
	private ArrayList<UserPO> po;
	
	public EmployeeData() throws RemoteException{}
	/**
	 * 获得用户数据
	 */
	public ArrayList<UserPO> getAll() throws RemoteException {
		this.po=new ArrayList<UserPO>();
		BufferedReader reader=null;
		String line="";
		try {
			reader=new BufferedReader(new FileReader(FILE));
			while((line=reader.readLine())!=null){
				this.handleDataFrom(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this.po;
	}

	/**
	 * 人员数据写入文本
	 */
	public void writeAll(ArrayList<UserPO> po) throws RemoteException {
		FileWriter writer=null;
		try {
			writer=new FileWriter(FILE);
			for (int i = 0; i < po.size(); i++) {
				writer.write(this.handleDataTo(po.get(i))+"\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try{
			writer.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 处理单行数据到文本
	 * @param po
	 * @return
	 */
	private String handleDataTo(UserPO po) {
		String ID=po.getId();
		String name=po.getName();
		String role=po.getRole();
		InstitutionPO temp=po.getInstitution();
		String institution;
		if(temp.getId()==null){
			institution="无";
		}
		else{
			institution=temp.getLocation()+" "+temp.getId()+" "+temp.getType();
		}
		String salary=po.getSalary()+"";
		String bonus=po.getBonus()+"";
		String count=po.getCount()+"";
		String isBusy=po.isBusy()?"true":"false";
		String month=po.getMonth();
		String result= ID+"；"+name+"；"+role+"；"+institution+"；"+salary+"；"+bonus+"；"+count+"；"+isBusy+"；"+month;
		return result;
	}
	/**
	 * 从文本处理单行数据
	 * @param line
	 */
	private void handleDataFrom(String line) {
		InstitutionPO institutionPO;
		
		String[] single=line.split("；");
		String ID=single[0];
		String name=single[1];
		String role=single[2];
		String institution=single[3];
		double salary=Double.parseDouble(single[4]);
		double bonus=Double.parseDouble(single[5]);
		int count=Integer.parseInt(single[6]);
		String isBusy_temp=single[7];
		String month=single[8];
		//处理机构信息
		if(institution.equals("无")){
		institutionPO=new InstitutionPO();
		}
		else{
		String[] temp=institution.split(" ");
		institutionPO=new InstitutionPO(temp[0], temp[1], temp[2]);
		}
		
		boolean isBusy=isBusy_temp.equals("true")?true:false;
		
		UserPO newPO=new UserPO(ID,name,role,institutionPO,salary,bonus,count,isBusy);
		newPO.setMonth(month);
		this.po.add(newPO);
	}
}
