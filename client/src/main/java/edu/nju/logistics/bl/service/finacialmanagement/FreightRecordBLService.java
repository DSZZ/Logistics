package edu.nju.logistics.bl.service.finacialmanagement;

import java.util.ArrayList;

import edu.nju.logistics.po.financial.FreightRecordPO;
/**
 * 该接口专门用来对运费记录进行读写属于BL层
 * @author 侍硕
 *
 */
public interface FreightRecordBLService {
	    /**
	     * 委托DS向服务器插入一个运费记录
	     * @param freightpo
	     */
         public  void   addFreightRecordPO(FreightRecordPO freightpo);
         /**
          * 委托DS向服务器插入一系列运费记录
          * @param list
          */
         public void    addFreightRecordPOList(ArrayList<FreightRecordPO>  list);
         /**
          * 委托DS从服务器读取全部运费记录
          * @return
          */
         public ArrayList<FreightRecordPO>     getAllFreightRecordPO();
         
}
