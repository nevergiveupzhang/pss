package com.psssystem.client.controllerimpl.salescontrollerimpl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import com.psssystem.client.controller.salescontroller.ICustomerController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.salesservice.ICustomerService;
import com.psssystem.connection.vo.CustomerVO;

public class CustomerControllerImpl implements ICustomerController {
	private ICustomerService customerService;
	public CustomerControllerImpl(){
		ConnectToServer.connect();
		this.customerService=ConnectToServer.customerService;
	}

	@Override
	public boolean deleteCustomer(CustomerVO vo) {
		try {
			return customerService.deleteCustomer(vo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void updateCustomer(CustomerVO vo) {
		try {
			customerService.updateCustomer(vo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<CustomerVO> searchCustomer(String info) {
		List<CustomerVO> customerList=null;
		try {
			customerList=customerService.searchCustomer(info);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return customerList;
	}

	@Override
	public boolean addCustomers(Set<CustomerVO> customerList) {
		try {
			return customerService.addCustomers(customerList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
		return false;
	}

	@Override
	public List<CustomerVO> getAllCustomers() {
		List<CustomerVO> customerList = null;
		try {
			customerList= customerService.getAllCustomers();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return customerList;
	}

	@Override
	public boolean deleteCustomers(List<CustomerVO> deletedList) {
		try {
			return customerService.deleteCustomers(deletedList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateCustomers(List<CustomerVO> updateList) {
		try {
			return customerService.updateCustomers(updateList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void initCustomers(Set<CustomerVO> customerList) {
		try {
			customerService.initCustomers(customerList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
