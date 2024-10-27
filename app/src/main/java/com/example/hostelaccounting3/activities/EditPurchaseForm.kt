package com.example.hostelaccounting3.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.Purchase
import com.example.hostelaccounting3.mechanics.editPurchase


@Composable
fun Context.EditPurchaseForm(purchase: Purchase, onClose: () -> Unit, onSuccess: (Purchase, Purchase) -> Unit) =
    PurchaseForm(onClose, { money, description ->
        val result = editPurchase(
            roomId = roomsManager.getCurrentRoomId()!!,
            money = money,
            description = description,
            purchaseId = purchase.id
        )
        onSuccess(purchase, result)
    }, "Применить", purchase.money.toString(), purchase.description)
