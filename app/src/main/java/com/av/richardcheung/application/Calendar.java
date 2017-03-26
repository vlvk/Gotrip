package com.av.richardcheung.application;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;

public class Calendar extends AppCompatActivity {
    public final static String GIVE_DATE = "com.example.myfirstapp.DATE";
    public static final int REQUEST_CODE = 1;
    public String givedate = "";

    //private final static int REQUEST_CODE=1;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        //与MainActivity同步数据
        final Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        System.out.println(message);
        CalendarView date = (CalendarView) findViewById(R.id.calendarView);
        String first = message.replace('年', '-');
        String secend = first.replace('月', '-');
        String fina = secend.substring(0, secend.length() - 1);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date ticDate = new Date();

        try {
            ticDate = sim.parse(fina);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = ticDate.getTime();
        date.setDate(time);


        date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + "年" + (month + 1) + "月" + dayOfMonth + "日";
                givedate = date;
                System.out.println(givedate);
                TextView y = (TextView) findViewById(R.id.textView3);
                y.setText(year + "年");
                TextView md = (TextView) findViewById(R.id.textView4);
                md.setText((month + 1) + "月" + dayOfMonth + "日");
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void givedate(View view) {
        Intent dateback = new Intent();
        dateback.putExtra(GIVE_DATE, givedate);
        setResult(REQUEST_CODE, dateback);
        finish();
    }


}
