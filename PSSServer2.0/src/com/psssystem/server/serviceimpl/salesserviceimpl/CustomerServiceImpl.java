package com.psssystem.server.serviceimpl.salesserviceimpl;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


import java.util.Set;

import com.psssystem.connection.service.salesservice.ICustomerService;
import com.psssystem.connection.vo.AccountVO;
import com.psssystem.connection.vo.CustomerVO;
import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.server.dao.impl.base.CustomerDaoImpl;
import com.psssystem.server.dao.inf.base.ICustomerDao;

public class CustomerServiceImpl extends UnicastRemoteObject implements
		ICustomerService {
	private ICustomerDao customerDao;
	private final String initFileName="resource/customer.txt";
	public CustomerServiceImpl() throws RemoteException {
		super();
		this.customerDao = new CustomerDaoImpl();
	}

	@Override
	public boolean addCustomers(Set<CustomerVO> voList) throws RemoteException {
		return customerDao.addCustomers(voList);
	}

	@Override
	public boolean deleteCustomer(CustomerVO vo) throws RemoteException {
		return customerDao.deleteCustomerByID(vo.getId());
	}

	@Override
	public List<CustomerVO> searchCustomer(String info) throws RemoteException {
		return customerDao.searchCustomer(info);
	}


	@Override
	public boolean updateCustomer(CustomerVO vo) throws RemoteException {
		return customerDao.update(vo);
	}

	@Override
	public List<CustomerVO> getAllCustomers() throws RemoteException {
		return customerDao.getAll();
	}

	@Override
	public boolean deleteCustomers(List<CustomerVO> voList) throws RemoteException {
		return customerDao.deleteCustomers(voList);
	}

	@Override
	public boolean updateCustomers(List<CustomerVO> voList) throws RemoteException {
		return customerDao.updateCustomers(voList);
	}

	@Override
	public boolean initCustomers(Set<CustomerVO> customerList)
			throws RemoteException {
		if(!(getAllCustomers().size()==0)){
			return false;
		}
		addCustomers(customerList);
		FileWriter writer = null;
		try {
			writer = new FileWriter(initFileName, true);
			for (CustomerVO vo : getAllCustomers()) {
				writer.write(vo.getId() + "|" + vo.getType() + "|"
						+ vo.getLevel() + "|" + vo.getName() + "|"
						+ vo.getAddr() + "|" + vo.getPhoneNumber() + "|"
						+ vo.getPostcode() + "|" + vo.getEmail() + "|"
						+ vo.getAmuntReceivable() + vo.getReceivable() + "|"
						+ vo.getPayable() + "|" + vo.getDefaultSalesman() + "|");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
