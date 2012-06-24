package com.ScanAppv2;

/**
 * In order to use Web services the application needs special permission.
 * Permissions are stored in AndroidManifest.xml file. For this mobile application, the following line is added:
 * <uses-permission android:name="android.permission.INTERNET"></uses-permission>
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

//Include the class library for ksoap2
//provides a lightweight and efficient SOAP(Simple Object Access Protocol) client library for the Android platform
import org.ksoap2.*;
import org.ksoap2.serialization.*;
import org.ksoap2.transport.*;


public class NextActivity extends Activity {
	
	
	//the parameters used in SOAP calls are initialized as follows: 
	private static final String NAMESPACE = "http://tempuri.org/"; // Web service namespace
	private static final String URL = "http://10.135.1.154/WebService5/Service1.asmx"; //Web service URL
	private static final String PalletInformation_SOAP_ACTION = "http://tempuri.org/GetPalletDescription"; 
	private static final String METHOD_NAME3 = "GetPalletDescription"; //Web service method name
		
	private TextView tDescription;
	private String palletInformation;	
	private String db;

	private String content = null;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        
        setContentView(R.layout.secondary_activity);
		tDescription = new TextView(this);		
		tDescription = (TextView)findViewById(R.id.textView2);
		
		//First Extract the bundle from intent
		Bundle bundle = getIntent().getExtras();		 

		//Next extract the values using the key as
	    db = bundle.getString("CompanyName");
	
	    //Start the Bar Scanner App already installed
	    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
	    //calling the method when we want a result to be returned from the child activity  
	    startActivityForResult(intent, 0);		
				 
	}
	
	//the current activity overrides the method in order to be able to get the data and act upon it
	 @Override
	 public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		 if (requestCode == 0) {
			 if (resultCode == RESULT_OK) {
				 content = intent.getStringExtra("SCAN_RESULT");	       
		    	 tDescription.setText(GetPalletDescription(db, content));
		     } else if (resultCode == RESULT_CANCELED) {
		    	 showDialog(0, "Failed");
		     }
		 }
	 }	  	
	
	//Add methods to call the web service methods and retrieve the results
	public String GetPalletDescription(String db, String pn) {

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME3);
		// Add the input required by web service
		request.addProperty("databaseName", db);
		request.addProperty("PalletNo", pn);		
			
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		//The HttpTransportSE class is used to make the actual call of the Web service method
		//The URL being passed as a parameter
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Make the soap call.
			androidHttpTransport.call(PalletInformation_SOAP_ACTION, envelope);
			// Get the SoapResult from the envelope body.
			SoapObject response = (SoapObject)envelope.bodyIn;				
			palletInformation = response.getProperty(0).toString();
				
		} catch (Exception e) {
			e.getMessage();
			e.getStackTrace();
		}

			return palletInformation;
	}
	
	 	 
	 private void showDialog(int title, CharSequence message) {
		    AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setTitle(title);
		    builder.setMessage(message);
		    builder.setPositiveButton("OK", null);
		    builder.show();
		  }

	
	
}
