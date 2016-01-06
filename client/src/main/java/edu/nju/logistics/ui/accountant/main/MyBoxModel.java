package edu.nju.logistics.ui.accountant.main;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class MyBoxModel extends AbstractListModel implements ComboBoxModel  {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String days[] = null;

		private String item = null;

		private int monthToDay[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		public MyBoxModel(String year, String month) {
			this.init(year, month);
		}

		// 根据年份和月份初始化当月天数
		private void init(String year, String month) {
			if (isLeapYear(year)) {
				this.monthToDay[1] = 29;
			}
			int month_num = Integer.parseInt(month);
			this.days = new String[this.monthToDay[month_num - 1]];
			for (int i = 1; i <= this.monthToDay[month_num - 1]; i++) {
				if(i < 10)
					this.days[i-1] = "0" + String.valueOf(i);
				else
					this.days[i-1] = String.valueOf(i);
			}
		}

		// 判断是否是闰年
		private boolean isLeapYear(String year) {
			int y = Integer.parseInt(year);
			if ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) {
				return true;
			}
			return false;
		}

		@Override
		public Object getElementAt(int index) {
			return this.days[index];
		}

		@Override
		public int getSize() {
			return this.days.length;
		}

		@Override
		public Object getSelectedItem() {
			return item;
		}

		@Override
		public void setSelectedItem(Object anItem) {
			item = (String) anItem;
		}

	
}
