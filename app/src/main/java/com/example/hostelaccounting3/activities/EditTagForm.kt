package com.example.hostelaccounting3.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.Tag
import com.example.hostelaccounting3.mechanics.editTag


@Composable
fun Context.EditTagForm(tag: Tag, onClose: () -> Unit, onSuccess: (Tag, Tag) -> Unit) =
    TagForm(onClose, { name ->
        val result = editTag(
            roomId = roomsManager.getCurrentRoomId()!!,
            name = name,
            tagId = tag.id
        )
        onSuccess(tag, result)
    }, "Применить", tag.name)
