package ca.ualberta.cs.travelexpensetracker;

//import ca.ualberta.cs.travelexpensetracker.StartUpActivity.ButtonListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AddActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		Button comfirmAddButton = (Button)findViewById(R.id.comfirmAddEventButton);
		ButtonListener comfirmAddButtonListner = new ButtonListener();
		comfirmAddButton.setOnClickListener(comfirmAddButtonListner);
	}

	
	class ButtonListener implements OnClickListener{
		@Override
		public void onClick (View view){
			// code to add save content
			
			
			Intent intent = new Intent(AddActivity.this,StartUpActivity.class);
			startActivity(intent);

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
