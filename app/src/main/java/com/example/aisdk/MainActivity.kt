package com.example.aisdk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aisdk.ui.screens.HomeScreen
import com.example.aisdk.ui.screens.Lesson1ExtractionScreen
import com.example.aisdk.ui.screens.Lesson2ClassificationScreen
import com.example.aisdk.ui.screens.Lesson3SummarizationScreen
import com.example.aisdk.ui.screens.Lesson4AppointmentExtractionScreen
import com.example.aisdk.ui.screens.Lesson5ChatbotScreen
import com.example.aisdk.ui.theme.AISDKTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AISDKTutorialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AISDKAppNavigation()
                }
            }
        }
    }
}

@Composable
fun AISDKAppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToLesson1 = { navController.navigate("lesson1") },
                onNavigateToLesson2 = { navController.navigate("lesson2") },
                onNavigateToLesson3 = { navController.navigate("lesson3") },
                onNavigateToLesson4 = { navController.navigate("lesson4") },
                onNavigateToLesson5 = { navController.navigate("lesson5") }
            )
        }
        composable("lesson1") {
            Lesson1ExtractionScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("lesson2") {
            Lesson2ClassificationScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("lesson3") {
            Lesson3SummarizationScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("lesson4") {
            Lesson4AppointmentExtractionScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("lesson5") {
            Lesson5ChatbotScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
