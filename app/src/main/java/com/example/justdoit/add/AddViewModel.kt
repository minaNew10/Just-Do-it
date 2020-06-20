package com.example.justdoit.add

import androidx.lifecycle.ViewModel
import com.example.justdoit.data.ToDo
import com.example.justdoit.data.ToDoRepository
import java.util.*

class AddViewModel (
    private val toDoRepository: ToDoRepository
): ViewModel(){
    val todo = ToDo(
        title = "",
        dueDate = null,
        completed = false,
        created = 0
    )


    fun save(): String? {
        if (todo.title == "") return "Title is required"

        todo.created = System.currentTimeMillis()
        toDoRepository.insert(todo)
        return null
    }
}