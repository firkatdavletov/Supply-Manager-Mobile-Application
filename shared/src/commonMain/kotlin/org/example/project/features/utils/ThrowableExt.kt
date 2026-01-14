package org.example.project.features.utils

import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException
import org.example.project.data.HttpException

fun Throwable.toUserMessage(): String = when (this) {
    is ClientRequestException -> when (response.status.value) {
        401 -> "Необходимо войти в систему"
        403 -> "Нет доступа"
        404 -> "Не найдено"
        else -> "Ошибка запроса"
    }
    is ServerResponseException -> "Сервер не отвечает"
    is SocketTimeoutException -> "Превышено время ожидания"
    is IOException -> "Нет подключения к интернету"
    is HttpException -> {
        when (this) {
            HttpException.AccessDenied -> "Нет доступа"
            HttpException.BadRequest -> "Ошибка запроса"
            HttpException.NotFound -> "Не найдено"
            HttpException.RequiredAuth -> "Необходимо войти в систему"
            HttpException.ServerError -> "Сервер не отвечает"
            else -> "Что-то пошло не так"
        }
    }
    else -> "Что-то пошло не так"
}