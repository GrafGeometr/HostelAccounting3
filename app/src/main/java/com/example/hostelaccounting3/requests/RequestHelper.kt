package com.example.hostelaccounting3.requests


import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*


object RequestHelper {
    private val client = HttpClient(CIO)

    fun createRequest(
        url: URL,
        requestMethod: HttpMethod,
        body: Body,
        contentType: ContentType
    ): AppRequest = AppRequest(url, requestMethod, body) {
        client.request(url) {
            method = requestMethod
            contentType(contentType)
            setBody(body)
        }
    }

    fun Post(
        url: URL,
        body: Body,
        contentType: ContentType = ContentType.Application.Json
    ): AppRequest = createRequest(
        url,
        HttpMethod.Post,
        body,
        contentType
    )

    fun Get(
        url: URL,
        body: Body,
        contentType: ContentType = ContentType.Application.Json
    ): AppRequest {
        println("BEGIN")
        val req = createRequest(
            url,
            HttpMethod.Get,
            body,
            contentType
        )
        println("END")
        return req
    }
}