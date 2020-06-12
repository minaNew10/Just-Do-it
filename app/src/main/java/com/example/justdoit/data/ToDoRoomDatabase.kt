package com.example.justdoit.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [ToDo::class], version = 1, exportSchema = false)
abstract class ToDoRoomDatabase :RoomDatabase(){
    /**
     * Connects the database to the DAO.
     */
    abstract val toDoDao : ToDoDao
    /**
     * Define a companion object, this allows us to add functions on the SleepDatabase class.
     *
     * For example, clients can call `SleepDatabase.getInstance(context)` to instantiate
     * a new SleepDatabase.
     */
    companion object{
        //volatile helps us make sure that the value of instance is always uptodate
        //and the same to all excution threads the value of the volatile variable will
        //neve be cashed and all writes and reads will be done to and from the main memory
        //it means that changes made by one thread are visible to all other threads immediately
        @Volatile
        private var toDoRoomInstance: ToDoRoomDatabase? = null
        /**
         * Helper function to get the database.
         *
         * If a database has already been retrieved, the previous database will be returned.
         * Otherwise, create a new database.
         *
         * This function is threadsafe, and callers should cache the result for multiple database
         * calls to avoid overhead.
         *
         * This is an example of a simple Singleton pattern that takes another Singleton as an
         * argument in Kotlin.
         *
         * To learn more about Singleton read the wikipedia article:
         * https://en.wikipedia.org/wiki/Singleton_pattern
         *
         * @param context The application context Singleton, used to get access to the filesystem.
         */
        fun getInstance(context: Context) : ToDoRoomDatabase {

                // Multiple threads can ask for the database at the same time, ensure we only initialize
                // it once by using synchronized. Only one thread may enter a synchronized block at a
                // time.
                synchronized(ToDoRoomDatabase::class.java){
                    // we assign to a local variable to make use of kotlin smart cast
                    //which is available only for local variables not for class ones
                    var instance = toDoRoomInstance
                    if(instance == null) {
                        instance = Room.databaseBuilder<ToDoRoomDatabase>(
                            context.applicationContext,
                            ToDoRoomDatabase::class.java,
                            "todo_database"
                        )
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // You can learn more about
                            // migration with Room in this blog post:
                            // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929

                            .fallbackToDestructiveMigration()
                            .build()
                        toDoRoomInstance = instance
                    }
                    // Return instance; smart cast to be non-null.
                    return instance;
                }


        }
    }
}