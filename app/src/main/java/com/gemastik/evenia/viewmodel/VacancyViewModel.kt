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

class VacancyViewModel(private val repo: EveniaRepository) : BaseViewModel() {

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