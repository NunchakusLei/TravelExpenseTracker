package ca.ualberta.cs.travelexpensetracker;

public class Currency {
	private String type;
	private float amount;
	
	public Currency() {
		this.setAmount(0);
	}

	public Currency(String type,float amount) {
		this.setType(type);
		this.setAmount(amount);
	}
	
	public Currency(String type){
		this.setType(type);
		this.setAmount(0);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	public String toString(){
		return "Total   "+this.type + "    " + this.amount;
	}
	
	public boolean isSameType(Currency currency){
		return this.type==currency.getType();	
	}
}
