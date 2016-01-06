package edu.nju.logistics.server.data.impl.transfersheetdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import edu.nju.logistics.data.service.transfersheetdata.TransferSheetDataService;
import edu.nju.logistics.po.TransferSheetPO;

public class TransferSheetData extends UnicastRemoteObject implements TransferSheetDataService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String TRANSFERSHEET = "File/transferData/transferSheet.txt";
	
	private static final String TNUM = "File/transferData/tnum.txt";
	
	private ArrayList<TransferSheetPO> list = null;

	public TransferSheetData() throws RemoteException {}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<TransferSheetPO> find(String centerID) throws RemoteException {
		ObjectInputStream ois = null;
		list = new ArrayList<TransferSheetPO>();
		ArrayList<TransferSheetPO> returnList = new ArrayList<TransferSheetPO>();
		if(this.getTransferSheetNum() == 0) {
			return returnList;
		}
		try {
			ois = new ObjectInputStream(new FileInputStream(TRANSFERSHEET));
			list = (ArrayList<TransferSheetPO>)ois.readObject();
			for(TransferSheetPO po : list) {
				if(po.getDestinationID().equals(centerID)) {
					returnList.add(po);
				}
			}
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean delete(String ID) throws RemoteException {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		list = new ArrayList<TransferSheetPO>();
		ArrayList<TransferSheetPO> returnList = new ArrayList<TransferSheetPO>(); 
		try {
			ois = new ObjectInputStream(new FileInputStream(TRANSFERSHEET));
			list = (ArrayList<TransferSheetPO>)ois.readObject();
			ois.close();
			for(TransferSheetPO po : list) {
				if(!po.getID().equals(ID)) {
					returnList.add(po);
					this.minusTNum();
				}
			}
			oos = new ObjectOutputStream(new FileOutputStream(TRANSFERSHEET));
			oos.writeObject(returnList);
			oos.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean insert(TransferSheetPO po) throws RemoteException {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		list = new ArrayList<TransferSheetPO>();
		if(this.getTransferSheetNum() != 0) {
			try {
				ois = new ObjectInputStream(new FileInputStream(TRANSFERSHEET));
				try {
					list = (ArrayList<TransferSheetPO>)ois.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			oos = new ObjectOutputStream(new FileOutputStream(TRANSFERSHEET));
			list.add(po);
			this.addTNum();
			oos.writeObject(list);
			oos.flush();
			oos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public String getTransferSheetID() throws RemoteException {
		BufferedReader br = null;
		BufferedWriter bw = null; 
		String id = null;
		try {
			br = new BufferedReader(new FileReader(TNUM));
			String s = br.readLine();
			id = br.readLine();
			br.close();
			int n = Integer.parseInt(id);
			String temp = String.valueOf(++n);
			if(temp.length() != 8) {
				for(int j = temp.length(); j < 8; j++) {
					temp = "0" + temp;
				}
			}
			bw = new BufferedWriter(new FileWriter(TNUM, false));
			bw.write(s + "\n" + temp);
			bw.flush();
			bw.close();
			if(id.length() != 8) {
				for(int j = id.length(); j < 8; j++) {
					id = "0" + id;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TransferSheetPO getTransferSheetByID(String id) throws RemoteException{
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(TRANSFERSHEET));
			list = (ArrayList<TransferSheetPO>)ois.readObject();
			for(TransferSheetPO po : list) {
				if(po.getID().equals(id)) {
					ois.close();
					return po;
				}
			}
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private int getTransferSheetNum() {
		BufferedReader br = null;
		int num = 0;
		try {
			br = new BufferedReader(new FileReader(TNUM));
			num = Integer.valueOf(br.readLine());
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	//TNUM为中转单号
	private void addTNum() {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new FileReader(TNUM));
			int num = Integer.parseInt(br.readLine());
			String id = br.readLine();
			br.close();
			bw = new BufferedWriter(new FileWriter(TNUM, false));
			bw.write(String.valueOf(++num) + "\n" + id);
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void minusTNum() {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new FileReader(TNUM));
			int num = Integer.parseInt(br.readLine());
			String id = br.readLine();
			br.close();
			bw = new BufferedWriter(new FileWriter(TNUM, false));
			bw.write(String.valueOf(--num) + "\n" + id);
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
//		TransferSheetData t = null;
//		try {
//			t = new TransferSheetData();
//			System.out.println(t.getTransferSheetID());
//			System.out.println(t.delete("00000001"));
//			System.out.println(t.getTransferSheetNum());
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//	}
}
