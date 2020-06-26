package com.cognizant.sales.dto;

import java.io.Serializable;

public class StockRequestDto implements Serializable{

	private static final long serialVersionUID = 8318360105129613318L;

	private long stockId;

	private String stockName;

	private String stockType;

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
		return "StockRequest [stockId=" + stockId + ", stockName=" + stockName + ", stockType=" + stockType
				+ ", stockCount=" + stockCount + "]";
	}
}
