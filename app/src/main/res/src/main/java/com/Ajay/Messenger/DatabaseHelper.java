package src.main.java.com.Ajay.Messenger;

/**
 * Created by Admin on 1/28/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DataBase_name = "Messenger.db";
    public static final String usersData = "users";
    public static final String Meetings = "Meetings";
    public static final String Col1 = "ID";
    public static final String Col2 = "Name";
    public static final String Col3 = "ToMeet";
    public static final String Col4 = "Date";
    public static final String Col5 = "Time";
    public static final String Col6 = "Duration";
    public static final String Col7 = "Status";
    public static final String Col8 = "Created";

    public DatabaseHelper(Context context) {
        super(context, DataBase_name, null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + usersData + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT)");
        db.execSQL("create table " + Meetings + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Created TEXT , ToMeet TEXT, Date TEXT, Time TEXT, Duration TEXT, Status TEXT) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
        db.close();
    }

    public long setMeetings(String ctr, String Tomeet, String Date, String Time, String Duration, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Col3, Tomeet);
        content.put(Col4, Date);
        content.put(Col5, Time);
        content.put(Col6, Duration);
        content.put(Col7, status);
        content.put(Col8, ctr);
        long meet_id = db.insert(Meetings, null, content);
        db.close();
        return meet_id;
    }

    public void setUsersData(String Name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(Col2, Name);
        db.insert(usersData, null, content);
        db.close();
    }

    public Cursor ViewMeeting() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor meet = db.rawQuery("select * from " + Meetings, null);
        return meet;
    }

    public Cursor viewUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor user = db.rawQuery("select *from " + usersData, null);
        return user;

    }

    public void UpdateMeetings(String frm, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Col7, status);
        db.update(Meetings, content, " ID=?", new String[]{frm});
        System.out.print(content);
        db.close();
    }
}
