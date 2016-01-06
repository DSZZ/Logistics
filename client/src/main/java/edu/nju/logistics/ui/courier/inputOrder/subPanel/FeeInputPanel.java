package edu.nju.logistics.ui.courier.inputOrder.subPanel;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;

import edu.nju.logistics.ui.courier.inputOrder.DayAndTimeShower;
import edu.nju.logistics.ui.courier.inputOrder.ShowPanel_InputOrder;
import edu.nju.logistics.ui.courier.inputOrder.EmptyException.EmptyException;
import edu.nju.logistics.ui.courier.inputOrder.EmptyException.ExceptionKind;
import edu.nju.logistics.vo.ordermanagement.OrderKind;

public class FeeInputPanel extends JPanel implements DayAndTimeShower{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5612539308929179184L;
	
	JLabel orderKind;
	@SuppressWarnings("rawtypes")
	JComboBox orderKindSelection;
	String[] kind = {"标准","经济","特快"};
	
	JLabel packagefee;
	JTextField packagefeefield;
	
	JLabel autofee;
	JLabel totalfee;
	JLabel time;
	
	double rawfee;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FeeInputPanel(){
		this.setLayout(null);
		
		setBorder(new TitledBorder(new LineBorder(Color.YELLOW,1,true)
		,"  费用信息  ",TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.TOP
		,ShowPanel_InputOrder.LABELFONT,Color.red));
		
		orderKind = new JLabel("快递类型：");
		orderKind.setBounds(20,30,80,20);
		orderKind.setFont(ShowPanel_InputOrder.LABELFONT);
		this.add(orderKind);
		
		orderKindSelection = new JComboBox(kind);
		orderKindSelection.setBounds(100, 30, 80, 22);
		orderKindSelection.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				FeeInputPanel.this.showFee(rawfee);			
			}
		});
		this.add(orderKindSelection);
		
		packagefee = new JLabel("包装费：");
		packagefee.setBounds(215,30,65,20);
		packagefee.setFont(ShowPanel_InputOrder.LABELFONT);
		this.add(packagefee);
		
		packagefeefield = new JTextField();
		packagefeefield.setBounds(280, 30,40, 22);
		packagefeefield.setFont(ShowPanel_InputOrder.INPUTFONT);
		packagefeefield.setDocument(new NumberLengthLimitedDmt(3));
		packagefeefield.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				FeeInputPanel.this.showFee(rawfee);	
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				FeeInputPanel.this.showFee(rawfee);				
			}		
		});
		this.add(packagefeefield);
		
		JLabel timeHint = new JLabel("预计到达时间：");
		timeHint.setBounds(400,25,150,20);
		timeHint.setFont(new java.awt.Font("Dialog", 1, 18));
		this.add(timeHint);
		
		time = new JLabel("1");
		time.setBounds(550,25,50,20);
		time.setFont(new java.awt.Font("Dialog", 1, 22));
		time.setForeground(Color.RED);
//		autofee.setVisible(false);
		this.add(time);
		
		JLabel autofeeHint = new JLabel("天       运费：");
		autofeeHint.setBounds(580,25,130,20);
		autofeeHint.setFont(new java.awt.Font("Dialog", 1, 18));
		this.add(autofeeHint);
		
		autofee = new JLabel("0");
		autofee.setBounds(705,25,50,20);
		autofee.setFont(new java.awt.Font("Dialog", 1, 22));
		autofee.setForeground(Color.RED);
//		autofee.setVisible(false);
		this.add(autofee);
		
		JLabel totalfeeHint = new JLabel("应收费用：");
		totalfeeHint.setBounds(760,25,100,20);
		totalfeeHint.setFont(new java.awt.Font("Dialog", 1, 18));
		this.add(totalfeeHint);
		
		totalfee = new JLabel("0");
		totalfee.setBounds(870,25,50,20);
		totalfee.setFont(new java.awt.Font("Dialog", 1, 22));
		totalfee.setForeground(Color.RED);
//		totalfee.setVisible(false);
		this.add(totalfee);
	}

	
	/**
	 * @param fee
	 * 显示运费
	 */
	@Override
	public void showFee(double rawfee){
		this.rawfee = rawfee;
		int fee = calculateFeeByOrderKind(rawfee);
		autofee.setText(Integer.toString(fee));
		int packageMoney = 0;
		try{
			packageMoney += Integer.parseInt(packagefeefield.getText());
		}catch(java.lang.NumberFormatException e){}
		
		autofee.setText(Integer.toString(fee));
		totalfee.setText(Integer.toString(fee+packageMoney));
		this.repaint();
	}
	
	private int calculateFeeByOrderKind(double rawfee) {
		switch((String)orderKindSelection.getSelectedItem()){
		case "标准":return (int) rawfee;
		case "经济":return (int) (rawfee*18/23);
		case "特快":return (int) (rawfee*25/23);
		default:System.out.println("Calculate Fee By Order Kind Error!!!!!");return 0;
		}
		
	}


	public int getPackageMoney() throws EmptyException{
		
		String text = packagefeefield.getText();
		if(text.length() < 1) throw new EmptyException(ExceptionKind.PackageFee);
		return Integer.parseInt(text);
	}
	public OrderKind getOrderKind(){
		switch((String)orderKindSelection.getSelectedItem()){
		case "标准":return OrderKind.standard;
		case "经济":return OrderKind.economic;
		case "特快":return OrderKind.quick;
		default:System.out.println("Order Kind Error!!!!!");return OrderKind.standard;
		}
	}
	public int getTransportationCost(){
		String text = autofee.getText();
		return Integer.parseInt(text);
	}

	@Override
	public void showTime(int day) {
		time.setText(Integer.toString(day));
	}


	public void clear() {
		packagefeefield.setText("");
		autofee.setText("");
		totalfee.setText("");
		rawfee = 0.0;
	}
	
}