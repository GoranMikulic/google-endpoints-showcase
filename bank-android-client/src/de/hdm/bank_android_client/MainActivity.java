package de.hdm.bank_android_client;

import java.io.IOException;
import java.util.List;

import com.appspot.skillful_octane_742.bankadministrationapi.Bankadministrationapi;
import com.appspot.skillful_octane_742.bankadministrationapi.model.Customer;
import com.appspot.skillful_octane_742.bankadministrationapi.model.CustomerCollection;

import de.hdm.bank_android_client.R;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.TextView1);

		new GetAllCustomersTask().execute();
	}

	private class GetAllCustomersTask extends
			AsyncTask<String, Void, CustomerCollection> {

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
			List<Customer> customers = result.getItems();
			StringBuilder sb = new StringBuilder();

			for (Customer c : customers) {
				sb.append(c.getFirstName());
				sb.append(", ");
			}
			
			textView.setText(sb.toString());

		}

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
