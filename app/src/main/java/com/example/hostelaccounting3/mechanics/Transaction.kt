package com.example.hostelaccounting3.mechanics

data class Transaction(val id: Int? = null, val fromUser: String, val fromUserId: Int, val toUser: String, val toUserId: Int, val money: Int)
