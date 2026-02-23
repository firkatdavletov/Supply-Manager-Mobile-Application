import SwiftUI
import Shared

struct ImportCsvContent: View {
    let title: String
    let modes: [CatalogImportMode]
    let selectedMode: CatalogImportMode
    let selectedFileName: String?
    let isLoading: Bool
    let responseTitle: String?
    let responseDetails: String?
    let isResponseError: Bool
    let onBack: () -> Void
    let onModeSelected: (CatalogImportMode) -> Void
    let onSelectFile: () -> Void
    let onImport: () -> Void

    var body: some View {
        VStack(spacing: 0) {
            topBar

            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    Text("Режим импорта")
                        .font(AppTypography.titleMedium)
                        .foregroundStyle(Color.onBackground)

                    ForEach(Array(modes.enumerated()), id: \.offset) { _, mode in
                        Button(action: {
                            onModeSelected(mode)
                        }) {
                            HStack(spacing: 12) {
                                Image(systemName: selectedMode.serverValue == mode.serverValue ? "largecircle.fill.circle" : "circle")
                                    .foregroundStyle(Color.blue)
                                    .font(.system(size: 18, weight: .semibold))
                                Text(mode.title)
                                    .font(AppTypography.bodyLarge)
                                    .foregroundStyle(Color.onBackground)
                                Spacer()
                            }
                        }
                        .buttonStyle(.plain)
                    }

                    Button(action: onSelectFile) {
                        Text("Выбрать CSV")
                            .font(AppTypography.bodyLarge)
                            .foregroundStyle(Color.onBackground)
                            .frame(maxWidth: .infinity)
                            .padding(.vertical, 12)
                            .overlay {
                                RoundedRectangle(cornerRadius: 12)
                                    .stroke(Color.onSurface.opacity(0.3), lineWidth: 1)
                            }
                    }
                    .disabled(isLoading)

                    Text(selectedFileName ?? "Файл не выбран")
                        .font(AppTypography.bodyMedium)
                        .foregroundStyle(Color.onSurface.opacity(0.8))

                    Button(action: onImport) {
                        HStack {
                            if isLoading {
                                ProgressView()
                                    .tint(Color.white)
                            } else {
                                Text("Импортировать")
                                    .font(AppTypography.bodyLarge)
                                    .foregroundStyle(Color.white)
                            }
                        }
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 12)
                        .background(Color.blue)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                    }
                    .disabled(selectedFileName == nil || isLoading)
                    .opacity(selectedFileName == nil || isLoading ? 0.6 : 1)

                    if let responseTitle {
                        VStack(alignment: .leading, spacing: 4) {
                            Text(responseTitle)
                                .font(AppTypography.titleSmall)
                            if let responseDetails, !responseDetails.isEmpty {
                                Text(responseDetails)
                                    .font(AppTypography.bodyMedium)
                            }
                        }
                        .foregroundStyle(isResponseError ? Color.onErrorContainer : Color.onSecondaryContainer)
                        .padding(12)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .background(isResponseError ? Color.errorContainer : Color.secondaryContainer)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                    }
                }
                .padding(16)
            }
        }
        .background(Color.background.ignoresSafeArea())
    }
}

extension ImportCsvContent {
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
