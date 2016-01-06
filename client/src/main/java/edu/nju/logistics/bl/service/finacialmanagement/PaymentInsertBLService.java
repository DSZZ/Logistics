package edu.nju.logistics.bl.service.finacialmanagement;

import edu.nju.logistics.po.financial.PaymentPO;
/**
 * 该接口的创建是为了满足面向接口编程和“小接口”思想。
 * 当付款单通过审批时，总经理会调用执行方法，将付款单插入到服务器
 * 而这个BL就是为了提供该服务而设置的。
 * @author 67534
 *
 */
public interface PaymentInsertBLService {
        public void addPayment(PaymentPO po);
}
