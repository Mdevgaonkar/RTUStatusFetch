package hemabh.mayur.com.smsuser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ViewContact extends ActionBarActivity implements OnItemClickListener {
	
	
	TextView name_tv,location_tv, site_id_tv;
	SQLcontroller dbcon;
	long mem_id;
	String memberName, memberPhone, memberLocation, memberSiteID, memberID;
	
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_member);
        
        dbcon=new SQLcontroller(this);
        dbcon.open();
        
        
        
        name_tv=(TextView) findViewById(R.id.name_view);
        location_tv=(TextView) findViewById(R.id.location_view);
		site_id_tv=(TextView) findViewById(R.id.site_id_view);
        
        
        Intent viewIntent=getIntent();
        Bundle b = viewIntent.getExtras();
        
        memberID = b.getString("memberID");
		memberName = b.getString("memberName");
		memberPhone = b.getString("memberPhone");
		memberLocation=b.getString("memberLocation");
		memberSiteID=b.getString("memberSiteID");

        
		mem_id= Long.parseLong(memberID);
		
		
		
		name_tv.setText(memberName);
		location_tv.setText(memberLocation);
		site_id_tv.setText(memberSiteID);
		
		
		
ListView commandListView=(ListView) findViewById(R.id.Command_list);
		
		String[] commands = getResources().getStringArray(R.array.commands);
		
		ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.row_command, commands);
			      
			      
			      commandListView.setAdapter(adapter);
			      
			      commandListView.setOnItemClickListener(this);
        
	}
	
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.view_options, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	    	
	    	super.onOptionsItemSelected(item);
	    	switch(item.getItemId()) 
	        { 
	           case R.id.Edit_Contact: 
	        	   
	        	   String name=memberName;
	        	   String phone=memberPhone;
				   String location=memberLocation;
				   String site_ID=memberSiteID;
	        	   String id=memberID;
	        	   
	              Intent editIntent = new Intent(getApplicationContext(),hemabh.mayur.com.smsuser.editContact.class);
	              editIntent.putExtra("memberId", id);
	              editIntent.putExtra("memberName", name);
	              editIntent.putExtra("memberPhone", phone);
				   editIntent.putExtra("memberLocation", location);
				   editIntent.putExtra("memberSiteID", site_ID);
	              startActivity(editIntent);
	              return true;
	              
	           case R.id.Delete_Contact:

	        	   AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        	      builder.setMessage(R.string.deleteContact)
	        	     .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
	        	         public void onClick(DialogInterface dialog, int id) {
	        	        	 dbcon.deleteData(mem_id);
	        	            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
	        	            //this.returnHome();
	        	            Intent home_intent = new Intent(getApplicationContext(),
	        		   				MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

	        		   		startActivity(home_intent);

	        	         }
	        	      })
	        	     .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
	        	         public void onClick(DialogInterface dialog, int id) {
	        	            // User cancelled the dialog
	        	         }
	        	      });
	        	      AlertDialog d = builder.create();
	        	      d.setTitle("Delete Contact");
	        	      d.show();


		              return true;
	           default: 
	              return super.onOptionsItemSelected(item); 
	              

	         } 
	    }



		protected void sendSMSMessage(String msg) {
		      Log.i("Send SMS", "");
		      try {
		         SmsManager smsManager = SmsManager.getDefault();
		         smsManager.sendTextMessage(memberPhone, null, msg, null, null);
		         Toast.makeText(getApplicationContext(), "SMS sent. ->"+ msg,
						 Toast.LENGTH_LONG).show();
		      } catch (Exception e) {
		         Toast.makeText(getApplicationContext(),
						 "SMS faild, please try again.",
						 Toast.LENGTH_LONG).show();
		         e.printStackTrace();
		      }
		   }


		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			
			final AlertDialog.Builder alert = new AlertDialog.Builder(this);
			
			
			switch (position) {
			case 0:
				

				alert.setTitle("Hooter Controls");
				//alert.setMessage("Enter Site ID");

				LinearLayout Hooterlayout =new LinearLayout(this);
				Hooterlayout.setOrientation(LinearLayout.HORIZONTAL);
				Hooterlayout.setGravity(Gravity.CENTER_HORIZONTAL);

				final Button H_on=new Button(this);
				H_on.setText("ON");


				Hooterlayout.addView(H_on);

				final Button H_of=new Button(this);
				H_of.setText("OFF");

				Hooterlayout.addView(H_of);
				alert.setView(Hooterlayout);



				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.

					}
				});

				 final AlertDialog ad= alert.show();

				H_on.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						String Hoot_on = "*221#";
						sendSMSMessage(Hoot_on);
						ad.dismiss();
					}
				});

				H_of.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						String Hoot_off = "*220#";
						sendSMSMessage(Hoot_off);
						ad.dismiss();


					}
				});
			
				break;
			case 1:
				
				alert.setTitle("Snapshot controls");
				alert.setMessage("Are you sure to take a snapshot ?");

				alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  String value = "*23#";
				  // Do something with value!
				  sendSMSMessage(value);
				  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
				
				
				break; 	

			case 2:
				Intent intent_AC=new Intent(getApplicationContext(), command_AC.class);
				intent_AC.putExtra("member_phone", memberPhone);
				startActivity(intent_AC);

				break;
			
			case 3:
				Intent intent_Signage=new Intent(getApplicationContext(), command_Signage.class);
				intent_Signage.putExtra("member_phone", memberPhone);
				startActivity(intent_Signage);
				break;
			
			case 4:

				alert.setTitle("Smoke detector");
				alert.setMessage("Are you sure to reset smoke detector ?");

				alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String value = "*21#";
						// Do something with value!
						sendSMSMessage(value);
					}
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

				alert.show();
				
				break;
			case 5:
				alert.setTitle("Get Authorised Numbers");
				alert.setMessage("Are you sure ?");

				alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String value = "*04#";
						// Do something with value!
						sendSMSMessage(value);
					}
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

				alert.show();
	
				break;
			case 6:
				alert.setTitle("Get call number List");
				alert.setMessage("Are you sure ?");

				alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String value = "*31#";
						// Do something with value!
						sendSMSMessage(value);
					}
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

				alert.show();

				break;
			case 7:
				alert.setTitle("Get ATU Status");
				alert.setMessage("Are you sure ?");

				alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String value = "*34#";
						// Do something with value!
						sendSMSMessage(value);
					}
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

				alert.show();
				break;
			/*case 8:
				alert.setTitle("Set Data period");
				alert.setMessage("Enter Data period");

				LinearLayout layout_period =new LinearLayout(this);
				layout_period.setOrientation(LinearLayout.VERTICAL);
				
				// Set an EditText view to get user input 
				final EditText input_period = new EditText(this);
				input_period.setHint("e.g. 60 /only numeric");
				input_period.setInputType(InputType.TYPE_CLASS_PHONE);
				layout_period.addView(input_period);
				
				alert.setView(layout_period);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  String value = "*09"+input_period.getText().toString()+"#";
				  // Do something with value!
				  
				  sendSMSMessage(value);
				  
				  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
				break;
			case 9:
				
				alert.setTitle("SIM APN");
				alert.setMessage("Edit APN of SIM 1 and SIM 2");

				LinearLayout layout_sim_apn =new LinearLayout(this);
				layout_sim_apn.setOrientation(LinearLayout.VERTICAL);
				
				final TextView sim1_apn_text=new TextView(this);
				sim1_apn_text.setText("Enter SIM1 APN");
				layout_sim_apn.addView(sim1_apn_text);
				
				final EditText input_sim1_apn = new EditText(this);
				input_sim1_apn.setHint("e.g www (max 30 characters)");
				layout_sim_apn.addView(input_sim1_apn);
				
				final Button set_sim1_apn=new Button(this);
				set_sim1_apn.setText("Set SIM1 APN");
				set_sim1_apn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String sim1_apn="*10"+input_sim1_apn.getText().toString()+"#";
					sendSMSMessage(sim1_apn);
						
					}
				});
				layout_sim_apn.addView(set_sim1_apn);
				
				final TextView sim2_apn_text=new TextView(this);
				sim2_apn_text.setText("Enter SIM2 APN");
				layout_sim_apn.addView(sim2_apn_text);
				
				// Set an EditText view to get user input 
				final EditText input_sim2_apn = new EditText(this);
				input_sim2_apn.setHint("e.g www (max 30 characters)");
				layout_sim_apn.addView(input_sim2_apn);
				
				final Button set_sim2_apn=new Button(this);
				set_sim2_apn.setText("Set SIM2 APN");
				set_sim2_apn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String sim2_apn="*13"+input_sim2_apn.getText().toString()+"#";
					sendSMSMessage(sim2_apn);
						
					}
				});
				layout_sim_apn.addView(set_sim2_apn);
				
				
				
				alert.setView(layout_sim_apn);

				

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
				
				break;
			case 10:
				
				alert.setTitle("SIM user");
				alert.setMessage("Edit user of SIM 1 and SIM 2");

				LinearLayout layout_sim_user =new LinearLayout(this);
				layout_sim_user.setOrientation(LinearLayout.VERTICAL);
				
				final TextView sim1_user_text=new TextView(this);
				sim1_user_text.setText("Enter SIM1 user");
				layout_sim_user.addView(sim1_user_text);
				
				final EditText input_sim1_user = new EditText(this);
				input_sim1_user.setHint("e.g user1 (max 20 characters)");
				layout_sim_user.addView(input_sim1_user);
				
				final Button set_sim1_user=new Button(this);
				set_sim1_user.setText("Set SIM1 user");
				set_sim1_user.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String sim1_user="*11"+input_sim1_user.getText().toString()+"#";
					sendSMSMessage(sim1_user);
						
					}
				});
				layout_sim_user.addView(set_sim1_user);
				
				final TextView sim2_user_text=new TextView(this);
				sim2_user_text.setText("Enter SIM2 user");
				layout_sim_user.addView(sim2_user_text);
				
				// Set an EditText view to get user input 
				final EditText input_sim2_user = new EditText(this);
				input_sim2_user.setHint("e.g user2 (max 20 characters)");
				layout_sim_user.addView(input_sim2_user);
				
				final Button set_sim2_user=new Button(this);
				set_sim2_user.setText("Set SIM2 user");
				set_sim2_user.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String sim2_user="*14"+input_sim2_user.getText().toString()+"#";
					sendSMSMessage(sim2_user);
						
					}
				});
				layout_sim_user.addView(set_sim2_user);
				
				
				
				alert.setView(layout_sim_user);

				

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
				
				
				break;
			case 11:
				
				alert.setTitle("SIM Password");
				alert.setMessage("Edit password of SIM 1 and SIM 2");

				LinearLayout layout_sim_password =new LinearLayout(this);
				layout_sim_password.setOrientation(LinearLayout.VERTICAL);
				
				final TextView sim1_password_text=new TextView(this);
				sim1_password_text.setText("Enter SIM1 password");
				layout_sim_password.addView(sim1_password_text);
				
				final EditText input_sim1_password = new EditText(this);
				input_sim1_password.setHint("e.g password1 (max 20 characters)");
				layout_sim_password.addView(input_sim1_password);
				
				final Button set_sim1_password=new Button(this);
				set_sim1_password.setText("Set SIM1 password");
				set_sim1_password.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String sim1_password="*12"+input_sim1_password.getText().toString()+"#";
					sendSMSMessage(sim1_password);
						
					}
				});
				layout_sim_password.addView(set_sim1_password);
				
				final TextView sim2_password_text=new TextView(this);
				sim2_password_text.setText("Enter SIM2 password");
				layout_sim_password.addView(sim2_password_text);
				
				// Set an EditText view to get user input 
				final EditText input_sim2_password = new EditText(this);
				input_sim2_password.setHint("e.g password2 (max 20 characters)");
				layout_sim_password.addView(input_sim2_password);
				
				final Button set_sim2_password=new Button(this);
				set_sim2_password.setText("Set SIM2 password");
				set_sim2_password.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String sim2_password="*15"+input_sim2_password.getText().toString()+"#";
					sendSMSMessage(sim2_password);
						
					}
				});
				layout_sim_password.addView(set_sim2_password);
				
				
				
				alert.setView(layout_sim_password);

				

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
				
				break;
			case 12:
				alert.setTitle("SIM 2");
				alert.setMessage("Enable or Disable SIM2");

				LinearLayout layout_SIM2 =new LinearLayout(this);
				layout_SIM2.setOrientation(LinearLayout.VERTICAL);
				
				final Button SIM2_enable=new Button(this);
				SIM2_enable.setText("Enable SIM 2");
				SIM2_enable.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String enable_sim2="*161#";
					sendSMSMessage(enable_sim2);
						
					}
				});
				layout_SIM2.addView(SIM2_enable);
				
				final Button SIM2_disable=new Button(this);
				SIM2_disable.setText("Disable SIM 2");
				SIM2_disable.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String disable_sim2="*160#";
					sendSMSMessage(disable_sim2);
						
					}
				});
				layout_SIM2.addView(SIM2_disable);
				
				alert.setView(layout_SIM2);

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();

				
				break;
			case 13:
				
				Intent intent_new_AC=new Intent(getApplicationContext(), command_AC.class);
				intent_new_AC.putExtra("member_phone", memberPhone);
				startActivity(intent_new_AC);
	
				break;

			case 14:
				
				alert.setTitle("Energy Monitoring");
				alert.setMessage("Enable or Disable Energy Monitoring");

				LinearLayout layout_energy =new LinearLayout(this);
				layout_energy.setOrientation(LinearLayout.VERTICAL);
				
				final Button energy_enable=new Button(this);
				energy_enable.setText("Enable Energy Monitoring");
				energy_enable.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String enable_energy="*201#";
					sendSMSMessage(enable_energy);
						
					}
				});
				layout_energy.addView(energy_enable);
				
				final Button energy_disable=new Button(this);
				energy_disable.setText("Disable Energy Monitoring");
				energy_disable.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String disable_energy="*200#";
					sendSMSMessage(disable_energy);
						
					}
				});
				layout_energy.addView(energy_disable);
				
				alert.setView(layout_energy);

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();

				
				
				break;
			case 15:
				sendSMSMessage("*21#");
				
				
				break;
			case 16:
				
				sendSMSMessage("*22#");
	
				break;
			case 17:
				
				sendSMSMessage("*23#");
	
				break;
			case 18:
				Intent intent_new_Signage=new Intent(getApplicationContext(), command_Signage.class);
				intent_new_Signage.putExtra("member_phone", memberPhone);
				startActivity(intent_new_Signage);
				break;
			case 19:
				
				alert.setTitle("Call Center Numbers");
				alert.setMessage("Add Call Center Phone Numbers");

				LinearLayout layout_num_call =new LinearLayout(this);
				layout_num_call.setOrientation(LinearLayout.VERTICAL);
				
				final TextView input_num_ser_call=new TextView(this);
				input_num_ser_call.setText("Enter Serial Number");
				layout_num_call.addView(input_num_ser_call);
				
				final EditText input_ser_call = new EditText(this);
				input_ser_call.setHint("e.g 01 (01-05)");
				input_ser_call.setInputType(InputType.TYPE_CLASS_PHONE);
				layout_num_call.addView(input_ser_call);
				
				final TextView num_input_call=new TextView(this);
				num_input_call.setText("Enter Phone Number");
				layout_num_call.addView(num_input_call);
				
				// Set an EditText view to get user input 
				final EditText input_num_call = new EditText(this);
				input_num_call.setInputType(InputType.TYPE_CLASS_PHONE);
				input_num_call.setHint("e.g. +919898989898");
				
				layout_num_call.addView(input_num_call);
				
				alert.setView(layout_num_call);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  String value = "*29"+input_ser_call.getText().toString()+input_num_call.getText().toString()+"#";
				  // Do something with value!
				  sendSMSMessage(value);
				  
				  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
				
				break;
			case 20:
				

				alert.setTitle("Delete Numbers From Call Center Group");
				alert.setMessage("Click on the number to delete");

				LinearLayout layout_callcenter =new LinearLayout(this);
				layout_callcenter.setOrientation(LinearLayout.VERTICAL);
				
				final Button callcenter_no_1=new Button(this);
				callcenter_no_1.setText("1");
				callcenter_no_1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String callcenter_no_1="*3001#";
						sendSMSMessage(callcenter_no_1);
						
					}
				});
				
				layout_callcenter.addView(callcenter_no_1);
				
				final Button callcenter_no_2=new Button(this);
				callcenter_no_2.setText("2");
				callcenter_no_2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String callcenter_no_2="*3002#";
						sendSMSMessage(callcenter_no_2);
						
					}
				});
				layout_callcenter.addView(callcenter_no_2);

				
				final Button callcenter_no_3=new Button(this);
				callcenter_no_3.setText("3");
				callcenter_no_3.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String callcenter_no_3="*3003#";
						sendSMSMessage(callcenter_no_3);
						
					}
				});
				layout_callcenter.addView(callcenter_no_3);
				
								
				final Button callcenter_no_4=new Button(this);
				callcenter_no_4.setText("4");
				callcenter_no_4.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String callcenter_no_4="*3004#";
						sendSMSMessage(callcenter_no_4);
						
					}
				});
				layout_callcenter.addView(callcenter_no_4);
				
				
				
				
				final Button callcenter_no_5=new Button(this);
				callcenter_no_5.setText("5");
				callcenter_no_5.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String callcenter_no_5="*3005#";
					sendSMSMessage(callcenter_no_5);
						
					}
				});
				layout_callcenter.addView(callcenter_no_5);
				alert.setView(layout_callcenter);

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				
				alert.show();
				

				
	
				break;
			case 21:
				
				sendSMSMessage("*31#");
				break;
			case 22:
				alert.setTitle("SMS Back Alert Number");
				alert.setMessage("Edit phone numbers that recieve SMS");

				LinearLayout layout_num =new LinearLayout(this);
				layout_num.setOrientation(LinearLayout.VERTICAL);
				
				final TextView input_num_ser=new TextView(this);
				input_num_ser.setText("Enter Serial Number");
				layout_num.addView(input_num_ser);
				
				final EditText input_ser = new EditText(this);
				input_ser.setHint("e.g 01 (01-05)");
				input_ser.setInputType(InputType.TYPE_CLASS_PHONE);
				layout_num.addView(input_ser);
				
				final TextView num_input=new TextView(this);
				num_input.setText("Enter Phone Number");
				layout_num.addView(num_input);
				
				// Set an EditText view to get user input 
				final EditText input_num = new EditText(this);
				input_num.setHint("e.g. +919898989898");
				input_num.setInputType(InputType.TYPE_CLASS_PHONE);
				layout_num.addView(input_num);
				
				alert.setView(layout_num);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  String value = "*33"+input_ser.getText().toString()+input_num.getText().toString()+"#";
				  // Do something with value!
				  
				  sendSMSMessage(value);
				  
				  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
				break;
				
			case 23:
				alert.setTitle("Set Camera Port");
				alert.setMessage("Enter Camera Port");

				LinearLayout layout_camera =new LinearLayout(this);
				layout_camera.setOrientation(LinearLayout.VERTICAL);
				
				// Set an EditText view to get user input 
				final EditText input_cam = new EditText(this);
				input_cam.setHint("e.g. 1234");
				input_cam.setInputType(InputType.TYPE_CLASS_PHONE);
				layout_camera.addView(input_cam);
				
				alert.setView(layout_camera);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  String value = "*27"+input_cam.getText().toString()+"#";
				  // Do something with value!
				  
				  sendSMSMessage(value);
				  
				  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
				break;*/
			
			}

			
		}

}
