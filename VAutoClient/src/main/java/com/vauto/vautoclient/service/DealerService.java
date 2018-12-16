package com.vauto.vautoclient.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.UriBuilder;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.vauto.vautoclient.model.DatasetModel;
import com.vauto.vautoclient.model.DealerModel;
import com.vauto.vautoclient.model.VehicleModel;
import com.vauto.vautoclient.utils.Constants;

public class DealerService {
	
	public DealerModel getDealer(DatasetModel datamodel, Integer dealerId) {
		Client client = new Client();
		UriBuilder builderURI =  UriBuilder.fromPath(Constants.BASE_URL)
				.path(Constants.REST_PATH)
				.path(datamodel.getDatasetId())
                .path(Constants.DEALERS_END_POINT)
                .path(dealerId.toString());
		
		Gson gson = new Gson();
		URI uri = builderURI.build();
		WebResource webResource = client.resource(uri);
		WebResource.Builder builder = webResource.getRequestBuilder();
		ClientResponse response = builder.type("application/json").get(ClientResponse.class);
		if(response.getStatus() != 200) {
			throw new RuntimeException("Error, winter is coming  : HTTP error code : " + response.getStatus()+" "+response.getEntity(String.class));
		}
		String strResponse = response.getEntity(String.class);
		DealerModel responseObject = gson.fromJson(strResponse, DealerModel.class);
		return responseObject;
	}
	
	public List<DealerModel> getDealersThread(DatasetModel datamodel, Set<Integer> setDealers) {
		List<DealerModel> list = Collections.synchronizedList(new ArrayList<DealerModel>()); 
        List<Thread> threads = new ArrayList<Thread>();
        for (Integer dealer : setDealers) {
        	ThreadDealer tVhicle = new ThreadDealer(datamodel, dealer, list);
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
