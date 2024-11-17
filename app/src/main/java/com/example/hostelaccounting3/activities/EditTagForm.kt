package com.example.hostelaccounting3.activities

import androidx.compose.runtime.Composable
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.RoomData
import com.example.hostelaccounting3.mechanics.Tag
import com.example.hostelaccounting3.mechanics.editTag
import com.example.hostelaccounting3.requests.goInParallel


@Composable
fun Context.EditTagForm(
    tag: Tag, onClose: () -> Unit, onSuccess: (Tag, Tag) -> Unit, currentRoomData: RoomData
) = TagForm(onClose, { name ->
    val result = Tag(
        id = tag.id, name = name, roomId = currentRoomData.roomId
    )
    editTag(
        roomId = currentRoomData.roomId, tag = result
    ).goInParallel()
    onSuccess(tag, result)
}, "Применить", tag.name)
