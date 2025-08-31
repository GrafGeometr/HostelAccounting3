package com.example.hostelaccounting3.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.RoomData
import com.example.hostelaccounting3.mechanics.Tag
import com.example.hostelaccounting3.mechanics.deleteTag
import com.example.hostelaccounting3.requests.goInParallel


@Composable
fun Context.RoomTags(
    currentRoomData: RoomData,
    addTag: (Tag) -> Unit,
    editTag: (Tag, Tag) -> Unit,
    deleteTag: (Tag) -> Unit,
    rerender: Boolean
) {
    val tags = currentRoomData.tags

    println("Tags debug")
    println(tags)

    var showAddForm by remember { mutableStateOf(false) }
    var editingTag: Tag? by remember { mutableStateOf(null) }
    var deletingTag: Tag? by remember { mutableStateOf(null) }

    Box(modifier = Modifier.fillMaxWidth()) {

        if (showAddForm) AddTagForm(
            onClose = { showAddForm = false }, onSuccess = addTag, currentRoomData
        )

        if (editingTag != null) EditTagForm(
            tag = editingTag!!,
            onClose = { editingTag = null },
            onSuccess = editTag,
            currentRoomData
        )

        if (deletingTag != null) {
            val tag: Tag = deletingTag!!
            DeleterForm(unsafeDeleter = {
                deleteTag(tagId = tag.id).goInParallel()
                deleteTag(tag)
            }) {
                deletingTag = null
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tags.forEachIndexed { idx, tag ->
                DisplayOneTag(tag, onEdit = {
                    editingTag = tag
                    showAddForm = false
                }, onDelete = {
                    deletingTag = tag
                })
                if (idx != tags.lastIndex) HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            }
            Spacer(Modifier.height(48.dp))
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp)
                .background(Color.Transparent)
        ) {
            Button(
                onClick = {
                    showAddForm = true
                    editingTag = null
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
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

