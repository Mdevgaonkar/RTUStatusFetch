package hemabh.mayur.com.smsuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends ActionBarActivity implements OnClickListener {

	EditText name_et, phone_et, location_et, site_id_et;
	Button add_bt;
	SQLcontroller dbcon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_member);
		
		
		dbcon=new SQLcontroller(this);
		dbcon.open();
		
		name_et=(EditText) findViewById(R.id.name_edit_contact);
		phone_et=(EditText) findViewById(R.id.phone_edit_contact);
		site_id_et=(EditText) findViewById(R.id.site_id_edit_contact);
		location_et=(EditText) findViewById(R.id.location_edit_contact);
		add_bt=(Button) findViewById(R.id.add_bt_id);

		
		add_bt.setOnClickListener(this);
		
		
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.add_bt_id:
			
			
			String name = name_et.getText().toString();
			String phone = phone_et.getText().toString();
			String location = location_et.getText().toString();
			String siteId = site_id_et.getText().toString();
			
			if (name.matches("") || phone.matches("") || location.matches("") || siteId.matches("")) {
				
				Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
				
			} else {
				
				dbcon.insertData(name, phone, location, siteId);
				dbcon.close();
				Intent main = new Intent(AddContact.this, MainActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(main);
				finish();

			}
			
			break;

		default:
			break;
		}

		
	}

}
