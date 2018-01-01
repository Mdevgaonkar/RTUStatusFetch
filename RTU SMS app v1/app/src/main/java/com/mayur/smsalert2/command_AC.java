package com.mayur.smsalert2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class command_AC extends ActionBarActivity implements OnClickListener {

	Button ac_auto,ac_manual,ac_enable,ac_disable,ac_on,ac_off,set_temp,ac_single,ac_dual;
	String memberPhone;
	EditText ac_temp_edit;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_control);
        
        
        Intent phoneIntent=getIntent();
        memberPhone=phoneIntent.getStringExtra("member_phone");
    
        ac_auto=(Button) findViewById(R.id.ac_auto);
        ac_manual=(Button) findViewById(R.id.ac_manual);
        ac_enable=(Button) findViewById(R.id.ac_enable);
        ac_disable=(Button) findViewById(R.id.ac_disable);
        ac_on=(Button) findViewById(R.id.ac_on);
        ac_off=(Button) findViewById(R.id.ac_off);
        set_temp=(Button) findViewById(R.id.set_temp);
        ac_single=(Button) findViewById(R.id.ac_single);
        ac_dual=(Button) findViewById(R.id.ac_dual);
        
        ac_temp_edit=(EditText) findViewById(R.id.ac_temp_edit);
        
        
        ac_auto.setOnClickListener(this);
        ac_manual.setOnClickListener(this);
        ac_enable.setOnClickListener(this);
        ac_disable.setOnClickListener(this);
        ac_on.setOnClickListener(this);
        ac_off.setOnClickListener(this);
        set_temp.setOnClickListener(this);
        ac_single.setOnClickListener(this);
        ac_dual.setOnClickListener(this);
        
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.ac_auto:
			
			sendSMS("*171#");
			
			break;
		case R.id.ac_manual:
			sendSMS("*172#");
			
			break;
		case R.id.ac_enable:
			sendSMS("*181#");
			
			break;
		case R.id.ac_disable:
			sendSMS("*182#");
			
			break;
		case R.id.ac_on:
			
			select_AC("ON", "1");
			
			break;
		case R.id.ac_off:
			
			select_AC("OFF", "0");
			
			break;
		case R.id.set_temp:
			
			sendSMS("*28"+ac_temp_edit.getText().toString()+"#");
			
			break;
		case R.id.ac_single:
			sendSMS("*321#");
			
			break;
		case R.id.ac_dual:
			sendSMS("*322#");
			
			break;
			

		default:
			break;
		}
		
	}
	
	public void select_AC(String ac_on_off, String on_off) {
		
		final String on_or_off=on_off;
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Switch "+ac_on_off+" AC");
		alert.setMessage("Enter AC number");

		LinearLayout layout_AC =new LinearLayout(this);
		layout_AC.setOrientation(LinearLayout.VERTICAL);
		
		// Set an EditText view to get user input 
		final EditText input_AC = new EditText(this);
		input_AC.setHint("e.g. 1 (only numeric)");
		input_AC.setInputType(InputType.TYPE_CLASS_NUMBER);
		layout_AC.addView(input_AC);
		
		alert.setView(layout_AC);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  String value = "*19"+input_AC.getText().toString()+on_or_off+"#";
		  
		  
		  sendSMS(value);
		  
		  }
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
		
	}
	
	protected void sendSMS(String msg) {
	      Log.i("Send SMS", "");
	      try {
	         SmsManager smsManager = SmsManager.getDefault();
	         smsManager.sendTextMessage(memberPhone, null, msg, null, null);
	         Toast.makeText(getApplicationContext(), "SMS sent.",
	         Toast.LENGTH_LONG).show();
	      } catch (Exception e) {
	         Toast.makeText(getApplicationContext(),
	         "SMS faild, please try again.",
	         Toast.LENGTH_LONG).show();
	         e.printStackTrace();
	      }
	   }


}
