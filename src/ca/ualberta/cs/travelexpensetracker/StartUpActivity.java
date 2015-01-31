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
import android.support.v4.app.ShareCompat.IntentBuilder;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class StartUpActivity extends Activity {
	private static final String FILENAME = "claimdata.sav";
	private ArrayList<Claim> forSavingClaimList;
	protected static int forSavingClaimListPosition;
	
	private static ArrayList<Expense> claims;
	private ArrayList<String> expenseList;
	//private ArrayAdapter<String> adapter;
	//private ArrayAdapter<Expense> adapter;
	private ExpenseAdapter adapter;
	private ListView oldClaimsList;
	private ListView totalMoneyListView;
	private ArrayAdapter<Currency> totalAdapter;
	
	protected static Expense forEditExpense;
	protected static int forEditExpensePosition;
	
	private ArrayList<Currency> total;
	
	private Claim openClaim;
	

	
	
	
	public ArrayList<Claim> getForSavingClaimList(){
		return forSavingClaimList;
	}
	
	
	public static int getForEditExpensePosition(){
		return forEditExpensePosition;
	}
	
	public void setForEditExpensePosition(int position){
		StartUpActivity.forEditExpensePosition = position;
	}
	
	public static Expense getForEditExpense() {
		return forEditExpense;
	}

	public void setForEditExpense(Expense forEditExpense) {
		Expense newExpense = new Expense();
		newExpense = forEditExpense;
		StartUpActivity.forEditExpense = newExpense;
	}
	
	
	public static ArrayList<Expense> getClaims(){
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
		
		/*
		Button editButton = (Button)findViewById(R.id.EditButton);
		ButtonListener editButtonListner = new ButtonListener();
		editButton.setOnClickListener((android.view.View.OnClickListener) editButtonListner);
		
		Button otherButton = (Button)findViewById(R.id.OthersButton);
		ButtonListener otherButtonListner = new ButtonListener();
		otherButton.setOnClickListener((android.view.View.OnClickListener) otherButtonListner);
		*/
		
		
		oldClaimsList = (ListView)findViewById(R.id.evenListView);
		totalMoneyListView = (ListView) findViewById(R.id.totalMoneyListView);
		
		//System.out.println(oldClaimsList);
		
		//http://www.ezzylearning.com/tutorial/handling-android-listview-onitemclick-event 1.26.2015.
		oldClaimsList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view  = (TextView) view;
                //String item = claims.get(position) + " has been removed";//.getContext().toString();
            	final int deleteIndex = position;
            	setForEditExpense(claims.get(deleteIndex));
            	Expense forDetailExpense = claims.get(position);
            	
            	// open an info dialog
            	AlertDialog.Builder adb = new AlertDialog.Builder(StartUpActivity.this);
            	
            	//http://stackoverflow.com/questions/13341560/how-to-create-a-custom-dialog-box-in-android 2015.1.30.
            	LayoutInflater factory = LayoutInflater.from(StartUpActivity.this);
        		View expenseInfoDialogView = factory.inflate(R.layout.expense_dialog, null);
            	adb.setView(expenseInfoDialogView);
            	
            	// set title
            	adb.setMessage("        Expense Details");
            			
            	//set up the original info
            	TextView expenseDialogItemInfo=(TextView) expenseInfoDialogView.findViewById(R.id.expenseDialogItemInfo);
            	TextView expenseDialogAmountInfo=(TextView) expenseInfoDialogView.findViewById(R.id.expenseDialogAmountInfo);
            	TextView expenseDialogDateInfo=(TextView) expenseInfoDialogView.findViewById(R.id.expenseDialogDateInfo);
            	TextView expenseDialogCategoryInfo=(TextView) expenseInfoDialogView.findViewById(R.id.expenseDialogCategoryInfo);
            	TextView expenseDialogDecriptionInfo=(TextView)  expenseInfoDialogView.findViewById(R.id.expenseDialogDecriptionInfo);
            	
            	/*
            	System.out.println(forDetailExpense.getItem());
            	System.out.println(expenseDialogItemInfo);*/
            	
            	expenseDialogItemInfo.setText(forDetailExpense.getItem());
            	expenseDialogAmountInfo.setText(forDetailExpense.getCurrency()+" "+forDetailExpense.getAmount());
            	expenseDialogDateInfo.setText(forDetailExpense.getDate().toString());
            	expenseDialogCategoryInfo.setText(forDetailExpense.getCategory());
            	expenseDialogDecriptionInfo.setText(forDetailExpense.getDescription());
            	/*
				adb.setMessage("Item: "+forDetailExpense.getItem()+"\n\n"
						+" "+forDetailExpense.getCurrency()+" "+forDetailExpense.getAmount()+"\n\n"
						+"Date:  "+forDetailExpense.getDate().toString()+"\n\n"+
						"Category: "+forDetailExpense.getCategory()+"\n\n"+
						"Description: "+forDetailExpense.getDescription());*/
            	
            	//adb.setTitle("Expense Details");
				adb.setCancelable(true);
				
				adb.setPositiveButton("Edit", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Intent intent = new Intent(StartUpActivity.this,AddActivity.class);
						
						//System.out.println(claims.get(deleteIndex));
						
						setForEditExpense(claims.get(deleteIndex));
						setForEditExpensePosition(deleteIndex);
						//System.out.println(getForEditExpense());
						
						startActivity(intent);
				}});
				adb.show();
				
				
				adapter.notifyDataSetChanged();
				
				//saveInFile(null, new Date(System.currentTimeMillis()));
				
				//Toast.makeText(getBaseContext(), item, 1).show();
                //Toast.cancel();
				//Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();
				//System.out.println(getForEditExpense());
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
				setForEditExpense(null);
				Intent intent = new Intent(StartUpActivity.this,AddActivity.class);
				startActivity(intent);
			} /*else if (view.getId()==R.id.EditButton){
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
			}*/
		}
	}
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		//expenseList = new ArrayList<String>();
		//expenseList = loadFromFile();
		//claims = loadFromFile();
		forSavingClaimList = loadFromFile();
		
		//testing
		Claim testing = new Claim();
		testing.setExpenseList(new ArrayList<Expense>());
		Currency testCurrency = new Currency();
		ArrayList<Currency> testCurrencyList = new ArrayList<Currency>();
		testCurrencyList.add(testCurrency);
		testing.setTotalCurrency(testCurrencyList);
		forSavingClaimList.add(testing);
		//!!!!!!!!!!!!
		
		forSavingClaimListPosition = 0;
		openClaim = forSavingClaimList.get(forSavingClaimListPosition);
		claims = openClaim.getExpenseList();
		
		
		//adapter = new ArrayAdapter<String>(this,R.layout.list_claim, expenseList);
		//adapter = new ArrayAdapter<Expense>(this,R.layout.list_claim, claims);
		adapter = new ExpenseAdapter(this,R.layout.list_expense, claims);

		//adapter = LayoutInflater.from(getContext()).inflate(R.layout.list_claim, parent, false);
		
		oldClaimsList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		// total currency
		total = openClaim.getTotalCurrency();//new ArrayList<Currency>();
		
		totalAdapter = new ArrayAdapter<Currency>(this,R.layout.list_tatol, total);
		
		//System.out.println(totalMoneyListView);
		//System.out.println(totalAdapter);
		totalMoneyListView.setAdapter(totalAdapter);
		
		//total.add(new Currency("CAD $",200));
		total.add(new Currency("USD $",200));
		totalAdapter.notifyDataSetChanged();
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
	//private ArrayList<Expense> loadFromFile() {	
	private ArrayList<Claim> loadFromFile() {	
		Gson gson = new Gson();
		
		//ArrayList<String> tweets = new ArrayList<String>();
		//ArrayList<Expense> tweets = new ArrayList<Expense>();
		ArrayList<Claim> tweets = new ArrayList<Claim>();
		try {
			FileInputStream fis = openFileInput(FILENAME);
			
			//Type listType = new TypeToken<ArrayList<String>>() {}.getType();
			//Type listType = new TypeToken<ArrayList<Expense>>() {}.getType();
			Type listType = new TypeToken<ArrayList<Claim>>() {}.getType();
			
			InputStreamReader isr = new InputStreamReader(fis);
			tweets = gson.fromJson(isr, listType);
			fis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(tweets==null){
			//tweets = new ArrayList<String>();
			//tweets = new ArrayList<Expense>();
			tweets = new ArrayList<Claim>();
		}
		
		return tweets;
	}
	
	
	//void saveInFile(String text, Date date) {
	//void saveInFile(Expense text, Date date) {
	void saveInFile(Claim text, Date date) {
		Gson gson = new Gson();
		
		try {
			FileOutputStream fos = openFileOutput(FILENAME,0/*Context.MODE_APPEND*/);
			
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			//gson.toJson(claims,osw);
			gson.toJson(forSavingClaimList,osw);


			osw.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
