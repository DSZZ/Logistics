package edu.nju.logistics.po.orderdata;

import java.io.Serializable;

public enum OrderKind  implements Serializable{

	branch_stay,
	branch_2receive,
	branch_2dispatch,
	courier_2dispatch,
	center_2receive,
	center_stay,
	center_2store,
	center_storage
}
