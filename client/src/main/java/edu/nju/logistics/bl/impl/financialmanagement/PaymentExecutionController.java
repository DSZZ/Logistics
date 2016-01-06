package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.impl.branchmanagement.DriverControllerImpl;
import edu.nju.logistics.bl.impl.operationmanagement.OperationManagementController;
import edu.nju.logistics.bl.service.branchmanagement.DriverController;
import edu.nju.logistics.bl.service.finacialmanagement.BankAccountUpdateBLService;
import edu.nju.logistics.bl.service.finacialmanagement.PaymentExecutionService;
import edu.nju.logistics.bl.service.finacialmanagement.PaymentInsertBLService;
import edu.nju.logistics.bl.service.operationmanagement.EnvironmentGetter;
import edu.nju.logistics.po.financial.InstiInfoPO;
import edu.nju.logistics.po.financial.PaymentPO;
import edu.nju.logistics.po.financial.UserInfoPO;

public class PaymentExecutionController implements PaymentExecutionService {
	PaymentInsertBLService pabl;
	BankAccountUpdateBLService babl;
	EnvironmentGetter getter;
	DriverController driverController;

	public PaymentExecutionController() throws RemoteException {
		pabl = new PaymentInsertBL();
		babl = new BankAccountUpdateBL();
		getter = new OperationManagementController();

		driverController = new DriverControllerImpl();

	}

	@Override
	public void executePaymentItem(PaymentPO paymentpo) throws RemoteException {
		// TODO Auto-generated method stub
		// 更新银行账户
		babl.updateBankAccountPO(paymentpo.getBankAccountName(), paymentpo.getSystemCostPO().getTotalCost());
		// 向服务器插入审批后的付款单单据.
		pabl.addPayment(paymentpo);
		// 改变员工付款月份
		ArrayList<UserInfoPO> userInfoPOList = paymentpo.getSystemCostPO().getUserInfoList();
		for (int i = 0; i < userInfoPOList.size(); i++) {
			if (!userInfoPOList.get(i).getRole().equals("司机")) {
				getter.setLastPaymentMonth(userInfoPOList.get(i).getUserID(), paymentpo.getSystemCostPO().getMonth());
			} else {

				driverController.updateDriverMonth(userInfoPOList.get(i).getUserID(),
						userInfoPOList.get(i).getInstiID(), paymentpo.getSystemCostPO().getMonth());

			}
		}
		// 改变机构资金支付年份
		ArrayList<InstiInfoPO> instiInfoPOList = paymentpo.getSystemCostPO().getInstiInfoList();
		for (int i = 0; i < instiInfoPOList.size(); i++) {
			getter.setLastPaymentMonth(instiInfoPOList.get(i).getInstiID(), paymentpo.getSystemCostPO().getYear());
		}

	}

}
