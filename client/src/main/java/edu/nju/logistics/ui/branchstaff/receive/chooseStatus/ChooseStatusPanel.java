package edu.nju.logistics.ui.branchstaff.receive.chooseStatus;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import edu.nju.logistics.ui.branchstaff.receive.ShowPanel_Receive;
import edu.nju.logistics.ui.commonButton.ZYXButton;
import edu.nju.logistics.vo.ReceiptState;

public class ChooseStatusPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3297718808983238812L;

	public final static Font BUTTONFONT = new java.awt.Font("宋体", 0, 25);
	
	public ChooseStatusPanel(ShowPanel_Receive owner,int width, int height) {
		setBounds(0,0,width,height);
		setBorder(BorderFactory.createEtchedBorder());
		setOpaque(false);
		setLayout(null);
		
		int interval = 50;
		int unitWidth = 160;
		int unitHeight = 30;
		int xp = -70;
		int yp = -50;
		
		JLabel hint = new JLabel("请选择货物到达状态",JLabel.CENTER);
		hint.setBounds(0, 100 , width + yp, unitHeight);
		hint.setFont(new Font("楷体", 0, 27));
		add(hint);
		
		JRadioButton broken = new JRadioButton("损坏");
		broken.setBounds( xp + width/2 , height/2 - interval + yp, unitWidth, unitHeight);
		broken.setOpaque(false);
		broken.setFont(BUTTONFONT);
	    add(broken);
	    
	    JRadioButton complete = new JRadioButton("完整");
	    complete.setBounds( xp + width/2 , height/2 + yp, unitWidth, unitHeight);
	    complete.setOpaque(false);
	    complete.setFont(BUTTONFONT);
	    add(complete);
	    
	    JRadioButton lost = new JRadioButton("丢失");
	    lost.setBounds( xp + width/2 , height/2 + interval + yp, unitWidth, unitHeight);
	    lost.setOpaque(false);
	    lost.setFont(BUTTONFONT);
	    add(lost);
	    
	    ButtonGroup bg = new ButtonGroup();
	    bg.add(broken);
	    bg.add(complete);
	    bg.add(lost);
		
		
		
		JButton okButton = new ZYXButton("确认状态");
		okButton.setBounds(800, 450, 160, 40);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReceiptState state = null;
				if(broken.isSelected())
					state = ReceiptState.BROKEN;
				else if(lost.isSelected())
					state = ReceiptState.LOST;
				else if(complete.isSelected())
					state = ReceiptState.COMPLETE;
				else
					return;
				owner.chooseStatus(state);
			}
		});
		add(okButton);
	}
}
