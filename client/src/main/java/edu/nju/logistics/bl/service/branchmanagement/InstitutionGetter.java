package edu.nju.logistics.bl.service.branchmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.vo.branchmanagement.BranchVO;

public interface InstitutionGetter {

	public ArrayList<BranchVO> getLocalBranchList() throws RemoteException;
}
