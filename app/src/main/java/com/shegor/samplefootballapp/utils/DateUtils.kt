package com.shegor.samplefootballapp.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateUtils {

    private val apiDateFormatter: DateTimeFormatter by lazy {
        DateTimeFormatter.ofPattern("yyyy-MM-dd")
    }

    private val uiDateFormatter: DateTimeFormatter by lazy {
        DateTimeFormatter.ofPattern("dd MMMM yyy")
    }

    fun milliToFormattedDateString(milli: Long): String {
        val instant = Instant.ofEpochMilli(milli)
        val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        return date.format(apiDateFormatter)
    }

    fun apiDateStringToLocalDate(apiDate: String?): LocalDate {
        return LocalDate.parse(apiDate, apiDateFormatter)
    }

    fun apiDateStringToUiDateString(apiDate: String?): String {
        return apiDateStringToLocalDate(apiDate).format(uiDateFormatter)
    }
}