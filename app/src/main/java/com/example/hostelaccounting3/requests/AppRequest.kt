package com.example.hostelaccounting3.requests

import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AppRequest(val request: suspend () -> HttpResponse) {
    inline fun <reified T> body(): suspend () -> T = { request().body<T>() }
    fun status(): suspend () -> HttpStatusCode = { request().status }
    fun info(): suspend () -> Pair<String, HttpStatusCode> = { body<String>()() to status()() }
}

typealias Action<T> = suspend () -> T

fun <T, P> Action<T>.map(mapper: (T) -> P): Action<P> = { mapper(this()) }

suspend fun <T> Action<T>.go(): T = this()

fun <T> Action<T>.goAndWait() = runBlocking { go() }

fun <T> Action<T>.goInParallel() = CoroutineScope(Dispatchers.Default).launch { go() }
