package edu.nju.logistics.ui.branchstaff.CarAndDriverManagement.driver;

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
import edu.nju.logistics.po.branchdata.DriverPO;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;

public class AddPanel_Driver{
	
	JDialog addDialog;
	
	private BranchManagementService branchManagement;
	private DisconnectInformer disconnectInformer;
	
	public final static Font LABELFONT = new java.awt.Font("宋体", 0, 18);
	public final static Font INPUTFONT = new java.awt.Font("黑体", 0, 18);
	
	
	public AddPanel_Driver(BranchManagementService branchManagement, Frame dialogOwner
			, Refresher refresher,DisconnectInformer disconnectInformer) {
		this.disconnectInformer = disconnectInformer;
		this.branchManagement = branchManagement;
		addDialog = new JDialog(dialogOwner, "增加司机", true);
		initDialog(refresher,new DriverPO("", "", "", "", "", true, "",false));
	}


	public AddPanel_Driver(BranchManagementService branchManagement
			, Frame dialogOwner, Refresher refresher
			, DriverPO driverPO,DisconnectInformer disconnectInformer) {
		this.disconnectInformer = disconnectInformer;
		this.branchManagement = branchManagement;
		addDialog = new JDialog(dialogOwner, "编辑司机", true);
		initDialog(refresher,driverPO);
	}


