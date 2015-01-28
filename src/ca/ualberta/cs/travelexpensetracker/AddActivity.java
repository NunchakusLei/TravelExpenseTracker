package ca.ualberta.cs.travelexpensetracker;

import ca.ualberta.cs.travelexpensetracker.Expense;

import java.util.ArrayList;
import java.util.Date;

import android.widget.AdapterView.OnItemSelectedListener;





//import android.app.Activity;
//import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class AddActivity extends StartUpActivity {
	private EditText itemText;
	private EditText amountText;
	
	private Spinner addExpenseCurrencySpinner;
	private ArrayAdapter<String> dataAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		itemText = (EditText)findViewById(R.id.editEventText);
		amountText = (EditText)findViewById(R.id.editAmountText);
		
		Button comfirmAddButton = (Button)findViewById(R.id.comfirmAddEventButton);
		ButtonListener comfirmAddButtonListner = new ButtonListener();
		comfirmAddButton.setOnClickListener(comfirmAddButtonListner);
		
		
		// For Spinner
		// oldClaim = (ListView)findViewById(R.id.evenListView);
		addExpenseCurrencySpinner = (Spinner) findViewById(R.id.addExpenseCurrencySpinner);
		System.out.println(addExpenseCurrencySpinner);

		ArrayList<String> list = new ArrayList<String>();
		list.add("CAD");
		list.add("USD");
		list.add("EUR");
		list.add("GBP");
		list.add("CNY");

		dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		System.out.println("1");
		System.out.println(addExpenseCurrencySpinner);
		System.out.println(dataAdapter.toString());
		addExpenseCurrencySpinner.setAdapter(dataAdapter);

		// Spinner item selection Listener
		addListenerOnSpinnerItemSelection();

		// Button click Listener
		addListenerOnButton();

		/*
		adapter = new ExpenseAdapter(this, R.layout.list_claim, claims);
		oldClaimsList.setAdapter(adapter);
		adapter.notifyDataSetChanged();*/
		        
	}

	
	class ButtonListener implements OnClickListener{
		@Override
		public void onClick (View view){
			// code to add save content
			setResult(RESULT_OK);
			String text = itemText.getText().toString();
			
			
			Expense expense = new Expense();
			expense.setItem(text);
			expense.setAmount(Float.parseFloat(amountText.getText().toString()));
			expense.setCurrency(addExpenseCurrencySpinner.getSelectedItem().toString()); 
			expense.setDate(new Date(System.currentTimeMillis()));
		
			
			getClaims().add(0,expense);
			//getExpenseList().add(0,expense.toString());
			getAdapter().notifyDataSetChanged();
			
			saveInFile(expense, new Date(System.currentTimeMillis()));
			
			Toast.makeText(getBaseContext(), "Expense added", Toast.LENGTH_SHORT).show();
			finish();
			/*
			Itent intent = new Intent(AddActivity.this,StartUpActivity.class);
			startActivity(intent);*/
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	//http://androidexample.com/Spinner_Basics_-_Android_Example/index.php?view=article_discription&aid=82&aaid=105 1.28.2015
	// Spinner
	//private Spinner spinner1;
	//private Button btnSubmit;

	/*
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		List<String> list = new ArrayList<String>();
		list.add("Android");
		list.add("Java");
		list.add("Spinner Data");
		list.add("Spinner Adapter");
		list.add("Spinner Example");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner1.setAdapter(dataAdapter);

		// Spinner item selection Listener
		addListenerOnSpinnerItemSelection();

		// Button click Listener
		addListenerOnButton();

	}*/

	// Add spinner data

	public void addListenerOnSpinnerItemSelection() {

		addExpenseCurrencySpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}

	// get the selected dropdown list value

	public void addListenerOnButton() {

		addExpenseCurrencySpinner = (Spinner) findViewById(R.id.addExpenseCurrencySpinner);

		/*btnSubmit = (Button) findViewById(R.id.btnSubmit);

		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Toast.makeText(
						SpinnerExample.this,
						"On Button Click : " + "\n"
								+ String.valueOf(spinner1.getSelectedItem()),
						Toast.LENGTH_LONG).show();
			}

		});*/

	}
	
	public class CustomOnItemSelectedListener implements OnItemSelectedListener {
		 
	    public void onItemSelected(AdapterView<?> parent, View view, int pos,
	            long id) {
	         
	        Toast.makeText(parent.getContext(), 
	                "On Item Select : \n" + parent.getItemAtPosition(pos).toString(),
	                Toast.LENGTH_LONG).show();
	    }
	 
	    @Override
	    public void onNothingSelected(AdapterView<?> arg0) {
	        // TODO Auto-generated method stub
	 
	    }
	 
	}
}
