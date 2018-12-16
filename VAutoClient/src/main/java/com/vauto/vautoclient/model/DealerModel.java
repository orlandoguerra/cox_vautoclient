package com.vauto.vautoclient.model;

public class DealerModel {
	
	private Integer dealerId;
	private String name;
	
	@Override
	public String toString() {
		return "DealerModel [dealerId=" + dealerId + ", name=" + name + "]";
	}
	
	public Integer getDealerId() {
		return dealerId;
	}
	public void setDealerId(Integer dealerId) {
		this.dealerId = dealerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
