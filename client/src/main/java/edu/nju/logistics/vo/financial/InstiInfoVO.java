package edu.nju.logistics.vo.financial;
/**
 * 计算系统成本时使用的机构信息项
 * 包括机构ID、租金、总运费
 * @author 侍硕
 *
 */
public class InstiInfoVO {
        String InstiID;
        double rent;
        double totalFreight;
        public InstiInfoVO (String InstiID,double rent , double totalFreight){
        	 this.InstiID=InstiID;
        	 this.rent=rent;
        	 this.totalFreight=totalFreight;
        }
        
        public String getInstiID(){
        	return this.InstiID;
        }
        
        public double getRent(){
        	return this.rent;
        }
        
        public double getTotalFreight(){
        	return this.totalFreight;
        }
        
        public void setInstiID(String instiID){
        	  this.InstiID = instiID;
        }
        
        public void setRent(double rent){
        	this.rent = rent;
        }
        
        public void setTotalFreight(double totalFreight){
        	this.totalFreight=totalFreight;
        }
}
