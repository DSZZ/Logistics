package edu.nju.logistics.vo.courierIncomeReceipt;
/**
 * 在查看收款单界面，需要罗列出所有收款单。
 * 其中的每一条目就是这个类所承载的信息。
 * @author 侍硕
 *
 */
public class ReceiptInfo {
	private String receiptID;
	private String date;
	private double totalFee = 0;
	private String institutionID;
	
	public ReceiptInfo(String ID,String date,double totalFee ,String instiID){
		   this.setReceiptID(ID);
		   this.setDate(date);
		   this.setTotalFee(totalFee);
		   this.setInstitutionID(instiID);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public String getInstitutionID() {
		return institutionID;
	}

	public void setInstitutionID(String institutionID) {
		this.institutionID = institutionID;
	}

	public String getReceiptID() {
		return receiptID;
	}

	public void setReceiptID(String receiptID) {
		this.receiptID = receiptID;
	}
	
	
}
