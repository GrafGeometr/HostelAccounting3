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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                        override val navController: NavHostController = rememberNavController()

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

                    val token = context.tokenHelper.getToken()

                    NavHost(
                        navController = context.navController,
                        startDestination = if (token == null) "login" else "main_page"
                    ) {
                        composable("main_page") { context.MainPage() }
                        composable("login") { context.Login() }
                        composable("register") { context.Register() }
                        composable("rooms_list") { context.Rooms() }
                        composable("one_room") { context.OneRoom() }
                    }
                }
            }
        }
    }
}


@Composable
fun Context.Login() {
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
            login(login, password)?.let {
                showWarning = true
                errorMessage = it
            }
        }) {
            Text("Login")
        }
        Button(onClick = { navController.navigate("register") }) {
            Text("Register (if you don't have an account)")
        }

        if (showWarning) {
            Text(text = errorMessage)
        }
    }
}

@Composable
fun Context.Register() {
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
            register(login, password)?.let {
                showWarning = true
                errorMessage = it
            }
        }) {
            Text("Register")
        }
        Button(onClick = { navController.navigate("login") }) {
            Text("Login (if you already have an account)")
        }

        if (showWarning) {
            Text(text = errorMessage)
        }
    }
}


@Composable
fun Context.MainPage() {
    userData = getUserData()!!
    Row {
        Text("Name: ${userData?.name}")
    }

    navController.navigate(
        if (roomsManager.getCurrentRoomId() != null) "one_room"
        else "rooms_list"
    )
}

@Composable
fun Context.Rooms() {
    // You can create new room

    var rooms by remember { mutableStateOf<List<Room>>(emptyList()) }

    rooms = getRooms()

    rooms.forEach { room ->
        Text(room.name)
    }


    Button(onClick = {

    }) {
        Text("Create new room")
    }
}

@Composable
fun Context.OneRoom() {

}
