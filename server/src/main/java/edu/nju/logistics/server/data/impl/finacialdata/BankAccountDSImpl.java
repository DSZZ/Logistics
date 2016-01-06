package edu.nju.logistics.server.data.impl.finacialdata;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.util.ArrayList;

import edu.nju.logistics.data.service.finacialdata.BankAccountDataService;
import edu.nju.logistics.po.financial.BankAccountPO;

public class BankAccountDSImpl extends UnicastRemoteObject implements BankAccountDataService {
	/**
	 * RMI部分
	 */
	
	private static final long serialVersionUID = 1L;
	private static final String FileURL="File/financial/bankaccount.txt";
	private ArrayList<BankAccountPO> bankAccountPOList;
	
	public BankAccountDSImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<BankAccountPO> getAll() throws RemoteException{
		// TODO Auto-generated method stub
		bankAccountPOList= new ArrayList<>();
		ObjectInputStream ois=null;
		BankAccountPO temp;
		try{ 	
			FileInputStream fis = new FileInputStream(FileURL);
			ois= new ObjectInputStream(fis);
			
			 if(fis.available()<=0){
				 System.out.println("fis.available()<=0");
			 }else{
				 System.out.println("fis.available()>0");
			 }
			 
			while(fis.available()>0){
				System.out.println("找到一个银行账户");
				temp = (BankAccountPO) ois.readObject();
				bankAccountPOList.add(temp);
			}
			
		    ois.close();
		}catch (EOFException ex){
			ex.printStackTrace();   
		}catch (FileNotFoundException ex){
			ex.printStackTrace();
		}catch (IOException ex){
			ex.printStackTrace();
		}catch (ClassNotFoundException ex){
			ex.printStackTrace();
		}finally{
			try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bankAccountPOList;
	}


   //清空原文件后写入
	@Override
	public void writeAll(ArrayList<BankAccountPO> list) throws RemoteException {
		// TODO Auto-generated method stub
		  File file = new File(FileURL);
		  if(file.exists()){
		        file.delete();
		  }
		  
		  try {
			file.createNewFile();
		  } catch (IOException e) {
			 // TODO Auto-generated catch block
			e.printStackTrace();
		  }
		   for(int i=0;i<list.size();i++){
			     insert(list.get(i));
		   }
	
	}
	
	@Override
	public void insert(BankAccountPO po) throws RemoteException {
		// TODO Auto-generated method stub
		ObjectOutputStream os=null;
		try{      
		     	 File  file  =  new File(FileURL);
			if(file.length()<1){
				 os = new ObjectOutputStream(new FileOutputStream(file,true));	
			}else{
				 os = new MyObjectOutputStream(new FileOutputStream(file,true));	
			}
		     os.writeObject(po);
         
        }catch (FileNotFoundException ex){
	          ex.printStackTrace();
        }catch (IOException ex){
	           ex.printStackTrace();
       }finally{
    	    try{
    	    	os.close();
    	    }catch(IOException e){
    	    	e.printStackTrace();
    	    }
       }
		
    }
	
	
	
	@Override
	public void Update(String name, double cost) throws RemoteException {
		// TODO Auto-generated method stub
		  this.getAll();
		  for(int i=0;i<bankAccountPOList.size();i++){
			  if(bankAccountPOList.get(i).getName().equals(name)){
				  bankAccountPOList.get(i).setBalance(bankAccountPOList.get(i).getBalance()-cost);
			  }
		  }
		  this.writeAll(bankAccountPOList);
	}



	



}
