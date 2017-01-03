package com.psssystem.connection.service.base;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

public interface IBaseService<T> extends Remote {
	public Set<T> getAll() throws RemoteException;
	public Set<T> getNotPassed() throws RemoteException;
	public boolean approve(Set<T> orders)throws RemoteException;
}
