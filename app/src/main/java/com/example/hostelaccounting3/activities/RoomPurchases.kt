package com.example.hostelaccounting3.activities

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.Purchase
import com.example.hostelaccounting3.mechanics.deletePurchase
import com.example.hostelaccounting3.mechanics.getPurchases


@Composable
fun Context.RoomPurchases() {
    val roomId = roomsManager.getCurrentRoomId()!!

    var purchases by remember { mutableStateOf(getPurchases(roomId)) }

    var showAddForm by remember { mutableStateOf(false) }
    var editingPurchase: Purchase? by remember { mutableStateOf(null) }

    Box(modifier = Modifier.fillMaxWidth().padding(bottom = 64.dp)) {

        if (showAddForm) {
            AddPurchaseForm(onClose = { showAddForm = false }, onSuccess = { purchases += it })
        }

        if (editingPurchase != null) {
            EditPurchaseForm(purchase = editingPurchase!!, onClose = { editingPurchase = null }, onSuccess = { p1, p2 ->
                purchases = purchases.map { if (it.id == p1.id) p2 else it }
            })
        }

        Column(
            modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            purchases.forEach { purchase ->
                DisplayOnePurchase(purchase, onEdit = {
                    editingPurchase = purchase
                    showAddForm = false
                }, onDelete = {
                    deletePurchase(purchaseId = purchase.id)
                    purchases = purchases.filter { it != purchase }
                })
            }
            Spacer(Modifier.height(48.dp))
        }

        Box(
            modifier = Modifier.align(Alignment.BottomStart).padding(start = 16.dp)
        ) {
            Button(
                onClick = {
                    showAddForm = true
                    editingPurchase = null
                }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Добавить покупку",
                    tint = MaterialTheme.colorScheme.onError
                )
            }
        }
    }
}

