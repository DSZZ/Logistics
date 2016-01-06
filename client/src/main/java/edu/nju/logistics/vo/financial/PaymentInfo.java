package edu.nju.logistics.vo.financial;
/**
 * 用于界面表格显示的付款单概要信息
 * @author 侍硕
 *
 */
public class PaymentInfo {
	     /**
	      * 付款单单号
	      */
           private String  sheetID;
          /**
           * 财务人员ID
           */
           private String  accountantID;
           /**
            * 创建日期
            */
           private String   date;
           /**
            * 总付款额
            */
           private double totalFee;
           /**
            * 付款单的当前状态;
            */
           private String state;
           
           
           public PaymentInfo (String sheetID ,String accountantID,String date ,double totalFee,String state){
        	             this.setSheetID(sheetID);
        	             this.setAccountantID(accountantID);
        	             this.setDate(date);
        	             this.setTotalFee(totalFee);
        	             this.setState(state);
           }

		public String getSheetID() {
			return sheetID;
		}

		public void setSheetID(String sheetID) {
			this.sheetID = sheetID;
		}

		public String getAccountantID() {
			return accountantID;
		}

		public void setAccountantID(String accountantID) {
			this.accountantID = accountantID;
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

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}
           
           
}
