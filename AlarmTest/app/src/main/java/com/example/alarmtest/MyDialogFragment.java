package com.example.alarmtest;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Message;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.logging.Handler;

public class MyDialogFragment extends DialogFragment {

    private int timeHour;
    private int timeMinute;
    private MainActivity.MyHandler handler;

    public MyDialogFragment(MainActivity.MyHandler handler) {
        this.handler = handler;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        timeHour = bundle.getInt("time_hour");
        timeMinute = bundle.getInt("time_minute");
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeHour = hourOfDay;
                timeMinute = minute;
                Bundle b = new Bundle();
                b.putInt("time_hour", timeHour);
                b.putInt("time_minute", timeMinute);
                Message msg = new Message();
                msg.setData(b);
                handler.sendMessage(msg);
            }
        };
        return new TimePickerDialog(getActivity(), listener, timeHour, timeMinute, false);
    }
}
