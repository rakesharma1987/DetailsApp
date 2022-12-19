package com.example.detailsapp

import com.example.detailsapp.db.AppDao
import com.example.detailsapp.db.Details

class AppRepository(private val appDao: AppDao) {
    val allDetails = appDao.getAllData()

    suspend fun insertDetail(details: Details){
        appDao.insertDetails(details)
    }
}