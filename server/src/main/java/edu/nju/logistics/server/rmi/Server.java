package edu.nju.logistics.server.rmi;

import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import edu.nju.logistics.data.service.branchdata.BranchDataService;
import edu.nju.logistics.data.service.bufferdata.BufferDataService;
import edu.nju.logistics.data.service.constantdata.ConstantDataService;
import edu.nju.logistics.data.service.employeedata.EmployeeDataService;
import edu.nju.logistics.data.service.finacialdata.AccountInitDataService;
import edu.nju.logistics.data.service.finacialdata.BankAccountDataService;
import edu.nju.logistics.data.service.finacialdata.CourierIncomeReceiptDataService;
import edu.nju.logistics.data.service.finacialdata.FreightRecordDataService;
import edu.nju.logistics.data.service.finacialdata.PaymentDataService;
import edu.nju.logistics.data.service.identitydata.IdentityDataService;
import edu.nju.logistics.data.service.institutiondata.InstitutionDataService;
import edu.nju.logistics.data.service.logdata.LogDataService;
import edu.nju.logistics.data.service.orderdata.OrderDataService;
import edu.nju.logistics.data.service.storagedata.StorageDataService;
import edu.nju.logistics.data.service.transfersheetdata.TransferSheetDataService;
import edu.nju.logistics.server.data.impl.branchdata.BranchDataManager;
import edu.nju.logistics.server.data.impl.bufferdata.BufferData;
import edu.nju.logistics.server.data.impl.constantdata.ConstantData;
import edu.nju.logistics.server.data.impl.employeedata.EmployeeData;
import edu.nju.logistics.server.data.impl.finacialdata.AccountInitDSImpl;
import edu.nju.logistics.server.data.impl.finacialdata.BankAccountDSImpl;
import edu.nju.logistics.server.data.impl.finacialdata.CourierIncomeReceiptDSImpl;
import edu.nju.logistics.server.data.impl.finacialdata.FreightRecordDSImpl;
import edu.nju.logistics.server.data.impl.finacialdata.PaymentDSImpl;
import edu.nju.logistics.server.data.impl.identitydata.IdentityData;
import edu.nju.logistics.server.data.impl.institutiondata.InstitutionData;
import edu.nju.logistics.server.data.impl.logdata.LogData;
import edu.nju.logistics.server.data.impl.orderdata.OrderDataManager;
import edu.nju.logistics.server.data.impl.storagedata.StorageData;
import edu.nju.logistics.server.data.impl.transfersheetdata.TransferSheetData;
import edu.nju.logistics.server.ui.ErrorFrame;
import edu.nju.logistics.server.ui.ServerFrame;

/**
 * rmi远程调用
 * 
 * @author 董轶波
 *
 */
public class Server {
	public static void main(String[] args) {
		new Server();
	}

	private static final String IP = "127.0.0.1";

	public Server() {

		try {
			LocateRegistry.createRegistry(2014);
			// 身份验证数据
			IdentityDataService identityData = new IdentityData();
			// 员工数据
			EmployeeDataService employeeData = new EmployeeData();
			// 机构信息
			InstitutionDataService institutionData = new InstitutionData();
			// 常量数据
			ConstantDataService constantData = new ConstantData();
			// 订单数据
			OrderDataService orderData = new OrderDataManager(institutionData);
			// 营业厅车辆司机数据
			BranchDataService branchData = new BranchDataManager(institutionData);
			// 日志数据
			LogDataService logData = new LogData();
			// 审批单据数据
			BufferDataService bufferData = new BufferData();
			// 库存数据
			StorageDataService storageData = new StorageData();
			// 中转单数据
			TransferSheetDataService transferSheetData = new TransferSheetData();
			// 银行账户数据
			BankAccountDataService bankAccountDSImpl = new BankAccountDSImpl();
			// 运费信息数据
			FreightRecordDataService freightRecordDSImpl = new FreightRecordDSImpl();
			// 付款单数据
			PaymentDataService paymentDSImpl = new PaymentDSImpl();
			// 营业厅收款单数据
			CourierIncomeReceiptDataService courierIncomeReceiptDSImpl = new CourierIncomeReceiptDSImpl();
			//期初建账账目数据
			AccountInitDataService accountInitDSImpl =  new AccountInitDSImpl();
			Naming.rebind("rmi://" + IP + ":2014/AccountInitDSImpl", accountInitDSImpl);
			Naming.rebind("rmi://" + IP + ":2014/CourierIncomeReceiptDSImpl", courierIncomeReceiptDSImpl);
			Naming.rebind("rmi://" + IP + ":2014/FreightRecordDSImpl", freightRecordDSImpl);
			Naming.rebind("rmi://" + IP + ":2014/BankAccountDSImpl", bankAccountDSImpl);
			Naming.rebind("rmi://" + IP + ":2014/PaymentDSImpl", paymentDSImpl);
			Naming.rebind("rmi://" + IP + ":2014/IdentityData", identityData);
			Naming.rebind("rmi://" + IP + ":2014/EmployeeData", employeeData);
			Naming.rebind("rmi://" + IP + ":2014/InstitutionData", institutionData);
			Naming.rebind("rmi://" + IP + ":2014/ConstantData", constantData);
			Naming.rebind("rmi://" + IP + ":2014/OrderData", orderData);
			Naming.rebind("rmi://" + IP + ":2014/BranchData", branchData);
			Naming.rebind("rmi://" + IP + ":2014/LogData", logData);
			Naming.rebind("rmi://" + IP + ":2014/BufferData", bufferData);
			Naming.rebind("rmi://" + IP + ":2014/StorageData", storageData);
			Naming.rebind("rmi://" + IP + ":2014/TransferSheetData", transferSheetData);
			Naming.rebind("rmi://" + IP + ":2014/ConnectionTester", new ConnectionTester());
			System.out.println("连接成功！");
			try {
				new ServerFrame();
			} catch (UnknownHostException e) {}
			

		} catch (Exception e) {
			new ErrorFrame();
		}

	}

}
