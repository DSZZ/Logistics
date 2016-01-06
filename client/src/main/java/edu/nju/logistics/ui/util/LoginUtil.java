package edu.nju.logistics.ui.util;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * 用与保存账号的工具类
 * @author 董轶波
 *
 */
public class LoginUtil {

	
	private LoginUtil() {}
	
	public static void save(String str){
		ObjectOutputStream oos=null;
		try {
			oos=new ObjectOutputStream(new FileOutputStream("File/save.dat"));
			oos.writeObject(str);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				oos.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static String load(){
		ObjectInputStream ois=null;
		String str="";
		try{
			ois=new ObjectInputStream(new FileInputStream("File/save.dat"));
			str=(String)ois.readObject();
		}catch(Exception e){
			System.err.print("无登录记录");
			e.printStackTrace();
		}finally{
			try{
				ois.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return str;
	}
}
