package com.mca.joshua.alarmmanagerandroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ImageButton clock;
    TextView time;
    Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clock=findViewById(R.id.clock);
        time=findViewById(R.id.timetxt);
        cancel=findViewById(R.id.cancel);
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener setTime=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Calendar alarm=Calendar.getInstance();
                        alarm.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        alarm.set(Calendar.MINUTE,minute);
                        alarm.set(Calendar.SECOND,0);

                        time.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(alarm.getTime()));

                        StartAlarm(alarm);
                    }
                };
                Calendar now = Calendar.getInstance();
                int hour = now.get(java.util.Calendar.HOUR_OF_DAY);
                int minute = now.get(java.util.Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, setTime,hour , minute, true);

                timePickerDialog.setIcon(R.drawable.clock);
                timePickerDialog.setTitle("Please select time.");

                timePickerDialog.show();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });
    }

    private void StartAlarm(Calendar alarm) {
        AlarmManager alrmmgnr=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this,Alert.class);
        PendingIntent pintent=PendingIntent.getBroadcast(this,1,intent ,0);
        if(alarm.before(Calendar.getInstance())){
            alarm.add(Calendar.DATE,1);
        }
        //alrmmgnr.setExact(AlarmManager.RTC_WAKEUP,alarm.getTimeInMillis(),pintent);
        alrmmgnr.setRepeating(AlarmManager.RTC_WAKEUP,alarm.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pintent);

    }

    private void cancelAlarm(){
        AlarmManager alrmmgnr=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this,Alert.class);
        PendingIntent pintent=PendingIntent.getBroadcast(this,1,intent ,0);
        alrmmgnr.cancel(pintent);
        Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.cancel();

    }

}
