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
    var onMapMoved: ((Double, Double, UInt, Bool) -> Void)? = nil
    var onSelectDepartments: ((KotlinInt) -> Void)? = nil

    func makeUIView(context: Context) -> YMKMapView {
        let mapView = YMKMapView(frame: .zero)
        context.coordinator.mapView = mapView
        context.coordinator.listener = CameraListener(onMapMoved: onMapMoved)
        context.coordinator.tapListener = TapListener(onTap: onSelectDepartments)
        mapView!.mapWindow.map.addCameraListener(with: context.coordinator.listener!)
        return mapView!
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
            }
        }
    }

    
    private func addPlacemark(mapView: YMKMapView, point: YMKPoint, selected: Bool, id: Int64, listener: YMKMapObjectTapListener) {
        let placemark = mapView.mapWindow.map.mapObjects.addPlacemark()
        placemark.geometry = point
        placemark.userData = id
        let image = if (selected) {
            drawRingImage(diameter: 24, ringWidth: 6, color: UIColor(Color("PrimaryColor")))
        } else {
            drawRingImage(diameter: 20, ringWidth: 5, color: UIColor(Color("PrimaryColor")))
        }
        placemark.setIconWith(image)
        placemark.addTapListener(with: listener)
    }
    
    private func removePlacemarks(mapView: YMKMapView) {
        mapView.mapWindow.map.mapObjects.clear()
    }
    
    func drawRingImage(diameter: CGFloat, ringWidth: CGFloat, color: UIColor) -> UIImage {
        let size = CGSize(width: diameter, height: diameter)
        let renderer = UIGraphicsImageRenderer(size: size)

        return renderer.image { context in
            let rect = CGRect(origin: .zero, size: size)
            let path = UIBezierPath(ovalIn: rect.insetBy(dx: ringWidth / 2, dy: ringWidth / 2))
            color.setStroke()
            path.lineWidth = ringWidth
            path.stroke()
        }
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
            onTap: ((KotlinInt) -> Void)?
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
        let onTap: ((KotlinInt) -> Void)?
        
        init(onTap: ((KotlinInt) -> Void)?) {
            self.onTap = onTap
        }
        func onMapObjectTap(with mapObject: YMKMapObject, point: YMKPoint) -> Bool {
            let id = mapObject.userData as! KotlinInt
            onTap?(id)
            return true
        }
    }
}
