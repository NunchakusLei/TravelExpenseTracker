package ca.ualberta.cs.travelexpensetracker;

import ca.ualberta.cs.travelexpensetracker.Expense;
import java.util.Date;




//import android.app.Activity;
//import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends StartUpActivity {
	private EditText itemText;
	private EditText amountText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		itemText = (EditText)findViewById(R.id.editEventText);
		amountText = (EditText)findViewById(R.id.editAmountText);
		
		Button comfirmAddButton = (Button)findViewById(R.id.comfirmAddEventButton);
		ButtonListener comfirmAddButtonListner = new ButtonListener();
		comfirmAddButton.setOnClickListener(comfirmAddButtonListner);
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
			expense.setCurrency("CAD"); 
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
}
