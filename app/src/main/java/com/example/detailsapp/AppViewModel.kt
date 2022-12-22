package com.example.detailsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detailsapp.db.Details
import kotlinx.coroutines.launch

class AppViewModel(private val appRepository: AppRepository): ViewModel() {
    fun insertDetails(details: Details){
        viewModelScope.launch {
            appRepository.insertDetail(details)
        }
    }

    fun updateDetails(details: Details){
        viewModelScope.launch {
            appRepository.updateDetails(details)
        }
    }

    val getAllDetails = appRepository.allDetails

    val getAllAdvancedDetails = appRepository.allAdvancedDetails


}