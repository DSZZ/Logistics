package edu.nju.logistics.bl.service.finacialmanagement;

import java.util.ArrayList;

import edu.nju.logistics.po.financial.BankAccountPO;
import edu.nju.logistics.vo.financial.BankAccountVO;

public interface PaymentBankAccountService {
	/**
	 * 从服务器取得所有银行账户PO
	 * @return
	 */
	public ArrayList<BankAccountPO>findBankAccountPOList();
	/**
	 * 在列表中选中某个银行账户请求查看详细信息
	 * @param name
	 */
	public BankAccountVO getBankAccountVO(String name);
	/**
	 * 更新列表中的PO
	 * @param po
	 * @return
	 */
	public boolean  updateBankAccountPO(BankAccountPO po);
	
	/**
	 * 此方法用于更新服务器上的数据
	 * @param list
	 */
	public void updateData();
	
	public BankAccountVO  BankAccountPoToVo(BankAccountPO po);
}
