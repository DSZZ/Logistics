package edu.nju.logistics.ui.accountant.main;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MoneyDocument extends PlainDocument{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		// TODO Auto-generated method stub

		  char keyChar = str.charAt(0);
		  
		  boolean canInput = isNum(keyChar) || isDot(keyChar);
		  if(! canInput){
			  return;
		  }
		super.insertString(offs, str, a);
	}

	
	private boolean isNum(char keyChar){
		if(keyChar>='0' && keyChar <='9'){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean isDot(char keyChar){
		return keyChar=='.';
	}

}
