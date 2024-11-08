import androidx.compose.runtime.*
import com.example.hostelaccounting3.activities.OneRoom
import com.example.hostelaccounting3.activities.Rooms
import com.example.hostelaccounting3.context.Context

@Composable
fun Context.Main() {
    var token by remember { mutableStateOf(tokenHelper.getToken()) }


    when (token) {
        null -> LoginOrRegister { token = tokenHelper.getToken() }
        else -> MainMenu {
            token = tokenHelper.getToken()
        }
    }
}

@Composable
fun Context.MainMenu(onLogout: () -> Unit) {
    var roomId by remember { mutableStateOf(roomsManager.getCurrentRoomId()) }
    if (roomId == null) {
        Rooms(
            { roomId = roomsManager.getCurrentRoomId() }, onLogout = onLogout
        )
    } else {
        OneRoom {
            roomsManager.killRoom()
            roomId = null
        }
    }
}