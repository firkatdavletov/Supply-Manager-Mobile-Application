//
//  HomeContent.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 12/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//


import SwiftUI
import Shared

struct HomeContent: View {
    let isLoading: Bool
    let orders: [OrderPreviewModel]
    let deliveredCount: Int
    let cancelledCount: Int
    let pendingCount: Int
    let processingCount: Int
    let onOrderTap: (Int64) -> Void
    let onRefresh: () -> Void
    let onAddTap: () -> Void
    
    private let columns = Array(
        repeating: GridItem(.flexible(), spacing: 12),
        count: 4
    )

    var body: some View {
        VStack(alignment: .center) {
            HomeTopBar(
                companyLogoName: "Logo", // имя ассета
                userName: "Эллина Кулушева",
                onAddTap: onAddTap
            )
            
            if (isLoading) {
                ProgressView()
            }
            
            ScrollView {
                VStack {
                    HStack {
                        AllCounterCardView(count: orders.count)
                        CounterCardView(count: pendingCount, status: OrderStatus.pending)
                    }
                    HStack {
                        CounterCardView(count: processingCount, status: OrderStatus.processing)
                        CounterCardView(count: deliveredCount, status: OrderStatus.completed)
                    }
                    
                    LazyVStack(spacing: 12) {
                        ForEach(orders, id: \.id) { order in
                            OrderPreviewCard(
                                companyName: order.companyName,
                                contactName: order.customerName,
                                totalAmount: order.totalAmount
                                    .asCurrency(locale: Locale(identifier: "ru_RU"), currencySymbol: "₽"),
                                deliveryDate: order.deliveryTime,
                                status: order.status
                            )
                            .onTapGesture {
                                onOrderTap(order.id)
                            }
                        }
                    }
                }
                .padding()
                .background(Color(.systemGroupedBackground))
            }
            .refreshable {
                onRefresh()
            }
        }
    }
}

#Preview {
    HomeContent(
        isLoading: false,
        orders: [],
        deliveredCount: 0,
        cancelledCount: 0,
        pendingCount: 0,
        processingCount: 0,
        onOrderTap: { id in},
        onRefresh: {},
        onAddTap: {}
    )
}

