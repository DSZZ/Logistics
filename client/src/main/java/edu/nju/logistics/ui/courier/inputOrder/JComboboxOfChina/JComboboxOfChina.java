package edu.nju.logistics.ui.courier.inputOrder.JComboboxOfChina;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

@SuppressWarnings({"rawtypes","unchecked"})
public class JComboboxOfChina {
	private JComboBox combobox_privince;
	private JComboBox combobox_city;
	private JComboBox combobox_area;
    private DefaultComboBoxModel model1 = new DefaultComboBoxModel();
	private DefaultComboBoxModel model2 = new DefaultComboBoxModel();
	private DefaultComboBoxModel model3 = new DefaultComboBoxModel();
	
	
	public JComboboxOfChina() {
		//����ʡ����������������
        //���õ�һ�����ݣ���xml�����ȡ����
		for(String str : XMLDao.getProvinces()) {
			model1.addElement(str);
		}
		combobox_privince = new JComboBox(model1);
		combobox_privince.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JComboBox source = (JComboBox) evt.getSource();
				//���ݻ�ȡ��ʡ���ҵ�������ļ������
				String provinces = (String) source.getSelectedItem();
				List<String> cities = XMLDao.getCities(provinces);
				model2.removeAllElements();
				for (String str : cities) {
					model2.addElement(str);
				}
				model3.removeAllElements();
				for (String str : XMLDao.getDistricts(cities.get(0))) {
					model3.addElement(str);
				}
			}
		});
		//���ö�������
		for (String str : XMLDao.getCities("北京市")) {
			model2.addElement(str);
		}
		combobox_city = new JComboBox(model2);
		combobox_city.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				JComboBox source = (JComboBox) evt.getSource();
				String city = (String) source.getSelectedItem();
				List<String> districts = XMLDao.getDistricts(city);
				model3.removeAllElements();
				for (String str : districts) {
					model3.addElement(str);
				}
			}
		});
		//������������
		for (String str : XMLDao.getDistricts("北京市")) {
			model3.addElement(str);
		}
		combobox_area = new JComboBox(model3);
	}

	public JComboBox getCombobox_privince() {
		return combobox_privince;
	}

	public JComboBox getCombobox_city() {
		return combobox_city;
	}

	public JComboBox getCombobox_area() {
		return combobox_area;
	}
}
