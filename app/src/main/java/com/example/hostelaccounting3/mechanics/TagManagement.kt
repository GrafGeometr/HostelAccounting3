package com.example.hostelaccounting3.mechanics

import com.example.hostelaccounting3.BASE_URL
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.datetime.toDatetime
import com.example.hostelaccounting3.requests.RequestHelper
import com.example.hostelaccounting3.requests.goAndWait
import com.example.hostelaccounting3.requests.goInParallel
import com.example.hostelaccounting3.requests.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

fun Context.getTags(roomId: Int) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Get(
        url = "$BASE_URL/tags/list", body = json.encodeToString(
            mapOf("auth_token" to token.value, "room_id" to roomId.toString())
        )
    ).info().map { (body, _) ->
        json.parseToJsonElement(body).jsonObject["tags"]!!.jsonArray.map {
            it.jsonObject
        }.map {
            Tag(
                it["id"]!!.jsonPrimitive.int, it["name"]!!.jsonPrimitive.content, it["room_id"]!!.jsonPrimitive.int
            )
        }
    }.goAndWait()
}!!


fun Context.deleteTag(tagId: Int) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/tags/delete", body = json.encodeToString(
            mapOf("auth_token" to token.value, "tag_id" to tagId.toString())
        )
    ).request.goInParallel()
}!!

fun Context.addTag(roomId: Int, name: String) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/tags/create", body = json.encodeToString(
            mapOf(
                "auth_token" to token.value, "room_id" to roomId.toString(), "name" to name
            )
        )
    ).info().map { (body, _) ->
        json.parseToJsonElement(body).jsonObject.let {
            Tag(
                it["id"]!!.jsonPrimitive.int, it["name"]!!.jsonPrimitive.content, it["room_id"]!!.jsonPrimitive.int
            )
        }
    }.goAndWait()
}!!


fun Context.editTag(
    roomId: Int, tagId: Int, name: String
) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/tags/update", body = json.encodeToString(
            mapOf(
                "auth_token" to token.value,
                "room_id" to roomId.toString(),
                "name" to name,
                "tag_id" to tagId.toString()
            )
        )
    ).info().map { (body, _) ->
        json.parseToJsonElement(body).jsonObject.let {
            Tag(
                it["id"]!!.jsonPrimitive.int, it["name"]!!.jsonPrimitive.content, it["room_id"]!!.jsonPrimitive.int
            )
        }
    }.goAndWait()
}!!