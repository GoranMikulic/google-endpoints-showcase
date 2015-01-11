package de.hdm.thies.bankProjekt.shared.bo;

import java.io.Serializable;

public class Balance implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private float balance;

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}
	
}
