package ru.slybeaver.slycalendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ru.slybeaver.slycalendarview.adapters.GridAdapter;

/**
 * Created by psinetron on 29/11/2018.
 * http://slybeaver.ru
 */
public class SlyCalendarView extends FrameLayout {

    private SlyCalendarData slyCalendarData = new SlyCalendarData();
    private View mainView = null;

    public SlyCalendarView(Context context) {
        super(context);
        init(null, 0);
    }

    public SlyCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SlyCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(@Nullable AttributeSet attrs, int defStyle) {
        inflate(getContext(), R.layout.slycalendar_mainview, this);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SlyCalendarView, defStyle, 0);

        slyCalendarData.setBackgroundColor(typedArray.getColor(R.styleable.SlyCalendarView_backgroundColor, ContextCompat.getColor(getContext(),R.color.slycalendar_defBackgroundColor)));
        slyCalendarData.setHeaderColor(typedArray.getColor(R.styleable.SlyCalendarView_headerColor, ContextCompat.getColor(getContext(),R.color.slycalendar_defHeaderColor)));
        slyCalendarData.setHeaderTextColor(typedArray.getColor(R.styleable.SlyCalendarView_headerTextColor, ContextCompat.getColor(getContext(),R.color.slycalendar_defHeaderTextColor)));
        slyCalendarData.setTextColor(typedArray.getColor(R.styleable.SlyCalendarView_textColor, ContextCompat.getColor(getContext(),R.color.slycalendar_defTextColor)));
        slyCalendarData.setSelectedColor(typedArray.getColor(R.styleable.SlyCalendarView_selectedColor, ContextCompat.getColor(getContext(),R.color.slycalendar_defSelectedColor)));
        slyCalendarData.setSelectedTextColor(typedArray.getColor(R.styleable.SlyCalendarView_selectedTextColor, ContextCompat.getColor(getContext(),R.color.slycalendar_defSelectedTextColor)));

        typedArray.recycle();

        ((ViewGroup) findViewById(R.id.content)).removeAllViews();
        LayoutInflater.from(getContext()).inflate(R.layout.slycalendar_calendar, (ViewGroup) findViewById(R.id.content), true);

        showCalendar();
    }

    private void showCalendar() {

        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = null;
        if (slyCalendarData.getSelectedStartDate()!=null) {
            calendarStart.setTime(slyCalendarData.getSelectedStartDate());
        } else {
            calendarStart.setTime(slyCalendarData.getShowDate());
        }

        if (slyCalendarData.getSelectedEndDate()!=null) {
            calendarEnd = Calendar.getInstance();
            calendarEnd.setTime(slyCalendarData.getSelectedEndDate());
        }

        ((TextView) findViewById(R.id.txtYear)).setText(String.valueOf(calendarStart.get(Calendar.YEAR)));


        if (calendarEnd==null ) {
            ((TextView) findViewById(R.id.txtSelectedPeriod)).setText(
                    new SimpleDateFormat("EE, dd MMM", Locale.getDefault()).format(calendarStart.getTime())
            );
        } else {
            if (calendarStart.get(Calendar.MONTH) == calendarEnd.get(Calendar.MONTH)) {
                ((TextView) findViewById(R.id.txtSelectedPeriod)).setText(
                        getContext().getString(R.string.slycalendar_dates_period, new SimpleDateFormat("EE, dd", Locale.getDefault()).format(calendarStart.getTime()), new SimpleDateFormat("EE, dd MMM", Locale.getDefault()).format(calendarEnd.getTime()))
                );
            } else {
                ((TextView) findViewById(R.id.txtSelectedPeriod)).setText(
                        getContext().getString(R.string.slycalendar_dates_period, new SimpleDateFormat("EE, dd MMM", Locale.getDefault()).format(calendarStart.getTime()), new SimpleDateFormat("EE, dd MMM", Locale.getDefault()).format(calendarEnd.getTime()))
                );
            }
        }

        ((TextView) findViewById(R.id.txtSelectedMonth)).setText( new SimpleDateFormat("LLLL", Locale.getDefault()).format(slyCalendarData.getShowDate()));

        findViewById(R.id.btnMonthPrev).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentCalendar = Calendar.getInstance();
                currentCalendar.setTime(slyCalendarData.getShowDate());
                currentCalendar.add(Calendar.MONTH, -1);
                slyCalendarData.setShowDate(currentCalendar.getTime());
                showCalendar();
            }
        });

        findViewById(R.id.btnMonthNext).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentCalendar = Calendar.getInstance();
                currentCalendar.setTime(slyCalendarData.getShowDate());
                currentCalendar.add(Calendar.MONTH, 1);
                slyCalendarData.setShowDate(currentCalendar.getTime());
                showCalendar();
            }
        });


        ((TextView) findViewById(R.id.day1)).setText(slyCalendarData.isFirstMonday() ? getContext().getString(R.string.slycalendar_mon) : getContext().getString(R.string.slycalendar_sun));
        ((TextView) findViewById(R.id.day2)).setText(slyCalendarData.isFirstMonday() ? getContext().getString(R.string.slycalendar_tue) : getContext().getString(R.string.slycalendar_mon));
        ((TextView) findViewById(R.id.day3)).setText(slyCalendarData.isFirstMonday() ? getContext().getString(R.string.slycalendar_wed) : getContext().getString(R.string.slycalendar_tue));
        ((TextView) findViewById(R.id.day4)).setText(slyCalendarData.isFirstMonday() ? getContext().getString(R.string.slycalendar_thu) : getContext().getString(R.string.slycalendar_wed));
        ((TextView) findViewById(R.id.day5)).setText(slyCalendarData.isFirstMonday() ? getContext().getString(R.string.slycalendar_fri) : getContext().getString(R.string.slycalendar_thu));
        ((TextView) findViewById(R.id.day6)).setText(slyCalendarData.isFirstMonday() ? getContext().getString(R.string.slycalendar_sat) : getContext().getString(R.string.slycalendar_fri));
        ((TextView) findViewById(R.id.day7)).setText(slyCalendarData.isFirstMonday() ? getContext().getString(R.string.slycalendar_sun) : getContext().getString(R.string.slycalendar_sat));

        GridAdapter adapter = new GridAdapter(getContext(), slyCalendarData, null);
        ((GridView) findViewById(R.id.calendarGrid)).setAdapter(adapter);


    }

}
