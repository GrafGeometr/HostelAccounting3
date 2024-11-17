package com.example.hostelaccounting3.activities

import androidx.compose.runtime.Composable
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.RoomData
import com.example.hostelaccounting3.mechanics.Tag
import com.example.hostelaccounting3.mechanics.addTag
import com.example.hostelaccounting3.mechanics.genRandomId
import com.example.hostelaccounting3.requests.goInParallel


@Composable
fun Context.AddTagForm(onClose: () -> Unit, onSuccess: (Tag) -> Unit, currentRoomData: RoomData) =
    TagForm(onClose, { name ->
        val tag = Tag(
            id = genRandomId(), name = name, roomId = currentRoomData.roomId
        )
        addTag(
            roomId = currentRoomData.roomId, tag = tag
        ).goInParallel()
        onSuccess(tag)
    }, submitButtonText = "Добавить")
