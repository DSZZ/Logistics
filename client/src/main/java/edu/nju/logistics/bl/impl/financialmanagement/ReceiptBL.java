package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.finacialmanagement.ReceiptBLService;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.finacialdata.CourierIncomeReceiptDataService;
import edu.nju.logistics.po.courierIncomeReceipt.CourierIncomeReceiptPO;
import edu.nju.logistics.po.courierIncomeReceipt.ShortOrderPO;
import edu.nju.logistics.vo.courierIncomeReceipt.CourierIncomeReceiptVO;
import edu.nju.logistics.vo.courierIncomeReceipt.ReceiptInfo;
import edu.nju.logistics.vo.courierIncomeReceipt.ShortOrderVO;
/**
 * 
 * @author 侍硕
 *
 */
public class ReceiptBL implements ReceiptBLService{
	
	  private ArrayList<CourierIncomeReceiptPO  > receiptPOList;
	  private ArrayList<CourierIncomeReceiptVO  > receiptVOList;
	  private ArrayList<ReceiptInfo  > receiptInfoList;
	  
	  private CourierIncomeReceiptDataService dataService ;
	  
	  public ReceiptBL (){
		       try{
 		             this.dataService = 
 		            		 (CourierIncomeReceiptDataService) Naming.lookup
 		            		 ("rmi://"+RMI.getIP() + ":2014/"+"CourierIncomeReceiptDSImpl");
 	                 System.out.println("ReceiptBL--CourierIncomeReceiptDataService awake!");
 	           }catch (Exception e) {
 		             System.out.println("ReceiptBL--CourierIncomeReceiptDS failed");
			         e.printStackTrace();
	           }
		  receiptPOList =  new ArrayList<>();
		  receiptVOList = new ArrayList<>();
		  receiptInfoList = new ArrayList<>();
		  
		  findReceiptList();
	  }

	@Override
	public void  findReceiptList() {
		// TODO Auto-generated method stub
          try {
			
			      receiptPOList =dataService.getAll();
			      System.out.println("The Receipt on server is :");
			      for(int i=0;i<receiptPOList.size();i++){
				           System.out.println(receiptPOList.get(i).getReceiptID()+" "
				        		   +receiptPOList.get(i).getTotalFee());
			      }
	      } catch (RemoteException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		  ConvertToReceiptVOList();
		  ConvetToReceiptInfoList();
    
	}
	
	
	/**
	 * 返回全部收款单信息
	 */
	@Override
	public ArrayList<ReceiptInfo> getInfoList() {
		// TODO Auto-generated method stub
		return  this.receiptInfoList;
	}
	@Override
	public ArrayList<ReceiptInfo> getInfoListByInstiID(String ID) {
		// TODO Auto-generated method stub
		ArrayList<ReceiptInfo > temp = new ArrayList<>();
		for(int i=0; i<this.receiptInfoList.size();i++){
			if(this.receiptInfoList.get(i).getInstitutionID().equals(ID)){
				temp.add(this.receiptInfoList.get(i));
			}
		}
		return  temp;
	}
	
	@Override
	public ArrayList<ReceiptInfo> getInfoListByDate(String date) {
		// TODO Auto-generated method stub
		ArrayList<ReceiptInfo > temp = new ArrayList<>();
		for(int i=0; i<this.receiptInfoList.size();i++){
			if(this.receiptInfoList.get(i).getDate().equals(date)){
				temp.add(this.receiptInfoList.get(i));
			}
		}
		return  temp;
	}
	
	
	@Override
	public ArrayList<ReceiptInfo> getInfoListByBoth(String date, String ID) {
		// TODO Auto-generated method stub
		ArrayList<ReceiptInfo > temp = getInfoListByDate(date);
		ArrayList<ReceiptInfo > result = new ArrayList<>();
		for(int i=0;i<temp.size();i++){
			if(temp.get(i).getInstitutionID().equals(ID)){
				result.add(temp.get(i));
			}
		}
		return result;
	}

	@Override
	public CourierIncomeReceiptVO getReceiptVO(String ID) {
		// TODO Auto-generated method stub
		for(int i=0;i<receiptVOList.size();i++){
			if(receiptVOList.get(i).getReceiptID().equals(ID)){
				    return  receiptVOList.get(i);
			}
		}
		System.out.println("Receiptbl - getReceiptVO--收款单ID不存在！");
		return null;
	}
   
	@Override
	public ReceiptInfo ReceiptVOToInfo(CourierIncomeReceiptVO vo) {
		// TODO Auto-generated method stub
		   ReceiptInfo info = new ReceiptInfo(vo.getReceiptID(),vo.getTime(), 
				   vo.getTotalFee(), vo.getInstitutionid());
	       return info;
	}

	@Override
	public CourierIncomeReceiptVO ReceiptPOToVO(CourierIncomeReceiptPO po) {
		// TODO Auto-generated method stub
		
		CourierIncomeReceiptVO receiptVO = new CourierIncomeReceiptVO
				(ConvertToOrderVOList(po.getOrderList()),po.getReceiptID(), po.getTime(), po.getInstitutionid());		
		return receiptVO;
		
		
	}

	@Override
	public  ShortOrderVO ShortOrderPOToVO(ShortOrderPO po) {
		
		// TODO Auto-generated method stub
		ShortOrderVO orderVO = new ShortOrderVO(po.getId(), po.getFee(), po.getCourierid(), po.getCouriername());
		return orderVO;
		
		
	}

	@Override
	public void outputReceipts(ArrayList<String> IDs) {
		// TODO Auto-generated method stub
		
	}


	
	public void ConvertToReceiptVOList(){
		   for(int i=0;i<receiptPOList.size();i++){
			   receiptVOList.add(ReceiptPOToVO(this.receiptPOList.get(i)));
		   }
	}
    
	public void ConvetToReceiptInfoList(){
		    for(int i=0;i<receiptVOList.size();i++){
			   receiptInfoList.add(ReceiptVOToInfo(this.receiptVOList.get(i)));
		   }
	}
	
	public ArrayList<ShortOrderVO> ConvertToOrderVOList(ArrayList<ShortOrderPO> poList){
		ArrayList<ShortOrderVO> voList = new ArrayList<>();
		   for(int  i =0 ; i< poList.size() ;i++){
			   voList.add(ShortOrderPOToVO(poList.get(i)));
		   }
			   
		return voList;	   
	}


}
