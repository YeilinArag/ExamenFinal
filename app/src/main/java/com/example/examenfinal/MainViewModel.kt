package com.example.examenfinal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.examenfinal.User.User
import com.example.examenfinal.Movie.Movie
import com.example.examenfinal.Rental.Rental
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: FirebaseRepository = FirebaseRepository()
) : ViewModel() {

    // UI state
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser

    private val _movies = mutableStateOf<List<Movie>>(emptyList())
    val movies: State<List<Movie>> = _movies

    private val _rentals = mutableStateOf<List<Rental>>(emptyList())
    val rentals: State<List<Rental>> = _rentals

    // ---------- Auth ----------
    fun login(email: String, password: String, onSuccess: () -> Unit = {}) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                repo.loginUser(email, password)
                _currentUser.value = repo.getCurrentUserProfile()
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun register(
        email: String,
        password: String,
        name: String,
        age: Int,
        creditCard: String,
        role: String,
        onSuccess: () -> Unit = {}
    ) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val fbUser = repo.registerUser(email, password)
                val uid = fbUser?.uid ?: return@launch

                val user = User(
                    id = uid,
                    name = name,
                    email = email,
                    age = age,
                    creditCard = creditCard,
                    role = role,
                    photoUrl = null
                )

                repo.saveUserProfile(user)
                _currentUser.value = user
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        repo.logout()
        _currentUser.value = null
    }

    // ---------- Movies ----------
    fun loadMovies() {
        viewModelScope.launch {
            try {
                _movies.value = repo.getAllMovies()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    // ---------- Rentals ----------
    fun createRental(rental: Rental, onSuccess: () -> Unit = {}) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                repo.createRental(rental)
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadRentals() {
        viewModelScope.launch {
            try {
                _rentals.value = repo.getAllRentals()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
