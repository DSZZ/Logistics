package edu.nju.logistics.ui.courier.inputOrder.subPanel;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * @author ThinkPad
 * 这个内部类用于限制订单编号：1.只能输入数字 2.将长度限制为10
 */
public class NumberLengthLimitedDmt  extends PlainDocument {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -1433013296653368578L;
	
	private int limit; 
	   public NumberLengthLimitedDmt(int limit) {
	    super();
	       this.limit = limit;
	    }  
	   public void insertString
	     (int offset, String  str, AttributeSet attr)
	                                   throws BadLocationException {   
	       if (str == null){
	        return;
	       }
	       if ((getLength() + str.length()) <= limit) {
	       
	       char[] upper = str.toCharArray();
	       int length=0;
	       for (int i = 0; i < upper.length; i++) {       
	           if (upper[i]>='0'&&upper[i]<='9'){           
	              upper[length++] = upper[i];
	           }
	       }
	         super.insertString(offset, new String(upper,0,length), attr);
	      }
	    }
	}

