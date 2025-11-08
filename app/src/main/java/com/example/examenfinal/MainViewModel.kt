package com.example.rentavirtual.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavirtual.data.FirebaseRepository
import com.example.rentavirtual.data.models.Movie
import com.example.rentavirtual.data.models.Rental
import com.example.rentavirtual.data.models.User
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(private val repo: FirebaseRepository = FirebaseRepository()) : ViewModel() {

    val currentUser = mutableStateOf<User?>(null)
    val movies = mutableStateOf<List<Movie>>(emptyList())
    val rentals = mutableStateOf<List<Rental>>(emptyList())
    val isAdmin = mutableStateOf(false)

    fun loadInitialData() {
        // cargar usuario auth -> doc user -> movies -> rentals
        val authUser = repo.getCurrentUser() ?: return
        viewModelScope.launch {
            val u = repo.getUser(authUser.uid)
            currentUser.value = u
            isAdmin.value = u?.role == "admin"
            // escuchar movies
            repo.listenMovies { movies.value = it }
            // escuchar rentals
            repo.listenRentalsForUser(authUser.uid, isAdmin.value) { rentals.value = it }
        }
    }

    fun registerAndSaveProfile(email: String, password: String, fullName: String, age: Int, cardLast4: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val fu = repo.register(email, password) ?: throw Exception("Registro falló")
                val user = User(uid = fu.uid, fullName = fullName, age = age, cardNumberLast4 = cardLast4, role = "user")
                repo.saveUserProfile(user)
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

    fun uploadPhotoAndSave(uid: String, uri: Uri, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val url = repo.uploadProfilePhoto(uid, uri)
                val u = currentUser.value?.copy(photoUrl = url) ?: return@launch
                repo.saveUserProfile(u)
                currentUser.value = u
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

    fun rentMovie(movie: Movie, days: Int = 3, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val cu = currentUser.value ?: throw Exception("Usuario no cargado")
                val start = Timestamp.now()
                val end = Timestamp(Date(System.currentTimeMillis() + days * 24L * 3600L * 1000L))
                val rental = Rental(
                    movieId = movie.id,
                    movieTitle = movie.title,
                    userId = cu.uid,
                    userName = cu.fullName.ifBlank { cu.uid },
                    startDate = start,
                    endDate = end
                )
                repo.createRental(rental)
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }
}
