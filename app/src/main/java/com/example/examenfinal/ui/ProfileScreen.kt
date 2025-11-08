package com.example.rentavirtual.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.rentavirtual.viewmodel.MainViewModel

@Composable
fun ProfileScreen(navController: NavController, vm: MainViewModel = MainViewModel()) {
    val userState = vm.currentUser
    val user = userState.value
    var name by remember { mutableStateOf(user?.fullName ?: "") }
    var age by remember { mutableStateOf(user?.age?.toString() ?: "") }
    var last4 by remember { mutableStateOf(user?.cardNumberLast4 ?: "") }
    var msg by remember { mutableStateOf<String?>(null) }

    // photo picker
    var pickedUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) pickedUri = uri
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Perfil", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(12.dp))
        if (!user?.photoUrl.isNullOrBlank()) {
            Image(painter = rememberAsyncImagePainter(user?.photoUrl), contentDescription = "Foto", modifier = Modifier.size(120.dp))
        } else {
            Box(modifier = Modifier.size(120.dp)) { Text("Sin foto") }
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { launcher.launch("image/*") }) { Text("Seleccionar foto") }
        pickedUri?.let {
            Spacer(Modifier.height(8.dp))
            Image(painter = rememberAsyncImagePainter(it), contentDescription = "Preview", modifier = Modifier.size(120.dp))
            Spacer(Modifier.height(8.dp))
            Button(onClick = {
                // subir foto
                val uid = user?.uid ?: return@Button
                vm.uploadPhotoAndSave(uid, it) { ok, err ->
                    msg = if (ok) "Foto subida" else "Error: $err"
                }
            }) { Text("Subir foto") }
        }

        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre completo") })
        OutlinedTextField(value = age, onValueChange = { age = it.filter { c -> c.isDigit() } }, label = { Text("Edad") })
        OutlinedTextField(value = last4, onValueChange = { last4 = it.take(4).filter { c -> c.isDigit() } }, label = { Text("No. tarjeta (últimos 4)") })
        Spacer(Modifier.height(12.dp))
        Button(onClick = {
            // actualizar doc users/{uid}
            val u = user?.copy(fullName = name, age = age.toIntOrNull() ?: 0, cardNumberLast4 = last4)
            if (u != null) {
                vm.viewModelScope.launch {
                    try {
                        vm.repo.saveUserProfile(u)
                        vm.currentUser.value = u
                        msg = "Perfil actualizado"
                    } catch (e: Exception) { msg = e.message }
                }
            }
        }) { Text("Guardar") }
        msg?.let { Text(it) }
    }
}
