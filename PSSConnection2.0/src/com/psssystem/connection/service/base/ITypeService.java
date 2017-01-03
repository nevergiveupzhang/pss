package com.psssystem.connection.service.base;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface ITypeService <T>  extends Remote {
	public Set<T> getAllByType(String type)throws RemoteException;
	public Set<T> getNotPassedByType(String type)throws RemoteException;
	public boolean approveByType(Set<T> orders,String type)throws RemoteException;
}
