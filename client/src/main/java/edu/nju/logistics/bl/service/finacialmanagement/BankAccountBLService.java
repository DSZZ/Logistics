package edu.nju.logistics.bl.service.finacialmanagement;

import java.util.ArrayList;

import edu.nju.logistics.po.financial.BankAccountPO;
import edu.nju.logistics.vo.financial.BankAccountVO;


/**
 * 
 * @author 侍硕
 *
 */
public interface BankAccountBLService {
	/**
	 * 从服务器取得所有银行账户PO
	 * @return
	 */
	public ArrayList<BankAccountPO>findBankAccountPOList();

	/**
	 * 在界面添加新的银行账户并点击确认
	 * @param name  从界面获得输入银行账户的名称
	 * @param balance 从界面获得输入银行账户的余额
	 * @return  
	 */
	public boolean addBankAccount(String name,String balance);
	/**
	 * 返回全部银行账户列表
	 * @return 
	 */
	public ArrayList<BankAccountVO> getBankAccountVOList();
	/**
	 * 根据关键字来检索BL中的VO
	 * @param key
	 * @return
	 */
	public ArrayList<BankAccountVO> getBankAccountVOListByKey(String key); 
	
	/**
	 * 在列表中选中某个银行账户请求查看详细信息
	 * @param name
	 */
	public BankAccountVO getBankAccountVO(String name);
	/**
	 * 在列表中选中某个银行账户并请求修改账户名称信息
	 * @param name  
	 * @return
	 */
	public boolean modifyBankAccount(String oldName,String newName);

	/**
	 * 在列表中选中某个银行账户并请求删除
	 * @param name
	 * @param balance
	 * @return
	 */
	public void deleteBankAccount(String name);
	/**
	 * 此方法用于更新数据库
	 * @param list
	 */
	public void updateData();
	
	public BankAccountVO  BankAccountPoToVo(BankAccountPO po);
}