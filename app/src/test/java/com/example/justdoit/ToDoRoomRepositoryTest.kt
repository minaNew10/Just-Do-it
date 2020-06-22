package com.example.justdoit

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.justdoit.data.ToDo
import com.example.justdoit.data.ToDoDao
import com.example.justdoit.data.ToDoRoomDatabase
import com.example.justdoit.data.ToDoRoomRepository
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import java.lang.RuntimeException

@RunWith(AndroidJUnit4::class)
class ToDoRoomRepositoryTest{
    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @get:Rule
    val exceptionRule = ExpectedException.none()
    //unit testing prioritize readability over reuse
    // so it is okay to use the same variable across multiple classes
    val now = System.currentTimeMillis()
    val day = 1000 * 60 * 60 * 24

    private lateinit var db: ToDoRoomDatabase

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context,ToDoRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun tearDown(){
        db.close()
    }
    @Test
    fun `test database with no todos then return 0`() {
        val dao = spy(db.toDoDao)
        val repo = ToDoRoomRepository(dao)
        val expected = 0

        val actual = repo.getUpcomingToDosCount().test().value()

        assertEquals(expected,actual)
        verify(dao).getDateCount(any())

    }
    @Test
    fun `test getUpComingTodos with 3 todos then return 1`() {
        val dao = spy(db.toDoDao)

        db.toDoDao.insert(ToDo(5,"ToDo 5",now - day,false,now));
        db.toDoDao.insert( ToDo(6,"ToDo 6",now + day,false,now));
        db.toDoDao.insert(ToDo(7,"ToDo 7",now - day,false,now));
        val repo = ToDoRoomRepository(dao)
        val expected = 1

        val actual = repo.getUpcomingToDosCount().test().value()

        assertEquals(expected,actual)
        verify(dao).getDateCount(any())

    }
    @Test
    fun `test_toggleTodo with a good id then return 1 for the changed row`(){
        val dao = mock<ToDoDao>{
            on{it.toggleTodo(any())}
                .doAnswer{
                    val id = it.arguments[0]
                    require(id != -1){"bad id"}
                    1
                }
        }
        val repo = ToDoRoomRepository(dao)
        val id = 1L
        repo.toggleToDo(id)
        verify(dao).toggleTodo(id)
    }
    @Test
    fun `test toggleTodo with a bad id then expect a run time exception`(){
        val dao = mock<ToDoDao>{
            on{it.toggleTodo(any())}
                .doAnswer{
                    val id = it.arguments[0]
                    require(id != -1L){"bad id"}
                    1
                }
        }
        val repo = ToDoRoomRepository(dao)
        val id = -1L
        exceptionRule.expect(RuntimeException::class.java)
        repo.toggleToDo(id)
        verify(dao).toggleTodo(id)
    }
    @Test
    fun `test insert todo with a todo then verify that the same todo inserted`(){
        val dao = mock<ToDoDao>()
        val repo = ToDoRoomRepository(dao)
        val expected = ToDo(5,"ToDo 5",now - day,false,now)

        repo.insert(expected)

        argumentCaptor<ToDo>()
            .apply {
                verify(dao).insert(capture())
                assertEquals(expected,firstValue)
            }
    }
    @Test
    fun `test getAllTodos with multiple todos then return num of todos`() {
        val dao = spy(db.toDoDao)
        val testTodo = ToDo(5,"ToDo 5",now - day,false,now);
        db.toDoDao.insert(testTodo);
        db.toDoDao.insert( ToDo(6,"ToDo 6",now + day,false,now));
        db.toDoDao.insert(ToDo(7,"ToDo 7",now - day,false,now));
        val repo = ToDoRoomRepository(dao)
        val expected = 3

        val actual = repo.getAllToDos().test().value()

        assertEquals(expected,actual.size)
        verify(dao).getAllTodos()
        assertTrue(actual.contains(testTodo))

    }
}