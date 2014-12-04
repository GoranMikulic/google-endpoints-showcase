package de.hdm.bank_android_client;

import java.io.IOException;

import com.appspot.skillful_octane_742.bankadministrationapi.Bankadministrationapi;
import com.appspot.skillful_octane_742.bankadministrationapi.model.CustomerCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.json.gson.GsonFactory;

import android.os.AsyncTask;
import android.util.Log;

public class BankEndpointTask extends AsyncTask<String, Void, CustomerCollection> {

	@Override
	protected CustomerCollection doInBackground(String... params) {
		
		// Getting Service Object
		Bankadministrationapi service = EndpointsUtil.getEndpointsService();
		
		try {
			// Calling Service
			CustomerCollection customers = service.getAllCustomers()
					.execute();
			
			return customers;
		
		} catch (IOException e) {
			Log.e(Constants.LOG, e.getMessage());
			return null;
		}
	}

	@Override
	protected void onPostExecute(CustomerCollection result) {
		super.onPostExecute(result);
	}

}
