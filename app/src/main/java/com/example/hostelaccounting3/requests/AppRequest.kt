package com.example.hostelaccounting3.requests

import com.example.hostelaccounting3.datetime.Datetime
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val cache: MutableMap<Pair<URL, Body>, Pair<HttpResponse, Datetime>> = mutableMapOf()

class AppRequest(
    private val url: URL,
    private val requestMethod: HttpMethod,
    private val requestBody: Body,
    val request: suspend () -> HttpResponse
) {

    suspend operator fun invoke(): HttpResponse {
        println(url)
        println(requestMethod)
        println(requestBody)
        println(cache)
        if (cache.containsKey(url to requestBody)) {
            val (result, time) = cache[url to requestBody]!!
            if ((Datetime.now() - time) < 5) {
                return result
            }
        }
        val result = request()
        cache[url to requestBody] = result to Datetime.now()
        return result
    }

    inline fun <reified T> body(): suspend () -> T = { this().body<T>() }
    fun status(): suspend () -> HttpStatusCode = { this().status }
    fun info(): suspend () -> Pair<String, HttpStatusCode> = {
        val res = this()
        val status = res.status
        val body = res.body<String>()
        body to status
    }
}

typealias Action<T> = suspend () -> T

fun <T, P> Action<T>.map(mapper: (T) -> P): Action<P> = { mapper(this()) }

suspend fun <T> Action<T>.go(): T = this()

fun <T> Action<T>.goAndWait() = runBlocking { go() }

fun <T> Action<T>.goInParallel() = CoroutineScope(Dispatchers.Default).launch { go() }
