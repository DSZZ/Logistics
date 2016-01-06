package edu.nju.logistics.ui.branchstaff.load.inputPersonInfo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.nju.logistics.ui.branchstaff.load.ShowPanel_Load;
import edu.nju.logistics.ui.commonButton.ZYXButton;

public class InputPersonInfoPanel extends JPanel{

	private ShowPanel_Load owner;

	public final static Font LABELFONT = new java.awt.Font("宋体", 0, 23);
	public final static Font INPUTFONT = new java.awt.Font("黑体", 0, 21);
	
	public InputPersonInfoPanel(ShowPanel_Load showPanel_Load, int width, int height) {
		
		owner = showPanel_Load;
		setBounds(0,0,width,height);
		setBorder(BorderFactory.createEtchedBorder());
		setOpaque(false);
		setLayout(null);
		
		int halfInterval = 30;
		int unitHeight = 30;
		int cross = 10;
		
		int Xp = -40;
		int Yp = -100;
		
		JLabel jzy = new JLabel("监装员：",JLabel.RIGHT);
		jzy.setBounds(0 + Xp, height/2 - halfInterval + Yp, width/2 - cross, unitHeight);
		jzy.setFont(LABELFONT);
		add(jzy);
		
		JTextField jzyfield = new JTextField();
		jzyfield.setBounds(width/2 + cross + Xp, height/2 - halfInterval + Yp, 150, unitHeight);
		jzyfield.setFont(INPUTFONT);
		add(jzyfield);
		
		JLabel yyy = new JLabel("押运员：",JLabel.RIGHT);
		yyy.setBounds(0 + Xp, height/2 + halfInterval + Yp, width/2 - cross, unitHeight);
		yyy.setFont(LABELFONT);
		add(yyy);
		
		JTextField yyyfield = new JTextField();
		yyyfield.setBounds(width/2 + cross + Xp, height/2 + halfInterval + Yp, 150, unitHeight);
		yyyfield.setFont(INPUTFONT);
		add(yyyfield);
		
		JLabel warning = new JLabel("请完整填写信息！！",JLabel.CENTER);
		warning.setBounds(0, 10, width, height/2 - halfInterval + Yp);
		warning.setFont(new java.awt.Font("宋体", 0, 25));
		warning.setForeground(Color.red);
		warning.setVisible(false);
		add(warning);
		
		JButton okButton = new ZYXButton("确认");
		okButton.setBounds(800, 450, 160, 40);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String observer = jzyfield.getText().trim();
				String supercargo = yyyfield.getText().trim();
				
				if(observer.length() < 1 || supercargo.length() < 1){
					warning.setVisible(true);
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(1500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if(warning != null)
							warning.setVisible(false);
						}
					}).start();
				}
				else{
					owner.inputObserverAndSuperCargo(observer,supercargo);
				}
			}
		});
		add(okButton);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6191389085361036116L;

	
	
}
