package edu.nju.logistics.ui.util.tableBuilder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.commonButton.ZYXButton;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;

public class TableHolder extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7810280548776721247L;
	
	private Refresher refresher;
	private OKCallerService okCaller;
	
	private JButton refreshButton;
	private JButton okButton;
	private JButton selectAllButton;
	private JButton selectContrastButton;
	
	private TableModel tabelModel;
	private JTable table;
	private JScrollPane scrollPane;
	JLabel ctrlHint;
	
	/**
	 * 如果刷新按钮执行的是刷新功能的话，为true，如果执行的是一般功能的话，为false;
	 */
	boolean refreshFunction = true;
	boolean allowNullChoice = false;
	
	public TableHolder(VectorGetter vectorGetter,
			OKCallerService okCaller, Refresher refresher,String title) {
		this.refresher = refresher;
		this.okCaller = okCaller;
		initialConponent(vectorGetter,title);
	}
	
	public void setPreferredWidth(int index, int width){
		table.getColumnModel().getColumn(index).setPreferredWidth(width);
	}

	private void initialConponent(VectorGetter vectorGetter,String title) {
		setOpaque(false);//设置透明
		setLayout(null);
//		tabelModel = new TableModel(new RecordPaymendVectorBuilder(unpayedOrderList));
		tabelModel = new TableModel(vectorGetter);
		table = new JTable(tabelModel);
		table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(),32));//设置表头高度
		table.setRowHeight(50);//设置行高
//		this.table.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14)); //设置表头字体
		this.table.setFont(new Font("宋体", Font.PLAIN, 19));//设置表项字体
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);//设置表头居中
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
		tcr.setHorizontalAlignment(JLabel.CENTER);// 设置table内容居中
		table.setDefaultRenderer(Object.class, tcr);// 设置table内容居中
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabelModel);  //设置点击表头自动排序的功能
        table.setRowSorter(sorter);  //设置点击表头自动排序的功能
        
        this.scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 50, 760, 490);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        MyTableHandler.decorateTableAndSpane(table,scrollPane);
		this.add(scrollPane);
		
		JLabel titleLabel = new JLabel(title+"!",JLabel.CENTER);
		titleLabel.setFont(new Font("楷体", Font.BOLD, 20));
		titleLabel.setOpaque(false);
		titleLabel.setBounds(20, 0, 760, 50);
		this.add(titleLabel);
		
		selectAllButton = new ZYXButton("全选");
		selectAllButton.setBounds(800, 50, 160, 40);
		selectAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table.setRowSelectionInterval(0, vectorGetter.getRowsCount() - 1);
			}
		});
		add(selectAllButton);
		
		ctrlHint = new JLabel("提示：按住Ctrl可多选");
		ctrlHint.setBounds(800, 50 + 140, 160, 40);
		add(ctrlHint);
		
		selectContrastButton = new ZYXButton("反选");
		selectContrastButton.setBounds(800, 50 + 80, 160, 40);
		selectContrastButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] selectedRows = table.getSelectedRows();
				table.clearSelection();
				a:for(int i = 0 ; i < table.getRowCount() ; i++){
					for(int selection : selectedRows){
						if(selection == i)
							continue a;
					}
					table.addRowSelectionInterval(i, i);
				}
			}
		});
		add(selectContrastButton);
		
		okButton = new ZYXButton(okCaller.getButtonName());
		okButton.setBounds(800, 450, 160, 40);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] selectedRows = table.getSelectedRows();
				
				if(!allowNullChoice && selectedRows.length == 0){
					GlobalHintInserter.insertGlobalHint("请至少选择一项！");
					return;
				}

				okCaller.select(selectedRows);
				if(refreshFunction){
					refresher.refresh();
				}
			}
		});
		add(okButton);
		
		refreshButton = new ZYXButton(refresher.getButtonName());
		refreshButton.setBounds(800, 370, 160, 40);
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresher.refresh();
				if(refresher.getButtonName().trim() == "刷新")
					GlobalHintInserter.insertGlobalHint("刷新成功！");
			}
		});
		add(refreshButton);
		
		if(vectorGetter.getRowsCount() == 0){
			JLabel nullHint = new JLabel("当前无任何可管理的内容，请点击右侧增加或刷新",JLabel.CENTER);
			nullHint.setFont(new Font("宋体", Font.BOLD, 25));
			nullHint.setForeground(Color.MAGENTA);
			nullHint.setBounds(20, 50, 760, 490);
			add(nullHint);
		}
	}

	public void setSingleChoose() {
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		remove(selectAllButton);
		remove(selectContrastButton);
		remove(ctrlHint);
	}
	
	public void addRightClickMenu(ArrayList<RightClickDealer> dealerList){
		JPopupMenu menu = new JPopupMenu();
		for(RightClickDealer dealer : dealerList){
			JMenuItem item = new JMenuItem(dealer.getServiceName());
			item.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					dealer.dealRightClick(table.getSelectedRow());
					
				}
			});
			menu.add(item);
		}
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getSource() == table
                        && e.getButton() == MouseEvent.BUTTON3) {
					
					int row = table.rowAtPoint(e.getPoint()); 
					table.setRowSelectionInterval(row, row);
                    
					menu.show((Component) table, e.getX(), e.getY());
                    }
			}
		});
	}

	public void changeRefreshButtonToCommonButton() {
		refreshFunction = false;
	}
	public void allowNullChoice() {
		allowNullChoice = true;
	}

}
