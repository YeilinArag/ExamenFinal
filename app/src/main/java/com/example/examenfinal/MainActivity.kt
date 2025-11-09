package com.example.examenfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.examenfinal.ui.AppNavHost
import com.example.examenfinal.ui.theme.ExamenFinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExamenFinalTheme {
                val navController = rememberNavController()
                val mainViewModel: MainViewModel = viewModel()

                AppNavHost(
                    navController = navController,
                    mainViewModel = mainViewModel
                )
            }
        }
    }
}
