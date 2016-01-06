package edu.nju.logistics.ui.branchstaff.receive.check;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.branchstaff.load.chooseOrder.VectorBuilder_ChooseOrder;
import edu.nju.logistics.ui.branchstaff.receive.ShowPanel_Receive;
import edu.nju.logistics.ui.commonButton.ZYXButton;
import edu.nju.logistics.ui.util.tableBuilder.TableModel;
import edu.nju.logistics.vo.ReceiptState;
import edu.nju.logistics.vo.TransferSheetVO;

public class CheckPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9218916227507888933L;

	public CheckPanel(int width,int height, ShowPanel_Receive owner, TransferSheetVO transferSheet, ReceiptState status) {
		setBounds(0,0,width,height);
		setBorder(BorderFactory.createEtchedBorder());
		setOpaque(false);
		setLayout(null);
		
		JPanel mainInfoPanel = new MainInfoPanel(transferSheet,status,transferSheet.getItems().size());
		mainInfoPanel.setBounds(20, 20, width-40, 130);
		add(mainInfoPanel);
		
		
		
		VectorBuilder_ChooseOrder vectorGetter = new VectorBuilder_ChooseOrder(transferSheet.getItems());
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
		
		
		
		
		JButton okButton = new ZYXButton("确认接收");
		okButton.setBounds(800, 450, 160, 40);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				owner.confirmReceive();
			}
		});
		add(okButton);
		}

		public void setPreferredWidth(JTable table ,int index, int width){
			table.getColumnModel().getColumn(index).setPreferredWidth(width);
		}
}
