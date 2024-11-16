package com.example.hostelaccounting3

import Main
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.login.TokenHelper
import com.example.hostelaccounting3.mechanics.RoomData
import com.example.hostelaccounting3.mechanics.RoomsManager
import com.example.hostelaccounting3.mechanics.UserData
import com.example.hostelaccounting3.mechanics.getUserData
import com.example.hostelaccounting3.requests.RequestHelper
import com.example.hostelaccounting3.storage.Storage
import com.example.hostelaccounting3.storage.StorageManager
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

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
                        get() = data ?: getUserData().also { data = it }
                        set(value) {
                            data = value
                        }
                    override val roomsManager: RoomsManager = RoomsManager(storage)

                    override var roomData: RoomData? = null
                    override var syncLoopRunning: Boolean = false
                }
                Box(modifier = Modifier.padding(innerPadding).background(Palette.background)) {
                    context.Main()
                }
            }
        }
    }
}
