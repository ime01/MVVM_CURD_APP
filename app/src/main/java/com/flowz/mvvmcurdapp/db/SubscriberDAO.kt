package com.flowz.mvvmcurdapp.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface SubscriberDAO {

    @Insert
    suspend fun insertSubcriber(subcriber: Subscriber): Long

    @Update
    suspend fun updateSubcriber(subcriber: Subscriber):Int

    @Delete
    suspend fun deleteSubcriber(subcriber: Subscriber):Int

    @Query("DELETE FROM  subcriber_data_table")
    suspend fun deleteAll():Int

    @Query("SELECT * FROM  subcriber_data_table")
    fun getAllSubcribers():LiveData<List<Subscriber>>


}