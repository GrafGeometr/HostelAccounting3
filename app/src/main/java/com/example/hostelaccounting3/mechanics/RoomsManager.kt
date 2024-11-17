package com.example.hostelaccounting3.mechanics

import com.example.hostelaccounting3.BASE_URL
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.requests.RequestHelper
import com.example.hostelaccounting3.requests.goAndWait
import com.example.hostelaccounting3.requests.map
import com.example.hostelaccounting3.storage.Storage
import io.ktor.server.util.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class RoomsManager(private val storage: Storage) {


    fun getCurrentRoomId() = storage.get("currentRoomId")?.toIntOrNull()

    fun setCurrentRoomId(id: Int) = storage.set("currentRoomId", id.toString())

    fun killRoom() = storage.remove("currentRoomId")
}

fun Context.createNewRoom(roomName: String) = RequestHelper.Post(
    url = "$BASE_URL/rooms/create", body = json.encodeToString(
        mapOf(
            "auth_token" to tokenHelper.getToken()!!.value, "name" to roomName
        )
    )
).info().map { (body, status) ->
    json.parseToJsonElement(body).jsonObject.let {
        Room(
            it["id"]!!.jsonPrimitive.int,
            it["name"]!!.jsonPrimitive.content,
            it["code"]!!.jsonPrimitive.content,
            it["short_name"]!!.jsonPrimitive.content
        )
    }
}.goAndWait()

fun Context.joinRoom(code: String) = RequestHelper.Post(
    url = "$BASE_URL/rooms/join", body = json.encodeToString(
        mapOf(
            "auth_token" to tokenHelper.getToken()!!.value, "code" to code
        )
    )
).info().map { (body, status) ->
    json.parseToJsonElement(body).jsonObject.let {
        Room(
            it["id"]!!.jsonPrimitive.int,
            it["name"]!!.jsonPrimitive.content,
            it["code"]!!.jsonPrimitive.content,
            it["short_name"]!!.jsonPrimitive.content
        )
    }
}.goAndWait()
