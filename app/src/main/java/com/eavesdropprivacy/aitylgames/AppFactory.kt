package com.eavesdropprivacy.aitylgames

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AppFactory(private val appRepository: AppRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)){
            return AppViewModel(appRepository) as T
        }
        throw IllegalArgumentException("Unknown class")
    }
}