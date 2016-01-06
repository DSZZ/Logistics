package edu.nju.logistics.ui.util.tableBuilder;

public interface OKCallerService {

	/**
	 * 这是用户单击确认按钮后调用的方法
	 * @param selectedRows 选择的行（可能多选或单选），至少为一行
	 */
	public void select(int[] selectedRows);

	public String getButtonName();

}
