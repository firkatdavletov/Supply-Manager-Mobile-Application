//
//  TestView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 22.09.2024.
//  Copyright © 2024 orgName. All rights reserved.
//
import SwiftUI
import Shared

struct LoginView: View {
    let component: SignInComponent
    @State private var phoneNumber: String = ""
    @StateValue private var screenState: SignInComponentScreenState
    @State private var selectedType: String
    
    init(component: SignInComponent) {
        _screenState = StateValue(component.screenState)
        _selectedType = State(initialValue: component.screenState.value.selectedAuthType)
        self.component = component
    }
    
    var body: some View {
        VStack {
            Text("Введите номер телефона")
                .font(.title)
                .foregroundColor(.primary)
            TextField("+7 (000)-000-00-00", text: $phoneNumber)
                .padding()
                .cornerRadius(40) // Закругление углов
                .overlay(
                    RoundedRectangle(cornerRadius: 40) // Рамка вокруг TextField
                        .stroke(Color.gray, lineWidth: 1)
                )
            test
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .padding()
        .navigationTitle(Text("Вход"))
    }
    
    @ViewBuilder
    var test: some View {
        if (screenState.isLoading) {
            ProgressView()
                .progressViewStyle(CircularProgressViewStyle())
                .scaleEffect(1.5)
        } else {
            HStack(spacing: 16) {
                ForEach(screenState.authTypes, id: \.self) { title in
                    Button(action: {
                        component.onEvent(event: LoginComponentEventAuthTypeChanged(authType: title))
                        selectedType = title
                    }) {
                        if (screenState.selectedAuthType == title) {
                            Text(title)
                                .fontWeight(.medium)
                                .foregroundColor(Color.white)
                                .padding()
                                .frame(maxWidth: .infinity)
                                .background(Color.blue)
                                .overlay(
                                    RoundedRectangle(cornerRadius: 40)
                                        .stroke(Color.clear, lineWidth: 2)
                                )
                                .cornerRadius(40) // Скругляем углы
                        } else {
                            Text(title)
                                .fontWeight(.medium)
                                .foregroundColor(Color.blue)
                                .padding()
                                .frame(maxWidth: .infinity)
                                .background(Color.white)
                                .overlay(
                                    RoundedRectangle(cornerRadius: 40)
                                        .stroke(Color.blue, lineWidth: 2)
                                )
                                .cornerRadius(40) // Скругляем углы
                        }
                        
                    }
                    .animation(.easeInOut, value: selectedType)
                }
            }
            Spacer()
            Button(action: {
                component.onEvent(event: LoginComponentEventLoginClicked(phoneNumber: phoneNumber))
            }) {
                Text("Продолжить")
                    .fontWeight(.medium)
                    .foregroundColor(Color.white)
                    .padding()
                    .frame(maxWidth: .infinity)
                    .background(Color.blue)
                    .overlay(
                        RoundedRectangle(cornerRadius: 40)
                            .stroke(Color.clear, lineWidth: 2)
                    )
                    .cornerRadius(40)
            }
            
        }
    }
}
