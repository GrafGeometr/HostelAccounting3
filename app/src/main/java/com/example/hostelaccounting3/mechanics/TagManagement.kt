package com.example.hostelaccounting3.mechanics

import com.example.hostelaccounting3.BASE_URL
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.requests.RequestHelper
import com.example.hostelaccounting3.requests.map
import kotlinx.serialization.encodeToString

//fun Context.getTags(roomId: Int) = tokenHelper.getToken()?.let { token ->
//    RequestHelper.Get(
//        url = "$BASE_URL/tags/list", body = json.encodeToString(
//            mapOf("auth_token" to token.value, "room_id" to roomId.toString())
//        )
//    ).info().map { (body, _) ->
//        json.parseToJsonElement(body).jsonObject["tags"]!!.jsonArray.map {
//            it.jsonObject
//        }.map {
//            Tag(
//                it["id"]!!.jsonPrimitive.int, it["name"]!!.jsonPrimitive.content, it["room_id"]!!.jsonPrimitive.int
//            )
//        }
//    }.goAndWait()
//}!!


fun Context.deleteTag(tagId: Int) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/tags/delete", body = json.encodeToString(
            mapOf("auth_token" to token.value, "tag_id" to tagId.toString())
        )
    ).request
}!!

fun Context.addTag(roomId: Int, tag: Tag) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/tags/create", body = json.encodeToString(
            mapOf(
                "auth_token" to token.value,
                "room_id" to roomId.toString(),
                "name" to tag.name,
                "tag_id" to tag.id.toString()
            )
        )
    ).info().map { (body, status) ->
        println("Tag added - status: $status")
    }
}!!


fun Context.editTag(
    roomId: Int, tag: Tag
) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/tags/update", body = json.encodeToString(
            mapOf(
                "auth_token" to token.value,
                "room_id" to roomId.toString(),
                "name" to tag.name,
                "tag_id" to tag.id.toString()
            )
        )
    ).info().map { (body, status) ->
        println("Tag edited - status: $status")
    }
}!!