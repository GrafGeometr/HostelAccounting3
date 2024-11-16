package com.example.hostelaccounting3.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hostelaccounting3.Palette
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.Purchase
import com.example.hostelaccounting3.mechanics.RoomData
import com.example.hostelaccounting3.mechanics.deletePurchase
import com.example.hostelaccounting3.requests.goInParallel


@Composable
fun Context.RoomPurchases(
    currentRoomData: RoomData,
    addPurchase: (Purchase) -> Unit,
    editPurchase: (Purchase, Purchase) -> Unit,
    deletePurchase: (Purchase) -> Unit,
    rerender: Boolean
) {
    val purchases = currentRoomData.purchases

    var showAddForm by remember { mutableStateOf(false) }
    var editingPurchase: Purchase? by remember { mutableStateOf(null) }

    Box(modifier = Modifier.fillMaxWidth()) {

        if (showAddForm) AddPurchaseForm(
            onClose = { showAddForm = false }, onSuccess = addPurchase, currentRoomData
        )

        if (editingPurchase != null) EditPurchaseForm(
            purchase = editingPurchase!!,
            onClose = { editingPurchase = null },
            onSuccess = editPurchase,
            currentRoomData
        )

        Column(
            modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical=4.dp).background(
                Palette.background),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            purchases.forEach { purchase ->
                DisplayOnePurchase(purchase, onEdit = {
                    editingPurchase = purchase
                    showAddForm = false
                }, onDelete = {
                    deletePurchase(purchaseId = purchase.id).goInParallel()
                    deletePurchase(purchase)
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

