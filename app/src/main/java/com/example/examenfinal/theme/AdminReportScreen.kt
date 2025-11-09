package com.example.examenfinal.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.examenfinal.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AdminReportScreen(mainViewModel: MainViewModel) {
    val rentals by mainViewModel.rentals
    val error by mainViewModel.error
    val fmt = remember { SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()) }

    LaunchedEffect(Unit) {
        mainViewModel.loadRentals()
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Informe de rentas", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(8.dp))

        error?.let {
            Text(it, color = Color.Red)
            Spacer(Modifier.height(8.dp))
        }

        if (rentals.isEmpty()) {
            Text("No hay rentas registradas.")
        } else {
            LazyColumn {
                items(rentals) { r ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(Modifier.padding(8.dp)) {
                            Text("UserId: ${r.userId}")
                            Text("MovieId: ${r.movieId}")
                            Text("Inicio: ${fmt.format(Date(r.startDate))}")
                            Text("Fin: ${fmt.format(Date(r.endDate))}")
                        }
                    }
                }
            }
        }
    }
}
