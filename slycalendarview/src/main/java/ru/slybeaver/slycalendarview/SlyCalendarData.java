package ru.slybeaver.slycalendarview;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by psinetron on 29/11/2018.
 * http://slybeaver.ru
 */
public class SlyCalendarData {

    private Date showDate = null; //current showing date
    private Date selectedStartDate = null; // first selected date
    private Date selectedEndDate = null; // ended selected date
    private boolean firstMonday = true; // is first monday

    private Integer backgroundColor = null;
    private Integer headerColor = null;
    private Integer headerTextColor = null;
    private Integer textColor = null;
    private Integer selectedColor = null;
    private Integer selectedTextColor = null;


    public Date getShowDate() {
        if (showDate == null) {
            showDate = Calendar.getInstance().getTime();
        }
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public Date getSelectedStartDate() {
        return selectedStartDate;
    }

    public void setSelectedStartDate(Date selectedStartDate) {
        this.selectedStartDate = selectedStartDate;
    }

    public Date getSelectedEndDate() {
        return selectedEndDate;
    }

    public void setSelectedEndDate(Date selectedEndDate) {
        this.selectedEndDate = selectedEndDate;
    }

    public boolean isFirstMonday() {
        return firstMonday;
    }

    public void setFirstMonday(boolean firstMonday) {
        this.firstMonday = firstMonday;
    }

    public Integer getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Integer backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Integer getHeaderColor() {
        return headerColor;
    }

    public void setHeaderColor(Integer headerColor) {
        this.headerColor = headerColor;
    }

    public Integer getHeaderTextColor() {
        return headerTextColor;
    }

    public void setHeaderTextColor(Integer headerTextColor) {
        this.headerTextColor = headerTextColor;
    }

    public Integer getTextColor() {
        return textColor;
    }

    public void setTextColor(Integer textColor) {
        this.textColor = textColor;
    }

    public Integer getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Integer selectedColor) {
        this.selectedColor = selectedColor;
    }

    public Integer getSelectedTextColor() {
        return selectedTextColor;
    }

    public void setSelectedTextColor(Integer selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }
}
