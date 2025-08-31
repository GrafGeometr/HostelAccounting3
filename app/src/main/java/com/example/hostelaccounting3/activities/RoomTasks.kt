package com.example.hostelaccounting3.activities

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.RoomData
import com.example.hostelaccounting3.mechanics.Task
import com.example.hostelaccounting3.mechanics.deleteTask
import com.example.hostelaccounting3.requests.goInParallel


@Composable
fun Context.RoomTasks(
    currentRoomData: RoomData,
    addTask: (Task) -> Unit,
    editTask: (Task, Task) -> Unit,
    deleteTask: (Task) -> Unit,
    rerender: Boolean
) {
    val roomId = currentRoomData.roomId

    var showAddForm by remember { mutableStateOf(false) }
    var editingTask: Task? by remember { mutableStateOf(null) }
    var deletingTask: Task? by remember { mutableStateOf(null) }

    Box(modifier = Modifier.fillMaxWidth()) {

        if (showAddForm) {
            AddTaskForm(
                onClose = { showAddForm = false },
                onSuccess = addTask,
                allTagsList = currentRoomData.tags,
                currentRoomData
            )
        }

        if (editingTask != null) {
            EditTaskForm(
                task = editingTask!!,
                onClose = { editingTask = null },
                onSuccess = editTask,
                allTagsList = currentRoomData.tags,
                currentRoomData
            )
        }

        if (deletingTask != null) {
            val task = deletingTask!!
            DeleterForm(unsafeDeleter = {
                deleteTask(roomId, task).goInParallel()
                deleteTask(task)
            }) {
                deletingTask = null
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            currentRoomData.tasks.forEach { task ->
                DisplayOneTask(task, onEdit = {
                    editingTask = task
                    showAddForm = false
                }, onDelete = {
                    deletingTask = task
                }, editTask = editTask, currentRoomData = currentRoomData)
            }
            Spacer(Modifier.height(48.dp))
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp)
        ) {
            Button(
                onClick = {
                    showAddForm = true
                    editingTask = null
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Добавить задание",
                    tint = MaterialTheme.colorScheme.onError
                )
            }
        }
    }
}

