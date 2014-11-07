package de.hdm.thies.bankProjekt.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.thies.bankProjekt.client.ClientsideSettings;
import de.hdm.thies.bankProjekt.shared.BankAdministrationAsync;
import de.hdm.thies.bankProjekt.shared.bo.Account;
import de.hdm.thies.bankProjekt.shared.bo.Customer;
import de.hdm.thies.bankProjekt.shared.bo.Transaction;

/**
 * Formular für die Darstellung des selektierten Kunden
 */

public class AccountForm extends VerticalPanel {
	BankAdministrationAsync bankVerwaltung = ClientsideSettings
			.getBankVerwaltung();
	Account shownAccount = null;
	CustomerAccountsTreeViewModel catvm = null;
	NumberFormat decimalFormatter = NumberFormat.getDecimalFormat();

	Label idValueLabel = new Label();
	Label balanceValueLabel = new Label();
	TextBox amountTextBox = new TextBox();

	public AccountForm() {
		Grid customerGrid = new Grid(4, 2);
		this.add(customerGrid);

		Label idLabel = new Label("Kontonummer");
		customerGrid.setWidget(1, 0, idLabel);
		customerGrid.setWidget(1, 1, idValueLabel);

		Label balanceLabel = new Label("Kontonstand");
		customerGrid.setWidget(2, 0, balanceLabel);
		customerGrid.setWidget(2, 1, balanceValueLabel);

		Label amountLabel = new Label("Betrag");
		customerGrid.setWidget(3, 0, amountLabel);
		customerGrid.setWidget(3, 1, amountTextBox);

		HorizontalPanel accountButtonsPanel = new HorizontalPanel();
		this.add(accountButtonsPanel);

		Button depositButton = new Button("Einzahlen");
		depositButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				float amount = 0.0F;
				try {
					amount = (float) decimalFormatter.parse(amountTextBox
							.getText());
				} catch (NumberFormatException nfe) {
					Window.alert("ungültiger Wert!");
					return;
				}

				if (shownAccount == null) {
					Window.alert("kein Konto ausgewählt!");
					return;
				}

				bankVerwaltung.createDeposit(shownAccount, amount,
						new AsyncCallback<Transaction>() {
							@Override
							public void onFailure(Throwable caught) {
							}

							@Override
							public void onSuccess(Transaction trans) {
								if (trans != null) {
									bankVerwaltung.getAccountById(
											trans.getTargetAccountID(),
											new updateAccountByIdCallback());
								}
							}
						});
			}
		});
		accountButtonsPanel.add(depositButton);

		Button withdrawButton = new Button("Abheben");
		withdrawButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				float amount = 0.0F;
				try {
					amount = (float) decimalFormatter.parse(amountTextBox
							.getText());
				} catch (NumberFormatException nfe) {
					Window.alert("ungültiger Wert!");
					return;
				}

				if (shownAccount == null) {
					Window.alert("kein Konto ausgewählt!");
					return;
				}

				bankVerwaltung.createWithdrawal(shownAccount, amount,
						new AsyncCallback<Transaction>() {
							@Override
							public void onFailure(Throwable caught) {
							}

							@Override
							public void onSuccess(Transaction trans) {
								if (trans != null) {
									bankVerwaltung.getAccountById(
											shownAccount.getId(),
											new updateAccountByIdCallback());
								}
							}
						});
			}
		});

		accountButtonsPanel.add(withdrawButton);

		Button deleteButton = new Button("Löschen");
		deleteButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (shownAccount == null) {
					Window.alert("kein Konto ausgewählt");
				} else {
					bankVerwaltung.getCustomerById(shownAccount.getOwnerID(),
							new useCustomerForAccountDeletionCallback(
									shownAccount));
				}
			}
		});

		accountButtonsPanel.add(deleteButton);

		Button newButton = new Button("Neu");
		newButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Customer selectedCustomer = catvm.getSelectedCustomer();
				if (selectedCustomer == null) {
					Window.alert("kein Kunde ausgewählt");
				} else {
					bankVerwaltung.createAccountFor(selectedCustomer,
							new CreateAccountCallback(selectedCustomer));
				}
			}
		});

		accountButtonsPanel.add(newButton);
	}

	void setCatvm(CustomerAccountsTreeViewModel catvm) {
		this.catvm = catvm;
	}

	void setFields() {
		idValueLabel.setText(Integer.toString(shownAccount.getId()));
		bankVerwaltung.getBalanceOf(shownAccount, new AsyncCallback<Float>() {
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Float result) {
				if (result != null) {
					balanceValueLabel.setText(decimalFormatter.format(result) + " Euro");
				}
			}
		});
	}

	void clearFields() {
		this.amountTextBox.setText("");
		this.balanceValueLabel.setText("");
		this.idValueLabel.setText("");
	}

	void setSelected(Account a) {
		if (a != null) {
			shownAccount = a;
			setFields();
		} else {
			shownAccount = null;
			clearFields();
		}
	}

	class updateAccountByIdCallback implements AsyncCallback<Account> {
		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Account a) {
			if (a != null) {
				setSelected(a);
				catvm.updateAccount(a);
			}
		}
	}

	class useCustomerForAccountDeletionCallback implements
			AsyncCallback<Customer> {

		Account account = null;

		useCustomerForAccountDeletionCallback(Account a) {
			account = a;
		}

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Customer customer) {
			if (customer != null && account != null) {
				bankVerwaltung.delete(account, new deleteAccountCallback(
						account, customer));
			}
		}
	}

	class deleteAccountCallback implements AsyncCallback<Void> {

		Customer customer = null;
		Account account = null;

		deleteAccountCallback(Account a, Customer c) {
			account = a;
			customer = c;
		}

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Void result) {
			setSelected(null);
			if (customer != null) {
				catvm.removeAccountOfCustomer(account, customer);
			}
		}
	}

	class CreateAccountCallback implements AsyncCallback<Account> {

		Customer c = null;

		CreateAccountCallback(Customer customer) {
			c = customer;
		}

		@Override
		public void onFailure(Throwable caught) {
			// this.showcase.append("Fehler bei der Abfrage " +
			// caught.getMessage());
		}

		@Override
		public void onSuccess(Account account) {
			if (account != null) {
				ClientsideSettings.getLogger().info(
						"Konto mit Kto.-Nr. " + account.getId()
								+ " für Kunde mit ID " + account.getOwnerID()
								+ " wurde angelegt.");
				if (c != null) {
					catvm.addAccountOfCustomer(account, c);
				}
			}
		}
	}

}
