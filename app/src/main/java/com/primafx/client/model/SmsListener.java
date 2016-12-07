package com.primafx.client.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by Technical on 11/30/2016.
 */

public class SmsListener extends BroadcastReceiver {

    private SharedPreferences preferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();

                        Log.i(msg_from, msgBody);

                        String[] verify_code = msgBody.split(" ");
                        if (verify_code[0].equals("Kode") && verify_code[1].equals("verifikasi") && verify_code[2].equals("PrimaFX")){
                            Log.i("Code", verify_code[3]);
                            Intent in = new Intent("SmsMessage.intent.MAIN").
                                    putExtra("code", verify_code[3]);
                            context.sendBroadcast(in);
                        }

                        String[] rebate_verify_code = msgBody.split(" ");
                        if (verify_code[0].equals("Kode") && verify_code[1].equals("verifikasi") && verify_code[2].equals("Transfer")
                                && verify_code[3].equals("Rebate")){
                            String[] tf_code = verify_code[10].split("\n");

                            Log.i("TrfCode", tf_code[0]);

                            Intent in = new Intent("SmsMessage.intent.MAIN").
                                    putExtra("code", tf_code[0]);
                            context.sendBroadcast(in);
                        }

                    }
                }catch(Exception e){
//                            Log.d("Exception caught",e.getMessage());
                }
            }
        }
    }
}