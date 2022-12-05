package com.muziho.trade

import java.time.Instant
import java.util.*

data class Order(val id: Long, val price : Double, val side: OrderSide, val size: Long, val created: Long = Date.from(Instant.now()).time)