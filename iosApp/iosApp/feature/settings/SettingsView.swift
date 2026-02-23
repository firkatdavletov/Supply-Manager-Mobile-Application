//
//  SettingsView.swift
//  iosApp
//
//  Created by Codex on 21/02/2026.
//

import SwiftUI
import Shared

struct SettingsView: View {
    let component: SettingsComponent

    @StateValue private var state: SettingsViewState

    init(component: SettingsComponent) {
        self.component = component
        _state = StateValue(component.state)
    }

    var body: some View {
        SettingsContent(
            title: state.title,
            items: state.items,
            selectedItem: state.selectedItem,
            onBack: {
                component.onEvent(event: SettingsViewEventOnBackClicked())
            },
            onItemClicked: { item in
                component.onEvent(event: SettingsViewEventOnMenuItemClicked(item: item))
            }
        )
        .navigationBarBackButtonHidden(true)
    }
}