	private void initDialog(Refresher refresher,DriverPO driverPO) {
		
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 410;
		int height = 470;
		addDialog.setResizable(false);
		addDialog.setBounds(screensize.width / 2 - width / 2, screensize.height / 2
				- height / 2, width, height);
		Container contentPane = addDialog.getContentPane();
		contentPane.setLayout(null);
		
		
		
		int firstHeight = 40;
		int interval = 45;
		int secondHeight = firstHeight + interval;
		int thirdHeight = secondHeight + interval;
		int forthHeight = thirdHeight + interval;
		int fifthHeight = forthHeight + interval;
		int sixthHeight = fifthHeight + interval;
		int seventhHeight = sixthHeight + interval;
		int unitHeight = 25;
		int fieldX = 170;
		int cross = 10;
		int averageFieldWidth = 125;
		
		
		JLabel name = new JLabel("姓名：",JLabel.RIGHT);
		name.setBounds(0, firstHeight, fieldX - cross, unitHeight);
		name.setFont(LABELFONT);
		contentPane.add(name);
		
		JTextField namefield = new JTextField(driverPO.getName());
		namefield.setBounds(fieldX, firstHeight + 1,averageFieldWidth, unitHeight);
		namefield.setFont(INPUTFONT);
//		idfield.setDocument(new NumberLengthLimitedDmt(10));
		contentPane.add(namefield);
		
		JLabel identityNum = new JLabel("身份证号：",JLabel.RIGHT);
		identityNum.setBounds(0, secondHeight, fieldX - cross, unitHeight);
		identityNum.setFont(LABELFONT);
		contentPane.add(identityNum);
		
		JTextField identityNumfield = new JTextField(driverPO.getIdentityNum());
		identityNumfield.setBounds(fieldX, secondHeight,200, unitHeight);
		identityNumfield.setFont(INPUTFONT);
//		idfield.setDocument(new NumberLengthLimitedDmt(10));
		contentPane.add(identityNumfield);
		
		JLabel birthday = new JLabel("出生日期：",JLabel.RIGHT);
		birthday.setBounds(0, thirdHeight, fieldX - cross, unitHeight);
		birthday.setFont(LABELFONT);
		contentPane.add(birthday);
		
		JTextField birthdayfield = new JTextField(driverPO.getBirthday());
		birthdayfield.setBounds(fieldX, thirdHeight,averageFieldWidth, unitHeight);
		birthdayfield.setFont(INPUTFONT);
		contentPane.add(birthdayfield);
		
		JLabel phone = new JLabel("手机号：",JLabel.RIGHT);
		phone.setBounds(0, forthHeight, fieldX - cross, unitHeight);
		phone.setFont(LABELFONT);
		contentPane.add(phone);
		
		JTextField phonefield = new JTextField(driverPO.getPhone());
		phonefield.setBounds(fieldX, forthHeight,averageFieldWidth, unitHeight);
		phonefield.setFont(INPUTFONT);
		contentPane.add(phonefield);
		
		JLabel licenseEndTime = new JLabel("行驶证到期时间：",JLabel.RIGHT);
		licenseEndTime.setBounds(0, fifthHeight, fieldX - cross, unitHeight);
		licenseEndTime.setFont(LABELFONT);
		contentPane.add(licenseEndTime);
		
		JTextField licenseEndTimefield = new JTextField(driverPO.getLicenseEndTime());
		licenseEndTimefield.setBounds(fieldX, fifthHeight,averageFieldWidth, unitHeight);
		licenseEndTimefield.setFont(INPUTFONT);
		contentPane.add(licenseEndTimefield);
		
		
		JLabel warning = new JLabel("请完整填写信息！！",JLabel.CENTER);
		warning.setBounds(0, -2, width, firstHeight);
		warning.setFont(new java.awt.Font("楷体", 0, 18));
		warning.setForeground(Color.red);
		warning.setVisible(false);
		contentPane.add(warning);
		
		
		JLabel sex = new JLabel("性别：",JLabel.RIGHT);
		sex.setBounds(0, sixthHeight, fieldX - cross, unitHeight);
		sex.setFont(LABELFONT);
		contentPane.add(sex);
		
		DefaultComboBoxModel<String> sexmodel = new DefaultComboBoxModel<>();
		sexmodel.addElement("男");
		sexmodel.addElement("女");
		JComboBox<String> sexBox = new JComboBox<>(sexmodel);
		if(!driverPO.isMale())
			sexBox.setSelectedIndex(1);
		sexBox.setBounds(fieldX, sixthHeight,averageFieldWidth, unitHeight);
		sexBox.setFont(LABELFONT);
		contentPane.add(sexBox);
		
		JLabel status = new JLabel("状态：",JLabel.RIGHT);
		status.setBounds(0, seventhHeight, fieldX - cross, unitHeight);
		status.setFont(LABELFONT);
		contentPane.add(status);
		
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		model.addElement("空闲");
		model.addElement("忙碌");
		JComboBox<String> statusBox = new JComboBox<>(model);
		if(driverPO.isBusy())
			statusBox.setSelectedIndex(1);
		statusBox.setBounds(fieldX, seventhHeight, averageFieldWidth, unitHeight);
		statusBox.setFont(LABELFONT);
		contentPane.add(statusBox);	
		
		JButton jbb = new JButton("确认");
		jbb.setBounds(width/2+30, height-110, 120, 40);
		contentPane.add(jbb);
		jbb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = namefield.getText().trim();
				String identity = identityNumfield.getText().trim();
				String birthday = birthdayfield.getText().trim();
				String phone = phonefield.getText().trim();
				String licenseEndTime = licenseEndTimefield.getText().trim();
				boolean isMale = true;
				boolean isBusy = true;
				if(((String)statusBox.getSelectedItem()).equals("空闲"))
					isBusy = false;
				if(((String)sexBox.getSelectedItem()).equals("女"))
					isMale = false;
				
				if(name.length() < 1 || identity.length() < 1 || birthday.length() < 1 || 
						phone.length() < 1 || licenseEndTime.length() < 1){
					
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
					String driverid = driverPO.getId();
					try {
						if(driverid.length() > 2){
							branchManagement.deleteDriver(driverid);
							addDriver(driverid,name,birthday,identity,phone,isMale,isBusy,
									licenseEndTime,refresher);
							GlobalHintInserter.insertGlobalHint("司机编辑成功！");
						}else{
							addDriver(branchManagement.getNextDriverID(),name,
								birthday,identity,phone,isMale,isBusy,licenseEndTime,refresher);
							GlobalHintInserter.insertGlobalHint("司机增加成功！");
						}
					} catch (RemoteException e1) {
						disconnectInformer.findDisconnect();
						return;
					}
				}
			}

		});
		
		JButton cancelButton = new JButton("取消");
		cancelButton.setBounds(width/2-150, height-110, 120, 40);
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
	
	private void addDriver(String id, String name, String birthday, String identityNum, String phone
			,boolean isMale, boolean isBusy, String licenseEndTime,Refresher refresher){
		try {
			branchManagement.addDriver(new DriverPO(id, name, birthday, identityNum, 
					phone, isMale, licenseEndTime,isBusy));
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
			return;
		}
		addDialog.dispose();
		refresher.refresh();
	}


}
