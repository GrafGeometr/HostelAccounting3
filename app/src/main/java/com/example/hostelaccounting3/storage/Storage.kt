package com.example.hostelaccounting3.storage

interface Storage {
    fun get(key: String): String?

    fun set(key: String, value: String)
    fun remove(key: String)
}