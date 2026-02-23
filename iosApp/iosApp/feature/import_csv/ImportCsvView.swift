import SwiftUI
import Shared
import UniformTypeIdentifiers

struct ImportCsvView: View {
    let component: ImportCsvComponent

    @StateValue private var state: ImportCsvViewState
    @State private var showFileImporter = false

    init(component: ImportCsvComponent) {
        self.component = component
        _state = StateValue(component.state)
    }

    var body: some View {
        ImportCsvContent(
            title: state.title,
            modes: state.modes,
            selectedMode: state.selectedMode,
            selectedFileName: state.selectedFileName,
            isLoading: state.isLoading,
            responseTitle: state.responseTitle,
            responseDetails: state.responseDetails,
            isResponseError: state.isResponseError,
            onBack: {
                component.onEvent(event: ImportCsvViewEventOnBackClicked())
            },
            onModeSelected: { mode in
                component.onEvent(event: ImportCsvViewEventOnModeSelected(mode: mode))
            },
            onSelectFile: {
                showFileImporter = true
            },
            onImport: {
                component.onEvent(event: ImportCsvViewEventOnImportClicked())
            }
        )
        .navigationBarBackButtonHidden(true)
        .fileImporter(
            isPresented: $showFileImporter,
            allowedContentTypes: [.commaSeparatedText, .plainText],
            allowsMultipleSelection: false
        ) { result in
            switch result {
            case .success(let urls):
                guard let url = urls.first else { return }
                let isStarted = url.startAccessingSecurityScopedResource()
                defer {
                    if isStarted {
                        url.stopAccessingSecurityScopedResource()
                    }
                }

                do {
                    let data = try Data(contentsOf: url)
                    component.onEvent(
                        event: ImportCsvViewEventOnFileSelected(
                            fileName: url.lastPathComponent,
                            fileBytes: data.toKotlinByteArray()
                        )
                    )
                } catch {
                    component.onEvent(event: ImportCsvViewEventOnError(error: "Не удалось прочитать выбранный файл"))
                }
            case .failure:
                component.onEvent(event: ImportCsvViewEventOnError(error: "Не удалось выбрать файл"))
            }
        }
    }
}

private extension Data {
    func toKotlinByteArray() -> KotlinByteArray {
        let kotlinByteArray = KotlinByteArray(size: Int32(count))
        for (index, element) in enumerated() {
            kotlinByteArray.set(index: Int32(index), value: Int8(bitPattern: element))
        }
        return kotlinByteArray
    }
}
