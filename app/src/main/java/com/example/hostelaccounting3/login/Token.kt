package com.example.hostelaccounting3.login

import com.example.hostelaccounting3.storage.Storage

data class Token(val value: String)

class TokenHelper(private val storage: Storage) {
    private var authToken: Token? = null
    fun getToken() =
        if (authToken != null) authToken else storage.get("authToken")?.let { Token(it) }.also { authToken = it }

    fun setToken(token: Token) {
        storage.set("authToken", token.value)
        authToken = token
    }

    fun removeToken() {
        storage.remove("authToken")
        authToken = null
    }
}
