package edu.nju.logistics.ui.branchstaff.load.check;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.nju.logistics.ui.branchstaff.load.DataContainer;

public class MainInfoPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4947434490001077616L;

	public final static Font LABELFONT = new java.awt.Font("宋体", 0, 19);
	public final static Font INFOFONT = new java.awt.Font("黑体", 0, 21);
	
	public final static Color INFOCOLOR = Color.green;
	public final static Color BORDERCOLOR = Color.yellow;

	public MainInfoPanel(DataContainer dataContainer) {
		
		setBorder(BorderFactory.createLineBorder(BORDERCOLOR));
		setOpaque(false);
		setLayout(null);
		
		int unitHeight = 30;
		int cross = 2;
		int firstHeight = 25;
		int secondHeight = 75;
		int firstM = 120;
		int secondM = 390;
		int thirdM = 650;
		int forthM = 800;
		
		int Xp = 0;
		int Yp = 0;
		
		JLabel destination = new JLabel("目的地：",JLabel.RIGHT);
		destination.setBounds(0 + Xp, firstHeight + Yp, firstM - cross, unitHeight);
		destination.setFont(LABELFONT);
		add(destination);
		
		JLabel destinationInfo = new JLabel(dataContainer.destinationname,JLabel.LEFT);
		destinationInfo.setBounds(firstM + cross, firstHeight + Yp , 500, unitHeight);
		destinationInfo.setFont(INFOFONT);
		destinationInfo.setForeground(INFOCOLOR);
		add(destinationInfo);
		
		JLabel carid = new JLabel("车辆代号：",JLabel.RIGHT);
		carid.setBounds(0 + Xp, firstHeight + Yp, secondM - cross, unitHeight);
		carid.setFont(LABELFONT);
		add(carid);
		
		JLabel carinfo = new JLabel(dataContainer.carid,JLabel.LEFT);
		carinfo.setBounds(secondM + cross, firstHeight + Yp , 150, unitHeight);
		carinfo.setFont(INFOFONT);
		carinfo.setForeground(INFOCOLOR);
		add(carinfo);
		
		JLabel orderNum = new JLabel("订单数：",JLabel.RIGHT);
		orderNum.setBounds(0 + Xp, firstHeight + Yp, thirdM - cross, unitHeight);
		orderNum.setFont(LABELFONT);
		add(orderNum);
		
		JLabel orderNuminfo = new JLabel(Integer.toString(dataContainer.selectedOrderList.size()),JLabel.LEFT);
		orderNuminfo.setBounds(thirdM + cross, firstHeight + Yp , 150, unitHeight);
		orderNuminfo.setFont(INFOFONT);
		orderNuminfo.setForeground(INFOCOLOR);
		add(orderNuminfo);
		
		JLabel fee = new JLabel("运费：",JLabel.RIGHT);
		fee.setBounds(0 + Xp, firstHeight + Yp, forthM - cross, unitHeight);
		fee.setFont(LABELFONT);
		add(fee);
		
		/* 以下三行使运费在显示时保留两位小数 */
		String feeStr = Double.toString(dataContainer.fee).concat("000");
		int temp = feeStr.lastIndexOf('.');
		String resultFee = feeStr.substring(0, temp + 3);
		
		JLabel feeidnfo = new JLabel(resultFee,JLabel.LEFT);
		feeidnfo.setBounds(forthM + cross, firstHeight + Yp , 150, unitHeight);
		feeidnfo.setFont(INFOFONT);
		feeidnfo.setForeground(INFOCOLOR);
		add(feeidnfo);
		
		JLabel driver = new JLabel("司机：",JLabel.RIGHT);
		driver.setBounds(0 + Xp, secondHeight + Yp, firstM - cross, unitHeight);
		driver.setFont(LABELFONT);
		add(driver);
		
		JLabel driverInfo = new JLabel(dataContainer.drivername,JLabel.LEFT);
		driverInfo.setBounds(firstM + cross, secondHeight + Yp , 150, unitHeight);
		driverInfo.setFont(INFOFONT);
		driverInfo.setForeground(INFOCOLOR);
		add(driverInfo);
		
		JLabel observer = new JLabel("监装员：",JLabel.RIGHT);
		observer.setBounds(0 + Xp, secondHeight + Yp, secondM - cross, unitHeight);
		observer.setFont(LABELFONT);
		add(observer);
		
		JLabel observerinfo = new JLabel(dataContainer.observer,JLabel.LEFT);
		observerinfo.setBounds(secondM + cross, secondHeight + Yp , 150, unitHeight);
		observerinfo.setFont(INFOFONT);
		observerinfo.setForeground(INFOCOLOR);
		add(observerinfo);
		
		JLabel supercargo = new JLabel("押运员：",JLabel.RIGHT);
		supercargo.setBounds(0 + Xp, secondHeight + Yp, thirdM - cross, unitHeight);
		supercargo.setFont(LABELFONT);
		add(supercargo);
		
		JLabel supercargoinfo = new JLabel(dataContainer.supercargo,JLabel.LEFT);
		supercargoinfo.setBounds(thirdM + cross, secondHeight + Yp , 150, unitHeight);
		supercargoinfo.setFont(INFOFONT);
		supercargoinfo.setForeground(INFOCOLOR);
		add(supercargoinfo);
		
	}
}
