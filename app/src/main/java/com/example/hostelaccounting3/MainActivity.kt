package com.example.hostelaccounting3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.login.TokenHelper
import com.example.hostelaccounting3.login.login
import com.example.hostelaccounting3.login.register
import com.example.hostelaccounting3.mechanics.*
import com.example.hostelaccounting3.requests.RequestHelper
import com.example.hostelaccounting3.storage.Storage
import com.example.hostelaccounting3.storage.StorageManager
import com.example.hostelaccounting3.ui.theme.HostelAccounting3Theme
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HostelAccounting3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = object : Context {
                        override val storage: Storage =
                            StorageManager(preferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE))
                        override val requestHelper: RequestHelper = RequestHelper
                        override val tokenHelper: TokenHelper = TokenHelper(storage)

                        var data: UserData? = null

                        override val json: Json = Json {
                            ignoreUnknownKeys = true
                            prettyPrint = true
                        }

                        override var userData: UserData?
                            get() = data ?: getUserData()?.also { data = it }
                            set(value) {
                                data = value
                            }
                        override val roomsManager: RoomsManager = RoomsManager(storage)
                    }

                    context.Main()
                }
            }
        }
    }
}

@Composable
fun Context.Main() {
    var token by remember { mutableStateOf(tokenHelper.getToken()) }


    when (token) {
        null -> LoginOrRegister { token = tokenHelper.getToken() }
        else -> MainMenu()
    }
}

@Composable
fun Context.MainMenu() {
    var roomId by remember { mutableStateOf(roomsManager.getCurrentRoomId()) }
    if (roomId == null) {
        Rooms {
            roomId = roomsManager.getCurrentRoomId()
        }
    } else {
        OneRoom {
            roomId = roomsManager.getCurrentRoomId()
        }
    }
}

@Composable
fun Context.LoginOrRegister(onSuccess: () -> Unit) {
    var needLogin by remember { mutableStateOf(true) }

    when {
        needLogin -> Login(onSuccess) {
            needLogin = false
        }

        else -> Register(onSuccess) {
            needLogin = true
        }
    }
}

@Composable
fun Context.Login(onSuccess: () -> Unit, onWantRegister: () -> Unit) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    var showWarning by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center
    ) {
        TextField(value = login, onValueChange = { login = it })
        TextField(value = password, onValueChange = { password = it })
        Button(onClick = {
            val error = login(login, password)?.let {
                showWarning = true
                errorMessage = it
            }
            if (error == null) {
                onSuccess()
            }
        }) {
            Text("Login")
        }
        Button(onClick = {
            onWantRegister()
        }) {
            Text("Register (if you don't have an account)")
        }

        if (showWarning) {
            Text(text = errorMessage)
        }
    }
}

@Composable
fun Context.Register(onSuccess: () -> Unit, onWantLogin: () -> Unit) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showWarning by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center
    ) {
        TextField(value = login, onValueChange = { login = it })
        TextField(value = password, onValueChange = { password = it })
        Button(onClick = {
            val error = register(login, password)?.let {
                showWarning = true
                errorMessage = it
            }
            if (error == null) {
                onSuccess()
            }
        }) {
            Text("Register")
        }
        Button(onClick = { onWantLogin() }) {
            Text("Login (if you already have an account)")
        }

        if (showWarning) {
            Text(text = errorMessage)
        }
    }
}

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

@Composable
fun Context.RoomPurchases() {
    val roomId = roomsManager.getCurrentRoomId()!!

    Text("Страница покупок")
    Column {
        Row {
            Text("Header")
        }
        var purchases by remember { mutableStateOf(getPurchases(roomId)) }
        purchases.forEach { purchase ->
            Row {
                Text(purchase.money.toString())
                Text(purchase.description)
                Text(purchase.datetime.toString())
                Button(onClick = {
                    val newPurchase = editPurchase(roomId, purchaseId = purchase.id, 1000, "Test purchase edit")
                    purchases = purchases.filter { it != purchase } + newPurchase
                }) {
                    Text("Изменить")
                }
                Button(onClick = {
                    deletePurchase(purchaseId = purchase.id)
                    purchases = purchases.filter { it != purchase }
                }) {
                    Text("Удалить")
                }
            }
        }

        Button(onClick = {
            val purchase = addPurchase(roomId, 1000, "Test purchase")
            purchases += purchase
        }) {
            Text("ADD")
        }
    }
}


@Composable
fun Context.RoomFinances() {
    Text("Страница финансов")
}

@Composable
fun Context.RoomTasks() {
    Text("Страница заданий")
}

@Composable
fun Context.RoomTags() {
    Text("Страница тегов")
}