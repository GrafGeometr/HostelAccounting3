package com.example.hostelaccounting3.mechanics

import com.example.hostelaccounting3.BASE_URL
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.requests.RequestHelper
import com.example.hostelaccounting3.requests.map
import kotlinx.serialization.encodeToString

//fun Context.getPurchases(roomId: Int) = tokenHelper.getToken()?.let { token ->
//    RequestHelper.Get(
//        url = "$BASE_URL/purchases/list", body = json.encodeToString(
//            mapOf("auth_token" to token.value, "room_id" to roomId.toString())
//        )
//    ).info().map { (body, _) ->
//        json.parseToJsonElement(body).jsonObject["purchases"]!!.jsonArray.map {
//            it.jsonObject
//        }.map {
//            Purchase(
//                it["id"]!!.jsonPrimitive.int,
//                it["money"]!!.jsonPrimitive.int,
//                it["short_username"]!!.jsonPrimitive.content,
//                it["description"]!!.jsonPrimitive.content,
//                it["datetime_bought"]!!.jsonPrimitive.content.toDatetime()
//            )
//        }
//    }.goAndWait()
//}!!

fun Context.deletePurchase(purchaseId: Int) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/purchases/delete", body = json.encodeToString(
            mapOf("auth_token" to token.value, "purchase_id" to purchaseId.toString())
        )
    ).request
}!!

fun Context.addPurchase(purchase: Purchase, roomId: Int) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/purchases/create", body = json.encodeToString(
            mapOf(
                "auth_token" to token.value,
                "room_id" to roomId.toString(),
                "money" to purchase.money.toString(),
                "description" to purchase.description,
                "purchase_id" to purchase.id.toString()
            )
        )
    ).info().map { (body, status) ->
        println("Purchase added - status: $status")
    }
}!!


fun Context.editPurchase(
    roomId: Int, newPurchase: Purchase
) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/purchases/update", body = json.encodeToString(
            mapOf(
                "auth_token" to token.value,
                "room_id" to roomId.toString(),
                "money" to newPurchase.money.toString(),
                "description" to newPurchase.description,
                "purchase_id" to newPurchase.id.toString()
            )
        )
    ).info().map { (body, status) ->
        println("Purchase edited - status: $status")
    }
}!!