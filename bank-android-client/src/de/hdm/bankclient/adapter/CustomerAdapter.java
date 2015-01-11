package de.hdm.bankclient.adapter;

import java.util.List;

import com.appspot.skillful_octane_742.bankadministrationapi.model.Customer;

import de.hdm.bank_android_client.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomerAdapter extends ArrayAdapter<Customer> {

	Context context;
    int layoutResourceId;
    List<Customer> customers;	
	
	public CustomerAdapter(Context context, int layoutResourceId, List<Customer> objects) {
		super(context, layoutResourceId, objects);
		
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.customers = objects;

	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}
		
		Customer customer = customers.get(position);
		
		TextView firstName = (TextView) convertView.findViewById(R.id.listItemFirstName);
		firstName.setText(customer.getFirstName());
		firstName.setTag(customer.getId());
		
		TextView lastName = (TextView) convertView.findViewById(R.id.listItemLastName);
		lastName.setText(customer.getLastName());
		lastName.setTag(customer.getId());
		
			
		return convertView;
	}
	
	
	
}
