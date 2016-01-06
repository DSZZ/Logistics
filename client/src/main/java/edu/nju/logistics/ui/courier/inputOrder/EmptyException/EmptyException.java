package edu.nju.logistics.ui.courier.inputOrder.EmptyException;

public class EmptyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1344079142274648007L;

	private ExceptionKind kind;
	public ExceptionKind getKind() {
		return kind;
	}
	public EmptyException(ExceptionKind kind) {
		this.kind = kind;
	}
	
}
