package com.mooncascade.di

import android.content.Context
import com.mooncascade.di.qualifier.ApplicationScope
import dagger.hilt.android.qualifiers.ActivityContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


/**
 * Used as String formatter to show dates in a human readable format.
 */
@ApplicationScope
class DateTimeFormatter @Inject constructor(
    @ActivityContext private val context: Context
) {

    companion object {
        const val TAG = "DateFormatter"
    }


    private val formatter = SimpleDateFormat("yyyy-MM-dd") // 2022-05-16
    private val formatterWeekday = SimpleDateFormat("EEEE") // Tuesday
    private val formatterWeekdayShort = SimpleDateFormat("EEE") // Tuesday


    /**
     * @sample Tuesday or Tue:
     * @return weekday: [String]
     */
    fun parseDateToWeekdayFormat(dateString: String, short: Boolean = true): String {
        val date = formatter.parse(dateString)!!
        return if (short) formatterWeekdayShort.format(date) else formatterWeekday.format(date)
    }

    /**
     * @return isDay: [Boolean]
     */
    fun isDay(): Boolean {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        return hour in 6..17
    }

}
