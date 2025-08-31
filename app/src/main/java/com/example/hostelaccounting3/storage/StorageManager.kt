package com.example.hostelaccounting3.storage

import android.content.SharedPreferences
import androidx.core.content.edit

class StorageManager(private val preferences: SharedPreferences) : Storage {
    override fun get(key: String) = preferences.getString(key, null)

    override fun set(key: String, value: String) = preferences.edit { putString(key, value) }

    override fun remove(key: String) = preferences.edit { remove(key) }
}
