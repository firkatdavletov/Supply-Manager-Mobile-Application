//
//  PaymentModalType.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 19/07/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

enum PaymentModalType : Identifiable {
    case card
    
    var id : Int {
        hashValue
    }
}
