package edu.nju.logistics.ui.courier.inputOrder;

import java.rmi.RemoteException;

import edu.nju.logistics.bl.service.ordermanagement.DistanceAndPriceGetter;
import edu.nju.logistics.ui.courier.inputOrder.subPanel.PersonalInfoInputPanel;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;

/**
 * @author ThinkPad
 * 3个功能：包括1.从总经理那边获取最小运费，运费参数等常量，来计算运费。2.通过两个城市名来从总经理那里获取距离。3.获取城市列表
 */
public class DayAndTimeCalculator implements WeightObserver,AddressChangeObserver{

	private PersonalInfoInputPanel sender;
	private PersonalInfoInputPanel receiver;
	private DistanceAndPriceGetter environmentGetter;
	private DayAndTimeShower dayAndTimeShower;
	private DisconnectInformer disconnectInformer;
	
	int weight = 0;
	double distance = 0;
	
	public DayAndTimeCalculator(PersonalInfoInputPanel sender,
			PersonalInfoInputPanel receiver,DayAndTimeShower dayAndTimeShower,
			DistanceAndPriceGetter environmentGetter, DisconnectInformer disconnectInformer) {
		this.sender = sender;
		this.receiver = receiver;
		this.dayAndTimeShower = dayAndTimeShower;
		this.environmentGetter = environmentGetter;
		this.disconnectInformer = disconnectInformer;
		sender.addObserver(this);
		receiver.addObserver(this);
		
		
	}

	@Override
	public void createFee(int weight) {
		this.weight = weight;
		double priceConstant = 23;
		int minfee = 10;
		try {
			priceConstant = environmentGetter.getStandardPrice();
			minfee = (int)environmentGetter.getMinTransportationCost();
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
		}
		double fee = distance*priceConstant/1000;
		if(fee > minfee){
			dayAndTimeShower.showFee(weight*fee);
		}else{
			dayAndTimeShower.showFee(weight*(double)minfee);
		}
	}

	public void createTime() {
		dayAndTimeShower.showTime(1);
	}

	@Override
	public void addressChangedNotify() {
		String senderCity = sender.getCity();
		String receiverCity = receiver.getCity();
		if(senderCity == null || receiverCity == null)
			return;
		try {
			distance = environmentGetter.getDistance(senderCity, receiverCity);
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
		}
		
		createFee(weight);
		createTime();
		
	}
	
}
