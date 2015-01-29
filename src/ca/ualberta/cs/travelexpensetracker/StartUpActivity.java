package ca.ualberta.cs.travelexpensetracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class StartUpActivity extends Activity {
	private static final String FILENAME = "claimdata.sav";
	private ArrayList<Expense> claims;
	private ArrayList<String> expenseList;
	//private ArrayAdapter<String> adapter;
	//private ArrayAdapter<Expense> adapter;
	private ExpenseAdapter adapter;
	private ListView oldClaimsList;
	

	
	public ArrayList<Expense> getClaims(){
		return claims;
	}
	
	public ArrayList<String> getExpenseList(){
		return expenseList;
	}
	
	//public ArrayAdapter<String> getAdapter() {
	public ArrayAdapter<Expense> getAdapter() {
		return adapter;
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_up);
		
		Button addButton = (Button)findViewById(R.id.AddButton);
		ButtonListener addButtonListner = new ButtonListener();
		addButton.setOnClickListener((android.view.View.OnClickListener) addButtonListner);
		
		Button editButton = (Button)findViewById(R.id.EditButton);
		ButtonListener editButtonListner = new ButtonListener();
		editButton.setOnClickListener((android.view.View.OnClickListener) editButtonListner);
		
		Button otherButton = (Button)findViewById(R.id.OthersButton);
		ButtonListener otherButtonListner = new ButtonListener();
		otherButton.setOnClickListener((android.view.View.OnClickListener) otherButtonListner);
		
		oldClaimsList = (ListView)findViewById(R.id.evenListView);
		//System.out.println(oldClaimsList);
		
		//http://www.ezzylearning.com/tutorial/handling-android-listview-onitemclick-event 1.26.2015.
		oldClaimsList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view  = (TextView) view;
                //String item = claims.get(position) + " has been removed";//.getContext().toString();
                
            	
            	Expense forDetailExpense = claims.get(position);
            	
            	// open an info dialog
            	AlertDialog.Builder adb = new AlertDialog.Builder(StartUpActivity.this);
				adb.setMessage("Item: "+forDetailExpense.getItem()+"\n"
						+" "+forDetailExpense.getCurrency()+" "+forDetailExpense.getAmount()+"\n"
						+"Date:  "+forDetailExpense.getDate().toString()+"\n"+
						"Category: "+forDetailExpense.getCategory()+"\n"+
						"Description: "+forDetailExpense.getDescription());
				adb.setCancelable(true);
				
				adb.setPositiveButton("Edit", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {

				}});
				adb.show();
				
				adapter.notifyDataSetChanged();
				
				//saveInFile(null, new Date(System.currentTimeMillis()));
				
				//Toast.makeText(getBaseContext(), item, 1).show();
                //Toast.cancel();
				//Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();
                
            }
		});
		
		
		
		//https://github.com/jeremykid/Weijie2_Travel-expense-tracking/blob/master/src/ca/ualberta/cs/travel/MainActivity.java 2015.1.29.
		oldClaimsList.setOnItemLongClickListener(new OnItemLongClickListener(){
            @Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            	final int deleteIndex = position;
            	
				AlertDialog.Builder adb = new AlertDialog.Builder(StartUpActivity.this);
				adb.setMessage("Delete "+"\""+claims.get(position).getItem()+"\""+"?");
				adb.setCancelable(true);
				
				adb.setPositiveButton("Delete", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
								claims.remove(deleteIndex);
								adapter.notifyDataSetChanged();
								saveInFile(null,new Date(System.currentTimeMillis()));
				}
				});
				adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
					@Override
						public void onClick(DialogInterface dialog, int which) {
					}
				});
				//Toast.makeText(ListStudentsActivity.this, "Is the on click working?", Toast.LENGTH_SHORT).show();
				adb.show();

				return true;
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
	
	
	
	
	
	class ButtonListener implements View.OnClickListener{
		@Override
		public void onClick (View view){
			if(view.getId()==R.id.AddButton){
				Intent intent = new Intent(StartUpActivity.this,AddActivity.class);
				startActivity(intent);
			} else if (view.getId()==R.id.EditButton){
				//
				
				Expense temp = new Expense();//
				
				temp.setItem("" + claims.size() + "Entered!");
				temp.setAmount( (float) 100.0);
				temp.setCurrency("CAD $");
				temp.setDate(new Date(System.currentTimeMillis()));

				claims.add(0,temp);
				//expenseList.add(0,temp.toString());
				adapter.notifyDataSetChanged();
				
				saveInFile(temp, new Date(System.currentTimeMillis()));
			} else if (view.getId()==R.id.OthersButton){
				claims.clear();
				//expenseList.clear();
				adapter.notifyDataSetChanged();
				
				saveInFile(null, new Date(System.currentTimeMillis()));
			}
		}
	}
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		//expenseList = new ArrayList<String>();
		//expenseList = loadFromFile();
		claims = loadFromFile();
		
		//adapter = new ArrayAdapter<String>(this,R.layout.list_claim, expenseList);
		//adapter = new ArrayAdapter<Expense>(this,R.layout.list_claim, claims);
		adapter = new ExpenseAdapter(this,R.layout.list_claim, claims);

		//adapter = LayoutInflater.from(getContext()).inflate(R.layout.list_claim, parent, false);
		
		oldClaimsList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
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
	          convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_expense, parent, false);
	       }
	       // Lookup view for data population
	       TextView eventName = (TextView) convertView.findViewById(R.id.eventListElementNameView);
	       TextView eventAmount = (TextView) convertView.findViewById(R.id.eventListElementAmountView);
	       TextView eventGap = (TextView) convertView.findViewById(R.id.eventListElementGapView);
	       TextView eventDate = (TextView) convertView.findViewById(R.id.eventListElementDateView);
	       TextView eventYear = (TextView) convertView.findViewById(R.id.eventListElementYearView);
	       //TextView TextViewClaimStatus = (TextView) convertView.findViewById(R.id.TextViewClaimStatus);
	       
	       
	       eventDate.setText(adapterDateInFormat(expense.getDate()));
	       eventYear.setText(""+((expense.getDate()).getYear()+1900));

	       // Populate the data into the template view using the data object
	       eventName.setText(expense.getItem());
	       // test: display date
	       //Calendar date = claim.getStartDate();
	       //TextViewClaimDate.setText(String.format("%1$tA %1$tb %1$td %1$tY", date));
	       
	       eventAmount.setText(""+expense.getAmount());
	       eventGap.setText(" "+expense.getCurrency());
	       
	       
	       //TextViewClaimStatus.setText(expense.getStatus());
	       // Return the completed view to render on screen
	       return convertView;
	   }
		
	}
	
	public String adapterDateInFormat(Date date){
		String formatDate = "";
		formatDate += ""+new DateFormatSymbols().getShortMonths()[date.getMonth()];
		formatDate += " "+date.getDate();
		//formatDate += ",\n        "+(date.getYear()+1900);
		return formatDate;
	}
	
	
	
	//private ArrayList<String> loadFromFile() {
	private ArrayList<Expense> loadFromFile() {	
		Gson gson = new Gson();
		
		//ArrayList<String> tweets = new ArrayList<String>();
		ArrayList<Expense> tweets = new ArrayList<Expense>();
		try {
			FileInputStream fis = openFileInput(FILENAME);
			
			//Type listType = new TypeToken<ArrayList<String>>() {}.getType();
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
			//tweets = new ArrayList<String>();
			tweets = new ArrayList<Expense>();
		}
		
		return tweets;
	}
	
	
	//void saveInFile(String text, Date date) {
	void saveInFile(Expense text, Date date) {
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
