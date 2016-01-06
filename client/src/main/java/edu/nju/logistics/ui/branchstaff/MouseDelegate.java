package edu.nju.logistics.ui.branchstaff;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import edu.nju.logistics.ui.functionButton.MyButton;

public class MouseDelegate implements MouseListener{

	private BranchFrame showPanelChanger;
	private ShowPanelKind kind;

	private static MyButton temp=null;
	private MyButton button=null;
	
	public MouseDelegate(BranchFrame showPanelChanger,ShowPanelKind kind, MyButton button) {
		this.showPanelChanger = showPanelChanger;
		this.kind = kind;
		this.button = button;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouse clicked! kind is " + kind );
		showPanelChanger.mouseClicked(e);
		handleButton(button);
		switch(kind){
		case car:showPanelChanger.changeShowPanelTo(showPanelChanger.SHOWPANEL_CAR);
			break;
		case distribute:showPanelChanger.changeShowPanelTo(showPanelChanger.SHOWPANEL_DISTRIBUTE);
			break;
		case driver:showPanelChanger.changeShowPanelTo(showPanelChanger.SHOWPANEL_DRIVER);
			break;
		case load:showPanelChanger.changeShowPanelTo(showPanelChanger.SHOWPANEL_LOAD);
			break;
		case pay:showPanelChanger.changeShowPanelTo(showPanelChanger.SHOWPANEL_PAYMENT);
			break;
		case receive:showPanelChanger.changeShowPanelTo(showPanelChanger.SHOWPANEL_RECEIVE);
			break;
		default:
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
