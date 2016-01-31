package src.main.java.com.Ajay.Messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Register extends AppCompatActivity {

    EditText edit;
    Button Submit;
    public static String myName;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mydb = new DatabaseHelper(this);
        edit = (EditText)findViewById(R.id.editText);
        Submit = (Button)findViewById(R.id.submit);
    }

    public void submit(View view) {
        myName = edit.getText().toString();
        startActivity(new Intent(this,Invite.class));
        mydb.setUsersData(myName);
        finish();
    }
}
