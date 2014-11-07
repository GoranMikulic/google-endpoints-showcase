package de.hdm.thies.bankProjekt.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import de.hdm.thies.bankProjekt.shared.bo.Customer;

public class CustomerForm extends VerticalPanel {
	TextBox firstNameTextBox = new TextBox();
	TextBox lastNameTextBox = new TextBox();
	Label idValueLabel = new Label();

	BankAdministrationAsync bankVerwaltung = ClientsideSettings
			.getBankVerwaltung();
	Customer shownCustomer = null;
	CustomerAccountsTreeViewModel catvm = null;

	/**
	 * Formular für die Darstellung des selektierten Kunden
	 */
	public CustomerForm() {
		Grid customerGrid = new Grid(3, 2);
		this.add(customerGrid);

		Label idLabel = new Label("ID");
		customerGrid.setWidget(0, 0, idLabel);
		customerGrid.setWidget(0, 1, idValueLabel);

		Label firstNameLabel = new Label("Vorname");
		customerGrid.setWidget(1, 0, firstNameLabel);
		customerGrid.setWidget(1, 1, firstNameTextBox);

		Label lastNameLabel = new Label("Nachname");
		customerGrid.setWidget(2, 0, lastNameLabel);
		customerGrid.setWidget(2, 1, lastNameTextBox);

		HorizontalPanel customerButtonsPanel = new HorizontalPanel();
		this.add(customerButtonsPanel);

		Button changeButton = new Button("Ändern");
		changeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				modifySelectedCustomer();
			}
		});
		customerButtonsPanel.add(changeButton);
		
		
		Button searchButton = new Button("Suchen");
		customerButtonsPanel.add(searchButton);
		
		Button deleteButton = new Button("Löschen");
		deleteButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (shownCustomer==null) {
					Window.alert("kein Kunde ausgewählt");
				} else {
					bankVerwaltung.delete(shownCustomer, new deleteCustomerCallback(shownCustomer));
				}
			}
		});
		
		customerButtonsPanel.add(deleteButton);
		
		Button newButton = new Button("Neu");
		newButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String firstName = firstNameTextBox.getText();
				String lastName = lastNameTextBox.getText();
				bankVerwaltung.createCustomer(firstName, lastName, new CreateCustomerCallback());
			}
		});
		
		customerButtonsPanel.add(newButton);
	}

	void setCatvm(CustomerAccountsTreeViewModel catvm) {
		this.catvm = catvm;
	}

	void setFields() {
		firstNameTextBox.setText(shownCustomer.getFirstName());
		lastNameTextBox.setText(shownCustomer.getLastName());
		idValueLabel.setText(Integer.toString(shownCustomer.getId()));
	}

	void clearFields() {
		firstNameTextBox.setText("");
		lastNameTextBox.setText("");
		idValueLabel.setText("");
	}

	void setSelected(Customer c) {
		if (c != null) {
			shownCustomer = c;
			setFields();
		} else {
			clearFields();
		}
	}
	
	void modifySelectedCustomer() {
		if (this.shownCustomer!=null){
			shownCustomer.setFirstName(firstNameTextBox.getText());
			shownCustomer.setLastName(lastNameTextBox.getText());
			bankVerwaltung.save(shownCustomer, new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable caught){					
				}
				@Override
				public void onSuccess(Void result){		
					catvm.updateCustomer(shownCustomer);
				}
			});
		} else {
			Window.alert("kein Kunde ausgewählt");
		}
	}

	class deleteCustomerCallback implements AsyncCallback<Void> {
		
		Customer customer = null;
		
		deleteCustomerCallback(Customer c) {
			customer = c;
		}

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Void result) {
			if (customer != null) {
				setSelected(null);
				catvm.removeCustomer(customer);
			}
		}
	}
	
	class CreateCustomerCallback implements AsyncCallback<Customer> {

		@Override
		public void onFailure(Throwable caught) {
			// this.showcase.append("Fehler bei der Abfrage " +
			// caught.getMessage());
		}

		@Override
		public void onSuccess(Customer customer) {
			if (customer != null) {
				ClientsideSettings.getLogger().info(
						"Kunde mit " + customer.getFirstName() + " "
								+ customer.getLastName() + " wurde angelegt.");
				catvm.addCustomer(customer);
			}
		}
	}

}
