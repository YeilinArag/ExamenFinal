package com.example.rentavirtual.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rentavirtual.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AdminReportScreen(navController: NavController, vm: MainViewModel = MainViewModel()) {
    LaunchedEffect(Unit) { vm.loadInitialData() }
    val rentals by remember { vm.rentals }
    val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Informe de Rentas", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(12.dp))
        LazyColumn {
            items(rentals) { r ->
                Card(Modifier.fillMaxWidth().padding(6.dp)) {
                    Column(Modifier.padding(8.dp)) {
                        Text("Película: ${r.movieTitle}")
                        Text("Usuario: ${r.userName} (${r.userId})")
                        Text("Inicio: ${r.startDate?.toDate()?.let { fmt.format(it) } ?: "—"}")
                        Text("Fin: ${r.endDate?.toDate()?.let { fmt.format(it) } ?: "—"}")
                    }
                }
            }
        }
    }
}
