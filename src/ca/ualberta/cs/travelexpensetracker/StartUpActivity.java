package ca.ualberta.cs.travelexpensetracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.TextView;

public class StartUpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_up);
		
		Button addButton = (Button)findViewById(R.id.AddButton);
		ButtonListener addButtonListner = new ButtonListener();
		addButton.setOnClickListener(addButtonListner);
		
		Button editButton = (Button)findViewById(R.id.EditButton);
		ButtonListener editButtonListner = new ButtonListener();
		editButton.setOnClickListener(editButtonListner);
	}

	
	class ButtonListener implements OnClickListener{
		@Override
		public void onClick (View view){
			if(view.getId()==R.id.AddButton){
				Intent intent = new Intent(StartUpActivity.this,AddActivity.class);
				startActivity(intent);
			} else if (view.getId()==R.id.EditButton){
				//do nothing
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_up, menu);
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
