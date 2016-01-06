package edu.nju.logistics.server.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import edu.nju.logistics.ui.util.FrameUtil;

public class ServerFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 547984238582820324L;

	public ServerFrame() throws UnknownHostException {
		setLayout(new BorderLayout());
		
		JLabel successHint = new JLabel("服务器启动成功！",JLabel.CENTER);
		successHint.setFont(new Font("楷体",Font.BOLD,20));
		add(successHint,BorderLayout.CENTER);
		
		JLabel ipHint = new JLabel("本机ip地址：" + InetAddress.getLocalHost().toString(),JLabel.CENTER);
		add(ipHint,BorderLayout.SOUTH);
		
		int width = 270;
		this.setSize(width,(int)(width*0.618));
		this.setVisible(true);
		this.setDefaultCloseOperation(3);
	}
}
