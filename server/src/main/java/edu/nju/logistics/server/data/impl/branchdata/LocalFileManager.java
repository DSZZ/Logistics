package edu.nju.logistics.server.data.impl.branchdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.data.service.institutiondata.InstitutionDataService;
import edu.nju.logistics.po.InstitutionPO;
import edu.nju.logistics.po.branchdata.CarPO;
import edu.nju.logistics.po.branchdata.DriverPO;

public class LocalFileManager {

	private static final String PATH = "File//orderData";
	/**
	 * 更新机构信息（即目录框架）
	 * @param institutionDataGetter
	 */
	public void updateLocalInstitution(InstitutionDataService institutionDataGetter){
		ArrayList<InstitutionPO> institutionList = null;
		try {
			institutionList = institutionDataGetter.getAll();
		} catch (RemoteException e) {e.printStackTrace();}
		
		
		for(InstitutionPO institution : institutionList){
			if(institution.getType().equals("中转中心")){
				File file = new File(PATH+"//center//"+institution.getId());
				if(!file.exists()){
					file.mkdirs();
				}
			}else{
				File file = new File(PATH+"//branch//"+institution.getId());
				if(!file.exists()){
					file.mkdirs();
				}
			}
		}
	}
	
	public void addCar(CarPO carPO, String institutionid){
		String directoryPath = PATH+"//branch//"+institutionid+"//car";
		File file = new File(directoryPath);
		if(!file.exists()){
			file.mkdirs();
		}
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(directoryPath+"//"+carPO.getId()+".car"));
			out.writeObject(carPO);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void deleteCar(String carID,String institutionid) {
		String directoryPath = PATH+"//branch//"+institutionid+"//car";
		File file = new File(directoryPath);
		if(!file.exists()){
			file.mkdirs();
		}
		File f[] = file.listFiles();
		
		for(File temp : f){
			if(temp.getName().endsWith(".car")){
				ObjectInputStream in;
				try {
					in = new ObjectInputStream(
							 new FileInputStream(temp));
					CarPO car = (CarPO)in.readObject();
					in.close();
					if(car.getId().equals(carID)){
						temp.delete();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					temp.delete();
				}
			}
			
		}
	}
	
	public ArrayList<CarPO> getCarList(String institutionid){
		String directoryPath = PATH+"//branch//"+institutionid+"//car";
		File file = new File(directoryPath);
		if(!file.exists()){
			file.mkdirs();
		}
		File f[] = file.listFiles();
		
		ArrayList<CarPO> carList = new ArrayList<>();
		
		for(File temp : f){
			if(temp.getName().endsWith(".car")){
				ObjectInputStream in;
				try {
					in = new ObjectInputStream(
							 new FileInputStream(temp));
					carList.add((CarPO)in.readObject());
					in.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					temp.delete();
				}
			}
		}
		return carList;
	}

	public void setCarBusyStatus(String carid, boolean busyOrNot,
			String institutionid) {
		String directoryPath = PATH+"//branch//"+institutionid+"//car";
		File file = new File(directoryPath);
		if(!file.exists()){
			file.mkdirs();
		}
		File f[] = file.listFiles();
		
		for(File temp : f){
			if(temp.getName().endsWith(".car")){
				ObjectInputStream in;
				try {
					in = new ObjectInputStream(
							 new FileInputStream(temp));
					CarPO car = (CarPO)in.readObject();
					in.close();
					if(car.getId().equals(carid)){
						temp.delete();
						car.setBusy(busyOrNot);
						
						ObjectOutputStream out = new ObjectOutputStream(
								new FileOutputStream(temp));
						out.writeObject(car);
						out.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					temp.delete();
				}
			}
			
		}
		
	}
	
	public void addDriver(DriverPO driverPO, String institutionid){
		String directoryPath = PATH+"//branch//"+institutionid+"//driver";
		File file = new File(directoryPath);
		if(!file.exists()){
			file.mkdirs();
		}
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(directoryPath+"//"+driverPO.getId()+".driver"));
			out.writeObject(driverPO);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void deleteDriver(String driverID,String institutionid) {
		String directoryPath = PATH+"//branch//"+institutionid+"//driver";
		File file = new File(directoryPath);
		if(!file.exists()){
			file.mkdirs();
		}
		File f[] = file.listFiles();
		
		for(File temp : f){
			if(temp.getName().endsWith(".driver")){
				ObjectInputStream in;
				try {
					in = new ObjectInputStream(
							 new FileInputStream(temp));
					DriverPO driver = (DriverPO)in.readObject();
					in.close();
					if(driver.getId().equals(driverID)){
						temp.delete();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					temp.delete();
				}
			}
			
		}
	}
	
	public ArrayList<DriverPO> getDriverList(String institutionid){
		String directoryPath = PATH+"//branch//"+institutionid+"//driver";
		File file = new File(directoryPath);
		if(!file.exists()){
			file.mkdirs();
		}
		File f[] = file.listFiles();
		
		ArrayList<DriverPO> driverList = new ArrayList<>();
		
		for(File temp : f){
			if(temp.getName().endsWith(".driver")){
				ObjectInputStream in;
				try {
					in = new ObjectInputStream(
							 new FileInputStream(temp));
					driverList.add((DriverPO)in.readObject());
					in.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					temp.delete();
				}
			}
		}
		return driverList;
	}

	public void setDriverBusyStatus(String driverid, boolean busyOrNot,
			String institutionid) {
		String directoryPath = PATH+"//branch//"+institutionid+"//driver";
		File file = new File(directoryPath);
		if(!file.exists()){
			file.mkdirs();
		}
		File f[] = file.listFiles();
		
		for(File temp : f){
			if(temp.getName().endsWith(".driver")){
				ObjectInputStream in;
				try {
					in = new ObjectInputStream(
							 new FileInputStream(temp));
					DriverPO driver = (DriverPO)in.readObject();
					in.close();
					if(driver.getId().equals(driverid)){
						temp.delete();
						driver.setBusy(busyOrNot);
						
						ObjectOutputStream out = new ObjectOutputStream(
								new FileOutputStream(temp));
						out.writeObject(driver);
						out.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					temp.delete();
				}
			}
			
		}
		
	}
}
