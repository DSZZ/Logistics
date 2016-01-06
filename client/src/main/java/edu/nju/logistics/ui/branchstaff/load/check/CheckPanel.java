package edu.nju.logistics.ui.branchstaff.load.check;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import edu.nju.logistics.bl.service.branchmanagement.BranchManagementService;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.branchstaff.load.DataContainer;
import edu.nju.logistics.ui.branchstaff.load.ShowPanel_Load;
import edu.nju.logistics.ui.branchstaff.load.chooseOrder.VectorBuilder_ChooseOrder;
import edu.nju.logistics.ui.commonButton.ZYXButton;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.tableBuilder.TableModel;
import edu.nju.logistics.vo.UserVO;

public class CheckPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4919356614587405491L;
	
	private BranchManagementService branchManagement;
	private DisconnectInformer disconnectInformer;

	public CheckPanel(DataContainer dataContainer, BranchManagementService branchManagement
			,int width, int height, ShowPanel_Load showPanel_Load,UserVO userVO
			, DisconnectInformer disconnectInformer) {
		this.branchManagement = branchManagement;
		setBounds(0,0,width,height);
		setBorder(BorderFactory.createEtchedBorder());
		setOpaque(false);
		setLayout(null);
		initialComponents(dataContainer,width,height,userVO,showPanel_Load);
	}

	public void setPreferredWidth(JTable table ,int index, int width){
		table.getColumnModel().getColumn(index).setPreferredWidth(width);
	}
	private void initialComponents(DataContainer dataContainer,int width, int height,UserVO userVO, ShowPanel_Load showPanel_Load) {
		
		JPanel mainInfoPanel = new MainInfoPanel(dataContainer);
		mainInfoPanel.setBounds(20, 20, width-40, 130);
		add(mainInfoPanel);
		
		
		
		VectorBuilder_ChooseOrder vectorGetter = new VectorBuilder_ChooseOrder(dataContainer.selectedOrderList);
		TableModel tabelModel = new TableModel(vectorGetter);
		JTable table = new JTable(tabelModel);
		table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(),32));//设置表头高度
		table.setRowHeight(50);//设置行高
//		this.table.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14)); //设置表头字体
		table.setFont(new Font("宋体", Font.PLAIN, 19));//设置表项字体
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);//设置表头居中
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
		tcr.setHorizontalAlignment(JLabel.CENTER);// 设置table内容居中
		table.setDefaultRenderer(Object.class, tcr);// 设置table内容居中
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabelModel);  //设置点击表头自动排序的功能
        table.setRowSorter(sorter);  //设置点击表头自动排序的功能
        
        JScrollPane scrollPane = new JScrollPane(table);
        int tableHeight = 175;
        scrollPane.setBounds(20, tableHeight, 760, 550 - tableHeight);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        MyTableHandler.decorateTableAndSpane(table,scrollPane);
		this.add(scrollPane);
		
		setPreferredWidth(table, 0, 80);
		setPreferredWidth(table, 1, 40);
		setPreferredWidth(table, 2, 30);
		setPreferredWidth(table, 3, 40);
		setPreferredWidth(table, 4, 40);
		setPreferredWidth(table, 5, 40);
		setPreferredWidth(table, 6, 200);
		
		
		
		
		
		JButton okButton = new ZYXButton("确认装车");
		okButton.setBounds(800, 450, 160, 40);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					branchManagement.load(dataContainer.selectedOrderList,dataContainer.destinationid
							,dataContainer.carid,dataContainer.driverid,dataContainer.observer
							,dataContainer.supercargo,dataContainer.fee,userVO.getId());
					GlobalHintInserter.insertGlobalHint("装车成功！");
				} catch (RemoteException e) {
					disconnectInformer.findDisconnect();
				}
				showPanel_Load.initialize();
			}
		});
		add(okButton);
	}
}
