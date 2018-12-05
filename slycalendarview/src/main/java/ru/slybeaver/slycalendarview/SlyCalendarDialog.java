package ru.slybeaver.slycalendarview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Date;

import ru.slybeaver.slycalendarview.listeners.DialogCompleteListener;

/**
 * Created by psinetron on 03/12/2018.
 * http://slybeaver.ru
 */
public class SlyCalendarDialog extends DialogFragment implements DialogCompleteListener {


    private SlyCalendarData slyCalendarData = new SlyCalendarData();
    private SlyCalendarView calendarView;
    private Callback callback = null;

    public SlyCalendarDialog setStartDate(@Nullable Date startDate) {
        slyCalendarData.setSelectedStartDate(startDate);
        return this;
    }

    public SlyCalendarDialog setEndDate(@Nullable Date endDate) {
        slyCalendarData.setSelectedEndDate(endDate);
        return this;
    }

    public SlyCalendarDialog setSingle(boolean single) {
        slyCalendarData.setSingle(single);
        return this;
    }

    public SlyCalendarDialog setFirstMonday(boolean firsMonday) {
        slyCalendarData.setFirstMonday(firsMonday);
        return this;
    }

    public SlyCalendarDialog setCallback(@Nullable Callback callback) {
        this.callback = callback;
        return this;
    }

    public SlyCalendarDialog setTimeTheme(@Nullable Integer themeResource) {
        slyCalendarData.setTimeTheme(themeResource);
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.SlyCalendarDialogStyle);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        calendarView = (SlyCalendarView)getActivity().getLayoutInflater().inflate(R.layout.slycalendar_main, container);
        calendarView.setSlyCalendarData(slyCalendarData);
        calendarView.setCallback(callback);
        calendarView.setCompleteListener(this);
        return calendarView;
    }

    @Override
    public void complete() {
        this.dismiss();
    }


    public interface Callback {
        void onCancelled();

        void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes);
    }



    public void setBackgroundColor(Integer backgroundColor) {
        slyCalendarData.setBackgroundColor(backgroundColor);
    }

    public void setHeaderColor(Integer headerColor) {
        slyCalendarData.setHeaderColor(headerColor);
    }

    public void setHeaderTextColor(Integer headerTextColor) {
        slyCalendarData.setHeaderTextColor(headerTextColor);
    }

    public void setTextColor(Integer textColor) {
        slyCalendarData.setTextColor(textColor);
    }

    public void setSelectedColor(Integer selectedColor) {
        slyCalendarData.setSelectedColor(selectedColor);
    }

    public void setSelectedTextColor(Integer selectedTextColor) {
        slyCalendarData.setSelectedTextColor(selectedTextColor);
    }


}
