package edu.nju.logistics.bl.impl.ordermanagement.serviceProvider;

import java.rmi.RemoteException;

import edu.nju.logistics.bl.impl.operationmanagement.OperationManagementController;
import edu.nju.logistics.bl.service.operationmanagement.EnvironmentGetter;
import edu.nju.logistics.bl.service.ordermanagement.DistanceAndPriceGetter;

public class DistanceAndPriceProvider implements DistanceAndPriceGetter{

	EnvironmentGetter environmentGetter;
	public DistanceAndPriceProvider() throws RemoteException {

		environmentGetter = new OperationManagementController();

	}
	@Override
	public double getStandardPrice() throws RemoteException {
		return environmentGetter.getServicePrice("标准快递运费价格");
	}

	@Override
	public double getDistance(String senderCity, String receiverCity) throws RemoteException {
		return environmentGetter.getDistance(senderCity, receiverCity);
	}

	@Override
	public double getMinTransportationCost() throws RemoteException {
		return environmentGetter.getServicePrice("标准快递的最低运费");
	}

}
