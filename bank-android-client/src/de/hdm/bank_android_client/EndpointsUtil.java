package de.hdm.bank_android_client;

import com.appspot.skillful_octane_742.bankadministrationapi.Bankadministrationapi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

public class EndpointsUtil {
	
	//constants for intent parameters
	public static String INTENT_EXTRA_CUSTOMER_ID = "customerId";
	public static String INTENT_EXTRA_CUSTOMER_FIRSTNAME = "firstName";
	public static String INTENT_EXTRA_CUSTOMER_LASTNAME = "lastName";
	
	/**
	 * Building service object for api calls of Bankadministrationapi
	 * 
	 * @return Bankadministrationapi
	 */
	public static Bankadministrationapi getEndpointsService() {

		Bankadministrationapi.Builder builder = new Bankadministrationapi.Builder(
				AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
		
		//this should be removed in production - only for local testing
		//builder.setRootUrl("http://localhost:8888/_ah/api");
		builder.setRootUrl("http://192.168.1.100:8888/_ah/api");
		builder.setApplicationName("bankadministrationapi");
		Bankadministrationapi service = builder.build();
		
		return service;
	}
}
