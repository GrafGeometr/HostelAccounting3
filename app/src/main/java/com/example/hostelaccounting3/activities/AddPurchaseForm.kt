package com.example.hostelaccounting3.activities

import androidx.compose.runtime.Composable
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.Purchase
import com.example.hostelaccounting3.mechanics.addPurchase


@Composable
fun Context.AddPurchaseForm(onClose: () -> Unit, onSuccess: (Purchase) -> Unit) =
    PurchaseForm(onClose, { money, description ->
        val purchase = addPurchase(
            roomId = roomsManager.getCurrentRoomId()!!,
            money = money,
            description = description
        )
        onSuccess(purchase)
    }, submitButtonText = "Добавить")
