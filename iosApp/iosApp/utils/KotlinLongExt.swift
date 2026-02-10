//
//  KotlinLongExt.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 10/02/2026.
//  Copyright © 2026 orgName. All rights reserved.
//

import Shared

protocol Int64Representable {
    var asInt64: Int64 { get }
}

extension Int64: Int64Representable {
    var asInt64: Int64 { self }
}

extension KotlinLong: Int64Representable {
    var asInt64: Int64 { int64Value }
}

extension Optional where Wrapped: Int64Representable {
    var asInt64: Int64? {
        self?.asInt64
    }
}
