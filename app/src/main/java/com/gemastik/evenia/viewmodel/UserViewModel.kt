package com.gemastik.evenia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gemastik.evenia.model.User
import com.google.firebase.auth.FirebaseAuth
import com.pertamina.spbucare.network.NetworkState
import com.pertamina.spbucare.repository.EveniaRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: EveniaRepository,
    private val auth: FirebaseAuth,
    userType: String
) : BaseViewModel() {

    private var supervisorJob = SupervisorJob()
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user
    val networkState = MutableLiveData<NetworkState>()

    init {
        when (userType) {
            "" -> getUser() //get current user login
        }
    }

    fun getUser(uid: String = auth.currentUser?.uid.toString()) {
        networkState.postValue(NetworkState.RUNNING)
        ioScope.launch(getJobErrorHandler() + supervisorJob) {
            _user.postValue(repository.getUser(uid))
            networkState.postValue(NetworkState.SUCCESS)
        }
    }

    fun changePassword(password: String) {
        auth.currentUser?.updatePassword(password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                }
            }
    }

    fun updateUser(user: User) {
        networkState.postValue(NetworkState.RUNNING)
        ioScope.launch(getJobErrorHandler() + supervisorJob) {
            repository.updateUser(user)
            networkState.postValue(NetworkState.SUCCESS)

        }
    }

    fun createUserData(uid: String, user: User) {
        networkState.postValue(NetworkState.RUNNING)
        ioScope.launch(getJobErrorHandler() + supervisorJob) {
            repository.createUserData(uid, user)
            networkState.postValue(NetworkState.SUCCESS)
        }
    }

    fun signout() {
        networkState.postValue(NetworkState.RUNNING)
        ioScope.launch(getJobErrorHandler() + supervisorJob) {
            val user = repository.getUser(auth.currentUser?.uid.toString())
            user?.token = ""
            user?.let { repository.updateUser(it) }
            auth.signOut()
            networkState.postValue(NetworkState.SUCCESS)
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, _ ->
        networkState.postValue(NetworkState.FAILED)
    }
}