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
import com.example.hostelaccounting3.mechanics.UserRoom


@Composable
fun AddTransactionForm(
    onClose: () -> Unit,
    onSuccess: (money: Int, toUserId: Int) -> Unit,
    userList: List<UserRoom>,
    currentUserId: Int
) {
    var money by remember { mutableStateOf("") }
    var toUserId: Int? by remember { mutableStateOf(null) }


    var showErrorMessage by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Transparent).wrapContentSize(align = Alignment.TopCenter)
            .zIndex(10f),
    ) {
        Card(
            modifier = Modifier.padding(16.dp), elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(value = money, onValueChange = {
                    money = it
                    when {
                        it.toIntOrNull() == null -> {
                            showErrorMessage = true
                            errorMessage = "Значение должно быть числом"
                        }

                        it.toInt() <= 0 -> {
                            showErrorMessage = true
                            errorMessage = "Это ещё что такое"
                        }

                        it.toInt() > 1000000 -> {
                            showErrorMessage = true
                            errorMessage = "Слишком большая сумма"
                        }

                        else -> showErrorMessage = false
                    }
                }, placeholder = { Text("Сумма") })


                var toUserShortName by remember { mutableStateOf("Кому") }

                var showDropdown by remember { mutableStateOf(false) }

                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {

                    TextButton(
                        onClick = {
                            showDropdown = !showDropdown
                            println("??????")
                        }, modifier = Modifier.padding(0.dp)
                    ) {
                        Text(toUserShortName)
                    }

                }

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    DropdownMenu(
                        expanded = showDropdown,
                        onDismissRequest = { showDropdown = false },
                    ) {
                        userList.forEach { user ->
                            if (user.id == currentUserId) return@forEach
                            DropdownMenuItem(onClick = {
                                toUserId = user.id
                                toUserShortName = user.shortName
                                showDropdown = false
                            }, text = { Text(user.shortName) })
                        }
                    }
                }

                if (showErrorMessage) Text(
                    text = errorMessage, color = MaterialTheme.colorScheme.error
                )
                Button(onClick = {
                    if (showErrorMessage) return@Button
                    if (toUserId == null) return@Button
                    onSuccess(money.toInt(), toUserId!!)
                    onClose()
                }) {
                    Text("Добавить")
                }
                Button(onClick = onClose) {
                    Text("Закрыть")
                }
            }
        }
    }
}