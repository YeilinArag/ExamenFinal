package com.example.rentavirtual.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rentavirtual.viewmodel.MainViewModel

@Composable
fun HomeScreen(navController: NavController, vm: MainViewModel = MainViewModel()) {
    LaunchedEffect(Unit) { vm.loadInitialData() }
    val user by remember { vm.currentUser }
    val admin by remember { vm.isAdmin }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Bienvenido ${user?.fullName ?: "Usuario"}", style = MaterialTheme.typography.h6)
        Spacer(Modifier.height(12.dp))
        Button(onClick = { navController.navigate("profile") }, modifier = Modifier.fillMaxWidth()) { Text("Perfil de usuario") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.navigate("movies") }, modifier = Modifier.fillMaxWidth()) { Text("Lista de películas") }
        Spacer(Modifier.height(8.dp))
        if (admin.value) {
            Button(onClick = { navController.navigate("adminReport") }, modifier = Modifier.fillMaxWidth()) { Text("Informe de rentas (Admin)") }
        }
    }
}
