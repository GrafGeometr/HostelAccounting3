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
import com.example.hostelaccounting3.mechanics.Task
import com.example.hostelaccounting3.mechanics.editTask


@Composable
fun Context.EditTaskForm(task: Task, onClose: () -> Unit, onSuccess: (Task, Task) -> Unit) =
    TaskForm(onClose, { name, description ->
        val result = editTask(
            roomId = roomsManager.getCurrentRoomId()!!,
            name = name,
            description = description,
            taskId = task.id
        )
        onSuccess(task, result)
    }, "Применить", task.name)
