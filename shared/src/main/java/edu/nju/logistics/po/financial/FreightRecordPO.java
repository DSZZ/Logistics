package edu.nju.logistics.po.financial;

import java.io.Serializable;
/**
 * 
 * @author 侍硕
 * 这是一个财务人员用于记录营业厅和中转中心的运费的类
 *
 */

public class FreightRecordPO implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	 /**
	  * 用于记录机构ID
	  */
      private String institutionID;
      
      /**
       * 用于记录运费数值
       */
      private double expense;
      /*
       * 用于记录日期
       */
      private  String date;
      
      public FreightRecordPO(String institutionID ,double  expense,String date){
    	  this.institutionID=institutionID;
    	  this.expense=expense;
    	  this.date=date;
      }
      
      public String  getInstitutionID(){
    	  return this.institutionID;
      }
      
  
      
      public double getExpense(){
    	  return this.expense;
      }
      
      public String getDate(){
    	  return this.date;
      }
      
      public void setInstitutionID(String InstituID){
    	   this.institutionID=InstituID;
      }
      
      
      public void  setExpense(double expense){
    	   this.expense=expense;
      }
      
      public void setDate(String date){
   	   this.date=date;
     }
}
