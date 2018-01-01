package hemabh.mayur.com.smsuser;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mayursanjaydevgaonkar on 11/06/15.
 */
public class lockscreen extends ActionBarActivity implements View.OnClickListener {

    Button check;
    EditText pin;
    String pin_value;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lockscreen);

        pin = (EditText) findViewById(R.id.pin);
        check = (Button) findViewById(R.id.check_passKey);

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        android.support.v7.app.ActionBar myac= getSupportActionBar();
        myac.hide();


        // boolean lock = SP.getBoolean("lock", false);

        pin_value = SP.getString("pass_key", "0000");

        pin.requestFocus();

        pin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do your stuff here
                    if (pin.getText().toString().matches(pin_value)) {

                        Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent_main);
                        finish();

                    } else {

                        Toast.makeText(getApplicationContext(), "Wrong Pin, please try again", Toast.LENGTH_LONG).show();
                    }

                }

                return false;
            }
        });


        check.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if (pin.getText().toString().matches(pin_value)) {

        Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent_main);
            finish();

                    } else {

            Toast.makeText(getApplicationContext(), "Wrong Pin, please try again", Toast.LENGTH_LONG).show();
                    }

    }
}



