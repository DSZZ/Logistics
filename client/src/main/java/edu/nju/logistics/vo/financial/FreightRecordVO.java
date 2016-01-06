package edu.nju.logistics.vo.financial;
/**
 * 
 * @author 侍硕
 * 这是一个财务人员用于记录营业厅和中转中心的运费的类
 *
 */
public class FreightRecordVO  {
	 
	private static final long serialVersionUID = 1L;
	 /**
	  * 用于记录机构ID
	  */
      private String institutionID;
      /**
       * 用于记录装车单的编号
       */
      private String sheetID;
      /**
       * 用于记录运费数值
       */
      private int expense;
      /*
       * 用于记录日期
       */
      private  String date;
      
      public FreightRecordVO(String institutionID ,String sheetID,int expense,String date){
    	  this.institutionID=institutionID;
    	  this.sheetID=sheetID;
    	  this.expense=expense;
    	  this.date=date;
      }
      
      public String  getInstitutionID(){
    	  return this.institutionID;
      }
      
      public String  getSheetID(){
    	  return this.sheetID;
      }
      
      public int getExpense(){
    	  return this.expense;
      }
      
      public String getDate(){
    	  return this.date;
      }
      
      public void setInstitutionID(String InstituID){
    	   this.institutionID=InstituID;
      }
      
           
      public void setSheetID(String SheetID){
    	   this.sheetID=SheetID;
      }
      
      public void  setExpense(int expense){
    	   this.expense=expense;
      }
      
      public void setDate(String date){
   	   this.date=date;
     }
}
