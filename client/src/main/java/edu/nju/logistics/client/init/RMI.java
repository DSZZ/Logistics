package edu.nju.logistics.client.init;

import java.io.BufferedReader;
import java.io.FileReader;

public class RMI {
       private static final String IP_PATH ="local/IP.txt";


	private RMI(){}
       
       
       public static String getIP(){
    	   BufferedReader reader=null;
    	   String line="";
    	   try{
    		   reader=new BufferedReader(new FileReader(IP_PATH));
    		   line=reader.readLine();
    	   }catch(Exception e){
    		   e.printStackTrace();
    	   }finally{
    		   try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	   }
    	   return line;
       }
       
//       public static void main(String[] args) {
//		System.out.println(RMI.getIP());
//	}
}
