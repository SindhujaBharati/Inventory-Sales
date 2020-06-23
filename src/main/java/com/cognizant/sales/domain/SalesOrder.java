package com.cognizant.sales.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "SalesOrder")
@ApiModel(description = "All details about the Sales. ")
public class SalesOrder {
	
	public SalesOrder() {
		
}

	public SalesOrder(long salesId, String salesName, String salesType,
			int salesCount, float salesAmtperCount, float salesAmtTotal) {
		super();
		this.salesId = salesId;
		this.salesName = salesName;
		this.salesType = salesType;
		this.salesCount = salesCount;
		this.salesAmtperCount = salesAmtperCount;
		this.salesAmtTotal = salesAmtTotal;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long salesId;
	
	@Column(name = "sales_name", nullable = false)
	private String salesName;
	
	@Column(name = "sales_type", nullable = false)
	private String salesType;
	
	@Column(name = "sales_count", nullable = false)
	private int salesCount;
	
	@Column(name = "sales_amtPerCount", nullable = false)
	private float salesAmtperCount;

	@Column(name = "sales_amtTotal", nullable = false)
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
