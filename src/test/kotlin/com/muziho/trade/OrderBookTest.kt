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
    fun testModifyOfferOrder() {
        val orderBook = OrderBook()
        val orderOffer1 = orderBook.addOrder(Order(1,100.00, OrderSide.O,2))
        var orders = orderBook.getOrders(OrderSide.O)
        val order = orders.find { it.id == 1L}
        assertEquals(orderOffer1, order)
        assertEquals(2L, order?.size)

        orderBook.modifyOrder(1, 5L)

        orders = orderBook.getOrders(OrderSide.O)
        val orderModified = orders.find { it.id == 1L}
        assertNotEquals(orderOffer1, orderModified)
        assertEquals(5L, orderModified?.size)
    }

    @Test
    fun testModifyBidOrder() {
        val orderBook = OrderBook()
        val orderBid1 = orderBook.addOrder(Order(1, 99.00, OrderSide.B,3))
        var orders = orderBook.getOrders(OrderSide.B)
        val order = orders.find { it.id == 1L}
        assertEquals(orderBid1, order)
        assertEquals(3L, order?.size)

        val modifiedOrderBid1 = orderBook.modifyOrder(1, 5L)

        orders = orderBook.getOrders(OrderSide.B)
        val orderModified = orders.find { it.id == 1L}
        assertNotEquals(orderBid1, orderModified)
        assertEquals(5L, orderModified?.size)
        assertEquals(orderBid1?.created, orderModified?.created)
    }


    @Test
    fun testGetPrice() {
        val orderBook = OrderBook()
        buildOrders(orderBook)

        var size = orderBook.getPrice(OrderSide.O, 1)
        assertEquals(100.0, size, 0.0)
        size = orderBook.getPrice(OrderSide.O, 2)
        assertEquals(101.0, size, 0.0)
        size = orderBook.getPrice(OrderSide.O, 3)
        assertEquals(102.0, size, 0.0)

        size = orderBook.getPrice(OrderSide.B, 1)
        assertEquals(99.0, size, 0.0)
        size = orderBook.getPrice(OrderSide.B, 2)
        assertEquals(98.0, size, 0.0)
        size = orderBook.getPrice(OrderSide.B, 3)
        assertEquals(97.0, size, 0.0)
    }

    @Test
    fun testGetPriceWhereLevelDoesNotExist() {
        val orderBook = OrderBook()
        buildOrders(orderBook)

        var size = orderBook.getPrice(OrderSide.O, 1)
        assertEquals(100.0, size, 0.0)
        size = orderBook.getPrice(OrderSide.O, 2)
        assertEquals(101.0, size, 0.0)

        // level does exist
        size = orderBook.getPrice(OrderSide.O, 4)
        assertEquals(0.0, size, 0.0)

        size = orderBook.getPrice(OrderSide.B, 1)
        assertEquals(99.0, size, 0.0)
        size = orderBook.getPrice(OrderSide.B, 2)
        assertEquals(98.0, size, 0.0)
        size = orderBook.getPrice(OrderSide.B, 3)
        assertEquals(97.0, size, 0.0)
        // level does exist
        size = orderBook.getPrice(OrderSide.B, 6)
        assertEquals(0.0, size, 0.0)
    }


    @Test
    fun testGetTotalSize() {

        val orderBook = OrderBook()
        buildOrders(orderBook)

        var size = orderBook.getTotalSize(OrderSide.O, 1)
        assertEquals(5, size)
        size = orderBook.getTotalSize(OrderSide.O, 2)
        assertEquals(4, size)
        size = orderBook.getTotalSize(OrderSide.O, 3)
        assertEquals(6, size)

        size = orderBook.getTotalSize(OrderSide.B, 1)
        assertEquals(3, size)
        size = orderBook.getTotalSize(OrderSide.B, 2)
        assertEquals(6, size)
        size = orderBook.getTotalSize(OrderSide.B, 3)
        assertEquals(5, size)
        size = orderBook.getTotalSize(OrderSide.B, 6)
        assertEquals(0, size)
    }

    @Test
    fun testGetOrders() {
        val orderBook = OrderBook()
        assertEquals(0,orderBook.getOrders(OrderSide.O).size)
        assertEquals(0,orderBook.getOrders(OrderSide.B).size)
        buildOrders(orderBook)
        assertEquals(4,orderBook.getOrders(OrderSide.O).size)
        assertEquals(4,orderBook.getOrders(OrderSide.B).size)
    }

    @Test
    fun testGetOrdersInTheRightCreatedOrder() {
        val orderBook = OrderBook()
        assertEquals(0,orderBook.getOrders(OrderSide.O).size)
        assertEquals(0,orderBook.getOrders(OrderSide.B).size)

        // add micro delay between adding order
        val offerOrder1 = orderBook.addOrder(Order(1,100.00, OrderSide.O,2))
        Thread.sleep(1L)
        val offerOrder2 = orderBook.addOrder(Order(4,102.00, OrderSide.O,6))
        Thread.sleep(1L)
        val offerOrder3 = orderBook.addOrder(Order(2,100.00, OrderSide.O,3))

        val offers = orderBook.getOrders(OrderSide.O)
        assertEquals(3,offers.size)
        assertEquals(offerOrder1, offers[0])
        assertEquals(offerOrder3, offers[1])
        assertEquals(offerOrder2, offers[2])


        val bidOrder1 = orderBook.addOrder(Order(5, 99.00, OrderSide.B,3))
        Thread.sleep(1L)
        val bidOrder2 = orderBook.addOrder(Order(7, 98.00, OrderSide.B,2))
        Thread.sleep(1L)
        val bidOrder3 = orderBook.addOrder(Order(6, 99.00, OrderSide.B,4))

        val bids = orderBook.getOrders(OrderSide.B)
        assertEquals(3,bids.size)
        assertEquals(bidOrder1, bids[0])
        assertEquals(bidOrder3, bids[1])
        assertEquals(bidOrder2, bids[2])
    }

    private fun buildOrders(orderBook :OrderBook){
        orderBook.addOrder(Order(1,100.00, OrderSide.O,2))
        orderBook.addOrder(Order(3,101.00, OrderSide.O,4))
        orderBook.addOrder(Order(4,102.00, OrderSide.O,6))
        orderBook.addOrder(Order(2,100.00, OrderSide.O,3))
        orderBook.addOrder(Order(5, 99.00, OrderSide.B,3))
        orderBook.addOrder(Order(7, 98.00, OrderSide.B,2))
        orderBook.addOrder(Order(6, 98.00, OrderSide.B,4))
        orderBook.addOrder(Order(8, 97.00, OrderSide.B,5))
    }
}