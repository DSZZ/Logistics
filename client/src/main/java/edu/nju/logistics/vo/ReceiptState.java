package edu.nju.logistics.vo;

public enum ReceiptState {
	LOST{
		@Override
		public String toString() {
			return "丢失";
		}
	}, BROKEN{
		@Override
		public String toString() {
			return "损坏";
		}
	}, COMPLETE{
		@Override
		public String toString() {
			return "完整";
		}
	}
}
