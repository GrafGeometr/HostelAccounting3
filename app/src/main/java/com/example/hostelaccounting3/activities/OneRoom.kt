package com.example.hostelaccounting3.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.example.hostelaccounting3.RoomFinances
import com.example.hostelaccounting3.RoomTags
import com.example.hostelaccounting3.RoomTasks
import com.example.hostelaccounting3.context.Context

@Composable
fun Context.OneRoom(onKillRoom: () -> Unit) {
    val roomId = roomsManager.getCurrentRoomId()
    var navbar by remember { mutableStateOf(0) }
    Column {
        Row {
            Button(onClick = { navbar = 0 }) { Text("Покупки") }
            Button(onClick = { navbar = 1 }) { Text("Финансы") }
            Button(onClick = { navbar = 2 }) { Text("Задачи") }
            Button(onClick = { navbar = 3 }) { Text("Теги") }
            Button(onClick = {
                roomsManager.killRoom()
                onKillRoom()
            }) { Text("Выйти") }
        }
        when (navbar) {
            0 -> RoomPurchases()
            1 -> RoomFinances()
            2 -> RoomTasks()
            3 -> RoomTags()
        }
    }
}