package edu.nju.logistics.ui.util.tableBuilder;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;

/**
 * 这是当表为空时，弹出的空白页面
 * @author ThinkPad
 *
 */
public class NilHintPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2846287867376484354L;
	JLabel nilHint;
	JLabel refreshHint;
	JButton refreshButton;
	Refresher refresher;
	
	public NilHintPanel(Refresher refresher, String hint) {
		setOpaque(false);
		setLayout(null);
		this.refresher = refresher;
		nilHint = new JLabel(hint,JLabel.CENTER);
		nilHint.setBounds(0,180,980,23);
		nilHint.setFont(new java.awt.Font(Font.SANS_SERIF, 1, 24));
		nilHint.setForeground(Color.green);
		this.add(nilHint);
		
		refreshHint = new JLabel("请点击下方按钮"+refresher.getButtonName()+"或稍后再试",JLabel.CENTER);
		refreshHint.setBounds(0,230,980,23);
		refreshHint.setFont(new java.awt.Font(Font.SANS_SERIF, 1, 22));
		refreshHint.setForeground(Color.yellow);
		this.add(refreshHint);
		
		int buttonWidth = 310;
		refreshButton = new JButton(refresher.getButtonName());
		refreshButton.setBounds(980/2 - buttonWidth/2,280,buttonWidth,(int)(buttonWidth*0.5));
		refreshButton.setFont(new java.awt.Font(Font.SANS_SERIF, 1, 20));
		refreshButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				refresher.refresh();
				if(refresher.getButtonName() == "刷新")
					GlobalHintInserter.insertGlobalHint("刷新成功！");
			}
		});
		this.add(refreshButton);
	}
}
