package com.example.hostelaccounting3.activities

import androidx.compose.runtime.Composable
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.datetime.Datetime
import com.example.hostelaccounting3.mechanics.*
import com.example.hostelaccounting3.requests.goInParallel

@Composable
fun Context.AddTaskForm(
    onClose: () -> Unit, onSuccess: (Task) -> Unit, allTagsList: List<Tag>, currentRoomData: RoomData
) = TaskForm(onClose, { name, description ->
    val task = Task(
        id = genRandomId(),
        name = name,
        description = description,
        roomId = currentRoomData.roomId,
        datetimeOpened = Datetime.now(),
        datetimeClosed = null
    )
    addTask(
        roomId = currentRoomData.roomId, task
    ).goInParallel()
    onSuccess(task)
}, submitButtonText = "Добавить", allTagsList = allTagsList)
