package com.psssystem.client.controller.salescontroller;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import com.psssystem.connection.vo.CustomerVO;

public interface ICustomerController {
	public boolean addCustomers(Set<CustomerVO> customerList) ;
	public void initCustomers(Set<CustomerVO> customerList) ;
	public boolean deleteCustomer(CustomerVO vo);
	public void updateCustomer(CustomerVO vo);
	public List<CustomerVO> searchCustomer(String info);
	public List<CustomerVO> getAllCustomers();
	public boolean deleteCustomers(List<CustomerVO> deletedList);
	public boolean updateCustomers(List<CustomerVO> updateList);
}
