package com.example.hostelaccounting3.activities

import androidx.compose.runtime.Composable
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.Tag
import com.example.hostelaccounting3.mechanics.Task
import com.example.hostelaccounting3.mechanics.editTask


@Composable
fun Context.EditTaskForm(task: Task, onClose: () -> Unit, onSuccess: (Task, Task) -> Unit, allTagsList: List<Tag>) =
    TaskForm(onClose, { name, description ->
        val result = editTask(
            roomId = roomsManager.getCurrentRoomId()!!,
            name = name,
            description = description,
            taskId = task.id,
            isOpened = task.isOpened
        )
        onSuccess(task, result)
    }, "Применить", task.name, task.description, allTagsList = allTagsList)
