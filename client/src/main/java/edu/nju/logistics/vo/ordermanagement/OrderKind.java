package edu.nju.logistics.vo.ordermanagement;

import java.io.Serializable;

public enum OrderKind implements Serializable{

	economic{
		@Override
		public String toString(){
			return "经济";
		}
	},standard{
		@Override
		public String toString(){
			return "标准";
		}
	},quick{
		@Override
		public String toString(){
			return "特快";
		}
	}
}
