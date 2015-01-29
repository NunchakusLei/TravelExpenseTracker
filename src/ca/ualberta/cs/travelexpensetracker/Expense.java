package ca.ualberta.cs.travelexpensetracker;

import java.util.Date;

public class Expense {
	protected String item;
	protected float amount;
	protected String currency;
	protected Date date;
	protected Date changeDate;
	protected String category;
	protected String description;
	
	// constructor
	public Expense(){;//String item, float amount, String currency, Date date){
	/*
		this.setItem(item);
		this.setAmount(amount);
		this.setCurrency(currency);
		this.setDate(date);*/
	}
	
	// to String
	@Override
	public String toString(){
		String str = new String();
		str = this.item + " -------> $" + "" + this.amount;
		return str;
	}
	

	public Date getChangeDate(){
		return changeDate;
	}
	public void setChangeDate(Date changeDate){
		this.changeDate = changeDate;
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

	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
		
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
		
	}
}
