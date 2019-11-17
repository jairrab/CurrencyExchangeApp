package com.jairrab.data.util

import java.util.*
import javax.inject.Inject

class TimeUtils @Inject constructor() {

    private val cacheTimeLimitInMinutes = 30

    fun isCacheValid(cachedDataTimeStamp: Long): Boolean {
        val minutesElapsed = (currentTime - cachedDataTimeStamp) /
                             (1 * 60 * 1000.0)

        println("^^ Cache data is $minutesElapsed minutes old")

        return minutesElapsed <= cacheTimeLimitInMinutes
    }

    val currentTime: Long
        get() = Calendar.getInstance().timeInMillis

}