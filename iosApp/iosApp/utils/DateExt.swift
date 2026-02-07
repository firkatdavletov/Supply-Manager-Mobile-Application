//
//  DateExt.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 30/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//

import Foundation

extension Date {
    
    /// Парсит строку из Kotlin LocalDateTime в Date
    static func fromLocalDateTime(_ string: String?) -> Date? {
        if (string == nil) { return nil }
        
        let formatter = ISO8601DateFormatter()
        formatter.formatOptions = [.withInternetDateTime, .withFractionalSeconds]
        // если сервер присылает без часового пояса
        formatter.timeZone = TimeZone(secondsFromGMT: 0)
        return formatter.date(from: string!)
    }
    
    /// Конвертирует Date обратно в строку в формате Kotlin LocalDateTime
    func toLocalDateTimeString() -> String {
        let formatter = ISO8601DateFormatter()
        formatter.formatOptions = [.withInternetDateTime, .withFractionalSeconds]
        formatter.timeZone = TimeZone(secondsFromGMT: 0)
        return formatter.string(from: self)
    }
}
