package ru.slybeaver.slycalendarview

import java.util.*

/**
 * Created by psinetron on 27/05/20
 * http://slybeaver.ru
 */
class SlyCalendarData(
        showDate: Date? = null,
        var selectedStartDate: Date? = null,
        var selectedEndDate: Date? = null,
        var selectedHour: Int = 0,
        var selectedMinutes: Int = 0,
        var firstMonday: Boolean = true,
        var showTime: Boolean = true,
        var single: Boolean = true,
        var backgroundColor: Int? = null,
        var headerColor: Int? = null,
        var headerTextColor: Int? = null,
        var textColor: Int? = null,
        var selectedColor: Int? = null,
        var selectedTextColor: Int? = null,
        var timeTheme: Int? = null
) {
        var showDate: Date? = showDate
                ?: (showDate
                    ?: if (selectedStartDate != null) {
                            selectedStartDate!!.clone() as Date
                    } else {
                            Calendar.getInstance().time
                    })
}