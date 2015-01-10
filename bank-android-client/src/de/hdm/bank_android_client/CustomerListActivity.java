package de.hdm.bank_android_client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.appspot.skillful_octane_742.bankadministrationapi.Bankadministrationapi;
import com.appspot.skillful_octane_742.bankadministrationapi.model.Customer;
import com.appspot.skillful_octane_742.bankadministrationapi.model.CustomerCollection;

import de.hdm.bank_android_client.adapter.CustomerAdapter;

public class CustomerListActivity extends Activity {

	private ListView customerListView;
	private CustomerAdapter arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_list);

		customerListView = (ListView) findViewById(R.id.listviewCustomers);

		arrayAdapter = new CustomerAdapter(this, R.layout.list_item_cuostomer,
				new ArrayList<Customer>());

		customerListView.setAdapter(arrayAdapter);
		customerListView.setOnItemClickListener(new OnCustomerItemClickListener());

		new GetAllCustomersTask().execute();
	}
	
	private class OnCustomerItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long arg3) {
			
			Customer clickedCustomer = (Customer) adapterView.getAdapter().getItem(position);
			
			Intent intent = new Intent(CustomerListActivity.this, AccountListActivity.class);
			intent.putExtra(EndpointsUtil.INTENT_EXTRA_CUSTOMER_ID, clickedCustomer.getId());
			intent.putExtra(EndpointsUtil.INTENT_EXTRA_CUSTOMER_FIRSTNAME, clickedCustomer.getFirstName());
			intent.putExtra(EndpointsUtil.INTENT_EXTRA_CUSTOMER_LASTNAME, clickedCustomer.getLastName());
			
			startActivity(intent);
		}
		
	}

	/**
	 *  Calling remote service in async task
	 *
	 */
	private class GetAllCustomersTask extends
			AsyncTask<String, Void, CustomerCollection> {

		@Override
		protected CustomerCollection doInBackground(String... params) {

			// Getting service object
			Bankadministrationapi service = EndpointsUtil.getEndpointsService();

			try {
				// Calling remote service
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
			arrayAdapter.clear();
			arrayAdapter.addAll(customers);
			//refreshing listview
			arrayAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.customer_list, menu);
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
