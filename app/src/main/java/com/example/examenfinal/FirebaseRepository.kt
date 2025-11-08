package com.example.rentavirtual.data

import android.net.Uri
import com.example.rentavirtual.data.models.Movie
import com.example.rentavirtual.data.models.Rental
import com.example.rentavirtual.data.models.User
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FirebaseRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
) {

    // Auth
    suspend fun register(email: String, password: String): FirebaseUser? {
        val res = auth.createUserWithEmailAndPassword(email, password).await()
        return res.user
    }

    suspend fun login(email: String, password: String): FirebaseUser? {
        val res = auth.signInWithEmailAndPassword(email, password).await()
        return res.user
    }

    fun getCurrentUser() = auth.currentUser

    // Users
    suspend fun saveUserProfile(user: User) {
        firestore.collection("users").document(user.uid).set(user).await()
    }

    suspend fun getUser(uid: String): User? {
        val snap = firestore.collection("users").document(uid).get().await()
        return snap.toObject(User::class.java)
    }

    // Storage: subir foto y devolver url
    suspend fun uploadProfilePhoto(uid: String, fileUri: Uri): String {
        val ref = storage.reference.child("users/$uid/profile.jpg")
        ref.putFile(fileUri).await()
        return ref.downloadUrl.await().toString()
    }

    // Movies
    fun listenMovies(callback: (List<Movie>) -> Unit) {
        firestore.collection("movies").addSnapshotListener { snap, _ ->
            val list = snap?.documents?.mapNotNull { d -> d.toObject(Movie::class.java)?.copy(id = d.id) } ?: emptyList()
            callback(list)
        }
    }

    // Rentals
    suspend fun createRental(rental: Rental) {
        firestore.collection("rentals").add(rental).await()
    }

    fun listenRentalsForUser(uid: String, isAdmin: Boolean, callback: (List<Rental>) -> Unit) {
        val query = if (isAdmin) firestore.collection("rentals") else firestore.collection("rentals").whereEqualTo("userId", uid)
        query.addSnapshotListener { snap, _ ->
            val list = snap?.documents?.mapNotNull { d -> d.toObject(Rental::class.java)?.copy(id = d.id) } ?: emptyList()
            callback(list)
        }
    }
}
