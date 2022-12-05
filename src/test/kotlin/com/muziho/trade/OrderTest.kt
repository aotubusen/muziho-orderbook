package com.muziho.trade

import org.junit.Assert.*
import org.junit.Test

class OrderTest{

    @Test
    fun testCreateOfferOrder(){
        val order = Order(1, 100.00, 'O', 2)
        assertEquals(1, order.id)
        assertEquals(100.00, order.price, 0.00)
        assertEquals('O', order.side)
        assertEquals(2, order.size)


        val order2 = Order(2, 100.00, 'B', 2)
        assertEquals(2, order2.id)
        assertEquals(100.00, order2.price, 0.00)
        assertEquals('B', order2.side)
        assertEquals(2, order2.size)
    }


    @Test
    fun testCopyOrderAndUpdate(){
        val order = Order(1, 100.00, 'O', 2)
        val order2 = order.copy(size = 6)
        assertNotEquals(order, order2)
        assertEquals(order.created, order2.created)
        assertEquals(6, order2.size)

        val orderB = Order(2, 100.00, 'B', 2)
        val orderB2 = orderB.copy(size = 3)
        assertNotEquals(orderB, orderB2)
        assertEquals(orderB.created, orderB2.created)
        assertEquals(3, orderB2.size)
    }
}