package hemabh.mayur.com.smsuser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class editContact extends ActionBarActivity implements OnClickListener {
	
	EditText name_edit,phone_edit,location_edit,site_id_edit;

	Button save,delete,cancel;
	SQLcontroller dbcon;
	String memID,memName,memPhone,memlocation,memSiteId;
	long member_id;
	
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_member);
        
        dbcon=new SQLcontroller(this);
        dbcon.open();
        
        name_edit=(EditText) findViewById(R.id.name_edit);
        phone_edit=(EditText) findViewById(R.id.phone_edit);
		location_edit=(EditText) findViewById(R.id.location_edit);
		site_id_edit=(EditText) findViewById(R.id.site_id_edit);

        save=(Button) findViewById(R.id.save);
        delete=(Button) findViewById(R.id.delete);
        cancel=(Button) findViewById(R.id.cancel);
        
        Intent showIntent=getIntent();
        Bundle b= showIntent.getExtras();
        memID=b.getString("memberId");
        memName=b.getString("memberName");
        memPhone=b.getString("memberPhone");
		memlocation=b.getString("memberLocation");
		memSiteId=b.getString("memberSiteID");
        
        member_id= Long.parseLong(memID);
		
        name_edit.setText(memName);
        phone_edit.setText( memPhone);
		location_edit.setText(memlocation);
		site_id_edit.setText(memSiteId);
        
        save.setOnClickListener(this);
        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);
	
	
	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.save:
			String name_upd=name_edit.getText().toString();
			String phone_upd=phone_edit.getText().toString();
			String location_upd=location_edit.getText().toString();
			String siteId_upd=site_id_edit.getText().toString();
			
			if (name_upd.matches("") | phone_upd.matches("") | location_upd.matches("") | siteId_upd.matches("")) {
				
				Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
   	            
				
			} else {
				
				dbcon.updateData(member_id,name_upd,phone_upd,location_upd,siteId_upd);
				dbcon.close();
				Intent main = new Intent(editContact.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(main);

			}
			
			break;
			
		case R.id.delete:
			 AlertDialog.Builder builder = new AlertDialog.Builder(this);
   	      builder.setMessage(R.string.deleteContact)
   	     .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
   	         public void onClick(DialogInterface dialog, int id) {
   	        	 dbcon.deleteData(member_id);
   	        	 dbcon.close();
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
		//this.returnHome();
		break;
		
		case R.id.cancel:
			super.onBackPressed();
	}
}



}
