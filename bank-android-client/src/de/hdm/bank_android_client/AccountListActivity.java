package de.hdm.bank_android_client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.appspot.skillful_octane_742.bankadministrationapi.Bankadministrationapi;
import com.appspot.skillful_octane_742.bankadministrationapi.Bankadministrationapi.GetAccountsOf;
import com.appspot.skillful_octane_742.bankadministrationapi.model.Account;
import com.appspot.skillful_octane_742.bankadministrationapi.model.AccountCollection;
import com.appspot.skillful_octane_742.bankadministrationapi.model.Customer;

import de.hdm.bank_android_client.adapter.AccountAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

public class AccountListActivity extends Activity {

	private ListView accountListView;
	private Customer customer = new Customer();
	private AccountAdapter accountAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_list);

		// reading data from intent
		Intent intent = getIntent();
		customer.setId(intent.getIntExtra(
				EndpointsUtil.INTENT_EXTRA_CUSTOMER_ID, 0));
		customer.setFirstName(intent
				.getStringExtra(EndpointsUtil.INTENT_EXTRA_CUSTOMER_FIRSTNAME));
		customer.setLastName(intent
				.getStringExtra(EndpointsUtil.INTENT_EXTRA_CUSTOMER_LASTNAME));
		
		getActionBar().setTitle(customer.getFirstName() + " " + customer.getLastName());
		
		//Preparing listview and adapter
		accountListView = (ListView) findViewById(R.id.listviewAccounts);
		accountAdapter = new AccountAdapter(this, R.layout.list_item_account,
				new ArrayList<Account>());
		accountListView.setAdapter(accountAdapter);
		
		
		new GetAccountsOfCustomerTask().execute(this.customer);
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

	private class GetAccountsOfCustomerTask extends
			AsyncTask<Customer, Void, AccountCollection> {

		@Override
		protected AccountCollection doInBackground(Customer... params) {
			Customer c = params[0];

			// Getting service object
			Bankadministrationapi service = EndpointsUtil.getEndpointsService();
			try {
				GetAccountsOf getAccountsOf = service.getAccountsOf(c.getId());
				
				AccountCollection ac = getAccountsOf.execute();
				
				return ac;
			} catch (IOException e) {
				e.printStackTrace();
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
}
