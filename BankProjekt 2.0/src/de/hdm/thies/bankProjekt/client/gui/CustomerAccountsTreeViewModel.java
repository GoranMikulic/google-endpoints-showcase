package de.hdm.thies.bankProjekt.client.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.thies.bankProjekt.client.ClientsideSettings;
import de.hdm.thies.bankProjekt.shared.BankAdministrationAsync;
import de.hdm.thies.bankProjekt.shared.bo.Account;
import de.hdm.thies.bankProjekt.shared.bo.BusinessObject;
import de.hdm.thies.bankProjekt.shared.bo.Customer;

public class CustomerAccountsTreeViewModel implements TreeViewModel {

	private CustomerForm cf;
	private AccountForm af;

	private Customer selectedCustomer = null;
	private Account selectedAccount = null;

	private BankAdministrationAsync bankVerwaltung = ClientsideSettings
			.getBankVerwaltung();
	private ListDataProvider<Customer> customerDataProvider;
	private Map<Customer, ListDataProvider<Account>> accountDataProviders = new HashMap<Customer, ListDataProvider<Account>>();

	private ProvidesKey<BusinessObject> boKeyProvider = new ProvidesKey<BusinessObject>() {
		@Override
		public Integer getKey(BusinessObject bo) {
			if (bo == null) {
				return null;
			}
			if (bo instanceof Customer) {
				return new Integer(bo.getId());
			} else {
				return new Integer(-bo.getId());
			}
		}
	};

	private SingleSelectionModel<BusinessObject> selectionModel = new SingleSelectionModel<BusinessObject>(
			boKeyProvider);

	public CustomerAccountsTreeViewModel(CustomerForm cf, AccountForm af) {
		this.cf = cf;
		cf.setCatvm(this);
		this.af = af;
		af.setCatvm(this);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					@Override
					public void onSelectionChange(SelectionChangeEvent event) {
						BusinessObject selection = selectionModel
								.getSelectedObject();
						if (selection instanceof Customer) {
							setSelectedCustomer((Customer) selection);
						} else if (selection instanceof Account) {
							setSelectedAccount((Account) selection);
						}
					}
				});
	}

	Customer getSelectedCustomer() {
		return selectedCustomer;
	}

	void setSelectedCustomer(Customer c) {
		selectedCustomer = c;
		cf.setSelected(c);
		selectedAccount = null;
		af.setSelected(null);
	}

	Account getSelectedAccount() {
		return selectedAccount;
	}

	void setSelectedAccount(Account a) {
		selectedAccount = a;
		af.setSelected(a);
		
		bankVerwaltung.getCustomerById(a.getOwnerID(), new AsyncCallback<Customer>(){
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Customer customer) {
				selectedCustomer = customer;
				cf.setSelected(customer);
			}
		});
	}

	void addCustomer(Customer customer) {
		customerDataProvider.getList().add(customer);
		accountDataProviders.put(customer, new ListDataProvider<Account>());
		this.selectionModel.setSelected(customer, true);
	}

	void updateCustomer(Customer customer) {
		List<Customer> customerList = customerDataProvider.getList();
		int i = 0;
		for (Customer c : customerList) {
			if (c.getId() == customer.getId()) {
				customerList.set(i, customer);
				break;
			} else {
				i++;
			}
		}
		customerDataProvider.refresh();
	}

	void removeCustomer(Customer customer) {
		customerDataProvider.getList().remove(customer);
		accountDataProviders.remove(customer);
	}

	void addAccountOfCustomer(Account account, Customer customer) {
		// falls es noch keinen Account Provider für diesen Customer gibt,
		// wurde der Baumknoten noch nicht geöffnet und wir brauchen nichts tun.
		if (!accountDataProviders.containsKey(customer)) {
			return;
		}
		ListDataProvider<Account> accountsProvider = accountDataProviders
				.get(customer);
		if (!accountsProvider.getList().contains(account)) {
			accountsProvider.getList().add(account);
		}
		this.selectionModel.setSelected(account, true);
	}

	void removeAccountOfCustomer(Account account, Customer customer) {
		// falls es keinen Account Provider für diesen Customer gibt,
		// wurde der Baumknoten noch nicht geöffnet und wir brauchen nichts tun.
		if (!accountDataProviders.containsKey(customer)) {
			return;
		}
		accountDataProviders.get(customer).getList().remove(account);
		this.selectionModel.setSelected(customer, true);
	}

	void updateAccount(final Account a) {
		bankVerwaltung.getCustomerById(a.getOwnerID(),
				new AsyncCallback<Customer>() {
					@Override
					public void onFailure(Throwable t) {
					}

					@Override
					public void onSuccess(Customer c) {
						updateAccountForCustomer(a, c);
					}
				});
	}

	void updateAccountForCustomer(Account account, Customer customer) {
		List<Account> accountList = accountDataProviders.get(customer)
				.getList();
		int i = 0;
		for (Account a : accountList) {
			if (account.getId() == i) {
				accountList.set(i, a);
				break;
			} else {
				i++;
			}
		}
	}

	// Get the NodeInfo that provides the children of the specified value.
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {

		if (value.equals("Root")) {
			// Erzeugen eines ListDataproviders für Customerdaten
			customerDataProvider = new ListDataProvider<Customer>();
			bankVerwaltung
					.getAllCustomers(new AsyncCallback<Vector<Customer>>() {
						@Override
						public void onFailure(Throwable t) {
						}

						@Override
						public void onSuccess(Vector<Customer> customers) {
							for (Customer c : customers) {
								customerDataProvider.getList().add(c);
							}
						}
					});

			// Return a node info that pairs the data with a cell.
			return new DefaultNodeInfo<Customer>(customerDataProvider,
					new CustomerCell(), selectionModel, null);
		}

		if (value instanceof Customer) {
			// Erzeugen eines ListDataproviders für Account-Daten
			final ListDataProvider<Account> accountsProvider = new ListDataProvider<Account>();
			accountDataProviders.put((Customer) value, accountsProvider);

			bankVerwaltung.getAccountsOf((Customer) value,
					new AsyncCallback<Vector<Account>>() {
						@Override
						public void onFailure(Throwable t) {
						}

						@Override
						public void onSuccess(Vector<Account> accounts) {
							for (Account a : accounts) {
								accountsProvider.getList().add(a);
							}
						}
					});

			// Return a node info that pairs the data with a cell.
			return new DefaultNodeInfo<Account>(accountsProvider,
					new AccountCell(), selectionModel, null);
		}
		return null;
	}

	// Check if the specified value represents a leaf node. Leaf nodes
	// cannot be opened.
	@Override
	public boolean isLeaf(Object value) {
		// value is of type Account
		return (value instanceof Account);
	}

}
