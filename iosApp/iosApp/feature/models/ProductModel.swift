//
//  Product.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 10.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import Foundation

struct ProductModel: Identifiable {
    let id = UUID()
    let name: String
    let price: String
    let imageUrl: String
}
