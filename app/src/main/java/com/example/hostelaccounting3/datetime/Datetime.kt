/**
 * Package containing date and time related classes and functions.
 */
package com.example.hostelaccounting3.datetime

import java.time.LocalDateTime

fun Int.prettyToString(length: Int = 2): String = toString().padStart(length, '0')

/**
 * Type alias for year number.
 */
typealias YearNumber = Int

/**
 * Type alias for month number.
 */
typealias MonthNumber = Int

/**
 * Type alias for day number.
 */
typealias DayNumber = Int

/**
 * Class representing a year.
 *
 * @property number the year number
 */
class Year(val number: YearNumber) {
    /**
     * Creates a new month object.
     *
     * @param number the month number
     * @return a new month object
     */
    fun Month(number: MonthNumber): Month = when {
        !(1..12).contains(number) -> throw IllegalArgumentException("Month number must be between 1 and 12")
        else -> object : Month {
            override val number = number
            override val daysCount = when (number) {
                1, 3, 5, 7, 8, 10, 12 -> 31
                2 -> if (isLeapYear()) 29 else 28
                else -> 30
            }
            override val year: Year = this@Year
        }
    }

    /**
     * Checks if the year is a leap year.
     *
     * @return true if the year is a leap year, false otherwise
     */
    fun isLeapYear() = (number % 4 == 0 && number % 100 != 0) || number % 400 == 0

    /**
     * The total number of days in the year.
     */
    val daysCount = if (isLeapYear()) 366 else 365
}

/**
 * Interface representing a month.
 */
interface Month {
    /**
     * The month number.
     */
    val number: MonthNumber

    /**
     * The total number of days in the month.
     */
    val daysCount: DayNumber

    /**
     * The year this month belongs to.
     */
    val year: Year

    /**
     * Creates a new day object.
     *
     * @param numberInMonth the day number in the month
     * @return a new day object
     */
    fun Day(numberInMonth: DayNumber): Day = when {
        !(1..daysCount).contains(numberInMonth) -> throw IllegalArgumentException("Day number of that month must be between 1 and $daysCount")
        else -> object : Day {
            override val number = numberInMonth
            override val month = this@Month
            override val year = this@Month.year
            override fun toString(): String =
                "${numberInMonth.prettyToString()}.${month.number.prettyToString()}.${year.number}"
        }
    }
}

/**
 * Interface representing a day.
 */
interface Day {
    /**
     * The day number.
     */
    val number: DayNumber

    /**
     * The month this day belongs to.
     */
    val month: Month

    /**
     * The year this day belongs to.
     */
    val year: Year

    /**
     * Calculates the number of days from the beginning of time (January 1, 1) to this day.
     *
     * @return the number of days from the beginning of time to this day
     */
    fun daysFromTheBeginningOfTheTimes(): Int =
        (1..<year.number).sumOf { Year(it).daysCount } + (1..<month.number).sumOf {
            year.Month(it).daysCount
        } + number

    /**
     * Calculates the difference in days between this day and another day.
     *
     * @param other the other day
     * @return the difference in days between this day and the other day
     */
    operator fun minus(other: Day): Int = daysFromTheBeginningOfTheTimes() - other.daysFromTheBeginningOfTheTimes()
}

/**
 * Type alias for date.
 */
typealias Date = Day

/**
 * Creates a new date object.
 *
 * @param year the year number
 * @param month the month number
 * @param day the day number
 * @return a new date object
 */
fun Date(year: YearNumber, month: MonthNumber, day: DayNumber): Date = Year(year).Month(month).Day(day)

/**
 * Converts a string to a date object.
 *
 * @param this the string to convert
 * @return a new date object
 */
fun String.toDate() = split(".").map { it.toInt() }.let {
    Year(it[2]).Month(it[1]).Day(it[0])
}

/**
 * Type alias for hour.
 */
typealias Hour = Int

/**
 * Type alias for minute.
 */
typealias Minute = Int

/**
 * Type alias for second.
 */
typealias Second = Int

/**
 * Type alias for millisecond.
 */
typealias Millisecond = Int

/**
 * Class representing a time.
 *
 * @property hour the hour
 * @property minute the minute
 * @property second the second
 */
class Time(private val hour: Hour, private val minute: Minute, private val second: Second) {
    override fun toString(): String = "${hour.prettyToString()}:${minute.prettyToString()}:${second.prettyToString()}"
}

/**
 * Converts a string to a time object.
 *
 * @param this the string to convert
 * @return a new time object
 */
fun String.toTime() = split(":").map { it.toInt() }.let { Time(it[0], it[1], it[2]) }

/**
 * Class representing a datetime.
 *
 * @property date the date
 * @property time the time
 */
class Datetime(val date: Date, val time: Time) {
    /**
     * Returns a string representation of the datetime.
     *
     * @return a string representation of the datetime
     */
    override fun toString(): String = "$date $time"

    /**
     * Companion object for Datetime class.
     */
    companion object {
        /**
         * Returns the current datetime.
         *
         * @return the current datetime
         */
        fun now(): Datetime {
            val now = LocalDateTime.now()
            return Datetime(
                Date(now.year, now.monthValue, now.dayOfMonth), Time(now.hour, now.minute, now.second)
            )
        }
    }
}

/**
 * Converts a string to a datetime object.
 *
 * @param this the string to convert
 * @return a new datetime object
 */
fun String.toDatetime() = split(" ").let { Datetime(it[0].toDate(), it[1].toTime()) }

