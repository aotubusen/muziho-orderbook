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
}