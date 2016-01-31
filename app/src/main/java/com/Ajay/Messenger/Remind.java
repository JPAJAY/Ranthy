package com.Ajay.Messenger;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Remind extends AppCompatActivity {
    TextView Sta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remind);
        Sta = (TextView)findViewById(R.id.st);
        Sta.setText("Time's Up For Meetings");
        Sta.setTextColor(Color.parseColor("#FD0303"));



    }
}
