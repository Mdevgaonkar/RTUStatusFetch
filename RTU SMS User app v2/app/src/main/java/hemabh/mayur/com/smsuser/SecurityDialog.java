package hemabh.mayur.com.smsuser;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mayursanjaydevgaonkar on 16/06/15.
 */
public class SecurityDialog extends Activity {



    String DialogTitle, pin_value;
    EditText pinEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        Intent clickIntent=getIntent();
        DialogTitle = clickIntent.getStringExtra("titleButton");


        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        pin_value = SP.getString("pass_key", "0000");

        //
        //Log.d("DEBUG", "showing dialog!");

        Dialog securityDialog = new Dialog(this);
        securityDialog.setContentView(R.layout.security_dialog);
        securityDialog.setTitle(DialogTitle);
        securityDialog.setCancelable(true);
        Button done = (Button) securityDialog.findViewById(R.id.PinDone);
        pinEdit = (EditText) securityDialog.findViewById(R.id.PinEdit);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pinEdit.getText().toString().matches(pin_value)) {


                    Intent intent_main = new Intent(getApplicationContext(), settings.class);
                    startActivity(intent_main);
                    finish();

                } else {

                    Toast.makeText(getApplicationContext(), "Wrong Pin, please try again", Toast.LENGTH_LONG).show();
                }

                //finish();

            }
        });


        securityDialog.show();
        //
        securityDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface arg0) {
                finish();
            }

        });
    }



}
