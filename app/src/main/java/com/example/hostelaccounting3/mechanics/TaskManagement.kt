package com.example.hostelaccounting3.mechanics

import com.example.hostelaccounting3.BASE_URL
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.requests.RequestHelper
import com.example.hostelaccounting3.requests.map
import kotlinx.serialization.encodeToString

//fun Context.getTasks(roomId: Int) = tokenHelper.getToken()?.let { token ->
//    RequestHelper.Get(
//        url = "$BASE_URL/tasks/list", body = json.encodeToString(
//            mapOf("auth_token" to token.value, "room_id" to roomId.toString())
//        )
//    ).info().map { (body, _) ->
//        json.parseToJsonElement(body).jsonObject["tasks"]!!.jsonArray.map {
//            it.jsonObject
//        }.map {
//            Task(
//                it["id"]!!.jsonPrimitive.int,
//                it["name"]!!.jsonPrimitive.content,
//                it["description"]!!.jsonPrimitive.content,
//                it["room_id"]!!.jsonPrimitive.int,
//                it["datetime_opened"]!!.jsonPrimitive.content.toDatetime(),
//                it["datetime_closed"]!!.jsonPrimitive.contentOrNull?.toDatetime()
//            )
//        }
//    }.goAndWait()
//}!!


fun Context.deleteTask(roomId: Int, task: Task) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/tasks/delete", body = json.encodeToString(
            mapOf("auth_token" to token.value, "task_id" to task.id.toString(), "room_id" to roomId.toString())
        )
    ).request
}!!

fun Context.addTask(roomId: Int, task: Task) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/tasks/create", body = json.encodeToString(
            mapOf(
                "auth_token" to token.value,
                "room_id" to roomId.toString(),
                "name" to task.name,
                "description" to task.description,
                "task_id" to task.id.toString()
            )
        )
    ).info().map { (body, status) ->
        println("Task added - status: $status")
    }
}!!


fun Context.editTask(
    roomId: Int, task: Task
) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/tasks/update", body = json.encodeToString(mapOf(
            "auth_token" to token.value, "room_id" to roomId.toString(),

            "task_id" to task.id.toString(),

            "name" to task.name, // OPTIONAL
            "description" to task.description, // OPTIONAL
            "is_opened" to task.isOpened.toString() // OPTIONAL
        ).also { println(it) })
    ).info().map { (body, status) ->
        println("Task edited - status: $status")
    }
}!!

fun findAutocomplete(text: String, todoList: List<String>): List<String> = if (text.isEmpty()) emptyList() else {
    todoList.map { it.lowercase() }.distinct().filter {
        it.length >= text.length && it.contains(text.lowercase())
    }
}
