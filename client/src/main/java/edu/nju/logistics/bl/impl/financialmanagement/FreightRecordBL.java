package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.finacialmanagement.FreightRecordBLService;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.finacialdata.FreightRecordDataService;
import edu.nju.logistics.po.financial.FreightRecordPO;

public class FreightRecordBL  implements FreightRecordBLService{
    
	private FreightRecordDataService dataService ;
	
	public FreightRecordBL(){
		  try{
   		       this.dataService = (FreightRecordDataService) Naming.lookup("rmi://" + RMI.getIP() + ":2014/"+"FreightRecordDSImpl");
   	           System.out.println("FreightRecordDataService awake!");
   	       } catch (Exception e) {
   		       System.out.println("FreightRecordDS failed");
 			   e.printStackTrace();
 		   }
     }
	
	@Override
	public void addFreightRecordPO(FreightRecordPO freightpo) {
		// TODO Auto-generated method stub
		try{   
		         dataService.insert(freightpo);
		         
		}catch(RemoteException e){
			   e.printStackTrace();
		}
	}

	@Override
	public void addFreightRecordPOList(ArrayList<FreightRecordPO> list) {
		// TODO Auto-generated method stub
		   try{   
	                dataService.writeAll(list);
	        }catch(RemoteException e){
		            e.printStackTrace();
	       }
	}

	@Override
	public ArrayList<FreightRecordPO> getAllFreightRecordPO() {
		// TODO Auto-generated method stub
		ArrayList<FreightRecordPO> freightRecordPOList  = new ArrayList<>();
		try{   
           freightRecordPOList= dataService.getAll();
           System.out.println("Length of the list : "+freightRecordPOList.size() );
           
        }catch(RemoteException e){
            e.printStackTrace();
         }

		    return freightRecordPOList;
		
	}

}
