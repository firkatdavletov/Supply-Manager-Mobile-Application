import SwiftUI
import Shared

struct CategoriesSettingsContent: View {
    let title: String
    let categories: [CategoryModel]
    let searchQuery: String
    let selectedCategoryId: KotlinLong?
    let isLoading: Bool
    let onBack: () -> Void
    let onAddCategory: () -> Void
    let onSearchQueryChanged: (String) -> Void
    let onCategoryClicked: (Int64) -> Void

    var body: some View {
        VStack(spacing: 0) {
            topBar

            VStack(spacing: 12) {
                Button(action: onAddCategory) {
                    Text("Добавить категорию")
                        .font(AppTypography.bodyLarge)
                        .foregroundStyle(Color.white)
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 12)
                        .background(Color.blue)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                }

                TextField(
                    "Поиск категорий",
                    text: Binding(
                        get: { searchQuery },
                        set: { onSearchQueryChanged($0) }
                    )
                )
                .textFieldStyle(.roundedBorder)

                if isLoading {
                    ProgressView()
                        .frame(maxWidth: .infinity, minHeight: 180)
                } else if categories.isEmpty {
                    Text("Категории не найдены")
                        .font(AppTypography.bodyMedium)
                        .foregroundStyle(Color.onSurface.opacity(0.75))
                        .frame(maxWidth: .infinity, minHeight: 180)
                } else {
                    ScrollView {
                        VStack(spacing: 0) {
                            ForEach(categories, id: \.id) { category in
                                let isSelected = selectedCategoryId.asInt64 == category.id.asInt64
                                Button(
                                    action: {
                                        onCategoryClicked(category.id.asInt64)
                                    },
                                    label: {
                                        HStack(spacing: 12) {
                                            VStack(alignment: .leading, spacing: 2) {
                                                Text(category.title)
                                                    .font(AppTypography.bodyLarge)
                                                    .foregroundStyle(Color.onBackground)
                                                if let imageUrl = category.imageUrl, !imageUrl.isEmpty {
                                                    Text(imageUrl)
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

                                if category.id != categories.last?.id {
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

extension CategoriesSettingsContent {
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
