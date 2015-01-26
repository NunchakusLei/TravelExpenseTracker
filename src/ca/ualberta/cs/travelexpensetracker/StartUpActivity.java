package ca.ualberta.cs.travelexpensetracker;

import ca.ualberta.cs.travelexpensetracker.Expense;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class StartUpActivity extends Activity {
	private static final String FILENAME = "claimdata.sav";
	private ArrayList<Expense> claims;
	private ArrayAdapter<Expense> adapter;
	private ListView oldClaimsList;
	
	public ArrayList<Expense> getClaims(){
		return claims;
	}
	

	public ArrayAdapter<Expense> getAdapter() {
		return adapter;
	}

	
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
		
		Button otherButton = (Button)findViewById(R.id.OthersButton);
		ButtonListener otherButtonListner = new ButtonListener();
		otherButton.setOnClickListener(otherButtonListner);
		
		oldClaimsList = (ListView)findViewById(R.id.evenListView);
		
		//http://www.ezzylearning.com/tutorial/handling-android-listview-onitemclick-event 1.26.2015.
		oldClaimsList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view  = (TextView) view;
                //String item = claims.get(position) + " has been removed";//.getContext().toString();
                
				claims.remove(position);
				getAdapter().notifyDataSetChanged();
				
				saveInFile(null, new Date(System.currentTimeMillis()));
				
				//Toast.makeText(getBaseContext(), item, 1).show();
                //Toast.cancel();
				//Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();
                
            }
        });
        
        
        
        
	}
	
	
	/*
	abstract class ListListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id){
			claims.add("on list!");
			getAdapter().notifyDataSetChanged();
			saveInFile("on list!", new Date(System.currentTimeMillis()));
		}
	}*/
	
	
	
	
	
	class ButtonListener implements OnClickListener{
		@Override
		public void onClick (View view){
			if(view.getId()==R.id.AddButton){
				Intent intent = new Intent(StartUpActivity.this,AddActivity.class);
				startActivity(intent);
			} else if (view.getId()==R.id.EditButton){
				//
				
				Expense temp = new Expense("" + claims.size() + "Entered!", 100, "CAD", new Date(System.currentTimeMillis()));
				//temp. "" + claims.size() + "Entered!";
				claims.add(0,temp);
				getAdapter().notifyDataSetChanged();
				
				saveInFile(temp.toString(), new Date(System.currentTimeMillis()));
			} else if (view.getId()==R.id.OthersButton){
				claims.clear();
				getAdapter().notifyDataSetChanged();
				
				saveInFile(null, new Date(System.currentTimeMillis()));
			}
		}
	}
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		//claims = loadFromFile();
		
		adapter = new ExpenseAdapter(this,R.layout.list_claim, claims);
		//adapter = LayoutInflater.from(getContext()).inflate(R.layout.list_claim, parent, false);
		
		oldClaimsList.setAdapter(adapter);
	}
	
	public class ExpenseAdapter extends ArrayAdapter<Expense> {
		public ExpenseAdapter(Context context, int resource,
				List<Expense> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
		}

		/*
		public ClaimListAdapter(Context context, ArrayList<Claim> ClaimList) {
		       super(context, 0, ClaimList);

	  	}*/

		@Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	       // Get the data item for this position
			Expense expense = getItem(position);    
	       // Check if an existing view is being reused, otherwise inflate the view
	       if (convertView == null) {
	          convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_claim, parent, false);
	       }
	       // Lookup view for data population
	       TextView eText = (TextView) convertView.findViewById(R.id.evenListElementView);
	       //TextView TextViewClaimName = (TextView) convertView.findViewById(R.id.TextViewClaimName);
	       //TextView TextViewClaimDate = (TextView) convertView.findViewById(R.id.TextViewClaimDate);
	       //TextView TextViewClaimStatus = (TextView) convertView.findViewById(R.id.TextViewClaimStatus);
	       
	       
	       // Populate the data into the template view using the data object
	       //TextViewClaimName.setText(expense.getClaimName());
	       // test: display date
	       //Calendar date = claim.getStartDate();
	       //TextViewClaimDate.setText(String.format("%1$tA %1$tb %1$td %1$tY", date));
	       eText.setText(expense.toString());
	       //TextViewClaimStatus.setText(expense.getStatus());
	       // Return the completed view to render on screen
	       return convertView;
	   }
		
	}
	
	
	
	
	
	private ArrayList<Expense> loadFromFile() {
		
		Gson gson = new Gson();
		
		ArrayList<Expense> tweets = new ArrayList<Expense>();
		try {
			FileInputStream fis = openFileInput(FILENAME);
			
			Type listType = new TypeToken<ArrayList<Expense>>() {}.getType();
			InputStreamReader isr = new InputStreamReader(fis);
			tweets = gson.fromJson(isr, listType);
			fis.close();
			
			/*
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			String line = in.readLine();
			while (line != null) {
				tweets.add(line);
				line = in.readLine();
			}*/

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(tweets==null){
			tweets = new ArrayList<Expense>();
		}
		
		return tweets;
	}
	
	
	void saveInFile(String text, Date date) {
		
		Gson gson = new Gson();
		
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					0/*Context.MODE_APPEND*/);
			
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(claims,osw);
			
			/*fos.write(new String(date.toString() + " | " + text)
					.getBytes());*/
			osw.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
