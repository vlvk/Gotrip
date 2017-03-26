package com.av.richardcheung.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public final static String EXTRA_TIME = "com.example.myfirstapp.MESSAGE";
    private final static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void exchange(View view) {
        EditText get_S = (EditText) findViewById(R.id.Sadress);
        EditText get_E = (EditText) findViewById(R.id.Eadress);
        String Stext = get_S.getText().toString();
        String Etext = get_E.getText().toString();
        get_E.setText(Stext);
        get_S.setText(Etext);
    }

    public void data(View view) {
        Intent intent = new Intent(this, Calendar.class);
        EditText data = (EditText) findViewById(R.id.editText12);
        String message = data.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void search(View view) {
        Intent intent = new Intent(this, Result.class);
        EditText data = (EditText) findViewById(R.id.editText12);
        String message = data.getText().toString();
        intent.putExtra(EXTRA_TIME, message);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Calendar.REQUEST_CODE) {
                System.out.println("GetIN");
                Bundle bundle = data.getExtras();
                String str = bundle.getString("GIVE_DATE");
                System.out.println(str);
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
            }
        }
    }
}
