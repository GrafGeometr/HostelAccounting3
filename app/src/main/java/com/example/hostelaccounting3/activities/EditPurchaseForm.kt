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
fun Context.EditPurchaseForm(purchase: Purchase, onClose: () -> Unit, onSuccess: (Purchase, Purchase) -> Unit) {
    var money by remember { mutableStateOf(purchase.money.toString()) }
    var description by remember { mutableStateOf(purchase.description) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .wrapContentSize(align = Alignment.TopCenter)
            .zIndex(10f),
    ) {
        Card(
            modifier = Modifier.padding(16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = money,
                    onValueChange = { money = it },
                    placeholder = { Text("Сумма") }
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = { Text("Описание покупки") }
                )
                Button(onClick = {
                    val result = editPurchase(
                        roomId = roomsManager.getCurrentRoomId()!!, money = money.toInt(), description = description,
                        purchaseId = purchase.id
                    )
                    onSuccess(purchase, result)
                    onClose()
                }) {
                    Text("Применить")
                }
                Button(onClick = onClose) {
                    Text("Закрыть")
                }
            }
        }
    }
}
