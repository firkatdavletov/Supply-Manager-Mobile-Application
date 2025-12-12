//
//  IOSSecurityStorageImpl.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 07.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Shared
import UIKit

@objc class IOSSecurityStorageImpl: NSObject, SecurityStorage {
    func saveCartToken(token: String) {
        save(key: "cart_token", value: token)
    }
    func saveAccessToken(token: String) {
        save(key: "access_token", value: token)
    }
        
    func saveRefreshToken(token: String) {
        save(key: "refresh_token", value: token)
    }
    
    func getCartToken() -> String {
        return load(key: "cart_token")
    }

    func getAccessToken() -> String {
        return load(key: "access_token")
    }

    func getRefreshToken() -> String {
        return load(key: "refresh_token")
    }
    
    func getDeviceId() -> String {
        return UIDevice.current.identifierForVendor!.uuidString
    }
    
    private func save(key: String, value: String) {
        delete(key: key)
            
        if let data = value.data(using: .utf8) {
            let query: [String: Any] = [
                kSecClass as String: kSecClassGenericPassword,
                kSecAttrAccount as String: key,
                kSecValueData as String: data
            ]
            SecItemAdd(query as CFDictionary, nil)
        }
    }
    
    private func load(key: String) -> String {
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: key,
            kSecReturnData as String: kCFBooleanTrue!,
            kSecMatchLimit as String: kSecMatchLimitOne
        ]

        var result: AnyObject?
        let status = SecItemCopyMatching(query as CFDictionary, &result)
        
        if status == errSecSuccess,
            let data = result as? Data,
            let value = String(data: data, encoding: .utf8) {
            return value
        }
        return ""
    }

    private func delete(key: String) {
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: key
        ]
        SecItemDelete(query as CFDictionary)
    }
}
