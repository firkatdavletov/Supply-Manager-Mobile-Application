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
                PENDING -> "В ожидании"
                AWAITING_PAYMENT -> "Ожидает оплаты"
                AWAITING_CASH_PAYMENT -> "Ожидает оплаты наличными"
                PAID -> "Готовится"
                PROCESSING -> "В пути"
                FAILED -> "Ошибка"
                CANCELLED -> "Отменен"
                DELIVERED -> "Доставлен"
            }
        }
    }
}