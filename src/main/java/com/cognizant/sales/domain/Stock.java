package com.cognizant.sales.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;

@Component
@Entity
@Table(name = "Stock")
@ApiModel(description = "All details about the Stock. ")
public class Stock {
	public Stock() {
		
	}

	public Stock(long stockId, String stockName, String stockType,
			int stockCount) {
		super();
		this.stockId = stockId;
		this.stockName = stockName;
		this.stockType = stockType;
		this.stockCount = stockCount;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long stockId;
	
	@Column(name = "stockName", nullable = false)
	private String stockName;
	
	@Column(name = "stock_type", nullable = false)
	private String stockType;
	
	@Column(name = "stock_count", nullable = false)
	private int stockCount;

	public long getStockId() {
		return stockId;
	}

	public void setStockId(long stockId) {
		this.stockId = stockId;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public int getStockCount() {
		return stockCount;
	}

	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}

	@Override
	public String toString() {
		return "Stock [stockId=" + stockId + ", stockName=" + stockName
				+ ", stockType=" + stockType + ", stockCount=" + stockCount
				+ "]";
	}
}
