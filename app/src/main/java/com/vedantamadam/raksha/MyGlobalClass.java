package com.vedantamadam.raksha;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;

public class MyGlobalClass {
    public static String phoneNumber1 = "", phoneNumber2 = "", senstivityNumber = "";
    public static boolean fall = true;

    public static void save_pref(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences("com.vedantamadam.raksha.PREFERENCE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String read_pref(Context context, String key) {
        String value;
        SharedPreferences pref = context.getSharedPreferences("com.vedantamadam.raksha.PREFERENCE", Context.MODE_PRIVATE);
        if (pref.contains(key)) {
            value = pref.getString(key, "");
        } else {
            value = null;
        }
        return value;
    }

    public static void delete_pref(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences("com.vedantamadam.raksha.PREFERENCE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.apply();
    }

    public static boolean if_sosnumber_exist(Context context) {
        String sos_ph1, sos_ph2;
        sos_ph1 = read_pref(context, "e1");
        sos_ph2 = read_pref(context, "e2");
        return (sos_ph1 != null && !sos_ph1.isEmpty()) || (sos_ph2 != null && !sos_ph2.isEmpty());

    }

    public void sendSMS(Context context, String msg) {
        SmsManager sms = SmsManager.getDefault();
        ArrayList<String> emerMsg;
        emerMsg = sms.divideMessage(msg);
        if (if_sosnumber_exist(context)) {
            String[] ph = {read_pref(context,"e1"),read_pref(context,"e2")};
            for (int i = 0; i < ph.length && !ph[i].isEmpty(); i++) {
                    sms.sendMultipartTextMessage(ph[i], null, emerMsg, null, null);
            }
        } else {
            Toast.makeText(context, "Please set SOS phone numbers", Toast.LENGTH_SHORT).show();
        }

    }


}
