package com.example.justdoit

import android.content.Context
import android.content.res.Resources
import com.example.justdoit.list.determineCardColor
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ListUtilsTest(
    private val expected :Int,
    private val dueDate : Double?,
    private val done : Boolean,
    private val scenario : String
) {
    companion object {
        val now = System.currentTimeMillis().toDouble()
        val day = (1000 * 60 * 60 * 24).toDouble()
        //to let kotlin know to generate a static method for the junit inorder to connect to
        @JvmStatic
        @Parameterized.Parameters(name = "determineCardColor: {3}" )
        fun todos() = listOf(
             arrayOf(R.color.todoDone,null, true , "with done = true and no due date then return todoDone color"),
            arrayOf(R.color.todoNotDue,null, false , "with done = false and no due date then return todoNotDue color"),
            arrayOf(R.color.todoOverDue,now - day, false , "with done = false and missed due date then return todoOverDue color"),
            arrayOf(R.color.todoDueStrong,now + (7*day),false,"with done = false and due date after 7 days then return todoStrong color"),
            arrayOf(R.color.todoDueStrong,now + day,false,"with done = false and due date after 1 day then return todoStrong color"),
            arrayOf(R.color.todoDueMedium,now + (14*day),false,"with done = false and due date after 12 days then return todoMedium color"),
            arrayOf(R.color.todoDueLight,now + (15*day),false,"with done = false and due date after 15 days then return todolight color")
        )
    }
    @Test
    fun test_determineCardColor(){


        //act
        val actual = determineCardColor(dueDate, done)

        //assert
        assertEquals(expected,actual)
    }

}
