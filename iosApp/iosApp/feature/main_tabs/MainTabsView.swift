//
//  MainTabsView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 08.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct MainTabsView: View {
    let root: MainTabsComponent

    
    init(root: MainTabsComponent) {
        self.root = root
    }
    
    var body: some View {
        StackView(
            stackValue: StateValue(root.childStack),
            getTitle: { a in
                return ""
            },
            onBack: { index in
                root.onBackClicked(toIndex: index)
            },
            childContent: { child in
                switch child {
                case let child as MainTabsComponentChild.CatalogChild: CatalogView(root: child.component)
                case let child as MainTabsComponentChild.HomeChild: HomeView(component: child.component)
                case let child as MainTabsComponentChild.CartChild: CartView(component: child.component)
                case let child as MainTabsComponentChild.PaymentChild: PaymentView(component: child.component)
                case let child as MainTabsComponentChild.SbpBanksChild: SbpBanksView(component: child.component)
                case let child as MainTabsComponentChild.CurrentOrderChild: CurrentOrderView(component: child.component)
                case let child as MainTabsComponentChild.SearchAddressChild: SearchAddressView(component: child.component)
                default: EmptyView()
                }
            }
        )
    }
}
