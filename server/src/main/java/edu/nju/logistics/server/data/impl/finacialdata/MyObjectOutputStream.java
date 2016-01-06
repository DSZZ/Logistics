package edu.nju.logistics.server.data.impl.finacialdata;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MyObjectOutputStream extends ObjectOutputStream {

	//无参构造函数
	public MyObjectOutputStream() throws IOException, SecurityException {
		super();
	}
	//有参构造函数
	public MyObjectOutputStream(OutputStream out) throws IOException {
		super(out);
	}
	
	/**
	 * 重写writeStreamHeader()方法
	 */
	@Override
	protected void writeStreamHeader() throws IOException {
		// TODO Auto-generated method stub
		return ;
	}

}
