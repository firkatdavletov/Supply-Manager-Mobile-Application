//
//  PriceExt.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 30/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//
import Foundation

extension Int64 {
    func asCurrency(locale: Locale = .current, currencySymbol: String? = "₽") -> String {
        let price = Double(self) / 100.0
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        formatter.locale = locale
        if let symbol = currencySymbol {
            formatter.currencySymbol = symbol
        }
        return formatter.string(from: NSNumber(value: price)) ?? "\(price)"
    }
}