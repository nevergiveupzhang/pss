/**
 * 
 */
package com.psssystem.connection.service.salesservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import com.psssystem.connection.vo.CustomerVO;

/**
 * @author ZTAO
 *
 */
public interface ICustomerService extends Remote {
	public boolean addCustomers(Set<CustomerVO> voList) throws RemoteException;
	public boolean initCustomers(Set<CustomerVO> voList) throws RemoteException;
	public boolean deleteCustomer(CustomerVO vo) throws RemoteException;
	public boolean deleteCustomers(List<CustomerVO> voList) throws RemoteException;
	public boolean updateCustomers(List<CustomerVO> voList) throws RemoteException;
	public boolean updateCustomer(CustomerVO vo) throws RemoteException;
	public List<CustomerVO> searchCustomer(String info) throws RemoteException;
	public List<CustomerVO> getAllCustomers() throws RemoteException;
}
