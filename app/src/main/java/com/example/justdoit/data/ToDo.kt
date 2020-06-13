package com.example.justdoit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDo (

    @PrimaryKey(autoGenerate = true)
    var id:Long = 0L,
    var title:String = "",
    var dueDate : Long? = null,
    var completed :Boolean = false,
    var created: Long = System.currentTimeMillis()

)