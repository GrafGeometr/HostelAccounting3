package com.example.hostelaccounting3.activities

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.*


// TODO: checkbox (opened/closed)
// Initially: checked := (datetime_closed == False)
// When switched: POST request to tasks/update with {'is_opened': checked}
@Composable
fun Context.RoomTasks() {
    val roomId = roomsManager.getCurrentRoomId()!!

    var tasks by remember { mutableStateOf(getTasks(roomId)) }

    var showAddForm by remember { mutableStateOf(false) }
    var editingTask: Task? by remember { mutableStateOf(null) }

    Box(modifier = Modifier.fillMaxWidth().padding(bottom = 64.dp)) {

//        if (showAddForm) {
//            AddTaskForm(onClose = { showAddForm = false }, onSuccess = { tasks += it })
//        }
//
//        if (editingTask != null) {
//            EditTaskForm(task = editingTask!!, onClose = { editingTask = null }, onSuccess = { p1, p2 ->
//                tasks = tasks.map { if (it.id == p1.id) p2 else it }
//            })
//        }

        Column(
            modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tasks.forEachIndexed { idx, task ->
                DisplayOneTask(task, onEdit = {
                    editingTask = task
                    showAddForm = false
                }, onDelete = {
                    deleteTask(taskId = task.id)
                    tasks = tasks.filter { it != task }
                })
                if (idx != tasks.lastIndex) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                }
            }
            Spacer(Modifier.height(48.dp))
        }

        Box(
            modifier = Modifier.align(Alignment.BottomStart).padding(start = 16.dp)
        ) {
            Button(
                onClick = {
                    showAddForm = true
                    editingTask = null
                }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
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

