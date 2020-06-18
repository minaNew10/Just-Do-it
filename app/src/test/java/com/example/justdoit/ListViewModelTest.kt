package com.example.justdoit

import com.example.justdoit.data.ToDo
import com.example.justdoit.data.ToDoRepository
import com.example.justdoit.list.ListViewModel
import com.example.justdoit.util.ToDoTestRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ListViewModelTest {
    lateinit var repository: ToDoRepository

    @Before
    fun setup(){
        //unit testing prioritize readability over reuse
        // so it is okay to use the same variable across multiple classes
        val now = System.currentTimeMillis()
        val day = 1000 * 60 * 60 * 24

        val todos = ArrayList<ToDo>()
        var toDo = ToDo(1,"ToDo 1",null,false,now)
        todos.add(toDo)
        toDo = ToDo(2,"ToDo 2",now + day,false,now)
        todos.add(toDo)
        toDo = ToDo(3,"ToDo 3",now + day,true,now)
        todos.add(toDo)
        toDo = ToDo(4,"ToDo 4",now + day,false,now)
        todos.add(toDo)
        toDo = ToDo(5,"ToDo 5",now + day,false,now)
        todos.add(toDo)

        repository = ToDoTestRepository(todos)
    }

    @Test
    fun `getAllTodos() then return not null arrayList with size 5()`(){
        val expectedCount = 5
        val model = ListViewModel(repository)

        val todos = model.allTodos.value
        assertNotNull(todos)
        assertEquals(expectedCount,todos!!.size)

    }

    @Test
    fun `getUpcomingTodosCount with 3 upcoming todos then return 3`(){
        val expectedCount = 3
        val model = ListViewModel(repository)

        val todosCount = model.upcomingTodosCount.value
        assertNotNull(todosCount)
        assertEquals(expectedCount,todosCount)
    }
}