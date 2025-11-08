package com.example.rentavirtual.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.rentavirtual.data.models.Movie
import com.example.rentavirtual.viewmodel.MainViewModel

@Composable
fun MovieListScreen(navController: NavController, vm: MainViewModel = MainViewModel()) {
    val movies by remember { vm.movies }
    // carga inicial si no lo hizo
    LaunchedEffect(Unit) { vm.loadInitialData() }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(movies) { movie ->
            Card(modifier = Modifier.fillMaxWidth().padding(6.dp)) {
                Row(Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(Modifier.weight(1f)) {
                        Text(movie.title, style = MaterialTheme.typography.h6)
                        Text(movie.description, maxLines = 2)
                        Text("Precio: Q${movie.price}")
                    }
                    if (movie.posterUrl.isNotEmpty()) {
                        Image(painter = rememberAsyncImagePainter(movie.posterUrl), contentDescription = movie.title, modifier = Modifier.size(80.dp))
                    }
                    Column {
                        Button(onClick = {
                            vm.rentMovie(movie) { ok, err ->
                                // Show simple alert
                            }
                        }) { Text("Rentar") }
                    }
                }
            }
        }
    }
}
