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

    @Test
    fun testRemoveOrder() {
        val orderBook = OrderBook()
        orderBook.addOrder(Order(1,100.00, OrderSide.O,2))
        orderBook.addOrder(Order(2,100.00, OrderSide.O,3))
        orderBook.addOrder(Order(5, 99.00, OrderSide.B,3))
        assertEquals(2,orderBook.getOrders(OrderSide.O).size)
        assertEquals(1,orderBook.getOrders(OrderSide.B).size)

        orderBook.removeOrder(1)
        assertEquals(1 ,orderBook.getOrders(OrderSide.O).size)
        assertEquals(1 ,orderBook.getOrders(OrderSide.B).size)

        orderBook.removeOrder(2)
        assertEquals(0,orderBook.getOrders(OrderSide.O).size)
        assertEquals(1,orderBook.getOrders(OrderSide.B).size)
    }

    @Test
    fun testRemoveOrderMoreThanOnce() {
        val orderBook = OrderBook()
        orderBook.addOrder(Order(1,100.00, OrderSide.O,2))
        orderBook.addOrder(Order(2,100.00, OrderSide.O,3))
        orderBook.addOrder(Order(5, 99.00, OrderSide.B,3))
        assertEquals(2,orderBook.getOrders(OrderSide.O).size)
        assertEquals(1,orderBook.getOrders(OrderSide.B).size)

        orderBook.removeOrder(1)
        orderBook.removeOrder(1)
        assertEquals(1 ,orderBook.getOrders(OrderSide.O).size)
        assertEquals(1 ,orderBook.getOrders(OrderSide.B).size)

        orderBook.removeOrder(2)
        orderBook.removeOrder(2)
        assertEquals(0,orderBook.getOrders(OrderSide.O).size)
        assertEquals(1,orderBook.getOrders(OrderSide.B).size)
    }



    @Test
    fun testGetOrders() {
        val orderBook = OrderBook()
        assertEquals(0,orderBook.getOrders(OrderSide.O).size)
        assertEquals(0,orderBook.getOrders(OrderSide.B).size)
        orderBook.addOrder(Order(1,100.00, OrderSide.O,2))
        orderBook.addOrder(Order(4,102.00, OrderSide.O,6))
        orderBook.addOrder(Order(2,100.00, OrderSide.O,3))
        orderBook.addOrder(Order(5, 99.00, OrderSide.B,3))
        orderBook.addOrder(Order(7, 98.00, OrderSide.B,2))
        orderBook.addOrder(Order(6, 98.00, OrderSide.B,4))
        assertEquals(3,orderBook.getOrders(OrderSide.O).size)
        assertEquals(3,orderBook.getOrders(OrderSide.B).size)
    }
}