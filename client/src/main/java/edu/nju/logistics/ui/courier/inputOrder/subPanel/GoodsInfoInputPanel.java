package edu.nju.logistics.ui.courier.inputOrder.subPanel;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;

import edu.nju.logistics.ui.courier.inputOrder.ShowPanel_InputOrder;
import edu.nju.logistics.ui.courier.inputOrder.WeightObserver;
import edu.nju.logistics.ui.courier.inputOrder.EmptyException.EmptyException;
import edu.nju.logistics.ui.courier.inputOrder.EmptyException.ExceptionKind;

public class GoodsInfoInputPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5549788123745672707L;
	
	JLabel goodsName;
	JTextField goodsNamefield;
	
	JLabel amount;
	JTextField amountfield;

	JLabel realWeight;
	JTextField realWeightfield;
	
	JLabel size;
	JTextField sizefield;
	private WeightObserver dynamicObserver;
	
	int goodsSize;
	int goodsWeight;
	
	public void addWeightObserver(WeightObserver dynamicObserver) {
		this.dynamicObserver = dynamicObserver;
	}

	public GoodsInfoInputPanel(){
		this.setLayout(null);
		
		setBorder(new TitledBorder(new LineBorder(Color.YELLOW,1,true)
		,"  托运货物信息  ",TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.TOP
		,ShowPanel_InputOrder.LABELFONT,Color.red));
		
		goodsName = new JLabel("内件品名：");
		goodsName.setBounds(20,30,80,20);
		goodsName.setFont(ShowPanel_InputOrder.LABELFONT);
		this.add(goodsName);
		
		goodsNamefield = new JTextField();
		goodsNamefield.setBounds(100, 30,150, 22);
		goodsNamefield.setFont(ShowPanel_InputOrder.INPUTFONT);
		this.add(goodsNamefield);
		
		amount = new JLabel("原件数：");
		amount.setBounds(280,30,65,20);
		amount.setFont(ShowPanel_InputOrder.LABELFONT);
		this.add(amount);
		
		amountfield = new JTextField();
		amountfield.setBounds(345, 30,60, 22);
		amountfield.setFont(ShowPanel_InputOrder.INPUTFONT);
		amountfield.setDocument(new NumberLengthLimitedDmt(6));
		this.add(amountfield);
		
		realWeight = new JLabel("实际重量：");
		realWeight.setBounds(440,30,80,20);
		realWeight.setFont(ShowPanel_InputOrder.LABELFONT);
		this.add(realWeight);
		
		realWeightfield = new JTextField();
		realWeightfield.setBounds(520, 30,60, 22);
		realWeightfield.setFont(ShowPanel_InputOrder.INPUTFONT);
		realWeightfield.setDocument(new NumberLengthLimitedDmt(5));
		realWeightfield.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				try{
					goodsWeight = Integer.parseInt(realWeightfield.getText());
				}catch(java.lang.NumberFormatException e){goodsWeight = 0;}
				updateWeight();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				try{
					goodsWeight = Integer.parseInt(realWeightfield.getText());
				}catch(java.lang.NumberFormatException e){goodsWeight = 0;}
				updateWeight();
				
			}
			
		});
		this.add(realWeightfield);
		
		JLabel kg = new JLabel("千克");
		kg.setBounds(585,30,30,20);
		kg.setFont(ShowPanel_InputOrder.LABELFONT);
		this.add(kg);
		
		size = new JLabel("体积：");
		size.setBounds(650,30,50,20);
		size.setFont(ShowPanel_InputOrder.LABELFONT);
		this.add(size);
		
		sizefield = new JTextField();
		sizefield.setBounds(700, 30,80, 22);
		sizefield.setFont(ShowPanel_InputOrder.INPUTFONT);
		sizefield.setDocument(new NumberLengthLimitedDmt(8));
		sizefield.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				try{
					goodsSize = Integer.parseInt(sizefield.getText());
				}catch(java.lang.NumberFormatException e){goodsSize = 0;}
				updateWeight();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				try{
					goodsSize = Integer.parseInt(sizefield.getText());
				}catch(java.lang.NumberFormatException e){goodsSize = 0;}
				updateWeight();
				
			}
			
		});
		this.add(sizefield);
		
		JLabel cm3 = new JLabel("立方厘米");
		cm3.setBounds(785,30,60,20);
		cm3.setFont(ShowPanel_InputOrder.LABELFONT);
		this.add(cm3);
	}
	
	private void updateWeight(){
		int realWeight = goodsWeight;
		if(goodsSize/5000>goodsWeight){
			realWeight = goodsSize/5000;
		}
		dynamicObserver.createFee(realWeight);
	}
	
	public String getGoodsName() throws EmptyException{
		
		String text = goodsNamefield.getText();
		if(text.length() < 1) throw new EmptyException(ExceptionKind.GoodsName);
		return text;
	}
	public String getAmount() throws EmptyException{
		
		String text = amountfield.getText();
		if(text.length() < 1) throw new EmptyException(ExceptionKind.GoodsAmount);
		return text;
	}
	public String getRealWeight() throws EmptyException{
		
		String text = realWeightfield.getText();
		if(text.length() < 1) throw new EmptyException(ExceptionKind.GoodsRealWeight);
		return text;
	}
	public String getGoodsSize() throws EmptyException{
		
		String text = sizefield.getText();
		if(text.length() < 1) throw new EmptyException(ExceptionKind.GoodsSize);
		return text;
	}

	public void clear() {
		goodsNamefield.setText("");
		amountfield.setText("");
		realWeightfield.setText("");
		sizefield.setText("");
	}
}
