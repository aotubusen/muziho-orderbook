package com.muziho.trade

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class OrderBook {

    private val offers: ConcurrentMap<Double, MutableMap<Long, Order>> = ConcurrentHashMap()
    private val bids: ConcurrentMap<Double, MutableMap<Long, Order>> = ConcurrentHashMap()

    fun addOrder(order:Order): Order?{
        val orders = when(order.side){
            OrderSide.O -> offers
            OrderSide.B -> bids
        }

        val orderSet = orders.getOrPut(order.price) { mutableMapOf() }
        orderSet?.getOrPut(order.id){order}
        return order
    }

    fun removeOrder(orderId:Long){
    }

    fun modifyOrder(orderId:Long, size:Long):Order?{
        return null
    }


    fun getOrders(side: OrderSide) : List<Order> {
        val orders = when(side){
            OrderSide.O -> offers
            OrderSide.B -> bids
        }
        return orders.values.flatMap { it.values}
    }


    fun getPrice(side: OrderSide, level:Int): Double{
        return 0.0
    }

    fun getTotalSize(side: OrderSide, level:Int) : Long{
        if(level<=0) return 0
        return 0
    }
}