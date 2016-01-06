package edu.nju.logistics.ui.accountant.accountinit;

import java.util.ArrayList;
import java.util.Vector;

import edu.nju.logistics.vo.financial.CarSM;
import edu.nju.logistics.vo.financial.StorageSM;

public class StorageTableModel extends SuperTableModel{
	
    /**
     * 
     */
private static final long serialVersionUID = 1L;

	private ArrayList<StorageSM> storageSMList;
    
    public   StorageTableModel(ArrayList<StorageSM> storageSMList){
    	this.storageSMList = storageSMList;
    	 initModel();
    }
    public void initModel(){
		title.add("中转中心编号");
		title.add("库存总容量");
		title.add("库存总占用量");
		title.add("库存占用率");
		
		for(int i = 0; i< this.storageSMList.size();i++){
			Vector<String> temp = new Vector<String> ();
			temp.add(storageSMList.get(i).getInstiID());
			temp.add(storageSMList.get(i).getTotalCapacity()+"");
			temp.add(storageSMList.get(i).getTotalOccupancy()+"");
			double rate =( (double)storageSMList.get(i).getTotalOccupancy()/(double)storageSMList.get(i).getTotalCapacity())*100;
			temp.add(rate+"%");
			data.add(temp);
		}
	}
    
    


}
