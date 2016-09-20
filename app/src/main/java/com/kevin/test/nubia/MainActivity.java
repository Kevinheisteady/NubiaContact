package com.kevin.test.nubia;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private PopupMenu popupMenu;
    private String newId;
    private static final String TAG = "MainActivity";
    private Button menuBtn;
    private ListView listView;
    InfoAdapter infoAdapter;
    private List personList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        initData();
        InfoAdapter infoAdapter = new InfoAdapter(this,R.layout.staff_info_layout,personList);
        listView.setAdapter(infoAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        freshListView();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    public void onMainMenuBtnClick(View Button){
        popupMenu = new PopupMenu(this,menuBtn);
        getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add_person_menu:
                        Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void setView() {
        menuBtn = (Button) findViewById(R.id.menu);
        menuBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onMainMenuBtnClick(menuBtn);
            }
        });
        listView = (ListView) findViewById(R.id.info_list);
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Person pers = (Person) personList.remove(i);
                Uri uri = Uri.parse(PersonProvider.ACESS_STR+pers.getId());
                Log.d(TAG, "onItemLongClick: "+pers.getId());
                getContentResolver().delete(uri,null,new String[]{String.valueOf(pers.getId())});
                infoAdapter = new InfoAdapter(MainActivity.this,R.layout.staff_info_layout,personList);
                listView.setAdapter(infoAdapter);
                return false;
            }
        });
    }

    private List  initData(){
        Uri uri = Uri.parse(PersonProvider.ACESS_STR);
        Person person = null;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                person = new Person();
                person.setName(cursor.getString(cursor.
                        getColumnIndex("name")));
                person.setPhoneNum(cursor.getString(cursor.
                        getColumnIndex("phone_num")));
                person.setStuNum(cursor.getString(cursor.
                        getColumnIndex("stu_num")));
                person.setId(cursor.getColumnIndex("id"));
                Log.d(TAG, "initData: "+person.getId());
                personList.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return personList;
    }
    private void freshListView(){
        Uri uri = Uri.parse(PersonProvider.ACESS_STR);
        Person person;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                person = new Person();
                person.setName(cursor.getString(cursor.
                        getColumnIndex("name")));
                person.setPhoneNum(cursor.getString(cursor.
                        getColumnIndex("phone_num")));
                person.setStuNum(cursor.getString(cursor.
                        getColumnIndex("stu_num")));
                if(!personList.contains(person)){
                    personList.add(person);
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
