package com.vauto.vautoclient.flow;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vauto.vautoclient.model.AnswerResponseModel;
import com.vauto.vautoclient.model.DatasetModel;
import com.vauto.vautoclient.model.DealerModel;
import com.vauto.vautoclient.model.ResultModel;
import com.vauto.vautoclient.model.VehicleByDatasetModel;
import com.vauto.vautoclient.model.VehicleModel;
import com.vauto.vautoclient.service.DatasetService;
import com.vauto.vautoclient.service.DealerService;
import com.vauto.vautoclient.service.VehicleService;

public class CompleteDatasetFlow {
	
	public ResultModel processCompleteFlow() {
		DatasetService dataSetService = new DatasetService();
        DatasetModel dataSetId = dataSetService.getDatasetId();
        VehicleService vehicleService = new VehicleService();
        VehicleByDatasetModel vehicleList =  vehicleService.list(dataSetId);
        List<VehicleModel> vehicles = vehicleService.getVehiclesThread(dataSetId, vehicleList.getVehicleIds());
        
        Set<Integer> setDealers = new HashSet<Integer>();
        for (VehicleModel vehicleModel : vehicles) {
        	setDealers.add(vehicleModel.getDealerId());
		}
        DealerService dealerService = new DealerService();
        List<DealerModel> dealers = dealerService.getDealersThread(dataSetId, setDealers);
        AnswerResponseModel responseAnswer = dataSetService.getResponseModel(dealers, vehicles);
        ResultModel flowResult = dataSetService.postAnswer(dataSetId, responseAnswer);
        return flowResult;
	}

}
