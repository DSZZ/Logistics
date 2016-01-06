package edu.nju.logistics.ui.branchstaff.CarAndDriverManagement;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import edu.nju.logistics.ui.branchstaff.BranchFrame;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;
import edu.nju.logistics.ui.util.tableBuilder.TableHolder;

public class CommonShowPanel extends JPanel implements Refresher{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7609192056664316812L;
	
	private CommonService serviceProvider;
	
	private TableHolder tabelPanel;

	public CommonShowPanel(CommonService serviceProvider) {
		this.serviceProvider = serviceProvider;
		initShowPanel();
	}
	
	protected void initShowPanel() {
		setBorder(BorderFactory.createEtchedBorder());
		setOpaque(false);
		setLayout(null);
		
		serviceProvider.updateList();
		serviceProvider.setRefresher(this);
		String name = serviceProvider.getName();

		tabelPanel = new TableHolder(serviceProvider.getVectorBuilder()
				, serviceProvider.getOrderCaller(),this,name + "管理(可右键单击想操作的"+name+")");
		tabelPanel.setSingleChoose();
		tabelPanel.allowNullChoice();
		tabelPanel.addRightClickMenu(serviceProvider.getRightClickDealerList());
		tabelPanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
		this.add(tabelPanel);
	}
	
	@Override
	public void refresh() {
		remove(tabelPanel);
		repaint();
		initShowPanel();
	}

	@Override
	public String getButtonName() {
		return "刷新";
	}

}
