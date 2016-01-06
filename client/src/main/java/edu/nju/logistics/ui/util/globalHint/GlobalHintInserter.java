package edu.nju.logistics.ui.util.globalHint;

import javax.swing.JFrame;

import edu.nju.logistics.ui.main.MainFrame;

public class GlobalHintInserter {

	private static final GlobalHintInserter g = new GlobalHintInserter();
	private GlobalHintInserter() {}
	
	public static synchronized void insertGlobalHint(String hint){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				g.privateInsertGlobalHint(hint);
			}
		}).start();
	}
	
	JFrame frame;
	ColorChangingPanel c = null;
	
	private synchronized void privateInsertGlobalHint(String hint){
		if(c != null){
			selfRemove(c);
		}
		c = new ColorChangingPanel(hint,this);
		((MainFrame)frame).getMainPanel().add(c);
		frame.repaint();
	}
	synchronized void selfRemove(ColorChangingPanel self){
		if(self == c){
			((MainFrame)frame).getMainPanel().remove(self);
			c = null;
			frame.repaint();
		}
	}
	public static void setFrame(JFrame frame) {
		g.frame = frame;
	}
}
