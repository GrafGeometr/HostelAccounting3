package com.example.hostelaccounting3.mechanics

import com.example.hostelaccounting3.BASE_URL
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.requests.RequestHelper
import com.example.hostelaccounting3.requests.goAndWait
import com.example.hostelaccounting3.requests.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

data class Room(val id: Int, val name: String, val code: String)

fun Context.getRooms() = tokenHelper.getToken()?.let { token ->
    RequestHelper.Get(
        url = "$BASE_URL/rooms/my",  body = json.encodeToString(
            mapOf("auth_token" to token.value)
        )
    ).info().map { (body, status) ->
        json.parseToJsonElement(body).jsonObject["rooms"]!!.jsonArray.map {
            it.jsonObject
        }.map {
            Room(
                it["id"]!!.jsonPrimitive.int,
                it["name"]!!.jsonPrimitive.content,
                it["code"]!!.jsonPrimitive.content
            )
        }
    }.goAndWait()
}!!
