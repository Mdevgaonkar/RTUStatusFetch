package hemabh.mayur.com.smsuser;

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
        
        ac_temp_edit=(EditText) findViewById(R.id.ac_temp_edit);
        
        
        ac_auto.setOnClickListener(this);
        ac_manual.setOnClickListener(this);
        ac_enable.setOnClickListener(this);
        ac_disable.setOnClickListener(this);
        ac_on.setOnClickListener(this);
        ac_off.setOnClickListener(this);
        set_temp.setOnClickListener(this);
        
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.ac_auto:
			
			assuranceAlert("AC mode auto","*171#");
			
			break;
		case R.id.ac_manual:
			assuranceAlert("AC mode manual","*172#");
			
			break;
		case R.id.ac_enable:
			assuranceAlert("AC 1 ON","*1910#");
			
			break;
		case R.id.ac_disable:
			assuranceAlert("AC 1 OFF","*1911#");
			
			break;
		case R.id.ac_on:
			assuranceAlert("AC 2 ON","*1921#");

			
			break;
		case R.id.ac_off:
			assuranceAlert("AC 2 OFF","*1920#");

			
			break;
		case R.id.set_temp:
			if (!ac_temp_edit.getText().toString().matches("")) {
				int temp = Integer.parseInt(ac_temp_edit.getText().toString());
				if (temp >= 20 && temp <= 25)
					sendSMS("*28" + ac_temp_edit.getText().toString() + "#");
				else
					Toast.makeText(getApplicationContext(), "Please enter a valid temperature value", Toast.LENGTH_LONG).show();
			}
			else Toast.makeText(getApplicationContext(), "Please enter a valid temperature value", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
		
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


	public void assuranceAlert(String display_msg, final String sms){


		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle(display_msg);
		alert.setMessage("Are you sure ?");

		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {


				sendSMS(sms);
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		alert.show();



	}


}
