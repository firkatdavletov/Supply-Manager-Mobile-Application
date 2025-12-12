package org.example.project.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle

object GeolocationService {
    @SuppressLint("MissingPermission")
    fun getCurrentLocationWithManager(
        context: Context,
        onLocationReceived: (Location?) -> Unit
    ) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Попробуем получить последнее известное местоположение
        val providers = locationManager.getProviders(true)
        var bestLocation: Location? = null
        for (provider in providers) {
            val l = locationManager.getLastKnownLocation(provider) ?: continue
            if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                bestLocation = l
            }
        }

        if (bestLocation != null) {
            onLocationReceived(bestLocation)
            return
        }

        // Если нет — запросим обновление
        val listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                onLocationReceived(location)
                locationManager.removeUpdates(this)
            }

            @Deprecated("Deprecated in API 31")
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        // GPS предпочтителен, если доступен
        when {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ->
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000L,
                    0f,
                    listener
                )

            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ->
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    1000L,
                    0f,
                    listener
                )

            else -> onLocationReceived(null)
        }
    }

}