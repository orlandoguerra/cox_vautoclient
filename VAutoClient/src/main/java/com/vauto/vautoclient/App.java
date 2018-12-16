package com.vauto.vautoclient;

import com.vauto.vautoclient.flow.CompleteDatasetFlow;
import com.vauto.vautoclient.model.ResultModel;

/**
 * Hello world!
 *
 */
public class App {
	
    public static void main( String[] args ){
    	CompleteDatasetFlow datasetFlow = new CompleteDatasetFlow();
    	ResultModel flowResult = datasetFlow.processCompleteFlow();
    	System.out.println(flowResult);
    }
}
