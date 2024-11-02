package com.example.hostelaccounting3.activities

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.Task
import com.example.hostelaccounting3.mechanics.editTask

@Composable
fun Context.DisplayOneTask(
    task: Task, onEdit: () -> Unit, onDelete: () -> Unit,
    forceEdit: (List<Task>) -> Unit, allTasks: List<Task>
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp).padding(vertical = 8.dp)
        ) {
            Row {
                Checkbox(checked = !task.isOpened, onCheckedChange = {
                    val newTask = editTask(
                        task.roomId,
                        task.id,
                        task.name,
                        task.description,
                        !task.isOpened
                    )
                    forceEdit(allTasks.map { if (it.id == task.id) newTask else it })
                })
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        task.name,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            textDecoration = if (task.isOpened) TextDecoration.None else TextDecoration.LineThrough
                        )
                    )
                    Text(
                        task.description,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        task.datetimeOpened.toString(),
                        modifier = Modifier.padding(end = 8.dp, bottom = 4.dp).alpha(0.5f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Filled.Edit, contentDescription = "Edit task"
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Filled.Delete, contentDescription = "Delete task"
                    )
                }
            }
        }
    }
}