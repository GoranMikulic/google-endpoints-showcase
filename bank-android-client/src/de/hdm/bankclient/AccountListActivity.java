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
import android.widget.ListView;

import com.appspot.skillful_octane_742.bankadministrationapi.Bankadministrationapi;
import com.appspot.skillful_octane_742.bankadministrationapi.Bankadministrationapi.GetAccountsOf;
import com.appspot.skillful_octane_742.bankadministrationapi.model.Account;
import com.appspot.skillful_octane_742.bankadministrationapi.model.AccountCollection;
import com.appspot.skillful_octane_742.bankadministrationapi.model.Customer;

import de.hdm.bank_android_client.R;
import de.hdm.bankclient.adapter.AccountAdapter;
import de.hdm.bankclient.util.EndpointsUtil;

public class AccountListActivity extends Activity {

	private ListView accountListView;
	private Customer customer = new Customer();
	private AccountAdapter accountAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_list);

		// displaying back button to the home activity
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// reading data from intent
		Intent intent = getIntent();
		customer.setId(intent.getIntExtra(
				EndpointsUtil.INTENT_EXTRA_CUSTOMER_ID, 0));
		customer.setFirstName(intent
				.getStringExtra(EndpointsUtil.INTENT_EXTRA_CUSTOMER_FIRSTNAME));
		customer.setLastName(intent
				.getStringExtra(EndpointsUtil.INTENT_EXTRA_CUSTOMER_LASTNAME));

		getActionBar().setTitle(
				customer.getFirstName() + " " + customer.getLastName());

		// Preparing listview and adapter
		accountListView = (ListView) findViewById(R.id.listviewAccounts);
		accountAdapter = new AccountAdapter(this, R.layout.list_item_account,
				new ArrayList<Account>());
		accountListView.setAdapter(accountAdapter);

		refreshAccounts();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
			case R.id.action_refresh_accounts:
				refreshAccounts();
				return true;
			case android.R.id.home:
				Intent upIntent = new Intent(this, CustomerListActivity.class);
				navigateUpToFromChild(this, upIntent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private class GetAccountsOfCustomerTask extends
			AsyncTask<Customer, Void, AccountCollection> {

		@Override
		protected AccountCollection doInBackground(Customer... params) {
			Customer c = params[0];

			// Getting service object
			Bankadministrationapi service = EndpointsUtil.getEndpointsService();

			try {
				return service.getAccountsOf(c.getId()).execute(); 

			} catch (IOException e) {
				Log.e(EndpointsUtil.LOG, e.getMessage(), e);
				return null;
			}

		}

		@Override
		protected void onPostExecute(AccountCollection result) {
			List<Account> accounts = result.getItems();

			accountAdapter.clear();
			accountAdapter.addAll(accounts);
			accountAdapter.notifyDataSetChanged();

			super.onPostExecute(result);
		}

	}

	private void refreshAccounts() {
		new GetAccountsOfCustomerTask().execute(this.customer);
	}
}
