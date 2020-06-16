package com.flowz.mvvmcurdapp.db

class SubscriberRepository(private val dao: SubscriberDAO) {

    val subscribers = dao.getAllSubcribers()

    suspend fun insert(subscriber: Subscriber):Long{
       return dao.insertSubcriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber):Int{
       return dao.updateSubcriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber):Int{
       return dao.deleteSubcriber(subscriber)
    }

    suspend fun deleteAll():Int{
       return dao.deleteAll()
    }




}