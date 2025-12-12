//
//  SbpBanksView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 18/07/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct SbpBanksView: View {
    @Environment(\.openURL) var openURL
    
    let component: SbpBanksComponent
    
    @StateValue private var state: SbpBanksViewState
    
    init(component: SbpBanksComponent) {
        self.component = component
        _state = StateValue(component.state)
    }
    
    var body: some View {
        ScrollView {
            LazyVStack {
                HStack(spacing: 12) {
                    Image(systemName: "magnifyingglass")
                        .resizable()
                        .frame(width: 16, height: 16)
                        .foregroundColor(Color("SlateBlueGray"))
                    Text("Поиск")
                        .font(.system(size: 14, weight: .regular, design: .rounded))
                        .foregroundColor(Color("DarkGrayColor"))
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(22)
                .background(Color("IceBlue"))
                .cornerRadius(25)
                .padding()
                
                ForEach(state.banks, id: \.bankName) { bank in
                    BankItemView(
                        logoUrl: bank.logoUrl,
                        bankName: bank.bankName,
                        scheme: bank.schema,
                        link: state.qrLink,
                        onClick: { link in
                            if let url = URL(string: link) {
                                openURL(url)
                            } else {
                                print("Некорректный URL")
                            }
                        }
                    )
                    .padding(.vertical, 4)
                    .frame(maxWidth: .infinity, alignment: .leading) // ← Выравнивание по левому краю
                    .padding(.horizontal)
                }
                .padding(.top, 16)
            }
        }
        .navigationBarBackButtonHidden(true)
        .toolbar {
            ToolbarItem(placement: .topBarLeading) {
                HStack(alignment: .center, spacing: 16) {
                    Button(action: {
                        component.onEvent(event: SbpBanksViewEventOnBackClicked())
                    }) {
                        Image(systemName: "chevron.backward")
                            .foregroundColor(.white) // цвет иконки
                            .frame(width: 36, height: 36)
                            .background(Circle().fill(Color("DarkGrayColor"))) // чёрный круглый фон
                    }
                    .buttonStyle(PlainButtonStyle())
                    Text("Выберите банк")
                        .font(.system(size: 17, weight: .regular, design: .rounded))
                        .foregroundColor(Color("DarkGrayColor"))
                }
            }
        }
    }
}

struct BankItemView : View {
    let logoUrl: String
    let bankName: String
    let scheme: String
    let link: String
    let onClick: (String) -> Void
    
    var body: some View {
        HStack {
            AsyncImage(url: URL(string: logoUrl)) { phase in
                switch phase {
                case .empty:
                    ProgressView()
                case .success(let image):
                    image.resizable()
                            .frame(width: 40, height: 40)
                            .clipShape(RoundedRectangle(cornerRadius: 8))
                case .failure:
                    Image(systemName: "exclamationmark.triangle")
                @unknown default:
                    EmptyView()
                }
            }
            .frame(width: 40, height: 40)
            .clipShape(RoundedRectangle(cornerRadius: 8))

            Text(bankName)
                .font(.headline)
        }
        .onTapGesture {
            let newText = link.replacingOccurrences(of: "https", with: scheme)
            onClick(newText)
        }
    }
}
