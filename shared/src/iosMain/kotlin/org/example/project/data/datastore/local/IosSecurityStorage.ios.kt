package org.example.project.data.datastore.local

import kotlinx.cinterop.*
import platform.CoreFoundation.*
import platform.Foundation.*
import platform.Security.*
import platform.UIKit.UIDevice
import kotlin.run

class IosSecurityStorage: SecurityStorage {
    private var deviceId: String? = null

    override fun saveCartToken(token: String) {
        setItem("cart_token", token)
    }

    override fun saveAccessToken(token: String) {
        setItem("access_token", token)
    }

    override fun saveRefreshToken(token: String) {
        setItem("refresh_token", token)
    }

    override fun getCartToken(): String {
        return getItem("cart_token")
    }

    override fun getAccessToken(): String {
        return getItem("access_token")
    }

    override fun getRefreshToken(): String {
        return getItem("refresh_token")
    }

    override fun getDeviceId(): String {
        if (deviceId == null) {
            deviceId = UIDevice.currentDevice.systemName() + "_" + UIDevice.currentDevice.systemVersion()
        }
        return deviceId!!
    }

    @OptIn(BetaInteropApi::class, ExperimentalForeignApi::class)
    private fun setItem(key: String, value: String) {
        val valueAsNSString = value.run {
            NSString.create(string = this).dataUsingEncoding(NSUTF8StringEncoding)
        }

        val query = query(
            kSecClass to kSecClassGenericPassword,
            kSecAttrAccount to CFBridgingRetain(key),
            kSecValueData to CFBridgingRetain(valueAsNSString)
        )

        SecItemDelete(query)
        val status = SecItemAdd(query, null)

        if (status == errSecDuplicateItem) {
            throw Exception("Item with key $key already exists")
        } else if (status != errSecSuccess) {
            throw Exception("Error adding item with key $key")
        }
    }

    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    private fun getItem(key: String): String {
        val getQuery = query(
            kSecClass to kSecClassGenericPassword,
            kSecAttrAccount to CFBridgingRetain(key),
            kSecReturnData to kCFBooleanTrue,
            kSecMatchLimit to kSecMatchLimitOne
        )

        memScoped {
            val result = alloc<CFTypeRefVar>()
            val status = SecItemCopyMatching(getQuery, result.ptr)

            if (status == errSecItemNotFound) {
                return ""
            } else if (status != errSecSuccess) {
                throw Exception("Error getting item with key $key")
            } else {
                val data = CFBridgingRelease(result.value) as NSData
                return NSString.create(data, NSUTF8StringEncoding).toString()
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun removeItem(key: String) {
        val deleteQuery = query(
            kSecClass to kSecClassGenericPassword,
            kSecAttrAccount to CFBridgingRetain(key),
            kSecReturnData to kCFBooleanTrue,
        )

        val status = SecItemDelete(deleteQuery)

        if (status != errSecSuccess) {
            throw Exception("Error deleting item with key $key and status $status")
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun query(vararg pairs: Pair<CFStringRef?, CFTypeRef?>): CFMutableDictionaryRef? {
    val map = mapOf(*pairs)
    return CFDictionaryCreateMutable(
        null, map.size.convert(), null, null
    ).apply {
        map.forEach { CFDictionaryAddValue(this, it.key, it.value) }
    }.apply {
        CFAutorelease(this)
    }
}