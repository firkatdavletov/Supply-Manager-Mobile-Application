import SwiftUI

struct EditCategoryContent: View {
    let title: String
    let name: String
    let imageUrl: String
    let isLoading: Bool
    let onBack: () -> Void
    let onNameChanged: (String) -> Void
    let onImageUrlChanged: (String) -> Void
    let onSave: () -> Void

    var body: some View {
        VStack(spacing: 0) {
            topBar

            VStack(spacing: 12) {
                TextField(
                    "Название категории",
                    text: Binding(
                        get: { name },
                        set: { onNameChanged($0) }
                    )
                )
                .textFieldStyle(.roundedBorder)

                TextField(
                    "URL изображения (необязательно)",
                    text: Binding(
                        get: { imageUrl },
                        set: { onImageUrlChanged($0) }
                    )
                )
                .textFieldStyle(.roundedBorder)

                Button(action: onSave) {
                    HStack {
                        if isLoading {
                            ProgressView()
                                .tint(Color.white)
                        } else {
                            Text("Сохранить")
                                .font(AppTypography.bodyLarge)
                                .foregroundStyle(Color.white)
                        }
                    }
                    .frame(maxWidth: .infinity)
                    .padding(.vertical, 12)
                    .background(Color.blue)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
                }
                .disabled(isLoading)
                .opacity(isLoading ? 0.6 : 1)

                Spacer()
            }
            .padding(16)
        }
        .background(Color.background.ignoresSafeArea())
    }
}

extension EditCategoryContent {
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
