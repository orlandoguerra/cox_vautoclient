package com.vauto.vautoclient.service;

import java.util.List;
import com.vauto.vautoclient.model.DatasetModel;
import com.vauto.vautoclient.model.DealerModel;

public class ThreadDealer extends Thread{ 	
	List<DealerModel> list;
	DatasetModel datamodel;
	Integer dealerId;
	
	public  ThreadDealer(DatasetModel datamodel, Integer dealerId, List<DealerModel> list) {
		this.datamodel = datamodel;
		this.list = list;
		this.dealerId = dealerId;
	}
	
    @Override
    public void run() { 
    	DealerService service = new DealerService();
    	DealerModel vehicleObject = service.getDealer(datamodel, dealerId);
    	list.add(vehicleObject);
    }

}

