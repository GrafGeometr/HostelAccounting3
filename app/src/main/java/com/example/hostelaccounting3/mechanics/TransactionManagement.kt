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

data class FinancesInfo(
    val totalMonth: Int,
    val averagePerUserMonth: Int,
    val spendTable: SpendTable,
    val transactionTable: TransactionTable
)

data class SpendTable(val table: List<UserSpend>)

data class UserSpend(val shortName: String, val userId: Int, val money: Int)

data class TransactionTable(val table: List<Transaction>)


fun Context.getFinancesInfo(roomId: Int) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Get(
        url = "$BASE_URL/finances/info", body = json.encodeToString(
            mapOf("auth_token" to token.value, "room_id" to roomId.toString())
        )
    ).info().map { (body, _) ->
        json.parseToJsonElement(body).jsonObject.let { financesJson ->
            FinancesInfo(
                totalMonth = financesJson["total_month"]!!.jsonPrimitive.int,
                averagePerUserMonth = financesJson["average_per_user_month"]!!.jsonPrimitive.int,
                spendTable = SpendTable(financesJson["spend_table"]!!.jsonArray.map {
                    UserSpend(
                        shortName = it.jsonObject["user"]!!.jsonObject["short_name"]!!.jsonPrimitive.content,
                        userId = it.jsonObject["user"]!!.jsonObject["user_id"]!!.jsonPrimitive.int,
                        money = it.jsonObject["money"]!!.jsonPrimitive.int
                    )
                }),
                transactionTable = TransactionTable(financesJson["transaction_table"]!!.jsonArray.map {
                    Transaction(
                        fromUser = it.jsonObject["from_user"]!!.jsonObject["short_name"]!!.jsonPrimitive.content,
                        fromUserId = it.jsonObject["from_user"]!!.jsonObject["user_id"]!!.jsonPrimitive.int,
                        toUser = it.jsonObject["to_user"]!!.jsonObject["short_name"]!!.jsonPrimitive.content,
                        toUserId = it.jsonObject["to_user"]!!.jsonObject["user_id"]!!.jsonPrimitive.int,
                        money = it.jsonObject["money"]!!.jsonPrimitive.int
                    )
                })
            )
        }
    }.goAndWait()
}!!

fun Context.getTransactions(roomId: Int) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Get(
        url = "$BASE_URL/transactions/list", body = json.encodeToString(
            mapOf("auth_token" to token.value, "room_id" to roomId.toString())
        )
    ).info().map { (body, _) ->
        json.parseToJsonElement(body).jsonObject["transactions"]!!.jsonArray.map {
            it.jsonObject
        }.map {
            Transaction(
                id = it["id"]!!.jsonPrimitive.int,
                fromUser = it.jsonObject["from_user"]!!.jsonObject["short_name"]!!.jsonPrimitive.content,
                fromUserId = it.jsonObject["from_user"]!!.jsonObject["user_id"]!!.jsonPrimitive.int,
                toUser = it.jsonObject["to_user"]!!.jsonObject["short_name"]!!.jsonPrimitive.content,
                toUserId = it.jsonObject["to_user"]!!.jsonObject["user_id"]!!.jsonPrimitive.int,
                money = it.jsonObject["money"]!!.jsonPrimitive.int
            )
        }
    }.goAndWait()
}!!

fun Context.deleteTransaction(transactionId: Int) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/transactions/delete", body = json.encodeToString(
            mapOf("auth_token" to token.value, "transaction_id" to transactionId.toString())
        )
    ).request.goInParallel()
}!!

fun Context.addTransaction(money: Int, toUserId: Int, roomId: Int) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Post(
        url = "$BASE_URL/transactions/create", body = json.encodeToString(
            mapOf(
                "auth_token" to token.value,
                "money" to money.toString(),
                "to_user_id" to toUserId.toString(),
                "room_id" to roomId.toString()
            )
        )
    ).info().map { (body, _) ->
        json.parseToJsonElement(body).jsonObject.let {
            Transaction(
                it["id"]!!.jsonPrimitive.int,
                it["from_user"]!!.jsonObject["short_name"]!!.jsonPrimitive.content,
                it["from_user"]!!.jsonObject["user_id"]!!.jsonPrimitive.int,
                it["to_user"]!!.jsonObject["short_name"]!!.jsonPrimitive.content,
                it["to_user"]!!.jsonObject["user_id"]!!.jsonPrimitive.int,
                it["money"]!!.jsonPrimitive.int
            )
        }
    }.goAndWait()
}!!

data class UserInTheRoom(val shortName: String, val userId: Int)

fun Context.getUserList(roomId: Int) = tokenHelper.getToken()?.let { token ->
    RequestHelper.Get(
        url = "$BASE_URL/rooms/userlist", body = json.encodeToString(
            mapOf("auth_token" to token.value, "room_id" to roomId.toString())
        )
    ).info().map { (body, _) ->
        json.parseToJsonElement(body).jsonObject["users"]!!.jsonArray.map {
            it.jsonObject
        }.map {
            UserInTheRoom(
                shortName = it["short_name"]!!.jsonPrimitive.content,
                userId = it["id"]!!.jsonPrimitive.int
            )
        }
    }.goAndWait()
}!!
