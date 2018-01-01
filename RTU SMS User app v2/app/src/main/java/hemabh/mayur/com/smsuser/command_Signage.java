package hemabh.mayur.com.smsuser;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class command_Signage extends ActionBarActivity implements OnClickListener {
	
	Button signage_auto, signage_manual, set_on_off_time, on_button, off_button, on_time_button, off_time_button;
	//EditText on_time_edit, off_time_edit;
	String memberPhone;
	Calendar current_Time = Calendar.getInstance();
	int hour = current_Time.get(Calendar.HOUR_OF_DAY);
	int minute = current_Time.get(Calendar.MINUTE);
	TimePickerDialog Time_Picker;


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
		on_time_button=(Button) findViewById(R.id.on_time_btn);
		off_time_button=(Button) findViewById(R.id.off_time_btn);
        
        signage_auto.setOnClickListener(this);
        signage_manual.setOnClickListener(this);
        set_on_off_time.setOnClickListener(this);
        on_button.setOnClickListener(this);
        off_button.setOnClickListener(this);
		on_time_button.setOnClickListener(this);
		off_time_button.setOnClickListener(this);

	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.signage_auto:
			assuranceAlert("Signage Auto","*241#");
			
			break;
		case R.id.signage_manual:
			assuranceAlert("Signage Manual","*242#");
			
			break;
		case R.id.set_on_off_time:
			if (!(on_time_button.getText().toString().matches("") || off_time_button.getText().toString().matches("") || on_time_button.getText().toString().matches("On Time") || off_time_button.getText().toString().matches("Off Time"))) {
				assuranceAlert("Set ON-OFF Time","*25" + on_time_button.getText().toString() + "," + off_time_button.getText().toString() + "#");
			}
			else Toast.makeText(getApplicationContext(), "Please set a valid time", Toast.LENGTH_LONG).show();
			break;
		case R.id.on_button:
			assuranceAlert("Signage ON","*261#");
			
			break;
		case R.id.off_button:
			assuranceAlert("Signage OFF","*260#");
			
			break;
		case R.id.on_time_btn:

			Time_Picker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
					on_time_button.setText( selectedHour + ":" + selectedMinute);
				}
			}, hour, minute, true);//Yes 24 hour time
			Time_Picker.setTitle("Select ON Time");
			Time_Picker.show();

				break;
		case R.id.off_time_btn:

			Time_Picker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
					off_time_button.setText( selectedHour + ":" + selectedMinute);
				}
			}, hour, minute, true);//Yes 24 hour time
			Time_Picker.setTitle("Select OFF Time");
			Time_Picker.show();

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
	         Toast.makeText(getApplicationContext(), "SMS sent.->"+msg,
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


				sendSMSmsg(sms);
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
