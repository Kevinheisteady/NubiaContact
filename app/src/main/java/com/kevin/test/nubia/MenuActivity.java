package com.kevin.test.nubia;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MenuActivity extends AppCompatActivity {
    private Button enterBtn;
    private Button cancleBtn;
    private EditText nameText;
    private EditText phoneText;
    private EditText stuNumText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setView();
    }
    private void setView(){
        enterBtn = (Button) findViewById(R.id.enter_btn);
        cancleBtn = (Button) findViewById(R.id.cancel_btn);
        nameText = (EditText) findViewById(R.id.name_edit);
        phoneText = (EditText) findViewById(R.id.phone_edit);
        stuNumText = (EditText) findViewById(R.id.stu_num_edit);

        enterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.person.provider/person_info");
                ContentValues values = new ContentValues();
                values.put("name", String.valueOf(nameText.getText()));
                values.put("phone_num", String.valueOf(phoneText.getText()));
                values.put("stu_num", String.valueOf(stuNumText.getText()));
                getContentResolver().insert(uri, values);
                startActivity(new Intent(MenuActivity.this,MainActivity.class));
            }
        });
        
        cancleBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuActivity.this, "do nothing", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
