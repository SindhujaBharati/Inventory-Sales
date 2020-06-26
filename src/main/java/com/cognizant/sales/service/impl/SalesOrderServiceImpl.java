package com.cognizant.sales.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cognizant.sales.domain.SalesOrderEntity;
import com.cognizant.sales.domain.StockEntity;
import com.cognizant.sales.dto.SalesOrderDto;
import com.cognizant.sales.exception.ResourceNotFoundException;
import com.cognizant.sales.repository.SalesRepository;
import com.cognizant.sales.service.SalesOrderService;

@Service
public class SalesOrderServiceImpl implements SalesOrderService{
	
	private static final Logger logger = LoggerFactory.getLogger(SalesOrderServiceImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private SalesRepository salesRepository;
	
	private static final String HOST = "localhost";
	private static final String SCHEME = "http";
	private int stockPort=8088;
	private StockEntity stockResult;
	private String stockName;
	private int stockCount;
	private String stockType;
	private String salesAddResult;
	

	@Override
	public String createOrder(SalesOrderDto salesOrderDto) {
		
		logger.debug("SalesOrderServiceImpl::createOrder::entry()");
			
		stockName=salesOrderDto.getSalesName();
		stockCount=salesOrderDto.getSalesCount();
		stockType=salesOrderDto.getSalesType();
				
		String url = SCHEME + "://" + HOST + ":" + stockPort + "/" + "cognizant/stock/getstock/v3/" + stockName;      
        
        stockResult=restTemplate.getForObject(url, StockEntity.class);
        
        if(stockResult!=null && stockResult.getStockName().equals(stockName) && stockResult.getStockType().equals(stockType)){
			int previousCount=stockResult.getStockCount();
			int currentCount=salesOrderDto.getSalesCount();
			int totalcount=previousCount-currentCount;
			if(totalcount>0){
			
				String updateUrl = SCHEME + "://" + HOST + ":" + stockPort + "/" + "cognizant/stock/updateStock/v2/" + stockResult.getStockId();
			     
			    Map<String, Long> params = new HashMap<String, Long>();
			    params.put("id", stockResult.getStockId());
			    
			    stockResult.setStockName(stockName);
			    stockResult.setStockType(stockType);
				stockResult.setStockCount(totalcount);

			    restTemplate.put( updateUrl, stockResult, params);
			    
				SalesOrderEntity salesOrder=new SalesOrderEntity();
				salesOrder.setSalesName(salesOrderDto.getSalesName());
				salesOrder.setSalesType(salesOrderDto.getSalesType());
				salesOrder.setSalesCount(salesOrderDto.getSalesCount());
				salesOrder.setSalesAmtperCount(salesOrderDto.getSalesAmtperCount());
				salesOrder.setSalesAmtTotal(salesOrderDto.getSalesAmtperCount()*salesOrderDto.getSalesCount());
				salesRepository.save(salesOrder);
			}
			salesAddResult="Sales Order placed Successfully";
		}
		else{
			salesAddResult="Out of Stock";
		}
        logger.debug("SalesOrderServiceImpl::createOrder::exit()");
		return salesAddResult;
	}


	@Override
	public List<SalesOrderEntity> findAll() {
		logger.debug("SalesOrderServiceImpl::findAll::entry()");
		
		logger.debug("SalesOrderServiceImpl::findAll::exit()");
		return salesRepository.findAll();
	}


	@Override
	public SalesOrderEntity getSalesById (Long salesId) throws ResourceNotFoundException{
		logger.debug("SalesOrderServiceImpl::getSalesById::entry()");
		
		logger.debug("SalesOrderServiceImpl::getSalesById::exit()");
		
		return salesRepository.findById(salesId)
				.orElseThrow(() -> new ResourceNotFoundException("Sales not found for this id :: " + salesId));
		
	

	}


	@Override
	public SalesOrderEntity updateSales(Long salesId, SalesOrderDto salesDetails) throws ResourceNotFoundException {
		logger.debug("SalesOrderServiceImpl::updateSales::entry()");
		
		SalesOrderEntity sales = salesRepository.findById(salesId)
				.orElseThrow(() -> new ResourceNotFoundException("Sales not found for this id :: " + salesId));

		sales.setSalesName(salesDetails.getSalesName());
		sales.setSalesType(salesDetails.getSalesType());
		sales.setSalesCount(salesDetails.getSalesCount());
		sales.setSalesAmtperCount(salesDetails.getSalesAmtperCount());
		sales.setSalesAmtTotal(salesDetails.getSalesAmtperCount()*salesDetails.getSalesCount());
		final SalesOrderEntity updatedSales = salesRepository.save(sales);
		
		logger.debug("SalesOrderServiceImpl::updateSales::exit()");
		return salesRepository.save(updatedSales);
	}


	@Override
	public void deleteSales(Long salesId) throws ResourceNotFoundException {
	
		logger.debug("SalesOrderServiceImpl::deleteSales::entry()");
		
		SalesOrderEntity sales = salesRepository.findById(salesId)
       .orElseThrow(() -> new ResourceNotFoundException("Sales not found for this id :: " + salesId));

		salesRepository.delete(sales);

       logger.debug("SalesOrderServiceImpl::deleteSales::exit()");
        
  
		
	}

}
