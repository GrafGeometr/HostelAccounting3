package com.example.hostelaccounting3.mechanics

import com.example.hostelaccounting3.BASE_URL
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.requests.RequestHelper
import com.example.hostelaccounting3.requests.goAndWait
import com.example.hostelaccounting3.requests.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

data class UserData(val id: Int, val name: String)

fun Context.getUserData() = tokenHelper.getToken()?.let {
    RequestHelper.Get(
        BASE_URL + "auth/me", headers = emptyList(), body = json.encodeToString(
            mapOf("auth_token" to it.value)
        )
    ).info().map { (body, status) ->
        println("Data is here!")
        if (status.value == 403) return@map null
        val json = json.parseToJsonElement(body).jsonObject
        UserData(json["id"]?.jsonPrimitive?.int!!, json["name"]?.jsonPrimitive?.content!!)
    }.goAndWait()
}
