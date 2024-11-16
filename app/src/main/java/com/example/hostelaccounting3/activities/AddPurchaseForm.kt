package com.example.hostelaccounting3.activities

import androidx.compose.runtime.Composable
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.datetime.Datetime
import com.example.hostelaccounting3.mechanics.Purchase
import com.example.hostelaccounting3.mechanics.RoomData
import com.example.hostelaccounting3.mechanics.addPurchase
import com.example.hostelaccounting3.mechanics.genRandomId
import com.example.hostelaccounting3.requests.goInParallel


@Composable
fun Context.AddPurchaseForm(
    onClose: () -> Unit,
    onSuccess: (Purchase) -> Unit,
    currentRoomData: RoomData
) = PurchaseForm(onClose, { money, description ->
    val purchase = Purchase(
        id = genRandomId(),
        money = money,
        shortUsername = currentRoomData.currentUser.shortName,
        description = description,
        datetime = Datetime.now()
    )
    addPurchase(
        purchase, currentRoomData.roomId
    ).goInParallel()
    onSuccess(purchase)
}, submitButtonText = "Добавить")
