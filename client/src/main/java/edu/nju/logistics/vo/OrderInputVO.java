package edu.nju.logistics.vo;

import edu.nju.logistics.po.ExpressType;
import edu.nju.logistics.po.PackageType;

/**
 * 
 * @author 侍硕
 * 寄件单
 */
public class OrderInputVO {
	/**
	 * 寄件人信息：姓名/地址/公司/电话/手机
	 */
	private String senderName;
    private String senderAddress;
    private String senderCompany;
    private String senderTelephone;
    private String senderMobile;
    /**
     * 收件人信息：姓名/地址/公司/电话/手机
     */
    private String receiverName;
    private String receiverAddress;
    private String receiverCompany;
    private String receiverTelephone;
    private String receiverMobile;
    /**
     * 货物信息：原件数量/重量/体积/名称
     */
    private int    commodityNumber;
    private double commodityWeight;
    private double commodityVolume;
    private String commodityName;
    /**
     * 订单信息
     */
    private String BarCode;
    private String CourierID;
    private ExpressType expressType;
    private PackageType packType;
    private double GeneralFee;
       
      /**
       * 构造函数
       * @param senderName
       * @param senderAddress
       * @param senderCompany
       * @param senderTelephone
       * @param senderMobile
       * @param receiverName
       * @param receiverAddress
       * @param receiverCompany
       * @param receiverTelephone
       * @param receiverMobile
       * @param commodityNumber
       * @param commodityWeight
       * @param commodityVolume
       * @param commodityName
       * @param BarCode
       * @param expressType
       * @param GeneralFee
       */
      public OrderInputVO(     
      String senderName,
      String senderAddress,
      String senderCompany,
      String senderTelephone,
      String senderMobile,
     
      String receiverName,
      String receiverAddress,
      String receiverCompany,
      String receiverTelephone,
      String receiverMobile,
    
      int    commodityNumber,
      double commodityWeight,
      double commodityVolume,
      String commodityName,
    
      String BarCode,
      String CourierID,
      ExpressType expressType,
      PackageType packType,
      double GeneralFee                     ){
    	  this.senderName=senderName;
    	  this.senderAddress=senderAddress;
    	  this.senderCompany=senderCompany;
    	  this.senderTelephone=senderTelephone;
    	  this.senderMobile=senderMobile;
    	  
    	  this.receiverName=senderName;
    	  this.receiverAddress=senderAddress;
    	  this.receiverCompany=senderCompany;
    	  this.receiverTelephone=senderTelephone;
    	  this.receiverMobile=senderMobile;
    	  
    	  this.commodityName=commodityName;
    	  this.commodityNumber=commodityNumber;
    	  this.commodityVolume=commodityVolume;
    	  this.commodityWeight=commodityWeight;
    	  
    	  this.BarCode=BarCode;
    	  this.CourierID = CourierID;
    	  this.expressType=expressType;
    	  this.packType=packType;
    	  this.GeneralFee=GeneralFee;
    	  
      }
}
