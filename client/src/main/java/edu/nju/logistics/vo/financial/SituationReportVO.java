package edu.nju.logistics.vo.financial;


import java.util.ArrayList;

import edu.nju.logistics.vo.courierIncomeReceipt.CourierIncomeReceiptVO;
/**
 * 
 * @author 侍硕  
 * 经营情况表
 *
 */
public class SituationReportVO  {
    private ArrayList<CourierIncomeReceiptVO> receiptList;
    private ArrayList<PaymentVO>  paymentList;
    
    public SituationReportVO(ArrayList<CourierIncomeReceiptVO> receiptList,
    		ArrayList<PaymentVO>  paymentList	){
    	this.receiptList=receiptList;
    	this.paymentList=paymentList;
    }
    
    public void setReceiptList(ArrayList<CourierIncomeReceiptVO> receiptList){
    	this.receiptList=receiptList;
    }
    
    public void setPaymentList(ArrayList<PaymentVO>  paymentList){
    	this.paymentList=paymentList;
    }
    
    public ArrayList<CourierIncomeReceiptVO> getReceiptList(){
    	return this.receiptList;
    }
    
    public ArrayList<PaymentVO> getPaymentList(){
    	return this.paymentList;
    }
}
