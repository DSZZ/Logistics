package edu.nju.logistics.server.data.impl.bufferdata;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import edu.nju.logistics.data.service.bufferdata.BufferDataService;
import edu.nju.logistics.po.SheetPO;

/**
 * 单据数据 序列化
 * 
 * @author 董轶波
 *
 */
public class BufferData extends UnicastRemoteObject implements BufferDataService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String FILE = "File/manager/sheet.txt";

	private ArrayList<SheetPO> po = null;

	public BufferData() throws RemoteException {
	}

	/**
	 * 获得所有的单据数据
	 * 
	 * @return
	 * @throws RemoteException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<SheetPO> getAll() throws RemoteException {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(FILE));
			this.po = (ArrayList<SheetPO>) ois.readObject();
		} catch (Exception e) {
			return new ArrayList<SheetPO>();
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
			}
		}
		// BufferedReader reader=null;
		// String line="";
		// try {
		// reader=new BufferedReader(new FileReader(FILE));
		// while((line=reader.readLine())!=null){
		// this.handleDataFrom(line);
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }finally{
		// try {
		// reader.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		return this.po;
	}

	// /**
	// * 从文本处理数据
	// * 该单据的ID
	// * 该单据的员工ID
	// * 该单据的类别
	// * 该单据的生成的时间
	// * 该单据的状态（提交，已审批）
	// *该单据的内容描述
	// */
	// private void handleDataFrom(String line) {
	// String single[]=line.split("；");
	// String ID=single[0];
	// String employeeID=single[1];
	// String type=single[2];
	// String date=single[3];
	// String state=single[4];
	// String description=single[5];
	// SheetPO temp=new SheetPO(ID, employeeID, type, date, state, description);
	// this.po.add(temp);
	//
	// }
	/**
	 * 所有的单据写入文本
	 */
	@Override
	public void writeAll(ArrayList<SheetPO> po) throws RemoteException {
		// FileWriter writer=null;
		// try {
		// writer=new FileWriter(FILE);
		// for (int i = 0; i < po.size(); i++) {
		// writer.write(this.handleDataTo(po.get(i))+"\n");
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }finally{
		// try {
		// writer.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(FILE));
			oos.writeObject(po);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	// /**
	// * 数据写入文本
	// * 从文本处理数据
	// * 该单据的ID
	// * 该单据的员工ID
	// * 该单据的类别
	// * 该单据的生成的时间
	// * 该单据的状态（提交，已审批）
	// *该单据的内容描述
	// * @param sheetPO
	// * @return
	// */
	// private String handleDataTo(SheetPO sheetPO) {
	// String ID=sheetPO.getId();
	// String employeeID=sheetPO.getEmployeeId();
	// String type=sheetPO.getType();
	// String date=sheetPO.getDate();
	// String state=sheetPO.getState();
	// String description=sheetPO.getDescription();
	// String
	// result=ID+"；"+employeeID+"；"+type+"；"+date+"；"+state+"；"+description;
	// return result;
	// }
	// /**
	// * 将某单据插入文本
	// */
	// @Override
	// public void insert(SheetPO po) throws RemoteException {
	// FileWriter writer=null;
	// try {
	// writer=new FileWriter(FILE,true);
	// writer.write(this.handleDataTo(po)+"\n");
	// } catch (Exception e) {
	// e.printStackTrace();
	// }finally{
	// try {
	// writer.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// }

}
