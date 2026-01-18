package org.example.project.domain.models

enum class OrderStatus {
    PENDING,
    AWAITING_PAYMENT,
    PAID,
    PROCESSING,
    AWAITING_COURIER,
    AWAITING_RECEIPT,
    DELIVERY,
    FAILED,
    CANCELLED,
    COMPLETED;

    companion object {
        fun getTitle(status: OrderStatus): String {
            return when (status) {
                PENDING -> "Ожидает подтверждения"
                AWAITING_PAYMENT -> "Ожидает оплаты"
                PAID -> "Оплачен"
                PROCESSING -> "Ресторан готовит заказ"
                AWAITING_COURIER -> "Ожидает курьера"
                AWAITING_RECEIPT -> "Ожидает получения"
                DELIVERY -> "Курьер уже в пути"
                FAILED -> "Ошибка"
                CANCELLED -> "Отменен"
                COMPLETED -> "Выполнен"
            }
        }
    }
}