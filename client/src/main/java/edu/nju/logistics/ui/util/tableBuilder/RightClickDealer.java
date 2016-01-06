package edu.nju.logistics.ui.util.tableBuilder;

public interface RightClickDealer {

	public String getServiceName();
	
	public void dealRightClick(int row);

	public void addRefresher(Refresher refresher);
}
