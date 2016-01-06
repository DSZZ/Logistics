package edu.nju.logistics.ui.courier.inputOrder.subPanel;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import edu.nju.logistics.ui.courier.inputOrder.AddressChangeObserver;
import edu.nju.logistics.ui.courier.inputOrder.DayAndTimeCalculator;
import edu.nju.logistics.ui.courier.inputOrder.ShowPanel_InputOrder;
import edu.nju.logistics.ui.courier.inputOrder.EmptyException.EmptyException;
import edu.nju.logistics.ui.courier.inputOrder.EmptyException.ExceptionKind;

public class PersonalInfoInputPanel extends JPanel{

	private static final long serialVersionUID = 5209194093792761915L;

	JLabel personName;
	JTextField personNamefield;
	
	JLabel unit;
	JTextField companyfield;
	
	JLabel tel;
	JTextField telfield;
	
	JLabel phone;
	JTextField phonefield;
	
	JLabel address;
	AddressPanel addressPanel;
	
	DayAndTimeCalculator observer;
	
	
	
	public void addObserver(AddressChangeObserver dynamicObserver) {
		addressPanel.addObserver(dynamicObserver);
	}

	/**
	 * 显示在上方的border上，比如“寄件人信息”，中的“寄件人”三个字
	 */
	String title;
	
	public PersonalInfoInputPanel(String title){
		super();
		this.title = title;
		this.setLayout(null);
		
		setBorder(new TitledBorder(new LineBorder(Color.YELLOW,1,true)
		,"  "+title+"信息  ",TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.TOP
		,ShowPanel_InputOrder.LABELFONT,Color.red));
		
		personName = new JLabel("姓名：");
		personName.setBounds(20,27,45,20);
		personName.setFont(ShowPanel_InputOrder.LABELFONT);
		this.add(personName);
		
		personNamefield = new JTextField();
		personNamefield.setBounds(65, 27,100, 22);
		personNamefield.setFont(ShowPanel_InputOrder.INPUTFONT);
		this.add(personNamefield);
		
		unit = new JLabel("单位：");
		unit.setBounds(20,60,45,20);
		unit.setFont(ShowPanel_InputOrder.LABELFONT);
		this.add(unit);
		
		companyfield = new JTextField();
		companyfield.setBounds(65, 60,180, 22);
		companyfield.setFont(ShowPanel_InputOrder.INPUTFONT);
		this.add(companyfield);
		
		tel = new JLabel("电话：");
		tel.setBounds(280,60,45,20);
		tel.setFont(ShowPanel_InputOrder.LABELFONT);
		this.add(tel);
		
		telfield = new JTextField();
		telfield.setBounds(325, 60,120, 22);
		telfield.setFont(ShowPanel_InputOrder.INPUTFONT);
		this.add(telfield);
		
		phone = new JLabel("手机：");
		phone.setBounds(480,60,45,20);
		phone.setFont(ShowPanel_InputOrder.LABELFONT);
		this.add(phone);
		
		phonefield = new JTextField();
		phonefield.setBounds(525, 60,110, 22);
		phonefield.setFont(ShowPanel_InputOrder.INPUTFONT);
		phonefield.setDocument(new NumberLengthLimitedDmt(11));
		this.add(phonefield);
		
		address = new JLabel("地址：");
		address.setBounds(200,27,45,20);
		address.setFont(ShowPanel_InputOrder.LABELFONT);
		this.add(address);
		
		addressPanel = new AddressPanel();
		addressPanel.setBounds(250,27,670,25);
		addressPanel.setFont(ShowPanel_InputOrder.LABELFONT);
		addressPanel.setOpaque(false);
		this.add(addressPanel);
	}
	public String getPersonName() throws EmptyException{
		
		String text = personNamefield.getText();
		if(text.length() < 1) throw new EmptyException(ExceptionKind.PersonName);
		return text;
	}
	public String getCompany() throws EmptyException{
		
		String text = companyfield.getText();
		if(text.length() < 1) throw new EmptyException(ExceptionKind.PersonCompany);
		return text;
	}
	public String getTel() throws EmptyException{
		
		String text = telfield.getText();
		if(text.length() < 1) throw new EmptyException(ExceptionKind.PersonTel);
		return text;
	}
	public String getPhone() throws EmptyException{
	
		String text = phonefield.getText();
		if(text.length() < 1) throw new EmptyException(ExceptionKind.PersonPhone);
		return text;
	}
	
	public String getAddress() throws EmptyException{
		
		String text = addressPanel.getText();
		return text;
	}
	public String getCity() {
		return addressPanel.getCity();
	}
	public void clear() {
		personNamefield.setText("");
		companyfield.setText("");
		phonefield.setText("");
		telfield.setText("");
		addressPanel.clear();
	}
}
