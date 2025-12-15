//
//  SearchAddressView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 26/07/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct SearchAddressView: View {
    let component: SearchAddressComponent
    @StateValue var state: SearchAddressViewState
    @State var query: String = ""
    @FocusState private var isTextFieldFocused: Bool
    
    init(component: SearchAddressComponent) {
        self.component = component
        _state = StateValue(component.state)
    }
    
    var body: some View {
        VStack {
            HStack(spacing: 8) {
                Image(systemName: "magnifyingglass")
                    .resizable()
                    .frame(width: 16, height: 16)
                    .foregroundColor(Color("SlateBlueGray"))
                TextField(
                    "Введите адрес",
                    text: Binding(
                        get: {
                            state.query
                        },
                        set: { value in
                            query = value
                            component.onEvent(event: SearchAddressViewEventOnQueryChanged(query: value))
                        }
                    )
                )
                .keyboardType(.default)
                    .font(.system(size: 16, weight: .regular, design: .rounded))
                    .foregroundColor(Color("DarkGrayColor"))
                    .lineLimit(1)
                    .focused($isTextFieldFocused)
                    .onChange(of: query) { newValue in
                       query = newValue
                    }
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(22)
            .background(Color("IceBlue"))
            .cornerRadius(25)
            
            LazyVStack(alignment: .leading, spacing: 24) {
                ForEach(state.addresses, id: \.self) { address in
                    Text("address")
                        .font(.system(size: 16, weight: .regular, design: .rounded))
                        .foregroundColor(Color("DarkGrayColor"))
                }
            }
            .padding()
            
            Spacer()
        }
        .padding(.top)
        .toolbar {
            ToolbarItem(placement: .topBarLeading) {
                HStack(alignment: .center, spacing: 16) {
                    Button(action: {
                        component.onEvent(event: SearchAddressViewEventOnBackClicked())
                    }) {
                        Image(systemName: "chevron.backward")
                            .foregroundColor(.white) // цвет иконки
                            .frame(width: 36, height: 36)
                            .background(Circle().fill(Color("DarkGrayColor"))) // чёрный круглый фон
                    }
                    .buttonStyle(PlainButtonStyle())
                    
                }
            }
        }
        .navigationBarBackButtonHidden(true)
        .padding(.horizontal)
    }
}
