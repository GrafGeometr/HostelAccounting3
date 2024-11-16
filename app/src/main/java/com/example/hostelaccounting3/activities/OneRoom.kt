package com.example.hostelaccounting3.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.hostelaccounting3.R
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.mechanics.*

@Composable
fun Context.OneRoom(
    onKillRoom: () -> Unit,
    roomData: RoomData,
    rerender: Boolean,
    addPurchase: (Purchase) -> Unit,
    editPurchase: (Purchase, Purchase) -> Unit,
    deletePurchase: (Purchase) -> Unit,
    addTransaction: (Transaction) -> Unit,
    deleteTransaction: (Transaction) -> Unit,
    addTask: (Task) -> Unit,
    editTask: (Task, Task) -> Unit,
    deleteTask: (Task) -> Unit,
    addTag: (Tag) -> Unit,
    editTag: (Tag, Tag) -> Unit,
    deleteTag: (Tag) -> Unit
) {
    println("debug")
    println(roomData.financesInfo.totalMonth)
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

//    var currentRoomData by remember { mutableStateOf(roomData) }
    val currentRoomData = roomData

    var navbar by remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxSize().padding(top = 32.dp)) {
        Row(
            modifier = Modifier.weight(10f)
        ) {
            println("Tags debug in room")
            println(currentRoomData.tags)
            when (navbar) {
                0 -> RoomPurchases(currentRoomData, addPurchase, editPurchase, deletePurchase, rerender)

                1 -> RoomFinances(currentRoomData, addTransaction, deleteTransaction, rerender)

                2 -> RoomTasks(currentRoomData, addTask, editTask, deleteTask, rerender)

                3 -> RoomTags(currentRoomData, addTag, editTag, deleteTag, rerender)
            }
        }
        // horizontal divider large and black
        HorizontalDivider(
            thickness = 2.dp, color = Color.Black, modifier = Modifier.fillMaxWidth().zIndex(1f)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                // color primary container
                .background(MaterialTheme.colorScheme.onTertiary).weight(1f)
        ) {
            Button(
                onClick = { navbar = 0 }, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Image(
                    painter = purchasesImage,
                    contentDescription = "Your SVG image",
                    modifier = Modifier.size(24.dp).alpha(if (navbar == 0) 1f else 0.5f),
                )
            }
            Button(
                onClick = { navbar = 1 }, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Image(
                    painter = financesImage,
                    contentDescription = "Your SVG image",
                    modifier = Modifier.size(24.dp).alpha(if (navbar == 1) 1f else 0.5f),
                )
            }
            Button(
                onClick = { navbar = 2 }, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Image(
                    painter = tasksImage,
                    contentDescription = "Your SVG image",
                    modifier = Modifier.size(24.dp).alpha(if (navbar == 2) 1f else 0.5f),
                )
            }
            Button(
                onClick = { navbar = 3 }, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Image(
                    painter = tagsImage,
                    contentDescription = "Your SVG image",
                    modifier = Modifier.size(24.dp).alpha(if (navbar == 3) 1f else 0.5f),
                )
            }
            Button(
                onClick = onKillRoom, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Image(
                    painter = logoutImage,
                    contentDescription = "Your SVG image",
                    modifier = Modifier.size(24.dp).alpha(0.5f),
                )
            }
        }
    }
}