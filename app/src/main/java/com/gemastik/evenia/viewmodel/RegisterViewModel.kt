package com.gemastik.evenia.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gemastik.evenia.model.User
import com.gemastik.evenia.model.Vacancy
import com.google.firebase.storage.FirebaseStorage
import com.pertamina.spbucare.network.NetworkState
import com.pertamina.spbucare.repository.EveniaRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel(
    private val repo: EveniaRepository,
    private val storage: FirebaseStorage
) : BaseViewModel() {

    private var supervisorJob = SupervisorJob()
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState
    private val _vacancies = MutableLiveData<List<Vacancy>>()
    val vacancies: LiveData<List<Vacancy>>
        get() = _vacancies


    init {
        getVacancies()
    }

    fun createVacancy(vacancy: Vacancy, bgImageName: String, bgImageUri: Uri?, identImageName: String, identImageUri: Uri?, ident2ImageName: String, ident2ImageUri: Uri?) {
        _networkState.postValue(NetworkState.RUNNING)
        if (bgImageUri != null && identImageUri != null && ident2ImageUri != null) {
            val storageRef = storage.getReference("vacancies")
            val fileReference = storageRef.child(bgImageName)
            val uploadTask = bgImageUri.let { fileReference.putFile(it) }

            val fileReference2 = storageRef.child(identImageName)
            val uploadTask2 = identImageUri.let { fileReference2.putFile(it) }

            val fileReference3 = storageRef.child(ident2ImageName)
            val uploadTask3 = ident2ImageUri.let { fileReference3.putFile(it) }

            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                fileReference.downloadUrl
            }.addOnCompleteListener { task ->
                vacancy.backgroundImage = task.result.toString()
                uploadTask2.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    fileReference2.downloadUrl
                }.addOnCompleteListener { task ->
                    vacancy.identityImage = task.result.toString()
                    uploadTask3.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        fileReference3.downloadUrl
                    }.addOnCompleteListener { task ->
                        vacancy.identityPersonImage = task.result.toString()

                        ioScope.launch(getJobErrorHandler() + supervisorJob) {
                            repo.createVacancy(vacancy)
                            _networkState.postValue(NetworkState.SUCCESS)
                        }
                    }
                }
            }
        } else {
            ioScope.launch(getJobErrorHandler() + supervisorJob) {
                repo.createVacancy(vacancy)
                _networkState.postValue(NetworkState.SUCCESS)
            }
        }
    }

    fun getVacancies() {
        _networkState.postValue(NetworkState.RUNNING)
        var vacancyList: List<Vacancy>
        ioScope.launch(getJobErrorHandler() + supervisorJob) {
            vacancyList = repo.getVacancies()
            _vacancies.postValue(vacancyList )
            _networkState.postValue(NetworkState.SUCCESS)
        }
    }


    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, _ ->
        _networkState.postValue(NetworkState.FAILED)
    }
}