package com.example.hostelaccounting3.mechanics

import com.example.hostelaccounting3.datetime.Datetime

data class Purchase(val id: Int, val money: Int, val shortUsername: String, val description: String, val datetime: Datetime)