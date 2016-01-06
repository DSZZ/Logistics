package edu.nju.logistics.bl.impl.financialmanagement;

import java.util.ArrayList;

import edu.nju.logistics.bl.service.finacialmanagement.ReceiptControllerService;
import edu.nju.logistics.vo.courierIncomeReceipt.CourierIncomeReceiptVO;
import edu.nju.logistics.vo.courierIncomeReceipt.ReceiptInfo;

public class ReceiptController implements ReceiptControllerService {
	
	 private ReceiptBL bl ;
	 
	 public ReceiptController(ReceiptBL bl){
		 this.setBl(bl);
	 }
	 
	@Override
	public ArrayList<ReceiptInfo> getInfoList() {
		// TODO Auto-generated method stub
		return bl.getInfoList();
	}
	

	@Override
	public ArrayList<ReceiptInfo> getInfoListByDate(String date) {
		// TODO Auto-generated method stub
		return bl.getInfoListByDate(date);
	}

	@Override
	public ArrayList<ReceiptInfo> getInfoListByInstiID(String ID) {
		// TODO Auto-generated method stub
		return bl.getInfoListByInstiID(ID);
	}
	@Override
	public ArrayList<ReceiptInfo> getInfoListByBoth(String date, String ID) {
		// TODO Auto-generated method stub
		return bl.getInfoListByBoth(date, ID);
	}
	
	@Override
	public CourierIncomeReceiptVO getReceiptVO(String ID) {
		// TODO Auto-generated method stub
		return bl.getReceiptVO(ID);
	}

	@Override
	public void outputReceipts(ArrayList<String> IDs) {
		// TODO Auto-generated method stub
		bl.outputReceipts(IDs);
	}

	public ReceiptBL getBl() {
		return bl;
	}

	public void setBl(ReceiptBL bl) {
		this.bl = bl;
	}



}
