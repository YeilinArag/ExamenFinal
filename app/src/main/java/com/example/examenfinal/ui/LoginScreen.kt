package com.example.rentavirtual.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rentavirtual.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController, vm: MainViewModel = MainViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center) {
        Text("Iniciar sesión", style = MaterialTheme.typography.h5)
        OutlinedTextField(email, { email = it }, label = { Text("Email") })
        OutlinedTextField(password, { password = it }, label = { Text("Contraseña") })
        Spacer(Modifier.height(12.dp))
        Button(onClick = {
            scope.launch {
                try {
                    vm.repo.login(email.trim(), password.trim()) // direct call to repo
                    vm.loadInitialData()
                    navController.navigate("home") { popUpTo("login") { inclusive = true } }
                } catch (e: Exception) {
                    error = e.message
                }
            }
        }) { Text("Entrar") }
        TextButton(onClick = { navController.navigate("register") }) { Text("Registrarse") }
        error?.let { Text(it, color = MaterialTheme.colors.error) }
    }
}
