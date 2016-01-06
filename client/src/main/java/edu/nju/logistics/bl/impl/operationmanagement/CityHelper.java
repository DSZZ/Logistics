package edu.nju.logistics.bl.impl.operationmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 城市类
 * @author 董轶波
 *
 */
public class CityHelper {
	
     private CityHelper(){}

     public static HashMap<String,String> getCityMap(){
    	 HashMap<String,String> map=new HashMap<String ,String>();
    	 
    	 map.put("北京", "0010");
    	 map.put("广州", "0020");
    	 map.put("上海", "0021");
    	 map.put("南京", "0025");
    	 map.put("天津", "0022");
    	 map.put("重庆", "0023");
    	 map.put("石家庄","0311");
    	 map.put("太原", "0351");
    	 map.put("郑州", "0371");
    	 map.put("沈阳", "0024");
    	 map.put("哈尔滨", "0451");
    	 map.put("长春", "0431");
    	 map.put("杭州", "0571");
    	 map.put("福州", "0591");
    	 map.put("合肥", "0551");
    	 map.put("济南", "0531");
    	 map.put("南昌", "0791");
    	 map.put("长沙", "0731");
    	 map.put("武汉", "0027");
    	 map.put("成都", "0028");
    	 map.put("西安", "0029");
    	 map.put("海口", "0898");
    	 map.put("西宁", "0971");
    	 map.put("贵阳", "0851");
    	 map.put("兰州", "0931");
    	 map.put("昆明", "0871");
    	 map.put("呼和浩特", "0471");
    	 map.put("乌鲁木齐", "0991");
    	 map.put("拉萨", "0891");
    	 map.put("南宁", "0771");
    	 map.put("银川", "0951");
		
    	 return map;
    	 
     }
     /**
      * 获得所有城市
      * @return
      */
     public static ArrayList<String> getCityName(){
    	 ArrayList<String> city=new ArrayList<String>();
    	 
    	 HashMap<String,String> map=CityHelper.getCityMap();
    	 Set<Entry<String,String>> entryset=map.entrySet();
 		 for(Entry<String,String> arg:entryset){
 			 city.add(arg.getKey());
 		 }
    	 return city; 
     }
     public static String getCityNameByID(String centerID){
    	 String city="";
    	 HashMap<String,String> map=CityHelper.getCityMap();
    	 Set<Entry<String,String>> entryset=map.entrySet();
 		 for(Entry<String,String> arg:entryset){
 			 if(arg.getValue().equals(centerID.substring(0,centerID.length()-2))){
 				 city=arg.getKey();
 			 }
 		 }
    	 return city; 
     }

}



