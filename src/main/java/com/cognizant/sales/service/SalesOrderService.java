package com.cognizant.sales.service;


import java.util.List;

import com.cognizant.sales.domain.SalesOrderEntity;
import com.cognizant.sales.dto.SalesOrderDto;
import com.cognizant.sales.exception.ResourceNotFoundException;

public interface SalesOrderService {
	
	public String createOrder(SalesOrderDto salesOrderDto);

	public List<SalesOrderEntity> findAll();

	public SalesOrderEntity getSalesById(Long salesId) throws ResourceNotFoundException;

	public SalesOrderEntity updateSales(Long salesId, SalesOrderDto salesDetails) throws ResourceNotFoundException;

	public void deleteSales(Long salesId) throws ResourceNotFoundException;

}
