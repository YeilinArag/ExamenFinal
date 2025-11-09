package com.example.examenfinal.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.examenfinal.MainViewModel

@Composable
fun ProfileScreen(
    mainViewModel: MainViewModel = viewModel()
) {
    val user by mainViewModel.currentUser

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (user == null) {
            Text("No hay usuario autenticado")
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                if (!user!!.photoUrl.isNullOrEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(user!!.photoUrl),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.size(96.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Text(text = user!!.name, style = MaterialTheme.typography.h6)
                Text(text = user!!.email, style = MaterialTheme.typography.body2)
                Text(text = "Rol: ${user!!.role}", style = MaterialTheme.typography.body2)
            }
        }
    }
}
