package org.example.project.data.api.catalog_import

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.path
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonPrimitive
import org.example.project.data.api.catalog_import.model.ImportCatalogCsvResponseBody
import org.example.project.domain.models.CatalogImportMode

class CatalogImportApiImpl(
    private val httpClient: HttpClient,
) : CatalogImportApi {
    private val json = Json {
        isLenient = true
    }

    override suspend fun importCsv(
        mode: CatalogImportMode,
        fileName: String,
        fileBytes: ByteArray,
    ): ImportCatalogCsvResponseBody {
        val responseText =
            httpClient
                .post {
                    url {
                        path("shop", "catalog", "import")
                        parameters.append("mode", mode.serverValue)
                    }
                    setBody(
                        MultiPartFormDataContent(
                            formData {
                                append(
                                    key = "file",
                                    value = fileBytes,
                                    headers = Headers.build {
                                        append(HttpHeaders.ContentType, "text/csv")
                                        append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                                    },
                                )
                            },
                        ),
                    )
                }.bodyAsText()

        val jsonObject = runCatching {
            json.parseToJsonElement(responseText) as? JsonObject
        }.getOrNull() ?: JsonObject(mapOf())

        val success =
            jsonObject["success"]
                ?.jsonPrimitive
                ?.booleanOrNull ?: false
        val error = jsonObject["error"].asTextOrNull()
        val code =
            jsonObject["code"]
                ?.jsonPrimitive
                ?.intOrNull
        val message = jsonObject["message"].asTextOrNull()
        val details =
            jsonObject["details"].asTextOrNull()
                ?: jsonObject["detail"].asTextOrNull()
                ?: jsonObject["data"].asTextOrNull()
                ?: jsonObject["result"].asTextOrNull()

        return ImportCatalogCsvResponseBody(
            success = success,
            error = error,
            code = code,
            message = message,
            details = details,
        )
    }
}

private fun JsonElement?.asTextOrNull(): String? {
    return when (this) {
        null -> {
            null
        }

        is JsonPrimitive -> {
            if (this.isString) this.content else this.toString()
        }

        else -> {
            this.toString()
        }
    }
}