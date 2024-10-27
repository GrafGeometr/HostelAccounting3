package com.example.hostelaccounting3

import Main
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
fun Context.RoomFinances() {
    Text("Страница финансов")
}

@Composable
fun Context.RoomTasks() {
    Text("Страница заданий")
}
