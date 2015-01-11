package de.hdm.bank_android_client.adapter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appspot.skillful_octane_742.bankadministrationapi.Bankadministrationapi;
import com.appspot.skillful_octane_742.bankadministrationapi.model.Account;
import com.appspot.skillful_octane_742.bankadministrationapi.model.Balance;

import de.hdm.bank_android_client.R;
import de.hdm.bank_android_client.util.EndpointsUtil;

public class AccountAdapter extends ArrayAdapter<Account> {
	
	private Context context;
	private int layoutResourceId;
	private List<Account> accounts;
	
	
	public AccountAdapter(Context context, int layoutResourceId, List<Account> objects) {
		super(context, layoutResourceId, objects);
		
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.accounts = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}
		
		Account account = accounts.get(position);
		
		TextView accountIdTv = (TextView) convertView.findViewById(R.id.listItemAccountId);
		accountIdTv.setText("" + account.getId());
		accountIdTv.setTag(account.getId());
		
		TextView accountBalanceTextView = (TextView) convertView.findViewById(R.id.listItemBalance);
		
		AsyncTask<Account, Void, Balance> test = new GetBalanceAsyncTask().execute(account);
		
		try {
			Balance balance = test.get();
			accountBalanceTextView.setText("" + balance.getBalance());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		return convertView;
	}
	
	private class GetBalanceAsyncTask extends AsyncTask<Account, Void, Balance> {

		@Override
		protected Balance doInBackground(Account... params) {
			
			Account a = params[0];
			
			Bankadministrationapi service = EndpointsUtil.getEndpointsService();
			
			try {
				Balance balance = service.getBalanceOf(a.getId()).execute();
				return balance;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			
		}
		
	}
	

}
