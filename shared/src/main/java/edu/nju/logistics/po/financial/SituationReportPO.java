package edu.nju.logistics.po.financial;

import java.io.Serializable;
import java.util.ArrayList;

import edu.nju.logistics.vo.courierIncomeReceipt.CourierIncomeReceiptVO;
/**
 * 
 * @author 侍硕  
 * 经营情况表
 *
 */
public class SituationReportPO implements Serializable {
	private static final long serialVersionUID = 1L;
    private ArrayList<CourierIncomeReceiptVO> receiptList;
    private ArrayList<PaymentPO>  paymentList;
    
    public SituationReportPO(ArrayList<CourierIncomeReceiptVO> receiptList,
    		ArrayList<PaymentPO>  paymentList	){
    	this.receiptList=receiptList;
    	this.paymentList=paymentList;
    }
    
    public void setReceiptList(ArrayList<CourierIncomeReceiptVO> receiptList){
    	this.receiptList=receiptList;
    }
    
    public void setPaymentList(ArrayList<PaymentPO>  paymentList){
    	this.paymentList=paymentList;
    }
    
    public ArrayList<CourierIncomeReceiptVO> getReceiptList(){
    	return this.receiptList;
    }
    
    public ArrayList<PaymentPO> getPaymentList(){
    	return this.paymentList;
    }
    

}
