//
//  RequestCardView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 28/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//
import SwiftUI
import Shared

struct CounterCardView: View {
    
    let count: Int
    
    let status: OrderStatus
    
    var title: String {
        switch status {
            case .pending: return "В ожидании"
            case .processing: return "Приняты"
            case .completed: return "Отправлены"
            case .cancelled: return "Отменены"
            default : return "Неизвестно"
        }
    }
    
    var backgroundColor: Color {
        switch status {
        case .pending: return Color.orange.opacity(0.15)
        case .processing: return Color.green.opacity(0.15)
        case .cancelled: return Color.green.opacity(0.15)
        case .cancelled: return Color.red.opacity(0.15)
        default : return Color.blue.opacity(0.15)
        }
    }
        
    var textColor: Color {
        switch status {
        case .pending: return .orange
        case .delivery: return .orange
        case .completed: return .green
        case .cancelled: return .red
        default : return .blue
        }
    }
    
    
    
    var body: some View {
        VStack(spacing: 8) {
            Text("\(count)")
                .font(.system(size: 32, weight: .bold))
                .foregroundColor(textColor)
            
            Text(title)
                .font(.system(size: 14, weight: .medium))
                .foregroundColor(textColor)
        }
        .frame(maxWidth: .infinity)
        .padding(.vertical, 20)
        .background(backgroundColor)
        .overlay(
            RoundedRectangle(cornerRadius: 16)
                .stroke(Color.gray.opacity(0.4), lineWidth: 1)
        )
        .cornerRadius(16)
        .shadow(color: .black.opacity(0.05), radius: 4, x: 0, y: 2)
    }
}
