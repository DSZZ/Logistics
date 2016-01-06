package edu.nju.logistics.ui.util.globalHint;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ColorChangingPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5324258646635093592L;
	
	JLabel hint;
	private Timer t;
	private Timer ttt;
	private GlobalHintInserter globalHintInserter;
	
	private int red = 125;
	private int green = 125;
	private int blue = 125;
	
	private static final int renewTime = 30;//50毫秒刷新一次
	private static final int stayTime = 700;// 停留毫秒数，除1000即为秒数

	public ColorChangingPanel(String hintWord, GlobalHintInserter globalHintInserter) {
		this.globalHintInserter = globalHintInserter;
		System.out.println("insert global hint : " + hintWord);
		setOpaque(false);
//		setBackground(Color.GRAY);
		int width = 1170;
		setBounds(0, 40,width, 30);
		
		hint = new JLabel(hintWord, JLabel.RIGHT);
		hint.setFont(new Font("宋体", Font.BOLD, 19));
		setLayout(null);
		hint.setBounds(0, 0, width, 30);
		this.add(hint);
		
		t = new Timer(renewTime, this);
		setVisible(true);
		t.start();
		hint.repaint();
	}

	public void actionPerformed(ActionEvent e) {
			if(red == 255){
				t.stop();
				Thread tt = new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							Thread.sleep(stayTime);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						ttt = new Timer(renewTime, new Decrease());
						ttt.start();
						repaint();
					}	
				});
				tt.start();
				
			}else{
				red += 5;
				green += 5;
				blue += 5;
			}
		hint.setForeground(new Color(red, green, blue));
//		hint.repaint();
		repaint();
	}
	
	class Decrease implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(red == 155){
				ttt.stop();
				remove(hint);
				globalHintInserter.selfRemove(ColorChangingPanel.this);
			}else{
				red -= 5;
				green -= 5;
				blue -= 5;
			}
			hint.setForeground(new Color(red, green, blue));
			repaint();	
		}
	}
	
}