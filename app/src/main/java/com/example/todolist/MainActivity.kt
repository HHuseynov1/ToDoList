package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.presentation.ui.OnBoarding.OnBoardingScreen
import com.example.todolist.presentation.ui.OnBoarding.OnBoardingUtils
import com.example.todolist.presentation.ui.home.HomeScreenContent
import com.example.todolist.presentation.ui.home.HomeUiState
import com.example.todolist.presentation.ui.home.HomeViewModel
import com.example.todolist.ui.theme.ToDoListTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            ToDoListTheme {
                MainNavHost(
                    navController = navController
                )
            }
        }
    }

    enum class FilterType {
        ALL,
        ACTIVE,
        COMPLETED
    }

//    @Composable
//    fun Home() {
//        val viewModel = hiltViewModel<HomeViewModel>()
//        val state by viewModel.homeUiState.collectAsState()
//
//        HomeScreenContent(
//            state = state,
//            onEvent = viewModel::homeUiEvent
//        )
//    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ToDoListTheme {
            MainNavHost(navController = rememberNavController())
        }
    }
}

@Composable
fun MainNavHost(navController: NavHostController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val state by viewModel.homeUiState.collectAsState()
    val onBoardingUtils = OnBoardingUtils(LocalContext.current)

    NavHost(
        navController = navController,
        startDestination = if (onBoardingUtils.isOnboardingCompleted()) ScreenRoute.Home.route else "onboarding"
    ) {
        composable(ScreenRoute.Home.route) {
            HomeScreenContent(
                state = state,
                onEvent = viewModel::homeUiEvent
            )
        }

        composable("onboarding") {
            OnBoardingScreen(
                onFinished = {
                    onBoardingUtils.setOnboardingCompleted()
                    navController.navigate(ScreenRoute.Home.route) {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }
    }
}

@Serializable
sealed class ScreenRoute(val route: String) {
    @Serializable
    object Home : ScreenRoute("home")
}