package ru.slybeaver.slycalendarview;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import ru.slybeaver.slycalendarview.listeners.DateSelectListener;
import ru.slybeaver.slycalendarview.listeners.GridChangeListener;

/**
 * Created by psinetron on 06/12/2018.
 * http://slybeaver.ru
 */
public class MonthPagerAdapter extends PagerAdapter {

    private SlyCalendarData slyCalendarData;
    private DateSelectListener listener;
    private ArrayList tags = new ArrayList();
    private final String TAG_PREFIX = "SLY_CAL_TAG";

    MonthPagerAdapter(SlyCalendarData slyCalendarData, DateSelectListener listener) {
        this.slyCalendarData = slyCalendarData;
        this.listener = listener;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        tags.remove(((View) object).getTag().toString());
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        int indexShift = position - (getCount() / 2);


        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.slycalendar_calendar, container, false);

        final GridAdapter adapter = new GridAdapter(container.getContext(), slyCalendarData, indexShift, listener, new GridChangeListener() {
            @Override
            public void gridChanged() {
                tags.add(TAG_PREFIX + (position + 1));
                tags.add(TAG_PREFIX + (position - 1));
                notifyDataSetChanged();
            }
        });
        ((GridView) view.findViewById(R.id.calendarGrid)).setAdapter(adapter);

        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(slyCalendarData.getShowDate());
        currentDate.add(Calendar.MONTH, indexShift);
        ((TextView) view.findViewById(R.id.txtSelectedMonth)).setText(new SimpleDateFormat("LLLL yyyy", Locale.getDefault()).format(currentDate.getTime()));

        view.setTag(TAG_PREFIX + position);
        container.addView(view);

        setDates(view, R.id.day1, R.id.day2, R.id.day3, R.id.day4, R.id.day5, R.id.day6, R.id.day7);
        return view;
    }

    private void setDates(View view, int... ids) {
        Calendar weekDays = Calendar.getInstance();
        weekDays.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EE", Locale.getDefault());
        for (int id : ids) {
            ((TextView) view.findViewById(id)).setText(dateFormat.format(weekDays.getTime()).substring(0, 1).toUpperCase() + dateFormat.format(weekDays.getTime()).substring(1));
            weekDays.add(Calendar.DAY_OF_WEEK, 1);
        }
    }

    @Override
    public int getItemPosition(Object object) {
        String tag = ((ViewGroup) object).getTag().toString();
        if (tags.contains(tag)) {
            tags.remove(tag);
            return POSITION_NONE;
        }
        return POSITION_UNCHANGED;

    }


}
