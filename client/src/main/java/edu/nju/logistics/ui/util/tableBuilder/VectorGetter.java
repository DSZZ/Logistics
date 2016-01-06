package edu.nju.logistics.ui.util.tableBuilder;

import java.util.Vector;


public interface VectorGetter {

	/**
	 * @return 返回每一列的表头名称
	 */
	public Vector<String> getColumnNameVector();
	
	/**
	 * 返回每一个表项的值，与表头一一以列对应
	 * @return
	 */
	public Vector<Vector<Object>> getTableValueVector();
	
	/**
	 * @return 获取行的数目
	 */
	public int getRowsCount();
}
