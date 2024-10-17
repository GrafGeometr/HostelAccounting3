package com.example.hostelaccounting3.requests


import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*


object RequestHelper {
    private val client = HttpClient(CIO)

    fun createRequest(
        url: URL,
        headers: Headers,
        requestMethod: HttpMethod,
        body: Body,
        contentType: ContentType
    ) =
        AppRequest {
            client.request(url) {
                method = requestMethod
                headers {
                    headers.forEach {
                        append(it.first, it.second)
                    }
                }
                contentType(contentType)
                setBody(body)
            }
        }

    fun Post(
        url: URL,
        headers: Headers = emptyList(),
        body: Body,
        contentType: ContentType = ContentType.Application.Json
    ): AppRequest = createRequest(
        url,
        headers,
        HttpMethod.Post,
        body,
        contentType
    )

    fun Get(
        url: URL,
        headers: Headers = emptyList(),
        body: Body,
        contentType: ContentType = ContentType.Application.Json
    ): AppRequest = createRequest(
        url,
        headers,
        HttpMethod.Get,
        body,
        contentType
    )
}