//
//  LocationManager.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 12.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import Foundation
import YandexMapsMobile
import CoreLocation

class LocationManager: NSObject, CLLocationManagerDelegate, ObservableObject {
    
    private let locationManager = CLLocationManager()
    private var locationCallback: ((CLLocation) -> Void)?

    func updateCurrentLocation() {
        locationManager.startUpdatingLocation()
    }
    
    override init() {
        super.init()
        self.locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
    }
    
    func requestLocation(completion: @escaping (CLLocation) -> Void) {
        self.locationCallback = completion
        locationManager.requestWhenInUseAuthorization()
        locationManager.requestLocation()
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        if let location = locations.first {
            locationCallback?(location)
            locationCallback = nil
        }
    }
        
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        print("Ошибка получения местоположения: \(error.localizedDescription)")
        locationCallback = nil
    }
}
