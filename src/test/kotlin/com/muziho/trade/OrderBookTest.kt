package com.muziho.trade

import org.junit.Assert.*
import org.junit.Test

class OrderBookTest{
    @Test
    fun testAddOrder() {
        val orderBook = OrderBook()
        orderBook.addOrder(Order(1,100.00, OrderSide.O,2))
        assertEquals(1, orderBook.getOrders(OrderSide.O,).size)
    }
}