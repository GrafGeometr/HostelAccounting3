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

@Composable
fun Context.RoomTags() {
    val roomId = roomsManager.getCurrentRoomId()!!

    var tags by remember { mutableStateOf(getTags(roomId)) }

    var showAddForm by remember { mutableStateOf(false) }
    var editingTag: Tag? by remember { mutableStateOf(null) }

    Box(modifier = Modifier.fillMaxWidth()) {

        if (showAddForm) {
            AddTagForm(onClose = { showAddForm = false }, onSuccess = { tags += it })
        }

        if (editingTag != null) {
            EditTagForm(tag = editingTag!!, onClose = { editingTag = null }, onSuccess = { p1, p2 ->
                tags = tags.map { if (it.id == p1.id) p2 else it }
            })
        }

        Column(
            modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tags.forEachIndexed { idx, tag ->
                DisplayOneTag(tag, onEdit = {
                    editingTag = tag
                    showAddForm = false
                }, onDelete = {
                    deleteTag(tagId = tag.id)
                    tags = tags.filter { it != tag }
                })
                if (idx != tags.lastIndex) {
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
                    editingTag = null
                }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Добавить тег",
                    tint = MaterialTheme.colorScheme.onError
                )
            }
        }
    }
}

