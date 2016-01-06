package edu.nju.logistics.po.orderdata;

import java.io.Serializable;

public enum OrderStatus  implements Serializable{
	
	received/*已收件*/,
	senderBranch,
	senderCenter,
	receiverCenter,
	receiverBranch,
	dispatching/*正派件*/
}
