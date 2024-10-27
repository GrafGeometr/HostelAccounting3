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
fun TaskForm(
    onClose: () -> Unit,
    onSuccess: (name: String, description: String) -> Unit,
    submitButtonText: String,
    nameDefault: String = "",
    descriptionDefault: String = ""
) {
    var name by remember { mutableStateOf(nameDefault) }
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
                TextField(value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Тег задачи") })
                if (showErrorMessage) Text(
                    text = errorMessage, color = MaterialTheme.colorScheme.error
                )
                TextField(value = description,
                    onValueChange = { name = it },
                    placeholder = { Text("Описание задачи") })
                Button(onClick = {
                    if (showErrorMessage) return@Button
                    onSuccess(name, description)
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