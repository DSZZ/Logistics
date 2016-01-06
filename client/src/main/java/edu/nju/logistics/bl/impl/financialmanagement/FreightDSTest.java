package edu.nju.logistics.bl.impl.financialmanagement;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.nju.logistics.po.financial.FreightRecordPO;

public class FreightDSTest {
    
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	public void test() {
		  
//		  assertEquals("001",temp1.getInstitutionID());
//		  assertEquals("2015/06/20", temp2.getDate());
	}
	
	public static void main(String args [] ){
		FreightRecordBL bl = new FreightRecordBL();
		FreightRecordPO temp;
	    bl.addFreightRecordPO(new  FreightRecordPO("005",100, "2015/03/20"));
    	bl.addFreightRecordPO(new  FreightRecordPO("006",200, "2015/06/20"));
    	ArrayList<FreightRecordPO> list  =bl.getAllFreightRecordPO();
        for(int i=0; i< list.size();i++){
        	temp=list.get(i);
        	if(temp!=null){
        		System.out.println(temp.getInstitutionID());
        	}
        }
	    
	}

}
