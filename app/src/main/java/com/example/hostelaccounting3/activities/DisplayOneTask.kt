package com.example.hostelaccounting3.activities

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.hostelaccounting3.Palette
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.datetime.Datetime
import com.example.hostelaccounting3.mechanics.RoomData
import com.example.hostelaccounting3.mechanics.Task
import com.example.hostelaccounting3.mechanics.editTask
import com.example.hostelaccounting3.requests.goInParallel


@Composable
fun Context.DisplayOneTask(
    task: Task, onEdit: () -> Unit, onDelete: () -> Unit, editTask: (Task, Task) -> Unit, currentRoomData: RoomData
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp).padding(vertical = 8.dp)
        ) {
            Row (verticalAlignment = Alignment.CenterVertically) {

                var checked by remember { mutableStateOf(false) }
                val checkedColor: Color = Palette.container1
                val uncheckedColor: Color = Palette.background
                val modifier = Modifier
                val isChecked = !task.isOpened
                val size = 32f

                val checkboxColor: Color by animateColorAsState(if (isChecked) checkedColor else uncheckedColor)
                val density = LocalDensity.current
                val duration = 200

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .heightIn(48.dp) // height of 48dp to comply with minimum touch target size
                        .toggleable(
                            value = isChecked,
                            role = Role.Checkbox,
                            onValueChange = {
                                val result = Task(
                                    id = task.id,
                                    name = task.name,
                                    description = task.description,
                                    roomId = task.roomId,
                                    datetimeOpened = task.datetimeOpened,
                                    datetimeClosed = if (task.isOpened) Datetime.now() else null
                                )
                                editTask(currentRoomData.roomId, result).goInParallel()
                                editTask(task, result)
                                checked = it
                            }
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .size(size.dp)
                            .background(color = checkboxColor, shape = RoundedCornerShape(50))
                            .border(width = 1.5.dp, color = checkedColor, shape = RoundedCornerShape(50)),
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = isChecked,
                            enter = slideInHorizontally(animationSpec = tween(duration)) {
                                with(density) { (size * -0.5).dp.roundToPx() }
                            } + expandHorizontally(
                                expandFrom = Alignment.Start,
                                animationSpec = tween(duration)
                            ),
                            exit = fadeOut()
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                tint = uncheckedColor
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    Text(
                        task.name,
                        modifier = Modifier.fillMaxWidth().padding(bottom=16.dp),
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