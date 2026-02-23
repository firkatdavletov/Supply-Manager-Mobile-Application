import SwiftUI
import Shared

struct ProductsSettingsContent: View {
    let title: String
    let products: [Shared.ProductModel]
    let searchQuery: String
    let selectedProductId: KotlinLong?
    let isLoading: Bool
    let onBack: () -> Void
    let onAddProduct: () -> Void
    let onSearchQueryChanged: (String) -> Void
    let onProductClicked: (Int64) -> Void

    var body: some View {
        VStack(spacing: 0) {
            topBar

            VStack(spacing: 12) {
                Button(action: onAddProduct) {
                    Text("Добавить товар")
                        .font(AppTypography.bodyLarge)
                        .foregroundStyle(Color.white)
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 12)
                        .background(Color.blue)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                }

                TextField(
                    "Поиск товаров",
                    text: Binding(
                        get: { searchQuery },
                        set: { onSearchQueryChanged($0) }
                    )
                )
                .textFieldStyle(.roundedBorder)

                if isLoading {
                    Spacer()
                    ProgressView()
                        .frame(maxWidth: .infinity, minHeight: 180)
                    Spacer()
                } else if products.isEmpty {
                    Spacer()
                    Text("Товары не найдены")
                        .font(AppTypography.bodyMedium)
                        .foregroundStyle(Color.onSurface.opacity(0.75))
                        .frame(maxWidth: .infinity, minHeight: 180)
                    Spacer()
                } else {
                    ScrollView {
                        VStack(spacing: 0) {
                            ForEach(products, id: \.id) { product in
                                let isSelected = selectedProductId.asInt64 == product.id.asInt64
                                Button(
                                    action: {
                                        onProductClicked(product.id.asInt64)
                                    },
                                    label: {
                                        HStack(spacing: 12) {
                                            VStack(alignment: .leading, spacing: 2) {
                                                Text(product.title)
                                                    .font(AppTypography.bodyLarge)
                                                    .foregroundStyle(Color.onBackground)
                                                Text("Цена: \(product.price.asCurrency())")
                                                    .font(AppTypography.bodySmall)
                                                    .foregroundStyle(Color.onSurface.opacity(0.8))
                                            
                                                if let description = product.description_ ,!description.isEmpty {
                                                    Text(description)
                                                        .font(AppTypography.bodySmall)
                                                        .foregroundStyle(Color.onSurface.opacity(0.8))
                                                }
                                            }
                                            Spacer()
                                            Image(systemName: "chevron.right")
                                                .font(.system(size: 14, weight: .semibold))
                                                .foregroundStyle(Color.onSurface)
                                        }
                                        .padding(.horizontal, 16)
                                        .padding(.vertical, 14)
                                        .background(isSelected ? Color.secondaryContainer : Color.surface)
                                    }
                                )
                                .buttonStyle(.plain)

                                if product.id != products.last?.id {
                                    Divider()
                                        .overlay(Color.onSurface.opacity(0.15))
                                        .padding(.leading, 16)
                                }
                            }
                        }
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                    }
                }
            }
            .padding(16)
        }
        .background(Color.background.ignoresSafeArea())
    }
}

extension ProductsSettingsContent {
    private var topBar: some View {
        HStack {
            Button(action: onBack) {
                Image(systemName: "chevron.left")
                    .foregroundColor(Color.white)
                    .frame(width: 28, height: 28)
            }
            Spacer()
            Text(title)
                .font(AppTypography.titleLarge)
                .foregroundStyle(Color.white)
            Spacer()
            Color.clear
                .frame(width: 28, height: 28)
        }
        .padding(.horizontal, 16)
        .padding(.top, 8)
        .padding(.bottom, 12)
        .background(Color.blue.ignoresSafeArea(edges: .top))
    }
}
