package ca.ualberta.cs.travelexpensetracker;

import java.util.ArrayList;
import java.util.Date;

public class Claim {
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private ArrayList<Currency> totalCurrency;
	private ArrayList<Expense> expenseList;
	
	public Claim() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ArrayList<Currency> getTotalCurrency() {
		return totalCurrency;
	}

	public void setTotalCurrency(ArrayList<Currency> totalCurrency) {
		this.totalCurrency = totalCurrency;
	}

	public ArrayList<Expense> getExpenseList() {
		return expenseList;
	}

	public void setExpenseList(ArrayList<Expense> expenseList) {
		this.expenseList = expenseList;
	}

	public String toString(){
		return this.name;
	}
}
