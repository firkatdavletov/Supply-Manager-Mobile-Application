package org.example.project.data.datastore.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AndroidSecurityStorage : SecurityStorage {

    private var prefs: SharedPreferences? = null

    fun initialize(context: Context) {
        prefs = context.getSharedPreferences("secure_prefs", Context.MODE_PRIVATE)
    }

    private val keyAlias = "secure_token_key"

    private val charset = Charsets.UTF_8
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

    override fun saveCartToken(token: String) {
        Log.d("AndroidSecurityStorage", "saveCartToken $token")
        val (encrypted, iv) = encrypt(token)
        with(prefs?.edit() ?: throw NullPointerException()) {
            putString("cart_token", encrypted)
            putString("cart_token_iv", iv)
            apply()
        }
    }

    override fun saveAccessToken(token: String) {
        Log.d("AndroidSecurityStorage", "saveAccessToken $token")
        val (encrypted, iv) = encrypt(token)
        with(prefs?.edit() ?: throw NullPointerException()) {
            putString("access_token", encrypted)
            putString("access_token_iv", iv)
            apply()
        }
    }

    override fun saveRefreshToken(token: String) {
        Log.d("AndroidSecurityStorage", "saveRefreshToken $token")
        val (encrypted, iv) = encrypt(token)
        with(prefs?.edit() ?: throw NullPointerException()) {
            putString("refresh_token", encrypted)
            putString("refresh_token_iv", iv)
            apply()
        }
    }

    override fun getCartToken(): String {
        val encrypted = prefs?.getString("cart_token", null) ?: return ""
        val iv = prefs?.getString("cart_token_iv", null) ?: return ""
        val token = decrypt(encrypted, iv)
        Log.d("AndroidSecurityStorage", "getCartToken $token")
        return token
    }

    override fun getAccessToken(): String {
        val encrypted = prefs?.getString("access_token", null) ?: return ""
        val iv = prefs?.getString("access_token_iv", null) ?: return ""
        val token = decrypt(encrypted, iv)
        Log.d("AndroidSecurityStorage", "getAccessToken $token")
        return token
    }

    override fun getRefreshToken(): String {
        val encrypted = prefs?.getString("refresh_token", null) ?: return ""
        val iv = prefs?.getString("refresh_token_iv", null) ?: return ""
        val token = decrypt(encrypted, iv)
        Log.d("AndroidSecurityStorage", "getAccessToken $token")
        return token
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getDeviceId(): String {
        val deviceId = prefs?.getString("getRefreshToken", null)
        if (deviceId != null) {
            return deviceId
        } else {
            val newDeviceId = Uuid.random().toString()
            with(prefs?.edit() ?: throw NullPointerException()) {
                putString("device_id", newDeviceId)
                apply()
            }

            return newDeviceId
        }
    }

    private fun getSecretKey(): SecretKey {
        if (!keyStore.containsAlias(keyAlias)) {
            val keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore")
            keyGenerator.init(
                android.security.keystore.KeyGenParameterSpec.Builder(
                    keyAlias,
                    android.security.keystore.KeyProperties.PURPOSE_ENCRYPT or android.security.keystore.KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(android.security.keystore.KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()
            )
            keyGenerator.generateKey()
        }
        val secretKeyEntry = keyStore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry
        return secretKeyEntry.secretKey
    }

    private fun encrypt(plainText: String): Pair<String, String> {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val iv = cipher.iv
        val encrypted = cipher.doFinal(plainText.toByteArray(charset))
        return Base64.encodeToString(encrypted, Base64.DEFAULT) to Base64.encodeToString(iv, Base64.DEFAULT)
    }

    private fun decrypt(encryptedText: String, ivText: String): String {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val iv = Base64.decode(ivText, Base64.DEFAULT)
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), spec)
        val decodedBytes = Base64.decode(encryptedText, Base64.DEFAULT)
        return String(cipher.doFinal(decodedBytes), charset)
    }
}