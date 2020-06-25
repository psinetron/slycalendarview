package ru.slybeaver.slycalendarview.listeners

import java.util.*

/**
 * Created by psinetron on 27/05/20
 * http://slybeaver.ru
 */
interface DateSelectListener {
    fun dateSelect(selectedDate: Date?)
    fun dateLongSelect(selectedDate: Date?)
}