package de.hdm.bank_android_client;

import com.appspot.skillful_octane_742.bankadministrationapi.Bankadministrationapi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

public class EndpointsUtil {

	/**
	 * Building service object for api calls of Bankadministrationapi
	 * 
	 * @return Bankadministrationapi
	 */
	public static Bankadministrationapi getEndpointsService() {

		Bankadministrationapi.Builder builder = new Bankadministrationapi.Builder(
				AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);

		builder.setApplicationName("bankadministrationapi");
		Bankadministrationapi service = builder.build();
		
		return service;
	}
}
