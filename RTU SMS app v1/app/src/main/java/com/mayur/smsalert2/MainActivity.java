package com.mayur.smsalert2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	ListView contact_list;
	SQLcontroller dbCon;
	TextView empty;
	TextView memID_tv,memName_tv,memPhone_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        dbCon = new SQLcontroller(this);
        dbCon.open();
        contact_list=(ListView) findViewById(R.id.contact_list);
        empty=(TextView) findViewById(R.id.empty);
        contact_list.setEmptyView(empty);
        
        
        //List view population using Cursor adapter
        Cursor cursor=dbCon.readData();
        String[] from = new String[] { DBHelper.MEMBER_ID, DBHelper.MEMBER_NAME, DBHelper.MEMBER_PHONE };
		int[] to = new int[] { R.id.member_id, R.id.member_name, R.id.member_phone };
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				MainActivity.this, R.layout.view_member_entry, cursor, from, to);

		adapter.notifyDataSetChanged();
		contact_list.setAdapter(adapter);

		// OnCLickListiner For List Items
		contact_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				memID_tv = (TextView) view.findViewById(R.id.member_id);
				memName_tv = (TextView) view.findViewById(R.id.member_name);
				memPhone_tv = (TextView) view.findViewById(R.id.member_phone);

				String memberID_val = memID_tv.getText().toString();
				String memberName_val = memName_tv.getText().toString();
				String memberPhone_val = memPhone_tv.getText().toString();

				Intent modify_intent = new Intent(getApplicationContext(), ViewContact.class);
				modify_intent.putExtra("memberName", memberName_val);
				modify_intent.putExtra("memberPhone", memberPhone_val);
				modify_intent.putExtra("memberID", memberID_val);
				startActivity(modify_intent);
			}
		});

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
           case R.id.action_settings: 
              Intent addIntent = new Intent(getApplicationContext(),AddContact.class);
              startActivity(addIntent);
              return true; 
           default: 
              return super.onOptionsItemSelected(item); 

         } 
    }
}
