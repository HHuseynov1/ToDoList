package com.example.todolist.presentation.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.todolist.MainActivity
import com.example.todolist.data.Tasks
import com.example.todolist.presentation.ui.home.HomeUiEvents
import com.example.todolist.presentation.ui.home.components.Items
import com.example.todolist.ui.theme.BtnAddColor
import com.example.todolist.ui.theme.BtnSelectedColor
import com.example.todolist.ui.theme.BtnTextColor
import com.example.todolist.ui.theme.CardBacgroundColor
import com.example.todolist.ui.theme.interFont
import kotlinx.coroutines.launch

@Composable
fun HomeScreenContent(
    state: HomeUiState,
    onEvent: (HomeUiEvents) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { MainActivity.FilterType.entries.size })

    Scaffold(
        topBar = {
            ConstraintLayout {

                val topGuideline = createGuidelineFromTop(0.3f)

                val (txtTitle, boxSelections) = createRefs()

                Text(
                    "Today",
                    fontFamily = interFont,
                    fontSize = 36.sp,
                    modifier = Modifier
                        .constrainAs(txtTitle) {
                            top.linkTo(topGuideline)
                        }
                        .padding(horizontal = 16.dp)
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .constrainAs(boxSelections) {
                            top.linkTo(anchor = txtTitle.bottom, 20.dp)
                        },
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                ) {

                    val currentFilter = state.filterChange
                    Log.e("currentFilter", currentFilter.toString())

                    val scope = rememberCoroutineScope()

                    MainActivity.FilterType.entries.forEach { type ->
                        Button(
                            onClick = {
                                scope.launch {
                                    onEvent(HomeUiEvents.filterChange(type))
                                    pagerState.animateScrollToPage(type.ordinal)
                                }
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = if (currentFilter == type) ButtonDefaults.buttonColors(
                                containerColor = BtnSelectedColor
                            ) else ButtonDefaults.buttonColors(containerColor = CardBacgroundColor)
                        ) {
                            Text(
                                text = type.name,
                                color = BtnTextColor,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            var textFieldState by remember { mutableStateOf("") }

                //val bottomGuideLine = createGuidelineFromBottom(0.5f)

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp).systemBarsPadding(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TextField(
                    modifier = Modifier
//                        .constrainAs(txtField) {
//                            bottom.linkTo(bottomGuideLine)
//                        }
                        .height(56.dp)
                        .weight(1f)
                        .padding(start = 10.dp),
                    value = textFieldState,
                    label = { Text(text = "Write a task") },
                    onValueChange = {
                        textFieldState = it
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                )

                Button(
                    onClick = {
                        if (textFieldState.isNotEmpty()) {
                            onEvent(
                                HomeUiEvents.addTask(
                                    Tasks(
                                        0,
                                        false,
                                        textFieldState
                                    )
                                )
                            )
                        }

                        textFieldState = ""

                        Log.e("textFieldState", textFieldState)
                    },
                    modifier = Modifier.height(56.dp).padding(end = 10.dp),
                    colors = ButtonDefaults.buttonColors(BtnAddColor),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Add", modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        },
        content = { innerPadding ->
            Box(Modifier.padding(innerPadding)) {

                val currentFilter = state.filterChange

                val filteredList = when (currentFilter) {

                    MainActivity.FilterType.ALL -> state.all.reversed()
                        .sortedBy { it.iSelected }

                    MainActivity.FilterType.ACTIVE -> state.all.filter { !it.iSelected }
                        .reversed()

                    MainActivity.FilterType.COMPLETED -> state.all.filter { it.iSelected }
                        .reversed()
                }

                Log.e("filteredList", filteredList.size.toString())

                if (filteredList.isEmpty()) {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { Text("There is no task yet") }
                } else {
                    HorizontalPager(
                        state = pagerState,
                        userScrollEnabled = false
                    ) {
                        LazyColumn {
                            items(filteredList) { task ->
                                Items(
                                    task = task,
                                    onToggle = { updatedTask ->
                                        onEvent(HomeUiEvents.update(updatedTask))
                                    },
                                    onDelete = { deletedTask ->
                                        onEvent(HomeUiEvents.deleteItem(deletedTask))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun Preview() {
    HomeScreenContent(
        HomeUiState()
    ) { }
}