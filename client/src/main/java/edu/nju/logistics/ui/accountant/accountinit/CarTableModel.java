package edu.nju.logistics.ui.accountant.accountinit;

import java.util.ArrayList;
import java.util.Vector;

import edu.nju.logistics.vo.financial.CarSM;

public class CarTableModel extends SuperTableModel{
	
    /**
     * 
     */
private static final long serialVersionUID = 1L;

	private ArrayList<CarSM> carSMList;
    
    public   CarTableModel(ArrayList<CarSM> carSMList){
    	this.carSMList = carSMList;
    	 initModel();
    }
    public void initModel(){
		title.add("车辆代号");
		title.add("车牌号");
		title.add("开始服役时间");
		
		for(int i = 0; i< carSMList.size();i++){
			Vector<String> temp = new Vector<String> ();
			temp.add(carSMList.get(i).getCarID());
			temp.add(carSMList.get(i).getPlateNum());
			temp.add(carSMList.get(i).getStartWorkTime());
			data.add(temp);
		}
	}
    
    


}
