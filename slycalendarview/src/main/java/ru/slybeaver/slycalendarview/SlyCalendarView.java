package ru.slybeaver.slycalendarview;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.slybeaver.slycalendarview.listeners.DateSelectListener;
import ru.slybeaver.slycalendarview.listeners.DialogCompleteListener;

/**
 * Created by psinetron on 29/11/2018.
 * http://slybeaver.ru
 */
public class SlyCalendarView extends FrameLayout implements DateSelectListener {

    private SlyCalendarData slyCalendarData = new SlyCalendarData();

    private SlyCalendarDialog.Callback callback = null;

    private DialogCompleteListener completeListener = null;

    private AttributeSet attrs = null;
    private int defStyleAttr = 0;
    private ViewPager vpager;
    private LinearLayout optionsBar;
    private TextView cancelOption;
    private TextView saveOption;
    private TextView headerText;
    private TextView timeView;
    private TextView periodView;
    private View previousMonth;
    private View nextMonth;


    public SlyCalendarView(Context context) {
        super(context);
        init(null, 0);
    }

    public SlyCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
    }

    public SlyCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs = attrs;
        this.defStyleAttr = defStyleAttr;

    }

    public void setCallback(@Nullable SlyCalendarDialog.Callback callback) {
        this.callback = callback;
    }

    public void setCompleteListener(@Nullable DialogCompleteListener completeListener) {
        this.completeListener = completeListener;
    }

    public void setSlyCalendarData(SlyCalendarData slyCalendarData) {
        this.slyCalendarData = slyCalendarData;
        init(attrs, defStyleAttr);
        showCalendar();
    }

    private void init(@Nullable AttributeSet attrs, int defStyle) {
        inflate(getContext(), R.layout.slycalendar_frame, this);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SlyCalendarView, defStyle, 0);

        if (slyCalendarData.getBackgroundColor() == null) {
            slyCalendarData.setBackgroundColor(typedArray.getColor(R.styleable.SlyCalendarView_backgroundColor, ContextCompat.getColor(getContext(), R.color.slycalendar_defBackgroundColor)));
        }
        if (slyCalendarData.getHeaderColor() == null) {
            slyCalendarData.setHeaderColor(typedArray.getColor(R.styleable.SlyCalendarView_headerColor, ContextCompat.getColor(getContext(), R.color.slycalendar_defHeaderColor)));
        }
        if (slyCalendarData.getHeaderTextColor() == null) {
            slyCalendarData.setHeaderTextColor(typedArray.getColor(R.styleable.SlyCalendarView_headerTextColor, ContextCompat.getColor(getContext(), R.color.slycalendar_defHeaderTextColor)));
        }
        if (slyCalendarData.getTextColor() == null) {
            slyCalendarData.setTextColor(typedArray.getColor(R.styleable.SlyCalendarView_textColor, ContextCompat.getColor(getContext(), R.color.slycalendar_defTextColor)));
        }
        if (slyCalendarData.getSelectedColor() == null) {
            slyCalendarData.setSelectedColor(typedArray.getColor(R.styleable.SlyCalendarView_selectedColor, ContextCompat.getColor(getContext(), R.color.slycalendar_defSelectedColor)));
        }
        if (slyCalendarData.getSelectedTextColor() == null) {
            slyCalendarData.setSelectedTextColor(typedArray.getColor(R.styleable.SlyCalendarView_selectedTextColor, ContextCompat.getColor(getContext(), R.color.slycalendar_defSelectedTextColor)));
        }

        typedArray.recycle();

        vpager = findViewById(R.id.content);
        optionsBar = findViewById(R.id.optionsBar);
        cancelOption = findViewById(R.id.txtCancel);
        saveOption = findViewById(R.id.txtSave);
        headerText = findViewById(R.id.txtYear);
        timeView = findViewById(R.id.txtTime);
        periodView = findViewById(R.id.txtSelectedPeriod);
        previousMonth = findViewById(R.id.btnMonthPrev);
        nextMonth = findViewById(R.id.btnMonthNext);


        vpager.setAdapter(new MonthPagerAdapter(slyCalendarData, this));
        vpager.setCurrentItem(vpager.getAdapter().getCount() / 2);

        showCalendar();
    }

    public void setBarOptionsEnabled(boolean enabled) {
        int visibility = GONE;

        if (enabled) {
            visibility = VISIBLE;
        }

        optionsBar.setVisibility(visibility);
    }

    private void showCalendar() {

        paintCalendar();
        if (slyCalendarData.isTimeEnabled())
            showTime();

        cancelOption.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onCancelled();
                }
                if (completeListener != null) {
                    completeListener.complete();
                }
            }
        });

        saveOption.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                completeSelectionOnCalendar();
            }
        });


        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = null;
        if (slyCalendarData.getSelectedStartDate() != null) {
            calendarStart.setTime(slyCalendarData.getSelectedStartDate());
        } else {
            calendarStart.setTime(slyCalendarData.getShowDate());
        }

        if (slyCalendarData.getSelectedEndDate() != null) {
            calendarEnd = Calendar.getInstance();
            calendarEnd.setTime(slyCalendarData.getSelectedEndDate());
        }


        if (calendarEnd == null) {
            periodView.setText(
                    new SimpleDateFormat("EE, dd MMMM", Locale.getDefault()).format(calendarStart.getTime())
            );
        } else {
            if (calendarStart.get(Calendar.MONTH) == calendarEnd.get(Calendar.MONTH)) {
                periodView.setText(
                        getContext().getString(R.string.slycalendar_dates_period, new SimpleDateFormat("EE, dd", Locale.getDefault()).format(calendarStart.getTime()), new SimpleDateFormat("EE, dd MMM", Locale.getDefault()).format(calendarEnd.getTime()))
                );
            } else {
                periodView.setText(
                        getContext().getString(R.string.slycalendar_dates_period, new SimpleDateFormat("EE, dd MMM", Locale.getDefault()).format(calendarStart.getTime()), new SimpleDateFormat("EE, dd MMM", Locale.getDefault()).format(calendarEnd.getTime()))
                );
            }
        }


        previousMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                vpager.setCurrentItem(vpager.getCurrentItem() - 1);
            }
        });

        nextMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                vpager.setCurrentItem(vpager.getCurrentItem() + 1);
            }
        });


        if (slyCalendarData.isTimeEnabled())
            timeView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    int style = R.style.SlyCalendarTimeDialogTheme;
                    if (slyCalendarData.getTimeTheme() != null) {
                        style = slyCalendarData.getTimeTheme();
                    }

                    TimePickerDialog tpd = new TimePickerDialog(getContext(), style, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            slyCalendarData.setSelectedHour(hourOfDay);
                            slyCalendarData.setSelectedMinutes(minute);
                            showTime();
                        }
                    }, slyCalendarData.getSelectedHour(), slyCalendarData.getSelectedMinutes(), true);
                    tpd.show();
                }
            });

        vpager.getAdapter().notifyDataSetChanged();
        vpager.invalidate();

    }

    public void completeSelectionOnCalendar() {
        if (callback != null) {
            Calendar start = null;
            Calendar end = null;
            if (slyCalendarData.getSelectedStartDate() != null) {
                start = Calendar.getInstance();
                start.setTime(slyCalendarData.getSelectedStartDate());
            }
            if (slyCalendarData.getSelectedEndDate() != null) {
                end = Calendar.getInstance();
                end.setTime(slyCalendarData.getSelectedEndDate());
            }
            callback.onDataSelected(start, end, slyCalendarData.getSelectedHour(), slyCalendarData.getSelectedMinutes());
        }
        if (completeListener != null) {
            completeListener.complete();
        }
    }

    @Override
    public void dateSelect(Date selectedDate) {
        if (slyCalendarData.getSelectedStartDate() == null || slyCalendarData.isSingle()) {
            slyCalendarData.setSelectedStartDate(selectedDate);
            showCalendar();
            return;
        }
        if (slyCalendarData.getSelectedEndDate() == null) {
            if (selectedDate.getTime() < slyCalendarData.getSelectedStartDate().getTime()) {
                slyCalendarData.setSelectedEndDate(slyCalendarData.getSelectedStartDate());
                slyCalendarData.setSelectedStartDate(selectedDate);
                showCalendar();
                return;
            } else if (selectedDate.getTime() == slyCalendarData.getSelectedStartDate().getTime()) {
                slyCalendarData.setSelectedEndDate(null);
                slyCalendarData.setSelectedStartDate(selectedDate);
                showCalendar();
                return;
            } else if (selectedDate.getTime() > slyCalendarData.getSelectedStartDate().getTime()) {
                slyCalendarData.setSelectedEndDate(selectedDate);
                showCalendar();
                return;
            }
        }
        if (slyCalendarData.getSelectedEndDate() != null) {
            slyCalendarData.setSelectedEndDate(null);
            slyCalendarData.setSelectedStartDate(selectedDate);
            showCalendar();
        }
    }

    public void setHeaderText(String value) {
        headerText.setText(value);
    }

    public void setPeriodText(String value) {
        periodView.setText(value);
    }

    public void setFontOnHeaderText(Typeface typeface) {
        headerText.setTypeface(typeface);
    }

    public void setFontOnPeriodTime(Typeface typeface) {
        periodView.setTypeface(typeface);
    }


    public boolean hasAllDatesBeenSelected() {

        if (slyCalendarData.isSingle()) {
            return slyCalendarData.getSelectedStartDate() != null;
        }
        return slyCalendarData.getSelectedStartDate() != null && slyCalendarData.getSelectedEndDate() != null;

    }

    @Override
    public void dateLongSelect(Date selectedDate) {
        slyCalendarData.setSelectedEndDate(null);
        slyCalendarData.setSelectedStartDate(selectedDate);
        showCalendar();
    }

    private void paintCalendar() {
        findViewById(R.id.mainFrame).setBackgroundColor(slyCalendarData.getBackgroundColor());
        findViewById(R.id.headerView).setBackgroundColor(slyCalendarData.getHeaderColor());
        headerText.setTextColor(slyCalendarData.getHeaderTextColor());
        periodView.setTextColor(slyCalendarData.getHeaderTextColor());
        timeView.setTextColor(slyCalendarData.getHeaderColor());

    }


    private void showTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, slyCalendarData.getSelectedHour());
        calendar.set(Calendar.MINUTE, slyCalendarData.getSelectedMinutes());
        timeView.setText(
                new SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.getTime())
        );

    }

}
