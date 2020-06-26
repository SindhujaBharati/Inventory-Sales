package com.cognizant.sales.controller;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.cognizant.sales.dto.SalesOrderDto;
import com.cognizant.sales.service.SalesOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(SalesController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SalesControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	WebApplicationContext webApplicationContext;
	@MockBean
	private SalesOrderService salesOrderService;
	private ObjectMapper objectMapper = new ObjectMapper();
	private String salesOrderRequest;
	private String salesResult;
	
	@Before
	public void setUp() throws JsonProcessingException {
		SalesOrderDto salesOrderDto = new SalesOrderDto();
		salesOrderDto.setSalesId(1);
		salesOrderDto.setSalesName("Wood");
		salesOrderDto.setSalesType("Raw");
		salesOrderDto.setSalesCount(2);
		salesOrderDto.setSalesAmtperCount(3000);
		salesOrderDto.setSalesAmtTotal(6000);
		
		salesOrderRequest = objectMapper.writeValueAsString(salesOrderDto);
		Mockito.when(salesOrderService.createOrder(Mockito.any())).thenReturn("Sales Order placed Successfully");
		

	}
	@Test
	public void createOrderTest() throws Exception {

		this.mockMvc.perform(post("/cognizant/sales/addSales/v1").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(salesOrderRequest)).andExpect(status().isOk());
	}
	@Test
	public void fetchResultsTest() throws Exception {
		this.mockMvc.perform(get("/cognizant/sales/getSales/v1")).andExpect(status().isOk());
	}

}
