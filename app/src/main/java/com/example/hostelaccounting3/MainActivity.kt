package com.example.hostelaccounting3

import Main
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

fun rgb(r: Int, g: Int, b: Int) = Color(r, g, b)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val storage: Storage = StorageManager(
                preferences = getSharedPreferences(
                    "sharedPrefs", MODE_PRIVATE
                )
            )

            val theme_: Boolean =
                storage.get("Theme")?.let { it == "true" } ?: isSystemInDarkTheme()
            var theme by remember { mutableStateOf(theme_) }
            val currentColorTheme = when (theme) {
                true -> {
                    darkColorScheme(
                        primary = rgb(148, 226, 213), // Teal
                        onTertiary = rgb(49, 50, 68), // Surface 0
                        surfaceVariant = rgb(49, 50, 68), // Surface 0
                        onSurfaceVariant = rgb(205, 214, 244), // Text
                        outline = rgb(166, 227, 161), // Green
                        background = rgb(17, 17, 27), // Crust
                        onBackground = rgb(220, 224, 232), // Crust (invert)
                        onError = rgb(30, 30, 46), // Base
                        onPrimary = rgb(17, 17, 27) // Base
                    )
                }

                false -> {
                    lightColorScheme(
                        primary = rgb(23, 146, 153), // Teal
                        onTertiary = rgb(188, 192, 204), // Surface 1
                        surfaceVariant = rgb(204, 208, 218), // Surface 0
                        onSurfaceVariant = rgb(76, 79, 105), // Text
                        outline = rgb(64, 160, 43), // Green
                        background = rgb(220, 224, 232), // Crust
                        onBackground = rgb(17, 17, 27), // Crust (invert)
                        onError = rgb(239, 241, 245), // Base
                        onPrimary = rgb(239, 241, 245) // Base
                    )
                }
            }
            MaterialTheme(
                colorScheme = currentColorTheme
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    val context = object : Context {
                        override val storage: Storage = storage
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
                    Box(modifier = Modifier.padding(innerPadding)) {
                        context.Main(onThemeChange = {
                            theme = !theme
                            storage.set("Theme", theme.toString())
                        })
                    }
                }
            }
        }
    }
}
