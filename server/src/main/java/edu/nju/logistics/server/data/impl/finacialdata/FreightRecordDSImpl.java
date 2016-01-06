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

import edu.nju.logistics.data.service.finacialdata.FreightRecordDataService;
import edu.nju.logistics.po.financial.FreightRecordPO;

public class FreightRecordDSImpl extends UnicastRemoteObject implements FreightRecordDataService{

	/**
	 * RMI部分
	 */
	
	private static final long serialVersionUID = 1L;
	private static final String FileURL="File/financial/freightRecord.txt";
	private ArrayList<FreightRecordPO> freightRecordPOList;
	
	public FreightRecordDSImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub

	}
	
	@Override
	public ArrayList<FreightRecordPO> getAll() throws RemoteException {
		// TODO Auto-generated method stub
		freightRecordPOList= new ArrayList<>();
		FreightRecordPO temp;
		try{ 		
			System.out.println("get one FreightRecordPO !");
			ObjectInputStream  os = new ObjectInputStream(new FileInputStream(FileURL));
		    while((temp=(FreightRecordPO)os.readObject())!=null){
		    	freightRecordPOList.add(temp);
		    	if(os.available()<=0){
		    		break;
		    	}
		    }
		    os.close();
		}catch (FileNotFoundException ex){
			ex.printStackTrace();
		}
		catch (IOException ex){
			ex.printStackTrace();
		}catch (ClassNotFoundException ex){
			ex.printStackTrace();
		}
		return freightRecordPOList;
	}



	@Override
	public void insert(FreightRecordPO po) throws RemoteException {
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
	public void writeAll(ArrayList<FreightRecordPO> freightRecordList) throws RemoteException {
		// TODO Auto-generated method stub
	      for(int i=0;i<freightRecordList.size();i++){
	    	       insert(freightRecordList.get(i));
	      }      
	}
}
