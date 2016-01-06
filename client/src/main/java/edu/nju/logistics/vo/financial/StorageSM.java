package edu.nju.logistics.vo.financial;

import java.io.Serializable;

/**
 * 库存快照
 * 包含总容量，总库存，各区容量，各区实际占用量
 * @author 67534
 *
 */
public class StorageSM  implements Serializable{
         /**
	       * 
	       */
	private static final long serialVersionUID = 1L;
		private   String instiID;
         private int totalCapacity;
         private int totalOccupancy;
         
         private int shipCapacity;
         private int shipOccupancy;
         private int trailCapacity;
         private int trailOccupancy;
         private int carCapacity;
         private int carOccupancy;
         private int mobileCapacity;
         private int mobileOccupancy;
         public StorageSM(String instiID ,  int shipCapacity,  int shipOccupancy    ,
        		 int trailCapacity,int trailOccupancy,int carCapacity,int carOccupancy,
                   int mobileCapacity,  int mobileOccupancy){
        	 
        	   this.setInstiID(instiID);
        	   this.shipCapacity = shipCapacity;
        	   this.shipOccupancy = shipOccupancy;
        	   this.trailCapacity = trailCapacity;
        	   this.trailOccupancy = trailOccupancy;
        	   this.carCapacity = carCapacity;
        	   this.carOccupancy = carOccupancy;
        	   this.mobileCapacity = mobileCapacity;
        	   this.mobileOccupancy = mobileOccupancy;
        	   
        	   this.setTotalCapacity(this.shipCapacity + this.trailCapacity + this.carCapacity + this.mobileCapacity);
        	   this.setTotalOccupancy(this.shipOccupancy + this.trailOccupancy + this.carOccupancy + this.mobileOccupancy);
         }
		public String getInstiID() {
			return instiID;
		}
		public void setInstiID(String instiID) {
			this.instiID = instiID;
		}
		public int getTotalCapacity() {
			return totalCapacity;
		}
		public void setTotalCapacity(int totalCapacity) {
			this.totalCapacity = totalCapacity;
		}
		public int getTotalOccupancy() {
			return totalOccupancy;
		}
		public void setTotalOccupancy(int totalOccupancy) {
			this.totalOccupancy = totalOccupancy;
		}
}
