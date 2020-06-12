package com.example.justdoit.data

import android.os.AsyncTask
import androidx.lifecycle.LiveData

class ToDoRoomRepository(private val toDoDao : ToDoDao):ToDoRepository  {

    private var allToDos : LiveData<List<ToDo>> = toDoDao.getAllTodos()
    override fun getAllToDos(): LiveData<List<ToDo>> {
        return allToDos
    }

    override fun insert(toDo: ToDo) {
        require(toDo.title != "") {"title must not be empty"}
        InsertAsynTask(toDoDao).execute(toDo)
    }

    override fun toggleToDo(id: String) {
        ToggleAsyncTask(todoDao).execute(id)
    }

    override fun getUpcomingToDosCount(): LiveData<Int> {
        return toDoDao.getDateCount(System.currentTimeMillis())
    }

    private class InsertAsynTask(val toDoDao: ToDoDao) : AsyncTask<ToDo, Unit, Unit>() {
        override fun doInBackground(vararg p0: ToDo?) {
            toDoDao.insert(p0[0]!!);
        }

    }
    private class ToggleAsyncTask(val todoDao: ToDoDao) : AsyncTask<String, Unit, Unit>() {
        override fun doInBackground(vararg ids: String?) {
            require(todoDao.toggleTodo(ids[0]!!) == 1) { "Todo not found" }
        }
    }

}