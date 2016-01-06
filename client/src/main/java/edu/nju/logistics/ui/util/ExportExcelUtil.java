package edu.nju.logistics.ui.util;


import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class ExportExcelUtil {
	
	public static String  getPath(){
		Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screensize.getWidth();
		int height = (int)screensize.getHeight();
		
		FileDialog fileDialog = new FileDialog(new JFrame(), null, FileDialog.SAVE);
		fileDialog.setLocation(width-fileDialog.getWidth()/2, height-fileDialog.getHeight()/2 );
		fileDialog .setVisible(true);
		String path = fileDialog.getDirectory();
		String fileName = fileDialog.getFile()+".xls";
		System.out.println(path+fileName);
		return path+fileName;
	}
    
	public static void exportExcel(JTable table, String filePath){
		
		
		try {
			TableModel model = table.getModel();
			File file = new File(filePath);
			OutputStreamWriter bWriter = new OutputStreamWriter(new FileOutputStream(file),"GB2312");
		    for(int i=0; i < model.getColumnCount(); i++) {
		    	bWriter.write(model.getColumnName(i));
		        bWriter.write('\t');
		    }
		    bWriter.write('\n');
		    for(int i=0; i< model.getRowCount(); i++) {
		    	for(int j=0; j < model.getColumnCount(); j++) {
		    		bWriter.write(model.getValueAt(i,j).toString());
		            bWriter.write('\t');
		        }
		        bWriter.write('\n');
		    }
		    bWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void exportBoth(JTable paymentTable,JTable receiptTable, String filePath){
		   
		try {
			TableModel paymentModel = paymentTable.getModel();
			TableModel receiptModel =  receiptTable.getModel();
			
			File file = new File(filePath);
			OutputStreamWriter bWriter =
					     new OutputStreamWriter(new FileOutputStream(file),"GB2312");
			
			
		    for(int i=0; i < paymentModel.getColumnCount(); i++) {
		    	bWriter.write(paymentModel.getColumnName(i));
		        bWriter.write('\t');
		    }
		    bWriter.write('\n');
		    for(int i=0; i< paymentModel.getRowCount(); i++) {
		    	for(int j=0; j < paymentModel.getColumnCount(); j++) {
		    		bWriter.write(paymentModel.getValueAt(i,j).toString());
		            bWriter.write('\t');
		        }
		        bWriter.write('\n');
		    }
		    
		    
		    for(int i=0; i < receiptModel.getColumnCount(); i++) {
		    	bWriter.write(receiptModel.getColumnName(i));
		        bWriter.write('\t');
		    }
		    bWriter.write('\n');
		    for(int i=0; i< receiptModel.getRowCount(); i++) {
		    	for(int j=0; j < receiptModel.getColumnCount(); j++) {
		    		bWriter.write(receiptModel.getValueAt(i,j).toString());
		            bWriter.write('\t');
		        }
		        bWriter.write('\n');
		    }
		    
		    bWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
