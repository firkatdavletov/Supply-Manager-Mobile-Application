//
//  AppDelegate.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 21.09.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import YandexMapsMobile

class AppDelegate: NSObject, UIApplicationDelegate {
    let rootHolder: RootHolder = RootHolder()
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil) -> Bool {
        YMKMapKit.setApiKey("ae6b93e5-52ca-4ab3-879c-34d8728b59b5")
        YMKMapKit.sharedInstance()
        return true
    }
    
    func application(_ application: UIApplication, continue userActivity: NSUserActivity, restorationHandler: @escaping ([UIUserActivityRestoring]?) -> Void) -> Bool {
        return true
    }

    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
        return true
    }

}
