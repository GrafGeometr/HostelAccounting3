package com.example.hostelaccounting3.mechanics

import com.example.hostelaccounting3.BASE_URL
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.datetime.toDatetime
import com.example.hostelaccounting3.requests.Action
import com.example.hostelaccounting3.requests.RequestHelper
import com.example.hostelaccounting3.requests.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*


data class UserRoom(val id: Int, val name: String, val shortName: String)

data class RoomData(
    val financesInfo: FinancesInfo,
    val purchases: List<Purchase>,
    val tags: List<Tag>,
    val tasks: List<Task>,
    val transactions: List<Transaction>,
    val users: List<UserRoom>,
    val roomId: Int,
    val currentUser: UserRoom,
) {
    fun addPurchase(purchase: Purchase) = copy(purchases = listOf(purchase) + purchases)
    fun editPurchase(old: Purchase, new: Purchase) =
        copy(purchases = purchases.map { if (it.id == old.id) new else it })

    fun deletePurchase(purchase: Purchase) = copy(purchases = purchases.filter { it.id != purchase.id })


    fun addTag(tag: Tag) = copy(tags = tags + tag)
    fun editTag(old: Tag, new: Tag) = copy(tags = tags.map { if (it.id == old.id) new else it })
    fun deleteTag(tag: Tag) = copy(tags = tags.filter { it.id != tag.id })

    fun addTask(task: Task) = copy(tasks = tasks + task)
    fun editTask(old: Task, new: Task) = copy(tasks = tasks.map { if (it.id == old.id) new else it })
    fun deleteTask(task: Task) = copy(tasks = tasks.filter { it.id != task.id })


    fun addTransaction(transaction: Transaction) = copy(transactions = transactions + transaction)
    fun editTransaction(old: Transaction, new: Transaction) =
        copy(transactions = transactions.map { if (it.id == old.id) new else it })

    fun deleteTransaction(transaction: Transaction) =
        copy(transactions = transactions.filter { it.id != transaction.id })
}

fun Context.getRoomData(roomId: Int): Action<RoomData> {
    //println("Sending request to get room data...")
    return tokenHelper.getToken()?.let { token ->
        RequestHelper.Get(
            url = "$BASE_URL/rooms/summary", body = json.encodeToString(
                mapOf("auth_token" to token.value, "room_id" to roomId.toString())
            )
        ).info().map { (body, _) ->
            //println(body)
            json.parseToJsonElement(body).jsonObject.let { roomDataJson ->
                RoomData(financesInfo = roomDataJson["finance"]!!.jsonObject.let { financesJson ->
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
                }, purchases = roomDataJson["purchases"]!!.jsonArray.map {
                    it.jsonObject
                }.map {
                    Purchase(
                        it["id"]!!.jsonPrimitive.int,
                        it["money"]!!.jsonPrimitive.int,
                        it["short_username"]!!.jsonPrimitive.content,
                        it["description"]!!.jsonPrimitive.content,
                        it["datetime_bought"]!!.jsonPrimitive.content.toDatetime()
                    )
                }, tags = roomDataJson["tags"]!!.jsonArray.map {
                    it.jsonObject
                }.map {
                    Tag(
                        it["id"]!!.jsonPrimitive.int,
                        it["name"]!!.jsonPrimitive.content,
                        it["room_id"]!!.jsonPrimitive.int
                    )
                }, tasks = roomDataJson["tasks"]!!.jsonArray.map {
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
                }, transactions = roomDataJson["transactions"]!!.jsonArray.map {
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
                }, users = roomDataJson["users"]!!.jsonArray.map {
                    it.jsonObject
                }.map {
                    UserRoom(
                        id = it["id"]!!.jsonPrimitive.int,
                        name = it["name"]!!.jsonPrimitive.content,
                        shortName = it["short_name"]!!.jsonPrimitive.content
                    )
                }, roomId = roomId, currentUser = roomDataJson["me"]!!.jsonObject.let {
                    UserRoom(
                        id = it["id"]!!.jsonPrimitive.int,
                        name = it["name"]!!.jsonPrimitive.content,
                        shortName = it["short_name"]!!.jsonPrimitive.content
                    )
                })
            }
        }
    }!!
}
