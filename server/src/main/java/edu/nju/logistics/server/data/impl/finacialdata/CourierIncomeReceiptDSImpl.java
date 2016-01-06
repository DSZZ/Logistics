package edu.nju.logistics.server.data.impl.finacialdata;

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

import edu.nju.logistics.data.service.finacialdata.CourierIncomeReceiptDataService;
import edu.nju.logistics.po.courierIncomeReceipt.CourierIncomeReceiptPO;




public class CourierIncomeReceiptDSImpl  extends UnicastRemoteObject 
                    implements CourierIncomeReceiptDataService {

	public CourierIncomeReceiptDSImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String FileURL="File/financial/courierIncomeReceipt.txt";
	private ArrayList<CourierIncomeReceiptPO>  receiptPOList;
	
	
	@Override
	public void insert(CourierIncomeReceiptPO po) throws RemoteException {
		// TODO Auto-generated method stub
		ObjectOutputStream os=null;
		try{      
			    File  file = new File(FileURL);
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
	public ArrayList<CourierIncomeReceiptPO> getAll() throws RemoteException {
		// TODO Auto-generated method stub
		receiptPOList= new ArrayList<>();
		CourierIncomeReceiptPO  temp;
		try{ 	
			ObjectInputStream os = new ObjectInputStream(new FileInputStream(FileURL));
		    while((temp=(CourierIncomeReceiptPO)os.readObject())!=null){
		    	receiptPOList.add(temp);
		    	if(os.available()<=0){
		    		break;
		    	}
		    }
		    os.close();
		}catch (FileNotFoundException ex){
			ex.printStackTrace();
		}catch (IOException ex){
			ex.printStackTrace();
		}catch (ClassNotFoundException ex){
			ex.printStackTrace();
		}
		return receiptPOList;
	}

}
