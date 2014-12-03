package de.hdm.thies.bankProjekt.server;

import java.util.Vector;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;

import de.hdm.thies.bankProjekt.shared.BankAdministration;
import de.hdm.thies.bankProjekt.shared.bo.Account;
import de.hdm.thies.bankProjekt.shared.bo.Customer;

@Api(name = "bankadministrationapi", version = "v1", description = "Bank Administration APIs", clientIds = { "1090984442246-4gc875lktk8b48nhglnnnovo12pvg6q2.apps.googleusercontent.com" }, audiences = { "1090984442246-4gc875lktk8b48nhglnnnovo12pvg6q2.apps.googleusercontent.com" })
public class BankAdministrationEndpoints {

	BankAdministration bankAdministration;

	public BankAdministrationEndpoints() {
		this.bankAdministration = new BankAdministrationImpl();
		this.bankAdministration.init();
	}

	@ApiMethod(name = "getAllCustomers")
	public Vector<Customer> getAllCustomers() {
		return this.bankAdministration.getAllCustomers();
	}

	@ApiMethod(name = "getAccountById")
	public Account getAccountById(@Named("id") int id) {
		return this.bankAdministration.getAccountById(id);
	}
}
