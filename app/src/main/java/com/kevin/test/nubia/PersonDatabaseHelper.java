package com.kevin.test.nubia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Kevin-He on 2016/8/26.
 */
public class PersonDatabaseHelper extends SQLiteOpenHelper {
    private PersonDatabaseHelper dbHelper;
    public static final String CREATE_PERSON = "create table person_info ("
            + "id integer primary key autoincrement, "
            + "name text, "
            + "stu_num text, "
            + "phone_num text)";
    private Context mContext;
    public PersonDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PERSON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
