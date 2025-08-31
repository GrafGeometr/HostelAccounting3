package com.example.hostelaccounting3.activities

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.*
import com.example.hostelaccounting3.requests.goInParallel
import kotlin.math.abs

@Composable
fun Context.RoomFinances(
    currentRoomData: RoomData,
    addTransaction: (Transaction) -> Unit,
    deleteTransaction: (Transaction) -> Unit,
    rerender: Boolean
) {
    val roomId = roomsManager.getCurrentRoomId()!!

    val financesInfo = currentRoomData.financesInfo

    val transactions = currentRoomData.transactions

    val userList = currentRoomData.users

    var showAddForm by remember { mutableStateOf(false) }
    var deletingTransaction: Transaction? by remember { mutableStateOf(null) }

    val currentUserId = currentRoomData.currentUser.id

    Box(
        modifier = Modifier.fillMaxWidth().padding(top=16.dp)
    ) {
        if (showAddForm) {
            AddTransactionForm(onClose = { showAddForm = false }, onSuccess = { money, toUserId ->
                val transaction = Transaction(
                    id = genRandomId(),
                    fromUser = currentRoomData.currentUser.shortName,
                    fromUserId = currentUserId,
                    toUser = userList.find { it.id == toUserId }!!.shortName,
                    toUserId = toUserId,
                    money = money
                )
                addTransaction(roomId, transaction).goInParallel()
                addTransaction(transaction)
            }, userList = userList, currentUserId = currentUserId)
        }

        if (deletingTransaction != null) {
            val transaction = deletingTransaction!!

            DeleterForm(unsafeDeleter = {
                deleteTransaction(transactionId = transaction.id!!).goInParallel()
                deleteTransaction(transaction)
            }) {
                deletingTransaction = null
            }
        }

        Column(
            modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState()).padding(vertical=4.dp, horizontal=16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Расходы в текущем месяце: ${financesInfo.totalMonth}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "В среднем на человека: ${financesInfo.averagePerUserMonth}",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Расходы", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold
                    )

                    financesInfo.spendTable.table.forEachIndexed { index, it ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(it.shortName, style = MaterialTheme.typography.bodyLarge)
                            Text(it.money.toString(), style = MaterialTheme.typography.bodyLarge)
                        }

                        if (index != financesInfo.spendTable.table.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
                            )
                        }
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Долг", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold
                    )

                    financesInfo.transactionTable.table.forEach {
                        val payer = it.fromUser
                        val amount = it.money
                        val earner = it.toUser
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    payer, style = MaterialTheme.typography.bodyLarge
                                )
                            }

                            Column(
                                modifier = Modifier.width(128.dp).padding(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.defaultMinSize(minWidth = 72.dp),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            "${abs(amount)}",
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight
                                        )
                                    }
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                        contentDescription = null
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier.padding(vertical = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                // min width to fit content
                            ) {
                                Text(
                                    earner,
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                        }
                    }
                }
            }

            // List of all GetTransactions() cool looking
            // big "Транзакции" text
            Spacer(Modifier.height(16.dp))
            Text(
                "Переводы",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            transactions.forEach { transaction ->
                DisplayOneTransaction(transaction, onDelete = {
                    deletingTransaction = transaction
                })
            }
            Spacer(Modifier.height(48.dp))
        }

        Box(
            modifier = Modifier.align(Alignment.BottomStart).padding(start = 16.dp)
        ) {
            Button(
                onClick = { showAddForm = true },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Добавить перевод",
                    tint = MaterialTheme.colorScheme.onError
                )
            }
        }
    }
}