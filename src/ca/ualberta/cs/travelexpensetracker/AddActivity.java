package ca.ualberta.cs.travelexpensetracker;

import ca.ualberta.cs.travelexpensetracker.Expense;

import java.util.ArrayList;
import java.util.Date;

import android.widget.AdapterView.OnItemSelectedListener;




import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
//import android.app.Activity;
//import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class AddActivity extends StartUpActivity {
	private EditText itemText;
	private EditText amountText;
	private EditText addExpenseDescriptionEditView;
	
	private Spinner addExpenseCurrencySpinner;
	private ArrayAdapter<String> dataCurrencyAdapter;
	private Spinner addExpenseCategorySpinner;
	private ArrayAdapter<String> dataCategoryAdapter;
	
	private DatePicker addExpenseDatePicker;
	private TimePicker addExpenseTimePicker;
	//private Button AddEventDateSettingButton;
	//private Button AddEventTimeSettingButton;
	private Date expenseDate;
	private Expense oldExpense;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		itemText = (EditText)findViewById(R.id.editEventText);
		amountText = (EditText)findViewById(R.id.editAmountText);
		addExpenseDescriptionEditView = (EditText)findViewById(R.id.addExpenseDescriptionEditView);
		
		addExpenseDatePicker = (DatePicker) findViewById(R.id.addExpenseDatePicker);
		addExpenseTimePicker = (TimePicker) findViewById(R.id.addExpenseTimePicker);
		
		/*
		AddEventDateSettingButton = (Button)findViewById(R.id.addEventDateSettingButton);
		ButtonListener AddEventDateSettingButtonListner = new ButtonListener();
		AddEventDateSettingButton.setOnClickListener(AddEventDateSettingButtonListner);
		
		AddEventTimeSettingButton = (Button)findViewById(R.id.addEventTimeSettingButton);
		ButtonListener AddEventTimeSettingButtonListner = new ButtonListener();
		AddEventTimeSettingButton.setOnClickListener(AddEventTimeSettingButtonListner);
		*/
		
		Button comfirmAddButton = (Button)findViewById(R.id.comfirmAddEventButton);
		ButtonListener comfirmAddButtonListner = new ButtonListener();
		comfirmAddButton.setOnClickListener(comfirmAddButtonListner);
		
		
		// For Currency Choice Spinner
		// oldClaim = (ListView)findViewById(R.id.evenListView);
		addExpenseCurrencySpinner = (Spinner) findViewById(R.id.addExpenseCurrencySpinner);
		ArrayList<String> list = new ArrayList<String>();
		list.add("CAD $");
		list.add("USD $");
		list.add("EUR €");
		list.add("GBP £");
		list.add("CNY ¥");
		dataCurrencyAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		dataCurrencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		addExpenseCurrencySpinner.setAdapter(dataCurrencyAdapter);
		
		addExpenseCategorySpinner = (Spinner) findViewById(R.id.addExpenseCategorySpinner);
		ArrayList<String> listCategory = new ArrayList<String>();
		listCategory.add("air fare");
		listCategory.add("ground transport");
		listCategory.add("vehicle rental");
		listCategory.add("fuel");
		listCategory.add("parking");
		listCategory.add("registration");
		listCategory.add("accommodation");
		listCategory.add("meal");
		dataCategoryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listCategory);
		dataCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		addExpenseCategorySpinner.setAdapter(dataCategoryAdapter);

		/*
		// Spinner item selection Listener
		addListenerOnSpinnerItemSelection();

		// Button click Listener
		addListenerOnButton();*/

		/*
		adapter = new ExpenseAdapter(this, R.layout.list_claim, claims);
		oldClaimsList.setAdapter(adapter);
		adapter.notifyDataSetChanged();*/
		
		oldExpense = getForEditExpense();
		//System.out.println(oldExpense);
		
		if(oldExpense!=null){
			itemText.setText(oldExpense.getItem());
			amountText.setText(""+(oldExpense.getAmount()));
			addExpenseDescriptionEditView.setText(oldExpense.getDescription());
			
			addExpenseCategorySpinner.setSelection(listCategory.indexOf(oldExpense.getCategory()));
			addExpenseCurrencySpinner.setSelection(list.indexOf(oldExpense.getCurrency()));
			
			//http://stackoverflow.com/questions/2198410/how-to-change-title-of-activity-in-android 2015.1.29.
			setTitle("Edit Expense");
			
			//http://stackoverflow.com/questions/6451837/how-do-i-set-the-current-date-in-a-datepicker 2015.1.29.
			addExpenseDatePicker.updateDate(oldExpense.getDate().getYear()+1900, oldExpense.getDate().getMonth(), oldExpense.getDate().getDate());
			
			//http://stackoverflow.com/questions/5817883/setting-time-and-date-to-date-picker-and-time-picker-in-android 2015.1.29.
			addExpenseTimePicker.setCurrentHour(oldExpense.getDate().getHours());
			addExpenseTimePicker.setCurrentMinute(oldExpense.getDate().getMinutes());
			//setContentView(R.layout.activity_add);
			//addExpenseDatePicker.setV
		}
		        
	}

	
	class ButtonListener implements OnClickListener{
		@SuppressWarnings("deprecation")
		@Override
		public void onClick (View view){
			// code to add save content
			setResult(RESULT_OK);
			

			Expense expense = new Expense();
			String errorType = "";
				
			if(itemText.getText().toString().length()!=0){
				expense.setItem(itemText.getText().toString());
			} else {
				errorType += "Enter a Item! ";
			}
				
			expense.setCurrency(addExpenseCurrencySpinner.getSelectedItem().toString());
			expense.setCategory(addExpenseCategorySpinner.getSelectedItem().toString());
			expense.setDescription(addExpenseDescriptionEditView.getText().toString());
				
			// set up amount
			try{
				expense.setAmount(Float.parseFloat(amountText.getText().toString()));
			}catch(NumberFormatException e){
				errorType += "  Enter Amount!";
			}
				
				// set up expenseDate
				expenseDate = new Date();
				//expenseDate = new Date(addExpenseDatePicker.getYear() - 1900,addExpenseDatePicker.getMonth(),addExpenseDatePicker.getDayOfMonth());
				expenseDate.setYear(addExpenseDatePicker.getYear()-1900);
				expenseDate.setMonth(addExpenseDatePicker.getMonth());
				expenseDate.setDate(addExpenseDatePicker.getDayOfMonth());
				expenseDate.setHours(addExpenseTimePicker.getCurrentHour());
				expenseDate.setMinutes(addExpenseTimePicker.getCurrentMinute());
				/*
				System.out.println(expenseDate);
				expenseDate.setTime(addExpenseDatePicker.get());
				System.out.println(expenseDate);
				//expense.setDate(addExpenseDatePicker.getDisplay());
				System.out.println(addExpenseDatePicker.getYear());
				System.out.println(addExpenseDatePicker.getDayOfMonth());
				System.out.println(addExpenseDatePicker.getMonth());*/
				expense.setDate(expenseDate);
				
				
				expense.setChangeDate(new Date(System.currentTimeMillis()));
			
				
				//Date testDate = new Date(System.currentTimeMillis());
				//System.out.println(testDate);
				
				
			if (errorType.length() == 0) {		
				System.out.println(oldExpense);
				
				if (oldExpense!=null){
					int position = StartUpActivity.getForEditExpensePosition();
					getClaims().remove(position);
					getClaims().add(position, expense);
				} else {
					getClaims().add(0, expense);
				}
				
				
				// getExpenseList().add(0,expense.toString());
				getAdapter().notifyDataSetChanged();

				saveInFile(expense, new Date(System.currentTimeMillis()));

				Toast.makeText(getBaseContext(), "Expense added",
						Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(getBaseContext(),errorType,Toast.LENGTH_SHORT).show();
			}
				/*
				Itent intent = new Intent(AddActivity.this,StartUpActivity.class);
				startActivity(intent);*/
				
			/*
			case R.id.addEventDateSettingButton:
				
				Date nowDate = new Date(System.currentTimeMillis());
				String nowDateStr = nowDate.toString();
				String[] nowDateList = nowDateStr.split(" ");
				int year = nowDateList[5];
				int month = nowDateList[0];
				int date = nowDateList[];
				System.out.println(year);
				
				DatePickerDialog datePicker = new DatePickerDialog(
						AddActivity.this, new OnDateSetListener(){
							@Override
							public void onDateSet(DatePicker view,int year,int monthOfYear,int dayOfMonth){
								AddEventDateSettingButton.setText(new StringBuilder().append(day).append("/")
			     														.append(month).append("/").append(year));
							}
						},2015,month,date);
				datePicker.show();
				break;
			case R.id.addEventTimeSettingButton:
				
				break;*/

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

	        /*
	        Toast.makeText(parent.getContext(), 
	                "On Item Select : \n" + parent.getItemAtPosition(pos).toString(),
	                Toast.LENGTH_SHORT).show();*/
	    }
	 
	    @Override
	    public void onNothingSelected(AdapterView<?> arg0) {
	        // TODO Auto-generated method stub
	 
	    }
	 
	}
}
