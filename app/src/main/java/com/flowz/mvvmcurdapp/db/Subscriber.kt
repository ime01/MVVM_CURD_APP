package com.flowz.mvvmcurdapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subcriber_data_table")
data class Subscriber (

    @PrimaryKey(autoGenerate = true )
   @ColumnInfo(name = "subcriber_id")
    var id : Int,

   @ColumnInfo(name = "subcriber_name")
    var name : String,

   @ColumnInfo(name = "subcriber_email")
    var email : String)