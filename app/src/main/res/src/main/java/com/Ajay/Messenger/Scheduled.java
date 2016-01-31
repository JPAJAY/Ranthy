package src.main.java.com.Ajay.Messenger;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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
        } else {
            Sta.setText(diffHours + " Hours " + diffMinutes + "Minutes " + diffSeconds + "Seconds Remaining ");
            Sta.setTextColor(Color.parseColor("#024B0D"));
        }


        } catch (ParseException e) {
            e.printStackTrace();

        }
    }
}
