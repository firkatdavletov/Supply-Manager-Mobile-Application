package org.example.project.data

interface HttpException {
    data object RequiredAuth : HttpException, Throwable("Необходимо войти в систему")
    data object AccessDenied : HttpException, Throwable("Нет доступа")
    data object NotFound : HttpException, Throwable("Не найдено")
    data object BadRequest : HttpException, Throwable("Ошибка запроса")
    data object ServerError : HttpException, Throwable("Проблемы на сервере. Попробуйте позже")
    data object Unknown : HttpException, Throwable("Что-то пошло не так")
    data object CartNotFound : HttpException, Throwable("Корзина не найдена")
}