package com.sagi.smartshopping.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.sagi.smartshopping.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by User on 14/09/2018.
 */

public class Utils {

    public static long getTimeStampFromDate(int year, int month, int day) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        return calendar.getTimeInMillis();
    }

    public static String getDateAndTimeFromTimeStamp(long timeStampDate ) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        Date netDate = (new Date(timeStampDate));
        return sdf.format(netDate);
    }

    public static String getTimeFromTimeStamp(long timeStampDate ) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
        Date netDate = (new Date(timeStampDate));
        return sdf.format(netDate);
    }

    public static String getDateFromTimeStamp(long timeStampDate ) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        Date netDate = (new Date(timeStampDate));
        return sdf.format(netDate);
    }
    public static long getTimeStampFromDateAndTime(long timeStampDate,int hour, int minuets) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(timeStampDate);
        calendar.set(Calendar.HOUR,hour);
        calendar.set(Calendar.MINUTE,minuets);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTimeInMillis();
    }

    public static boolean isValid(String email, String fName, String lName,long dateBirthDay, Context context) {
        if ( email.equals("") || fName.equals("") || lName.equals("")) {
            Toast.makeText(context, context.getResources().getString(R.string.fill_fields), Toast.LENGTH_SHORT).show();
            return false;
        } else   if (dateBirthDay == -1) {
            Toast.makeText(context, context.getResources().getString(R.string.choose_birth_date), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValid(String email, String pass, String fName, String lName,long dateBirthDay, Context context) {
        if (pass.equals("") || email.equals("") || fName.equals("") || lName.equals("")) {
            Toast.makeText(context, "must fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isEmailValid(email)) {
            Toast.makeText(context, "Email is not valid", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pass.length() < 6) {
            Toast.makeText(context, "Pass must have at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if (dateBirthDay == -1) {
            Toast.makeText(context, "must choose a birth date", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public static void hideKeyboardFrom(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String textEmailForFirebase(String email) {
        String emailFirebase = email;
        emailFirebase = emailFirebase.replace("@", "_");
        emailFirebase = emailFirebase.replace(".", "_");
        return emailFirebase;
    }

    public static String geteFirstLattersUpperCase(String text ) {
        return text.substring(0, 1).toUpperCase() + text.substring(1, text.length());
    }

    public static long getStartTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_WEEK,-30);
         return calendar.getTimeInMillis();
    }
}
