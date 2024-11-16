package com.example.hostelaccounting3.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.hostelaccounting3.R
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.Room
import com.example.hostelaccounting3.mechanics.createNewRoom
import com.example.hostelaccounting3.mechanics.getRooms
import com.example.hostelaccounting3.mechanics.joinRoom

@Composable
fun Context.Rooms(onRoomSelect: () -> Unit, onLogout: () -> Unit) {
    println(roomsManager.getCurrentRoomId())
    // You can create new room
    val rooms = getRooms()

    var showAddForm by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)) {

        if (showAddForm) AddRoomForm(onRoomSelect = onRoomSelect, onClose = { showAddForm = false })

        Column(
            modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            rooms.forEach { room ->
                RoomCard(room = room, onClick = {
                    roomsManager.setCurrentRoomId(room.id)
                    onRoomSelect()
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
                }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Добавить комнату",
                    tint = MaterialTheme.colorScheme.onError
                )
            }
        }
        Box(
            modifier = Modifier.align(Alignment.BottomEnd).padding(end = 16.dp),

            ) {
            val logoutImage = rememberVectorPainter(
                ImageVector.vectorResource(R.drawable.logout)
            )
            Button(
                onClick = {
                    tokenHelper.removeToken()
                    onLogout()
                }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    painter = logoutImage,
                    contentDescription = "Your SVG image",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onError
                )
            }
        }
    }
}

@Composable
fun RoomCard(room: Room, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp), onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = room.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(2f)
            )

            Spacer(Modifier.width(16.dp))

            VerticalDivider(
                modifier = Modifier.height(24.dp).width(1.dp), color = MaterialTheme.colorScheme.outline
            )

            Spacer(Modifier.width(16.dp))

            Text(
                text = room.userShortName,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun Context.AddRoomForm(onRoomSelect: () -> Unit, onClose: () -> Unit) {
    var createNewRoom by remember { mutableStateOf(false) }
    var joinRoom by remember { mutableStateOf(true) }
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
                if (createNewRoom) {
                    CreateNewRoom(onClose = onClose, onSuccess = { name ->
                        val room = createNewRoom(name)
                        roomsManager.setCurrentRoomId(room.id)
                        onRoomSelect()
                    }, onSwitch = {
                        createNewRoom = false
                        joinRoom = true
                    })
                } else if (joinRoom) {
                    JoinRoom(onClose = onClose, onSuccess = { code ->
                        val room = joinRoom(code)
                        roomsManager.setCurrentRoomId(room.id)
                        onRoomSelect()
                    }, onSwitch = {
                        createNewRoom = true
                        joinRoom = false
                    })
                }
            }
        }
    }
}

@Composable
fun Context.CreateNewRoom(onClose: () -> Unit, onSuccess: (name: String) -> Unit, onSwitch: () -> Unit) {
    var name by remember { mutableStateOf("") }

    TextField(value = name,
        onValueChange = { name = it },
        label = { Text("Название комнаты") },
        modifier = Modifier.fillMaxWidth()
    )
    Button(modifier = Modifier.fillMaxWidth(), onClick = { onSuccess(name) }) { Text("Создать") }
    TextButton(
        modifier = Modifier.fillMaxWidth(), onClick = onSwitch
    ) { Text("Присоединиться к комнате") }
    TextButton(
        modifier = Modifier.fillMaxWidth(), onClick = onClose
    ) { Text("Закрыть") }
}

@Composable
fun Context.JoinRoom(onClose: () -> Unit, onSuccess: (code: String) -> Unit, onSwitch: () -> Unit) {
    var code by remember { mutableStateOf("") }

    TextField(value = code,
        onValueChange = { code = it },
        label = { Text("Код комнаты") },
        modifier = Modifier.fillMaxWidth()
    )
    Button(modifier = Modifier.fillMaxWidth(), onClick = { onSuccess(code) }) { Text("Присоединиться") }
    TextButton(
        modifier = Modifier.fillMaxWidth(), onClick = onSwitch
    ) { Text("Создать новую комнату") }
    TextButton(
        modifier = Modifier.fillMaxWidth(), onClick = onClose
    ) { Text("Закрыть") }
}
