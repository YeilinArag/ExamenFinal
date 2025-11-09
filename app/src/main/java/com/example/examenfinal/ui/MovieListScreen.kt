package com.example.examenfinal.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.examenfinal.MainViewModel
import com.example.examenfinal.Rental.Rental

@Composable
fun MovieListScreen(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val movies by mainViewModel.movies
    val currentUser by mainViewModel.currentUser

    LaunchedEffect(Unit) {
        mainViewModel.loadMovies()
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Películas", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(8.dp))

        if (currentUser == null) {
            Card(Modifier.fillMaxWidth()) {
                Text(
                    "Inicia sesión para rentar",
                    Modifier.padding(16.dp)
                )
            }
        } else {
            LazyColumn {
                items(movies) { movie ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(Modifier.padding(8.dp)) {
                            Text(movie.title)
                            Spacer(Modifier.height(4.dp))
                            Button(onClick = {
                                val now = System.currentTimeMillis()
                                val oneDay = 24L * 60 * 60 * 1000

                                val rental = Rental(
                                    userId = currentUser!!.id,
                                    movieId = movie.id,
                                    startDate = now,
                                    endDate = now + oneDay
                                )

                                mainViewModel.createRental(rental)
                            }) {
                                Text("Rentar")
                            }
                        }
                    }
                }
            }
        }
    }
}


