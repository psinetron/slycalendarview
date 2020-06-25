package ru.slybeaver.slycalendarview

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import ru.slybeaver.slycalendarview.listeners.DateSelectListener
import ru.slybeaver.slycalendarview.listeners.GridChangeListener
import java.util.*

/**
 * Created by psinetron on 27/05/20
 * http://slybeaver.ru
 */
class GridAdapter(
        context: Context,
        calendarData: SlyCalendarData,
        shiftMonth: Int,
        listener: DateSelectListener?,
        gridListener: GridChangeListener?
) : BaseAdapter() {
    private var context: Context? = null
    private var calendarData: SlyCalendarData? = null
    private var listener: DateSelectListener? = null
    private var gridListener: GridChangeListener? = null
    private var shiftMonth = 0

    private var monthlyDates = ArrayList<Date>()
    private var inflater: LayoutInflater? = null

    init {
        this.context = context
        this.calendarData = calendarData
        this.listener = listener
        this.gridListener = gridListener
        this.shiftMonth = shiftMonth
        this.inflater = LayoutInflater.from(context)

        monthlyDates = ArrayList()
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.time = calendarData.showDate!!
        calendar.add(Calendar.MONTH, shiftMonth)
        calendar[Calendar.DAY_OF_MONTH] = 1
        val firstDayOfTheMonth = if (calendarData.firstMonday) calendar[Calendar.DAY_OF_WEEK] - 2 else calendar[Calendar.DAY_OF_WEEK] - 1
        calendar.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth)
        val MAX_CALENDAR_COLUMN = 42
        while (monthlyDates.size < MAX_CALENDAR_COLUMN) {
            monthlyDates.add(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val dateCal = Calendar.getInstance()
        dateCal.time = monthlyDates[position]
        var calendarStart: Calendar? = null
        if (calendarData?.selectedStartDate != null) {
            calendarStart = Calendar.getInstance()
            calendarStart.time = calendarData?.selectedStartDate!!
            calendarStart[Calendar.HOUR] = 0
            calendarStart[Calendar.MINUTE] = 0
            calendarStart[Calendar.SECOND] = 0
            calendarStart[Calendar.MILLISECOND] = 0
        }
        var calendarEnd: Calendar? = null
        if (calendarData?.selectedEndDate != null) {
            calendarEnd = Calendar.getInstance()
            calendarEnd.time = calendarData?.selectedEndDate!!
            calendarEnd[Calendar.HOUR] = 0
            calendarEnd[Calendar.MINUTE] = 0
            calendarEnd[Calendar.SECOND] = 0
            calendarEnd[Calendar.MILLISECOND] = 0
        }
        var view = convertView
        if (view == null) {
            view = inflater!!.inflate(R.layout.slycalendar_single_cell, parent, false)
        }
        (view!!.findViewById<View>(R.id.txtDate) as TextView).text = dateCal[Calendar.DAY_OF_MONTH].toString()
        view.findViewById<View>(R.id.cellView).setBackgroundResource(R.color.slycalendar_defBackgroundColor)
        view.findViewById<View>(R.id.cellView).setOnClickListener {
            val selectedDate = Calendar.getInstance()
            selectedDate.time = monthlyDates[position]
            selectedDate[Calendar.HOUR] = 0
            selectedDate[Calendar.MINUTE] = 0
            selectedDate[Calendar.SECOND] = 0
            selectedDate[Calendar.MILLISECOND] = 0
            listener?.dateSelect(selectedDate.time)
            notifyDataSetChanged()
            gridListener!!.gridChanged()
        }
        view.findViewById<View>(R.id.cellView).setOnLongClickListener {
            val selectedDate = Calendar.getInstance()
            selectedDate.time = monthlyDates[position]
            selectedDate[Calendar.HOUR] = 0
            selectedDate[Calendar.MINUTE] = 0
            selectedDate[Calendar.SECOND] = 0
            selectedDate[Calendar.MILLISECOND] = 0
            listener?.dateLongSelect(monthlyDates[position])
            notifyDataSetChanged()
            gridListener!!.gridChanged()
            true
        }
        view.findViewById<View>(R.id.cellView).setBackgroundColor(calendarData?.backgroundColor!!)
        if (calendarStart != null && calendarEnd != null) {
            if (dateCal[Calendar.DAY_OF_YEAR] == calendarStart[Calendar.DAY_OF_YEAR] && dateCal[Calendar.YEAR] == calendarStart[Calendar.YEAR]) {
                val shape = (ContextCompat.getDrawable(context!!, R.drawable.slycalendar_start_day) as LayerDrawable?)!!
                (shape.findDrawableByLayerId(R.id.dateShapeItem) as GradientDrawable).setColor(calendarData?.selectedColor!!)
                shape.findDrawableByLayerId(R.id.dateShapeItem).alpha = 20
                view.findViewById<View>(R.id.cellView).background = shape
            } else if (dateCal.timeInMillis > calendarStart.timeInMillis && dateCal.timeInMillis < calendarEnd.timeInMillis) {
                var shape = (ContextCompat.getDrawable(context!!, R.drawable.slycalendar_middle_day) as LayerDrawable?)!!
                (shape.findDrawableByLayerId(R.id.dateShapeItem) as GradientDrawable).setColor(calendarData?.selectedColor!!)
                shape.findDrawableByLayerId(R.id.dateShapeItem).alpha = 20
                view.findViewById<View>(R.id.cellView).background = shape
                if (position % 7 == 0) {
                    shape = ContextCompat.getDrawable(context!!, R.drawable.slycalendar_start_day) as LayerDrawable
                    assert(shape != null)
                    (shape.findDrawableByLayerId(R.id.dateShapeItem) as GradientDrawable).setColor(calendarData?.selectedColor!!)
                    shape.findDrawableByLayerId(R.id.dateShapeItem).alpha = 20
                    view.findViewById<View>(R.id.cellView).background = shape
                }
                if ((position + 1) % 7 == 0) {
                    shape = ContextCompat.getDrawable(context!!, R.drawable.slycalendar_end_day) as LayerDrawable
                    assert(shape != null)
                    (shape.findDrawableByLayerId(R.id.dateShapeItem) as GradientDrawable).setColor(calendarData?.selectedColor!!)
                    shape.findDrawableByLayerId(R.id.dateShapeItem).alpha = 20
                    view.findViewById<View>(R.id.cellView).background = shape
                }
            } else if (dateCal[Calendar.DAY_OF_YEAR] == calendarEnd[Calendar.DAY_OF_YEAR] && dateCal[Calendar.YEAR] == calendarEnd[Calendar.YEAR]) {
                val shape = (ContextCompat.getDrawable(context!!, R.drawable.slycalendar_end_day) as LayerDrawable?)!!
                (shape.findDrawableByLayerId(R.id.dateShapeItem) as GradientDrawable).setColor(calendarData?.selectedColor!!)
                shape.findDrawableByLayerId(R.id.dateShapeItem).alpha = 20
                view.findViewById<View>(R.id.cellView).background = shape
            }
        }
        val currentDate = Calendar.getInstance()
        currentDate.time = calendarData?.showDate!!
        currentDate.add(Calendar.MONTH, shiftMonth)
        view.findViewById<View>(R.id.frameSelected).setBackgroundResource(0)
        (view.findViewById<View>(R.id.txtDate) as TextView).setTextColor(calendarData?.textColor!!)
        if (calendarStart != null && dateCal[Calendar.DAY_OF_YEAR] == calendarStart[Calendar.DAY_OF_YEAR] && currentDate[Calendar.MONTH] == dateCal[Calendar.MONTH] && dateCal[Calendar.YEAR] == calendarStart[Calendar.YEAR]) {
            val shape = (ContextCompat.getDrawable(context!!, R.drawable.slycalendar_selected_day) as LayerDrawable?)!!
            (shape.findDrawableByLayerId(R.id.selectedDateShapeItem) as GradientDrawable).setColor(calendarData?.selectedColor!!)
            view.findViewById<View>(R.id.frameSelected).background = shape
            (view.findViewById<View>(R.id.txtDate) as TextView).setTextColor(calendarData?.selectedTextColor!!)
            (view.findViewById<View>(R.id.txtDate) as TextView).typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
        }
        if (calendarEnd != null && dateCal[Calendar.DAY_OF_YEAR] == calendarEnd[Calendar.DAY_OF_YEAR] && currentDate[Calendar.MONTH] == dateCal[Calendar.MONTH] && dateCal[Calendar.YEAR] == calendarEnd[Calendar.YEAR]) {
            val shape = (ContextCompat.getDrawable(context!!, R.drawable.slycalendar_selected_day) as LayerDrawable?)!!
            (shape.findDrawableByLayerId(R.id.selectedDateShapeItem) as GradientDrawable).setColor(calendarData?.selectedColor!!)
            view.findViewById<View>(R.id.frameSelected).background = shape
            (view.findViewById<View>(R.id.txtDate) as TextView).setTextColor(calendarData?.selectedTextColor!!)
        }
        if (currentDate[Calendar.MONTH] == dateCal[Calendar.MONTH]) {
            view.findViewById<View>(R.id.txtDate).alpha = 1f
        } else {
            view.findViewById<View>(R.id.txtDate).alpha = .2f
        }
        return view
    }

    override fun getCount(): Int {
        return monthlyDates.size
    }

    override fun getItem(position: Int): Date? {
        return monthlyDates[position]
    }

    override fun getItemId(position: Int): Long {
        return monthlyDates[position].time
    }
}