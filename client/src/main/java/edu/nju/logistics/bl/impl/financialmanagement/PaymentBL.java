package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.impl.examinebl.ExamineBL;
import edu.nju.logistics.bl.service.finacialmanagement.PaymentBLService;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.finacialdata.PaymentDataService;
import edu.nju.logistics.po.financial.PaymentPO;
import edu.nju.logistics.po.financial.SystemCostPO;
import edu.nju.logistics.vo.financial.PaymentInfo;
import edu.nju.logistics.vo.financial.PaymentVO;
import edu.nju.logistics.vo.financial.SystemCostVO;

public class PaymentBL implements PaymentBLService{
      /*
       * 付款单的数据存取逻辑对象
       */
	 private PaymentDataService dataService;
     /*
      * 创建付款单的逻辑对象
      */
     private PaymentList  paymentList;
     /*
      * 总经理审批的逻辑对象
      */
     private ExamineBL examinebl;
     /*
      * 计算系统成本的逻辑对象
      */
     private CostCalculatorBL costbl ;

     /*
      * 所有历史付款单的PO
      */
     private ArrayList<PaymentPO> paymentPOList;
     /*
      * 与PO同步的VO
      */
     private ArrayList<PaymentVO> paymentVOList;
     /*
      * 用于历史付款单列表显示的概要信息
      */
     private ArrayList<PaymentInfo> paymentInfoList;
    
     
     public PaymentBL () throws RemoteException{
    	 costbl = new CostCalculatorBL();
    	 examinebl = new ExamineBL();
    	 paymentList = new PaymentList(costbl,examinebl);
    	 
    	 paymentPOList = new ArrayList<>();
    	 paymentVOList = new ArrayList<>();
    	 paymentInfoList = new ArrayList<>();
    	 
   	     try{
   		        this.dataService = (PaymentDataService) Naming.lookup("rmi://"+RMI.getIP() + ":2014/"+"PaymentDSImpl");
   	            System.out.println("PaymentDataService awake!");
   	     } catch (Exception e) {
   		        System.out.println("PaymentDS failed");
 			    e.printStackTrace();
 	    }
   	     findPaymentPOList();
     }
     
	@Override
	public void findPaymentPOList() {
		// TODO Auto-generated method stub
		try {
			
			paymentPOList =dataService.getAll();
			System.out.println("The paymentID on server is :");
			for(int i=0;i<paymentPOList.size();i++){
				System.out.println(paymentPOList.get(i).getId());
			}
	    } catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Get all paymentpo from the server!");
		
		ConvertToVOList();
		ConvertToInfoList();
	}

	@Override
	public ArrayList<PaymentVO> getPaymentVOList() {
		// TODO Auto-generated method stub
		return paymentVOList;
	}
	
	@Override
	public ArrayList<PaymentInfo> getPaymentInfoList() {
		// TODO Auto-generated method stub
		return paymentInfoList;
	}

	@Override
	public PaymentVO showOldChart(String ID) {
		// TODO Auto-generated method stub
		for(int i=0;i<paymentVOList.size();i++){
			if (paymentVOList.get(i).getId().equals(ID)){
				   return paymentVOList.get(i);
			}
		}
		System.out.println("不存在这样的付款单VO");
		return null;
	}

	@Override
	public PaymentVO showNewChart() throws RemoteException {
		// TODO Auto-generated method stub
		PaymentVO newPayment  =   paymentList.createNewPayment();
		return  newPayment;
	}

	@Override
	public void createPayment(PaymentVO paymentVO) {
		// TODO Auto-generated method stub
		//将UI层的PaymentVO持久化并送往总经理审批
		PaymentPO po  =  PaymentVOtoPO(paymentVO);
		this.paymentPOList.add(po);
		this.paymentVOList.add(paymentVO);
		System.out.println("PaymentBL--createPayment : addVO : "+paymentVO.getId());
		this.paymentInfoList.add(PaymentVOtoInfo(paymentVO));
		/**
		 * 送往总经理审批
		 */
	     examinebl.insertSheet(po);
	}


	
	
	
	public void ConvertToVOList() {
		// TODO Auto-generated method stub
		
		if(paymentPOList==null){
			System.out.println("paymentVOListPO is empty!'");
		}else{
			for(int i=0;i<paymentPOList.size();i++){
				paymentVOList.add(PaymentPOtoVO(paymentPOList.get(i)));
			}
		}
		
	}
	
	/**
	 *  该方法将paymentVOlist 转化为paymentInfoList
	 * @return
	 */
	public void ConvertToInfoList() {
		// TODO Auto-generated method stub
		
		if(paymentVOList==null){
			System.out.println("paymentVOListVO is empty!'");
		}else{
			for(int i=0;i<paymentVOList.size();i++){
				paymentInfoList.add(PaymentVOtoInfo(paymentVOList.get(i)));
			}
		}
		
	}

	@Override
	public PaymentPO PaymentVOtoPO(PaymentVO vo) {
		// TODO Auto-generated method stub
		SystemCostPO costpo   =  costbl.SystemCostVOToPO(vo.getSystemCostVO());
		PaymentPO   po  =  new PaymentPO(vo.getId(), vo.getEmployeeId(), "付款单", 
				vo.getDate(), vo.getState(),vo.getDescription(), costpo,vo.getBankAccountName());
		return po;
	}
	
	
	@Override
	public PaymentVO PaymentPOtoVO(PaymentPO po) {
		// TODO Auto-generated method stub
		SystemCostVO costvo   =  costbl.SystemCostPOtoVO(po.getSystemCostPO());
		 PaymentVO vo = new PaymentVO(po.getId(), po.getEmployeeId(), po.getType(), 
			po.getDate(), po.getState(), po.getDescription(), costvo,po.getBankAccountName());
		return vo;
	}
	
	@Override
	public PaymentInfo PaymentVOtoInfo(PaymentVO vo){
		PaymentInfo info = new PaymentInfo(vo.getId(), vo.getEmployeeId(), vo.getDate(), vo.getSystemCostVO().getTotalCost(),vo.getState());
		return info;
	}
    


}
