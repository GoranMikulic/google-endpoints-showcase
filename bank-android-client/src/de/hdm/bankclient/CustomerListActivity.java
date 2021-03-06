package de.hdm.bankclient;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.appspot.skillful_octane_742.bankadministrationapi.Bankadministrationapi;
import com.appspot.skillful_octane_742.bankadministrationapi.model.Customer;
import com.appspot.skillful_octane_742.bankadministrationapi.model.CustomerCollection;

import de.hdm.bank_android_client.R;
import de.hdm.bankclient.adapter.CustomerAdapter;
import de.hdm.bankclient.util.EndpointsUtil;

public class CustomerListActivity extends Activity {

	private ListView customerListView;
	private CustomerAdapter arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_list);

		// Setting adapter for ListView
		customerListView = (ListView) findViewById(R.id.listviewCustomers);
		arrayAdapter = new CustomerAdapter(this, R.layout.list_item_cuostomer,
				new ArrayList<Customer>());

		customerListView.setAdapter(arrayAdapter);
		customerListView
				.setOnItemClickListener(new OnCustomerItemClickListener());

		refreshCustomers();
	}

	private class OnCustomerItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view,
				int position, long arg3) {

			Customer clickedCustomer = (Customer) adapterView.getAdapter()
					.getItem(position);

			// Transferring data in the intent
			Intent intent = new Intent(CustomerListActivity.this,
					AccountListActivity.class);
			intent.putExtra(EndpointsUtil.INTENT_EXTRA_CUSTOMER_ID,
					clickedCustomer.getId());
			intent.putExtra(EndpointsUtil.INTENT_EXTRA_CUSTOMER_FIRSTNAME,
					clickedCustomer.getFirstName());
			intent.putExtra(EndpointsUtil.INTENT_EXTRA_CUSTOMER_LASTNAME,
					clickedCustomer.getLastName());

			startActivity(intent);
		}

	}

	/**
	 * Calling remote service in a AsyncTask. An asynchronous task is defined by
	 * a computation that runs on a background thread and whose result is
	 * published on the UI thread.
	 * @see AsyncTask
	 * 
	 */
	private class GetAllCustomersTask extends
			AsyncTask<String, Void, CustomerCollection> {

		// 1. Processing the task and returning the result
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
				Log.e(EndpointsUtil.LOG, e.getMessage(), e);
				return null;
			}
		}

		// 2. Receiving the result and handling the gui / similar to onSuccess()
		// in AsyncCallback<T> in GWT GUIs
		@Override
		protected void onPostExecute(CustomerCollection result) {
			List<Customer> customers = result.getItems();
			arrayAdapter.clear();
			arrayAdapter.addAll(customers);
			// refreshing listview
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

		int id = item.getItemId();
		if (id == R.id.action_refresh_customers) {
			refreshCustomers();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void refreshCustomers() {
		// remote calls have to be called in a AsyncTask, otherwise the
		// interface would freeze
		new GetAllCustomersTask().execute();
	}
}
