package com.muziho.trade

import org.junit.Assert.*
import org.junit.Test
import kotlin.test.assertFailsWith

class OrderBookTest{
    @Test
    fun testAddOrder() {
        val orderBook = OrderBook()
        orderBook.addOrder(Order(1,100.00, OrderSide.O,2))
        assertEquals(1, orderBook.getOrders(OrderSide.O,).size)
    }
    @Test
    fun testAddOfferAndBidOrders() {
        val orderBook = OrderBook()
        orderBook.addOrder(Order(1,100.00, OrderSide.O,2))
        assertEquals(1, orderBook.getOrders(OrderSide.O,).size)
        assertEquals(0, orderBook.getOrders(OrderSide.B).size)

        orderBook.addOrder(Order(2, 99.00, OrderSide.B,3))
        assertEquals(1, orderBook.getOrders(OrderSide.O,).size)
        assertEquals(1, orderBook.getOrders(OrderSide.B).size)
    }

    @Test
    fun testCannotAddDuplicateOrderId() {
        val orderBook = OrderBook()
        val exception = assertFailsWith<Exception>{
            orderBook.addOrder(Order(1,100.00, OrderSide.O,2))
            //add same order again
            orderBook.addOrder(Order(1,100.00, OrderSide.B,2))
        }
        assertEquals("Duplicate ID ${1}",exception.message)
    }
}