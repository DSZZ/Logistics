package edu.nju.logistics.bl.service.finacialmanagement;


/**
 * 此接口用于在总经理审批付款单通过后，调用执行方法时使用。
 * 职责为根据银行账户和付款金额来更新服务器数据
 * @author 侍硕
 *
 */
public interface BankAccountUpdateBLService {
      /**
       * 
       * @param baName  银行账户的用户名
       * @param cost           代扣除的金额
       */
     public void updateBankAccountPO(String baName  ,double cost);
}
