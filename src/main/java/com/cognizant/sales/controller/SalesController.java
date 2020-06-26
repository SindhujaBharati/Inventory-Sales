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


import com.cognizant.sales.domain.SalesOrderEntity;
import com.cognizant.sales.dto.SalesOrderDto;
import com.cognizant.sales.exception.ResourceNotFoundException;
import com.cognizant.sales.service.SalesOrderService;

@RestController
@RequestMapping("/cognizant/sales")
@Api(value = "Sales Management", description = "Operations pertaining to Sales in Inventory Management System")
public class SalesController {
	
	private static final Logger logger = LoggerFactory.getLogger(SalesController.class);
	
	@Autowired
	private SalesOrderService salesOrderService;
	
	@ApiOperation(value = "Add a sales",response=SalesOrderEntity.class)
	@ApiResponses(value = {
		        @ApiResponse(code = 200, message = "Successfully created Sales Order"),
		        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		    })
	@PostMapping("/addSales/v1")
    public String createOrder(@RequestBody SalesOrderDto Order) {
		String salesAddResult=salesOrderService.createOrder(Order);
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
	//@Cacheable(value = "sales")
	public List<SalesOrderEntity> getAllSales() {
	    return salesOrderService.findAll();
	  }
	
	
	@ApiOperation(value = "Get a sales by Id")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully sales by Id "),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	    })	
	@GetMapping("/getSales/v2/{id}")
	//@Cacheable(value = "sales", key = "#salesId")
	public ResponseEntity<SalesOrderEntity> getSalesById(@PathVariable(value = "id") Long salesId)
	        throws ResourceNotFoundException {
		SalesOrderEntity sales = salesOrderService.getSalesById(salesId);	
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
	//@CachePut(value = "sales", key = "#salesId")
	public ResponseEntity<SalesOrderEntity> updateSales(@PathVariable(value = "id") Long salesId,
	         @RequestBody SalesOrderDto salesDetails) throws ResourceNotFoundException {
		
		final SalesOrderEntity updatedSale = salesOrderService.updateSales(salesId, salesDetails);
		return ResponseEntity.ok(updatedSale);
	    }
	
	@ApiOperation(value = "Delete a sales")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully deleted list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	    })	
	@DeleteMapping("/deleteSales/v1/{id}")
	//@CacheEvict(value = "sale", allEntries=true)
	public Map<String, Boolean> deleteSales(@PathVariable(value = "id") Long salesId)
	         throws ResourceNotFoundException {
		salesOrderService.deleteSales(salesId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
		
			
	    }
}
