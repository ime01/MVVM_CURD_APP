package com.flowz.mvvmcurdapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subscriber::class], version = 1)
abstract class SubcriberDatabase: RoomDatabase(){

    abstract val subcriberDAO: SubscriberDAO

    companion object{
        @Volatile
        private var INSTANCE : SubcriberDatabase? = null

        fun getInstance(context: Context):SubcriberDatabase{
            synchronized(this){
                var instance:  SubcriberDatabase? = INSTANCE

                if (instance==null){

                 instance =   Room.databaseBuilder(context.applicationContext, SubcriberDatabase::class.java, "subscriber_data_database").build()
                }
                return instance
            }

        }
    }

}