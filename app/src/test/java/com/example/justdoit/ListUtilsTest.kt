package com.example.justdoit

import com.example.justdoit.list.determineCardColor
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ListUtilsTest(
    private val expected :Int,
    private val dueDate : Long?,
    private val done : Boolean,
    private val scenario : String
) {
    companion object {
        val now = System.currentTimeMillis()
        val day = 1000 * 60 * 60 * 24
        //to let kotlin know to generate a static method for the junit inorder to connect to
        @JvmStatic
        @Parameterized.Parameters(name = "determineCardColor: {3}" )
        fun todos() = listOf(
            arrayOf(R.color.todoDone,null, true , "with done = true and no due date then return todoDone color"),
            arrayOf(R.color.todoNotDue,null, false , "with done = false and no due date then return todoNotDue color"),
            arrayOf(R.color.todoOverDue,now - day, false , "with done = false and no due date then return todoOverDue color")

        )
    }
    @Test
    fun `test_determineCardColor() with overdue Null then return color of overDue`(){


        //act
        val actual = determineCardColor(dueDate, done)

        //assert
        assertEquals(expected,actual)
    }

}
