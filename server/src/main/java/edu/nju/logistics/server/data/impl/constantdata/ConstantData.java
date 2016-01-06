package edu.nju.logistics.server.data.impl.constantdata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import edu.nju.logistics.data.service.constantdata.ConstantDataService;
import edu.nju.logistics.po.DistancePO;
import edu.nju.logistics.po.PricePO;

public class ConstantData extends UnicastRemoteObject implements ConstantDataService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String FILE_PRICE="File/manager/price.txt";
	
	private static final String FILE_DISTANCE="File/manager/distance.txt";
	
	private static final String FILE_SALARY="File/manager/salaryStrategy.txt";
	
	public ConstantData() throws RemoteException{}

	private PricePO pricePO=null;
	
	private ArrayList<DistancePO> distancePO=null;
	
	private HashMap<String, Double> priceMap=null;
	
	private HashMap<String ,Double> salaryMap=null;
	/**
	 * 获得薪水策略数据
	 * @return
	 */
	@Override
	public HashMap<String, Double> getSalaryStrategy() throws RemoteException{
		BufferedReader reader=null;
		String line="";
		this.salaryMap=new HashMap<String,Double>();
		try {
			reader=new BufferedReader(new FileReader(FILE_SALARY));
			while((line=reader.readLine())!=null){
				this.handleSalaryDataFrom(line);
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
		return this.salaryMap;
	
	}
	/**
	 * 从文本 
	 * @param line
	 */
	private void handleSalaryDataFrom(String line) {
		String single[]=line.split("；");
		String role=single[0];
		double percent=Double.parseDouble(single[1]);
		
		this.salaryMap.put(role, percent);
		
	}
	/**
	 * 薪水策略写入文本
	 * @param map
	 */
	@Override
	public void writeSalaryStrategy(HashMap<String, Double> map) throws RemoteException {
		FileWriter writer=null;
		try {
			writer=new FileWriter(FILE_SALARY);
			Set<Entry<String,Double>> entryset=map.entrySet();
			for(Entry<String,Double> arg:entryset){
				String role=arg.getKey();
				String percent=arg.getValue()+"";
				writer.write(role+"；"+percent+"\n");
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
	 * 获得城市距离数据
	 */
	@Override
	public ArrayList<DistancePO> getAllDistance() throws RemoteException {
		BufferedReader reader=null;
		String line="";
		this.distancePO=new ArrayList<DistancePO>();
		try {
			reader=new BufferedReader(new FileReader(FILE_DISTANCE));
			while((line=reader.readLine())!=null){
				this.handleDistanceDataFrom(line);
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
		return this.distancePO;
	
	}

	@Override
	public void writeAll(ArrayList<DistancePO> po) throws RemoteException {
		FileWriter writer=null;
		try {
			writer=new FileWriter(FILE_DISTANCE);
			 for (int i = 0; i < po.size(); i++) {
				 DistancePO temp=po.get(i);
				 writer.write(temp.getCity1()+"；"+temp.getCity2()+"；"+temp.getDistance()+"\n");
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
	 * 获得服务价格数据
	 */
	@Override
	public PricePO getAllPrice() throws RemoteException {
		BufferedReader reader=null;
		String line="";
		this.priceMap=new HashMap<String, Double>();
		this.pricePO=new PricePO();
		try {
			reader=new BufferedReader(new FileReader(FILE_PRICE));
			while((line=reader.readLine())!=null){
				this.handlePriceDataFrom(line);
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
		this.pricePO.setMap(this.priceMap);
		return this.pricePO;
	}
	/**
	 * 服务价格数据写入文本
	 */
	@Override
	public void writeAll(PricePO po) throws RemoteException {
		FileWriter writer=null;
		try {
			writer=new FileWriter(FILE_PRICE);
			this.priceMap=po.getMap();
			Set<Entry<String,Double>> entryset=this.priceMap.entrySet();
			for(Entry<String,Double> arg:entryset){
				String service=arg.getKey();
				String price=arg.getValue()+"";
				writer.write(service+"；"+price+"\n");
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
	 * 从文本处理价格数据
	 * @param line
	 */
	private void handlePriceDataFrom(String line) {
		String single[]=line.split("；");
		String service=single[0];
		double price=Double.parseDouble(single[1]);
		this.priceMap.put(service, price);
		
	}
	/**
	 * 从文本获得城市距离数据
	 * @param line
	 */
	private void handleDistanceDataFrom(String line) {
		String single[]=line.split("；");
		String city1=single[0];
		String city2=single[1];
		double distance=Double.parseDouble(single[2]);
		
		DistancePO po=new DistancePO(city1, city2, distance);
		this.distancePO.add(po);
		
	}

}
