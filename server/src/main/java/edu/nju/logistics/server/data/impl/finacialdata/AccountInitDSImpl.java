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
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import edu.nju.logistics.data.service.finacialdata.AccountInitDataService;
import edu.nju.logistics.po.financial.AccountPO;
import edu.nju.logistics.po.financial.BankAccountPO;


public class AccountInitDSImpl extends UnicastRemoteObject implements AccountInitDataService{
		/**
		 * RMI相关
		 */
		private static final long serialVersionUID = 1L;
		public AccountInitDSImpl() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}

		private static final String FileURL="File/financial/accountInit.txt";
		private ArrayList<AccountPO> accountPOList;
		
		

		@Override
		public void insert(AccountPO po) throws RemoteException{
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
		public void writeAll(ArrayList<AccountPO> list) throws RemoteException{
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
		public ArrayList<AccountPO> getAll() throws RemoteException{
			// TODO Auto-generated method stub
			accountPOList= new ArrayList<>();
			ObjectInputStream ois=null;
			AccountPO temp;
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
					temp = (AccountPO) ois.readObject();
					accountPOList.add(temp);
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
			return accountPOList;
		}




}
