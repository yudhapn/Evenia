package com.pertamina.spbucare.di

import com.gemastik.evenia.viewmodel.RegisterViewModel
import com.gemastik.evenia.viewmodel.UserViewModel
import com.gemastik.evenia.viewmodel.VacancyViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.pertamina.spbucare.repository.EveniaRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

//val networkModule = module {
//    factory { OkHttpClient.Builder().build() }
//    single {
//        Retrofit.Builder()
//            .client(get())
//            .baseUrl("https://spbu-care.firebaseapp.com/api/v1/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            .build()
//    }
//
//    factory { get<Retrofit>().create(SpbuCareApiService::class.java) }
//}

val repositoryModule = module {
    factory { EveniaRepository() }
}

val viewModelModule = module {
    viewModel { (userType: String) -> RegisterViewModel(get(), get()) }
    viewModel { VacancyViewModel(get()) }
    viewModel { (type: String) -> UserViewModel(get(), get(), type) }
//    viewModel { (quizId : String) -> QuizViewModel(get(), get(), quizId) }
//    viewModel { NotificationViewModel(get(), get()) }
//    viewModel { EducationViewModel(get(), get()) }
//    viewModel { InformationViewModel(get(), get()) }
//    viewModel { (category : String) -> HistoryViewModel(get(), get(), category) }
}

val firebaseModule = module {
    factory { FirebaseAuth.getInstance() }
    factory { FirebaseStorage.getInstance() }
}


val appComponent = listOf(viewModelModule, repositoryModule, firebaseModule)