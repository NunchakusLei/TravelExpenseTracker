package ca.ualberta.cs.travelexpensetracker;

import java.util.Date;

public class Expense {
	private String item;
	private float amount;
	private String currency;
	private Date date;
	
	// constructor
	public Expense(String item, float amount, String currency, Date date){
		this.setItem(item);
		this.setAmount(amount);
		this.setCurrency(currency);
		this.setDate(date);
	}
	
	// to String
	@Override
	public String toString(){
		String str = new String();
		str = this.item + " -------> $" + "" + this.amount;
		return str;
	}
	


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}



	public String getItem() {
		return item;
	}



	public void setItem(String item) {
		this.item = item;
	}



	public float getAmount() {
		return amount;
	}



	public void setAmount(float amount) {
		this.amount = amount;
	}



	public String getCurrency() {
		return currency;
	}



	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
