package com.example.hostelaccounting3.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.hostelaccounting3.RoomFinances
import com.example.hostelaccounting3.RoomTasks
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.R

@Composable
fun Context.OneRoom(onKillRoom: () -> Unit) {
    val purchasesImage = rememberVectorPainter(
        ImageVector.vectorResource(R.drawable.purchases)
    )
    val financesImage = rememberVectorPainter(
        ImageVector.vectorResource(R.drawable.finances)
    )
    val tasksImage = rememberVectorPainter(
        ImageVector.vectorResource(R.drawable.tasks)
    )
    val tagsImage = rememberVectorPainter(
        ImageVector.vectorResource(R.drawable.tags)
    )
    val logoutImage = rememberVectorPainter(
        ImageVector.vectorResource(R.drawable.logout)
    )
    val roomId = roomsManager.getCurrentRoomId()
    var navbar by remember { mutableStateOf(0) }
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth()
                // color primary container
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(bottom = 12.dp, top = 36.dp)
        ) {
            Button(
                onClick = { navbar = 0 },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Image(
                    painter = purchasesImage,
                    contentDescription = "Your SVG image",
                    modifier = Modifier.size(24.dp)
                )
            }
            Button(
                onClick = { navbar = 1 },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Image(
                    painter = financesImage,
                    contentDescription = "Your SVG image",
                    modifier = Modifier.size(24.dp)
                )
            }
            Button(
                onClick = { navbar = 2 },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Image(
                    painter = tasksImage,
                    contentDescription = "Your SVG image",
                    modifier = Modifier.size(24.dp)
                )
            }
            Button(
                onClick = { navbar = 3 },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Image(
                    painter = tagsImage,
                    contentDescription = "Your SVG image",
                    modifier = Modifier.size(24.dp)
                )
            }
            Button(
                onClick = { navbar = 3 },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Image(
                    painter = logoutImage,
                    contentDescription = "Your SVG image",
                    modifier = Modifier.size(24.dp)
                )
            }
//            Button(
//                onClick = {
//                    roomsManager.killRoom()
//                    onKillRoom()
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
//            ) {
//                Image(
//                    painter = tagsImage,
//                    contentDescription = "Your SVG image",
//                    modifier = Modifier.size(40.dp).background(Color.Red)
//                )
//            }
        }
        // horizontal divider large and black
        HorizontalDivider(
            thickness = 2.dp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f)
        )
        when (navbar) {
            0 -> RoomPurchases()
            1 -> RoomFinances()
            2 -> RoomTasks()
            3 -> RoomTags()
        }
    }
}