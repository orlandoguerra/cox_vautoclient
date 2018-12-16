package com.vauto.vautoclient.service;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.vauto.vautoclient.model.AnswerResponseModel;
import com.vauto.vautoclient.model.DatasetModel;
import com.vauto.vautoclient.model.DealerModel;
import com.vauto.vautoclient.model.DealerResponseModel;
import com.vauto.vautoclient.model.ResultModel;
import com.vauto.vautoclient.model.VehicleModel;
import com.vauto.vautoclient.model.VehicleResponseModel;
import com.vauto.vautoclient.utils.Constants;

public class DatasetService {
	
	Gson gson = new Gson();
	
	public DatasetModel getDatasetId() {
		Client client = new Client();
		UriBuilder builderURI =  UriBuilder.fromPath(Constants.BASE_URL)
				.path(Constants.REST_PATH)
                .path(Constants.DATASET_END_POINT);
		
		URI uri = builderURI.build();
		WebResource webResource = client.resource(uri);
		WebResource.Builder builder = webResource.getRequestBuilder();
		ClientResponse response = builder.type("application/json").get(ClientResponse.class);
		if(response.getStatus() != 200) {
			throw new RuntimeException("Error, winter is coming  : HTTP error code : " + response.getStatus()+" "+response.getEntity(String.class));
		}
		String strResponse = response.getEntity(String.class);
		DatasetModel responseObject = gson.fromJson(strResponse, DatasetModel.class);
		System.out.println(responseObject);
		return responseObject;
	}
	
	public ResultModel postAnswer(DatasetModel datamodel, AnswerResponseModel answerModel) {
		Client client = new Client();
		UriBuilder builderURI =  UriBuilder.fromPath(Constants.BASE_URL)
				.path(Constants.REST_PATH)
				.path(datamodel.getDatasetId())
                .path(Constants.ANSWER_END_POINT);
		
		URI uri = builderURI.build();
		WebResource webResource = client.resource(uri);
		WebResource.Builder builder = webResource.getRequestBuilder();
		String postInfo = gson.toJson(answerModel);
		System.out.println(postInfo);
		ClientResponse response = builder.type("application/json").accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, postInfo);
		if(response.getStatus() != 200) {
			throw new RuntimeException("Error, winter is coming  : HTTP error code : " + response.getStatus()+" "+response.getEntity(String.class));
		}
		String strResponse = response.getEntity(String.class);
		ResultModel responseObject = gson.fromJson(strResponse, ResultModel.class);
		return responseObject;
	}
	
	public AnswerResponseModel getResponseModel(List<DealerModel> dealers, List<VehicleModel> vehicles) {
		AnswerResponseModel answerModel = new AnswerResponseModel();
		answerModel.setDealers(new ArrayList<DealerResponseModel>());
		for (DealerModel dealerModel : dealers) {
			DealerResponseModel responseModel = new DealerResponseModel();
			answerModel.getDealers().add(responseModel);
			responseModel.setDealerId(dealerModel.getDealerId());
			responseModel.setName(dealerModel.getName());
			List<VehicleResponseModel> vehiclesResponse = new ArrayList<VehicleResponseModel>();
			for (VehicleModel vehicleModel : vehicles) {
				if(dealerModel.getDealerId().intValue() == vehicleModel.getDealerId().intValue()) {
					VehicleResponseModel vehicleRespon = new VehicleResponseModel();
					vehicleRespon.setMake(vehicleModel.getMake());
					vehicleRespon.setVehicleId(vehicleModel.getVehicleId());
					vehicleRespon.setModel(vehicleModel.getModel());
					vehicleRespon.setYear(vehicleModel.getYear());
					vehiclesResponse.add(vehicleRespon);
				}
			}
			responseModel.setVehicles(vehiclesResponse);
		}
		return answerModel;
	}

}
