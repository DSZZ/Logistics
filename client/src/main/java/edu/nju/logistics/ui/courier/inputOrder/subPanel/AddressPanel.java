package edu.nju.logistics.ui.courier.inputOrder.subPanel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.nju.logistics.ui.courier.inputOrder.AddressChangeObserver;
import edu.nju.logistics.ui.courier.inputOrder.ShowPanel_InputOrder;
import edu.nju.logistics.ui.courier.inputOrder.EmptyException.EmptyException;
import edu.nju.logistics.ui.courier.inputOrder.EmptyException.ExceptionKind;
import edu.nju.logistics.ui.courier.inputOrder.JComboboxOfChina.JComboboxOfChina;

public class AddressPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3517833687609532825L;
	int width = 90;
	
	@SuppressWarnings("rawtypes")
	JComboBox combobox_privince,combobox_city,combobox_area;
	JTextField detailAddress;
	AddressChangeObserver observer;
	
	public AddressPanel() {
		this.setLayout(null);
		
		JComboboxOfChina box = new JComboboxOfChina();

		combobox_privince = box.getCombobox_privince();
		combobox_privince.setBounds(0, 0, width, 25);
		this.add(combobox_privince);
		
		combobox_city = box.getCombobox_city();
		combobox_city.setBounds(width, 0, width, 25);
		combobox_city.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				observer.addressChangedNotify();			
			}
		});
		this.add(combobox_city);
		
		combobox_area = box.getCombobox_area();
		combobox_area.setBounds(2*width, 0, width, 25);
		this.add(combobox_area);
		
		detailAddress = new JTextField();
		detailAddress.setBounds(3*width, 0, 300, 25);
		detailAddress.setFont(ShowPanel_InputOrder.INPUTFONT);
		this.add(detailAddress);
		
		this.setVisible(true);
	}
	public String getText() throws EmptyException{
		String detailAddressText = detailAddress.getText();
		if(detailAddressText.length() < 1) throw new EmptyException(ExceptionKind.PersonAddress);
		return (String)combobox_privince.getSelectedItem() + "-"
		+ (String)combobox_city.getSelectedItem() + "-"
		+ (String)combobox_area.getSelectedItem() + "-" + detailAddressText;
	}
	public void addObserver(AddressChangeObserver observer) {
		this.observer = observer;
	}
	public String getCity() {
		return (String)combobox_city.getSelectedItem();
	}
	public void clear() {
		detailAddress.setText("");
		
	}
}
