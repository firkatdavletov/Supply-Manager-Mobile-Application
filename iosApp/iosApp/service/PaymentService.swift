//
//  PaymentService.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 16.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import Cloudpayments

final class PaymentService {
    func createCryptogram(
        cardNumber: String,
        expDate: String,
        cvv: String,
        merchantPublicID: String,
        publicKey: String,
        keyVersion: Int
    ) -> String? {
        let cartCryptogramPacket = Card.makeCardCryptogramPacket(
            cardNumber: cardNumber,
            expDate: expDate,
            cvv: cvv,
            merchantPublicID: merchantPublicID,
            publicKey: publicKey,
            keyVersion: keyVersion
        )
        return cartCryptogramPacket
    }
}
