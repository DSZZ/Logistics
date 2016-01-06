package edu.nju.logistics.server.data.impl.branchdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import edu.nju.logistics.po.branchdata.DriverPO;

public class DriverReplacer {

	private static final String PATH = "File//orderData";
	
	public static DriverPO getDriver(String driverid, String institution){
		String driverPath = getDriverFullPath(driverid,institution);
		File temp = new File(driverPath);
		ObjectInputStream in;
		DriverPO driver = null;
		try {
			in = new ObjectInputStream(
					 new FileInputStream(temp));
			driver = (DriverPO)in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			temp.delete();
		}
		return driver;
	}
	public static void replace(String driverid, String institution,DriverPO newDriver){
		String driverPath = getDriverFullPath(driverid,institution);
		
		File file = new File(driverPath);
		try {
			file.delete();
			file.createNewFile();
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(file));
			out.writeObject(newDriver);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getDriverFullPath(String driverid, String institutionid){
		
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
						return temp.getAbsolutePath();
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
		
		return null;
	}
}
