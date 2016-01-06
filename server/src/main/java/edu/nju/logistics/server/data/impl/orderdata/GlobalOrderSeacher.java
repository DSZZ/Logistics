package edu.nju.logistics.server.data.impl.orderdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import edu.nju.logistics.po.orderdata.OrderPO;

public class GlobalOrderSeacher {
	
	public static String getOrderFullPath(String orderid,String directory){
		File file = new File(directory);
		if(!file.isDirectory() || !file.exists())
			return null;
		File[] filelist = file.listFiles();
		String result = null;
		for(File temp : filelist){
			if(temp.isDirectory()){
				if(temp.getName() != "unpay"){
					result = getOrderFullPath(orderid,directory + "//" + temp.getName());
					if(result!=null){
						return result;
					}
				}
			}
			else{
				if(temp.getName().endsWith(".order")){
					ObjectInputStream in;
					try {
						in = new ObjectInputStream(
								 new FileInputStream(temp));
						OrderPO tempOrder = (OrderPO)in.readObject();
						if(tempOrder.getOrderVO().getOrderID().equals(orderid)){
							in.close();
							return temp.getAbsolutePath();
						}
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
		}
		return result;
	}
	
	public static void move(String orderPath, String destinationDir){
		File oldFile = new File(orderPath);
		File fnewpath = new File(destinationDir); 
		
		if(!fnewpath.exists()) 
			fnewpath.mkdirs(); 
		
		//将文件移到新文件里 
		File fnew = new File(destinationDir +"//" +oldFile.getName()); 
		oldFile.renameTo(fnew); 
		oldFile.delete();
	}

}
