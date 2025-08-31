import androidx.compose.runtime.*
import com.example.hostelaccounting3.activities.OneRoom
import com.example.hostelaccounting3.activities.Rooms
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.RoomData
import com.example.hostelaccounting3.mechanics.getRoomData
import com.example.hostelaccounting3.requests.*
import kotlinx.coroutines.delay

@Composable
fun Context.Main(onThemeChange: () -> Unit) {
    var token by remember { mutableStateOf(tokenHelper.getToken()) }


    when (token) {
        null -> LoginOrRegister { token = tokenHelper.getToken() }
        else -> MainMenu(onThemeChange) {
            token = tokenHelper.getToken()
        }
    }
}

suspend fun Context.updateRoomData(onSuccess: (RoomData) -> Unit) = roomsManager.getCurrentRoomId()?.let { roomId ->
    getRoomData(roomId).map {
        println(it.tags)
        onSuccess(it)
    }.go()
} ?: {}

fun Context.syncLoop(onSuccess: (RoomData) -> Unit): Action<Unit> = {
    syncLoopRunning = true
    while (true) {
        println("Updating room data...")
        try {
            updateRoomData(onSuccess)
        }
        catch (e: Exception) {
            println(e.toString())
        }
        delay(20000)
    }
}


@Composable
fun Context.MainMenu(onThemeChange: () -> Unit, onLogout: () -> Unit) {
    println("RENDERING!!!")
    var roomId by remember { mutableStateOf(roomsManager.getCurrentRoomId()) }
    if (roomId == null) {
        Rooms(
            { roomId = roomsManager.getCurrentRoomId() }, onLogout = onLogout,
            onThemeChange = onThemeChange
        )
    } else {
        roomData = getRoomData(roomId!!).goAndWait()
        Fuck {
            roomId = null
        }
    }
}

@Composable
fun Context.Fuck(onLogout: () -> Unit) {
    var rerender by remember { mutableStateOf(true) }

    fun render() {
        rerender = !rerender
    }

    if (!syncLoopRunning) {
        syncLoop {
            roomData = it
            render()
        }.goInParallel()
    }
    OneRoom({
        roomsManager.killRoom()
        onLogout()
    }, roomData = roomData!!, rerender = rerender, addPurchase = {
        roomData = roomData?.addPurchase(it)
        render()
    }, editPurchase = { p1, p2 ->
        roomData = roomData?.editPurchase(p1, p2)
        render()
    }, deletePurchase = {
        roomData = roomData?.deletePurchase(it)
        render()
    }, addTransaction = {
        roomData = roomData?.addTransaction(it)
        render()
    }, deleteTransaction = {
        roomData = roomData?.deleteTransaction(it)
        render()
    }, addTag = {
        roomData = roomData?.addTag(it)
        render()
    }, editTag = { t1, t2 ->
        roomData = roomData?.editTag(t1, t2)
        render()
    }, deleteTag = {
        roomData = roomData?.deleteTag(it)
        render()
    }, addTask = {
        roomData = roomData?.addTask(it)
        render()
    }, editTask = { t1, t2 ->
        roomData = roomData?.editTask(t1, t2)
        render()
    }, deleteTask = {
        roomData = roomData?.deleteTask(it)
        render()
    })
}