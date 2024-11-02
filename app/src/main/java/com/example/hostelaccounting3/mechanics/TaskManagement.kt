package com.example.hostelaccounting3.mechanics

import com.example.hostelaccounting3.BASE_URL
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.datetime.toDatetime
import com.example.hostelaccounting3.requests.RequestHelper
import com.example.hostelaccounting3.requests.goAndWait
import com.example.hostelaccounting3.requests.goInParallel
import com.example.hostelaccounting3.requests.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

fun Context.getTasks(roomId: Int) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Get(
        url = "$BASE_URL/tasks/list", body = json.encodeToString(
            mapOf("auth_token" to token.value, "room_id" to roomId.toString())
        )
    ).info().map { (body, _) ->
        json.parseToJsonElement(body).jsonObject["tasks"]!!.jsonArray.map {
            it.jsonObject
        }.map {
            Task(
                it["id"]!!.jsonPrimitive.int,
                it["name"]!!.jsonPrimitive.content,
                it["description"]!!.jsonPrimitive.content,
                it["room_id"]!!.jsonPrimitive.int,
                it["datetime_opened"]!!.jsonPrimitive.content.toDatetime(),
                it["datetime_closed"]!!.jsonPrimitive.contentOrNull?.toDatetime()
            )
        }
    }.goAndWait()
}!!


fun Context.deleteTask(taskId: Int) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/tasks/delete", body = json.encodeToString(
            mapOf("auth_token" to token.value, "task_id" to taskId.toString())
        )
    ).request.goInParallel()
}!!

fun Context.addTask(roomId: Int, name: String, description: String) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/tasks/create", body = json.encodeToString(
            mapOf(
                "auth_token" to token.value,
                "room_id" to roomId.toString(),
                "name" to name,
                "description" to description
            )
        )
    ).info().map { (body, _) ->
        json.parseToJsonElement(body).jsonObject.let {
            Task(
                it["id"]!!.jsonPrimitive.int,
                it["name"]!!.jsonPrimitive.content,
                it["description"]!!.jsonPrimitive.content,
                it["room_id"]!!.jsonPrimitive.int,
                it["datetime_opened"]!!.jsonPrimitive.content.toDatetime(),
                it["datetime_closed"]!!.jsonPrimitive.contentOrNull?.toDatetime()
            )
        }
    }.goAndWait()
}!!


fun Context.editTask(
    roomId: Int, taskId: Int, name: String, description: String, isOpened: Boolean
) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/tasks/update", body = json.encodeToString(
            mapOf(
                "auth_token" to token.value,
                "room_id" to roomId.toString(),

                "task_id" to taskId.toString(),

                "name" to name, // OPTIONAL
                "description" to description, // OPTIONAL
                "is_opened" to isOpened.toString() // OPTIONAL
            ).also { println(it) }
        )
    ).info().map { (body, _) ->
        json.parseToJsonElement(body).jsonObject.let {
            Task(
                it["id"]!!.jsonPrimitive.int,
                it["name"]!!.jsonPrimitive.content,
                it["description"]!!.jsonPrimitive.content,
                it["room_id"]!!.jsonPrimitive.int,
                it["datetime_opened"]!!.jsonPrimitive.content.toDatetime(),
                it["datetime_closed"]!!.jsonPrimitive.contentOrNull?.toDatetime()
            )
        }
    }.goAndWait()
}!!

fun findAutocomplete(text: String, todoList: List<String>): List<String> = if (text.isEmpty()) emptyList() else {
    todoList.map { it.lowercase() }.distinct().filter {
        it.length >= text.length && it.contains(text.lowercase())
    }
}
