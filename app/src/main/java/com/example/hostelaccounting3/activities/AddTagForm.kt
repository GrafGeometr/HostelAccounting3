package com.example.hostelaccounting3.activities

import androidx.compose.runtime.Composable
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.Tag
import com.example.hostelaccounting3.mechanics.addTag


@Composable
fun Context.AddTagForm(onClose: () -> Unit, onSuccess: (Tag) -> Unit) =
    TagForm(onClose, { name ->
        val tag = addTag(
            roomId = roomsManager.getCurrentRoomId()!!,
            name = name
        )
        onSuccess(tag)
    }, submitButtonText = "Добавить")
