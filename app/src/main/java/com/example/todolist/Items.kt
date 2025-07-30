package com.example.todolist

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.todolist.data.Tasks
import com.example.todolist.ui.theme.CheckBoxColor
import com.example.todolist.ui.theme.ItemBackgroundColor
import com.example.todolist.ui.theme.ItemTextColor
import com.example.todolist.ui.theme.interFont
import kotlin.math.roundToInt

@Composable
fun Items(
    task: Tasks,
    onToggle: (Tasks) -> Unit,
    onDelete: (Tasks) -> Unit
) {
    var checkedState by remember(task.id) { mutableStateOf(task.iSelected) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var isSwiped by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Red, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.CenterEnd,
        ) {
            if (isSwiped) {
                IconButton(
                    onClick = {

                        offsetX = 0f
                        isSwiped = false

                        onDelete(task)

                    },
                    modifier = Modifier.padding(end = 16.dp),
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.delete),
                        tint = Color.Unspecified,
                        contentDescription = null,
                    )
                }

            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .fillMaxSize()
                .background(ItemBackgroundColor, shape = RoundedCornerShape(12.dp))
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            offsetX = (offsetX + dragAmount).coerceIn(-300f, 0f)
                        },
                        onDragEnd = {
                            isSwiped = offsetX < -120f
                            if (!isSwiped) {
                                offsetX = 0f
                            }
                        }
                    )
                }
        ) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = { isChecked ->
                    checkedState = isChecked
                    onToggle(task.copy(iSelected = isChecked))
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = CheckBoxColor,
                    uncheckedColor = CheckBoxColor
                ),
            )
            Text(
                text = task.taskName,
                color = ItemTextColor,
                fontFamily = interFont,
            )
        }
    }

}
