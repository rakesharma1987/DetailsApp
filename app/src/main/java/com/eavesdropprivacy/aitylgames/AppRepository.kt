package com.eavesdropprivacy.aitylgames

import com.eavesdropprivacy.aitylgames.db.AppDao
import com.eavesdropprivacy.aitylgames.db.Details

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