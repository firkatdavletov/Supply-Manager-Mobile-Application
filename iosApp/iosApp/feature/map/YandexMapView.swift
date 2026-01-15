//
//  YandexMapView.swift
//  iosApp
//
//  Created by Фиркат Давлетов on 11.05.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import YandexMapsMobile
import Shared
import CoreLocation

struct YandexMapView: UIViewRepresentable {
    let position: UiPoint?
    let moveToLocation: Bool
    let showDepartments: Bool
    let selectedDepartment: KotlinInt?
    let departments: Array<DepartmentModel>
    let deliveryType: DeliveryType
    var onMapMoved: ((Double, Double, UInt, Bool) -> Void)? = nil
    var onSelectDepartments: ((Int64) -> Void)? = nil

    func makeUIView(context: Context) -> YMKMapView {
        let mapView = YMKMapView()
        context.coordinator.mapView = mapView
        context.coordinator.listener = CameraListener(onMapMoved: onMapMoved)
        context.coordinator.tapListener = TapListener(onTap: onSelectDepartments)
        mapView.mapWindow.map.addCameraListener(with: context.coordinator.listener!)
        return mapView
    }

    func updateUIView(_ uiView: YMKMapView, context: Context) {
        DispatchQueue.main.async {
            if (moveToLocation && position != nil) {
                let point = YMKPoint(latitude: position!.latitude, longitude: position!.longitude)
                let position = YMKCameraPosition(target: point, zoom: 15, azimuth: 0, tilt: 0)
                uiView.mapWindow.map.move(
                    with: position,
                    animation: YMKAnimation(type: .smooth, duration: 0.8),
                    cameraCallback: nil
                )
                self.removePlacemarks(mapView: uiView)
                
                if (deliveryType == DeliveryType.pickup) {
                    departments.forEach { department in
                        let point = YMKPoint(latitude: department.latitude, longitude: department.longitude)
                        self.addPlacemark(
                            mapView: uiView,
                            point: point,
                            selected: department.selected,
                            id: Int64(department.id),
                            listener: context.coordinator.tapListener!
                        )
                    }
                }
            }
        }
    }

    
    private func addPlacemark(mapView: YMKMapView, point: YMKPoint, selected: Bool, id: Int64, listener: YMKMapObjectTapListener) {
        let placemark = mapView.mapWindow.map.mapObjects.addPlacemark()
            placemark.geometry = point
            placemark.userData = id

            let imageName = "Placemark"
            guard let image = UIImage(named: imageName) else { return }

        placemark.setIconWith(image.withTintColor(UIColor.orange))
            placemark.addTapListener(with: listener)
    }
    
    private func removePlacemarks(mapView: YMKMapView) {
        mapView.mapWindow.map.mapObjects.clear()
    }

    func makeCoordinator() -> Coordinator {
        Coordinator(
            onMapMoved: onMapMoved,
            onTap: onSelectDepartments
        )
    }

    class Coordinator {
        var mapView: YMKMapView?
        var listener: CameraListener?
        var tapListener: TapListener?

        init(
            onMapMoved: ((Double, Double, UInt, Bool) -> Void)?,
            onTap: ((Int64) -> Void)?
        ) {
            self.listener = CameraListener(onMapMoved: onMapMoved)
            self.tapListener = TapListener(onTap: onTap)
        }
    }

    class CameraListener: NSObject, YMKMapCameraListener {
        let onMapMoved: ((Double, Double, UInt, Bool) -> Void)?

        init(onMapMoved: ((Double, Double, UInt, Bool) -> Void)?) {
            self.onMapMoved = onMapMoved
        }
        
        func onCameraPositionChanged(
            with map: YMKMap,
            cameraPosition: YMKCameraPosition,
            cameraUpdateReason: YMKCameraUpdateReason,
            finished: Bool
        ) {
            let center = cameraPosition.target
            onMapMoved?(center.latitude, center.longitude, cameraUpdateReason.rawValue, finished)
        }
    }
    
    class TapListener: NSObject, YMKMapObjectTapListener {
        let onTap: ((Int64) -> Void)?
        
        init(onTap: ((Int64) -> Void)?) {
            self.onTap = onTap
        }
        func onMapObjectTap(with mapObject: YMKMapObject, point: YMKPoint) -> Bool {
            let id = mapObject.userData as! Int64
            onTap?(id)
            return true
        }
    }
}
