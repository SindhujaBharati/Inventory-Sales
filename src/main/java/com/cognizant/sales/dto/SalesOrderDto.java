package com.cognizant.sales.dto;

import java.io.Serializable;

public class SalesOrderDto implements Serializable{
	
	private static final long serialVersionUID = 7385325066515149619L;
	
	private long salesId;
	
	private String salesName;
	
	private String salesType;
	
	private int salesCount;
	
	private float salesAmtperCount;

	private float salesAmtTotal;

	public long getSalesId() {
		return salesId;
	}

	public void setSalesId(long salesId) {
		this.salesId = salesId;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	public int getSalesCount() {
		return salesCount;
	}

	public void setSalesCount(int salesCount) {
		this.salesCount = salesCount;
	}

	public float getSalesAmtperCount() {
		return salesAmtperCount;
	}

	public void setSalesAmtperCount(float salesAmtperCount) {
		this.salesAmtperCount = salesAmtperCount;
	}

	public float getSalesAmtTotal() {
		return salesAmtTotal;
	}

	public void setSalesAmtTotal(float salesAmtTotal) {
		this.salesAmtTotal = salesAmtTotal;
	}

	@Override
	public String toString() {
		return "SalesOrder [salesId=" + salesId + ", salesName=" + salesName
				+ ", salesType=" + salesType + ", salesCount=" + salesCount
				+ ", salesAmtperCount=" + salesAmtperCount + ", salesAmtTotal="
				+ salesAmtTotal + "]";
	}
}
