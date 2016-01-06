package edu.nju.logistics.ui.keeper;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import edu.nju.logistics.bl.impl.storehousemanagement.StorehouseManagementController;
import edu.nju.logistics.bl.service.storehousemanagement.StorehouseManagementBLService;
import edu.nju.logistics.po.StorageState;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.commonButton.commonButton;
import edu.nju.logistics.ui.util.TimeUtil;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.BufferVO;
import edu.nju.logistics.vo.StorehouseVO;
import edu.nju.logistics.vo.UserVO;

/**
 * 入库登记界面(980*560)
 * @author HanzZou
 *
 */
public class PanelImport extends JPanel implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserVO user = null;
	
	private StorehouseManagementBLService controller = null;
	
	private DisconnectInformer disconnectInformer = null;
	
	private JButton confirm_button, update_button = null;
	
	private Cursor cursor = null;
	
	private JTable table = null;
	
	private JScrollPane jsp = null;
	
	private ImportTable model = null;
	
	private JComboBox<String> area = null;
	
	private JLabel areaLabel, tip = null;

	public PanelImport(UserVO user, DisconnectInformer d) throws RemoteException {
		this.user = user;
		this.controller = new StorehouseManagementController();
		this.setLayout(null);
		this.setSize(980, 560);
		this.setOpaque(false);
		cursor = new Cursor(Cursor.HAND_CURSOR);
		this.initComponent();
		this.disconnectInformer = d;
	}
	
	private void initComponent() {
		//确认按钮
		confirm_button = new commonButton("入库");
		confirm_button.setLocation(830, 480);
		confirm_button.setSize(100, 40);
		confirm_button.setCursor(cursor);
		confirm_button.addMouseListener(this);
		this.add(confirm_button);
		//更新按钮
		update_button = new commonButton("刷新");
		update_button.setBounds(680, 480, 100, 40);
		update_button.setCursor(cursor);
		update_button.addMouseListener(this);
		this.add(this.update_button);
		//表格
		this.model = new ImportTable();
		this.table = new JTable(this.model);
		this.table.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14));
		this.table.setFont(new Font("楷体", Font.PLAIN, 14));
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.getTableHeader().setResizingAllowed(false);
        this.jsp = new JScrollPane(table);
        jsp.setBounds(20, 20, 620, 520);
		MyTableHandler.initTable(this.table);
		MyTableHandler.initSpane(this.jsp, this.table);
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(460);
        this.add(this.jsp);
        this.initTable();
        //区域标签
        areaLabel = new JLabel("区域:");
        areaLabel.setBounds(680, 120, 80, 30);
        this.add(this.areaLabel);
        //区域选择
        String[] areas = {"铁运区", "航运区", "汽运区"};
        area = new JComboBox<String>(areas); 
        area.setBounds(760, 120, 120, 30);
        this.add(this.area);
        //tip
        tip = new JLabel("提示:按住ctrl或command可多选。");
        tip.setForeground(Color.red);
        tip.setBounds(680, 300, 300, 30);
        this.add(this.tip);

		this.repaint();
	}
	
    //初始化表格
	public void initTable() {
		model.reset();
        ArrayList<BufferVO> goods = new ArrayList<BufferVO>();
        try {
			goods = controller.getToImport(controller.getInstitutionIDByUser(user.getId()));
		} catch (RemoteException e) {
			this.disconnectInformer.findDisconnect();
		}
        if(goods.size() != 0) {
        	for(BufferVO good : goods) {
        		Vector<String> temp = new Vector<String>();
        		temp.add(good.getId());
        		temp.add(good.getDestination());
        		model.rows.add(temp);
        	}
        }
        this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == this.confirm_button) {
			if(table.getSelectedRowCount() == 0) {
				GlobalHintInserter.insertGlobalHint("请选择入库项！");
				return;
			} else {
				int[] indexs = table.getSelectedRows();
				for(int i : indexs) {
					int[] place = null;
					try {
						place = controller.getPlace(controller.getInstitutionIDByUser(user.getId()), (String)area.getSelectedItem());
						controller.importItem(new StorehouseVO(controller.getInstitutionIDByUser(user.getId()), TimeUtil.getCurrentTime(),
								(String)area.getSelectedItem(), place[0], place[1], place[2], (String)table.getValueAt(i, 0),
								(String)table.getValueAt(i, 1), controller.getMoney((String)table.getValueAt(i, 0)), StorageState.IMPORT),user.getId());
					} catch (RemoteException e1) {
						this.disconnectInformer.findDisconnect();
						return;
					}
				}
				GlobalHintInserter.insertGlobalHint("入库成功！");
			}
			//判断是否超过警戒值
			try {
				if(controller.getPercentage(controller.getInstitutionIDByUser(user.getId()), (String)area.getSelectedItem()) >=
						controller.getAlarm(controller.getInstitutionIDByUser(user.getId()))) {
					JOptionPane.showMessageDialog(this, "报警！超出警戒值，请及时调整库存。");
				}
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (RemoteException e1) {
				this.disconnectInformer.findDisconnect();
				return;
			}
			this.initTable();
		}
		if(e.getSource() == this.update_button) {
			this.initTable();
			GlobalHintInserter.insertGlobalHint("刷新成功！");

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

class ImportTable extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Vector<String> colums = null;
	
	public Vector<Vector<String>> rows = null;
	
	public ImportTable() {
		this.colums = new Vector<String>();
		this.colums.add("快递编号");
		this.colums.add("收件地址");
		
		this.rows = new Vector<Vector<String>>();
	}
	
	@Override
	public int getRowCount() {
		if(this.rows==null){
			return 0;
		}
		return this.rows.size();
	}

	@Override
	public int getColumnCount() {
		return this.colums.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.rows.get(rowIndex).get(columnIndex);
	}
	
	@Override
	public String getColumnName(int arg0) {

		return this.colums.get(arg0);
	}
	
	public void reset() {
		this.rows.removeAllElements();
	}
	
}
