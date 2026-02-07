//
//  OrderPreviewCard 2.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 28/01/2026.
//  Copyright © 2026 orgName. All rights reserved.
//
import SwiftUI
import Shared

struct OrderPreviewCard: View {
    
    let companyName: String?
    let contactName: String
    let totalAmount: String
    let deliveryDate: String?
    let status: OrderStatus
    
    var body: some View {
        ZStack(alignment: .topTrailing) {
            
            VStack(alignment: .leading, spacing: 10) {
                
                if companyName != nil {
                    Text(companyName!)
                        .font(.system(size: 16, weight: .bold))
                        .foregroundColor(.black)
                }
                
                Text(contactName)
                    .font(.system(size: 14, weight: .medium))
                    .foregroundColor(.gray)
                
                HStack(spacing: 6) {
                    Image(systemName: "shippingbox")
                        .foregroundColor(.gray)
                    Text(totalAmount)
                        .font(.system(size: 14))
                        .foregroundColor(.black)
                }
                
                if (deliveryDate != nil) {
                    HStack(spacing: 6) {
                        Image(systemName: "calendar")
                            .foregroundColor(.gray)
                        Text(deliveryDate!)
                            .font(.system(size: 14))
                            .foregroundColor(.black)
                    }
                }
            
            }
            .padding(16)
            .frame(maxWidth: .infinity, alignment: .leading)
            
            StatusChip(status: status)
                .padding(12)
        }
        .background(Color.white)
        .overlay(
            RoundedRectangle(cornerRadius: 16)
                .stroke(Color.gray.opacity(0.3), lineWidth: 1)
        )
        .cornerRadius(16)
        .shadow(color: .black.opacity(0.04), radius: 4, x: 0, y: 2)
    }
}
