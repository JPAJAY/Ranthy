package src.main.java.com.Ajay.Messenger;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Calendar;

public class Invite extends AppCompatActivity implements  AdapterView.OnItemSelectedListener, View.OnClickListener{
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "INVITE";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    DatabaseHelper mydb;
    TextView date, time;
    static String name, Text;
    static String dat, tim, AM_PM;
    int hour, Minute, hourOfDay, Am, sec, day, month, year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite);
        mydb = new DatabaseHelper(this);
        Cursor users = mydb.viewUsers();
        StringBuffer buff = new StringBuffer();
        users.moveToFirst();
        buff.append(users.getString(1));
        name = buff.toString();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        date = (TextView) findViewById(R.id.dtebutt);
        time = (TextView) findViewById(R.id.tmebutt);
        date.setOnClickListener(this);
        time.setOnClickListener(this);
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        Minute = c.get(Calendar.MINUTE);
        Am = c.get(Calendar.AM_PM);
        hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        sec = c.get(Calendar.SECOND);
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH) + 1;
        year = c.get(Calendar.YEAR);
        if (Am == 0) {
            AM_PM = "AM";
        } else {
            AM_PM = "PM";
        }

        tim = hour + ":" + Minute + " " + AM_PM;
        dat = day + "-" + month + "-" +
                "" + year;
        time.setText(String.format("%02d:%02d:%02d", hourOfDay, Minute, sec));
        date.setText(dat);
        System.out.println("The decimal Hour : " + hour + Minute);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.Contacts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
            }
        };

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Toast.makeText(getBaseContext(),"Wait Few Moments Registering",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
            Toast.makeText(Invite.this, "Thanks For Waiting Registered you can Continue ", Toast.LENGTH_LONG).show();
        }

        System.out.println(name);

    }
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override

    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void invite(View v) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Text = spinner.getSelectedItem().toString();
        System.out.println("   " + date.getText().toString() + "  " + time.getText().toString());
        System.out.print(name);
        long id = mydb.setMeetings(
                name.toString(),
                Text.toString(),
                dat.toString(),
                tim.toString(),
                "30 Minutes",
                "Invite Sent");
        String Tomeet_ID = String.valueOf(id);
        System.out.println("\n\n\n inserted from Main Activity" + id + "\n\n\n");
        String[] array = new String[5];
        array[0] = name.toString();
        array[1] = Text.toString();
        array[2] = date.getText().toString();
        ;
        array[3] = time.getText().toString();
        array[4] = Tomeet_ID.toString();
        myTask task = new myTask();
        task.execute(array);
    }


    public void onClick(View v) {
        if (v == date) {

            // Process to get Current Date
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            date.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);
                        }
                    }, year, month, day);
            dpd.show();
        }
        if (v == time) {

            // Process to get Current Time
            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            Am = c.get(Calendar.AM_PM);
            Minute = c.get(Calendar.MINUTE);
            hourOfDay = c.get(Calendar.HOUR_OF_DAY);
            sec = c.get(Calendar.SECOND);

            // Launch Time Picker Dialog
            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hour,
                                              int minute) {
                            time.setText(String.format("%02d:%02d:%02d", hour, minute, sec));
                        }
                    }, hour, Minute, false);
            tpd.show();

            // Display Selected time in textbox
//            time.setText(String.format("%02d:%02d",hour + ":" + minute + "  "
//                    + timeSet
//            ));
        }
    }

    public void view(View view) {
        Cursor res = mydb.ViewMeeting();
        if (res.getCount() == 0) {
            ShowMessage("whow", "No Appointments");
            return;
        }
        StringBuffer buff = new StringBuffer();
        while (res.moveToNext()) {
            buff.append("ID :  " + res.getString(0) + "\n\n");
            buff.append("Invite Created By  :  " + res.getString(1) + "\n\n");
            buff.append("To meet :  " + res.getString(2) + "\n\n");
            buff.append("date :  " + res.getString(3) + "\n\n");
            buff.append("Time :  " + res.getString(4) + "\n\n");
            buff.append("Duration :  " + res.getString(5) + "\n\n");
            buff.append("status :  " + res.getString(6) + "\n\n");
            ShowMessage("Appointments", buff.toString());

        }
    }



    public void ShowMessage(String Title, String Message) {
        AlertDialog.Builder Dialog = new AlertDialog.Builder(this);
        Dialog.setCancelable(true);
        Dialog.setTitle(Title);
        Dialog.setMessage(Message);
        Dialog.show();
    }


    class myTask extends AsyncTask<String, String[], Integer> {
        @Override
        protected Integer doInBackground(String[] array) {
            GcmSender.main(array);
//            String test = array.toString();
            return null;
        }


    }
}
