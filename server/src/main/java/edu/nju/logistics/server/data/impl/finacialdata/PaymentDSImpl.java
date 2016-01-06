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

import edu.nju.logistics.data.service.finacialdata.PaymentDataService;
import edu.nju.logistics.po.financial.PaymentPO;

public class PaymentDSImpl  extends UnicastRemoteObject implements PaymentDataService{
	/**
	 * RMI相关
	 */
	private static final long serialVersionUID = 1L;
	public PaymentDSImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final String FileURL="File/financial/payment.txt";
	private ArrayList<PaymentPO> paymentPOList;
	
	

	@Override
	public void insert(PaymentPO po) throws RemoteException{
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
	public void writeAll(ArrayList<PaymentPO> list) throws RemoteException{
		// TODO Auto-generated method stub
		  for(int i=0;i<list.size();i++){
			    insert(list.get(i));
		  }
	}

	@Override
	public ArrayList<PaymentPO> getAll() throws RemoteException{
		// TODO Auto-generated method stub
		paymentPOList= new ArrayList<>();
		PaymentPO temp;
		try{ 	
			ObjectInputStream os = new ObjectInputStream(new FileInputStream(FileURL));
		    while((temp=(PaymentPO)os.readObject())!=null){
		    	paymentPOList.add(temp);
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
		return paymentPOList;
	}



}
