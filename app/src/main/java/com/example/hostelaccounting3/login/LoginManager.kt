package com.example.hostelaccounting3.login

import com.example.hostelaccounting3.BASE_URL
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.requests.goAndWait
import com.example.hostelaccounting3.requests.map
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


fun Context.login(login: String, password: String) = requestHelper.Post(
    "$BASE_URL/auth/login", body = json.encodeToString(
        mapOf("name" to login, "password" to password)
    ), contentType = ContentType.Application.Json
).info().map { (body, status) ->
    println("Login results came")
    if (status.value == 400) return@map "No name or password"
    if (status.value == 403) return@map "Invalid name or password"
    if (status.value != 200) return@map "Unknown error"
    val token = json.parseToJsonElement(body).jsonObject["auth_token"]?.jsonPrimitive?.content?.let { Token(it) }
    if (token == null) return@map "Unknown error"

    tokenHelper.setToken(token)
//    navController.navigate("main_page")
    null
}.goAndWait()

fun Context.register(login: String, password: String) = requestHelper.Post(
    "$BASE_URL/auth/register", body = json.encodeToString(
        mapOf("name" to login, "password" to password)
    ), contentType = ContentType.Application.Json
).info().map { (body, status) ->
    if (status.value == 400) return@map ("No name or password")
    if (status.value == 403) return@map ("Such user already exists")
    if (status.value != 200) return@map ("Unknown error")

    val token = json.parseToJsonElement(body).jsonObject["auth_token"]?.jsonPrimitive?.content?.let { Token(it) }
    if (token == null) return@map ("Unknown error")

//    navController.navigate("main_page")
    null
}.goAndWait()

fun Context.logout() {
    tokenHelper.removeToken()
//    navController.navigate("login")
}
