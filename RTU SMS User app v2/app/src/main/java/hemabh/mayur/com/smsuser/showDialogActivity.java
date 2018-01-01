package hemabh.mayur.com.smsuser;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by mayursanjaydevgaonkar on 07/06/15.
 */
public class showDialogActivity extends Activity {



    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        Intent phoneIntent=getIntent();
        String message=phoneIntent.getStringExtra("msg");

        number = phoneIntent.getStringExtra("no");

        //
        //Log.d("DEBUG", "showing dialog!");

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_dialog_singlechoice);
        dialog.setTitle(number);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        TextView text = (TextView) dialog.findViewById(R.id.msg1);
        Button ok = (Button) dialog.findViewById(R.id.done);
        text.setText(message);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        dialog.show();
        //
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface arg0) {
                finish();
            }

        });
    }



}