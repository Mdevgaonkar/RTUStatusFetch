package hemabh.mayur.com.smsuser;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

	ListView contact_list;
	SQLcontroller dbCon, dbConnew;
	TextView empty;
	TextView memID_tv,memName_tv,memPhone_tv, memLocation_tv, memSiteID_tv;
    LinearLayout search_layout;
    EditText search_edit;
    boolean visiblity_tag;

    SimpleCursorAdapter adapter;

    boolean lock;
    String pin_value;


    ArrayList<Integer>  selectedItemsposition = new ArrayList<Integer>();

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbCon = new SQLcontroller(this);
        dbCon.open();
        contact_list=(ListView) findViewById(R.id.contact_list);
        empty=(TextView) findViewById(R.id.empty);
        contact_list.setEmptyView(empty);


        search_layout = (LinearLayout) findViewById(R.id.search_layout);
        search_edit = (EditText) findViewById(R.id.search_edit);

        search_layout.setVisibility(View.GONE);
        visiblity_tag = false;



        search_edit.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
                //String text = search_edit.getText().toString().toLowerCase(Locale.getDefault());
                populateListview(arg0.toString());
            }
        });



        //List view population using Cursor adapter
        Cursor cursor=dbCon.readData();
        String[] from = new String[] { DBHelper.MEMBER_ID, DBHelper.MEMBER_NAME, DBHelper.MEMBER_PHONE,  DBHelper.MEMBER_LOCATION, DBHelper.MEMBER_LOC_ID};
		int[] to = new int[] { R.id.member_id, R.id.member_name, R.id.member_phone, R.id.member_location, R.id.member_loc_id };
		

        adapter = new SimpleCursorAdapter(
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
                memLocation_tv = (TextView) view.findViewById(R.id.member_location);
                memSiteID_tv = (TextView) view.findViewById(R.id.member_loc_id);

				String memberID_val = memID_tv.getText().toString();
				String memberName_val = memName_tv.getText().toString();
				String memberPhone_val = memPhone_tv.getText().toString();
                String memberLocation_val = memLocation_tv.getText().toString();
                String memberSiteID_val = memSiteID_tv.getText().toString();

				Intent modify_intent = new Intent(getApplicationContext(), ViewContact.class);
				modify_intent.putExtra("memberName", memberName_val);
				modify_intent.putExtra("memberPhone", memberPhone_val);
                modify_intent.putExtra("memberLocation", memberLocation_val);
                modify_intent.putExtra("memberSiteID", memberSiteID_val);
				modify_intent.putExtra("memberID", memberID_val);
				startActivity(modify_intent);
			}
		});


        contact_list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        contact_list.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                int checkedCount = contact_list.getCheckedItemCount();
                mode.setTitle(checkedCount+" Selected");

                selectedItemsposition.add(position);

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {

                mode.getMenuInflater().inflate(R.menu.my_context_menu,menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.multiselectDelete:
                        Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),selectedItemsposition.toString(),Toast.LENGTH_LONG).show();
                        for (int i = 0 ; i<selectedItemsposition.size();i++){
                            Cursor c = (Cursor) adapter.getItem(selectedItemsposition.get(i));
                            int itemId = c.getInt(0);
                            dbCon.deleteData(itemId);
                        }
                        adapter.getCursor().requery();
                        return true;

                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                selectedItemsposition.clear();

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

    	
    	super.onOptionsItemSelected(item);
    	switch(item.getItemId()) 
        { 
           case R.id.action_settings: 
              Intent addIntent = new Intent(getApplicationContext(),AddContact.class);
              startActivity(addIntent);
              return true;
            case R.id.action_change_pass:
                change_process();
                return true;
            case R.id.search:
                if (visiblity_tag == false) {
                    search_layout.setVisibility(View.VISIBLE);
                    search_edit.requestFocus();
                    visiblity_tag = true;
                }
                else{
                    search_edit.setText("");
                    search_layout.setVisibility(View.GONE);
                    contact_list.setAdapter(adapter);
                    visiblity_tag = false;
                }

                return true;
           default:
              return super.onOptionsItemSelected(item); 

         } 
    }

    public void change_process(){


        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());



        lock = SP.getBoolean("lock", false);
        pin_value = SP.getString("pass_key","0000");

        if(lock){
           /* */

            Intent changeIntent = new Intent(getApplicationContext(), SecurityDialog.class);
            changeIntent.putExtra("titleButton", "Settings");
            startActivity(changeIntent);

        }
        else
        {
            Intent changeIntent = new Intent(getApplicationContext(), hemabh.mayur.com.smsuser.settings.class);
            startActivity(changeIntent);
        }



    }


    public void populateListview(String s){

        if (!s.matches("")) {
            contact_list.setAdapter(null);
        Cursor cursor=dbCon.searchPopulate(s);
            String[] from = new String[] { DBHelper.MEMBER_ID, DBHelper.MEMBER_NAME, DBHelper.MEMBER_PHONE,  DBHelper.MEMBER_LOCATION, DBHelper.MEMBER_LOC_ID};
            int[] to = new int[] { R.id.member_id, R.id.member_name, R.id.member_phone, R.id.member_location, R.id.member_loc_id };

            SimpleCursorAdapter adapternew = new SimpleCursorAdapter(
                MainActivity.this, R.layout.view_member_entry, cursor, from, to);

        adapternew.notifyDataSetChanged();
        contact_list.setAdapter(adapternew);
            empty.setText("No Similar Sites Found");



        }
        else {
            contact_list.setAdapter(adapter);
        }
    }




}
