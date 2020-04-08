package com.example.alarmtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);

    TextView text1;
    private static TextView text2;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    public static TextView getText2View() {
        return text2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = findViewById(R.id.text1);
        text1.setText(timeHour + ":" + timeMinute);
        text2 = findViewById(R.id.text2);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent =
                PendingIntent.getBroadcast(MainActivity.this, 0,
                                           intent, PendingIntent.FLAG_UPDATE_CURRENT);

        findViewById(R.id.start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2.setText("");
                Bundle bundle = new Bundle();
                bundle.putInt("time_hour", timeHour);
                bundle.putInt("time_minute", timeMinute);

//                MyDialogFragment myDialogFragment = new MyDialogFragment(new MyHandler());
//                myDialogFragment.setArguments(bundle);
//                FragmentManager manager = getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.add(myDialogFragment, "time_picker");
//                transaction.commit();
                setAlarm();
            }
        });

        findViewById(R.id.stop_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2.setText("");
                cancelAlarm();
            }
        });
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            timeHour = bundle.getInt("time_hour");
            timeMinute = bundle.getInt("time_minute");
            text1.setText(timeHour + ":" + timeMinute);
            setAlarm();
        }
    }

    private void setAlarm() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        SimpleDateFormat mins = new SimpleDateFormat("mm");
        SimpleDateFormat hour = new SimpleDateFormat("hh");

        System.out.println(calendar.getTime());
        calendar.set(Calendar.HOUR, Integer.parseInt(hour.format(date)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(mins.format(date)) + 1);
        System.out.println(calendar.getTime());
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
