package com.example.hostelaccounting3.activities

import androidx.compose.runtime.Composable
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.Task
import com.example.hostelaccounting3.mechanics.addTask

@Composable
fun Context.AddTaskForm(onClose: () -> Unit, onSuccess: (Task) -> Unit) =
    TaskForm(onClose, { name, description ->
        val task = addTask(
            roomId = roomsManager.getCurrentRoomId()!!,
            name = name,
            description = description,
        )
        onSuccess(task)
    }, submitButtonText = "Добавить")
