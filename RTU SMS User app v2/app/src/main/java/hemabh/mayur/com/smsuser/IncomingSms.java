package hemabh.mayur.com.smsuser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by mayursanjaydevgaonkar on 06/06/15.
 */
public class IncomingSms extends BroadcastReceiver {

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    Context c1;

    public void onReceive(Context context, Intent intent) {

        abortBroadcast();
        c1=context;
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ phoneNumber + "; message: " + message);


                    // Show Alert

                    //MainActivity.triggerAlert(phoneNumber, senderNum);




                    Intent intent_SMS=new Intent(context, showDialogActivity.class);
                    intent_SMS.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent_SMS.putExtra("no",phoneNumber);
                    intent_SMS.putExtra("msg",message);
                    context.startActivity(intent_SMS);




                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }




}
