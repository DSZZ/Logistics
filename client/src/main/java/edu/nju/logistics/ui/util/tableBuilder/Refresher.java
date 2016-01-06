package edu.nju.logistics.ui.util.tableBuilder;

public interface Refresher {

	/**
	 * 这是用户点击刷新按钮后，调用的方法
	 */
	public void refresh();

	public String getButtonName();
}
