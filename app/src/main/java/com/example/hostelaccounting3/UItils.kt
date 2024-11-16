package com.example.hostelaccounting3


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun box(modifier: Modifier, content: @Composable () -> Unit) {
    Box(modifier = modifier.padding(0.dp)) {
        Box(modifier = modifier.background(Color.Transparent)) {
            content()
        }
    }
}

@Composable
fun column(modifier: Modifier, content: @Composable () -> Unit) {
    Box(modifier = modifier.padding(0.dp)) {
        Column(modifier = modifier.background(Color.Transparent)) {
            content()
        }
    }
}

@Composable
fun row(
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.padding(0.dp)) {
        Row(
            modifier = modifier.background(Color.Transparent),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment
        ) {
            content()
        }
    }
}

@Composable
fun button(modifier: Modifier, onClick: () -> Unit, content: @Composable () -> Unit) {
    Box(modifier = modifier.padding(0.dp)) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = modifier
        ) {
            content()
        }
    }
}