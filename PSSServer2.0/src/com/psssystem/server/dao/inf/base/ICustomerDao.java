package com.psssystem.server.dao.inf.base;

import java.util.List;
import java.util.Set;

import com.psssystem.connection.vo.CustomerVO;

public interface ICustomerDao extends IBaseDao<CustomerVO>{
	public boolean deleteCustomerByID(int id);
	public List<CustomerVO> searchCustomer(String info);
	public boolean addCustomers(Set<CustomerVO> voList);
	public boolean deleteCustomers(List<CustomerVO> voList);
	public boolean updateCustomers(List<CustomerVO> voList);
	public boolean increasePayableById(int customerID, int sum);
	public boolean reducePayableById(int customerID, int sum);
	public boolean increaseReceivableById(int customerID, int sumAfterDiscount);
	public boolean reduceReceivableById(int customerID, int sumAfterDiscount);
}
