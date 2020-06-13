package com.example.justdoit.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo ORDER BY created DESC")
    fun getAllTodos(): LiveData<List<ToDo>>

    @Query("SELECT count(*) FROM todo WHERE completed = 0 AND dueDate >= :date")
    fun getDateCount(date: Long): LiveData<Int>

    @Query("SELECT * FROM ToDo WHERE id = :id")
    fun getTodo(id: Long): ToDo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: ToDo) : Long

    @Query("UPDATE todo set completed = ~completed WHERE id = :id")
    fun toggleTodo(id: Long): Int

    @Query("SELECT * FROM todo ORDER BY created DESC LIMIT 1")
    fun getLastInsertedTodo() :ToDo
}