package com.vauto.vautoclient.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.UriBuilder;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.vauto.vautoclient.model.DatasetModel;
import com.vauto.vautoclient.model.VehicleByDatasetModel;
import com.vauto.vautoclient.model.VehicleModel;
import com.vauto.vautoclient.utils.Constants;

public class VehicleService {
	
	public VehicleByDatasetModel list(DatasetModel datamodel) {
		Client client = new Client();
		UriBuilder builderURI =  UriBuilder.fromPath(Constants.BASE_URL)
				.path(Constants.REST_PATH)
				.path(datamodel.getDatasetId())
                .path(Constants.VEHICLES_END_POINT);
		
		Gson gson = new Gson();
		URI uri = builderURI.build();
		WebResource webResource = client.resource(uri);
		WebResource.Builder builder = webResource.getRequestBuilder();
		ClientResponse response = builder.type("application/json").get(ClientResponse.class);
		String strResponse = response.getEntity(String.class);
		VehicleByDatasetModel responseObject = gson.fromJson(strResponse, VehicleByDatasetModel.class);
		return responseObject;
	}
	
	public VehicleModel getVehicle(DatasetModel datamodel, Integer vehicleId) {
		Client client = new Client();
		UriBuilder builderURI =  UriBuilder.fromPath(Constants.BASE_URL)
				.path(Constants.REST_PATH)
				.path(datamodel.getDatasetId())
                .path(Constants.VEHICLES_END_POINT)
                .path(vehicleId.toString());
		
		Gson gson = new Gson();
		URI uri = builderURI.build();
		WebResource webResource = client.resource(uri);
		WebResource.Builder builder = webResource.getRequestBuilder();
		ClientResponse response = builder.type("application/json").get(ClientResponse.class);
		if(response.getStatus() != 200) {
			throw new RuntimeException("Error, winter is coming  : HTTP error code : " + response.getStatus()+" "+response.getEntity(String.class));
		}
		String strResponse = response.getEntity(String.class);
		VehicleModel responseObject = gson.fromJson(strResponse, VehicleModel.class);
		return responseObject;

	}
	
	public List<VehicleModel> getVehiclesThread(DatasetModel datamodel, List<Integer> vehicles) {
		List<VehicleModel> list = Collections.synchronizedList(new ArrayList<VehicleModel>()); 
        List<Thread> threads = new ArrayList<Thread>();
        for (Integer vehicle : vehicles) {
        	ThreadVehicle tVhicle = new ThreadVehicle(datamodel, vehicle, list);
        	tVhicle.start();
        	threads.add(tVhicle);
		}
        for(Thread t: threads) {
        	try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        return list;

	}
}
