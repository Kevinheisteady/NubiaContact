package com.kevin.test.nubia;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by Kevin-He on 2016/8/26.
 */
public class PersonProvider extends ContentProvider {
    private static final String TAG = "PersonProvider";
    private static final int TABLE1_DIR = 0;
    private static final int TABLE1_ITEM = 1;
    private static final String ATHORITY = "com.example.person.provider";
    private static final String DATABASE_NAME = "person.db";
    private static final String TABLE_NAME = "person_info";
    private static UriMatcher uriMatcher;
    public static final String ACESS_STR =  "content://" + ATHORITY + "/"+TABLE_NAME+"/";
    private PersonDatabaseHelper dbHelper;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ATHORITY,TABLE_NAME,TABLE1_DIR);
        uriMatcher.addURI(ATHORITY,TABLE_NAME+"/#",TABLE1_ITEM);
    }
    @Override
    public boolean onCreate() {
        dbHelper = new PersonDatabaseHelper(getContext(), DATABASE_NAME, null, 2);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                cursor = db.query(TABLE_NAME, strings,  s, strings1,
                        null, null, s1);
                break;
            case TABLE1_ITEM:
                String personId = uri.getPathSegments().get(1);
                cursor = db.query(TABLE_NAME,strings , "id = ?", new String[]
                        { personId }, null, null, s1);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                return "vnd.android.cursor.dir/vnd."+ATHORITY+"."+TABLE_NAME;
            case TABLE1_ITEM:
                return "vnd.android.cursor.item/vnd."+ATHORITY+"."+TABLE_NAME;
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        // 添加数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
            case TABLE1_ITEM:
                long newBookId = db.insert(TABLE_NAME, null, contentValues);
                Log.d(TAG, "insert: "+newBookId);
                uriReturn = Uri.parse(ACESS_STR +
                        newBookId);
                break;
            default:
                break;
        }
        Log.d(TAG, "insert: "+uriReturn);
        return uriReturn;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                deletedRows = db.delete(TABLE_NAME, s, strings);
                break;
            case TABLE1_ITEM:
                String personId = uri.getPathSegments().get(1);
                deletedRows = db.delete(TABLE_NAME, "id = ?", new String[] { personId });
                break;
            default:
                break;
        }
        return deletedRows;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                updatedRows = db.update(TABLE_NAME, contentValues, s, strings);
                break;
            case TABLE1_ITEM:
                String bookId = uri.getPathSegments().get(1);
                updatedRows = db.update(TABLE_NAME, contentValues, "id = ?", new String[]
                        { bookId });
                break;
            default:
                break;
        }
        return updatedRows;
    }

}
