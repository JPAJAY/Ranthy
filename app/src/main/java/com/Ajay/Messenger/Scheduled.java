package com.Ajay.Messenger;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Scheduled extends AppCompatActivity {
    TextView Sta;
    String Appionment_Date = MyGcmListenerService.date;
    String Appionment_Date_Time;
    String Appionment_Time = MyGcmListenerService.time;
    long diff,diffSeconds,diffMinutes,diffHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduled);
        Sta = (TextView)findViewById(R.id.st);

        prin();}

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void prin() {
        Appionment_Date_Time = Appionment_Date + " " + Appionment_Time;
        System.out.println("Appionment_Date_Time : " + Appionment_Date_Time);
        Calendar calendar = Calendar.getInstance();
        int Hour = calendar.get(Calendar.HOUR_OF_DAY);
        int Minute = calendar.get(Calendar.MINUTE);
        int Second = calendar.get(Calendar.SECOND);
        int Date = calendar.get(Calendar.DATE);
        int Month = calendar.get(Calendar.MONTH) + 1;
        int Year = calendar.get(Calendar.YEAR);
        String Current_Date = Date + "-" + Month + "-" + Year;
        String Current_Time = String.format("%02d:%02d:%02d", Hour, Minute, Second);
        String Current_Date_Time = Current_Date + " " + Current_Time;
        System.out.println(Current_Date_Time);
        String dateStart = Current_Date_Time;
        String dateStop = Appionment_Date_Time;
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);


        diff = d2.getTime() - d1.getTime();
        diffSeconds = diff / 1000 % 60;
        diffMinutes = diff / (60 * 1000) % 60;
        diffHours = diff / (60 * 60 * 1000);
        System.out.println("Time in seconds: " + diffSeconds + " seconds.");
        System.out.println("Time in minutes: " + diffMinutes + " minutes.");
        System.out.println("Time in hours: " + diffHours + " hours.");
        if (diffHours <= 0 || diffMinutes < 0 || diffSeconds < 0) {
            Sta.setText("UPCOMMING MEETINGS  " + diffMinutes + " Minutes Remaining");
            Sta.setTextColor(Color.parseColor("#FD0303"));
            Intent intent = new Intent(this,Broadcast.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this.getApplicationContext(), 234324243, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            long minumilli = diffMinutes*60000;
            System.out.println("The Current Milli Seconds : "+ System.currentTimeMillis());
            alarmManager.set(AlarmManager.RTC_WAKEUP, (System.currentTimeMillis()
                    + minumilli), pendingIntent);
            System.out.println(" diff minu" + minumilli);
            System.out.println("The Alarm  Milli Seconds : " + (System.currentTimeMillis()+60000));
            Toast.makeText(this, "Alarm set in " + diffMinutes+ " Minutes ",
                    Toast.LENGTH_LONG).show();
        }else {
            Sta.setText(diffHours + " Hours " + diffMinutes + "Minutes " + diffSeconds + "Seconds Remaining ");
            Sta.setTextColor(Color.parseColor("#024B0D"));
            long min = diffHours*60+diffMinutes;
            Intent intent = new Intent(this,Broadcast.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this.getApplicationContext(), 234324243, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            long minuTOmilli = min*60000;
            System.out.println("The Current Milli Seconds : "+ System.currentTimeMillis());
            alarmManager.set(AlarmManager.RTC_WAKEUP, (System.currentTimeMillis()
                    + minuTOmilli), pendingIntent);
            Toast.makeText(this, "Alarm set in " +diffHours + " Hours "+ diffMinutes+ " Minutes ",
                    Toast.LENGTH_LONG).show();
        }

        } catch (ParseException e) {
            e.printStackTrace();

        }
    }
}
