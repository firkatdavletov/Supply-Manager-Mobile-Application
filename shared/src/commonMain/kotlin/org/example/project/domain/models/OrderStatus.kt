package org.example.project.domain.models

enum class OrderStatus {
    PENDING,
    AWAITING_PAYMENT,
    AWAITING_CASH_PAYMENT,
    PAID,
    PROCESSING,
    FAILED,
    CANCELLED,
    DELIVERED;

    companion object {
        fun getTitle(status: OrderStatus): String {
            return when (status) {
                PENDING -> "Ожидаем подтверждения"
                AWAITING_PAYMENT -> "Ожидаем оплаты"
                AWAITING_CASH_PAYMENT -> "Ожидаем оплаты наличными"
                PAID -> "Ресторан готовит заказ"
                PROCESSING -> "Заказ в доставка"
                FAILED -> "Ошибка"
                CANCELLED -> "Отменен"
                DELIVERED -> "Доставлен"
            }
        }
    }
}