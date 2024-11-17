package com.example.hostelaccounting3.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.hostelaccounting3.mechanics.Tag
import com.example.hostelaccounting3.mechanics.findAutocomplete


@Composable
fun TaskForm(
    onClose: () -> Unit,
    onSuccess: (name: String, description: String) -> Unit,
    submitButtonText: String,
    nameDefault: String = "",
    descriptionDefault: String = "",
    allTagsList: List<Tag>,
) {
    val possibleSuggestions = allTagsList.map { it.name }

    var name by remember { mutableStateOf(nameDefault) }
    var description by remember { mutableStateOf(descriptionDefault) }

    var suggestions by remember { mutableStateOf(findAutocomplete(name, possibleSuggestions)) }
    var expanded by remember { mutableStateOf(suggestions.isNotEmpty()) }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Transparent).wrapContentSize(align = Alignment.TopCenter)
            .zIndex(10f),
    ) {
        Card(
            modifier = Modifier.padding(32.dp), elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(value = name, onValueChange = {
                    name = it
                    suggestions = findAutocomplete(name, possibleSuggestions)
                    expanded = suggestions.isNotEmpty()
                }, placeholder = { Text("Тег задачи") })
                if (expanded) Column(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.background, shape = MaterialTheme.shapes.medium
                    ).fillMaxWidth()
                ) {
                    suggestions.forEach { suggestion ->
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                name = suggestion
                                expanded = false
                            }) {
                            Text(
                                text = suggestion,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                TextField(value = description,
                    onValueChange = { description = it },
                    placeholder = { Text("Описание задачи") })
                Button(onClick = {
                    onSuccess(name, description)
                    onClose()
                }) {
                    Text(submitButtonText)
                }
                Button(onClick = onClose) {
                    Text("Закрыть")
                }
            }
        }
    }
}