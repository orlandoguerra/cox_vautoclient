package com.vauto.vautoclient.model;

import java.util.List;

public class DealerResponseModel {
	
	private Integer dealerId;
	private String name;
	private List<VehicleResponseModel> vehicles;
	
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
	public List<VehicleResponseModel> getVehicles() {
		return vehicles;
	}
	public void setVehicles(List<VehicleResponseModel> vehicles) {
		this.vehicles = vehicles;
	}

	
}
