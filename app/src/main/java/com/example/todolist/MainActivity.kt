package com.example.todolist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.foundation.pager.PagerState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.todolist.OnBoarding.OnBoardingScreen
import com.example.todolist.OnBoarding.OnBoardingUtils
import com.example.todolist.data.Tasks
import com.example.todolist.presentation.ui.event.HomeUiEvents
import com.example.todolist.presentation.ui.viewmodel.HomeViewModel
import com.example.todolist.ui.theme.BtnAddColor
import com.example.todolist.ui.theme.BtnSelectedColor
import com.example.todolist.ui.theme.BtnTextColor
import com.example.todolist.ui.theme.CardBacgroundColor
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.ui.theme.interFont
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val onBoardingUtils by lazy { OnBoardingUtils(this) }

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ToDoListTheme {
                if (onBoardingUtils.isOnboardingCompleted()) {
                    Home()
                } else {
                    ShowOnBoardingScreen()
                }
            }
        }
    }

    enum class FilterType {
        ALL,
        ACTIVE,
        COMPLETED
    }

    @Composable
    fun Home() {
        HomeScreenContent(homeViewModel)
    }

    @Composable
    fun ShowOnBoardingScreen() {
        val scope = rememberCoroutineScope()

        OnBoardingScreen {
            onBoardingUtils.setOnboardingCompleted()
            scope.launch {
                setContent {
                    Home()
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ToDoListTheme {
            Home()
        }
    }
}