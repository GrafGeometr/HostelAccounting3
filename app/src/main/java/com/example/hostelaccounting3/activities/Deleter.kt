package com.example.hostelaccounting3.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex


@Composable
fun DeleterForm(unsafeDeleter: () -> Unit, onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .wrapContentSize(align = Alignment.TopCenter)
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
                Text("Удалить?")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp) // Добавляем расстояние между кнопками
                ) {
                    OutlinedButton( // Заменяем обычную кнопку на контурную
                        onClick = {
                            unsafeDeleter()
                            onClose()
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent // Делаем фон прозрачным
                        )
                    ) {
                        Text("Да")
                    }
                    Button(onClick = onClose) {
                        Text("Нет")
                    }
                }
            }
        }
    }
}