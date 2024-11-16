package com.example.hostelaccounting3.context



import com.example.hostelaccounting3.login.TokenHelper
import com.example.hostelaccounting3.mechanics.RoomData
import com.example.hostelaccounting3.mechanics.RoomsManager
import com.example.hostelaccounting3.mechanics.UserData
import com.example.hostelaccounting3.requests.RequestHelper
import com.example.hostelaccounting3.storage.Storage
import kotlinx.serialization.json.Json

interface Context {
    val storage: Storage
    val requestHelper: RequestHelper
    val tokenHelper: TokenHelper

    val json: Json

    var userData: UserData?
    val roomsManager: RoomsManager

    var roomData: RoomData?
    var syncLoopRunning: Boolean
}