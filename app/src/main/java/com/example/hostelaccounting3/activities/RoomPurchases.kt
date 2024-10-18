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
            AddPurchaseForm(
                onClose = { showAddForm = false },
                onSuccess = { purchases += it }
            )
        }

        if (editingPurchase != null) {
            EditPurchaseForm(
                purchase = editingPurchase!!,
                onClose = { editingPurchase = null },
                onSuccess = { p1, p2 ->
                    purchases = purchases.map { if (it.id == p1.id) p2 else it }
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            purchases.forEach { purchase ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                purchase.shortUsername,
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                "${purchase.money} - ${purchase.description}",
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                purchase.datetime.toString(),
                                modifier = Modifier
                                    .padding(end = 8.dp, bottom = 4.dp)
                                    .alpha(0.5f),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(onClick = {
                                editingPurchase = purchase
                                showAddForm = false
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "Edit purchase"
                                )
                            }
                            IconButton(onClick = {
                                deletePurchase(purchaseId = purchase.id)
                                purchases = purchases.filter { it != purchase }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Delete purchase"
                                )
                            }
                        }
                    }
                }
            }
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
