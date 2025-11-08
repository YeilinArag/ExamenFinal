package com.example.rentavirtual.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rentavirtual.viewmodel.MainViewModel

@Composable
fun RegisterScreen(navController: NavController, vm: MainViewModel = MainViewModel()) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var ageStr by remember { mutableStateOf("") }
    var cardLast4 by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center) {
        Text("Registro", style = MaterialTheme.typography.h5)
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Contraseña") })
        OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Nombre completo") })
        OutlinedTextField(value = ageStr, onValueChange = { ageStr = it.filter { c -> c.isDigit() } }, label = { Text("Edad") })
        OutlinedTextField(value = cardLast4, onValueChange = { cardLast4 = it.take(4).filter { c -> c.isDigit() } }, label = { Text("No. tarjeta (últimos 4)") })
        Spacer(Modifier.height(12.dp))
        Button(onClick = {
            vm.registerAndSaveProfile(email.trim(), pass.trim(), fullName.trim(), ageStr.toIntOrNull() ?: 0, cardLast4) { ok, err ->
                if (ok) {
                    message = "Registro OK. Inicia sesión."
                    navController.navigate("login")
                } else {
                    message = err
                }
            }
        }) { Text("Crear cuenta") }
        message?.let { Text(it, color = MaterialTheme.colors.onSurface) }
    }
}
