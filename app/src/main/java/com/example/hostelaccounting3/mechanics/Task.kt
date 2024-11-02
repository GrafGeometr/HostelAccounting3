package com.example.hostelaccounting3.mechanics

import com.example.hostelaccounting3.datetime.Datetime

data class Task(
    val id: Int,
    val name: String,
    val description: String,
    val roomId: Int,
    val datetimeOpened: Datetime,
    val datetimeClosed: Datetime?
) {
    val isOpened: Boolean get() = datetimeClosed == null
}