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

fun Context.getPurchases(roomId: Int) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Get(
        url = "$BASE_URL/purchases/list", body = json.encodeToString(
            mapOf("auth_token" to token.value, "room_id" to roomId.toString())
        )
    ).info().map { (body, _) ->
        json.parseToJsonElement(body).jsonObject["purchases"]!!.jsonArray.map {
            it.jsonObject
        }.map {
            Purchase(
                it["id"]!!.jsonPrimitive.int,
                it["money"]!!.jsonPrimitive.int,
                it["short_username"]!!.jsonPrimitive.content,
                it["description"]!!.jsonPrimitive.content,
                it["datetime_bought"]!!.jsonPrimitive.content.toDatetime()
            )
        }
    }.goAndWait()
}!!

fun Context.deletePurchase(purchaseId: Int) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/purchases/delete", body = json.encodeToString(
            mapOf("auth_token" to token.value, "purchase_id" to purchaseId.toString())
        )
    ).request.goInParallel()
}!!

fun Context.addPurchase(roomId: Int, money: Int, description: String) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/purchases/create", body = json.encodeToString(
            mapOf(
                "auth_token" to token.value,
                "room_id" to roomId.toString(),
                "money" to money.toString(),
                "description" to description
            )
        )
    ).info().map { (body, _) ->
        json.parseToJsonElement(body).jsonObject.let {
            Purchase(
                it["id"]!!.jsonPrimitive.int,
                it["money"]!!.jsonPrimitive.int,
                it["short_username"]!!.jsonPrimitive.content,
                it["description"]!!.jsonPrimitive.content,
                it["datetime_bought"]!!.jsonPrimitive.content.toDatetime()
            )
        }
    }.goAndWait()
}!!


fun Context.editPurchase(
    roomId: Int, purchaseId: Int, money: Int, description: String
) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/purchases/update", body = json.encodeToString(
            mapOf(
                "auth_token" to token.value,
                "room_id" to roomId.toString(),
                "money" to money.toString(),
                "description" to description,
                "purchase_id" to purchaseId.toString()
            )
        )
    ).info().map { (body, _) ->
        json.parseToJsonElement(body).jsonObject.let {
            Purchase(
                it["id"]!!.jsonPrimitive.int,
                it["money"]!!.jsonPrimitive.int,
                it["short_username"]!!.jsonPrimitive.content,
                it["description"]!!.jsonPrimitive.content,
                it["datetime_bought"]!!.jsonPrimitive.content.toDatetime()
            )
        }
    }.goAndWait()
}!!