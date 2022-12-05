package com.muziho.trade

import java.time.Instant
import java.util.*

data class Order(val id: Long, val price : Double, val side: Char, val size: Long)