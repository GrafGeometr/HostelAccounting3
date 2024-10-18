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
import com.example.hostelaccounting3.mechanics.addPurchase

//@Composable
//fun Context.AddPurchaseForm(onClose: () -> Unit, onSuccess: (Purchase) -> Unit) {
//    var money by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Transparent)
//            .wrapContentSize(align = Alignment.TopCenter)
//            .zIndex(10f),
//    ) {
//        Card(
//            modifier = Modifier.padding(16.dp),
//            elevation = CardDefaults.cardElevation(4.dp)
//        ) {
//            Column(
//                modifier = Modifier.padding(16.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                TextField(
//                    value = money,
//                    onValueChange = { money = it },
//                    placeholder = { Text("Сумма") }
//                )
//                TextField(
//                    value = description,
//                    onValueChange = { description = it },
//                    placeholder = { Text("Описание покупки") }
//                )
//                Button(onClick = {
//                    val purchase = addPurchase(
//                        roomId = roomsManager.getCurrentRoomId()!!, money = money.toInt(), description = description
//                    )
//                    onSuccess(purchase)
//                    onClose()
//                }) {
//                    Text("Добавить")
//                }
//                Button(onClick = onClose) {
//                    Text("Закрыть")
//                }
//            }
//        }
//    }
//}

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
