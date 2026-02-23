package org.example.project.feature.import_csv

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.project.features.import_csv.ImportCsvComponent
import org.example.project.features.import_csv.ImportCsvViewEvent

@Composable
fun ImportCsvScreen(component: ImportCsvComponent) {
    val state by component.state.subscribeAsState()
    val context = LocalContext.current
    val openDocumentLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument(),
        ) { uri: Uri? ->
            if (uri == null) return@rememberLauncherForActivityResult

            val fileName = context.resolveFileName(uri) ?: "import.csv"
            val fileBytes = runCatching {
                context.contentResolver
                    .openInputStream(uri)
                    ?.use { it.readBytes() }
            }.getOrNull()

            if (fileBytes == null) {
                component.onEvent(ImportCsvViewEvent.OnError("Не удалось прочитать выбранный файл"))
                return@rememberLauncherForActivityResult
            }

            component.onEvent(
                ImportCsvViewEvent.OnFileSelected(
                    fileName = fileName,
                    fileBytes = fileBytes,
                ),
            )
        }

    BackHandler {
        component.onEvent(ImportCsvViewEvent.OnBackClicked)
    }

    ImportCsvContent(
        title = state.title,
        modes = state.modes,
        selectedMode = state.selectedMode,
        selectedFileName = state.selectedFileName,
        isLoading = state.isLoading,
        responseTitle = state.responseTitle,
        responseDetails = state.responseDetails,
        isResponseError = state.isResponseError,
        onBackClicked = {
            component.onEvent(ImportCsvViewEvent.OnBackClicked)
        },
        onModeSelected = { mode ->
            component.onEvent(ImportCsvViewEvent.OnModeSelected(mode))
        },
        onSelectCsvClicked = {
            openDocumentLauncher.launch(
                arrayOf(
                    "text/csv",
                    "text/comma-separated-values",
                    "text/plain",
                    "application/vnd.ms-excel",
                ),
            )
        },
        onImportClicked = {
            component.onEvent(ImportCsvViewEvent.OnImportClicked)
        },
    )
}

private fun Context.resolveFileName(uri: Uri): String? {
    contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (nameIndex != -1 && cursor.moveToFirst()) {
            return cursor.getString(nameIndex)
        }
    }
    return null
}
