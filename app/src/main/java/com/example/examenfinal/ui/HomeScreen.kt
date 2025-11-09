package com.example.examenfinal.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.examenfinal.MainViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Text(text = "Menú principal")

        Spacer(modifier = Modifier.height(16.dp))

        // Ir al perfil
        Button(onClick = { navController.navigate("profile") }) {
            Text("Perfil de usuario")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Ir a lista de películas
        Button(onClick = { navController.navigate("movies") }) {
            Text("Lista de películas")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Solo admin: ir a informe
        val currentUser = mainViewModel.currentUser.value
        if (currentUser?.role == "admin") {
            Button(onClick = { navController.navigate("adminReport") }) {
                Text("Informe de rentas (Admin)")
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        // CERRAR SESIÓN
        Button(
            onClick = {
                mainViewModel.logout()
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            }
        ) {
            Text("Cerrar sesión")
        }
    }
}

