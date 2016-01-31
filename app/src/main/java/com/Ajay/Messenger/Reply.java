package com.Ajay.Messenger;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Reply extends AppCompatActivity implements View.OnClickListener {
        Button Accept, Reject;
        TextView text;
public static String MessageButton;

        DatabaseHelper mydb;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reply);
        mydb = new DatabaseHelper(this);
        text = (TextView)findViewById(R.id.textView);
        Accept = (Button) findViewById(R.id.accept);
        Reject = (Button) findViewById(R.id.reject);
        text.setText(MyGcmListenerService.message);
        try {
        Accept.setOnClickListener(this);
        Reject.setOnClickListener(this);
        } catch (Exception e) {
        e.printStackTrace();
        }
        }
@Override
public void onClick(View A) {
        switch (A.getId()) {
        case R.id.accept:
        MessageButton = "Accepted";
        System.out.println(MessageButton);
        break;
        case R.id.reject:
        MessageButton = "Rejected";
        System.out.println(MessageButton);
        break;
default:
        throw new RuntimeException("unKnown");

        }
//        mydb.insert(Integer.toString(id),MyGcmListenerService.date.toString(),MyGcmListenerService.time.toString(),MessageButton);
        String[] array = new String[5];
        String from = MyGcmListenerService.fromAddr;
        String toAddr = MyGcmListenerService.toAddr;
        String date = MyGcmListenerService.date;
        String time = MyGcmListenerService.time;
        String meet = MyGcmListenerService.meet;
        System.out.println("in main2Activity"+from+toAddr+date+time);
        array[0]=MessageButton;
        array[1]=from;
        array[2]=date;
        array[3]=time;
        array[4]=meet;
        hello  hi = new hello();
        hi.execute(array);
        mydb.UpdateMeetings(
        meet,
        MessageButton
        );
        finish();
        }
class  hello extends AsyncTask<String, String[],Integer>{
    @Override
    protected Integer doInBackground(String[] array) {
        GcmSender.main(array);
        return null;
    }
}
}
