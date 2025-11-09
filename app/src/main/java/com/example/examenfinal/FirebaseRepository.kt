package com.example.examenfinal

import android.net.Uri
import com.example.examenfinal.User.User
import com.example.examenfinal.Movie.Movie
import com.example.examenfinal.Rental.Rental
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FirebaseRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
) {

    // AUTH
    suspend fun registerUser(email: String, password: String): FirebaseUser? {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        return result.user
    }

    suspend fun loginUser(email: String, password: String): FirebaseUser? {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user
    }

    fun logout() {
        auth.signOut()
    }

    // USERS
    suspend fun saveUserProfile(user: User) {
        val uid = user.id.ifBlank {
            auth.currentUser?.uid ?: throw IllegalStateException("No UID disponible")
        }

        firestore.collection("users")
            .document(uid)
            .set(user.copy(id = uid))
            .await()
    }

    suspend fun getCurrentUserProfile(): User? {
        val uid = auth.currentUser?.uid ?: return null
        val snapshot = firestore.collection("users")
            .document(uid)
            .get()
            .await()
        return snapshot.toObject(User::class.java)
    }

    // MOVIES
    suspend fun getAllMovies(): List<Movie> {
        val snapshot = firestore.collection("movies")
            .get()
            .await()
        return snapshot.toObjects(Movie::class.java)
    }

    // RENTALS
    suspend fun createRental(rental: Rental) {
        firestore.collection("rentals")
            .add(rental)
            .await()
    }

    suspend fun getAllRentals(): List<Rental> {
        val snapshot = firestore.collection("rentals")
            .get()
            .await()
        return snapshot.toObjects(Rental::class.java)
    }

    // opcional: subir foto
    suspend fun uploadProfileImage(userId: String, imageUri: Uri): String {
        val ref = storage.reference
            .child("profileImages")
            .child("$userId.jpg")

        ref.putFile(imageUri).await()
        return ref.downloadUrl.await().toString()
    }
}
