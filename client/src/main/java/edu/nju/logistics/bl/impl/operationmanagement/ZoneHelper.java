package edu.nju.logistics.bl.impl.operationmanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class ZoneHelper {
     private ZoneHelper(){}
     
     public static final int ZONE_NUM = 4;
     
     public static final int HALL_NUM = 20;
     
     public static final int COUNT1 = 25;
     
     public static final int COUNT2 = 5;
     
     public static final String FILE="File/manager/zone.txt";
     
     public static HashMap<String ,HashMap<Integer,String>> zoneMap=new HashMap<>();
     
     public static HashMap<Integer,String> hallMap = new HashMap<>();
     
     static{
//
//    	 String prompt="该中转中心尚未创建";
//    	 zoneMap.put("", )
//    	 
//    	 
    	 BufferedReader reader=null;
    	 
    	 try{
    	 reader=new BufferedReader(new FileReader(FILE));
    	 String line="";
    	 while((line=reader.readLine())!=null){
    		 String single[]=line.split("；");
    		 String city=single[0];
    		 String zone1=single[1];
    		 String zone2=single[2];
    		 String zone3=single[3];
    		 String zone4=single[4];
    		 zoneHelper(city, zone1, zone2, zone3, zone4);
       	 }
    	 
    	 }catch(Exception e){
    		 e.printStackTrace();
    	   }try{
    		   reader.close();
    	   }catch(Exception e){
    		   e.printStackTrace();
    	   }
     }
     
     static{
    	 for (int i = 0; i < 5; i++) {
    		 hallMap.put( 0+i*HALL_NUM, "新华"+destionationGet(i));
        	 hallMap.put( 1+i*HALL_NUM, "海渡"+destionationGet(i));
        	 hallMap.put( 2+i*HALL_NUM, "普盛"+destionationGet(i));
        	 hallMap.put( 3+i*HALL_NUM, "裕龙"+destionationGet(i));
        	 hallMap.put( 4+i*HALL_NUM, "枫槿"+destionationGet(i));
        	 hallMap.put( 5+i*HALL_NUM, "万通"+destionationGet(i));
        	 hallMap.put( 6+i*HALL_NUM, "申滨"+destionationGet(i));
        	 hallMap.put( 7+i*HALL_NUM, "希陵"+destionationGet(i));
        	 hallMap.put( 8+i*HALL_NUM, "百阳"+destionationGet(i));
        	 hallMap.put( 9+i*HALL_NUM, "笛石桥"+destionationGet(i));
        	 hallMap.put( 10+i*HALL_NUM, "新街口"+destionationGet(i));
        	 hallMap.put( 11+i*HALL_NUM, "熙石门"+destionationGet(i));
        	 hallMap.put( 12+i*HALL_NUM, "临安"+destionationGet(i));
        	 hallMap.put( 13+i*HALL_NUM, "虎骏"+destionationGet(i));
        	 hallMap.put( 14+i*HALL_NUM, "乐市"+destionationGet(i));
        	 hallMap.put( 15+i*HALL_NUM, "斐路"+destionationGet(i));
        	 hallMap.put( 16+i*HALL_NUM, "帆江"+destionationGet(i));
        	 hallMap.put( 17+i*HALL_NUM, "恒达"+destionationGet(i));
        	 hallMap.put( 18+i*HALL_NUM, "涪凌"+destionationGet(i));
        	 hallMap.put( 19+i*HALL_NUM, "曲庆"+destionationGet(i));
		}
    	 
     }
     
     public static String destionationGet(int i){
    	 switch(i){
    	 case 0:return "东";
    	 case 1:return "西";
    	 case 2:return "南";
    	 case 3:return "北";
    	 case 4:return "中";
    	 default:return "";
    	 }
     }
     
     public static void zoneHelper(String city,String zone1,String zone2,String zone3,String zone4){
    	 
    	 HashMap<Integer,String> mapTemp=new HashMap<>();
    	 for (int i = 0; i < COUNT1; i++) {
    		if(i==0){
    		mapTemp.put(0, zone1+"区");
			mapTemp.put(1, zone2+"区");
			mapTemp.put(2, zone3+"区");
			mapTemp.put(3, zone4+"区");
    		 }
    		else{
    		mapTemp.put(0+i*ZONE_NUM, zone1+i+"区");
			mapTemp.put(1+i*ZONE_NUM, zone2+i+"区");
			mapTemp.put(2+i*ZONE_NUM, zone3+i+"区");
			mapTemp.put(3+i*ZONE_NUM, zone4+i+"区"); 
		
    		}
    	 }
    	 zoneMap.put(city,mapTemp);
    	 
     }
     public static void main(String[] args) {
//    	 System.out.println(ZoneHelper.zoneMap.get("银川").get(96));
//    	 System.out.println(ZoneHelper.zoneMap.get("武汉").get(97));
//    	 System.out.println(ZoneHelper.zoneMap.get("南宁").get(98));
//    	 System.out.println(ZoneHelper.zoneMap.get("呼和浩特").get(99));
    	 for (int i = 0; i < 100; i++) {
    		 System.out.println(ZoneHelper.zoneMap.get("南京").get(2));
		}


	}
}
