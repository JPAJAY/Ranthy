package src.main.java.com.Ajay.Messenger;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Admin on 1/28/2016.
 */
public class Check extends AppCompatActivity{
    @Override protected void onStart() {super.onStart();on();}

    @Override protected void onRestart(){super.onRestart();on();}
    public void on(){
        DatabaseHelper  myb = new DatabaseHelper(this);;
        Cursor res = myb.viewUsers();
        if (res.getCount() == 0)
            startActivity(new Intent(this, Register.class));
        else
            startActivity(new Intent(this, Invite.class));
        finish();
    }
}
