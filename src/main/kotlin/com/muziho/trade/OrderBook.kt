package com.muziho.trade

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class OrderBook {

    private val offers: ConcurrentMap<Double, MutableMap<Long, Order>> = ConcurrentHashMap()
    private val bids: ConcurrentMap<Double, MutableMap<Long, Order>> = ConcurrentHashMap()

    fun addOrder(order:Order): Order?{

        val offerPool = getOrders(OrderSide.O)
        val bidPool = getOrders(OrderSide.B)

        if(offerPool.find {it->  it.id == order.id }!=null || bidPool.find {it->  it.id == order.id }!=null )
            throw Exception("Duplicate ID ${order.id}")

        val orders = when(order.side){
            OrderSide.O -> offers
            OrderSide.B -> bids
        }

        val orderSet = orders.getOrPut(order.price) { mutableMapOf() }
        orderSet?.getOrPut(order.id){order}
        return order
    }

    fun removeOrder(orderId:Long){

        val offerPool = getOrders(OrderSide.O)
        val bidPool = getOrders(OrderSide.B)

        offerPool.find {it->  it.id == orderId }?.let {
            offers[it.price]?.remove(orderId)
        }
        bidPool.find {it->  it.id == orderId }?.let {
            bids[it.price]?.remove(orderId)
        }
    }

    fun modifyOrder(orderId:Long, size:Long):Order?{
        val offerPool = getOrders(OrderSide.O)
        val bidPool = getOrders(OrderSide.B)

        offerPool.find {it->  it.id == orderId }?.let {
            return modifyOrder(it,size,offers)
        }
        bidPool.find {it->  it.id == orderId }?.let {
            return modifyOrder(it,size,bids)
        }
        return null
    }

    private fun modifyOrder(order:Order, size:Long, orders:ConcurrentMap<Double, MutableMap<Long, Order>>):Order?{
        val updatedOrder = order.copy(size = size)
        orders[order.price]?.remove(order.id)
        orders[order.price]?.put(order.id, updatedOrder)
        return updatedOrder
    }

    fun getOrders(side: OrderSide) : List<Order> {
        val orders = when(side){
            OrderSide.O -> offers
            OrderSide.B -> bids
        }
        return orders.values.flatMap { it.values}
    }

    fun getPrice(side: OrderSide, level:Int): Double{
        return when(side){
            OrderSide.O -> getPrice(offers.keys.sorted(),level)
            OrderSide.B -> getPrice(bids.keys.sortedDescending(),level)
        }
    }

    private fun getPrice(prices: List<Double>, level: Int): Double {
        if(level>prices.size) return 0.0
        return prices[level - 1]
    }

    fun getTotalSize(side: OrderSide, level:Int) : Long{
        if(level<=0) return 0
        return 0
    }
}