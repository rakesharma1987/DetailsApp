package com.eavesdropprivacy.aitylgames

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eavesdropprivacy.aitylgames.db.Details
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