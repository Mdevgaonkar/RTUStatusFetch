package hemabh.mayur.com.smsuser;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * Created by mayursanjaydevgaonkar on 11/06/15.
 */
public class appOpener extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());


        boolean lock = SP.getBoolean("lock", false);

        if (lock) {
            Intent intent_main = new Intent(getApplicationContext(), lockscreen.class);
            startActivity(intent_main);
            finish();

        } else {

            Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent_main);
            finish();
        }


    }
}
