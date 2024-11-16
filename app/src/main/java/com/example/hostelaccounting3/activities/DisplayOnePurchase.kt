package com.example.hostelaccounting3.activities

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostelaccounting3.Palette
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.Purchase

@Composable
fun Context.DisplayOnePurchase(
    purchase: Purchase, onEdit: () -> Unit, onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.height(IntrinsicSize.Max).padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Column(modifier = Modifier.weight(2f)) {
                    Text(
                        purchase.shortUsername,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 2.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold, fontSize = 20.sp
                        ),
                        color = Palette.text
                    )
                    Text(
                        "${purchase.money} - ${purchase.description}",
                        modifier = Modifier.fillMaxWidth().padding(bottom = 2.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Palette.text
                    )
                    Text(
                        purchase.datetime.toString(),
                        modifier = Modifier.alpha(0.5f),
                        style = MaterialTheme.typography.bodySmall,
                        color = Palette.text
                    )
                }
                Column(modifier = Modifier.weight(1f).fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onEdit) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Edit purchase",
                                tint = Palette.text
                            )
                        }
                        IconButton(onClick = onDelete) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete purchase",
                                tint = Palette.text
                            )
                        }
                    }
                }
            }
        }
    }
}