package edu.nju.logistics.ui.util.reconnection;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;

public class ReconnectPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1430304693605722876L;
	
	public ReconnectSuccessObserver observer;
	private final static int reconnectInterval = 5;
	private int nextReconnectTime = reconnectInterval;
	
	private JLabel reconnectfailHint;
	private JLabel refreshHint;
	
	private boolean connectSuccessful;

	public ReconnectPanel(ReconnectSuccessObserver observer) {
		System.out.println("ReconnectPanel build success");
		this.observer = observer;
		connectSuccessful = false;
		
		setBorder(BorderFactory.createEtchedBorder());
		setOpaque(false);
		setLayout(null);
		
		reconnectfailHint = new JLabel("重连失败！",JLabel.CENTER);
		reconnectfailHint.setBounds(0,100,980,23);
		reconnectfailHint.setFont(new java.awt.Font(Font.SANS_SERIF, 1, 20));
		reconnectfailHint.setForeground(Color.red);
		reconnectfailHint.setVisible(false);
		this.add(reconnectfailHint);
		
		JLabel nilHint = new JLabel("抱歉，与主机网络连接失败",JLabel.CENTER);
		nilHint.setBounds(0,180,980,23);
		nilHint.setFont(new java.awt.Font(Font.SANS_SERIF, 1, 24));
		nilHint.setForeground(Color.green);
		this.add(nilHint);
		
		refreshHint = new JLabel("系统将在 < "+nextReconnectTime+" > 秒后自动重连",JLabel.CENTER);
		refreshHint.setBounds(0,230,980,23);
		refreshHint.setFont(new java.awt.Font(Font.SANS_SERIF, 1, 22));
		refreshHint.setForeground(Color.yellow);
		this.add(refreshHint);
		
		int buttonWidth = 310;
		
		JButton refreshButton = new JButton("手动重连");
		refreshButton.setBounds(980/2 - buttonWidth/2,280,buttonWidth,(int)(buttonWidth*0.5));
		refreshButton.setFont(new java.awt.Font(Font.SANS_SERIF, 1, 20));
		refreshButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				connectSuccessful = reconnect();
				nextReconnectTime = reconnectInterval + 1;
				
			}
		});
		this.add(refreshButton);
	}
	
	@Override
	protected void finalize(){
		connectSuccessful = true;
	}
	private boolean reconnect() {
		try {
			Naming.lookup("rmi://" + RMI.getIP() + ":2014/ConnectionTester");
		} catch (Exception e) {
			reconnectfailHint.setVisible(true);
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					reconnectfailHint.setVisible(false);
				}
			});
			t.start();
			return false;
		}
		observer.informReconnectSuccess();
		GlobalHintInserter.insertGlobalHint("重连服务器成功！");
		return true;
	}
	
	public void startReconnection(){
		connectSuccessful=false;
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while(!connectSuccessful){
					refreshHint.setText("系统将在 < "+nextReconnectTime+" > 秒后自动重连");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(nextReconnectTime == 0){
						connectSuccessful = reconnect();
						nextReconnectTime = reconnectInterval;
					}else{
						nextReconnectTime --;
					}
				}
				
			}

			
		});
		t.start();
		
	}

}
