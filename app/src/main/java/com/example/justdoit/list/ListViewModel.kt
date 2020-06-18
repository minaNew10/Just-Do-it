package com.example.justdoit.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.justdoit.data.ToDo
import com.example.justdoit.data.ToDoRepository

class ListViewModel(
    private val todoRepository : ToDoRepository
) :ViewModel(){
    val allTodos: LiveData<List<ToDo>> = todoRepository.getAllToDos()
    val upcomingTodosCount: LiveData<Int> = todoRepository.getUpcomingToDosCount()

    fun toggleTodo(id: Long) {
        todoRepository.toggleToDo(id)
    }

}