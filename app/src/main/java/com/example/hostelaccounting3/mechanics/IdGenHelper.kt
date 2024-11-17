package com.example.hostelaccounting3.mechanics

fun genRandomId(
    range: IntRange = 1..Int.MAX_VALUE
) = kotlin.random.Random.nextInt(range.first, range.last)
