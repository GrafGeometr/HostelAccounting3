package com.example.hostelaccounting3.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.getRooms

@Composable
fun Context.Rooms(onRoomSelect: () -> Unit) {
    println(roomsManager.getCurrentRoomId())
    // You can create new room
    val rooms = getRooms()

    println(rooms)
    Column {
        Row {
            rooms.forEach { room ->
                Button(onClick = {
                    roomsManager.setCurrentRoomId(room.id)
                    onRoomSelect()
                }) { Text(room.name) }
            }
        }

        Button(onClick = {

        }) {
            Text("Create new room")
        }
    }
}