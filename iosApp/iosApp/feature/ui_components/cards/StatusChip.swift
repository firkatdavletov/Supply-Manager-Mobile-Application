//
//  StatusChip.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 28/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//
import SwiftUI
import Shared

struct StatusChip: View {
    
    let status: OrderStatus
    
    var title: String {
        switch status {
            case .pending: return "Новый"
            case .processing: return "В работе"
            case .completed: return "Завершён"
            case .cancelled: return "Отменён"
            default : return "Неизвестно"
        }
    }
    
    var backgroundColor: Color {
        switch status {
        case .pending: return Color.blue.opacity(0.15)
        case .processing: return Color.orange.opacity(0.15)
        case .completed: return Color.green.opacity(0.15)
        case .cancelled: return Color.red.opacity(0.15)
        default : return Color.blue.opacity(0.15)
        }
    }
        
    var textColor: Color {
        switch status {
        case .pending: return .blue
        case .processing: return .orange
        case .completed: return .green
        case .cancelled: return .red
        default : return .blue
        }
    }
    
    var body: some View {
        Text(title)
            .font(.system(size: 12, weight: .semibold))
            .padding(.horizontal, 10)
            .padding(.vertical, 6)
            .background(backgroundColor)
            .foregroundColor(textColor)
            .clipShape(Capsule())
    }
}
