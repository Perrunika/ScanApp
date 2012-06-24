package com.ScanAppv2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ScanAppv2Activity extends Activity {
	
	private Button confirm;
	
	
    /** Called when the activity is first created. 
	Used to perform all initialization and UI setup */
	@Override
	public void onCreate(Bundle savedInstanceState) {    
		super.onCreate(savedInstanceState);    
		setContentView(R.layout.main);  
		confirm = (Button) this.findViewById(R.id.button1); 
		
		//creating a drop down menu with the companies names
		final Spinner s = (Spinner) findViewById(R.id.spinner);   
		@SuppressWarnings("rawtypes")
		ArrayAdapter adapter = ArrayAdapter.createFromResource(            
				this, R.array.databases, android.R.layout.simple_spinner_item);    
		
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);    
		s.setAdapter(adapter);		
		
		confirm.setOnClickListener(new View.OnClickListener() {
			
			
	/** Called when a view has been clicked. */		
	public void onClick(View v) {
		
		//Starting new activity			 
		Intent myIntent = new Intent(ScanAppv2Activity.this, NextActivity.class);
		//getting the selected item from the drop down menu
		Object o = s.getSelectedItem();
		String text = o.toString();
		//Create bundle used for passing data between the Activities		
		Bundle bundle = new Bundle();
		//passing the company name from scanappactivity to nextactivity
		bundle.putString("CompanyName", text);		        
				
		myIntent.putExtras(bundle); 
		     ScanAppv2Activity.this.startActivity(myIntent);

		}
     	});	
				                                                                                    
		}	

	
}