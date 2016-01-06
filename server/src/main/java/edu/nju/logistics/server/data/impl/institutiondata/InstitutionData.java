package edu.nju.logistics.server.data.impl.institutiondata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import edu.nju.logistics.data.service.institutiondata.InstitutionDataService;
import edu.nju.logistics.po.InstitutionPO;
/**
 * 机构数据
 * @author 董轶波
 *
 */
public class InstitutionData extends UnicastRemoteObject implements InstitutionDataService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String FILE="File/manager/institution.txt";
	
	private static final String FILE_NAME = "File/manager/institutionName.txt";
	
	private ArrayList<InstitutionPO> po=null;
	
	public InstitutionData() throws RemoteException {
		
	}
	/**
	 * 获得所有机构信息
	 */
	@Override
	public ArrayList<InstitutionPO> getAll() throws RemoteException {
		this.po=new ArrayList<InstitutionPO>();
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
	 * 所有机构信息写入文本
	 */
	@Override
	public void writeAll(ArrayList<InstitutionPO> po) throws RemoteException {

		FileWriter writer=null;
		try {
			writer=new FileWriter(FILE);
			for (int i = 0; i < po.size(); i++) {
				writer.write(this.handleDataTo(po.get(i))+"\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	}
	/**
	 * 从文本处理单行数据
	 * @param line
	 */
	private void handleDataFrom(String line) {
		String single[]=line.split("；");
		String location=single[0];
		String ID=single[1];
		String type=single[2];
		double rental=Double.parseDouble(single[3]);
		String year=single[4];
		InstitutionPO temp=new InstitutionPO(location, ID, type, rental);
		temp.setYear(year);
		this.po.add(temp);	
	}
	/**
	 * 处理单行数据到文本
	 * @param po
	 * @return
	 */
	private String handleDataTo(InstitutionPO po) {
		String location=po.getLocation();
		String ID=po.getId();
		String type=po.getType();
		String rental=po.getRental()+"";
		String year=po.getYear();
		String result=location+"；"+ID+"；"+type+"；"+rental+"；"+year;
		return result;
	}
	/**
	 * 机构名字写入文本
	 */
	public void writeName(String line)throws RemoteException {
		FileWriter writer = null;
		try {
			writer = new FileWriter(FILE_NAME, true);
			writer.write(line+ "\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	
		
	}
	/**
	 * 删除机构名字
	 */
	public void deleteName(String id) throws RemoteException{

		ArrayList<String> name = new ArrayList<String>();
		BufferedReader reader = null;
		FileWriter writer = null;
		String line = "";
		try {
			reader = new BufferedReader(new FileReader(FILE_NAME));
			while ((line = reader.readLine()) != null) {
				name.add(line);
			}

			for (int i = 0; i < name.size(); i++) {
				String single = name.get(i);
				String temp[] = single.split("-");
				if (id.equals(temp[0])) {
					name.remove(i);
					break;
				}
			}

			writer = new FileWriter(FILE_NAME);
			for (int i = 0; i < name.size(); i++) {
				writer.write(name.get(i) + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	
		
	}
	/**
	 * 根据ID获得机构名字
	 */
	public String getName(String id) throws RemoteException{

		BufferedReader reader = null;
		String name="";
		String line = "";
		try {
			reader = new BufferedReader(new FileReader(FILE_NAME));
			while ((line = reader.readLine()) != null) {
				String single[]=line.split("-");
				if(id.equals(single[0])){
					name=single[1];
				}
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
		return name;
	
	}
	/**
	 * 根据名字获得机构ID
	 */
	public String getID(String name)throws RemoteException {

		BufferedReader reader = null;
		String id="";
		String line = "";
		try {
			reader = new BufferedReader(new FileReader(FILE_NAME));
			while ((line = reader.readLine()) != null) {
				String single[]=line.split("-");
				if(name.equals(single[1])){
					id=single[0];
				}
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
		return id;
	
	}
	/**
	 * 获得所有机构名字
	 */
	public ArrayList<String> getAllNames() throws RemoteException {
		ArrayList<String> names=new ArrayList<String>();
		BufferedReader reader=null;
		String line="";
		try {
			reader=new BufferedReader(new FileReader(FILE_NAME));
			while((line=reader.readLine())!=null){
				String single[]=line.split("-");
				names.add(single[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return names;
	}

}
