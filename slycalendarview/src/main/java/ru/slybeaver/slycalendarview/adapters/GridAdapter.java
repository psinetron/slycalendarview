package ru.slybeaver.slycalendarview.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.slybeaver.slycalendarview.R;
import ru.slybeaver.slycalendarview.SlyCalendarData;
import ru.slybeaver.slycalendarview.listeners.DateSelectListener;

/**
 * Created by psinetron on 29/11/2018.
 * http://slybeaver.ru
 */
public class GridAdapter extends ArrayAdapter {

    private SlyCalendarData calendarData = null;
    private ArrayList<Date> monthlyDates = new ArrayList<>();
    private DateSelectListener listener = null;
    private LayoutInflater inflater = null;
    private final int MAX_CALENDAR_COLUMN = 42;

    public GridAdapter(@NonNull Context context,  @NonNull SlyCalendarData calendarData, @Nullable DateSelectListener listener) {
        super(context, R.layout.slycalendar_single_cell);
        this.calendarData = calendarData;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
        init();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(monthlyDates.get(position));

        Calendar calendarStart = null;
        if (calendarData.getSelectedStartDate()!=null) {
            calendarStart = Calendar.getInstance();
            calendarStart.setTime(calendarData.getSelectedStartDate());
        }
        Calendar calendarEnd = null;
        if (calendarData.getSelectedEndDate()!=null) {
            calendarEnd = Calendar.getInstance();
            calendarEnd.setTime(calendarData.getSelectedEndDate());
        }



        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.slycalendar_single_cell, parent, false);
        }

        ((TextView) view.findViewById(R.id.txtDate)).setText(String.valueOf(dateCal.get(Calendar.DAY_OF_MONTH)));

        view.findViewById(R.id.cellView).setBackgroundResource(R.color.slycalendar_defBackgroundColor);
        if (calendarStart!=null && calendarEnd!=null) {
            if (dateCal.get(Calendar.DAY_OF_YEAR) == calendarStart.get(Calendar.DAY_OF_YEAR)) {
                LayerDrawable shape = (LayerDrawable) ContextCompat.getDrawable(getContext(), R.drawable.slycalendar_start_day);
                ((GradientDrawable)shape.findDrawableByLayerId(R.id.dateShapeItem)).setColor(calendarData.getSelectedColor());
                (shape.findDrawableByLayerId(R.id.dateShapeItem)).setAlpha(20);
                view.findViewById(R.id.cellView).setBackground(shape);
            } else if (dateCal.get(Calendar.DAY_OF_YEAR) > calendarStart.get(Calendar.DAY_OF_YEAR) && dateCal.get(Calendar.DAY_OF_YEAR)<calendarEnd.get(Calendar.DAY_OF_YEAR)) {
                LayerDrawable shape = (LayerDrawable) ContextCompat.getDrawable(getContext(), R.drawable.slycalendar_middle_day);
                ((GradientDrawable)shape.findDrawableByLayerId(R.id.dateShapeItem)).setColor(calendarData.getSelectedColor());
                (shape.findDrawableByLayerId(R.id.dateShapeItem)).setAlpha(20);
                view.findViewById(R.id.cellView).setBackground(shape);
                if (position % 7 == 0) {
                    shape = (LayerDrawable) ContextCompat.getDrawable(getContext(), R.drawable.slycalendar_start_day);
                    ((GradientDrawable)shape.findDrawableByLayerId(R.id.dateShapeItem)).setColor(calendarData.getSelectedColor());
                    (shape.findDrawableByLayerId(R.id.dateShapeItem)).setAlpha(20);
                    view.findViewById(R.id.cellView).setBackground(shape);
                }
                if ((position+1) % 7 == 0) {
                    shape = (LayerDrawable) ContextCompat.getDrawable(getContext(), R.drawable.slycalendar_end_day);
                    ((GradientDrawable)shape.findDrawableByLayerId(R.id.dateShapeItem)).setColor(calendarData.getSelectedColor());
                    (shape.findDrawableByLayerId(R.id.dateShapeItem)).setAlpha(20);
                    view.findViewById(R.id.cellView).setBackground(shape);
                }


            } else if (dateCal.get(Calendar.DAY_OF_YEAR)==calendarEnd.get(Calendar.DAY_OF_YEAR)) {
                LayerDrawable shape = (LayerDrawable) ContextCompat.getDrawable(getContext(), R.drawable.slycalendar_end_day);
                ((GradientDrawable)shape.findDrawableByLayerId(R.id.dateShapeItem)).setColor(calendarData.getSelectedColor());
                (shape.findDrawableByLayerId(R.id.dateShapeItem)).setAlpha(20);
                view.findViewById(R.id.cellView).setBackground(shape);
            }
        }


        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(calendarData.getShowDate());

        if (calendarStart!=null && dateCal.get(Calendar.DAY_OF_YEAR)==calendarStart.get(Calendar.DAY_OF_YEAR) && currentDate.get(Calendar.MONTH) == dateCal.get(Calendar.MONTH)) {
            LayerDrawable shape = (LayerDrawable) ContextCompat.getDrawable(getContext(), R.drawable.slycalendar_selected_day);
            ((GradientDrawable)shape.findDrawableByLayerId(R.id.selectedDateShapeItem)).setColor(calendarData.getSelectedColor());
            view.findViewById(R.id.frameSelected).setBackground(shape);
            ((TextView)view.findViewById(R.id.txtDate)).setTextColor(calendarData.getSelectedTextColor());
            ((TextView)view.findViewById(R.id.txtDate)).setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        }

        if (calendarEnd!=null && dateCal.get(Calendar.DAY_OF_YEAR)==calendarEnd.get(Calendar.DAY_OF_YEAR) && currentDate.get(Calendar.MONTH) == dateCal.get(Calendar.MONTH)) {
            LayerDrawable shape = (LayerDrawable) ContextCompat.getDrawable(getContext(), R.drawable.slycalendar_selected_day);
            ((GradientDrawable)shape.findDrawableByLayerId(R.id.selectedDateShapeItem)).setColor(calendarData.getSelectedColor());
            view.findViewById(R.id.frameSelected).setBackground(shape);
            ((TextView)view.findViewById(R.id.txtDate)).setTextColor(calendarData.getSelectedTextColor());
        }

        if (currentDate.get(Calendar.MONTH) == dateCal.get(Calendar.MONTH)) {
            (view.findViewById(R.id.txtDate)).setAlpha(1);
        } else {
            (view.findViewById(R.id.txtDate)).setAlpha(.2f);
        }

        return view;
    }

    @Override
    public int getCount() {
        return monthlyDates.size();
    }

    @Nullable
    @Override
    public Date getItem(int position) {
        return monthlyDates.get(position);
    }

    @Override
    public int getPosition(Object item) {
        return monthlyDates.indexOf(item);
    }



    public void updateCalendarData(SlyCalendarData calendarData) {
        this.calendarData = calendarData;
        init();
        notifyDataSetChanged();
    }

    private void init() {
        monthlyDates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(calendarData.getShowDate());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = calendarData.isFirstMonday()? calendar.get(Calendar.DAY_OF_WEEK) - 2: calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while (monthlyDates.size() < MAX_CALENDAR_COLUMN) {
            monthlyDates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }
    }


}
