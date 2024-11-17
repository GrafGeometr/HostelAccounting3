package com.example.hostelaccounting3.activities

import androidx.compose.runtime.Composable
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.Purchase
import com.example.hostelaccounting3.mechanics.RoomData
import com.example.hostelaccounting3.mechanics.editPurchase
import com.example.hostelaccounting3.requests.goInParallel


@Composable
fun Context.EditPurchaseForm(
    purchase: Purchase, onClose: () -> Unit, onSuccess: (Purchase, Purchase) -> Unit, currentRoomData: RoomData
) = PurchaseForm(onClose, { money, description ->
    val result = Purchase(
        id = purchase.id,
        money = money,
        shortUsername = purchase.shortUsername,
        description = description,
        datetime = purchase.datetime
    )
    editPurchase(
        roomId = currentRoomData.roomId, result
    ).goInParallel()
    onSuccess(purchase, result)
}, "Применить", purchase.money.toString(), purchase.description)
