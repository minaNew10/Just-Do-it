package com.example.justdoit

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.justdoit.data.ToDo
import com.example.justdoit.data.ToDoDao
import com.example.justdoit.data.ToDoRoomDatabase
import org.junit.Assert.assertEquals
import org.junit.After

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ToDoDatabaseTest {

    private lateinit var toDoDao : ToDoDao
    private lateinit var db : ToDoRoomDatabase

    @Before
    fun createDb(){
        val context =  InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, ToDoRoomDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        toDoDao = db.toDoDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetTask(){
        val task = ToDo()
        //the id is assigned by Auto generation
        var id = toDoDao.insert(task)
        task.id = id
        val returnedTask = toDoDao.getLastInsertedTodo()
        assertEquals(returnedTask, task)
    }

}