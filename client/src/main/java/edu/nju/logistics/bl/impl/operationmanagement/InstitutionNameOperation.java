package edu.nju.logistics.bl.impl.operationmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.data.service.institutiondata.InstitutionDataService;

public class InstitutionNameOperation {

	private InstitutionDataService institutionDataService = null;

	public InstitutionNameOperation(InstitutionDataService institutionDataService) {
		this.institutionDataService = institutionDataService;
	}

	static final String DATA = "曲折荷塘上望田叶子水高亭舞裙层叶" + "子中零星点缀白花有袅娜羞涩朵儿正如一明珠如碧天里星" + "如刚浴美人微风送缕清香仿佛远高楼上渺茫歌声"
			+ "动闪电般霎传过荷塘去叶本肩并密挨宛然道凝碧波痕叶子" + "脉流水遮住能颜色更风致月光如流水静子" + "花薄青雾浮起荷塘里花牛中笼轻纱梦然满月" + "天上一层淡云以朗照以到好酣眠睡风"
			+ "光隔树照过高处丛生灌木落下参差斑驳黑影却像画在荷叶上塘中均" + "匀光影谐旋律如梵婀玲上奏名曲" + "涵枫槿皇靖康慕容云海卿柳寒念";

	static final String[] postfix = { "花园", "小区", "学校", "路", "大道", "街", "庙", "山", "大学", "桥", "中学", "小学", "寺", "湖", "机场",
			"门", "楼", "广场", "公园", "开发区" };

	/**
	 * 生成新的名字
	 * 
	 * @param id
	 * @throws RemoteException
	 */
	public void createNewName(String id) throws RemoteException {
		String name = "";

		name = this.createRandomName();

		while (!this.isLegalName(name)) {
			name = this.createRandomName();
		}

		this.institutionDataService.writeName(id + "-" + name);

	}

	/**
	 * 判断生成的名字是否与已有重复
	 * 
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	private boolean isLegalName(String name) throws RemoteException {

		ArrayList<String> names = this.institutionDataService.getAllNames();
		for (int i = 0; i < names.size(); i++) {
			String single = names.get(i);
			if (name.equals(single)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 随机生成名字
	 * 
	 * @return
	 */
	private String createRandomName() {
		String name = DATA.charAt((int) (Math.random() * (DATA.length() - 1))) + ""
				+ DATA.charAt((int) (Math.random() * (DATA.length() - 1)))
				+ postfix[(int) (Math.random() * (postfix.length - 1))];
		return name;

	}

	/**
	 * 根据ID删除名字
	 * 
	 * @param id
	 * @throws RemoteException
	 */
	public void deleteName(String id) throws RemoteException {

		this.institutionDataService.deleteName(id);

	}

	/**
	 * 根据ID得到名字
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public String getName(String id) throws RemoteException {
		String name = "";

		name = this.institutionDataService.getName(id);

		return name;
	}

	/**
	 * 根据名字得到ID
	 * 
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	public String getID(String name) throws RemoteException {
		String id = "";

		id = this.institutionDataService.getID(name);

		return id;
	}
	// public static void main(String[] args) {
	// InstitutionNameOperation creater = new InstitutionNameOperation();
	// creater.createNewName("002401");
	// }
	// public static void main(String[] args) {
	// EnvironmentGetter environmentGetter = new
	// OperationManagementController();
	// ArrayList<InstitutionPO> list = environmentGetter.getAllInstitution();
	// File file = new File(PATH);
	// if (file.exists()) {
	// file.delete();
	// }
	// try {
	// file.createNewFile();
	// FileWriter fw = new FileWriter(file.getAbsoluteFile());
	// BufferedWriter bw = new BufferedWriter(fw);
	// // bw.write(content);
	//
	// for (InstitutionPO ins : list) {
	// bw.write(ins.getId());
	//
	// String name = DATA.charAt((int)(Math.random()*(DATA.length()-1)))+""
	// +DATA.charAt((int)(Math.random()*(DATA.length()-1)))
	// +postfix[(int)(Math.random()*(postfix.length-1))];
	//
	// bw.write("-"+name);
	// bw.newLine();
	// }
	// bw.close();
	// } catch (IOException e) {
	// // TODO 自动生成的 catch 块
	// e.printStackTrace();
	// }
	// System.out.println("sucessful build file!!");
	// }

}
