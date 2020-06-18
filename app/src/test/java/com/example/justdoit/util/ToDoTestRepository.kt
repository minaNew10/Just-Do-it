package com.example.justdoit.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.justdoit.data.ToDo
import com.example.justdoit.data.ToDoRepository

class ToDoTestRepository (private val todos:ArrayList<ToDo>): ToDoRepository {
    override fun getAllToDos(): LiveData<List<ToDo>> {
        return MutableLiveData(todos)
    }

    override fun insert(ToDo: ToDo) {
        TODO("Not yet implemented")
    }

    override fun toggleToDo(id: Long) {
        TODO("Not yet implemented")
    }

    override fun getUpcomingToDosCount(): LiveData<Int> {
        val count =
            todos.count{
                !it.completed &&
                        it.dueDate != null &&
                        it.dueDate!! >= System.currentTimeMillis()
            }
        return MutableLiveData(count)
    }
}