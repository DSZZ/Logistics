package edu.nju.logistics.ui.branchstaff.CarAndDriverManagement.car;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import edu.nju.logistics.bl.service.branchmanagement.BranchManagementService;
import edu.nju.logistics.po.branchdata.CarPO;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;

public class AddPanel_Car{
	
	JDialog addDialog;
	
	private BranchManagementService branchManagement;
	private DisconnectInformer disconnectInformer;
	
	public final static Font LABELFONT = new java.awt.Font("宋体", 0, 18);
	public final static Font INPUTFONT = new java.awt.Font("黑体", 0, 18);
	
	
	public AddPanel_Car(BranchManagementService branchManagement, Frame dialogOwner, Refresher refresher
			, DisconnectInformer disconnectInformer) {
		this.disconnectInformer = disconnectInformer;
		this.branchManagement = branchManagement;
		addDialog = new JDialog(dialogOwner, "增加车辆", true);
		initDialog(refresher,"","",0,null);
	}


	public AddPanel_Car(BranchManagementService branchManagement, Frame dialogOwner
			, Refresher refresher, CarPO carPO, DisconnectInformer disconnectInformer) {
		this.disconnectInformer = disconnectInformer;
		this.branchManagement = branchManagement;
		addDialog = new JDialog(dialogOwner, "编辑车辆", true);
		int status = 0;
		if(carPO.isBusy())
			status = 1;
		else
			status = 0;
		
		initDialog(refresher,carPO.getPlateNum(),carPO.getStartWorkTime(),status,carPO.getId());
	}


	private void initDialog(Refresher refresher,String originPlateNum, String origintime, int statusIndex,String carid) {
		
		addDialog.setResizable(false);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 350;
		int height = 290;
		addDialog.setBounds(screensize.width / 2 - width / 2, screensize.height / 2
				- height / 2, width, height);
		Container contentPane = addDialog.getContentPane();
		contentPane.setLayout(null);
		
		JLabel warning = new JLabel("请完整填写信息！！",JLabel.CENTER);
		warning.setBounds(0, 3, 350, 20);
		warning.setFont(new java.awt.Font("楷体", 0, 18));
		warning.setForeground(Color.red);
		warning.setVisible(false);
		contentPane.add(warning);
		
		JLabel id = new JLabel("车牌号：");
		id.setBounds(75, 26, 90, 25);
		id.setFont(LABELFONT);
		contentPane.add(id);
		
		JTextField idfield = new JTextField(originPlateNum);
		idfield.setBounds(170, 27,110, 25);
		idfield.setFont(INPUTFONT);
//		idfield.setDocument(new NumberLengthLimitedDmt(10));
		contentPane.add(idfield);
		
		JLabel time = new JLabel("开始服役时间：");
		time.setBounds(20, 80, 150, 25);
		time.setFont(LABELFONT);
		contentPane.add(time);
		
		JTextField timefield = new JTextField(origintime);
		timefield.setBounds(170, 80,110, 25);
		timefield.setFont(INPUTFONT);
//		idfield.setDocument(new NumberLengthLimitedDmt(10));
		contentPane.add(timefield);
		
		JLabel status = new JLabel("状态：");
		status.setBounds(92, 135, 150, 25);
		status.setFont(LABELFONT);
		contentPane.add(status);
		
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		model.addElement("空闲");
		model.addElement("忙碌");
		JComboBox<String> statusBox = new JComboBox<>(model);
		statusBox.setSelectedIndex(statusIndex);
		statusBox.setBounds(170, 135,110, 25);
		statusBox.setFont(LABELFONT);
		contentPane.add(statusBox);
		
		JButton jbb = new JButton("确认");
		jbb.setBounds(190, height-95, 100, 30);
		contentPane.add(jbb);
		jbb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = idfield.getText().trim();
				String time = timefield.getText().trim();
				if(id.length() < 1 || time.length() < 1){
					
					warning.setVisible(true);
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(1500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if(warning != null)
							warning.setVisible(false);
						}
					}).start();
					
				}
				else{
					String busyStatus = (String)statusBox.getSelectedItem();
					try {
						if(carid != null){
							branchManagement.deleteCar(carid);
							addCar(carid,id, time, busyStatus,refresher);
							GlobalHintInserter.insertGlobalHint("车辆编辑成功！");
						}else{
							addCar(branchManagement.getNextCarID(),id, time, busyStatus,refresher);
							GlobalHintInserter.insertGlobalHint("车辆增加成功！");
						}
					} catch (RemoteException e1) {
						disconnectInformer.findDisconnect();
						return;
					}
				}
			}

		});
		
		JButton cancelButton = new JButton("取消");
		cancelButton.setBounds(50, height-95, 100, 30);
		contentPane.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addDialog.dispose();
			}

		});

		addDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		addDialog.setVisible(true);
		
	}
	
	private void addCar(String carid, String id, String time, String busyStatus,Refresher refresher){
		try {
			if(busyStatus.equals("空闲")){
				branchManagement.addCar(new CarPO(carid, id, time,false));
			}else{
				branchManagement.addCar(new CarPO(carid, id, time,true));
			}
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
			return;
		}
		addDialog.dispose();
		refresher.refresh();
	}


}
