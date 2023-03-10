package com.eavesdropprivacy.aitylgames.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
interface AppDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertDetails(details: Details)

    @Query("SELECT * FROM details WHERE isMore = 0")
    fun getAllData(): LiveData<List<Details>>

    @Query("SELECT * FROM details WHERE isMore = 1")
    fun getAllAdvancedData(): LiveData<List<Details>>

    @Update
    suspend fun updateDetails(details: Details)


}