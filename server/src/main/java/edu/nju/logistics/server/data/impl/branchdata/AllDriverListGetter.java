package edu.nju.logistics.server.data.impl.branchdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import edu.nju.logistics.po.branchdata.DriverPO;

public class AllDriverListGetter {
	
	private static final String PATH = "File//orderData//branch";
	
	public static ArrayList<DriverPO> getAllDriverList(){
		File file = new File(PATH);
		if(!file.isDirectory() || !file.exists()){
			file.mkdirs();
		}
		File[] filelist = file.listFiles();
		ArrayList<DriverPO> driverList = new ArrayList<>();
		
		for(File institutionDir : filelist){
			
			File driverDir = new File(institutionDir.getAbsolutePath() + "//driver");
			if(driverDir.isDirectory() && driverDir.exists()){
				
				File[] driverfilelist = driverDir.listFiles();
				for(File driver : driverfilelist){
					if(driver.getName().endsWith(".driver")){
						ObjectInputStream in;
						try {
							in = new ObjectInputStream(new FileInputStream(driver));
							driverList.add((DriverPO)in.readObject());
							in.close();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							driver.delete();
						}
					}
				}
			}
		}
		
		return driverList;
	}
	
}
