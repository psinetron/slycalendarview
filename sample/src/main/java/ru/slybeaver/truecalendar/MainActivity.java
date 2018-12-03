package ru.slybeaver.truecalendar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnShowCalendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlyCalendarDialog calendarDialog = new SlyCalendarDialog();
                calendarDialog.setSingle(false);
                calendarDialog.setSelectedColor(Color.parseColor("#00FF00"));
                calendarDialog.setSelectedTextColor(Color.parseColor("#0000FF"));
                calendarDialog.show(getSupportFragmentManager(),"TAG_SLYCALENDAR");

            }
        });

    }
}
