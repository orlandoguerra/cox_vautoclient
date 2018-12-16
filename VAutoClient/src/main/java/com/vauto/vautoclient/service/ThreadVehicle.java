package com.vauto.vautoclient.service;

import java.util.List;

import com.vauto.vautoclient.model.DatasetModel;
import com.vauto.vautoclient.model.VehicleModel;

public class ThreadVehicle extends Thread{ 	
	List<VehicleModel> list;
	DatasetModel datamodel;
	Integer vehicleId;
	
	public  ThreadVehicle(DatasetModel datamodel, Integer vehicleId, List<VehicleModel> list) {
		this.datamodel = datamodel;
		this.list = list;
		this.vehicleId = vehicleId;
	}
	
    @Override
    public void run() { 
    	VehicleService vehicleService = new VehicleService();
    	VehicleModel vehicleObject = vehicleService.getVehicle(datamodel, vehicleId);
    	list.add(vehicleObject);
    }

}
