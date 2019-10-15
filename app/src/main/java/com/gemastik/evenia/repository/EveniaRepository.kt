package com.pertamina.spbucare.repository

import android.net.Uri
import android.util.Log
import com.gemastik.evenia.model.User
import com.gemastik.evenia.model.Vacancy
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class EveniaRepository() {
    private val tag = EveniaRepository::class.java.simpleName

    private val firestore = Firebase.firestore
    private val usersRef = firestore.collection("users")
    private val vacanciesRef = firestore.collection("vacancies")

    suspend fun createUserData(uid: String, user: User) {
        usersRef.document(uid).set(user).await()
    }

    suspend fun updateUser(user: User) =
        usersRef.document(user.userId).set(user, SetOptions.merge()).await()

    suspend fun getUser(uid: String): User? {
        var user: User? = User()
        try {
            val docSnapshow = usersRef.document(uid).get().await()
            user = docSnapshow.toObject()
        } catch (e: FirebaseFirestoreException) {
            Log.e(tag, e.toString())
        }
        return user
    }

    suspend fun getUsers(type: String): List<User> {
        var users = listOf<User>()
        try {
            val querySnapshot =
                when (type) {
                    "seeker" -> usersRef.whereEqualTo("seeker", true).orderBy(
                        "name", Query.Direction.ASCENDING
                    ).get().await()
                    "provider" -> usersRef.whereEqualTo("provider", true).whereEqualTo(
                        "type", "sales_retail"
                    ).orderBy("name", Query.Direction.ASCENDING).get().await()
                    else -> usersRef.whereEqualTo("pertamina", false).whereEqualTo(
                        "type", "spbu"
                    ).orderBy("name", Query.Direction.ASCENDING).get().await()
                }
            users = querySnapshot.toObjects()
        } catch (e: FirebaseFirestoreException) {
            Log.e(tag, e.toString())
        }
        return users
    }

    suspend fun createVacancy(vacancy: Vacancy) {
        val vacancyId = vacanciesRef.document().id
        vacancy.vacancyId = vacancyId
        vacanciesRef.document(vacancyId).set(vacancy).await()
    }

    suspend fun getVacancies(): List<Vacancy> {
        var vacancies = listOf<Vacancy>()
        try {
            val querySnapshot =
                vacanciesRef.orderBy("createdOn", Query.Direction.DESCENDING).get().await()
            vacancies = querySnapshot.toObjects()
        } catch (e: FirebaseFirestoreException) {
            Log.e(tag, e.toString())
        }
        return vacancies
    }
}