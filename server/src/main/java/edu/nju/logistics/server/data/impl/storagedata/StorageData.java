package edu.nju.logistics.server.data.impl.storagedata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import edu.nju.logistics.data.service.storagedata.StorageDataService;
import edu.nju.logistics.po.BufferPO;
import edu.nju.logistics.po.StoragePO;
import edu.nju.logistics.po.StorageRecordPO;
import edu.nju.logistics.po.StorageState;

/**
 * 库存数据处理
 * 
 * @author HanzZou
 *
 */
public class StorageData extends UnicastRemoteObject implements StorageDataService {

	private static final String STORAGE = "File/storehouseData/storage.txt";

	private static final String RECORD = "File/storehouseData/record.txt";

	private static final String PATCH = "File/storehouseData/patch.txt";

	private static final String BUFFER = "File/storehouseData/buffer.txt";

	private static final String SCALE = "File/storehouseData/scale.txt";

	private static final String ALARM = "File/storehouseData/alarm.txt";

	private ArrayList<StoragePO> po_list = null;

	private ArrayList<StorageRecordPO> recordpo_list = null;

	private ArrayList<BufferPO> buffer_list = null;

	public StorageData() throws RemoteException {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public ArrayList<StoragePO> find() throws RemoteException {
		BufferedReader br = null;
		String line = null;
		po_list = new ArrayList<StoragePO>();
		try {
			br = new BufferedReader(new FileReader(STORAGE));
			while ((line = br.readLine()) != null) {
				po_list.add(this.stringToStoragePO(line));
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return po_list;
	}

	@Override
	public void updateState(String id, StorageState s) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		StringBuffer buf = new StringBuffer();
		String line = null;
		try {
			br = new BufferedReader(new FileReader(STORAGE));
			while ((line = br.readLine()) != null) {
				StoragePO po = this.stringToStoragePO(line);
				if (po.getNumber().equals(id)) {
					po.setState(s);
				}
				buf.append(this.storagePOToString(po));
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(STORAGE, false));
			bw.write(buf.toString());
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Boolean insert(StoragePO po) throws RemoteException {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(STORAGE, true));
			bw.write(this.storagePOToString(po));
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Boolean deleteByID(String id) throws RemoteException {
		BufferedReader br = null;
		BufferedWriter bw = null;
		String line = null;
		StringBuffer buf = new StringBuffer();
		try {
			br = new BufferedReader(new FileReader(STORAGE));
			while ((line = br.readLine()) != null) {
				if (!this.stringToStoragePO(line).getNumber().equals(id)) {
					buf.append(line + "\n");
				}
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(STORAGE, false));
			bw.write(buf.toString());
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Boolean deleteByCenterID(String centerID) throws RemoteException {
		BufferedReader br = null;
		BufferedWriter bw = null;
		String line = null;
		StringBuffer buf = new StringBuffer();
		try {
			br = new BufferedReader(new FileReader(STORAGE));
			while ((line = br.readLine()) != null) {
				if (!this.stringToStoragePO(line).getCenterID().equals(centerID)) {
					buf.append(line + "\n");
				}
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(STORAGE, false));
			bw.write(buf.toString());
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Boolean update(String id) throws RemoteException {
		BufferedReader br = null;
		BufferedWriter bw = null;
		String line = null;
		StringBuffer buf = new StringBuffer();
		StoragePO po = null;
		try {
			br = new BufferedReader(new FileReader(STORAGE));
			while ((line = br.readLine()) != null) {
				if (this.stringToStoragePO(line).getNumber().equals(id)) {
					po = this.stringToStoragePO(line);
					po.setArea("机动区");
					line = this.storagePOToString(po);
				}
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(STORAGE, false));
			bw.write(buf.toString());
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public ArrayList<BufferPO> getBuffer(String centerID, boolean isImport) throws RemoteException {
		BufferedReader br = null;
		String line = null;
		buffer_list = new ArrayList<BufferPO>();
		try {
			br = new BufferedReader(new FileReader(BUFFER));
			while ((line = br.readLine()) != null) {
				BufferPO po = this.stringToBufferPO(line);
				if (po.getCenterID().equals(centerID) && po.getStatus().equals(isImport ? "import" : "export")) {
					buffer_list.add(this.stringToBufferPO(line));
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer_list;
	}

	@Override
	public Boolean setToBuffer(BufferPO po) throws RemoteException {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(BUFFER, true));
			bw.write(this.bufferPOToString(po));
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void deleteBuffer(String id) throws RemoteException {
		BufferedReader br = null;
		BufferedWriter bw = null;
		String line = null;
		StringBuffer buf = new StringBuffer();
		try {
			br = new BufferedReader(new FileReader(BUFFER));
			while ((line = br.readLine()) != null) {
				if (!this.stringToBufferPO(line).getId().equals(id)) {
					buf.append(line + "\n");
				}
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(BUFFER, false));
			bw.write(buf.toString());
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Boolean setStorageRecord(StorageRecordPO po) throws RemoteException {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(RECORD, true));
			bw.write(this.storageRecordPOToString(po));
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public ArrayList<StorageRecordPO> getStorageRecord(String centerID) throws RemoteException {
		BufferedReader br = null;
		recordpo_list = new ArrayList<StorageRecordPO>();
		String line = null;
		try {
			br = new BufferedReader(new FileReader(RECORD));
			while ((line = br.readLine()) != null) {
				if (this.stringToStorageRecordPO(line).getCenterID().equals(centerID)) {
					StorageRecordPO po = this.stringToStorageRecordPO(line);
					recordpo_list.add(po);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recordpo_list;
	}

	@Override
	public void setAlarm(String centerID, double alarm) throws RemoteException {
		BufferedReader br = null;
		BufferedWriter bw = null;
		String line = null;
		StringBuffer buf = new StringBuffer("");
		boolean exist = false;
		try {
			br = new BufferedReader(new FileReader(ALARM));
			while ((line = br.readLine()) != null) {
				if (line.split(" ")[0].equals(centerID)) {
					line = centerID + " " + String.valueOf(alarm);
					exist = true;
				}
				buf.append(line + "\n");
			}
			if (!exist) {
				buf.append(centerID + " " + String.valueOf(alarm) + "\n");
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(ALARM, false));
			bw.write(buf.toString());
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getAlarm(String centerID) throws RemoteException {
		BufferedReader br = null;
		BufferedWriter bw = null;
		String alarm = null;
		String line = null;
		try {
			br = new BufferedReader(new FileReader(ALARM));
			while ((line = br.readLine()) != null) {
				if (line.substring(0, 6).equals(centerID)) {
					alarm = line.split(" ")[1];
					br.close();
					return alarm;
				}
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(ALARM, true));
			bw.write(centerID + " " + "0.8" + "\n");
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0.8";
	}

	@Override
	public String getPatchTime(String centerID) throws RemoteException {
		BufferedReader br = null;
		BufferedWriter bw = null;
		String line = null;
		try {
			br = new BufferedReader(new FileReader(PATCH));
			while ((line = br.readLine()) != null) {
				if (line.substring(0, 6).equals(centerID)) {
					br.close();
					return line.split(" ")[1] + " " + line.split(" ")[2];
				}
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(PATCH, true));
			bw.write(centerID + " " + "1970/01/01 00:00:00" + "\n");
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "1970/01/01 00:00:00";
	}

	@Override
	public void setPatchTime(String time, String centerID) throws RemoteException {
		BufferedReader br = null;
		BufferedWriter bw = null;
		String line = null;
		StringBuffer buf = new StringBuffer();
		boolean exist = false;
		try {
			br = new BufferedReader(new FileReader(PATCH));
			while ((line = br.readLine()) != null) {
				if (line.substring(0, 6).equals(centerID)) {
					line = line.substring(0, 6) + " " + time;
					exist = true;
				}
				buf.append(line + "\n");
			}
			br.close();
			if (!exist) {
				buf.append(centerID + " " + time);
			}
			bw = new BufferedWriter(new FileWriter(PATCH, false));
			bw.write(buf.toString());
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void setScale(String centerID, String area, int lineNum, int shelfNum, int positionNum)
			throws RemoteException {
		BufferedReader br = null;
		BufferedWriter bw = null;
		String line = null;
		StringBuffer buf = new StringBuffer();
		boolean exist = false;
		try {
			br = new BufferedReader(new FileReader(SCALE));
			while ((line = br.readLine()) != null) {
				if (line.split(" ")[0].equals(centerID) && line.split(" ")[1].equals(area)) {
					line = centerID + " " + area + " " + String.valueOf(lineNum) + " " + String.valueOf(shelfNum) + " "
							+ String.valueOf(positionNum);
					exist = true;
				}
				buf.append(line + "\n");
			}
			br.close();
			if (!exist) {
				buf.append(centerID + " " + area + " " + String.valueOf(lineNum) + " " + String.valueOf(shelfNum) + " "
						+ String.valueOf(positionNum) + "\n");
			}
			bw = new BufferedWriter(new FileWriter(SCALE, false));
			bw.write(buf.toString());
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int[] getScale(String centerID, String area) throws RemoteException {
		int[] scale = new int[3];
		BufferedReader br = null;
		BufferedWriter bw = null;
		String line = null;
		try {
			br = new BufferedReader(new FileReader(SCALE));
			while ((line = br.readLine()) != null) {
				if (line.split(" ")[0].equals(centerID) && line.split(" ")[1].equals(area)) {
					scale[0] = Integer.valueOf(line.split(" ")[2]);
					scale[1] = Integer.valueOf(line.split(" ")[3]);
					scale[2] = Integer.valueOf(line.split(" ")[4]);
					br.close();
					return scale;
				}
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(SCALE, true));
			bw.write(centerID + " " + area + " 100 100 100" + "\n");
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		scale[0] = scale[1] = scale[2] = 100;
		return scale;
	}

	private StoragePO stringToStoragePO(String s) {
		String[] array = s.split(";");
		StorageState state = null;
		switch (array[9]) {
		case "1":
			state = StorageState.IMPORT; break;
		case "2":
			state = StorageState.IN; break;
		case "3":
			state = StorageState.EXPORT; break;
		}
		StoragePO po = new StoragePO(array[0], array[1], array[2], Integer.valueOf(array[3]), Integer.valueOf(array[4]),
				Integer.valueOf(array[5]), array[6], array[7], Integer.valueOf(array[8]), state);
		return po;
	}

	private String storagePOToString(StoragePO po) {
		String ss = null;
		switch (po.getState()) {
		case IMPORT:
			ss = "1"; break;
		case IN:
			ss = "2"; break;
		case EXPORT:
			ss = "3"; break;
		}
		return po.getCenterID() + ";" + po.getTime() + ";" + po.getArea() + ";" + String.valueOf(po.getLine()) + ";"
				+ String.valueOf(po.getShelf()) + ";" + String.valueOf(po.getPosition()) + ";" + po.getNumber() + ";"
				+ po.getDestination() + ";" + String.valueOf(po.getMoney()) + ";" + ss + "\n";
	}

	private StorageRecordPO stringToStorageRecordPO(String s) {
		String[] array = s.split(";");
		StorageRecordPO po = new StorageRecordPO(array[0], array[1], Integer.parseInt(array[2]),
				Integer.parseInt(array[3]), Integer.parseInt(array[4]), array[5], array[6], Integer.parseInt(array[7]),
				array[8]);
		return po;
	}

	private String storageRecordPOToString(StorageRecordPO po) {
		String s = po.getCenterID() +";"+ po.getArea() +";"+ String.valueOf(po.getLine()) +";"+ String.valueOf(po.getShelf()) +";"+
				String.valueOf(po.getPosition()) +";"+ po.getNumber() +";"+ po.getState() +";"+ String.valueOf(po.getMoney()) +";"+ po.getTime() + "\n";
		return s;
	}

	private BufferPO stringToBufferPO(String s) {
		String[] array = s.split(";");
		BufferPO po = new BufferPO(array[0], array[1], array[2], array[3]);
		return po;
	}

	private String bufferPOToString(BufferPO po) {
		return po.getCenterID() + ";" + po.getId() + ";" + po.getDestination() + ";" + po.getStatus() + "\n";
	}

}
