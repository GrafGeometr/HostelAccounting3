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


@Composable
fun PurchaseForm(
    onClose: () -> Unit,
    onSuccess: (money: Int, description: String) -> Unit,
    submitButtonText: String,
    moneyDefault: String = "",
    descriptionDefault: String = ""
) {
    var money by remember { mutableStateOf(moneyDefault) }
    var description by remember { mutableStateOf(descriptionDefault) }

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
                TextField(value = description,
                    onValueChange = { description = it },
                    placeholder = { Text("Описание покупки") })
                if (showErrorMessage) Text(
                    text = errorMessage, color = MaterialTheme.colorScheme.error
                )
                Button(onClick = {
                    if (showErrorMessage) return@Button
                    onSuccess(money.toInt(), description)
                    onClose()
                }) {
                    Text(submitButtonText)
                }
                Button(onClick = onClose) {
                    Text("Закрыть")
                }
            }
        }
    }
}