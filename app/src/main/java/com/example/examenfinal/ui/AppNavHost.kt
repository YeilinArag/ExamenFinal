package com.example.examenfinal.ui
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.examenfinal.MainViewModel
import com.example.examenfinal.ui.login.LoginScreen
import com.example.examenfinal.theme.AdminReportScreen
import com.example.examenfinal.ui.HomeScreen
import com.example.examenfinal.ui.theme.MovieListScreen
import com.example.examenfinal.ui.theme.RegisterScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // LOGIN
        composable("login") {
            LoginScreen(
                mainViewModel = mainViewModel,
                onLoginSuccess = { isAdmin ->
                    if (isAdmin) {
                        navController.navigate("adminReport") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        // REGISTRO
        composable("register") {
            RegisterScreen(
                mainViewModel = mainViewModel,
                onRegisterSuccess = {
                    navController.popBackStack() // vuelve al login
                }
            )
        }

        // HOME (menú de usuario)
        composable("home") {
            HomeScreen(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }

        // LISTA DE PELÍCULAS
        composable("movies") {
            MovieListScreen(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }

        // INFORME ADMIN
        composable("adminReport") {
            AdminReportScreen(
                mainViewModel = mainViewModel
            )
        }
    }
}
