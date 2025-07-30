package com.example.todolist

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.todolist.data.Tasks
import com.example.todolist.presentation.ui.event.HomeUiEvents
import com.example.todolist.presentation.ui.viewmodel.HomeViewModel
import com.example.todolist.ui.theme.BtnAddColor
import com.example.todolist.ui.theme.BtnSelectedColor
import com.example.todolist.ui.theme.BtnTextColor
import com.example.todolist.ui.theme.CardBacgroundColor
import com.example.todolist.ui.theme.interFont
import kotlinx.coroutines.launch

@Composable
fun HomeScreenContent(viewModel: HomeViewModel) {

    val pagerState = rememberPagerState { 3 }

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
                        .constrainAs(boxSelections) {
                            top.linkTo(anchor = txtTitle.bottom, 20.dp)
                        },
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    val currentPage by viewModel.homeUiState.collectAsState()

                    val currentFilter = currentPage.filterChange
                    Log.e("currentFilter", currentFilter.toString())

                    val scope = rememberCoroutineScope()

                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.homeUiEvent(HomeUiEvents.filterChange(MainActivity.FilterType.ALL))
                                pagerState.animateScrollToPage(1)
                            }
                        },
                        Modifier.width(120.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = if (currentFilter == MainActivity.FilterType.ALL) ButtonDefaults.buttonColors(
                            containerColor = BtnSelectedColor
                        ) else ButtonDefaults.buttonColors(containerColor = CardBacgroundColor)
                    )
                    {
                        Text(
                            "All",
                            color = BtnTextColor
                        )
                    }
                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.homeUiEvent(HomeUiEvents.filterChange(MainActivity.FilterType.ACTIVE))
                                pagerState.animateScrollToPage(2)
                            }
                        },
                        Modifier.width(120.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = if (currentFilter == MainActivity.FilterType.ACTIVE) ButtonDefaults.buttonColors(
                            containerColor = BtnSelectedColor
                        ) else ButtonDefaults.buttonColors(containerColor = CardBacgroundColor)
                    ) {
                        Text(
                            "Active",
                            color = BtnTextColor
                        )
                    }
                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.homeUiEvent(HomeUiEvents.filterChange(MainActivity.FilterType.COMPLETED))
                                pagerState.animateScrollToPage(3)
                            }
                        },
                        Modifier.width(120.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = if (currentFilter == MainActivity.FilterType.COMPLETED) ButtonDefaults.buttonColors(
                            containerColor = BtnSelectedColor
                        ) else ButtonDefaults.buttonColors(containerColor = CardBacgroundColor)
                    ) {
                        Text(
                            "Completed",
                            color = BtnTextColor
                        )
                    }
                }
            }
        },
        bottomBar = {
            var textFieldState by remember { mutableStateOf("") }

            ConstraintLayout(Modifier.fillMaxWidth()) {

                val bottomGuideLine = createGuidelineFromBottom(0.5f)

                val (txtField, btnAdd) = createRefs()

                TextField(
                    modifier = Modifier
                        .constrainAs(txtField) {
                            bottom.linkTo(bottomGuideLine)
                        }
                        .height(56.dp)
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
                            viewModel.homeUiEvent(
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
                    Modifier
                        .constrainAs(btnAdd) {
                            start.linkTo(txtField.end)
                            bottom.linkTo(txtField.bottom)
                            end.linkTo(parent.end)
                            top.linkTo(txtField.top)
                        }
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(BtnAddColor),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Add")
                }
            }
        },
        content = { innerPadding ->
            Box(Modifier.padding(innerPadding)) {

                val homeUiState by viewModel.homeUiState.collectAsState()

                val currentFilter = homeUiState.filterChange

                val filteredList = when (currentFilter) {

                    MainActivity.FilterType.ALL -> homeUiState.all.reversed().sortedBy { it.iSelected }
                    MainActivity.FilterType.ACTIVE -> homeUiState.all.filter { !it.iSelected }.reversed()
                    MainActivity.FilterType.COMPLETED -> homeUiState.all.filter { it.iSelected }.reversed()
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
                                        viewModel.homeUiEvent(HomeUiEvents.update(updatedTask))
                                    },
                                    onDelete = { deletedTask ->
                                        viewModel.homeUiEvent(HomeUiEvents.deleteItem(deletedTask))
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