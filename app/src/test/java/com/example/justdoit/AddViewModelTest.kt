package com.example.justdoit

import com.example.justdoit.add.AddViewModel
import com.example.justdoit.data.ToDoRepository
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit

class AddViewModelTest{
    @get:Rule
    val collector = MockitoJUnit.collector()
    @Test
    fun `testInsertTodo with a title and without date then the function works properly and save the same todo` (){
        val repo : ToDoRepository = mock()
        val model = AddViewModel(repo)
        val actualTitle = "test todo"
        model.todo.title = actualTitle

        val actual = model.save()

        assertNull(actual)
        verify(repo).insert(any())
        //this verification is intentionally error prone
        verify(repo).insert(
            argThat { created == System.currentTimeMillis() }
        )
        verify(repo).insert(
            argThat {
            title == actualTitle && dueDate == null
        })
    }
    @Test
    fun `testInsertTodo without a title then the function returns "title is required" string` (){
        val repo : ToDoRepository = mock()
        val model = AddViewModel(repo)

        val expected = "Title is required"

        val actual = model.save()

        assertEquals(expected,actual)

        verify(repo, never()).insert(any())
    }
    @Test
    fun `testInsertTodo with a Date then the function works properly` (){
        val repo : ToDoRepository = mock()
        val model = AddViewModel(repo)
        val actualTitle = "test todo"
        model.todo.title = actualTitle
        val actualDate = System.currentTimeMillis()
        model.todo.dueDate = actualDate
        val actual = model.save()

        assertNull(actual)
        verify(repo).insert(
            argThat {
                title == actualTitle && dueDate == actualDate
            }
        )
    }



}