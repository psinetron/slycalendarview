package ru.slybeaver.truecalendar

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ru.slybeaver.slycalendarview.SlyCalendarDialog
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by danielvilha on 28/05/20
 */
class MainActivity : AppCompatActivity(), SlyCalendarDialog.Callback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btnShowCalendar).setOnClickListener {
            SlyCalendarDialog()
                    .setSingle(false)
                    ?.setFirstMonday(false)
                    ?.setCallback(this@MainActivity)
                    ?.show(supportFragmentManager, "TAG_SLYCALENDAR")
        }

        findViewById<View>(R.id.btnShowCalendarHidden).setOnClickListener {
            SlyCalendarDialog()
                .setSingle(false)
                ?.setFirstMonday(false)
                ?.showTime(false)
                ?.setCallback(this@MainActivity)
                ?.show(supportFragmentManager, "TAG_SLYCALENDAR")
        }
    }

    override fun onCancelled() {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
    }

    override fun onDataSelected(firstDate: Calendar?, secondDate: Calendar?, hours: Int, minutes: Int) {
        if (firstDate != null) {
            if (secondDate == null) {
                firstDate[Calendar.HOUR_OF_DAY] = hours
                firstDate[Calendar.MINUTE] = minutes
                Snackbar.make(
                    findViewById(R.id.btnShowCalendar),
                    SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault()).format(firstDate.time),
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                secondDate[Calendar.HOUR_OF_DAY] = hours
                secondDate[Calendar.MINUTE] = minutes
                Snackbar.make(
                    findViewById(R.id.btnShowCalendar),
                    getString(
                        R.string.period,
                        SimpleDateFormat(getString(R.string.dateFormat), Locale.getDefault()).format(firstDate.time),
                        SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault()).format(secondDate.time)
                    ),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        } else {
            Snackbar.make(
                findViewById(R.id.btnShowCalendar),
                "No days selected",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}