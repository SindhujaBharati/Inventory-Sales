package com.cognizant.sales.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cognizant.sales.domain.SalesOrder;
import com.cognizant.sales.domain.Stock;
import com.cognizant.sales.exception.ResourceNotFoundException;
import com.cognizant.sales.repository.SalesRepository;

@RestController
@RequestMapping("/cognizant/sales")
@Api(value = "Sales Management", description = "Operations pertaining to Sales in Inventory Management System")
public class SalesController {
	
	Logger logger = LoggerFactory.getLogger(SalesController.class);
	
	@Autowired
	private SalesRepository salesRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
		
	private static final String HOST = "localhost";
	private static final String SCHEME = "http";
	private int stockPort=8084;
	private Stock stockResult;
	private String stockName;
	private int stockCount;
	private String salesAddResult;
	private String stockType;
	
	@ApiOperation(value = "Add a sales",response=SalesOrder.class)
	@ApiResponses(value = {
		        @ApiResponse(code = 200, message = "Successfully created Sales Order"),
		        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		    })
	@PostMapping("/addSales/v1")
    public String createOrder(@RequestBody SalesOrder Order) {
		
		logger.debug("SalesController::createOrder::entry()");
		
		stockName=Order.getSalesName();
		stockCount=Order.getSalesCount();
		stockType=Order.getSalesType();
				
		String url = SCHEME + "://" + HOST + ":" + stockPort + "/" + "cognizant/stock/getstock/v3/" + stockName;      
        
        stockResult=restTemplate.getForObject(url, Stock.class);
        
        if(stockResult!=null && stockResult.getStockName().equals(stockName) && stockResult.getStockType().equals(stockType)){
			int previousCount=stockResult.getStockCount();
			int currentCount=Order.getSalesCount();
			int totalcount=previousCount-currentCount;
			if(totalcount>0){
			
				String updateUrl = SCHEME + "://" + HOST + ":" + stockPort + "/" + "cognizant/stock/updateStock/v2/" + stockResult.getStockId();
			     
			    Map<String, Long> params = new HashMap<String, Long>();
			    params.put("id", stockResult.getStockId());
			    
			    stockResult.setStockName(stockName);
			    stockResult.setStockType(stockType);
				stockResult.setStockCount(totalcount);

			    restTemplate.put( updateUrl, stockResult, params);
			    
				SalesOrder salesOrder=new SalesOrder();
				salesOrder.setSalesName(Order.getSalesName());
				salesOrder.setSalesType(Order.getSalesType());
				salesOrder.setSalesCount(Order.getSalesCount());
				salesOrder.setSalesAmtperCount(Order.getSalesAmtperCount());
				salesOrder.setSalesAmtTotal(Order.getSalesAmtperCount()*Order.getSalesCount());
				salesRepository.save(salesOrder);
			}
			salesAddResult="Sales Order placed Successfully";
		}
		else{
			salesAddResult="Out of Stock";
		}
		return salesAddResult;
    }
	
	
	@ApiOperation(value = "View a list of Sales done", response = List.class)
	@ApiResponses(value = {
		        @ApiResponse(code = 200, message = "Successfully retrieved list"),
		        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		    })		 
	@GetMapping("/getSales/v1")
	@Cacheable(value = "sales")
	public List<SalesOrder> getAllSales() {
		
		logger.debug("SalesController::getAllSales::entry()");
		
		logger.debug("SalesController::getAllSales::exit()");
		 
	    return salesRepository.findAll();
	  }
	
	
	@ApiOperation(value = "Get a sales by Id")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully sales by Id "),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	    })	
	@GetMapping("/getSales/v2/{id}")
	@Cacheable(value = "sales", key = "#salesId")
	public ResponseEntity<SalesOrder> getSalesById(@PathVariable(value = "id") Long salesId)
	        throws ResourceNotFoundException {
		
		logger.debug("SalesController::getSalesById::entry()");
		
			SalesOrder sales = salesRepository.findById(salesId)
	          .orElseThrow(() -> new ResourceNotFoundException("Sales not found for this id :: " + salesId));
			
		logger.debug("SalesController::getSalesById::exit()");
	        return ResponseEntity.ok().body(sales);
	    }
	
	@ApiOperation(value = "Update a sales")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully updated list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	    })	
	@PutMapping("/updateSales/v1/{id}")
	@CachePut(value = "sales", key = "#salesId")
	public ResponseEntity<SalesOrder> updateSales(@PathVariable(value = "id") Long salesId,
	         @RequestBody SalesOrder salesDetails) throws ResourceNotFoundException {
		
		    logger.debug("SalesController::updateSales::entry()");
		
			SalesOrder sales = salesRepository.findById(salesId)
	        .orElseThrow(() -> new ResourceNotFoundException("Sales not found for this id :: " + salesId));

			sales.setSalesName(salesDetails.getSalesName());
			sales.setSalesType(salesDetails.getSalesType());
			sales.setSalesCount(salesDetails.getSalesCount());
			sales.setSalesAmtperCount(salesDetails.getSalesAmtperCount());
			sales.setSalesAmtTotal(salesDetails.getSalesAmtperCount()*salesDetails.getSalesCount());
			final SalesOrder updatedSales = salesRepository.save(sales);
			
			logger.debug("SalesController::updateSales::exit()");
			
	        return ResponseEntity.ok(updatedSales);
	    }
	
	@ApiOperation(value = "Delete a sales")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully deleted list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	    })	
	@DeleteMapping("/deleteSales/v1/{id}")
	@CacheEvict(value = "sale", allEntries=true)
	public Map<String, Boolean> deleteSales(@PathVariable(value = "id") Long salesId)
	         throws ResourceNotFoundException {
		
			logger.debug("SalesController::deleteSales::entry()");
		
			SalesOrder sales = salesRepository.findById(salesId)
	       .orElseThrow(() -> new ResourceNotFoundException("Sales not found for this id :: " + salesId));

			salesRepository.delete(sales);
	        Map<String, Boolean> response = new HashMap<>();
	        response.put("deleted", Boolean.TRUE);
	        
	        logger.debug("SalesController::deleteSales::exit()");
	        
	        return response;
	    }
}
