package edu.nju.logistics.ui.keeper;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import edu.nju.logistics.bl.impl.storehousemanagement.StorehouseManagementController;
import edu.nju.logistics.bl.service.storehousemanagement.StorehouseManagementBLService;
import edu.nju.logistics.ui.commonButton.commonButton;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.UserVO;

/**
 * 设置警戒值界面
 * @author HanzZou
 *
 */
public class PanelSetAlarm extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel setAlarm_label, percentSign, percentTip, car, train, plane, carValue, trainValue, planeValue = null;
	
	private JTextField alarm_text = null;
	
	private JButton confirm_button = null;
	
	private JProgressBar jpbCar, jpbPlane, jpbTrain = null;
	
	private Cursor cursor = null;
	
	private UserVO user = null;
	
	private StorehouseManagementBLService controller = null;
	
	private DisconnectInformer disconnectInformer = null;
	
	public PanelSetAlarm(UserVO user, DisconnectInformer d) throws RemoteException {
		this.user = user;
		this.controller = new StorehouseManagementController();
		this.setLayout(null);
		this.setSize(980, 560);
		this.setOpaque(false);
		this.cursor = new Cursor(Cursor.HAND_CURSOR);
		this.initComponent();
		this.disconnectInformer = d;
	}
	
	public void initComponent() throws RemoteException{
		//标题标签
		setAlarm_label = new JLabel("输入警戒值:");
		setAlarm_label.setLocation(680, 100);
		setAlarm_label.setSize(100, 30);	
		this.add(setAlarm_label);
		//百分号标签
		percentSign = new JLabel("%");
		percentSign.setLocation(900, 100);
		percentSign.setSize(100, 30);
		this.add(percentSign);
		//警戒值输入框
		alarm_text = new JTextField();
		alarm_text.setLocation(780, 100);
		alarm_text.setSize(100, 30);
		alarm_text.setHorizontalAlignment(JTextField.CENTER);
		this.add(alarm_text);
		//确认按钮
		confirm_button = new commonButton("确认");
		confirm_button.setLocation(830, 480);
		confirm_button.setSize(100, 40);
		confirm_button.setCursor(cursor);
		confirm_button.addMouseListener(this);
		this.add(confirm_button);
		//百分率信息
		percentTip = new JLabel("目前库存比例如下：");
		percentTip.setBounds(50, 50, 150, 30);
		this.add(this.percentTip);
		car = new JLabel("汽运区：");
		car.setBounds(200, 112, 80, 30);
		this.add(this.car);
		train = new JLabel("铁运区：");
		train.setBounds(200, 212, 80, 30);
		this.add(this.train);
		plane = new JLabel("航运区：");
		plane.setBounds(200, 312, 80, 30);
		this.add(this.plane);
		//进度条
		jpbCar = new JProgressBar();
		jpbCar.setValue((int)(controller.getPercentage(controller.getInstitutionIDByUser(this.user.getId()), "汽运区")*100));
		jpbCar.setBounds(300, 100, 200, 60);
		this.add(this.jpbCar);
		jpbTrain = new JProgressBar();
		jpbTrain.setValue((int)(controller.getPercentage(controller.getInstitutionIDByUser(this.user.getId()), "铁运区")*100));
		jpbTrain.setBounds(300, 200, 200, 60);
		this.add(this.jpbTrain);
		jpbPlane = new JProgressBar();
		jpbPlane.setValue((int)(controller.getPercentage(controller.getInstitutionIDByUser(this.user.getId()), "航运区")*100));
		jpbPlane.setBounds(300, 300, 200, 60);
		this.add(this.jpbPlane);
		//即使比例
		java.text.DecimalFormat df=new java.text.DecimalFormat("#.###"); 
		this.carValue = new JLabel();
		this.carValue.setBounds(520, 100, 100, 60);
		this.carValue.setText(df.format(this.controller.getPercentage(controller.getInstitutionIDByUser(this.user.getId()), "汽运区")*100) + "%");
		this.trainValue = new JLabel();
		this.trainValue.setBounds(520, 200, 100, 60);
		this.trainValue.setText(df.format(this.controller.getPercentage(controller.getInstitutionIDByUser(this.user.getId()), "铁运区")*100)+"%");
		this.planeValue = new JLabel();
		this.planeValue.setBounds(520, 300, 100, 60);
		this.planeValue.setText(df.format(this.controller.getPercentage(controller.getInstitutionIDByUser(this.user.getId()), "航运区")*100)+"%");
		this.add(this.carValue);
		this.add(this.trainValue);
		this.add(this.planeValue);
		
		
		this.repaint();
	}
	
	/**
	 * 判断字符串为数字
	 * 
	 * @param str
	 * @return
	 */
	private boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == this.confirm_button) {
			String alarm = alarm_text.getText();
			if(alarm.equals("")) {
				GlobalHintInserter.insertGlobalHint("请输入警戒值！");
				return;
			}
			if(!isNumeric(alarm)) {
				GlobalHintInserter.insertGlobalHint("请输入整数！");
			}
			double alarmValue = (double)Double.valueOf(alarm)/100;
			if(alarmValue < 0.0 || alarmValue > 1.0) {
				GlobalHintInserter.insertGlobalHint("请确保输入值正确！");
			}else if(alarmValue <= 0.5) {
				GlobalHintInserter.insertGlobalHint("设置警戒值过低！");
			}else {
				try {
					controller.setAlarm(controller.getInstitutionIDByUser(user.getId()), alarmValue);
				} catch (RemoteException e1) {
					this.disconnectInformer.findDisconnect();
					return;
				}
				GlobalHintInserter.insertGlobalHint("设置完成！");
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
