package com.example.justdoit

import androidx.lifecycle.MutableLiveData
import com.example.justdoit.data.ToDo
import com.example.justdoit.data.ToDoRepository
import com.example.justdoit.list.ListViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.lang.IllegalArgumentException

class ListViewModelTest {
    //unit testing prioritize readability over reuse
    // so it is okay to use the same variable across multiple classes
    val now = System.currentTimeMillis()
    val day = 1000 * 60 * 60 * 24
    //this tells junit that we are not expecting exceptions on tests
    //what gives us the ability to capture them on a per test basis
    //since kotlin makes all properties private by default we will need to add agetter
    @get:Rule
    val exceptionRule: ExpectedException = ExpectedException.none()

    @Test
    fun `getAllTodos() with an empty todos then return an empty arraylist()`(){
        val expectedCount = 0
        val repository : ToDoRepository = mock()
        whenever(repository.getAllToDos())
            .thenReturn(MutableLiveData(arrayListOf()))
    val model = ListViewModel(repository)

        val todos = model.allTodos.value
        assertNotNull(todos)
        assertEquals(expectedCount,todos!!.size)

    }

    @Test
    fun `getAllTodos() with an single todo then return an arraylist contains single todo()`(){
        val expectedCount = 1
        val repository : ToDoRepository = mock()
        whenever(repository.getAllToDos())
            .thenReturn(MutableLiveData(arrayListOf(
                ToDo(5,"ToDo 5",now + day,false,now)
            )))
        val model = ListViewModel(repository)

        val todos = model.allTodos.value
        assertNotNull(todos)
        assertEquals(expectedCount,todos!!.size)

    }
    @Test
    fun `getAllTodos() with an multiple todos then return an arraylist contains the same number of todos()`(){
        val expectedCount = 3
        //add this argument when you face troubles but not by default
        val repository : ToDoRepository = mock(verboseLogging = true)
        whenever(repository.getAllToDos())
            .thenReturn(MutableLiveData(arrayListOf(
                ToDo(5,"ToDo 5",now + day,false,now),
                ToDo(6,"ToDo 6",now - day,false,now),
                ToDo(7,"ToDo 7",now + day,false,now)
            )))
        val model = ListViewModel(repository)

        val todos = model.allTodos.value
        assertNotNull(todos)
        assertEquals(expectedCount,todos!!.size)

    }

    @Test
    fun `getUpcomingTodosCount with 0 upcoming todos then return 0`(){
        val repository : ToDoRepository = mock()
        val expectedCount = 0
        whenever(repository.getUpcomingToDosCount())
            .thenReturn(MutableLiveData(expectedCount))
        val model = ListViewModel(repository)

        val todosCount = model.upcomingTodosCount.value
        assertNotNull(todosCount)
        assertEquals(expectedCount,todosCount)
    }
     @Test
    fun `getUpcomingTodosCount with 1 upcoming todos then return 1`(){
        val repository : ToDoRepository = mock()
        val expectedCount = 1
        whenever(repository.getUpcomingToDosCount())
            .thenReturn(MutableLiveData(expectedCount))
        val model = ListViewModel(repository)

        val todosCount = model.upcomingTodosCount.value
        assertNotNull(todosCount)
        assertEquals(expectedCount,todosCount)
    }
     @Test
    fun `getUpcomingTodosCount with 5 upcoming todos then return 5`(){
        val repository : ToDoRepository = mock()
        val expectedCount = 5
        whenever(repository.getUpcomingToDosCount())
            .thenReturn(MutableLiveData(expectedCount))
        val model = ListViewModel(repository)

        val todosCount = model.upcomingTodosCount.value
        assertNotNull(todosCount)
        assertEquals(expectedCount,todosCount)
    }


    @Test
    fun `test_toggleTodo with a present todo then verify behavior of toggletodo method`(){
        val repository : ToDoRepository = mock()
        val id = -1L
        val model = ListViewModel(repository)

        model.toggleTodo(id)

        verify(repository)
            .toggleToDo(id)
    }
    @Test
    fun `test_toggleTodo with no todo found then throw illegalArgumend exception` (){
        val repository : ToDoRepository = mock()
        val exceptionMessage = "Todo not found"
        val id = -1L
        whenever(repository.toggleToDo(id))
            .thenThrow(IllegalArgumentException(exceptionMessage))
        val model = ListViewModel(repository)
        exceptionRule.expect(IllegalArgumentException::class.java)
        exceptionRule.expectMessage(exceptionMessage)
        model.toggleTodo(id)

        verify(repository)
            .toggleToDo(id)
    }
}