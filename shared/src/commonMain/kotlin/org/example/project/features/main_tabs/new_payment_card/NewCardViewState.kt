package org.example.project.features.main_tabs.new_payment_card

data class NewCardViewState(
    val cardNumber: String,
    val cardHolder: String,
    val expirationDate: String,
    val cvv: String,
)
