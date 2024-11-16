package com.example.hostelaccounting3.activities

import androidx.compose.runtime.Composable
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.RoomData
import com.example.hostelaccounting3.mechanics.Tag
import com.example.hostelaccounting3.mechanics.Task
import com.example.hostelaccounting3.mechanics.editTask
import com.example.hostelaccounting3.requests.goInParallel


@Composable
fun Context.EditTaskForm(
    task: Task, onClose: () -> Unit, onSuccess: (Task, Task) -> Unit, allTagsList: List<Tag>, currentRoomData: RoomData
) = TaskForm(onClose, { name, description ->
    val result = Task(
        id = task.id,
        name = name,
        description = description,
        roomId = currentRoomData.roomId,
        datetimeOpened = task.datetimeOpened,
        datetimeClosed = task.datetimeClosed
    )
    editTask(
        roomId = currentRoomData.roomId, task = result
    ).goInParallel()
    onSuccess(task, result)
}, "Применить", task.name, task.description, allTagsList = allTagsList)
