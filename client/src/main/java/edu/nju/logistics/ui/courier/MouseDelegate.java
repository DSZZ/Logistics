package edu.nju.logistics.ui.courier;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import edu.nju.logistics.ui.functionButton.MyButton;

public class MouseDelegate implements MouseListener{

	private CourierFrame showPanelChanger;
	private ShowPanelKind kind;
	private static MyButton temp=null;
	private MyButton button=null;
	
	public MouseDelegate(CourierFrame showPanelChanger,ShowPanelKind kind,MyButton button) {
		this.showPanelChanger = showPanelChanger;
		this.kind = kind;
		this.button=button;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouse clicked! kind is " + kind );
		showPanelChanger.mouseClicked(e);
		handleButton(button);
		switch(kind){
		case dispatch:
			showPanelChanger.changeShowPanelTo(showPanelChanger.SHOWPANEL_DISPATCH);
			break;
		case input:
			showPanelChanger.changeShowPanelTo(showPanelChanger.SHOWPANEL_INPUT);
			break;
		default:System.out.println("kind no found!");
			break;
		
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		showPanelChanger.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		showPanelChanger.mouseExited(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		showPanelChanger.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		showPanelChanger.mouseReleased(e);
	}
	
	private static void handleButton(MyButton e){
		if(temp!=null){
			temp.restore();
		}
		temp=e;
		temp.clicked();
	}

}
