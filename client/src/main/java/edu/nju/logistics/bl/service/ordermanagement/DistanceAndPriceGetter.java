package edu.nju.logistics.bl.service.ordermanagement;

import java.rmi.RemoteException;

public interface DistanceAndPriceGetter {

	public double getStandardPrice() throws RemoteException;

	public double getDistance(String senderCity, String receiverCity) throws RemoteException;

	public double getMinTransportationCost() throws RemoteException;
}
