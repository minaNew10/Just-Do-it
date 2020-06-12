package com.example.justdoit.data

import androidx.lifecycle.LiveData
//this level of abstraction allow us to swap room with diff data sources
interface ToDoRepository {
    fun getAllToDos(): LiveData<List<ToDo>>

    fun insert(ToDo: ToDo)

    fun toggleToDo(id: String)

    fun getUpcomingToDosCount(): LiveData<Int>
}