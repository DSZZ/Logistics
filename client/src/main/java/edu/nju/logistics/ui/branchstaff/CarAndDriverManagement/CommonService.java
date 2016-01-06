package edu.nju.logistics.ui.branchstaff.CarAndDriverManagement;

import java.util.ArrayList;

import edu.nju.logistics.ui.util.tableBuilder.OKCallerService;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;
import edu.nju.logistics.ui.util.tableBuilder.RightClickDealer;
import edu.nju.logistics.ui.util.tableBuilder.VectorGetter;

public interface CommonService {

	public String getName();
	
	public VectorGetter getVectorBuilder();

	public OKCallerService getOrderCaller();
	
	public boolean listIsEmpty();

	public void updateList();
	
	public void setRefresher(Refresher refresher);

	public ArrayList<RightClickDealer> getRightClickDealerList();

}
