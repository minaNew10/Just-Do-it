package com.example.justdoit

import com.example.justdoit.list.determineCardColor
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ListUtilsTest {
    val now = System.currentTimeMillis()
    val day = 1000 * 60 * 60 * 24

    @Test
    fun `test_determineCardColor() with overdue Null then return color of overDue`(){
        //arrange
        val expected = R.color.todoOverDue
        val dueDate = now - day
        val done = false

        //act
        val actual = determineCardColor(dueDate, done)

        //assert
        assertEquals(expected,actual)
    }
    @Test
    fun `test_determineCardColor() with done equal true and dueDate equals Null then return color todoDone`(){
        //arrange
        val expected = R.color.todoDone
        val dueDate = null
        val done = true

        //act
        val actual = determineCardColor(dueDate, done)

        //assert
        assertEquals(expected,actual)
    }

    @Test
    fun `test_determineCardColor() with done equals false and due date equals null then return color of todoNotdue`(){
        val expected = R.color.todoNotDue
        val dueDate = null
        val done = false

        val actual = determineCardColor(dueDate, done)

        assertEquals(expected,actual)
    }
}
