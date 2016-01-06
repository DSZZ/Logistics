package edu.nju.logistics.vo.branchmanagement;

public class BranchVO {

	private String id;
	
	private String name;
	
	public BranchVO(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}
