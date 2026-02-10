//
//  HomeView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 08.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//P

import SwiftUI
import Shared

struct HomeView: View {
    let component: HomeComponent
    
    @StateValue private var state: HomeViewState
    
    init(component: HomeComponent) {
        self.component = component
        _state = StateValue(component.state)
    }
    
    var body: some View {
        HomeContent(
            isLoading: state.isLoading,
            userName: state.userName,
            orders: state.orders,
            deliveredCount: Int(state.deliveredCount),
            cancelledCount: Int(state.cancelledCount),
            pendingCount: Int(state.pendingCount),
            processingCount: Int(state.processingCount),
            onOrderTap: { self.component.onEvent(event: HomeViewEventOnOrderClicked(id: $0)) },
            onRefresh: { self.component.onEvent(event: HomeViewEventOnRefresh())},
            onAddTap: { self.component.onEvent(event: HomeViewEventOnAddClicked())},
            onProfileClicked: { component.onEvent(event: HomeViewEventOnUserClicked())}
        )
        .navigationBarBackButtonHidden(true)
    }
}
