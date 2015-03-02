package com.paypal.api.payments;

import java.util.List;

import com.paypal.base.rest.PayPalModel;

public class CreditCardHistory  extends PayPalModel {

	/**
	 * A list of credit card resources
	 */
	private List<CreditCard> creditCards;

	/**
	 * Number of items returned in each range of results. Note that the last results range could have fewer items than the requested number of items.
	 */
	private int count;

	/**
	 * Identifier of the next element to get the next range of results.
	 */
	private String nextId;

	/**
	 * Default Constructor
	 */
	public CreditCardHistory() {
	}


	/**
	 * Setter for creditCards
	 */
	public CreditCardHistory setCreditCards(List<CreditCard> creditCards) {
		this.creditCards = creditCards;
		return this;
	}

	/**
	 * Getter for creditCards
	 */
	public List<CreditCard> getCreditCards() {
		return this.creditCards;
	}


	/**
	 * Setter for count
	 */
	public CreditCardHistory setCount(int count) {
		this.count = count;
		return this;
	}

	/**
	 * Getter for count
	 */
	public int getCount() {
		return this.count;
	}


	/**
	 * Setter for nextId
	 */
	public CreditCardHistory setNextId(String nextId) {
		this.nextId = nextId;
		return this;
	}

	/**
	 * Getter for nextId
	 */
	public String getNextId() {
		return this.nextId;
	}


}
