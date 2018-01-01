package com.mayur.smsalert2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class command_Signage extends ActionBarActivity implements OnClickListener {
	
	Button signage_auto, signage_manual, set_on_off_time, on_button, off_button;
	EditText on_time_edit, off_time_edit;
	String memberPhone;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signage_control);
        
        Intent phoneIntent=getIntent();
        memberPhone=phoneIntent.getStringExtra("member_phone");
        

        signage_auto=(Button) findViewById(R.id.signage_auto);
        signage_manual=(Button) findViewById(R.id.signage_manual);
        set_on_off_time=(Button) findViewById(R.id.set_on_off_time);
        on_button=(Button) findViewById(R.id.on_button);
        off_button=(Button) findViewById(R.id.off_button);
        
        on_time_edit=(EditText) findViewById(R.id.on_time_edit);
        off_time_edit=(EditText) findViewById(R.id.off_time_edit);
        
        signage_auto.setOnClickListener(this);
        signage_manual.setOnClickListener(this);
        set_on_off_time.setOnClickListener(this);
        on_button.setOnClickListener(this);
        off_button.setOnClickListener(this);

	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.signage_auto:
			sendSMSmsg("*241#");
			
			break;
		case R.id.signage_manual:
			sendSMSmsg("*241#");
			
			break;
		case R.id.set_on_off_time:
			sendSMSmsg("*25"+on_time_edit.getText().toString()+","+off_time_edit.getText().toString()+"#");
			
			break;
		case R.id.on_button:
			sendSMSmsg("*261#");
			
			break;
		case R.id.off_button:
			sendSMSmsg("*260#");
			
			break;
			

		default:
			break;
		}
		
	}
	
	
	protected void sendSMSmsg(String msg) {
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
