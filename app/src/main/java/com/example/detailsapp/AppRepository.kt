package com.example.detailsapp

import com.example.detailsapp.db.AppDao
import com.example.detailsapp.db.Details

class AppRepository(private val appDao: AppDao) {
    val allDetails = appDao.getAllData()
    val allAdvancedDetails = appDao.getAllAdvancedData()

    suspend fun insertDetail(details: Details){
        appDao.insertDetails(details)
    }

    suspend fun updateDetails(details: Details){
        appDao.updateDetails(details)
    }
}