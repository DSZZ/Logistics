package edu.nju.logistics.vo;

public enum Transportation {

	AIR{
		@Override
		public String toString() {
			return "飞机";			
		}
	},
	TRAIN{
		@Override
		public String toString() {
			return "火车";
		}
	},
	CAR{
		@Override
		public String toString() {
			return "汽车";
		}
	}
}
