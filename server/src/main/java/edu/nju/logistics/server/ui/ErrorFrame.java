package edu.nju.logistics.server.ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

import edu.nju.logistics.ui.util.FrameUtil;

public class ErrorFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4366931546056257044L;

	public ErrorFrame() {
		setLayout(new BorderLayout());
		
		JLabel successHint = new JLabel("您已经启动了服务器！",JLabel.CENTER);
		successHint.setFont(new Font("楷体",Font.BOLD,20));
		add(successHint,BorderLayout.CENTER);
		
		int width = 270;
		this.setSize(width,(int)(width*0.618));
		this.setVisible(true);
		this.setDefaultCloseOperation(3);
	}
}
